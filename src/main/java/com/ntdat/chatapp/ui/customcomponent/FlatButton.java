// https://dutasharing.blogspot.com/2019/08/java-swing-netbeans-custom-palette_8.html
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
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class FlatButton extends JButton {
    private Color mouseHoverBackground = new Color(58,66,129);
    private Color mousePressBackground = new Color(30,34,82);
    private Color defaultBackground = new Color(36,55,114);
    private boolean enter, press;  
  
    public boolean isEnter() {  
        return enter;  
    }  
  
    public void setEnter(boolean enter) {  
        this.enter = enter;  
        repaint();  
    }  
  
    public boolean isPress() {  
        return press;  
    }  
  
    public void setPress(boolean press) {  
        this.press = press;  
        repaint();  
    }

    public void setColors(Color hoverBg, Color pressBg, Color defaultBg) {
        mouseHoverBackground = hoverBg;
        mousePressBackground = pressBg;
        defaultBackground = defaultBg;
    }
  
    public FlatButton() {
        setFont(new Font("Roboto", 0, 18));
        setOpaque(false);  
        setBorderPainted(false);  
        setFocusPainted(false);  
        setContentAreaFilled(false);  
        setForeground(Color.white);  
        setFont(getFont().deriveFont(Font.PLAIN));  
        addMouseListener(new MouseAdapter() {  
  
            @Override  
            public void mouseEntered(MouseEvent e) {  
                super.mouseEntered(e);  
                setEnter(true);  
            }  
  
            @Override  
            public void mouseExited(MouseEvent e) {  
                super.mouseExited(e);  
                setEnter(false);  
            }  
  
            @Override  
            public void mousePressed(MouseEvent e) {  
                super.mousePressed(e);  
                setPress(true);  
            }  
  
            @Override  
            public void mouseReleased(MouseEvent e) {  
                super.mouseReleased(e);  
                setPress(false);  
            }
        });  
    }  
    
    public FlatButton(Color mouseHoverBackground, Color mousePressBackground, Color defaultBackground ) {
        this.mouseHoverBackground = mouseHoverBackground;
        this.mousePressBackground = mousePressBackground;
        this.defaultBackground = defaultBackground;
        setFont(new Font("Roboto", 0, 18));
        setOpaque(false);  
        setBorderPainted(false);  
        setFocusPainted(false);  
        setContentAreaFilled(false);  
        setForeground(Color.white);  
        setFont(getFont().deriveFont(Font.PLAIN));  
        addMouseListener(new MouseAdapter() {  
  
            @Override  
            public void mouseEntered(MouseEvent e) {  
                super.mouseEntered(e);  
                setEnter(true);  
            }  
  
            @Override  
            public void mouseExited(MouseEvent e) {  
                super.mouseExited(e);  
                setEnter(false);  
            }  
  
            @Override  
            public void mousePressed(MouseEvent e) {  
                super.mousePressed(e);  
                setPress(true);  
            }  
  
            @Override  
            public void mouseReleased(MouseEvent e) {  
                super.mouseReleased(e);  
                setPress(false);  
            }  
        });  
    }  
  
    @Override  
    protected void paintComponent(Graphics g) {  
        Graphics2D gd = (Graphics2D) g.create();  
        Color background = defaultBackground;
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10);  
        if (isEnter()) {
            background = mouseHoverBackground;
            if (isPress()) {
                background = mousePressBackground;
            }  
        }  
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
        gd.setColor(background);
        gd.fill(shape);  
        gd.dispose();  
        super.paintComponent(g);  
    }  
  
    public Color getDefaultBackground() {
        return this.defaultBackground;
    }  
  
    public void setDefaultBackground(Color bgColor) {
        this.defaultBackground = bgColor;
    }  

    public Color getMouseHoverBackground() {
        return this.mouseHoverBackground;
    }  
  
    public void setMouseHoverBackground(Color bgColor) {
        this.mouseHoverBackground = bgColor;
    }  

    public Color getMousePressBackground() {
        return this.mousePressBackground;
    }  
  
    public void setMousePressBackground(Color bgColor) {
        this.mousePressBackground = bgColor;
    }  
}  