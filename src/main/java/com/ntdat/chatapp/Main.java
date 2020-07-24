package com.ntdat.chatapp;

import com.ntdat.chatapp.ui.Login;

import javax.swing.*;
import java.awt.*;

import static javax.swing.UIManager.getDefaults;

public class Main {
    // DEFINE VALUES
    public static final Font DEFAULT_FONT = new Font("Roboto", Font.PLAIN, 18);
    public static final Color PANEL_BACKGROUND_COLOR = Color.decode("#586692");
    public static final String APP_NAME = "ChatApp ITD v1.0";


    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        getDefaults().put("ScrollBar.minimumThumbSize", new Dimension(29, 29));
        EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
}
