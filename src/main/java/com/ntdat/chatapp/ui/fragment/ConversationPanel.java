package com.ntdat.chatapp.ui.fragment;

import com.ntdat.chatapp.Main;
import com.ntdat.chatapp.ui.Login;
import com.ntdat.chatapp.ui.customcomponent.*;
import com.ntdat.chatapp.utilities.GFG;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

import static com.ntdat.chatapp.utilities.GFG.getSHA;

public class ConversationPanel extends JPanel {
    // DEFINE VALUES
    private static final Font DEFAULT_FONT = new Font("Roboto", Font.PLAIN, 18);
    private static final Color PANEL_BACKGROUND_COLOR = Color.decode("#586692");

    private JPanel pnlConversationContent;
    private JScrollPane splConversationContent;
    private GroupLayout.Group pnlConversationContentHorizontalGroup;
    private GroupLayout.Group pnlConversationContentVerticalGroup;
    private FlatTextArea edtInputChat;
    private Socket socket;
    private String username;
    private String recipient;
    private File chosenFile = null;

    private String emoji1;
    private String emoji2;
    private String emoji3;
    private String emoji4;
    private String emoji5;

    public ConversationPanel(Socket socket, String username, String recipient) {
        this.socket = socket;
        this.username = username;
        this.recipient = recipient;
        initComponents();
    }

