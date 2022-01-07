package main;

import affichage.ConnexionWindow;

public class mainFront {
    public static void main(String[] args){
        launchConnexionWindow();
    }
    // Launch connexion window
    private static void launchConnexionWindow() {
        ConnexionWindow fen = new ConnexionWindow();
        fen.setVisible(true);
    }
}
