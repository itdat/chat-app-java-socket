package com.ntdat.chatapp.ui.fragment;

import com.ntdat.chatapp.ui.customcomponent.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConversationPanel extends JPanel {
    private static ConversationPanel instance;
    // DEFINE VALUES
    private static final Font DEFAULT_FONT = new Font("Roboto", 0, 18);
    private static final Color PANEL_BACKGROUND_COLOR = new Color(88, 102, 146);
    private static final int MAX_CHARACTERS_IN_ROW = 50;

    private DefaultTableModel dataModel = new DefaultTableModel();

    private JPanel chatArea;
    private JScrollPane chatAreaScroll;
    private GroupLayout.Group chatAreaHorizontalGroup;
    private GroupLayout.Group chatAreaVerticalGroup;
    private FlatTextArea edtInputChat;

    private JTable tblClassroom = new JTable(dataModel);
    private FlatButton btnSettings;
    private FlatButton btnSearch;
    private FlatButton btnAddStudent;
    
    private String classIDMain = "";
    private boolean adminPermission = true;

    private ConversationPanel(boolean adminPermission) {
        this.adminPermission = adminPermission;
        initComponents();
    }

    public static ConversationPanel getInstance(boolean adminPermission) {
        if (instance == null) {
            instance = new ConversationPanel(adminPermission);
        }
        return instance;
    }

    public static void releaseInstance() {
        instance = null;
    }

    private void initComponents() {
        setBackground(PANEL_BACKGROUND_COLOR);
        setSize(1280,768);
        setVisible(true);

        btnSettings = new FlatButton();
        btnSettings.setText("Cài đặt");
        btnSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/settings.png")));

        btnSearch = new FlatButton();
        btnSearch.setText("Đăng xuất");
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logout.png")));

        JLabel username = new JLabel("Username: ntdat1999");
        username.setFont(DEFAULT_FONT);
        username.setForeground(Color.WHITE);
        username.setHorizontalAlignment(SwingConstants.CENTER);
        username.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user.png")));
        RoundedPanel usernamePanel = new RoundedPanel(10);
        usernamePanel.setLayout(new BorderLayout());
        usernamePanel.setPreferredSize(new Dimension(100,50));
        usernamePanel.setBackground(Color.decode("#243772"));
        usernamePanel.add(username, BorderLayout.CENTER);

        /////////////////////////////////////////////////////////////////////////
        JLabel chatName = new JLabel();
        chatName.setFont(new Font("Roboto", 1, 24));
        chatName.setText("User 1");
        chatName.setForeground(new Color(68, 68, 68));
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(142, 142, 142));

        RoundedPanel chatNamePanel = new RoundedPanel(20);
        chatNamePanel.setBackground(Color.WHITE);
        chatName.setOpaque(false);
        javax.swing.GroupLayout chatNamePanelLayout = new javax.swing.GroupLayout(chatNamePanel);
        chatNamePanel.setLayout(chatNamePanelLayout);
        chatNamePanelLayout.setHorizontalGroup(
                chatNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatNamePanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chatName, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatNamePanelLayout.createSequentialGroup()
                                .addGap(2)
                                .addComponent(separator)
                                .addGap(2))
        );
        chatNamePanelLayout.setVerticalGroup(
                chatNamePanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chatName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        JLabel btnSendPicture = new JLabel();
        btnSendPicture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/photo.png")));

        JLabel btnSendFile = new JLabel();
        btnSendFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/file.png")));

        JLabel btnSend = new JLabel();
        btnSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/send.png")));

        // Input text panel
        JLabel pickEmoji = new JLabel();
        pickEmoji.setIcon(new javax.swing.ImageIcon(getClass().getResource("/emoji_images/emoji_1.png")));
        edtInputChat = new FlatTextArea(20, new Color(238, 238, 238), new Color(238,238,238));
        edtInputChat.setFont(DEFAULT_FONT);
        edtInputChat.setBorder(new EmptyBorder(10, 10, 10, 10));
        edtInputChat.setLineWrap(true);
        edtInputChat.setColumns(1);
        edtInputChat.setRows(1);
        JScrollPane edtInputChatScroll = new JScrollPane();
        edtInputChatScroll.setViewportView(edtInputChat);
        edtInputChatScroll.setBorder(new EmptyBorder(0,0,0,0));
        edtInputChatScroll.getVerticalScrollBar().setUI(new MyScrollbarUI(new Color(238,238,238), new Color(100,100,100)));
        RoundedPanel inputChat = new RoundedPanel(50);
        inputChat.setBackground(new Color(238,238,238));
        javax.swing.GroupLayout inputChatLayout = new javax.swing.GroupLayout(inputChat);
        inputChat.setLayout(inputChatLayout);
        inputChatLayout.setHorizontalGroup(
                inputChatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(edtInputChatScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pickEmoji)
                        .addContainerGap()
        );
        inputChatLayout.setVerticalGroup(
                inputChatLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(inputChatLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(pickEmoji, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(edtInputChatScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap()
        );



        RoundedPanel conversationPanel = new RoundedPanel(20);
        conversationPanel.setBackground(Color.WHITE);

        chatArea = new JPanel();

        JLabel bubbleContent = new JLabel();
        bubbleContent.setFont(DEFAULT_FONT);
        bubbleContent.setText("<html><div style='width: 400px'>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</div></html>");
        bubbleContent.setForeground(Color.WHITE);
        bubbleContent.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        RoundedPanel bubble = new RoundedPanel(20);
        bubble.add(bubbleContent);
        bubble.setBackground(new Color(77, 89, 159));
        bubble.setBorder(new EmptyBorder(10,10,10,10));
        JPanel alignedBubble = new JPanel();
        alignedBubble.setOpaque(false);
        alignedBubble.setLayout(new BorderLayout());
        alignedBubble.add(bubble, BorderLayout.EAST);

        JLabel bubbleContent2 = new JLabel();
        bubbleContent2.setFont(DEFAULT_FONT);
        bubbleContent2.setText("<html><div style='width: 400px'>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.</div></html>");
        bubbleContent2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        RoundedPanel bubble2 = new RoundedPanel(20);
        bubble2.add(bubbleContent2);
        bubble2.setBorder(new EmptyBorder(10,10,10,10));
        JPanel alignedBubble2 = new JPanel();
        alignedBubble2.setOpaque(false);
        alignedBubble2.setLayout(new BorderLayout());
        alignedBubble2.add(bubble2, BorderLayout.WEST);

        chatArea.setBackground(Color.WHITE);
        javax.swing.GroupLayout chatAreaLayout = new javax.swing.GroupLayout(chatArea);
        chatArea.setLayout(chatAreaLayout);

        chatAreaHorizontalGroup = chatAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(alignedBubble, GroupLayout.PREFERRED_SIZE, 990, GroupLayout.PREFERRED_SIZE)
                .addComponent(alignedBubble2, GroupLayout.PREFERRED_SIZE, 990, GroupLayout.PREFERRED_SIZE);
        chatAreaLayout.setHorizontalGroup(chatAreaHorizontalGroup);

        chatAreaVerticalGroup = chatAreaLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(alignedBubble,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(alignedBubble2,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        chatAreaLayout.setVerticalGroup(chatAreaVerticalGroup);

        chatAreaScroll = new JScrollPane();
        chatAreaScroll.getVerticalScrollBar().setUI(new MyScrollbarUI(new Color(238,238,238), new Color(100,100,100)));
        chatAreaScroll.setViewportView(chatArea);
        chatAreaScroll.getVerticalScrollBar().setUnitIncrement(16);
        chatAreaScroll.setBorder(new EmptyBorder(0,0,0,0));
        chatAreaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout conversationPanelLayout = new javax.swing.GroupLayout(conversationPanel);
        conversationPanel.setLayout(conversationPanelLayout);
        conversationPanelLayout.setHorizontalGroup(
                conversationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(chatNamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(conversationPanelLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(btnSendFile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSendPicture)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSend)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(conversationPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(chatAreaScroll)
                                .addContainerGap())
        );
        conversationPanelLayout.setVerticalGroup(
                conversationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(conversationPanelLayout.createSequentialGroup()
                                .addComponent(chatNamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chatAreaScroll)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(conversationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(btnSendPicture, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSend, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSendFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(inputChat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );


        GroupLayout pnlMainLayout = new GroupLayout(this);
        this.setLayout(pnlMainLayout);
        pnlMainLayout.setHorizontalGroup(
            pnlMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlMainLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pnlMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(conversationPanel)
                        .addGroup(pnlMainLayout.createSequentialGroup()
                            .addComponent(usernamePanel)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSettings)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnSearch)))
                    .addContainerGap())
        );
        pnlMainLayout.setVerticalGroup(
            pnlMainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pnlMainLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(pnlMainLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSettings, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(usernamePanel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(conversationPanel, GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                    .addGap(9, 9, 9)
                    .addContainerGap())
        );


        // EVENT LISTENERS

        tblClassroom.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
//                classIDMain = edtSearch.getText();
            }
        });

        btnSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            }
        });

        btnSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBubbleChat(edtInputChat.getText());
                edtInputChat.setText("");
            }
        });


        edtInputChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode()== KeyEvent.VK_ENTER) {
                    addBubbleChat(edtInputChat.getText());
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            edtInputChat.setText("");
                        }
                    });

                }
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dimension vpSize = chatAreaScroll.getViewport().getExtentSize();
                Dimension logSize = chatArea.getSize();

                int height = logSize.height - vpSize.height;
                chatAreaScroll.getViewport().setViewPosition(new Point(0, height));
            }
        });
    }

    private String breakLongWords(String content) {
        List<String> words = new ArrayList<>(Arrays.asList(content.split(" ")));
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
                brkWord.add(word.substring(startPos));
                words.set(i, brkWord.stream().collect(Collectors.joining("<br/>")));
            }
        }
