/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui;

import com.ntdat.chatapp.ui.customcomponent.*;
import com.ntdat.chatapp.utilities.SetTimeOut;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static javax.swing.GroupLayout.*;

public class Login extends RoundedJFrame {
    // DEFINE VALUES
    private static final Font DEFAULT_FONT = new Font("Roboto", 0, 18);
    private static final Color DEFAULT_DARK_COLOR = new Color(82, 82, 82, 255);

    public Login() {
        initComponents();
    }

    private void initComponents() {

        // =============================== TITLE BAR ===============================
        // Application name
        txtAppName = new JLabel();
        txtAppName.setFont(DEFAULT_FONT);
        txtAppName.setForeground(Color.WHITE);
        txtAppName.setText("ChatApp ITD v1.0");

        // Create UI minimize button
        txtMinimize = new JLabel();
        txtMinimize.setFont(new Font("Roboto", 1, 28));
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
        txtClose.setFont(new Font("Roboto", 1, 28));
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
        titleBar = new JPanel();
        titleBar.setMaximumSize(new Dimension(1282, 40));
        titleBar.setBackground(new Color(58, 66, 129));
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

        // =============================== LOGIN FORM ==============================
        txtLogin = new javax.swing.JLabel();
        txtLogin.setFont(new Font("Roboto", 1, 36));
        txtLogin.setForeground(DEFAULT_DARK_COLOR);
        txtLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtLogin.setText("Đăng nhập");

        txtUsername = new javax.swing.JLabel();
        txtUsername.setFont(DEFAULT_FONT);
        txtUsername.setForeground(DEFAULT_DARK_COLOR);
        txtUsername.setText("Tài khoản");

        edtUsername = new FlatTextInput();
        edtUsername.setBackground(new Color(240, 240, 240));
        edtUsername.setFont(DEFAULT_FONT);
        edtUsername.setForeground(DEFAULT_DARK_COLOR);

        txtPassword = new javax.swing.JLabel();
        txtPassword.setFont(DEFAULT_FONT);
        txtPassword.setForeground(DEFAULT_DARK_COLOR);
        txtPassword.setText("Mật khẩu");

        edtPassword = new FlatPasswordInput();
        edtPassword.setBackground(new Color(240, 240, 240));
        edtPassword.setFont(DEFAULT_FONT);
        edtPassword.setForeground(DEFAULT_DARK_COLOR);

        txtNotification = new javax.swing.JLabel();
        txtNotification.setVisible(false);
        txtNotification.setFont(DEFAULT_FONT);
        txtNotification.setForeground(new Color(204, 0, 0));
        txtNotification.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNotification.setText("Lỗi đăng nhập #???");

        btnLogin = new FlatButton();
        btnLogin.setBackground(new Color(0, 102, 153));
        btnLogin.setFont(DEFAULT_FONT);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setText("Đăng nhập");
        btnLogin.setBorder(null);

        txtForgetPassword = new javax.swing.JLabel();
        txtForgetPassword.setFont(new Font("Roboto", 2, 14));
        txtForgetPassword.setForeground(new Color(102, 102, 102));
        txtForgetPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtForgetPassword.setText("Quên mật khẩu?");

        formLogin = new RoundedPanel(20);
        formLogin.setBackground(Color.WHITE);

        javax.swing.GroupLayout formLoginLayout = new javax.swing.GroupLayout(formLogin);
        formLogin.setLayout(formLoginLayout);

        GroupLayout.Group horizontalGroup = formLoginLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(formLoginLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(formLoginLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(txtPassword)
                                .addComponent(txtUsername)
                                .addComponent(txtLogin, Alignment.LEADING, DEFAULT_SIZE, 561, Short.MAX_VALUE)
                                .addComponent(edtUsername, Alignment.LEADING)
                                .addComponent(edtPassword)
                                .addComponent(txtNotification, Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLogin, Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtForgetPassword, Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(30, 30, 30));
        formLoginLayout.setHorizontalGroup(horizontalGroup);

        GroupLayout.Group verticalGroup = formLoginLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(txtLogin, PREFERRED_SIZE, 65, PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(txtUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtUsername, PREFERRED_SIZE, 57, PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(txtPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edtPassword, PREFERRED_SIZE, 57, PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNotification)
                .addGap(18, 18, 18)
                .addComponent(btnLogin, PREFERRED_SIZE, 55, PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(txtForgetPassword)
                .addContainerGap(45, Short.MAX_VALUE);
        formLoginLayout.setVerticalGroup(verticalGroup);
        // =========================================================================

        // =============================== BACKGROUND ==============================
//        JPanel overlay = new JPanel();
//        overlay.setBackground(new Color(0, 0, 0, 52));
//        overlay.setLayout(new BorderLayout());
//        JLabel loading = new JLabel("Loading...");
//        loading.setFont(new Font("Roboto", 1, 32));
//        loading.setForeground(Color.WHITE);
//        loading.setHorizontalAlignment(JLabel.CENTER);
//        loading.setVerticalAlignment(JLabel.CENTER);
//        overlay.add(loading);

        background = new javax.swing.JPanel();
        background.setBackground(new Color(30, 34, 82));
        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(324, 324, Short.MAX_VALUE)
                                .addComponent(formLogin, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(324, 324, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(140, 140, Short.MAX_VALUE)
                                .addComponent(formLogin, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(140, 140, Short.MAX_VALUE))
        );
        // =========================================================================

        // Main jFrame
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());

        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(boxLayout);
        getContentPane().add(titleBar);
        getContentPane().add(background);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        btnMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimize(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mouseEnterHandle(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mouseExitHandle(evt);
            }
        });

        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mouseEnterHandle(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mouseExitHandle(evt);
            }
        });

        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    executeLogin(evt);
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
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
        if (evt.getSource() == this.btnClose) {
            this.txtClose.setForeground(Color.red);
        };
        if (evt.getSource() == this.btnMinimize) {
            this.txtMinimize.setForeground(Color.red);
        };
    }

    private void mouseExitHandle(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == this.btnClose) {
            this.txtClose.setForeground(Color.white);
        };
        if (evt.getSource() == this.btnMinimize) {
            this.txtMinimize.setForeground(Color.white);
        };
    }

    private void minimize(java.awt.event.MouseEvent evt) {
        this.setExtendedState(JFrame.ICONIFIED);
    }

    private void close(java.awt.event.MouseEvent evt) {
        dispose();
    }

    private void raiseError(String message) {
        txtNotification.setIcon(null);
        txtNotification.setForeground(new Color(204, 0, 0));
        txtNotification.setText(message);
        txtNotification.setVisible(true);
        SetTimeOut.setTimeout(()->{txtNotification.setVisible(false);}, 2000);
    }

    private void showLoading() {
        txtNotification.setText("");
        Image spinner = new ImageIcon(this.getClass().getResource("/loader.gif")).getImage();
        txtNotification.setIcon(new ImageIcon(spinner));
        txtNotification.setVisible(true);
        SetTimeOut.setTimeout(()->{txtNotification.setVisible(false);}, 2000);
    }

    private void executeLogin(java.awt.event.MouseEvent evt) throws IOException, URISyntaxException {
        String username = this.edtUsername.getText();
        String password = this.edtPassword.getText();

        String img1 = "<img src='" + this.getClass().getResource("/emoji_images/emoji_6.png") + "' width='32' height='32'/>";

        showLoading();
        SetTimeOut.setTimeout(()->{
            if (username.equals("") || password.equals("")) {
            raiseError("<html>Vui lòng nhập tài khoản và mật khẩu " + img1 + "</html>");
            return;
        }}, 2000);

    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(com.ntdat.chatapp.ui.Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(com.ntdat.chatapp.ui.Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(com.ntdat.chatapp.ui.Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(com.ntdat.chatapp.ui.Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        System.out.println("Start login");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new com.ntdat.chatapp.ui.Login().setVisible(true);
            }
        });
    }

    private javax.swing.JPanel background;
    private javax.swing.JPanel btnClose;
    private javax.swing.JButton btnLogin;
    private javax.swing.JPanel btnMinimize;
    private javax.swing.JTextField edtPassword;
    private javax.swing.JTextField edtUsername;
    private javax.swing.JPanel formLogin;
    private javax.swing.JLabel txtForgetPassword;
    private javax.swing.JLabel txtAppName;
    private javax.swing.JPanel titleBar;
    private javax.swing.JLabel txtClose;
    private javax.swing.JLabel txtNotification;
    private javax.swing.JLabel txtLogin;
    private javax.swing.JLabel txtMinimize;
    private javax.swing.JLabel txtPassword;
    private javax.swing.JLabel txtUsername;
    private int pX;
    private int pY;
}
