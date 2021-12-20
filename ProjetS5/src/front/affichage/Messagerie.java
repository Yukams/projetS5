package front.affichage;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class Messagerie extends JFrame {
    private DefaultMutableTreeNode racine= new DefaultMutableTreeNode("GROUPE");
    private DefaultMutableTreeNode peda= new DefaultMutableTreeNode("Groupe Pedagogique");
    private DefaultMutableTreeNode service= new DefaultMutableTreeNode("Groupe Services");
    private DefaultMutableTreeNode sport= new DefaultMutableTreeNode("Groupe Sport");
    private DefaultMutableTreeNode td= new DefaultMutableTreeNode("Groupe TD");

    public Messagerie(){
        super("Messagerie");
        Dimension currentScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //taille fenetre
        setSize(currentScreenSize);
        //taille non modifiable manuellement
        setResizable(true);
        //Click sur croix ferme la fenetre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Centrer la fenetre par rapport à l'ecran
        setLocationRelativeTo(null);

        JPanel panneau = (JPanel) this.getContentPane();

        racine.add(peda);
        racine.add(service);
        racine.add(sport);
        racine.add(td);
        JTree arbre= new JTree(racine);
        panneau.add(arbre);
        setContentPane(panneau);
    }
}
