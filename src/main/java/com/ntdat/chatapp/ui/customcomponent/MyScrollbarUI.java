// http://www.java2s.com/Tutorials/Java/Swing_How_to/JScrollPane/Create_custom_JScrollBar_for_JScrollPane.htm
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ntdat.chatapp.ui.customcomponent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

/**
 *
 * @author ntdat
 */
public class MyScrollbarUI extends BasicScrollBarUI {
  private final Dimension d = new Dimension();
  private Color bg = new Color(46, 50, 92);
  private Color scrBtnBgClick = new Color(61, 71, 116, 255);
  private Color scrBtnBgHover = new Color(61, 71, 116, 200);
  private Color scrBtnBgDefault = new Color(61, 71, 116, 100);

  public MyScrollbarUI() {}

  public MyScrollbarUI(Color background, Color scrollButtonBackground) {
    this.bg = background;
    setScrollButtonBackground(scrollButtonBackground);
  }

  private void setScrollButtonBackground(Color scrBtnBgClick) {
    this.scrBtnBgClick =scrBtnBgClick;
    this.scrBtnBgHover = new Color(scrBtnBgClick.getRed(),scrBtnBgClick.getGreen(), scrBtnBgClick.getBlue(), 200);
    this.scrBtnBgClick = new Color(scrBtnBgClick.getRed(),scrBtnBgClick.getGreen(), scrBtnBgClick.getBlue(), 100);
  }

  @Override
  protected JButton createDecreaseButton(int orientation) {
    return new JButton() {
      @Override
      public Dimension getPreferredSize() {
        return d;
      }
    };
  }

  @Override
  protected JButton createIncreaseButton(int orientation) {
    return new JButton() {
      @Override
      public Dimension getPreferredSize() {
        return d;
      }
    };
  }

  @Override
  protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
  }

  @Override
  protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
    Color color = null;
    JScrollBar sb = (JScrollBar) c;
    if (!sb.isEnabled() || r.width > r.height) {
      return;
    } else if (isDragging) {
      color = this.scrBtnBgClick;
    } else if (isThumbRollover()) {
      color = this.scrBtnBgHover;
    } else {
      color = this.scrBtnBgDefault;
    }

    g2.setPaint(color);
    g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
    g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);
    g2.dispose();
  }

  @Override
  protected void setThumbBounds(int x, int y, int width, int height) {
    super.setThumbBounds(x, y, width, height);
    scrollbar.repaint();
    scrollbar.setBackground(this.bg);
  }
}
