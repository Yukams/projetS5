package front.affichage;

import front.client.ClientConnexion;
import front.frontobjects.FrontUser;
import front.main.mainFront;
import front.server.ServerInterface;
import front.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetreConnexion extends JFrame implements ActionListener {


    private JLabel seCo = new JLabel("Se connecter !",SwingConstants.CENTER);
    private JLabel id = new JLabel("Identifiant : ");
    private JLabel mdp = new JLabel("Mot de passe : ");
    private JTextField idTexte = new JTextField(10);
    private JPasswordField mdpTexte = new JPasswordField(10);
    private JButton connexionButton = new JButton("Connexion");
    private String username;
    private String password;
    private ClientConnexion clientConnexion;
    private FrontUser frontUser;

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

        if (Utils.credentialsNature(username, password) == -1) {
            mdpTexte.setText("");
            Utils.syntaxErrorMessage();
        } else if (Utils.credentialsNature(username, password) == 1) {
            setVisible(false);
            ServerInterface serverInterface = new ServerInterface();
            serverInterface.setVisible(true);
        } else {
            this.clientConnexion = new ClientConnexion(username, password);
            this.frontUser = clientConnexion.getFrontUser();
            if(this.frontUser != null){
                setVisible(false);
                Messagerie mess = new Messagerie(this.frontUser);
                mess.setVisible(true);
            }
        }
    }

}
