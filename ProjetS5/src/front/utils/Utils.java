package front.utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Utils {
    /* String must not contain: !@#$%&*()'+,-./:;<=>?[]^`{|}
    * String must be between [4-20] chars */
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

    // Message Error Syntax
    public void syntaxErrorMessage() {
        JOptionPane.showMessageDialog(new JFrame(), "Invalid Username or Password", "Error Syntax",
                JOptionPane.ERROR_MESSAGE);
    }
    // Message Error Data
    public void credentialsErrorMessage() {
        JOptionPane.showMessageDialog(new JFrame(), "Wrong Username or Password", "Error Credentials",
                JOptionPane.WARNING_MESSAGE);
    }

    // Avoids long nested try catch statements (Closes Client cx)
    public void closeAll(Socket socket, BufferedReader in, PrintWriter out){
        try{
            if(in != null){
                in.close();
            }
            if(out != null){
                out.close();
            }
            if(socket != null){
                socket.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
