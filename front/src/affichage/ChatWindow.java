package affichage;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import client.ClientConnexionRequest;
import client.UserRequest;
import frontobjects.FrontGroup;
import frontobjects.FrontMessage;
import frontobjects.FrontThread;
import frontobjects.FrontUser;
import utils.Utils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;




/**
 *
 * @author Utilisateur
 */
public class ChatWindow extends JFrame {
    private FrontGroup groupSelectedNewFil;
    private String[] listGroupe;
    private String titleAdd;
    private static Map<FrontThread, JPanel> componentForTicket = new HashMap<>();
    private static DefaultMutableTreeNode rootTree = new DefaultMutableTreeNode("Groups");
    private boolean firstClick = true;

    private UserRequest userRequest;
    private static FrontUser connectedUser;
    public static FrontGroup allFrontGroup[];
    public static FrontGroup userFrontGroups[];
    public static FrontThread userThreads[];


    public ChatWindow(ClientConnexionRequest clientConnexion) {
        super("Chat: " + ClientConnexionRequest.connectedUser.toString());
        initComponents();
        this.userRequest = new UserRequest(clientConnexion);
        this.connectedUser = ClientConnexionRequest.connectedUser;
        this.userRequest.askThreadsFromServer(ClientConnexionRequest.connectedUser);
        this.userRequest.getUserGroups(ClientConnexionRequest.connectedUser);

        setLocationRelativeTo(null);
        FrameAjout.setLocation(this.getX(), this.getY());

        dimensionMaxSizeRight.height = panelMessage.getHeight() / 9;
        dimensionMaxSizeRight.width = (int) width;
        dimensionMinSizeRight.height = panelMessage.getHeight() / 9;
        dimensionMinSizeRight.width = panelMessage.getWidth();
        dimensionMinSizeLeft.height = panelTicket.getHeight() / 10;
        dimensionMinSizeLeft.width = panelTicket.getWidth();
        dimensionMaxSizeLeft.height = panelTicket.getHeight() / 10;
        dimensionMaxSizeLeft.width = (int) width;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        //frame qui pop quand on veut ajouter un fil
        FrameAjout = new JFrame();
        labelAjoutTitre = new JLabel();
        comboBoxGroup = new JComboBox<>();
        labelChoixGroupeAjout = new JLabel();
        zoneTextTitre = new JTextField();
        labelTitreAjout = new JLabel();
        labelMessagaAjout = new JLabel();
        scrollPaneMessageAjout = new JScrollPane();
        zoneTextNewMessage = new JTextArea();
        buttonAddNewFil = new JButton();
        userFrontGroups = new FrontGroup[1];
        allFrontGroup = new FrontGroup[1];
        userThreads = new FrontThread[1];


        splitPaneMessagerie = new JSplitPane();
        //panneau de gauche sur interface messagerie
        panelLeft = new JPanel();
        panelFil = new JPanel();
        labelFil = new JLabel();
        buttonAjoutTicket = new JButton();
        scrollPaneTicket = new JScrollPane();
        panelTicket = new JPanel();


        //panneau de droite sur interface messagerie
        panelRight = new JPanel();
        panelListMessage = new JPanel();
        labelTitreTicket = new JLabel();
        scrollPaneListMessage = new JScrollPane();
        panelMessage = new JPanel();
        panelEcrireMessage = new JPanel();
        panelBorderMessage = new JPanel();
        zoneTexteMessage = new JTextField();

        labelAjoutTitre.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelAjoutTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelAjoutTitre.setText("Creation d'un nouveau fil de discussion");
        labelAjoutTitre.setHorizontalTextPosition(SwingConstants.CENTER);

        //TO DO remplacer valeur item par string de tous les groupes
        comboBoxGroup.setModel(new DefaultComboBoxModel<>());


        labelChoixGroupeAjout.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        labelChoixGroupeAjout.setHorizontalAlignment(SwingConstants.CENTER);
        labelChoixGroupeAjout.setText("Choix du groupe : ");

        zoneTextTitre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        labelTitreAjout.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        labelTitreAjout.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitreAjout.setText("Titre du fil de discussion : ");

        labelMessagaAjout.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        labelMessagaAjout.setHorizontalAlignment(SwingConstants.CENTER);
        labelMessagaAjout.setText("Message :");

        zoneTextNewMessage.setColumns(20);
        zoneTextNewMessage.setRows(5);
        scrollPaneMessageAjout.setViewportView(zoneTextNewMessage);


        buttonAddNewFil.setText("Ajouter");
        buttonAddNewFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        FrameAjout.setResizable(false);
        GroupLayout frameAjoutFilLayout = new GroupLayout(FrameAjout.getContentPane());
        FrameAjout.getContentPane().setLayout(frameAjoutFilLayout);
        frameAjoutFilLayout.setHorizontalGroup(
                frameAjoutFilLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelAjoutTitre, GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
                        .addGroup(frameAjoutFilLayout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addGroup(frameAjoutFilLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollPaneMessageAjout)
                                        .addComponent(labelChoixGroupeAjout, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(comboBoxGroup, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(labelMessagaAjout, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(zoneTextTitre))
                                .addGap(95, 95, 95))
                        .addGroup(GroupLayout.Alignment.TRAILING, frameAjoutFilLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonAddNewFil, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(frameAjoutFilLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(frameAjoutFilLayout.createSequentialGroup()
                                        .addGap(95, 95, 95)
                                        .addComponent(labelTitreAjout, GroupLayout.PREFERRED_SIZE, 466, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(95, Short.MAX_VALUE)))
        );
        frameAjoutFilLayout.setVerticalGroup(
                frameAjoutFilLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(frameAjoutFilLayout.createSequentialGroup()
                                .addComponent(labelAjoutTitre, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(zoneTextTitre, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(labelChoixGroupeAjout)
                                .addGap(18, 18, 18)
                                .addComponent(comboBoxGroup, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(labelMessagaAjout, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(scrollPaneMessageAjout, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(buttonAddNewFil)
                                .addContainerGap())
                        .addGroup(frameAjoutFilLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(frameAjoutFilLayout.createSequentialGroup()
                                        .addGap(100, 100, 100)
                                        .addComponent(labelTitreAjout)
                                        .addContainerGap(432, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        splitPaneMessagerie.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        panelFil.setBackground(new Color(132, 169, 140));

        labelFil.setBackground(new Color(132, 169, 140));
        labelFil.setFont(new Font("Tahoma", 1, 18)); // NOI18N
        labelFil.setHorizontalAlignment(SwingConstants.CENTER);
        labelFil.setText("Fils de discussion");

        GroupLayout panelFilLayout = new GroupLayout(panelFil);
        panelFil.setLayout(panelFilLayout);
        panelFilLayout.setHorizontalGroup(
                panelFilLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelFil, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );
        panelFilLayout.setVerticalGroup(
                panelFilLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelFilLayout.createSequentialGroup()
                                .addComponent(labelFil, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        buttonAjoutTicket.setBackground(new java.awt.Color(102, 102, 102));
        buttonAjoutTicket.setIcon(new ImageIcon(getClass().getResource("/icons/plus.png")));
        buttonAjoutTicket.setContentAreaFilled(false);
        buttonAjoutTicket.setHorizontalAlignment(SwingConstants.RIGHT);
        buttonAjoutTicket.setOpaque(true);
        buttonAjoutTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAjoutTicketActionPerformed(evt);
            }
        });

        treeTicket = new JTree(rootTree);
        ImageIcon leafIcon = new ImageIcon(getClass().getResource("/icons/iconFil.png"));
        ImageIcon nodeIcon = new ImageIcon(getClass().getResource("/icons/icons8-user-account-50 (1).png"));
        if (leafIcon != null) {
            DefaultTreeCellRenderer renderer =
                    new DefaultTreeCellRenderer();
            renderer.setLeafIcon(leafIcon);
            renderer.setIconTextGap(0);
            renderer.setClosedIcon(nodeIcon);
            renderer.setOpenIcon(nodeIcon);
            treeTicket.setCellRenderer(renderer);
        }
        treeTicket.setBackground(new java.awt.Color(102, 102, 102));
        treeTicket.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        treeTicket.setRootVisible(false);
        scrollPaneTicket.setViewportView(treeTicket);
        treeTicket.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                ticketSelected(e);
            }
        });
        treeTicket.setAutoscrolls(true);

        GroupLayout panelLeftLayout = new GroupLayout(panelLeft);
        panelLeft.setLayout(panelLeftLayout);
        panelLeftLayout.setHorizontalGroup(
                panelLeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panelFil, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonAjoutTicket, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelLeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(scrollPaneTicket, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
        );
        panelLeftLayout.setVerticalGroup(
                panelLeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelLeftLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(panelFil, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 552, Short.MAX_VALUE)
                                .addComponent(buttonAjoutTicket))
                        .addGroup(panelLeftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(panelLeftLayout.createSequentialGroup()
                                        .addGap(75, 75, 75)
                                        .addComponent(scrollPaneTicket, GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                                        .addGap(52, 52, 52)))
        );

        splitPaneMessagerie.setLeftComponent(panelLeft);

        panelRight.setBackground(new java.awt.Color(102, 102, 102));
        panelRight.setToolTipText("");

        panelListMessage.setBackground(new java.awt.Color(102, 102, 102));

        labelTitreTicket.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelTitreTicket.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitreTicket.setText("Aucun ticket selectionne");

        panelMessage.setAutoscrolls(true);
        panelMessage.setLayout(new BoxLayout(panelMessage, BoxLayout.Y_AXIS));
        scrollPaneListMessage.setViewportView(panelMessage);
        scrollPaneListMessage.setAutoscrolls(true);
        panelMessage.setVisible(false);

        panelBorderMessage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        zoneTexteMessage.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        zoneTexteMessage.setText("Ecrire un message dans \"Titre ticket selectionne\"");
        zoneTexteMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoneTexteMessageActionPerformed(evt);
            }

        });
        zoneTexteMessage.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                zoneTexteMessageMouseClicked(evt);
            }
        });


        GroupLayout panelBorderMessageLayout = new GroupLayout(panelBorderMessage);
        panelBorderMessage.setLayout(panelBorderMessageLayout);
        panelBorderMessageLayout.setHorizontalGroup(
                panelBorderMessageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(zoneTexteMessage)
        );
        panelBorderMessageLayout.setVerticalGroup(
                panelBorderMessageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(zoneTexteMessage, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
        );

        GroupLayout panelEcrireMessageLayout = new GroupLayout(panelEcrireMessage);
        panelEcrireMessage.setLayout(panelEcrireMessageLayout);
        panelEcrireMessageLayout.setHorizontalGroup(
                panelEcrireMessageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelEcrireMessageLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panelBorderMessage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        panelEcrireMessageLayout.setVerticalGroup(
                panelEcrireMessageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, panelEcrireMessageLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelBorderMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );


        panelEcrireMessage.setVisible(false);

        GroupLayout panelListMessageLayout = new GroupLayout(panelListMessage);
        panelListMessage.setLayout(panelListMessageLayout);
        panelListMessageLayout.setHorizontalGroup(
                panelListMessageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(labelTitreTicket, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelListMessageLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPaneListMessage)
                                .addContainerGap())
                        .addComponent(panelEcrireMessage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelListMessageLayout.setVerticalGroup(
                panelListMessageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(panelListMessageLayout.createSequentialGroup()
                                .addComponent(labelTitreTicket, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPaneListMessage)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panelEcrireMessage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout panelRightLayout = new GroupLayout(panelRight);
        panelRight.setLayout(panelRightLayout);
        panelRightLayout.setHorizontalGroup(
                panelRightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 1008, Short.MAX_VALUE)
                        .addGroup(panelRightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(panelListMessage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRightLayout.setVerticalGroup(
                panelRightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 674, Short.MAX_VALUE)
                        .addGroup(panelRightLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(panelListMessage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        splitPaneMessagerie.setRightComponent(panelRight);

        getContentPane().add(splitPaneMessagerie, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>

    private void buttonAjoutTicketActionPerformed(java.awt.event.ActionEvent evt) {
        FrameAjout.pack();
        FrameAjout.setVisible(true);
    }

    private void ticketSelected(TreeSelectionEvent evt) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeTicket.getLastSelectedPathComponent();



        if (node == null) {
            return;
        }
        if (!node.isLeaf()) {
            for (int k = 0; k < node.getChildCount(); k++) {
                if (node.getChildAt(k).toString().length() > 30) {
                    splitPaneMessagerie.setDividerLocation(this.getWidth() / 2);
                }
            }
            System.out.println("je rentre la");
            labelTitreTicket.setText("Aucun ticket selectionne");
            panelEcrireMessage.setVisible(false);
            scrollPaneListMessage.getComponent(0).setVisible(false);
        }
        if (node.isLeaf()) {
            firstClick = true;
            labelTitreTicket.setText(node.toString());
            panelEcrireMessage.setVisible(true);
            zoneTexteMessage.setText("Ecrire un message dans " + node);
            System.out.println("hehe je rentre la");

            scrollPaneListMessage.getComponent(0).setVisible(true);
            scrollPaneListMessage.setViewportView(componentForTicket.get(node.getUserObject()));

            labelTitreTicket.setText(node.toString());

        }

    }

    private void setMessageColor(FrontMessage message, JTextPane jTextPane){
        if (message.status.equals("NOT_SENT")) {
            jTextPane.setBackground(new java.awt.Color(125, 125, 125));
        }
        if (message.status.equals("NOT_SEEN")) {
            jTextPane.setBackground(new java.awt.Color(255, 50, 50));
        }
        if (message.status.equals("HALF_SEEN")) {
            jTextPane.setBackground(new java.awt.Color(255, 100, 0));
        }
        if (message.status.equals("SEEN")) {
            jTextPane.setBackground(new java.awt.Color(0, 255, 0));
        }
    }

    public static void updateCreatedMessage(FrontThread frontThread){
        FrontMessage frontMessage = frontThread.messages.get(0);
        JScrollPane scrollPaneNewMessage = new JScrollPane();
        JTextPane textPaneNewMessage = new JTextPane();
        //textPaneNewMessage.setText(frontMessage.user.toString() + ", " + frontMessage.date.format(new Date(messageToAdd.date)) + "\n\n" + messageAdd);

    }

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {
        //*---Date format--*//*
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy [HH:mm]");
        Date date = new Date();
        String dayStr = dayFormat.format(date);
        //*----------------*//*

        titleAdd = zoneTextTitre.getText();
        String messageAdd = zoneTextNewMessage.getText();

        //avoir le groupe choisi pour ajouter un fil
        groupSelectedNewFil = (FrontGroup) comboBoxGroup.getSelectedItem();
        //System.out.println("groupe choisi :"+groupSelectedNewFil);


        zoneTextTitre.setText("");
        zoneTextNewMessage.setText("");

        if (titleAdd.length() == 0) {
            Utils.warningWindow("Missing Fields in Title of new Ticket!", "Error Syntax");
        } else {
            if (messageAdd.length() == 0) {
                Utils.warningWindow("Missing Fields in Message of new Ticket!", "Error Syntax");
            } else {
                FrameAjout.setVisible(false);
                /*-------------Local front thread Creation---------------*/
                FrontThread threadToAdd = new FrontThread();
                threadToAdd.title = titleAdd;
                FrontMessage messageToAdd = new FrontMessage();
                messageToAdd.content = messageAdd;
                messageToAdd.status = "NOT_SENT";
                messageToAdd.date = date.getTime();
                List<FrontMessage> listMessageToAdd = new ArrayList<>();
                listMessageToAdd.add(messageToAdd);
                threadToAdd.messages=listMessageToAdd;
                threadToAdd.messages.add(messageToAdd);
                /*--------------Local front thread Setup-------------*/

                JPanel panelAjout = new JPanel();
                panelAjout.setAutoscrolls(true);
                panelAjout.setLayout(new BoxLayout(panelAjout, BoxLayout.Y_AXIS));

                JScrollPane scrollPaneNewMessage = new JScrollPane();
                JTextPane textPaneNewMessage = new JTextPane();
                textPaneNewMessage.setText(connectedUser.toString() + ", " + dayFormat.format(new Date(messageToAdd.date)) + "\n\n" + messageAdd);
                textPaneNewMessage.setEditable(false);
                scrollPaneNewMessage.setViewportView(textPaneNewMessage);

                scrollPaneNewMessage.setMinimumSize(dimensionMinSizeRight);
                scrollPaneNewMessage.setMaximumSize(dimensionMaxSizeRight);
                scrollPaneNewMessage.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

                this.setMessageColor(messageToAdd, textPaneNewMessage);

                panelAjout.add(scrollPaneNewMessage);
                //panelAjout.updateUI();
                if (componentForTicket.containsKey(threadToAdd)) {
                    //JOptionPane.showMessageDialog(new JFrame(), "Ce ticket existe déjà dans "+groupSelectedNewFil, "Attention !", JOptionPane.WARNING_MESSAGE);
                    JOptionPane.showMessageDialog(new JFrame(), "Ce ticket existe déjà!", "Attention !", JOptionPane.WARNING_MESSAGE);

                } else {
                    componentForTicket.put(threadToAdd, panelAjout);

                    int indexGroup;
                    boolean existInTree = false;
                    for (int k = 0; k < rootTree.getChildCount(); k++) {
                        if (groupSelectedNewFil.toString().equals(rootTree.getChildAt(k).toString())) {
                            System.out.println("je sais qu'il existe");
                            existInTree = true;
                            indexGroup=k;
                            k = rootTree.getChildCount();
                        }
                    }
                    if (existInTree==false){

                        DefaultMutableTreeNode newGroupToAdd = new DefaultMutableTreeNode(groupSelectedNewFil);
                        rootTree.add(newGroupToAdd);
                        indexGroup=rootTree.getIndex(newGroupToAdd);

                        DefaultTreeModel model = (DefaultTreeModel) treeTicket.getModel();
                        DefaultMutableTreeNode racine = (DefaultMutableTreeNode) model.getRoot();

                        DefaultMutableTreeNode gp = (DefaultMutableTreeNode) model.getChild(racine, indexGroup);
                        DefaultMutableTreeNode newTicket = new DefaultMutableTreeNode(threadToAdd);
                        newTicket.setAllowsChildren(false);
                        model.insertNodeInto(newTicket, gp, gp.getChildCount());
                        model.reload();
                        TreePath path = new TreePath(newTicket);
                        treeTicket.setSelectionPath(path);


                    }
                    else {
                        System.out.println("il existe");

                        int indexGroupInsert=0;
                        for (int k = 0; k < rootTree.getChildCount(); k++) {
                            if (groupSelectedNewFil.toString().equals(rootTree.getChildAt(k).toString())) {

                                indexGroupInsert=k;
                                k = rootTree.getChildCount();
                            }
                        }

                        DefaultTreeModel model = (DefaultTreeModel) treeTicket.getModel();
                        DefaultMutableTreeNode racine = (DefaultMutableTreeNode) model.getRoot();
                        DefaultMutableTreeNode gp = (DefaultMutableTreeNode) model.getChild(racine, indexGroupInsert);
                        DefaultMutableTreeNode ticket = new DefaultMutableTreeNode(threadToAdd);
                        ticket.setAllowsChildren(false);
                        model.insertNodeInto(ticket, gp, gp.getChildCount());
                        model.reload();
                        TreePath path = new TreePath(ticket);
                        treeTicket.setSelectionPath(path);

                    }
                    this.userRequest.createThread(groupSelectedNewFil.id,titleAdd,messageAdd);
                }
                zoneTexteMessage.setText("");
            }
        }

    }

    private void zoneTexteMessageMouseClicked(MouseEvent evt){
        if (firstClick==true) {
            zoneTexteMessage.setText("");
            firstClick=false;
        }
    }

    private void zoneTexteMessageActionPerformed(ActionEvent evt) {

        DefaultMutableTreeNode selectedItemTree = (DefaultMutableTreeNode) treeTicket.getLastSelectedPathComponent();
        FrontThread threadSelected = (FrontThread) selectedItemTree.getUserObject();



        if (zoneTexteMessage.getText().length()==0){
            Utils.warningWindow("Missing Fields in Message to send","Error Syntax");
        } else {
            if (selectedItemTree != null) {
                //*---Date format--*//*
                SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy [HH:mm]");
                Date date = new Date();
                String dayStr = dayFormat.format(date);
                //*----------------*//*

                //creation affichage new message local
                JScrollPane scrollPaneNewMessage = new JScrollPane();
                JTextPane textPaneNewMessage = new JTextPane();
                textPaneNewMessage.setText(this.connectedUser.toString() + ", " + dayStr + "\n\n" + zoneTexteMessage.getText());
                textPaneNewMessage.setEditable(false);
                scrollPaneNewMessage.setViewportView(textPaneNewMessage);

                FrontMessage mess = new FrontMessage(23,connectedUser,zoneTexteMessage.getText(), date.getTime(),"NOT_SENT");
                threadSelected.messages.add(mess);

                if (mess.status.equals("NOT_SENT")) {
                    textPaneNewMessage.setBackground(new java.awt.Color(125, 125, 125));
                }
                if (mess.status.equals("NOT_SEEN")) {
                    textPaneNewMessage.setBackground(new java.awt.Color(255, 50, 50));
                }
                if (mess.status.equals("HALF_SEEN")) {
                    textPaneNewMessage.setBackground(new java.awt.Color(255, 100, 0));
                }
                if (mess.status.equals("SEEN")) {
                    textPaneNewMessage.setBackground(new java.awt.Color(0, 255, 0));
                }

                scrollPaneNewMessage.setMinimumSize(dimensionMinSizeRight);
                scrollPaneNewMessage.setMaximumSize(dimensionMaxSizeRight);
                scrollPaneNewMessage.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

                componentForTicket.get(threadSelected).add(scrollPaneNewMessage);
                componentForTicket.get(threadSelected).updateUI();


                firstClick = true;
                zoneTexteMessage.setText("");
            }

        }
    }

    public static void fillTree() {
        if(userThreads.length > 0) {
            for (FrontGroup group : userFrontGroups) {
                addGroupToRoot(group);
            }
        }
    }


    private static void addGroupToRoot(FrontGroup node){
        /*---Date format--*/
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy [HH:mm]");
        Date date = new Date();
        String dayStr = dayFormat.format(date);
        /*----------------*/
        DefaultMutableTreeNode newGroup = new DefaultMutableTreeNode(node);
        DefaultTreeModel model = (DefaultTreeModel) treeTicket.getModel();
        DefaultMutableTreeNode racine = (DefaultMutableTreeNode) model.getRoot();



        for(FrontThread frontThread : userThreads) {
            if (frontThread.group.id == node.id) {
                DefaultMutableTreeNode ticket = new DefaultMutableTreeNode(frontThread);
                ticket.setAllowsChildren(false);
                newGroup.add(ticket);
                int j = 0;
                for (FrontMessage mess : frontThread.messages) {
                    JPanel panelAjout = new JPanel();
                    panelAjout.setAutoscrolls(true);
                    panelAjout.setLayout(new BoxLayout(panelAjout, BoxLayout.Y_AXIS));

                    JScrollPane scrollPaneNewMessage = new JScrollPane();
                    JTextPane textPaneNewMessage = new JTextPane();

                    textPaneNewMessage.setEditable(false);
                    textPaneNewMessage.setText(mess.user + ", " + dayStr + "\n\n" + mess.content);

                    if (mess.status.equals("NOT_SENT")) {
                        textPaneNewMessage.setBackground(new java.awt.Color(125, 125, 125));
                    }

                    if (mess.status.equals("NOT_SEEN")) {
                        textPaneNewMessage.setBackground(new java.awt.Color(255, 50, 50));
                    }
                    if (mess.status.equals("HALF_SEEN")) {
                        textPaneNewMessage.setBackground(new java.awt.Color(255, 100, 0));
                    }
                    if (mess.status.equals("SEEN")) {
                        textPaneNewMessage.setBackground(new java.awt.Color(0, 255, 0));
                    }


                    scrollPaneNewMessage.setMinimumSize(dimensionMinSizeRight);
                    scrollPaneNewMessage.setMaximumSize(dimensionMaxSizeRight);
                    scrollPaneNewMessage.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
                    scrollPaneNewMessage.setViewportView(textPaneNewMessage);

                    panelAjout.add(scrollPaneNewMessage);

                    panelAjout.updateUI();
                    /*zoneTexteMessage.setText("Ecrire un message dans " + frontThread.toString());
                    labelTitreTicket.setText(frontThread.toString());*/
                    componentForTicket.putIfAbsent(frontThread, panelAjout);
                    componentForTicket.get(frontThread).add(scrollPaneNewMessage);
                    //scrollPaneListMessage.setViewportView(componentForTicket.get(frontThread));

                }

            }

            if (newGroup.getChildCount() > 0) {
                racine.add(newGroup);

            }

        }
        model.reload();
        //panelEcrireMessage.setVisible(true);

    }


    // Variables declaration - do not modify
    private JButton buttonAddNewFil;
    private JButton buttonAjoutTicket;
    public static JComboBox<FrontGroup> comboBoxGroup;
    private JFrame FrameAjout;
    private JLabel labelAjoutTitre;
    private JLabel labelChoixGroupeAjout;
    private JLabel labelTitreAjout;
    private JLabel labelMessagaAjout;
    private static JScrollPane scrollPaneListMessage;
    private JScrollPane scrollPaneMessageAjout;
    private JSplitPane splitPaneMessagerie;
    private JLabel labelFil;
    private static JLabel labelTitreTicket;
    private JPanel panelBorderMessage;
    private static JPanel panelEcrireMessage;
    private JPanel panelFil;
    private JPanel panelLeft;
    private JPanel panelListMessage;
    private JPanel panelMessage;
    private JPanel panelRight;
    private JPanel panelTicket;
    private JScrollPane scrollPaneTicket;
    private JTextArea zoneTextNewMessage;
    private JTextField zoneTextTitre;
    private static JTextField zoneTexteMessage;
    private Dimension dimensionMaxSizeLeft=new Dimension();
    private final Dimension dimensionMinSizeLeft=new Dimension();
    private static final Dimension dimensionMinSizeRight=new Dimension();
    private static final Dimension dimensionMaxSizeRight=new Dimension();
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final double width = screenSize.getWidth();
    private final double height = screenSize.getHeight();
    private static JTree treeTicket;
    // End of variables declaration
}

