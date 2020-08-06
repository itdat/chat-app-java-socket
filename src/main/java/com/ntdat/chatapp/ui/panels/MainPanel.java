package com.ntdat.chatapp.ui.panels;

import com.ntdat.chatapp.ui.Login;
import com.ntdat.chatapp.ui.customcomponent.FlatButton;
import com.ntdat.chatapp.ui.customcomponent.FlatTextInput;
import com.ntdat.chatapp.ui.customcomponent.MyScrollbarUI;
import com.ntdat.chatapp.ui.fragment.ConversationPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import static com.ntdat.chatapp.Main.*;

public class MainPanel extends JPanel {
    private Socket socket;
    private String username;

    private String currentRecipient = null;

    private JPanel onlineList = new JPanel();
    private GridBagConstraints gbc;
    private JPanel pnlMain;
    private ConversationPanel pnlConversation;

    private String currentDownloadFileName;
    private List<String> currentDownloadFile;

    private List<String> activeUsers;
    private int countHighLight = 0;

    private List<String> currentConversation;


    public MainPanel(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
        initComponents();
        try {
            updateAppUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
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
        onlineList.setLayout(new BorderLayout());

        // Init constraint for user list
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0;
        gbc.weighty = 1.0;
        gbc.gridx = 0;
        gbc.insets = new Insets(2,2,2,2);
        gbc.anchor = GridBagConstraints.NORTH;

        // Add to scrollable pane
        JScrollPane onlineListScroll = new JScrollPane();
        onlineListScroll.setPreferredSize(new Dimension(223, 1000));
        onlineListScroll.setOpaque(true);
        onlineListScroll.setBackground(Color.RED);
        onlineListScroll.setBorder(new EmptyBorder(0,0,0,0));
        onlineListScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        onlineListScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        onlineListScroll.getVerticalScrollBar().setUnitIncrement(24);
        onlineListScroll.getVerticalScrollBar().setUI(new MyScrollbarUI());
        onlineListScroll.setViewportView(onlineList);

        // Add elements to SidePanel
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.decode("#1E2252"));
        sidePanel.setMinimumSize(new Dimension(500,736));
        GroupLayout sidePanelLayout = new GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        GroupLayout.Group horizontalGroup = sidePanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(txtLogo)
                .addGap(2,2,2)
                .addComponent(spr1)
                .addGroup(sidePanelLayout.createSequentialGroup().addGap(2).addComponent(searchUser).addGap(2))
                .addComponent(onlineListScroll);
        GroupLayout.Group verticalGroup = sidePanelLayout.createSequentialGroup()
                .addGap(48)
                .addComponent(txtLogo)
                .addGap(48)
                .addComponent(spr1, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
                .addGap(2)
                .addComponent(searchUser, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(2)
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

        this.setBackground(PANEL_BACKGROUND_COLOR);
        GroupLayout backgroundLayout = new GroupLayout(this);
        this.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createSequentialGroup()
                        .addComponent(sidePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(pnlMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createSequentialGroup()
                        .addGroup(
                                backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(sidePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnlMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
        );
    }

    private void updateOnlineList(List<String> users) {
        // Clear current online list
        onlineList.removeAll();
        // Create new online list
        JPanel usersPanel = new JPanel();
        usersPanel.setLayout(new GridBagLayout());
        usersPanel.setOpaque(false);
        for (int i = 0; i < users.size(); i++) {
            // Create new button
            FlatButton btn = new FlatButton();
            btn.setFont(DEFAULT_FONT);
            btn.setText(users.get(i));
            btn.setPreferredSize(new Dimension(200, 50));
            // Add mouseClickListener
            int finalIndex = i;
            btn.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    currentRecipient = users.get(finalIndex);
                    try {
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF("GET_CONVERSATION_HISTORY|" + username + "|" + currentRecipient);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            gbc.gridy = i;
            usersPanel.add(btn, gbc);
        }
        onlineList.add(usersPanel, BorderLayout.PAGE_START);
        onlineList.revalidate();
        onlineList.repaint();
    }

    private void updateAppUI() throws IOException {
        DataInputStream dis = new DataInputStream(this.socket.getInputStream());
        Thread readMessage = new Thread(() -> {
            while (!Login.exit) {
                try {
                    String msg = dis.readUTF();
                    String[] tokens = msg.split("\\|");
                    switch (tokens[0]) {
                        case "UPDATE_ONLINE_LIST":
                            // "UPDATE_ONLINE_LIST|user1,user2,user3"
                            String[] users = tokens[1].split(",");
                            activeUsers = Arrays.asList(users);
                            updateOnlineList(activeUsers);
                            break;
                        case "MESSAGE":
                            // "MESSAGE|message|sender"
                            String message = tokens[1];
                            String sender = tokens[2];
                            if (sender.equals(currentRecipient)) {
                                pnlConversation.addBubbleChatReceive(message);
                                pnlConversation.revalidate();
                                pnlConversation.repaint();
                            }
                            break;
//                        case "MESSAGE_FILE":
//                            String messageFile = tokens[1];
//                            String senderFile = tokens[2];
//                            pushHistoryConversation(username, senderFile, senderFile + ":" + messageFile);
//                            if (senderFile.equals(currentRecipient)) {
//                                pnlConversation.addClickableBubbleChatReceive(messageFile);
//                                pnlConversation.revalidate();
//                                pnlConversation.repaint();
//                            }
//                            break;
//                        case "DELIVER":
//                            if (tokens[1].equals("INFO")) {
//                                String fileName = tokens[2];
//                                this.currentDownloadFileName = fileName;
//                                this.currentDownloadFile = new ArrayList<>();
//                            } else if (tokens[1].equals("REMAIN")) {
//                                String data = msg.substring("DELIVER|REMAIN|".length());
//                                currentDownloadFile.add(data);
//                            } else {
//                                String data = msg.substring("DELIVER|END|".length());
//                                currentDownloadFile.add(data);
//                                String receivedString = String.join("", currentDownloadFile);
//                                byte[] decodeFileData = null;
//                                try {
//                                    decodeFileData = Base64.getDecoder().decode(receivedString.getBytes());
//                                } catch (Exception ec) {
//                                    ec.printStackTrace();
//                                } finally {
//                                    FileOutputStream fos = null;
//                                    try {
//                                        System.out.println(currentDownloadFileName);
//                                        fos = new FileOutputStream(currentDownloadFileName);
//                                    } catch (FileNotFoundException fileNotFoundException) {
//                                        fileNotFoundException.printStackTrace();
//                                    }
//                                    assert decodeFileData != null;
//                                    try {
//                                        fos.write(decodeFileData);
//                                        fos.close();
//                                    } catch (IOException ioException) {
//                                        ioException.printStackTrace();
//                                    }
//                                    break;
//                                }
//                            }
//                            break;
                        case "CONVERSATION_HISTORY":
                            String conversation = "";
                            if (tokens.length == 4) {
                                conversation = tokens[3];
                            }
                            currentConversation = Arrays.asList(conversation.split("#"));
                            pnlMain.removeAll();
                            pnlConversation = new ConversationPanel(socket, username, currentRecipient);
                            pnlMain.add(pnlConversation);

                            if (tokens.length == 4) {
                                loadHistoryConversation(username, currentRecipient);
                            }

                            pnlMain.revalidate();
                            pnlMain.repaint();
                            break;
                        default:
                            break;
                    }
                } catch (IOException ignored) {
                }
            }
        });
        readMessage.start();
    }

    private void loadHistoryConversation(String username, String currentRecipient) {
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
}
