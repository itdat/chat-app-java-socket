/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui;

import com.ntdat.chatapp.Main;
import com.ntdat.chatapp.ui.customcomponent.*;
import com.ntdat.chatapp.ui.fragment.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.List;

import static javax.swing.UIManager.getDefaults;

public class MainFrame extends RoundedJFrame {
    // DEFINE VALUES
    private static final Font DEFAULT_FONT = new Font("Roboto", Font.PLAIN, 18);
    private static final Color PANEL_BACKGROUND_COLOR = Color.decode("#586692");
    private static final Color MOUSE_HOVER_NAV_BUTTON = new Color(51, 74, 137);
    private static final Color MOUSE_PRESS_NAV_BUTTON = new Color(36,55,114);
    private static final Color DEFAULT_NAV_BUTTON = new Color(88,102,146);


    private Socket socket;
    private String username;
    private String currentRecipient = null;

    private JPanel onlineList = new JPanel();
    private JPanel pnlMain;
    private ConversationPanel pnlConversation;

    private String currentDownloadFileName;
    private List<String> currentDownloadFile;

    private List<String> activeUsers;
    private int countHighLight = 0;

    private List<String> currentConversation;

    public MainFrame(Socket socket, String username) throws IOException {
        this.socket = socket;
        this.username = username;
        initComponents();
        updateUI();
    }

