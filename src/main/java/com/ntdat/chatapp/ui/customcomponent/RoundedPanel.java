/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui.customcomponent;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ntdat
 */
public class RoundedPanel extends JPanel
    {
        private Color backgroundColor;
        private int cornerRadius = 15;
        public RoundedPanel(LayoutManager layout, int radius) {
            super(layout);
            setOpaque(false);
            cornerRadius = radius;
        }
//        public RoundedPanel(LayoutManager layout, int radius, Color bgColor) {
//            super(layout);
//            setOpaque(false);
//            cornerRadius = radius;
//            backgroundColor = bgColor;
//        }
        public RoundedPanel(int radius) {
            super();
            setOpaque(false);
            cornerRadius = radius;
            
        }
//        public RoundedPanel(int radius, Color bgColor) {
//            super();
//            setOpaque(false);
//            cornerRadius = radius;
//            backgroundColor = bgColor;
//        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();
            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //Draws the rounded panel with borders.
            if (backgroundColor != null) {
                graphics.setColor(backgroundColor);
            } else {
                graphics.setColor(getBackground());
            }
            graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
            graphics.setColor(getForeground());
//            graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border
//            
        }
    } 