//        getFontMetrics(new Font("Roboto", 1, 16)).stringWidth()
        return words.stream().collect(Collectors.joining(" "));
    }

    private void addBubbleChat(String content) {
        JLabel bubbleContent = new JLabel();
        bubbleContent.setFont(DEFAULT_FONT);
        bubbleContent.setText("<html><div style='width: 400px'>" + breakLongWords(content) + "</div></html>");
        System.out.println(breakLongWords(content));
        bubbleContent.setForeground(Color.WHITE);
        bubbleContent.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        RoundedPanel bubble = new RoundedPanel(20);
        bubble.add(bubbleContent);
        bubble.setBackground(new Color(77, 89, 159));
        bubble.setBorder(new EmptyBorder(10,10,10,10));
        JPanel alignedBubble = new JPanel();
        alignedBubble.setOpaque(false);
        alignedBubble.setLayout(new BorderLayout());
        alignedBubble.add(bubble, BorderLayout.EAST);

        chatAreaHorizontalGroup.addComponent(alignedBubble, GroupLayout.PREFERRED_SIZE, 990, GroupLayout.PREFERRED_SIZE);
        chatAreaVerticalGroup
                .addComponent(alignedBubble,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(5);

        this.chatArea.add(new JLabel(content));
        this.chatArea.revalidate();
        this.chatAreaScroll.revalidate();
        this.chatArea.repaint();
        this.chatAreaScroll.repaint();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Dimension vpSize = chatAreaScroll.getViewport().getExtentSize();
                Dimension logSize = chatArea.getSize();

                int height = logSize.height - vpSize.height;
                chatAreaScroll.getViewport().setViewPosition(new Point(0, height));
            }
        });
    }
}
