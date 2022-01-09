package affichage;

import client.ClientConnexionRequest;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import server.ServerInterface;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConnexionWindow extends JFrame implements ActionListener {

    private static JFrame reconnectionFrame;
    private final JTextField idTexte = new JTextField(10);
    private final JPasswordField mdpTexte = new JPasswordField(10);
    private JButton connexionButton;
    public ClientConnexionRequest clientConnexionRequest;
    public static ServerInterface serverInterface;
    public static ChatWindow chatWindow;
    public static String username;
    public static String password;


    public ConnexionWindow(){
        /*------------------------------Connexion Window------------------------------*/
        super("Se connecter");
        //taille fenetre
        setSize(900, 550);
        //taille non modifiable manuellement
        setResizable(false);
        //Click sur croix ferme la fenetre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Centrer la fenetre par rapport à l'ecran
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/connexionIcon.png"));
        this.setIconImage(icon.getImage());
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
        connexionButton = new JButton("Connexion");
        connexionButton.setBounds(600,375,200,30);


        connexionButton.addActionListener(this::actionPerformed);

        connex.add(seCo);
        connex.add(id);
        connex.add(idTexte);
        connex.add(mdp);
        connex.add(mdpTexte);
        connex.add(connexionButton);

        setContentPane(connex);

        /*------------------------------Reconnection Window------------------------------*/
        reconnectionFrame = new JFrame("Connexion Lost");
        JPanel mainPanel = new JPanel();
        /* WINDOW SETEUP */
        reconnectionFrame.setSize(300,175);
        reconnectionFrame.setAlwaysOnTop(true);
        reconnectionFrame.setResizable(false);

        mainPanel.setLayout(new AbsoluteLayout());
        //TEXT SETUP
        JLabel label = new JLabel("Trying to reconnect...");
        label.setFont(new Font("Segoe UI Semibold", 2, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(label,new AbsoluteConstraints(50,5,180,50));
        // BUTTON SETUP
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(147, 3, 46));
        cancelButton.setFont(new Font("Candara", 3, 22));
        cancelButton.setForeground(new Color(255, 255, 255));
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        mainPanel.add(cancelButton,new AbsoluteConstraints(90,60,100,50));
        // MAIN PANEL SETUP
        GroupLayout layout = new GroupLayout(reconnectionFrame.getContentPane());
        reconnectionFrame.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        // Set close operation
        reconnectionFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        reconnectionFrame.setLocationRelativeTo(null);
        reconnectionFrame.setEnabled(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        username = idTexte.getText();
        password = new String(mdpTexte.getPassword());

        if (!Utils.validCredentials(username, password)) {
            mdpTexte.setText("");
            Utils.warningWindow("Invalid Username or Password","Error Syntax");
        } else {
            this.clientConnexionRequest = new ClientConnexionRequest(username, password);
            if(ClientConnexionRequest.connectedUser != null){
                if(ClientConnexionRequest.connectedUser.isAdmin){
                    setVisible(false);
                    serverInterface = new ServerInterface(this.clientConnexionRequest);
                    serverInterface.setVisible(true);
                } else {
                    setVisible(false);
                    chatWindow = new ChatWindow(this.clientConnexionRequest);
                    chatWindow.setVisible(true);
                }
            }
        }
    }

    public void reconnect() throws InterruptedException {
        ClientConnexionRequest.connected = false;
        ClientConnexionRequest.isReconnecting = true;
        reconnectionFrame.setEnabled(true);
        reconnectionFrame.setVisible(true);
        // Disable frame behind
        this.setEnabled(false);
        while(!ClientConnexionRequest.connected){
            if(!ClientConnexionRequest.clientExists) System.exit(-1);
            
            this.clientConnexionRequest = new ClientConnexionRequest(ConnexionWindow.username,ConnexionWindow.password);
            Thread.sleep(5000);
        }
        reconnectionFrame.setEnabled(false);
        reconnectionFrame.setVisible(false);
        this.setVisible(false);
        if(serverInterface != null) {
            serverInterface = new ServerInterface(this.clientConnexionRequest);
            serverInterface.setVisible(true);
        }
        if(chatWindow != null) {
            chatWindow = new ChatWindow(this.clientConnexionRequest);
            chatWindow.setVisible(true);
        }
    }

}
