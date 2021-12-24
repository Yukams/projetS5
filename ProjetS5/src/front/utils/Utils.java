package front.utils;

import javax.swing.*;

public class Utils {
    public static boolean isValidString(String str){
        String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^`{|} ";
        if(str.length()>=4 && str.length() <= 20) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if(specialCharactersString.contains(Character.toString(c))){ return false; }
            }
            return true;
        }
        return false;
    }

    // Message Erreur Syntax
    public void messageErrorSyntax() {
        JOptionPane.showMessageDialog(new JFrame(), "Nom d'Utilisateur ou Mot De Pass invalide !", "Error Syntax",
                JOptionPane.ERROR_MESSAGE);
    }
    // Message Erreur Data
    public void messageErrorCredentials() {
        JOptionPane.showMessageDialog(new JFrame(), "Nom d'Utilisateur ou Mot De Pass incorrect !", "Error Credentials",
                JOptionPane.WARNING_MESSAGE);
    }
}
