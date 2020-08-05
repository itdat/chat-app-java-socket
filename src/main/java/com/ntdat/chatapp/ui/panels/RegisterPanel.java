package com.ntdat.chatapp.ui.panels;

import com.ntdat.chatapp.AppConfig;
import com.ntdat.chatapp.ui.customcomponent.FlatButton;
import com.ntdat.chatapp.ui.customcomponent.FlatPasswordInput;
import com.ntdat.chatapp.ui.customcomponent.FlatTextInput;
import com.ntdat.chatapp.ui.customcomponent.RoundedPanel;
import com.ntdat.chatapp.utilities.Notification;
import com.ntdat.chatapp.utilities.SetTimeOut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static com.ntdat.chatapp.AppConfig.getIpServer;
import static com.ntdat.chatapp.AppConfig.getPortServer;
import static com.ntdat.chatapp.Main.DEFAULT_DARK_COLOR;
import static com.ntdat.chatapp.Main.DEFAULT_FONT;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;

public class RegisterPanel extends JPanel {
    private JTextField edtPassword;
    private JTextField edtConfirmPassword;
    private JTextField edtUsername;
    private JLabel txtNotification;
    private Socket socket;

    public RegisterPanel() {
        initComponents();
    }

    private void initComponents() {
        // ============================== REGISTER FORM ============================
        JLabel txtLogin = new JLabel();
        txtLogin.setFont(new Font("Roboto", Font.BOLD, 36));
        txtLogin.setForeground(DEFAULT_DARK_COLOR);
        txtLogin.setHorizontalAlignment(SwingConstants.CENTER);
        txtLogin.setText("Đăng ký");

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

        JLabel txtConfirmPassword = new JLabel();
        txtConfirmPassword.setFont(DEFAULT_FONT);
        txtConfirmPassword.setForeground(DEFAULT_DARK_COLOR);
        txtConfirmPassword.setText("Xác nhận mật khẩu");

        edtConfirmPassword = new FlatPasswordInput();
        edtConfirmPassword.setBackground(new Color(240, 240, 240));
        edtConfirmPassword.setFont(DEFAULT_FONT);
        edtConfirmPassword.setForeground(DEFAULT_DARK_COLOR);

        txtNotification = new JLabel();
        txtNotification.setVisible(false);
        txtNotification.setFont(DEFAULT_FONT);
        txtNotification.setForeground(new Color(204, 0, 0));
        txtNotification.setHorizontalAlignment(SwingConstants.CENTER);
        txtNotification.setText("Lỗi đăng ký #???");

        JButton btnRegister = new FlatButton();
        btnRegister.setBackground(new Color(0, 102, 153));
        btnRegister.setFont(DEFAULT_FONT);
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setText("Đăng ký");
        btnRegister.setBorder(null);

        JLabel txtGotoLogin = new JLabel();
        txtGotoLogin.setFont(new Font("Roboto", Font.ITALIC, 16));
        txtGotoLogin.setForeground(new Color(102, 102, 102));
        txtGotoLogin.setHorizontalAlignment(SwingConstants.CENTER);
        txtGotoLogin.setText("Đi đến đăng nhập");

        JPanel formRegister = new RoundedPanel(20);
        formRegister.setBackground(Color.WHITE);

        GroupLayout formRegisterLayout;
        formRegisterLayout = new GroupLayout(formRegister);
        formRegister.setLayout(formRegisterLayout);

        GroupLayout.Group horizontalGroup = formRegisterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(formRegisterLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(formRegisterLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtPassword)
                                .addComponent(txtUsername)
                                .addComponent(txtConfirmPassword)
                                .addComponent(edtConfirmPassword)
                                .addComponent(txtLogin, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, 561, Short.MAX_VALUE)
                                .addComponent(edtUsername, GroupLayout.Alignment.LEADING)
                                .addComponent(edtPassword)
                                .addComponent(txtNotification, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRegister, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGotoLogin, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30));
        formRegisterLayout.setHorizontalGroup(horizontalGroup);

        GroupLayout.Group verticalGroup = formRegisterLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(txtLogin, PREFERRED_SIZE, 65, PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtUsername)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtUsername, PREFERRED_SIZE, 57, PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPassword)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtPassword, PREFERRED_SIZE, 57, PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtConfirmPassword)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtConfirmPassword, PREFERRED_SIZE, 57, PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNotification)
                .addGap(18, 18, 18)
                .addComponent(btnRegister, PREFERRED_SIZE, 55, PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(txtGotoLogin)
                .addContainerGap(45, Short.MAX_VALUE);
        formRegisterLayout.setVerticalGroup(verticalGroup);
        // =========================================================================

        // =============================== BACKGROUND ==============================
        this.setBackground(new Color(30, 34, 82));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(324, 324, Short.MAX_VALUE)
                                .addComponent(formRegister, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(324, 324, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, Short.MAX_VALUE)
                                .addComponent(formRegister, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(150, 150, Short.MAX_VALUE))
        );
        // =========================================================================

        btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                executeRegister();
            }
        });

        txtGotoLogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Component source = (Component) e.getSource();
                Container root = source.getParent().getParent().getParent();
                root.remove(1);
                root.add(new LoginPanel());
                root.revalidate();
                root.repaint();
            }
        });
    }

    private void executeRegister() {
        String username = this.edtUsername.getText();
        String password = this.edtPassword.getText();
        String confirmPassword = this.edtConfirmPassword.getText();
        String failImage = "/emoji_images/emoji_6.png";
        String successImage = "/emoji_images/emoji_2.png";
        String serverDownImage = "/emoji_images/emoji_5.png";
        Notification.getInstance().showLoading(txtNotification);
        EventQueue.invokeLater(() -> {
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Notification.getInstance().showNotification(txtNotification, "Không được để trống các trường.", failImage, Notification.DANGER, 3000);
            } else if (!password.equals(confirmPassword)) {
                Notification.getInstance().showNotification(txtNotification, "Mật khẩu xác nhận không trùng khớp.", failImage, Notification.DANGER, 3000);
            } else {
                InetAddress ip = null;
                try {
                    ip = InetAddress.getByName(getIpServer());
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

                socket = null;
                InetAddress finalIp = ip;
                SetTimeOut.setTimeout(()->{
                    try {
                        socket = new Socket();
                        socket.connect(new InetSocketAddress(finalIp, getPortServer()),3000);
                        socket.setSoTimeout(3000);

                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        dos.writeUTF("REGISTER|" + username + "|" + password);

                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        String result = dis.readUTF();
                        switch (result) {
                            case "REGISTER_SUCCESS":
                                Notification.getInstance().showNotification(txtNotification, "Đăng ký thành công.", successImage, Notification.SUCCESS, 3000);
                                break;
                            case "USERNAME_EXISTED":
                                Notification.getInstance().showNotification(txtNotification, "Tên người dùng đã tồn tại.", failImage, Notification.DANGER, 3000);
                                break;
                        }
                    } catch (IOException e) {
                        txtNotification.setVisible(false);
                        Notification.getInstance().showNotification(txtNotification, "<html>Không thể kết nối đến server. Vui lòng cấu hình lại địa chỉ IP và port</html>", serverDownImage, Notification.WARNING, 5000);
                    }
                }, 500);
            }
        });
    }
}
