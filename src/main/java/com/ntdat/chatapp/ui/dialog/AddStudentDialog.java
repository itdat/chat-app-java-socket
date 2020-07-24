/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui.dialog;

import com.ntdat.chatapp.ui.customcomponent.FlatButton;
import com.ntdat.chatapp.ui.customcomponent.FlatTextInput;
import com.ntdat.chatapp.ui.customcomponent.RoundedPanel;

import javax.swing.*;
import java.awt.*;

public class AddStudentDialog extends RoundedPanel {
    private static final Color MOUSE_HOVER_BUTTON = new Color(51, 74, 137);
    private static final Color MOUSE_PRESS_BUTTON = new Color(36,55,114);
    private static final Color DEFAULT_BUTTON = new Color(88,102,146);

    public AddStudentDialog(int radius, String classID) {
        super(radius);
        this.classIDMain = classID;
        initComponents();
    }

    private String classIDMain;
    private String sex = "Nam";
    private boolean addToDefaultClass = true;

    private void initComponents() {
        txtStudentId = new JLabel();
        txtStudentId.setText("MSSV");
        edtStudentId = new FlatTextInput();

        txtFullName = new JLabel();
        txtFullName.setText("Họ và tên");
        edtFullName = new FlatTextInput();

        txtSex = new JLabel();
        txtSex.setText("Giới tính");
        btnMale = new FlatButton(MOUSE_HOVER_BUTTON, MOUSE_PRESS_BUTTON, MOUSE_PRESS_BUTTON);

        btnMale.setText("Nam");
        btnFemale = new FlatButton(MOUSE_HOVER_BUTTON, MOUSE_PRESS_BUTTON, DEFAULT_BUTTON);
        btnFemale.setText("Nữ");

        txtId = new JLabel();
        txtId.setText("CMND");
        edtId = new FlatTextInput();

        btnAdd = new FlatButton();
        btnAdd.setText("Thêm");

        btnCancel = new FlatButton();
        btnCancel.setText("Hủy");

        if (classIDMain.contains("-")) {
            edtFullName.setEditable(false);
            edtId.setEditable(false);
            addToDefaultClass = false;
        }

        edtStudentId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
            }
        });

        btnMale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (addToDefaultClass) {
                    sex = "Nam";
                    btnMale.setColors(MOUSE_HOVER_BUTTON, MOUSE_PRESS_BUTTON, MOUSE_PRESS_BUTTON);
                    btnMale.repaint();
                    btnFemale.setColors(MOUSE_HOVER_BUTTON, MOUSE_PRESS_BUTTON, DEFAULT_BUTTON);
                    btnFemale.repaint();
                }
            }
        });
        btnFemale.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (addToDefaultClass) {
                    sex = "Nu";
                    btnMale.setColors(MOUSE_HOVER_BUTTON, MOUSE_PRESS_BUTTON, DEFAULT_BUTTON);
                    btnMale.repaint();
                    btnFemale.setColors(MOUSE_HOVER_BUTTON, MOUSE_PRESS_BUTTON, MOUSE_PRESS_BUTTON);
                    btnFemale.repaint();
                }
            }
        });

        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            }
        });

        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JOptionPane.getRootFrame().dispose();
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(edtStudentId)
                                        .addComponent(edtFullName)
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 246, Short.MAX_VALUE)
                                                .addComponent(btnCancel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnAdd))
                                        .addComponent(edtId)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtStudentId)
                                                        .addComponent(txtFullName)
                                                        .addComponent(txtSex)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnMale)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnFemale))
                                                        .addComponent(txtId))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(txtStudentId)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edtStudentId, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtFullName)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edtFullName, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSex)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnMale)
                                        .addComponent(btnFemale))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtId)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(edtId, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAdd)
                                        .addComponent(btnCancel))
                                .addContainerGap())
        );
    }

    private void edtStudentIdActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void edtFullNameActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void edtIdActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private JButton btnAdd;
    private JButton btnCancel;
    private JComboBox<String> cbbSex;
    private JLabel txtStudentId;
    private JLabel txtFullName;
    private JLabel txtSex;
    private JLabel txtId;
    private JTextField edtStudentId;
    private JTextField edtFullName;
    private JTextField edtId;
    private FlatButton btnMale;
    private FlatButton btnFemale;
}
