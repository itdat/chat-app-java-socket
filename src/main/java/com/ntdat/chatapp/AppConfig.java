package com.ntdat.chatapp;

import java.io.*;
import java.util.Scanner;

public class AppConfig {
    public static final int SERVER_PORT = 1234;
    public static final String DEFAULT_IP_SERVER = "192.168.1.14";
    public static final String DATA_DIRECTORY = "./data";
    public static final String CONVERSATION_DICRECTORY = DATA_DIRECTORY + "/conversations";
    public static final String USER_LIST_DATA_PATH = DATA_DIRECTORY + "/user-list.dat";
    public static final String IP_CONFIG_FILE = DATA_DIRECTORY + "/ip-config.txt";

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
}
