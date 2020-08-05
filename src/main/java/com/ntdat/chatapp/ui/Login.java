/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui;

import com.ntdat.chatapp.AppConfig;
import com.ntdat.chatapp.Main;
import com.ntdat.chatapp.ui.customcomponent.*;
import com.ntdat.chatapp.ui.panels.LoginPanel;
import com.ntdat.chatapp.ui.panels.MainPanel;
import com.ntdat.chatapp.utilities.SetTimeOut;

import javax.swing.*;
import javax.swing.text.GapContent;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static com.ntdat.chatapp.AppConfig.*;
import static com.ntdat.chatapp.Main.DEFAULT_FONT;
import static com.ntdat.chatapp.Main.PANEL_BACKGROUND_COLOR;
import static javax.swing.GroupLayout.*;

public class Login extends RoundedJFrame {
    public static boolean exit = false;

    public Login() {
        // Init directory
        File dataDirectory = new File(DATA_DIRECTORY);
        dataDirectory.mkdirs();
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

        JPanel background = new LoginPanel();

        // Main jFrame
        BoxLayout boxLayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
        getContentPane().setLayout(boxLayout);
        getContentPane().add(titleBar);
        getContentPane().add(background);
        getContentPane().setBackground(PANEL_BACKGROUND_COLOR);
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
        Login.exit = true;
        dispose();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    private javax.swing.JPanel btnClose;
    private javax.swing.JPanel btnMinimize;
    private javax.swing.JLabel txtClose;
    private javax.swing.JLabel txtMinimize;
    private int pX;
    private int pY;
}
