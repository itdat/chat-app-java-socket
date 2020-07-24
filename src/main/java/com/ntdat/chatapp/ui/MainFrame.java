/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui;

import com.ntdat.chatapp.ui.customcomponent.*;
import com.ntdat.chatapp.ui.fragment.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static javax.swing.UIManager.getDefaults;

public class MainFrame extends RoundedJFrame {
    // DEFINE VALUES
    private static final Font DEFAULT_FONT = new Font("Roboto", Font.PLAIN, 18);
    private static final Color PANEL_BACKGROUND_COLOR = Color.decode("#586692");

    public MainFrame() {initComponents();}

    public void initComponents() {
        // =============================== TITLE BAR ===============================
        // Application name
        JLabel txtAppName = new JLabel();
        txtAppName.setFont(DEFAULT_FONT);
        txtAppName.setForeground(Color.WHITE);
        txtAppName.setText("ChatApp ITD v1.0");

        // Create Title bar button
        RoundedPanel btnTitleBar = new RoundedPanel(10);
        btnTitleBar.setPreferredSize(new Dimension(30,30));
        btnTitleBar.setBackground(Color.decode("#4D599F"));
        btnTitleBar.setLayout(new BorderLayout());

        // Create UI minimize button
        txtMinimize = new JLabel();
        txtMinimize.setFont(new Font("Roboto", Font.BOLD, 28));
        txtMinimize.setForeground(Color.WHITE);
        txtMinimize.setHorizontalAlignment(SwingConstants.CENTER);
        txtMinimize.setText("-");
        txtMinimize.setAlignmentY(0.0F);
        btnMinimize = btnTitleBar;
        btnMinimize.add(txtMinimize, BorderLayout.CENTER);

        // Create UI close button
        txtClose = new JLabel();
        txtClose.setFont(new Font("Roboto", Font.BOLD, 28));
        txtClose.setForeground(Color.WHITE);
        txtClose.setHorizontalAlignment(SwingConstants.CENTER);
        txtClose.setText("×");
        txtClose.setAlignmentY(0.0F);
        btnClose = btnTitleBar;
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
        JPanel onlineList = new JPanel();
        onlineList.setBackground(Color.decode("#1E2252"));
        GroupLayout onlineListLayout = new GroupLayout(onlineList);
        onlineList.setLayout(onlineListLayout);
        GroupLayout.Group onlineListLayoutHorizontalGroup = onlineListLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
        GroupLayout.Group onlineListLayoutVerticalGroup = onlineListLayout.createSequentialGroup();
        // Dynamically add online user
        int onlineUsers = 100;
        for (int i = 0; i < onlineUsers; i++) {
            FlatButton btn = new FlatButton();
            btn.setFont(DEFAULT_FONT);
            btn.setText("User " + i);
            onlineListLayoutHorizontalGroup
                    .addComponent(btn, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE);
            onlineListLayoutVerticalGroup
                    .addGap(2)
                    .addComponent(btn, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE);
        }
        onlineListLayout.setHorizontalGroup(onlineListLayout.createSequentialGroup().addGap(2).addGroup(onlineListLayoutHorizontalGroup).addGap(2));
        onlineListLayout.setVerticalGroup(onlineListLayoutVerticalGroup);
        // Add to scrollable pane
        JScrollPane onlineListScroll = new JScrollPane();
        onlineList.setBackground(Color.decode("#1E2252"));
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
        JPanel pnlMain = ConversationPanel.getInstance();
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
                                        .addGroup(backgroundLayout.createSequentialGroup()
                                                .addGap(0, 0, 0)
                                                .addComponent(pnlMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(0, 0, 0))))
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
        dispose();
    }

    public static void main(String[] args) {
        getDefaults().put("ScrollBar.minimumThumbSize", new Dimension(29, 29));
        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    private JPanel btnClose;
    private JPanel btnMinimize;
    private JLabel txtClose;
    private JLabel txtMinimize;
    private int pX;
    private int pY;
}
