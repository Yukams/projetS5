package utils;

import javax.swing.*;

import static client.UserRequest.checkValidString;

public class Utils {
    public static final int MIN_CHARS = 3;
    public static final int MAX_CHARS = 25;
    /* String must not contain: !@#$%&*()'+,-./:;<=>?[]^`{|}
    * String must be between [MIN_CHARS-MAX_CHARS] chars */
    public static boolean isValidString(String str){
        String specialCharactersString = "\\!@#$%&*()'+,./:;<=>?[]^`{|} ";
        return checkValidString(str, MIN_CHARS, MAX_CHARS, specialCharactersString);
    }

    // Warning Window
    public static void warningWindow(String message, String title) {
        JOptionPane.showMessageDialog(new JFrame(), message, title,
                JOptionPane.WARNING_MESSAGE);
    }
    // Error Window
    public static void errorWindow(String message, String title) {
        JOptionPane.showMessageDialog(new JFrame(), message, title,
                JOptionPane.ERROR_MESSAGE);
    }
    // Error Window
    public static void informationWindow(String message, String title) {
        JOptionPane.showMessageDialog(new JFrame(), message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Check credentials nature
    public static boolean validCredentials(String username, String password) {
        return !username.equals("") && !password.equals("") && Utils.isValidString(username);
    }
}
