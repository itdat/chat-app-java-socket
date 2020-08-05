package com.ntdat.chatapp.ui.panels;

import com.ntdat.chatapp.ui.customcomponent.FlatButton;
import com.ntdat.chatapp.ui.customcomponent.FlatPasswordInput;
import com.ntdat.chatapp.ui.customcomponent.FlatTextInput;
import com.ntdat.chatapp.ui.customcomponent.RoundedPanel;
import com.ntdat.chatapp.utilities.Notification;
import com.ntdat.chatapp.utilities.SetTimeOut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.ntdat.chatapp.AppConfig.*;
import static com.ntdat.chatapp.Main.DEFAULT_DARK_COLOR;
import static com.ntdat.chatapp.Main.DEFAULT_FONT;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;

public class LoginPanel extends JPanel {
    private JTextField edtUsername;
    private JTextField edtPassword;
    private JLabel txtNotification;
    private Socket socket;
    private GroupLayout backgroundLayout;

    public LoginPanel() {
        initComponents();
    };

    private void initComponents() {
        // =============================== LOGIN FORM ==============================
        JLabel icoSettings = new JLabel();
        icoSettings.setIcon(new ImageIcon(getClass().getResource("/settings_dark.png")));

        JLabel txtLogin = new JLabel();
        txtLogin.setFont(new Font("Roboto", Font.BOLD, 36));
        txtLogin.setForeground(DEFAULT_DARK_COLOR);
        txtLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLogin.setText("Đăng nhập");

        JLabel txtUsername = new JLabel();
        txtUsername.setFont(DEFAULT_FONT);
        txtUsername.setForeground(DEFAULT_DARK_COLOR);
        txtUsername.setText("Tài khoản");

        edtUsername = new FlatTextInput();
        edtUsername.setBackground(new Color(240, 240, 240));
        edtUsername.setFont(DEFAULT_FONT);
        edtUsername.setForeground(DEFAULT_DARK_COLOR);

        JLabel txtPassword = new JLabel();
        txtPassword.setFont(DEFAULT_FONT);
        txtPassword.setForeground(DEFAULT_DARK_COLOR);
        txtPassword.setText("Mật khẩu");

        edtPassword = new FlatPasswordInput();
        edtPassword.setBackground(new Color(240, 240, 240));
        edtPassword.setFont(DEFAULT_FONT);
        edtPassword.setForeground(DEFAULT_DARK_COLOR);

        txtNotification = new JLabel();
        txtNotification.setVisible(false);
        txtNotification.setFont(DEFAULT_FONT);
        txtNotification.setForeground(new Color(204, 0, 0));
        txtNotification.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNotification.setText("Lỗi đăng nhập #???");

        JButton btnLogin = new FlatButton();
        btnLogin.setBackground(new Color(0, 102, 153));
        btnLogin.setFont(DEFAULT_FONT);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setText("Đăng nhập");
        btnLogin.setBorder(null);

        JLabel txtGotoRegister = new JLabel();
        txtGotoRegister.setFont(new Font("Roboto", Font.ITALIC, 16));
        txtGotoRegister.setForeground(new Color(102, 102, 102));
        txtGotoRegister.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtGotoRegister.setText("Đi đến đăng ký");

        JPanel formLogin = new RoundedPanel(20);
        formLogin.setBackground(Color.WHITE);

        javax.swing.GroupLayout formLoginLayout;
        formLoginLayout = new GroupLayout(formLogin);
        formLogin.setLayout(formLoginLayout);

        GroupLayout.Group horizontalGroup = formLoginLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(formLoginLayout.createSequentialGroup()
                        .addComponent(icoSettings)
                        .addGap(30)
                )
                .addGroup(formLoginLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(formLoginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtPassword)
                                .addComponent(txtUsername)
                                .addComponent(txtLogin, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, 561, Short.MAX_VALUE)
                                .addComponent(edtUsername, GroupLayout.Alignment.LEADING)
                                .addComponent(edtPassword)
                                .addComponent(txtNotification, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLogin, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGotoRegister, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30));
        formLoginLayout.setHorizontalGroup(horizontalGroup);

        GroupLayout.Group verticalGroup = formLoginLayout.createSequentialGroup()
                .addGap(12)
                .addComponent(icoSettings)
                .addGap(26, 26, 26)
                .addComponent(txtLogin, PREFERRED_SIZE, 65, PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtUsername, PREFERRED_SIZE, 57, PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtPassword, PREFERRED_SIZE, 57, PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNotification)
                .addGap(18, 18, 18)
                .addComponent(btnLogin, PREFERRED_SIZE, 55, PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(txtGotoRegister)
                .addContainerGap(45, Short.MAX_VALUE);
        formLoginLayout.setVerticalGroup(verticalGroup);
        // =========================================================================

        // =============================== BACKGROUND ==============================
        this.setBackground(new Color(30, 34, 82));
        backgroundLayout = new javax.swing.GroupLayout(this);
        this.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(324, 324, Short.MAX_VALUE)
                                .addComponent(formLogin, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(324, 324, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(110)
                                .addComponent(formLogin, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(110))
        );
        // =========================================================================


        // =========================================================================
        // ============================ EVENT LISTENERS ============================
        // =========================================================================

        icoSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component source = (Component) e.getSource();
                Container root = source.getParent().getParent().getParent();
                System.out.println(root);
                root.remove(1);
                JPanel loginSettingsPanel = new LoginSettingsPanel();
                root.add(loginSettingsPanel);
                root.revalidate();
                root.repaint();
            }
        });

        edtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Component source = (Component) e.getSource();
                    Container root = source.getParent().getParent().getParent();
                    executeLogin(root);
                }
            }
        });

        edtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Component source = (Component) e.getSource();
                    Container root = source.getParent().getParent().getParent();
                    executeLogin(root);
                }
            }
        });

        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Component source = (Component) e.getSource();
                Container root = source.getParent().getParent().getParent();
                executeLogin(root);
            }
        });

        txtGotoRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Component source = (Component) e.getSource();
                Container root = source.getParent().getParent().getParent();
                root.remove(1);
                root.add(new RegisterPanel());
                root.revalidate();
                root.repaint();
            }
        });
    }

    private void executeLogin(Container root) {
        String username = this.edtUsername.getText();
        String password = this.edtPassword.getText();
        String failImage = "/emoji_images/emoji_6.png";
        String successImage = "/emoji_images/emoji_2.png";
        String serverDownImage = "/emoji_images/emoji_5.png";
        Notification.getInstance().showLoading(txtNotification);
        EventQueue.invokeLater(() -> {
            if (username.isEmpty() || password.isEmpty()) {
                Notification.getInstance().showNotification(txtNotification, "Tài khoản hoặc mật khẩu không thể rỗng.", failImage, Notification.DANGER, 3000);
            } else {
                InetAddress ip = null;
                try {
                    ip = InetAddress.getByName(getIpServer());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                socket = null;
                InetAddress finalIp = ip;
                SetTimeOut.setTimeout(()-> {
                    try {
                        socket = new Socket();
                        socket.connect(new InetSocketAddress(finalIp, getPortServer()),3000);
                        socket.setSoTimeout(3000);

                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF("LOGIN|" + username + "|" + password);

                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        String result = dis.readUTF();
                        switch (result) {
                            case "LOGIN_SUCCESS":
                                Notification.getInstance().showNotification(txtNotification, "Đăng nhập thành công.", successImage, Notification.SUCCESS, 3000);
                                dos.writeUTF("READY");
                                SetTimeOut.setTimeout(()-> {
                                    root.remove(1);
                                    root.add(new MainPanel(socket, username));
                                    root.revalidate();
                                    root.repaint();
                                }, 1000);
                                break;
                            case "USER_NOT_EXIST":
                                Notification.getInstance().showNotification(txtNotification, "Tên người dùng không tồn tại.", failImage, Notification.DANGER, 3000);
                                break;
                            case "WRONG_PASSWORD":
                                Notification.getInstance().showNotification(txtNotification, "Sai mật khẩu.", failImage, Notification.DANGER, 3000);
                                break;
                        }

                    } catch (IOException e) {
                        txtNotification.setVisible(false);
                        Notification.getInstance().showNotification(txtNotification, "<html>Không thể kết nối đến server. Vui lòng cấu hình lại địa chỉ IP và port</html>", serverDownImage, Notification.WARNING, 5000);
                    }
                },500);
            }
        });
    }
}
