package com.ntdat.chatapp.socket;

import com.ntdat.chatapp.data.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Server
{
    // Vector to store active clients
    static Vector<ClientHandler> ar = new Vector<>();
    static List<User> userList = new ArrayList<>();

    // counter for clients
    static int i = 0;

    public static void main(String[] args) throws IOException
    {
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(1234);

        Socket s;

        // running infinite loop for getting
        // client request
        while (true)
        {
            // Accept the incoming request
            s = ss.accept();

            System.out.println("New client request received : " + s);

            // obtain input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(s,"client " + i, dis, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(mtch);

//            System.out.println("Adding this client to active client list");

            // add this client to active clients list
//            ar.add(mtch);

            // start the thread.
            t.start();

            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            i++;

        }
    }
}

// ClientHandler class
class ClientHandler implements Runnable
{
    private static final String USER_LIST_DATA_PATH = "./src/main/resources/data/user-list.dat";

    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    private List<String> receivedFile = new ArrayList<>();
    private String receivedFileName;
    private String hashName;

    // constructor
    public ClientHandler(Socket s, String name,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin=true;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void loadUserList() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(USER_LIST_DATA_PATH));
            Server.userList = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void storeUserList() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_LIST_DATA_PATH));
        oos.writeObject(Server.userList);
    }

    @Override
    public void run() {

        String received;
        while (true)
        {
            try
            {
                // receive the string
                received = dis.readUTF();

                System.out.println(received);

                String[] tokens = received.split("\\|");

                List<String> usernames;
                String newOnlineList;

                switch (tokens[0]) {
                    case "LOGOUT":
                        for (int i = 0; i < Server.ar.size(); i++) {
                            if (Server.ar.get(i).s == this.s) {
                                Server.ar.remove(i);
                                this.s.close();
                                break;
                            }
                        }
                        System.out.println("Deleted " + this.name + " from active list");
                        usernames = new ArrayList<>();
                        for (ClientHandler client : Server.ar) {
                            usernames.add(client.name);
                        }
                        newOnlineList = String.join(",", usernames);
                        for (ClientHandler client : Server.ar) {
                            client.dos.writeUTF("UPDATE_ONLINE_LIST|" + newOnlineList);
                        }
                        break;
                    case "REGISTER":
                        loadUserList();
                        boolean isExisted = false;
                        for (User user : Server.userList) {
                            if (user.getUsername().equals(tokens[1])) {
                                dos.writeUTF("USERNAME_EXISTED");
                                this.s.close();
                                isExisted = true;
                                break;
                            }
                        }
                        if (!isExisted) {
                            User newUser = new User(tokens[1], tokens[2]);
                            Server.userList.add(newUser);
                            storeUserList();
                            dos.writeUTF("REGISTER_SUCCESS");
                            this.s.close();
                            break;
                        }
                        break;
                    case "LOGIN":
                        loadUserList();
                        boolean isValid = false;
                        for (User user : Server.userList) {
                            if (user.getUsername().equals(tokens[1])) {
                                isValid = true;
                                if (user.getPassword().equals(tokens[2])) {
                                    this.setName(tokens[1]);
                                    dos.writeUTF("LOGIN_SUCCESS");
                                    break;
                                } else {
                                    dos.writeUTF("WRONG_PASSWORD");
                                    this.s.close();
                                    break;
                                }
                            }
                        }
                        if (!isValid) {
                            dos.writeUTF("USER_NOT_EXIST");
                            this.s.close();
                            break;
                        }
                        break;
                    case "READY":
                        Server.ar.add(this);
                        System.out.println("Added " + this.name + " to active list");

                        usernames = new ArrayList<>();
                        for (ClientHandler client : Server.ar) {
                            usernames.add(client.name);
                        }
                        newOnlineList = String.join(",", usernames);

                        for (ClientHandler client : Server.ar) {
                            client.dos.writeUTF("UPDATE_ONLINE_LIST|" + newOnlineList);
                        }
                        break;
                    case "SEND":
                        // "SEND|recipient|message|sender"
                        String recipient = tokens[1];
                        String message = tokens[2];
                        String sender = tokens[3];
                        for (ClientHandler client : Server.ar) {
                            if (client.name.equals(recipient)) {
                                client.dos.writeUTF("MESSAGE|" + message + "|" + sender);
                                break;
                            }
                        }
                        break;
                    case "SEND_FILE":
                        if (tokens[1].equals("INFO")) {
                            this.receivedFileName = tokens[2];
                            this.hashName = tokens[3];
                        } else if (tokens[1].equals("REMAIN")) {
                            String msg = received.substring("SEND_FILE|REMAIN|".length());
                            receivedFile.add(msg);
                        } else if (tokens[1].equals("END")) {
                            String msg = received.substring("SEND_FILE|END|".length());
                            receivedFile.add(msg);
                            String receivedString = String.join("", receivedFile);
                            byte[] decodeFileData = null;
                            try {
                                decodeFileData = Base64.getDecoder().decode(receivedString.getBytes());
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                FileOutputStream fos = new FileOutputStream("./src/main/resources/data/" + hashName);
                                assert decodeFileData != null;
                                fos.write(decodeFileData);
                                fos.close();
                            }
                        } else {
                            String recipientFile = tokens[3];
                            String senderFile = tokens[4];
                            for (ClientHandler client : Server.ar) {
                                if (client.name.equals(recipientFile)) {
                                    client.dos.writeUTF("MESSAGE_FILE|<b style='color:yellow' title='file:" + hashName + "'>" + this.receivedFileName + "</b>|" + senderFile);
                                    break;
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

        }
    }
}