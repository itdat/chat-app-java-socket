package com.ntdat.chatapp;

import com.ntdat.chatapp.ui.Login;
import java.awt.*;

import static javax.swing.UIManager.getDefaults;

public class Main {
    // DEFINE VALUES
    public static final Font DEFAULT_FONT = new Font("Roboto", Font.PLAIN, 18);
    public static final Color PANEL_BACKGROUND_COLOR = Color.decode("#586692");
    public static final String APP_NAME = "ChatApp ITD v1.0";
    public static final Color MOUSE_HOVER_NAV_BUTTON = new Color(51, 74, 137);
    public static final Color MOUSE_PRESS_NAV_BUTTON = new Color(36,55,114);
    public static final Color DEFAULT_NAV_BUTTON = new Color(88,102,146);
    public static final Color DEFAULT_DARK_COLOR = new Color(82, 82, 82, 255);

    public static void main(String[] args) {
        getDefaults().put("ScrollBar.minimumThumbSize", new Dimension(29, 29));
        EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
}
