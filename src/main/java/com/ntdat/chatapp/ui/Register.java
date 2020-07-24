/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui;

import com.ntdat.chatapp.Main;
import com.ntdat.chatapp.ui.customcomponent.*;
import com.ntdat.chatapp.utilities.SetTimeOut;

import javax.swing.*;
import java.awt.*;

import static javax.swing.GroupLayout.*;

public class Register extends RoundedJFrame {
    // DEFINE VALUES
    private static final Font DEFAULT_FONT = new Font("Roboto", Font.PLAIN, 18);
    private static final Color DEFAULT_DARK_COLOR = new Color(82, 82, 82, 255);

    public Register() {
        initComponents();
    }

    private void initComponents() {

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

        GroupLayout.Group horizontalGroup = formRegisterLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(formRegisterLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(formRegisterLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(txtPassword)
                                .addComponent(txtUsername)
                                .addComponent(txtConfirmPassword)
                                .addComponent(edtConfirmPassword)
                                .addComponent(txtLogin, Alignment.LEADING, DEFAULT_SIZE, 561, Short.MAX_VALUE)
                                .addComponent(edtUsername, Alignment.LEADING)
                                .addComponent(edtPassword)
                                .addComponent(txtNotification, Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnRegister, Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtGotoLogin, Alignment.LEADING, DEFAULT_SIZE, DEFAULT_SIZE, Short.MAX_VALUE))
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
        JPanel background = new JPanel();
        background.setBackground(new Color(30, 34, 82));
        GroupLayout backgroundLayout = new GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
                backgroundLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(324, 324, Short.MAX_VALUE)
                                .addComponent(formRegister, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(324, 324, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
                backgroundLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(backgroundLayout.createSequentialGroup()
                                .addGap(70, 70, Short.MAX_VALUE)
                                .addComponent(formRegister, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                .addGap(150, 150, Short.MAX_VALUE))
        );
        // =========================================================================

        // Main jFrame
        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(boxLayout);
        getContentPane().add(titleBar);
        getContentPane().add(background);

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

        btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                executeLogin();
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
        }
        if (evt.getSource() == this.btnMinimize) {
            this.txtMinimize.setForeground(Color.red);
        }
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

    private void raiseError(String message) {
        txtNotification.setIcon(null);
        txtNotification.setForeground(new Color(204, 0, 0));
        txtNotification.setText(message);
        txtNotification.setVisible(true);
        SetTimeOut.setTimeout(()-> txtNotification.setVisible(false), 2000);
    }

    private void showLoading() {
        txtNotification.setText("");
        Image spinner = new ImageIcon(this.getClass().getResource("/loader.gif")).getImage();
        txtNotification.setIcon(new ImageIcon(spinner));
        txtNotification.setVisible(true);
        SetTimeOut.setTimeout(()-> txtNotification.setVisible(false), 2000);
    }

    private void executeLogin() {
        String username = this.edtUsername.getText();
        String password = this.edtPassword.getText();
        String confirmPassword = this.edtConfirmPassword.getText();

        String img1 = "<img src='" + this.getClass().getResource("/emoji_images/emoji_6.png") + "' width='32' height='32'/>";

        showLoading();
        SetTimeOut.setTimeout(()->{
            System.out.println(confirmPassword);
            if (username.equals("") || password.equals("")) {
            raiseError("<html>Vui lòng nhập tài khoản và mật khẩu " + img1 + "</html>");
            }}, 2000);

    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        System.out.println("Start login");
        EventQueue.invokeLater(() -> new Register().setVisible(true));
    }

    private JPanel btnClose;
    private JPanel btnMinimize;
    private JTextField edtPassword;
    private JTextField edtConfirmPassword;
    private JTextField edtUsername;
    private JLabel txtClose;
    private JLabel txtNotification;
    private JLabel txtMinimize;
    private int pX;
    private int pY;
}
