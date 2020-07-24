//https://www.codejava.net/java-se/swing/jtable-column-header-custom-renderer-examples
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui.customcomponent;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 *
 * @author ntdat
 */
public class HeaderRenderer extends JLabel implements TableCellRenderer {

    public HeaderRenderer() {
        setFont(new Font("Roboto", Font.BOLD, 18));
        setOpaque(true);
        setForeground(Color.BLACK);
        setBackground(new Color(210,210,210));
        setHorizontalAlignment(JLabel.CENTER);
        this.setPreferredSize(new Dimension(10,40));
//        setBorder(BorderFactory.createEtchedBorder());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        return this;
    }
}