package com.ntdat.chatapp;

import java.io.*;
import java.util.Scanner;

public class AppConfig {
    public static final int SERVER_PORT = 1234;
    public static final String DEFAULT_IP_SERVER = "192.168.1.14";
    public static final String DEFAULT_PORT_SERVER = "1111";
    public static final String DATA_DIRECTORY = "./data";
    public static final String CONVERSATION_DICRECTORY = DATA_DIRECTORY + "/conversations";
    public static final String USER_LIST_DATA_PATH = DATA_DIRECTORY + "/user-list.dat";
    public static final String IP_CONFIG_FILE = DATA_DIRECTORY + "/ip-config.txt";
    public static final String PORT_CONFIG_FILE = DATA_DIRECTORY + "/port-config.txt";

    public static  void initIpConfigFile() throws IOException {
        FileWriter myWriter = new FileWriter(IP_CONFIG_FILE);
        myWriter.write(DEFAULT_IP_SERVER);
        myWriter.close();
    }

    public static String getIpServer() {
        String ipAddress = "";
        try {
            File myObj = new File(IP_CONFIG_FILE);
            System.out.println(myObj.getAbsolutePath());
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                ipAddress = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("ip-config.txt not founded.");
            initIpConfigFile();
            System.out.println("Initialized ip-config.txt");
            ipAddress = DEFAULT_IP_SERVER;
        } finally {
            return ipAddress;
        }
    }

    public static void initPortConfigFile() throws IOException {
        FileWriter myWriter = new FileWriter(PORT_CONFIG_FILE);
        myWriter.write(DEFAULT_PORT_SERVER);
        myWriter.close();
    }

    public static int getPortServer() {
        int port = 1111;
        try {
            File myObj = new File(PORT_CONFIG_FILE);
            System.out.println(myObj.getAbsolutePath());
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                port = Integer.parseInt(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("port-config.txt not founded.");
            initPortConfigFile();
            System.out.println("Initialized port-config.txt");
            port = Integer.parseInt(DEFAULT_PORT_SERVER);
        } finally {
            return port;
        }
    }

    public static boolean storeServerAddress(String ipAddress, String port) {
        boolean success = true;
        try {
            FileWriter myWriter = new FileWriter(IP_CONFIG_FILE);
            myWriter.write(ipAddress);
            myWriter.close();
            myWriter = new FileWriter(PORT_CONFIG_FILE);
            myWriter.write(port);
            myWriter.close();
        } catch (IOException e) {
            success = false;
        } finally {
            return success;
        }
    }
}
