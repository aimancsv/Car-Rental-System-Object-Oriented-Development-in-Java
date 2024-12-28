package main.utils;

import javax.swing.*;

public class PromptSwing {
    /**
     * Display info or error messages
     */
    public static void promptMessageInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void promptMessageError(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.ERROR_MESSAGE);
    }
}
