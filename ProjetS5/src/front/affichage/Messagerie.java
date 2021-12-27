package front.affichage;

import front.fil.FilDeDiscussion;
import front.fil.Message;
import front.frontobjects.FrontUser;
import front.groups.Groupe;
import front.groups.GroupeType;
import front.users.Utilisateur;

import javax.swing.*;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;
import java.util.ArrayList;

import java.util.List;

public class Messagerie extends JFrame {

    public Messagerie(FrontUser frontUser){
        super("Messagerie: "+frontUser.name +" "+frontUser.surname);

        Dimension currentScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Construction of Tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultMutableTreeNode peda = new DefaultMutableTreeNode("Groupe Pedagogique");
        root.add(peda);
        DefaultMutableTreeNode service = new DefaultMutableTreeNode("Groupe Services");
        root.add(service);
        DefaultMutableTreeNode sport = new DefaultMutableTreeNode("Groupe Sport");
        root.add(sport);
        DefaultMutableTreeNode td = new DefaultMutableTreeNode("Groupe TD");

        root.add(td);
        JTree tree= new JTree(root);
        tree.setBackground(Color.white);

        tree.setRootVisible(false);

        //test fil de discuission

        Utilisateur utiTest = new Utilisateur("soulie","gabriel");
        List<Utilisateur> listUtiTest= new ArrayList<>();
        listUtiTest.add(utiTest);
        Groupe groupTest = new Groupe("Groupe Pedagogique",1,GroupeType.PEDAGOGIQUE,listUtiTest);
        Message messTest = new Message(utiTest,1,"Je suis un test UwU","22/12/2021","RED");
        Message messTest2 = new Message(utiTest,1,"Je suis un test mais deuxieme fois UwU","22/12/2021","RED");
        List<Message> listTest= new ArrayList<>();
        listTest.add(messTest);
        listTest.add(messTest2);
        FilDeDiscussion test1 = new FilDeDiscussion(1,"Probleme tuyau", groupTest,utiTest,listTest);

        DefaultMutableTreeNode filTest = new DefaultMutableTreeNode(test1,false);
        peda.add(filTest);




        JTextPane text = new JTextPane();
        text.setText("Aucun ticket selectionne !!!");
        text.setEditable(false);
        StyledDocument docText = text.getStyledDocument();
        MutableAttributeSet centerText= new SimpleAttributeSet();
        StyleConstants.setAlignment(centerText,StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(centerText,30);
        StyleConstants.setBold(centerText,true);
        docText.setParagraphAttributes(0,0,centerText,true);

        JTextPane group = new JTextPane();
        group.setText("GROUPE");
        group.setEditable(false);
        group.setBackground(new Color(132,169,140));
        StyledDocument doc = group.getStyledDocument();
        MutableAttributeSet centerGroup= new SimpleAttributeSet();
        StyleConstants.setAlignment(centerGroup,StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontSize(centerGroup,15);
        doc.setParagraphAttributes(0,0,centerGroup,true);



        JSplitPane ar = new JSplitPane(JSplitPane.VERTICAL_SPLIT,group,tree);
        ar.setDividerSize(0);


        JSplitPane splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,ar,new JPanel());

        splitPane.setRightComponent(text);



        splitPane.getRightComponent().setBackground(Color.gray);

        /*tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (node==null){
                    return;
                }
                if (node.isLeaf()){
                    JPanel panel=new JPanel();
                    FilDeDiscussion f = (FilDeDiscussion) node.getUserObject();
                    BorderLayout layout = new BorderLayout();
                    //For the title
                    //panel.setLayout(layout);
                    JTextPane title = new JTextPane();
                    title.setText(f.getTitre());
                    title.setEditable(false);
                    StyledDocument docText = title.getStyledDocument();
                    MutableAttributeSet centerTitle= new SimpleAttributeSet();
                    StyleConstants.setAlignment(centerTitle,StyleConstants.ALIGN_CENTER);
                    StyleConstants.setFontSize(centerTitle,30);
                    StyleConstants.setBold(centerTitle,true);
                    docText.setParagraphAttributes(0,0,centerTitle,true);
                    title.setBackground(Color.gray);
                    panel.add(title,BorderLayout.PAGE_START);

                    //For the writing part
                    JTextField enterMess = new JTextField("Envoyer un message dans "+f.getTitre());
                    enterMess.setFont(new Font("Serif", Font.ITALIC, 10));
                    panel.add(enterMess,BorderLayout.PAGE_END);

                    //For the messages
                    JList<Message> messList= (JList<Message>) f.getMessages();
                    JScrollPane messScroll = new JScrollPane(messList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                    panel.add(messScroll,BorderLayout.CENTER);
                    //splitPane.setRightComponent(panel);
                }
            }
        });*/

        this.setLayout(new BorderLayout());
        this.add(splitPane,BorderLayout.CENTER);

        setSize(currentScreenSize);

        /* Object[][] donnees = {
                {"Johnathan", "Sykes", Color.red, true, "TENNIS"},
                {"Nicolas", "Van de Kampf", Color.black, true, "FOOTBALL"},
                {"Damien", "Cuthbert", Color.cyan, true, "RIEN"},
                {"Corinne", "Valance", Color.blue, false, "NATATION"},
                {"Emilie", "Schrödinger", Color.magenta, false, "FOOTBALL"},
                {"Delphine", "Duke", Color.yellow, false, "TENNIS"},
                {"Eric", "Trump", Color.pink, true, "FOOTBALL"},
        };
        String[] entetes = {"Prénom", "Nom", "Couleur favorite", "Homme", "Sport"};
        JTable tableau = new JTable(donnees, entetes);
        getContentPane().add(tableau.getTableHeader(), BorderLayout.NORTH);
        getContentPane().add(tableau, BorderLayout.CENTER);

        getContentPane().add(text, BorderLayout.SOUTH);

        pack();*/

        /*DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode racine= (DefaultMutableTreeNode) model.getRoot();

        model.insertNodeInto(new DefaultMutableTreeNode("test"),racine,2);
        DefaultMutableTreeNode gp = (DefaultMutableTreeNode) model.getChild(racine,0);
        model.insertNodeInto(new DefaultMutableTreeNode("enfant"),gp,gp.getChildCount());
        model.insertNodeInto(new DefaultMutableTreeNode("enfant2"),gp,gp.getChildCount());
        model.insertNodeInto(new DefaultMutableTreeNode("enfant3"),gp,gp.getChildCount());
        model.insertNodeInto(new DefaultMutableTreeNode("enfant4"),gp,gp.getChildCount());

        DefaultMutableTreeNode groupep=new DefaultMutableTreeNode("Groupe pedagogique(2)");
        //pour mettre à jour en fonction des messages non lus
        int nbenfant= gp.getChildCount();
        for (int i=0; i<nbenfant;i++) {
            int j=0;

            groupep.add((MutableTreeNode) gp.getChildAt(j));

        }



        model.insertNodeInto(groupep,racine,0);
        model.removeNodeFromParent(gp);

        model.reload();*/
        //panneau.add(arbre,BorderLayout.WEST);

        //setContentPane(panneau);
        //arbre.setOpaque(false);

    }
}