    private void initComponents() {
        // ============================== HEADER BAR ===============================
        // Accessed Username
        JLabel txtUsername = new JLabel();
        txtUsername.setFont(DEFAULT_FONT);
        txtUsername.setForeground(Color.WHITE);
        txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
        txtUsername.setText("Username: " + username);
        txtUsername.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user.png")));
        RoundedPanel pnlUsername = new RoundedPanel(10);
        pnlUsername.setLayout(new BorderLayout());
        pnlUsername.setPreferredSize(new Dimension(100,50));
        pnlUsername.setBackground(Color.decode("#243772"));
        pnlUsername.add(txtUsername, BorderLayout.CENTER);
        // Button settings
        FlatButton btnSettings = new FlatButton();
        btnSettings.setText("Cài đặt");
        btnSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/settings.png")));
        // Button logout
        FlatButton btnLogout = new FlatButton();
        btnLogout.setText("Đăng xuất");
        btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logout.png")));
        // =========================================================================

        // ============================ CONVERSATION AREA ==========================
        // Conversation name
        JLabel txtConversationName = new JLabel();
        txtConversationName.setFont(new Font("Roboto", Font.BOLD, 24));
        txtConversationName.setText(recipient);
        txtConversationName.setForeground(new Color(68, 68, 68));
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(142, 142, 142));
        RoundedPanel pnlConversationName = new RoundedPanel(20);
        pnlConversationName.setBackground(Color.WHITE);
        txtConversationName.setOpaque(false);
        GroupLayout pnlConversationNameLayout = new GroupLayout(pnlConversationName);
        pnlConversationName.setLayout(pnlConversationNameLayout);
        pnlConversationNameLayout.setHorizontalGroup(
                pnlConversationNameLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, pnlConversationNameLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtConversationName, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, pnlConversationNameLayout.createSequentialGroup()
                                .addGap(2)
                                .addComponent(separator)
                                .addGap(2))
        );
        pnlConversationNameLayout.setVerticalGroup(
                pnlConversationNameLayout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtConversationName)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        // Chat features
        // Pick picture
        JLabel icoPickPicture = new JLabel();
        icoPickPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photo.png")));
        // Pick file
        JLabel icoPickFile = new JLabel();
        icoPickFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/file.png")));
        // Send chat
        JLabel icoSend = new JLabel();
        icoSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/send.png")));
        // Input chat panel
        JLabel icoPickEmoji = new JLabel();
        icoPickEmoji.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emoji_images/emoji_1.png")));
        edtInputChat = new FlatTextArea(20, Color.decode("#EEEEEE"), Color.decode("#EEEEEE"));
        edtInputChat.setFont(DEFAULT_FONT);
        edtInputChat.setBorder(new EmptyBorder(10, 10, 10, 10));
        edtInputChat.setLineWrap(true);
        edtInputChat.setColumns(1);
        edtInputChat.setRows(1);
        JScrollPane splInputChat = new JScrollPane();
        splInputChat.setViewportView(edtInputChat);
        splInputChat.setBorder(new EmptyBorder(0,0,0,0));
        splInputChat.getVerticalScrollBar().setUI(new MyScrollbarUI(new Color(238,238,238), new Color(100,100,100)));



        RoundedPanel pnlBoundInputChat = new RoundedPanel(50);
        pnlBoundInputChat.setBackground(Color.decode("#EEEEEE"));
        GroupLayout inputChatLayout = new GroupLayout(pnlBoundInputChat);
        pnlBoundInputChat.setLayout(inputChatLayout);
        inputChatLayout.setHorizontalGroup(
                inputChatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(splInputChat, GroupLayout.PREFERRED_SIZE, 738, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(icoPickEmoji)
                        .addContainerGap()
        );
        inputChatLayout.setVerticalGroup(
                inputChatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(inputChatLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(icoPickEmoji, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(splInputChat, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap()
        );

        // Add to content panel
        pnlConversationContent = new JPanel();
        pnlConversationContent.setBackground(Color.WHITE);
        GroupLayout pnlConversationContentLayout = new javax.swing.GroupLayout(pnlConversationContent);
        pnlConversationContent.setLayout(pnlConversationContentLayout);
        pnlConversationContentHorizontalGroup = pnlConversationContentLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        pnlConversationContentLayout.setHorizontalGroup(pnlConversationContentHorizontalGroup);
        pnlConversationContentVerticalGroup = pnlConversationContentLayout.createSequentialGroup();
        pnlConversationContentLayout.setVerticalGroup(pnlConversationContentVerticalGroup);
        // Add to scrollable content panel
        splConversationContent = new JScrollPane();
        splConversationContent.getVerticalScrollBar().setUI(new MyScrollbarUI(new Color(238,238,238), new Color(100,100,100)));
        splConversationContent.setViewportView(pnlConversationContent);
        splConversationContent.getVerticalScrollBar().setUnitIncrement(16);
        splConversationContent.setBorder(new EmptyBorder(0,0,0,0));
        splConversationContent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        splConversationContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);



        JLabel emoji1 = new JLabel();
        emoji1.setPreferredSize(new Dimension(40,40));
        emoji1.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/emoji_images/emoji_1.png")).getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT)));
        emoji1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                addBubbleChat("<img src='./data/icon/emoji_1.png' width='30' height='30'/>");
                try {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    String msg = edtInputChat.getText();
                    try {
                        dos.writeUTF("SEND|" + recipient +"|" + msg + "|" + username);
                    } catch (IOException evt) {
                        evt.printStackTrace();
                    }
                } catch (IOException evt) {
                    evt.printStackTrace();
                }
            }
        });

        JLabel emoji2 = new JLabel();
        emoji2.setPreferredSize(new Dimension(40,40));
        emoji2.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/emoji_images/emoji_2.png")).getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT)));
        emoji2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                addBubbleChat("<img src='./data/icon/emoji_2.png' width='30' height='30'/>");
                try {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    String msg = edtInputChat.getText();
                    try {
                        dos.writeUTF("SEND|" + recipient +"|" + msg + "|" + username);
                    } catch (IOException evt) {
                        evt.printStackTrace();
                    }
                } catch (IOException evt) {
                    evt.printStackTrace();
                }            }
        });

        JLabel emoji3 = new JLabel();
        emoji3.setPreferredSize(new Dimension(40,40));
        emoji3.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/emoji_images/emoji_3.png")).getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT)));
        emoji3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                addBubbleChat("<img src='./data/icon/emoji_3.png' width='30' height='30'/>");
                try {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    String msg = edtInputChat.getText();
                    try {
                        dos.writeUTF("SEND|" + recipient +"|" + msg + "|" + username);
                    } catch (IOException evt) {
                        evt.printStackTrace();
                    }
                } catch (IOException evt) {
                    evt.printStackTrace();
                }            }
        });

        JLabel emoji4 = new JLabel();
        emoji4.setPreferredSize(new Dimension(40,40));
        emoji4.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/emoji_images/emoji_4.png")).getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT)));
        emoji4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                addBubbleChat("<img src='./data/icon/emoji_4.png' width='30' height='30'/>");
                try {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    String msg = edtInputChat.getText();
                    try {
                        dos.writeUTF("SEND|" + recipient +"|" + msg + "|" + username);
                    } catch (IOException evt) {
                        evt.printStackTrace();
                    }
                } catch (IOException evt) {
                    evt.printStackTrace();
                }            }
        });

        JLabel emoji5 = new JLabel();
        emoji5.setPreferredSize(new Dimension(40,40));
        emoji5.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/emoji_images/emoji_5.png")).getImage().getScaledInstance(40,40, Image.SCALE_DEFAULT)));
        emoji5.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                addBubbleChat("<img src='./data/icon/emoji_5.png' width='30' height='30'/>");
                try {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    String msg = edtInputChat.getText();
                    try {
                        dos.writeUTF("SEND|" + recipient +"|" + msg + "|" + username);
                    } catch (IOException evt) {
                        evt.printStackTrace();
                    }
                } catch (IOException evt) {
                    evt.printStackTrace();
                }            }
        });

        JPanel emojiPanel = new JPanel();
        emojiPanel.setLayout(new BoxLayout(emojiPanel, BoxLayout.X_AXIS));
        emojiPanel.add(emoji1);
        emojiPanel.add(Box.createRigidArea(new Dimension(5,5)));
        emojiPanel.add(emoji2);
        emojiPanel.add(Box.createRigidArea(new Dimension(5,5)));
        emojiPanel.add(emoji3);
        emojiPanel.add(Box.createRigidArea(new Dimension(5,5)));
        emojiPanel.add(emoji4);
        emojiPanel.add(Box.createRigidArea(new Dimension(5,5)));
        emojiPanel.add(emoji5);

        emojiPanel.setVisible(false);


        // Add to Conversation panel
        RoundedPanel pnlConversation = new RoundedPanel(20);
        pnlConversation.setBackground(Color.WHITE);
        GroupLayout conversationPanelLayout = new GroupLayout(pnlConversation);
        pnlConversation.setLayout(conversationPanelLayout);
        conversationPanelLayout.setHorizontalGroup(
                conversationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(pnlConversationName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(conversationPanelLayout.createSequentialGroup()
                                .addGap(25)
                                .addComponent(icoPickFile)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(icoPickPicture)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(conversationPanelLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(emojiPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                                        .addComponent(pnlBoundInputChat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                )
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(icoSend)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(conversationPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(splConversationContent)
                                .addContainerGap())
        );
        conversationPanelLayout.setVerticalGroup(
                conversationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(conversationPanelLayout.createSequentialGroup()
                                .addComponent(pnlConversationName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(splConversationContent)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(emojiPanel,40,40,40)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(conversationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(icoPickPicture, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(icoSend, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(icoPickFile, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pnlBoundInputChat, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        // =========================================================================

        // Main panel
        setBackground(PANEL_BACKGROUND_COLOR);
        setSize(1280,768);
        setVisible(true);
        GroupLayout pnlMainLayout = new GroupLayout(this);
        this.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlMainLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pnlMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(pnlConversation)
                        .addGroup(pnlMainLayout.createSequentialGroup()
                            .addComponent(pnlUsername)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
//                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSettings)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnLogout)))
                    .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlMainLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pnlMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSettings, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(pnlUsername, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(pnlConversation, GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                    .addGap(12)
                    .addContainerGap())
        );

        // =========================================================================
        // =========================================================================
        // =========================================================================

        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            }
        });

        icoSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBubbleChat(edtInputChat.getText());
                edtInputChat.setText("");
            }
        });

        icoPickFile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int res = fileChooser.showDialog(null, "Chọn file");
                if (res == JFileChooser.APPROVE_OPTION) {
                    chosenFile = fileChooser.getSelectedFile();
                    edtInputChat.setForeground(Main.PANEL_BACKGROUND_COLOR);
                    edtInputChat.setText(fileChooser.getSelectedFile().getName());
                    edtInputChat.requestFocusInWindow();
                }
            }
        });

        icoPickEmoji.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                emojiPanel.setVisible(!emojiPanel.isVisible());
            }
        });

        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("LOGOUT");
                } catch (IOException evt) {
                    evt.printStackTrace();
                } finally {
                    logOut();
                }
            }
        });

        edtInputChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode()== KeyEvent.VK_ENTER) {
                    if (chosenFile != null) {
                        Date date = new Date();
                        String inputHash = new Timestamp(date.getTime()).toString() + username + recipient;
                        String hash = "";
                        try {
                            hash = GFG.toHexString(getSHA(inputHash));
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        String finalHash = hash;
                        Thread uploadFile = new Thread(()-> {
                            DataOutputStream dos = null;
                            try {
                                dos = new DataOutputStream(socket.getOutputStream());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            byte[] fileDataByte = new byte[0];
                            try {
                                fileDataByte = Files.readAllBytes(chosenFile.toPath());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            byte[] encodeDataByte = Base64.getEncoder().withoutPadding().encode(fileDataByte);
                            String encodeDataString = new String(encodeDataByte);
                            try {
                                dos.writeUTF("SEND_FILE|INFO|" + chosenFile.getName() + "|" + finalHash);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            while (encodeDataString.length() > 4096) {
                                String subString = encodeDataString.substring(0, 4096);
                                try {
                                    dos.writeUTF("SEND_FILE|REMAIN|" + subString);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                encodeDataString = encodeDataString.substring(4096);
                            }
                            try {
                                dos.writeUTF("SEND_FILE|END|" + encodeDataString);
                                dos.writeUTF("SEND_FILE|SIGN|" + chosenFile.getName() + "|" + recipient + "|" + username);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            chosenFile = null;
                        });

                        addClickableBubbleChat("<b style='color:yellow' title='file:" + hash + "'>" + chosenFile.getName() + "</b>");
                        uploadFile.start();
                        SwingUtilities.invokeLater(() -> {
                            edtInputChat.setText("");
                            edtInputChat.setForeground(Color.decode("#525252"));
                        });
                    } else {
                        addBubbleChat(edtInputChat.getText());
                        try {
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            String msg = edtInputChat.getText();
                            try {
                                dos.writeUTF("SEND|" + recipient +"|" + msg + "|" + username);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SwingUtilities.invokeLater(() -> {
                            edtInputChat.setText("");
                        });
                    }
                } else {
                    chosenFile = null;
                    SwingUtilities.invokeLater(() -> {
                        edtInputChat.setForeground(Color.decode("#525252"));
                    });
                }
            }
        });
    }

    private void logOut() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Login login = new Login();
        login.setVisible(true);
        topFrame.dispose();
    }

    private void initEmoji() {
        URL res = getClass().getClassLoader().getResource("emoji_1.png");
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        emoji1 = "<img src='" + file.getAbsolutePath() + "' width='30' height='30'/>";
        res = getClass().getClassLoader().getResource("emoji_2.png");
        file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        emoji2 = "<img src='" + file.getAbsolutePath() + "' width='30' height='30'/>";
        res = getClass().getClassLoader().getResource("emoji_3.png");
        file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        emoji3 = "<img src='" + file.getAbsolutePath() + "' width='30' height='30'/>";
        res = getClass().getClassLoader().getResource("emoji_4.png");
        file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        emoji4 = "<img src='" + file.getAbsolutePath() + "' width='30' height='30'/>";
        res = getClass().getClassLoader().getResource("emoji_5.png");
        file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        emoji5 = "<img src='" + file.getAbsolutePath() + "' width='30' height='30'/>";
    }

    private String breakLongWords(String content) {
        String handleContent = content;
        if (content.startsWith("<b style='color:yellow' title='file:")) {
            Document doc = Jsoup.parse(content);
            Element b = doc.select("b").first();
            handleContent = b.text();
        }
        List<String> words = new ArrayList<>(Arrays.asList(handleContent.split(" ")));
        FontMetrics fontMetrics = getFontMetrics(DEFAULT_FONT);
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (fontMetrics.stringWidth(word) > 500) {
                List<String> brkWord = new ArrayList<>();
                int startPos = 0;
                int j = 0;
                while (j < word.length()) {
                    if (fontMetrics.stringWidth(word.substring(startPos,j)) > 500) {
                        brkWord.add(word.substring(startPos,j));
                        startPos = j;
                    }
                    j++;
                }
                brkWord.add(word.substring(startPos));  // remainder
                words.set(i, String.join("<br/>", brkWord));
            }
        }

        if (content.startsWith("<b style='color:yellow' title='file:")) {
            Document doc = Jsoup.parse(content);
            Element b = doc.select("b").first();
            String oldContent = b.text();
            content.replace(oldContent, String.join(" ", words));
        } else {
            content = String.join(" ", words);
        }
        return content;
    }

    public void addBubbleChat(String content) {
        JLabel txtBubbleContent = new JLabel();
        txtBubbleContent.setFont(DEFAULT_FONT);
        txtBubbleContent.setText("<html><div style='width: 400px'>" + breakLongWords(content) + "</div></html>");
        txtBubbleContent.setForeground(Color.WHITE);
        txtBubbleContent.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        RoundedPanel pnlBubble = new RoundedPanel(20);
        pnlBubble.add(txtBubbleContent);
        pnlBubble.setBackground(Color.decode("#4D599F"));
        pnlBubble.setBorder(new EmptyBorder(10,10,10,10));
        JPanel pnlAlignedBubble = new JPanel();
        pnlAlignedBubble.setOpaque(false);
        pnlAlignedBubble.setLayout(new BorderLayout());
        pnlAlignedBubble.add(pnlBubble, BorderLayout.EAST);

        pnlConversationContentHorizontalGroup.addComponent(pnlAlignedBubble, GroupLayout.PREFERRED_SIZE, 990, GroupLayout.PREFERRED_SIZE);
        pnlConversationContentVerticalGroup
                .addComponent(pnlAlignedBubble,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(5);

        this.pnlConversationContent.revalidate();
        this.splConversationContent.revalidate();
        this.pnlConversationContent.repaint();
        this.splConversationContent.repaint();

        SwingUtilities.invokeLater(() -> {
            Dimension vpSize = splConversationContent.getViewport().getExtentSize();
            Dimension logSize = pnlConversationContent.getSize();
            int height = logSize.height - vpSize.height;
            splConversationContent.getViewport().setViewPosition(new Point(0, height));
        });
    }

    public void addClickableBubbleChat(String content) {
        Document doc = Jsoup.parse(content);
        Element b = doc.select("b").first();
        String fileName = b.text();
        String hashName = b.attr("title").substring(5);

        JLabel txtBubbleContent = new JLabel();
        txtBubbleContent.setFont(DEFAULT_FONT);
        txtBubbleContent.setText("<html><div style='width: 400px'>" + breakLongWords(content) + "</div></html>");
        txtBubbleContent.setForeground(Color.YELLOW);
        txtBubbleContent.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        RoundedPanel pnlBubble = new RoundedPanel(20);
        pnlBubble.add(txtBubbleContent);
        pnlBubble.setBackground(Color.decode("#4D599F"));
        pnlBubble.setBorder(new EmptyBorder(10,10,10,10));
        JPanel pnlAlignedBubble = new JPanel();
        pnlAlignedBubble.setOpaque(false);
        pnlAlignedBubble.setLayout(new BorderLayout());
        pnlAlignedBubble.add(pnlBubble, BorderLayout.EAST);

        pnlConversationContentHorizontalGroup.addComponent(pnlAlignedBubble, GroupLayout.PREFERRED_SIZE, 990, GroupLayout.PREFERRED_SIZE);
        pnlConversationContentVerticalGroup
                .addComponent(pnlAlignedBubble,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(5);

        this.pnlConversationContent.revalidate();
        this.splConversationContent.revalidate();
        this.pnlConversationContent.repaint();
        this.splConversationContent.repaint();

        pnlBubble.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new java.io.File("."));
                    chooser.setDialogTitle("Chọn thư mục lưu file");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        System.out.println("getCurrentDirectory(): "
                                +  chooser.getCurrentDirectory());
                        System.out.println("getSelectedFile() : "
                                +  chooser.getSelectedFile());
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF("GET_FILE|" + chooser.getSelectedFile().getAbsolutePath() + "/" + fileName + "|" + hashName);
                        ////
                    }
                    else {
                        System.out.println("No Selection ");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        SwingUtilities.invokeLater(() -> {
            Dimension vpSize = splConversationContent.getViewport().getExtentSize();
            Dimension logSize = pnlConversationContent.getSize();
            int height = logSize.height - vpSize.height;
            splConversationContent.getViewport().setViewPosition(new Point(0, height));
        });
    }

    public void addBubbleChatReceive(String content) {
        JLabel txtBubbleContent = new JLabel();
        txtBubbleContent.setFont(DEFAULT_FONT);
        txtBubbleContent.setText("<html><div style='width: 400px'>" + breakLongWords(content) + "</div></html>");
        txtBubbleContent.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        RoundedPanel pnlBubble = new RoundedPanel(20);
        pnlBubble.add(txtBubbleContent);
        pnlBubble.setBorder(new EmptyBorder(10,10,10,10));
        JPanel pnlAlignedBubble = new JPanel();
        pnlAlignedBubble.setOpaque(false);
        pnlAlignedBubble.setLayout(new BorderLayout());
        pnlAlignedBubble.add(pnlBubble, BorderLayout.WEST);

        pnlConversationContentHorizontalGroup.addComponent(pnlAlignedBubble, GroupLayout.PREFERRED_SIZE, 990, GroupLayout.PREFERRED_SIZE);
        pnlConversationContentVerticalGroup
                .addComponent(pnlAlignedBubble,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(5);

        this.pnlConversationContent.revalidate();
        this.splConversationContent.revalidate();
        this.pnlConversationContent.repaint();
        this.splConversationContent.repaint();

        SwingUtilities.invokeLater(() -> {
            Dimension vpSize = splConversationContent.getViewport().getExtentSize();
            Dimension logSize = pnlConversationContent.getSize();
            int height = logSize.height - vpSize.height;
            splConversationContent.getViewport().setViewPosition(new Point(0, height));
        });
    }

    public void addClickableBubbleChatReceive(String content) {
        content = content.replace("style='color:yellow'", "style='color:#586692'");
        Document doc = Jsoup.parse(content);
        Element b = doc.select("b").first();
        String fileName = b.text();
        String hashName = b.attr("title").substring(5);

        JLabel txtBubbleContent = new JLabel();
        txtBubbleContent.setFont(DEFAULT_FONT);
        txtBubbleContent.setText("<html><div style='width: 400px'>" + breakLongWords(content) + "</div></html>");
        txtBubbleContent.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        RoundedPanel pnlBubble = new RoundedPanel(20);
        pnlBubble.add(txtBubbleContent);
        pnlBubble.setBorder(new EmptyBorder(10,10,10,10));
        JPanel pnlAlignedBubble = new JPanel();
        pnlAlignedBubble.setOpaque(false);
        pnlAlignedBubble.setLayout(new BorderLayout());
        pnlAlignedBubble.add(pnlBubble, BorderLayout.WEST);

        pnlConversationContentHorizontalGroup.addComponent(pnlAlignedBubble, GroupLayout.PREFERRED_SIZE, 990, GroupLayout.PREFERRED_SIZE);
        pnlConversationContentVerticalGroup
                .addComponent(pnlAlignedBubble,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(5);

        this.pnlConversationContent.revalidate();
        this.splConversationContent.revalidate();
        this.pnlConversationContent.repaint();
        this.splConversationContent.repaint();

        pnlBubble.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new java.io.File("."));
                    chooser.setDialogTitle("Chọn thư mục lưu file");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        System.out.println("getCurrentDirectory(): "
                                +  chooser.getCurrentDirectory());
                        System.out.println("getSelectedFile() : "
                                +  chooser.getSelectedFile());
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF("GET_FILE|" + chooser.getSelectedFile().getAbsolutePath() + "/" + fileName + "|" + hashName);
                        ////
                    }
                    else {
                        System.out.println("No Selection ");
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        SwingUtilities.invokeLater(() -> {
            Dimension vpSize = splConversationContent.getViewport().getExtentSize();
            Dimension logSize = pnlConversationContent.getSize();
            int height = logSize.height - vpSize.height;
            splConversationContent.getViewport().setViewPosition(new Point(0, height));
        });
    }
}
