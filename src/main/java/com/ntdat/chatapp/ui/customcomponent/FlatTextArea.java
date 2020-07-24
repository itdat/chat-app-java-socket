/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui.customcomponent;

/**
 *
 * @author ntdat
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FlatTextArea extends JTextArea {

    private int radius = 10;
    private Color border = new Color(173, 173, 173, 255);
    private Color fill = new Color(255,255,255,255);

    public FlatTextArea() {
        super(10, 20);
        setOpaque(false);
        setBorder(null);
        setRadius(radius);
    }

    public FlatTextArea(int radius, Color border, Color fill) {
        super(1,20);
        setOpaque(false);
        setBorder(null);
        setRadius(radius);
        this.border = border;
        this.fill = fill;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fill);
        g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, getRadius(), getRadius());
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(border);
        g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, getRadius(), getRadius());
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public int getRadius() {
        return radius;
    }

//    @Override
//    public Insets getInsets() {
//        int value = getRadius() / 10;
//        return new Insets(value, value, value, value);
//    }

}