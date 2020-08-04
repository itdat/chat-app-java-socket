package com.ntdat.chatapp.socket;

import com.ntdat.chatapp.AppConfig;
import com.ntdat.chatapp.data.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.ntdat.chatapp.AppConfig.*;

public class Server
{
    static Vector<ClientHandler> activeList = new Vector<>();
    static List<User> userList = new ArrayList<>();
    static int i = 0;
    public static void main(String[] args) throws IOException
    {
        // Init directory
        File dataDirectory = new File(DATA_DIRECTORY);
        dataDirectory.mkdirs();

        ServerSocket ss = new ServerSocket(SERVER_PORT);
        System.out.println("Sever start on port " + SERVER_PORT);
        System.out.println("Waiting for client request...");
        Socket s;
        while (true)
        {
            s = ss.accept();
            System.out.println("New client request received : " + s);
            
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating a new handler for this client...");
            
            ClientHandler clientHandler = new ClientHandler(s,"client " + i, dis, dos);
            Thread t = new Thread(clientHandler);
            t.start();

            i++;
        }
    }
}

class ClientHandler implements Runnable
{
    Socket s;
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;

    private List<String> receivedFile = new ArrayList<>();
    private String receivedFileName;
    private String hashName;

    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.name = name;
        this.dis = dis;
        this.dos = dos;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void loadUserList() {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream(AppConfig.USER_LIST_DATA_PATH));
            Server.userList = (List<User>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void storeUserList() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(AppConfig.USER_LIST_DATA_PATH));
        oos.writeObject(Server.userList);
        oos.close();
    }

    @Override
    public void run() {

        String received;
        while (true)
        {
            try
            {
                received = dis.readUTF();
                System.out.println(received);
                String[] tokens = received.split("\\|");
                List<String> usernames;
                String newOnlineList;
                switch (tokens[0]) {
                    case "LOGOUT":
                        for (int i = 0; i < Server.activeList.size(); i++) {
                            if (Server.activeList.get(i).s == this.s) {
                                Server.activeList.remove(i);
                                this.s.close();
                                break;
                            }
                        }
                        System.out.println(this.name + " log out");
                        usernames = new ArrayList<>();
                        for (ClientHandler client : Server.activeList) {
                            usernames.add(client.name);
                        }
                        newOnlineList = String.join(",", usernames);
                        for (ClientHandler client : Server.activeList) {
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
                        Server.activeList.add(this);
                        System.out.println(this.name + " log in");

                        usernames = new ArrayList<>();
                        for (ClientHandler client : Server.activeList) {
                            usernames.add(client.name);
                        }
                        newOnlineList = String.join(",", usernames);

                        for (ClientHandler client : Server.activeList) {
                            client.dos.writeUTF("UPDATE_ONLINE_LIST|" + newOnlineList);
                        }
                        break;
                    case "SEND":
                        // "SEND|recipient|message|sender"
                        String recipient = tokens[1];
                        String message = tokens[2];
                        String sender = tokens[3];
                        for (ClientHandler client : Server.activeList) {
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
                            for (ClientHandler client : Server.activeList) {
                                if (client.name.equals(recipientFile)) {
                                    client.dos.writeUTF("MESSAGE_FILE|<b style='color:yellow' title='file:" + hashName + "'>" + this.receivedFileName + "</b>|" + senderFile);
                                    break;
                                }
                            }
                        }
                        break;
                    case "GET_FILE":
                        String fileName = tokens[1];
                        String hashName = tokens[2];
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                        byte[] fileDataByte = Files.readAllBytes(Paths.get("./src/main/resources/data/" + hashName));
                        byte[] encodeDataByte = Base64.getEncoder().withoutPadding().encode(fileDataByte);
                        String encodeDataString = new String(encodeDataByte);
                        dos.writeUTF("DELIVER|INFO|" + fileName);
                        while (encodeDataString.length() > 4096) {
                            String subString = encodeDataString.substring(0, 4096);
                            try {
                                dos.writeUTF("DELIVER|REMAIN|" + subString);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            encodeDataString = encodeDataString.substring(4096);
                        }
                        dos.writeUTF("DELIVER|END|" + encodeDataString);
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