package front.affichage;

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
    private String identifiant;
    private String motDePasse;

    public FenetreConnexion(){
        //titre
        super("Se connecter");
        //taille fenetre
        setSize(900, 550);
        //taille non modifiable manuellement
        setResizable(true);
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
        identifiant = idTexte.getText();
        motDePasse = mdpTexte.getText();
        setVisible(false);
        
        Messagerie mess = new Messagerie();
        mess.setVisible(true);
        System.out.println("id = "+identifiant+"\n");
        System.out.println("mdp = "+motDePasse+"\n");
    }
}