    private void updateUI() throws IOException {
        DataInputStream dis = new DataInputStream(this.socket.getInputStream());
        // readMessage thread
        Thread readMessage = new Thread(() -> {
            while (true) {
                try {
                    // read the message sent to this client
                    String msg = dis.readUTF();
//                    System.out.println(msg);
                    String[] tokens = msg.split("\\|");
                    switch (tokens[0]) {
                        case "UPDATE_ONLINE_LIST":
                            String[] users = tokens[1].split(",");
                            activeUsers = Arrays.asList(users);
                            updateOnlineList(activeUsers);
                            break;
                        case "MESSAGE":
                            String message = tokens[1];
                            String sender = tokens[2];
                            pushHistoryConversation(username, sender, sender+":"+message);
                            if (sender.equals(currentRecipient)) {
                                pnlConversation.addBubbleChatReceive(message);
                                pnlConversation.revalidate();
                                pnlConversation.repaint();
                            } else {
//                                for (int i = 0; i < activeUsers.size(); i++) {
//                                    if (activeUsers.get(i).equals(sender)) {
//                                        activeUsers.remove(i);
//                                        break;
//                                    }
//                                }
//                                activeUsers.add(0, sender);
//                                updateOnlineList(users);
                            }
                            break;
                        case "MESSAGE_FILE":
                            String messageFile = tokens[1];
                            String senderFile = tokens[2];
                            pushHistoryConversation(username, senderFile, senderFile + ":" + messageFile);
                            if (senderFile.equals(currentRecipient)) {
                                pnlConversation.addClickableBubbleChatReceive(messageFile);
                                pnlConversation.revalidate();
                                pnlConversation.repaint();
                            }
                            break;
                        case "DELIVER":
                            if (tokens[1].equals("INFO")) {
                                String fileName = tokens[2];
                                this.currentDownloadFileName = fileName;
                                this.currentDownloadFile = new ArrayList<>();
                            } else if (tokens[1].equals("REMAIN")) {
                                String data = msg.substring("DELIVER|REMAIN|".length());
                                currentDownloadFile.add(data);
                            } else {
                                String data = msg.substring("DELIVER|END|".length());
                                currentDownloadFile.add(data);
                                String receivedString = String.join("", currentDownloadFile);
                                byte[] decodeFileData = null;
                                try {
                                    decodeFileData = Base64.getDecoder().decode(receivedString.getBytes());
                                } catch (Exception ec) {
                                    ec.printStackTrace();
                                } finally {
                                    FileOutputStream fos = null;
                                    try {
                                        System.out.println(currentDownloadFileName);
                                        fos = new FileOutputStream(currentDownloadFileName);
                                    } catch (FileNotFoundException fileNotFoundException) {
                                        fileNotFoundException.printStackTrace();
                                    }
                                    assert decodeFileData != null;
                                    try {
                                        fos.write(decodeFileData);
                                        fos.close();
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                    break;
                                }
                            }
                            break;
                        case "CONVERSATION_HISTORY":
                            String recipient = tokens[2];
                            if (tokens.length == 4) {
                                String conversation = tokens[3];
                                System.out.println(tokens[3]);
                                if (currentRecipient.equals(recipient)) {
                                    currentConversation = Arrays.asList(conversation.split("#"));
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
        });
        readMessage.start();
    }

    public void initComponents() throws IOException {
        // =============================== TITLE BAR ===============================
        // Application name
        JLabel txtAppName = new JLabel();
        txtAppName.setFont(DEFAULT_FONT);
        txtAppName.setForeground(Color.WHITE);
        txtAppName.setText(Main.APP_NAME);

        // Create UI minimize button
        txtMinimize = new JLabel();
        txtMinimize.setFont(new Font("Roboto", Font.BOLD, 28));
        txtMinimize.setForeground(Color.WHITE);
        txtMinimize.setHorizontalAlignment(SwingConstants.CENTER);
        txtMinimize.setText("-");
        txtMinimize.setAlignmentY(0.0F);
        btnMinimize = new RoundedPanel(10);
        btnMinimize.setPreferredSize(new Dimension(30,30));
        btnMinimize.setBackground(new Color(77, 89, 159));
        btnMinimize.setLayout(new BorderLayout());
        btnMinimize.add(txtMinimize, BorderLayout.CENTER);

        // Create UI close button
        txtClose = new JLabel();
        txtClose.setFont(new Font("Roboto", Font.BOLD, 28));
        txtClose.setForeground(Color.WHITE);
        txtClose.setHorizontalAlignment(SwingConstants.CENTER);
        txtClose.setText("×");
        txtClose.setAlignmentY(0.0F);
        btnClose = new RoundedPanel(10);
        btnClose.setPreferredSize(new Dimension(30,30));
        btnClose.setBackground(new Color(77, 89, 159));
        btnClose.setLayout(new BorderLayout());
        btnClose.add(txtClose, BorderLayout.CENTER);

        // Create UI title bar
        JPanel titleBar = new JPanel();
        titleBar.setMaximumSize(new Dimension(1282, 40));
        titleBar.setBackground(Color.decode("#3A4281"));
        titleBar.setLayout(new GridBagLayout());
        txtAppName.setHorizontalAlignment(JLabel.LEADING);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(1,7,1,7);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        titleBar.add(txtAppName, gbc);

        gbc.insets = new Insets(1,3,1,3);
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        titleBar.add(btnMinimize, gbc);

        gbc.insets = new Insets(1,3,1,7);
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 0;
        titleBar.add(btnClose, gbc);
        // =========================================================================

        // ============================== SIDE PANEL ===============================
        // Logo
        JLabel txtLogo = new JLabel();
        txtLogo.setFont(new Font("EmojiOne", Font.PLAIN, 50));
        txtLogo.setText("ITD");
        Image logo = new ImageIcon(this.getClass().getResource("/logo_simple.png")).getImage();
        Image scaledLogo = logo.getScaledInstance(32, 32, Image.SCALE_DEFAULT);
        txtLogo.setIcon(new ImageIcon(scaledLogo));
        txtLogo.setForeground(Color.WHITE);
        txtLogo.setHorizontalAlignment(SwingConstants.CENTER);
        // Separator
        JSeparator spr1 = new JSeparator();
        spr1.setBackground(PANEL_BACKGROUND_COLOR);
        spr1.setForeground(PANEL_BACKGROUND_COLOR);

        // TextInput search username
        FlatTextInput searchUser = new FlatTextInput("Tìm tài khoản...");

        // Online list
        onlineList = new JPanel();
        onlineList.setBackground(Color.decode("#1E2252"));
        GroupLayout onlineListLayout = new GroupLayout(onlineList);
        onlineList.setLayout(onlineListLayout);
        GroupLayout.Group onlineListLayoutHorizontalGroup = onlineListLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.Group onlineListLayoutVerticalGroup = onlineListLayout.createSequentialGroup();

        FlatButton btn = new FlatButton();
        btn.setFont(DEFAULT_FONT);
        btn.setText(username);
        onlineListLayoutHorizontalGroup
                .addComponent(btn, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE);
        onlineListLayoutVerticalGroup
                .addGap(2)
                .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE);
        onlineListLayout.setHorizontalGroup(onlineListLayout.createSequentialGroup().addGap(2).addGroup(onlineListLayoutHorizontalGroup).addGap(2));
        onlineListLayout.setVerticalGroup(onlineListLayout.createSequentialGroup().addGroup(onlineListLayoutVerticalGroup));

        // Add to scrollable pane
        JScrollPane onlineListScroll = new JScrollPane();
        onlineListScroll.setBorder(new EmptyBorder(0,0,0,0));
        onlineListScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        onlineListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        onlineListScroll.getVerticalScrollBar().setUnitIncrement(24);
        onlineListScroll.getVerticalScrollBar().setUI(new MyScrollbarUI());
        onlineListScroll.setViewportView(onlineList);

        // Add elements to SidePanel
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.decode("#1E2252"));
        GroupLayout sidePanelLayout = new GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        GroupLayout.Group horizontalGroup = sidePanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(txtLogo)
                .addGap(2,2,2)
                .addComponent(spr1)
                .addGroup(sidePanelLayout.createSequentialGroup().addGap(2).addComponent(searchUser).addGap(2))
                .addComponent(onlineListScroll);
        GroupLayout.Group verticalGroup = sidePanelLayout.createSequentialGroup()
                .addGap(44,44,44)
                .addComponent(txtLogo)
                .addGap(37,37,37)
                .addComponent(spr1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(searchUser, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addComponent(onlineListScroll);
        sidePanelLayout.setHorizontalGroup(horizontalGroup);
        sidePanelLayout.setVerticalGroup(verticalGroup);
        // =========================================================================

        // ============================== MAIN PANEL ===============================
        pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        this.pnlConversation = new ConversationPanel(this.socket, this.username, null);
        pnlMain.add(this.pnlConversation);
        // =========================================================================

        // Background Panel
        JPanel background = new JPanel();
        background.setBackground(PANEL_BACKGROUND_COLOR);
        GroupLayout backgroundLayout = new GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(titleBar, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(sidePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(pnlMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(titleBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGroup(backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(sidePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        // Main JFrame
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(background, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(background, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1280, 768));
        setSize(new Dimension(1280, 768));
        setLocationRelativeTo(null);


        // =========================================================================
        // =========================================================================
        // =========================================================================


        
        titleBar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                moveFrame(evt);
            }
        });
        titleBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                getPosition(evt);
            }
        });

        txtLogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pnlConversation.addBubbleChatReceive("bla bla");
            }
        });

        btnClose.setBackground(Color.decode("#4D599F"));
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close();
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mouseEnterHandle(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mouseExitHandle(evt);
            }
        });

        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimize();
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mouseEnterHandle(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mouseExitHandle(evt);
            }
        });
    }

    private void updateOnlineList(List<String> users) {
        onlineList.removeAll();
        GroupLayout onlineListLayout = new GroupLayout(onlineList);
        onlineList.setLayout(onlineListLayout);
        GroupLayout.Group onlineListLayoutHorizontalGroup = onlineListLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.Group onlineListLayoutVerticalGroup = onlineListLayout.createSequentialGroup();

        int count = -1;
        for (String user : users) {
            count++;
            FlatButton btn = new FlatButton();
            if (count < countHighLight) {
                btn.setColors(MOUSE_HOVER_NAV_BUTTON, MOUSE_PRESS_NAV_BUTTON, new Color(49, 68, 127));
            } else {
                btn.setColors(MOUSE_HOVER_NAV_BUTTON, MOUSE_PRESS_NAV_BUTTON, DEFAULT_NAV_BUTTON);
            }
            btn.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    currentRecipient = user;
                    pnlMain.removeAll();
                    pnlConversation = new ConversationPanel(socket, username, user);
                    pnlMain.add(pnlConversation);

                    try {
                        loadHistoryConversation(username, currentRecipient);
                        pnlMain.revalidate();
                        pnlMain.repaint();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            btn.setFont(DEFAULT_FONT);
            btn.setText(user);
            onlineListLayoutHorizontalGroup
                    .addComponent(btn, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE);
            onlineListLayoutVerticalGroup
                    .addGap(2)
                    .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE);
        }
        onlineListLayout.setHorizontalGroup(onlineListLayout.createSequentialGroup().addGap(2).addGroup(onlineListLayoutHorizontalGroup).addGap(2));
        onlineListLayout.setVerticalGroup(onlineListLayout.createSequentialGroup().addGroup(onlineListLayoutVerticalGroup));

        onlineList.revalidate();
        onlineList.repaint();
    }

    private void loadHistoryConversation(String username, String currentRecipient) throws IOException {
        getHistoryConversation(username, currentRecipient);
        SwingUtilities.invokeLater(() -> {
            for (String message : currentConversation) {
                if (message.startsWith(username)) {
                    if (message.contains("title='file:")) {
                        pnlConversation.addClickableBubbleChat(message.substring(username.length()+1));
                    } else {
                        pnlConversation.addBubbleChat(message.substring(username.length()+1));
                    }
                    if (message.startsWith(currentRecipient)) {
                        if (message.contains("title='file:")) {
                            pnlConversation.addClickableBubbleChatReceive(message.substring(currentRecipient.length()+1));
                        } else {
                            pnlConversation.addBubbleChatReceive(message.substring(currentRecipient.length()+1));
                        }
                    }
                } else {
                    if (message.contains("title='file:")) {
                        pnlConversation.addClickableBubbleChatReceive(message.substring(currentRecipient.length()+1));
                    } else {
                        pnlConversation.addBubbleChatReceive(message.substring(currentRecipient.length()+1));
                    }
                }
            }
        });
    }

    private void getHistoryConversation(String username, String currentRecipient) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("GET_CONVERSATION_HISTORY|" + username + "|" + currentRecipient);
    }

    private void pushHistoryConversation(String username, String currentRecipient, String message) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.writeUTF("PUSH_CONVERSATION_HISTORY|" + username + "|" + currentRecipient + "|" + message);
    }

    private void moveFrame(java.awt.event.MouseEvent evt) {
        setLocation(getLocation().x + evt.getX() - this.pX, getLocation().y + evt.getY() - this.pY);
    }

    private void getPosition(java.awt.event.MouseEvent evt) {
        this.pX = evt.getX();
        this.pY = evt.getY();
    }

    private void mouseEnterHandle(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == this.btnClose) this.txtClose.setForeground(Color.red);
        if (evt.getSource() == this.btnMinimize) this.txtMinimize.setForeground(Color.red);
    }

    private void mouseExitHandle(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == this.btnClose) {
            this.txtClose.setForeground(Color.white);
        }
        if (evt.getSource() == this.btnMinimize) {
            this.txtMinimize.setForeground(Color.white);
        }
    }

    private void minimize() {
        this.setExtendedState(JFrame.ICONIFIED);
    }

    private void close() {
        try {
            DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
            dos.writeUTF("LOGOUT");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dispose();
        }
    }

    public static void main(String[] args) {
        getDefaults().put("ScrollBar.minimumThumbSize", new Dimension(29, 29));
//        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    private JPanel btnClose;
    private JPanel btnMinimize;
    private JLabel txtClose;
    private JLabel txtMinimize;
    private int pX;
    private int pY;
}
