package front.affichage;

import front.users.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FenetreConnexion extends JFrame implements ActionListener {


    private JLabel seCo = new JLabel("Se connecter !",SwingConstants.CENTER);
    private JLabel id = new JLabel("Identifiant : ");
    private JLabel mdp = new JLabel("Mot de passe : ");
    private JTextField idTexte = new JTextField(10);
    private JPasswordField mdpTexte = new JPasswordField(10);
    private JButton connexionButton = new JButton("Connexion");
    private String username;
    private String password;

    public FenetreConnexion(){
        //titre
        super("Se connecter");
        //taille fenetre
        setSize(900, 550);
        //taille non modifiable manuellement
        setResizable(false);
        //Click sur croix ferme la fenetre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Centrer la fenetre par rapport à l'ecran
        setLocationRelativeTo(null);


        JPanel connex = (JPanel) this.getContentPane();
        connex.setLayout(null);

        seCo.setBounds(250,10,400,60);
        seCo.setFont(new Font("Serif", Font.BOLD,50));
        id.setBounds(175,175,200,30);
        id.setFont(new Font("Serif",Font.PLAIN,30));
        idTexte.setBounds(450,175,200,30);
        idTexte.setFont(new Font("Serif",Font.PLAIN,20));
        mdp.setBounds(175,250,200,30);
        mdp.setFont(new Font("Serif",Font.PLAIN,30));
        mdpTexte.setBounds(450,250,200,30);
        mdpTexte.setFont(new Font("Serif",Font.PLAIN,20));
        connexionButton.setBounds(600,375,200,30);


        connexionButton.addActionListener(this::actionPerformed);

        connex.add(seCo);
        connex.add(id);
        connex.add(idTexte);
        connex.add(mdp);
        connex.add(mdpTexte);
        connex.add(connexionButton);

        setContentPane(connex);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        username = idTexte.getText();
        password = new String( mdpTexte.getPassword());
        int userId;
        if ((userId = connexionUtilisateur(username, password)) == -1) {
            mdpTexte.setText("");
            afficherMessageErreur();
        } else {
            setVisible(false);
            Messagerie mess = new Messagerie();
            mess.setVisible(true);
        }
    }
    private static boolean isValidString(String str){
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
    // Call verifiant le password et le username
    private static int connexionUtilisateur(String username, String password) {
        // TODO
        int id = -1;
        if (username.equals("") || password.equals(""))
            return id;
        else if(!isValidString(username))
            return -1;
        final String HOST = "127.0.0.1";
        final int PORT = 5000;

        try {
            StringBuilder sb = new StringBuilder();
            String strID;
            Socket sc = new Socket(HOST,PORT);
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            // Sending CX Token
            sb.append(username+"@"+password); //format: username@password
            String cxToken = sb.toString();
            out.writeUTF(cxToken);
            //Recieving response (NULL == wrong password)
            strID = in.readUTF();
            sc.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
    // Affiche le message d'erreur
    private static void afficherMessageErreur() {
        // TODO
        JOptionPane.showMessageDialog(new JFrame(), "Nom d'Utilisateur ou Mot De Pass invalide !", "Dialog",
                JOptionPane.ERROR_MESSAGE);
    }
}
