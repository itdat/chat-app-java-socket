package com.ntdat.chatapp.ui.panels;

import com.ntdat.chatapp.ui.customcomponent.FlatButton;
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
import java.net.Socket;
import java.util.Scanner;

import static com.ntdat.chatapp.AppConfig.*;
import static com.ntdat.chatapp.Main.DEFAULT_DARK_COLOR;
import static com.ntdat.chatapp.Main.DEFAULT_FONT;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;

public class LoginSettingsPanel extends JPanel {
    private JTextField edtIpAddress;
    private JTextField edtPort;
    private JLabel txtNotification;
    private GroupLayout backgroundLayout;

    public LoginSettingsPanel() {
        initComponents();
    };

    private void initComponents() {
        // =============================== LOGIN FORM ==============================
        JLabel icoClose = new JLabel();
        icoClose.setIcon(new ImageIcon(getClass().getResource("/close_dark.png")));

        JLabel txtSettings = new JLabel();
        txtSettings.setFont(new Font("Roboto", Font.BOLD, 24));
        txtSettings.setForeground(DEFAULT_DARK_COLOR);
        txtSettings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtSettings.setText("Cài đặt");

        JLabel txtIpAddress = new JLabel();
        txtIpAddress.setFont(DEFAULT_FONT);
        txtIpAddress.setForeground(DEFAULT_DARK_COLOR);
        txtIpAddress.setText("Địa chỉ IP server");

        edtIpAddress = new FlatTextInput();
        edtIpAddress.setBackground(new Color(240, 240, 240));
        edtIpAddress.setFont(DEFAULT_FONT);
        edtIpAddress.setForeground(DEFAULT_DARK_COLOR);
        edtIpAddress.setText(getIpServer());

        JLabel txtPort = new JLabel();
        txtPort.setFont(DEFAULT_FONT);
        txtPort.setForeground(DEFAULT_DARK_COLOR);
        txtPort.setText("Port server");

        edtPort = new FlatTextInput();
        edtPort.setBackground(new Color(240, 240, 240));
        edtPort.setFont(DEFAULT_FONT);
        edtPort.setForeground(DEFAULT_DARK_COLOR);
        edtPort.setText("" + getPortServer());

        txtNotification = new JLabel();
        txtNotification.setVisible(false);
        txtNotification.setFont(DEFAULT_FONT);
        txtNotification.setForeground(new Color(204, 0, 0));
        txtNotification.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        JButton btnUpdate = new FlatButton();
        btnUpdate.setBackground(new Color(0, 102, 153));
        btnUpdate.setFont(DEFAULT_FONT);
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setText("Cập nhật");
        btnUpdate.setBorder(null);

        JPanel formLogin = new RoundedPanel(20);
        formLogin.setBackground(Color.WHITE);

        GroupLayout formLoginLayout;
        formLoginLayout = new GroupLayout(formLogin);
        formLogin.setLayout(formLoginLayout);

        GroupLayout.Group horizontalGroup = formLoginLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addGroup(formLoginLayout.createSequentialGroup()
                        .addComponent(icoClose)
                        .addGap(30)
                )
                .addGroup(formLoginLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(formLoginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(txtPort)
                                .addComponent(txtIpAddress)
                                .addComponent(txtSettings, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, 561, Short.MAX_VALUE)
                                .addComponent(edtIpAddress, GroupLayout.Alignment.LEADING)
                                .addComponent(edtPort)
                                .addComponent(txtNotification, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnUpdate, GroupLayout.Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30));
        formLoginLayout.setHorizontalGroup(horizontalGroup);

        GroupLayout.Group verticalGroup = formLoginLayout.createSequentialGroup()
                .addGap(12)
                .addComponent(icoClose)
                .addGap(18)
                .addComponent(txtSettings, PREFERRED_SIZE, 30, PREFERRED_SIZE)
                .addGap(18)
                .addComponent(txtIpAddress)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtIpAddress, PREFERRED_SIZE, 57, PREFERRED_SIZE)
                .addGap(18)
                .addComponent(txtPort)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtPort, PREFERRED_SIZE, 57, PREFERRED_SIZE)
                .addGap(18)
                .addComponent(txtNotification)
                .addGap(18)
                .addComponent(btnUpdate, PREFERRED_SIZE, 55, PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE);
        formLoginLayout.setVerticalGroup(verticalGroup);
        // =========================================================================

        // =============================== BACKGROUND ==============================
        this.setBackground(new Color(30, 34, 82));
        backgroundLayout = new GroupLayout(this);
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
                                .addGap(150)
                                .addComponent(formLogin, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(150))
        );
        // =========================================================================


        // =========================================================================
        // ============================ EVENT LISTENERS ============================
        // =========================================================================

        icoClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Component source = (Component) e.getSource();
                Container root = source.getParent().getParent().getParent();
                System.out.println(root);
                root.remove(1);
                JPanel loginPanel = new LoginPanel();
                root.add(loginPanel);
                root.revalidate();
                root.repaint();
            }
        });

        edtIpAddress.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                }
            }
        });

        edtPort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                }
            }
        });

        btnUpdate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (storeServerAddress(edtIpAddress.getText(), edtPort.getText())) {
                    Notification.getInstance().showNotification(txtNotification, "Cập nhật thành công.", "/emoji_images/emoji_3.png", Notification.SUCCESS, 2000);
                } else {
                    Notification.getInstance().showNotification(txtNotification, "Cập nhật thất bại.", "/emoji_images/emoji_7.png", Notification.DANGER, 2000);
                }
            }
        });
    }
}
