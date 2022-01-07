package affichage;

import client.ClientConnexionRequest;
import server.ServerInterface;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnexionWindow extends JFrame implements ActionListener {


    private final JTextField idTexte = new JTextField(10);
    private final JPasswordField mdpTexte = new JPasswordField(10);
    public ClientConnexionRequest clientConnexionRequest;

    public ConnexionWindow(){
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

        JLabel seCo = new JLabel("Se connecter !", SwingConstants.CENTER);
        seCo.setBounds(250,10,400,60);
        seCo.setFont(new Font("Serif", Font.BOLD,50));
        JLabel id = new JLabel("Identifiant : ");
        id.setBounds(175,175,200,30);
        id.setFont(new Font("Serif",Font.PLAIN,30));
        idTexte.setBounds(450,175,200,30);
        idTexte.setFont(new Font("Serif",Font.PLAIN,20));
        JLabel mdp = new JLabel("Mot de passe : ");
        mdp.setBounds(175,250,200,30);
        mdp.setFont(new Font("Serif",Font.PLAIN,30));
        mdpTexte.setBounds(450,250,200,30);
        mdpTexte.setFont(new Font("Serif",Font.PLAIN,20));
        JButton connexionButton = new JButton("Connexion");
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
        String username = idTexte.getText();
        String password = new String(mdpTexte.getPassword());

        if (!Utils.validCredentials(username, password)) {
            mdpTexte.setText("");
            Utils.warningWindow("Invalid Username or Password","Error Syntax");
        } else {
            this.clientConnexionRequest = new ClientConnexionRequest(username, password);

            if(clientConnexionRequest.connectedUser != null){
                if(clientConnexionRequest.connectedUser.isAdmin){
                    setVisible(false);
                    ServerInterface serverInterface = new ServerInterface(this.clientConnexionRequest);
                    serverInterface.setVisible(true);
                } else {
                    setVisible(false);
                    ChatWindow chatWindow = new ChatWindow(this.clientConnexionRequest);
                    chatWindow.setVisible(true);
                }
            }
        }
    }

}
