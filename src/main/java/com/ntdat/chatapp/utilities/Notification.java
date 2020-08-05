package com.ntdat.chatapp.utilities;

import javax.swing.*;
import java.awt.*;

public class Notification {
    public static final Color SUCCESS = new Color(34, 187, 51);
    public static final Color DANGER = new Color(187, 33, 36);
    public static final Color WARNING = new Color(240, 173, 78);
    public static final Color INFO = new Color(91,192,222);

    private static Notification instance;

    private Notification() {}

    public static Notification getInstance() {
        if (instance == null) {
            instance = new Notification();
        }
        return instance;
    }

    public void showNotification(JLabel target, String message, String icon, Color color, int timeout) {
        EventQueue.invokeLater(()-> {
            target.setIcon(null);
            if (!icon.equals("")) {
                target.setIcon(new ImageIcon(getClass().getResource(icon)));
            }
            target.setForeground(color);
            target.setText(message);
            target.setVisible(true);
            SetTimeOut.setTimeout(()-> target.setVisible(false), timeout);
        });
    }

    public void showLoading(JLabel target) {
        target.setText("");
        Image spinner = new ImageIcon(this.getClass().getResource("/loader.gif")).getImage();
        target.setIcon(new ImageIcon(spinner));
        target.setVisible(true);
    }
}
