package front.affichage;

import front.client.Client;
import front.main.mainFront;
import front.users.Utilisateur;
import front.utils.Utils;
import server.AdminInterface;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
    private Utils utils = new Utils();
    private Client client;

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

        if (credentialsNature(username, password) == -1) {
            mdpTexte.setText("");
            utils.messageErrorSyntax();
        } else if (credentialsNature(username, password) == 1){
            setVisible(false);
            AdminInterface adminInterface = new AdminInterface();
        }
        else {
            this.client = new Client(username, password);
            setVisible(false);
            Messagerie mess = new Messagerie();
            mess.setVisible(true);
        }
    }
    // Check credentials nature
    private int credentialsNature(String username, String password) {
        if(username.equals("") || password.equals("") ||!utils.isValidString(username)) return -1;
        else if(username.equals("root") && password.equals("root")) return 1;
        return 0;

    }

    // Connection
    /*private static int connect(String username, String password){
        final String HOST = "127.0.0.1";

        int recvId = 0;
        try (Socket sc = new Socket(HOST, Server.PORT)){
            StringBuilder authSB = new StringBuilder();
            ObjectInputStream in = new ObjectInputStream(sc.getInputStream()); //ce que je reçois
            DataOutputStream out = new DataOutputStream(sc.getOutputStream()); ////ce que j'envoie  ======> DONC COTE SERVER: L'inverse
            // Serealization
            authSB.append("{username:"+username+",password:"+password+"}"); //format(json): username:usrnm,password:pswrd
            String authToken = authSB.toString();
            // Sending token
            out.writeUTF(authToken);
            //Recieving response (null == wrong password)
            Utilisateur usr = (Utilisateur)in.readObject();
            recvId = usr.getId();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return recvId;
    }*/
}
