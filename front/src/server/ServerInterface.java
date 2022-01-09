package server;

import client.ClientConnexionRequest;
import client.RootRequest;
import frontobjects.FrontGroup;
import frontobjects.FrontUser;
import utils.Utils;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import org.netbeans.lib.awtextra.*;

public class ServerInterface extends JFrame {
    /*---------------------- JSwing Declarations ----------------------*/
    private JPanel btn1;
    private JPanel btn2;
    private JPanel btn3;
    private JPanel centrePanel;
    private JTextField firstNameTxtField;
    public static JComboBox<FrontGroup> grpSelectAddUser;
    public static JComboBox<FrontGroup> grpListComboBoxMU;
    public static JComboBox<FrontGroup> grpListToRemove;
    private JTextField grpNameTextFieldCreate;
    private JTextField lastNameTxtField;
    public static JPanel mainPanel;
    private JPanel pAddUsr;
    private JPanel pMngGrps;
    private JPanel pMngUsr;
    private JPasswordField passwordTxtField;
    public static JComboBox<FrontUser> usrListComboBox;
    private JTextField usrNameTxtField;
    private static JTable usrTable;
    public static JCheckBox isUserAdminCheckBox;
    /*---------------------- Util Declarations ----------------------*/
    private int xMouse;
    private int yMouse;
    private static final DefaultTableModel dtmUsers = new DefaultTableModel();
    private static final Map<FrontUser,String> usersTableData = new HashMap<>();
    private final RootRequest rootRequest;
    private final FrontUser connectedUser;

    public static FrontGroup selectedDefaultGroup;
    public static FrontGroup[] allFrontGroups;
    public static ArrayList<FrontGroup> selectedUserFrontGroups = new ArrayList<>();

    /* CONSTRUCTOR :
    * - GET DATA BASE INFO
    * - SET UP THE SERVER INTERFACE FRAME
    * */
    public ServerInterface(ClientConnexionRequest clientConnexionRequest) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/serveur-cloud.png"));
        this.setIconImage(icon.getImage());
        initComponents();
        this.rootRequest = new RootRequest(clientConnexionRequest);
        this.connectedUser = ClientConnexionRequest.connectedUser;
        /*Getting groups of preselected default user on 'manage users'*/
        FrontUser selectedUser = usrListComboBox.getItemAt(usrListComboBox.getSelectedIndex());
        if(selectedUser != null) this.rootRequest.getGroupsOfUser(selectedUser);

        this.centrePanel.setVisible(false);

    }
    /* ------------------------------------------START------------------------------------------ */

    /*------------------ {UTILS} ------------------*/
    // SWITCH PAGES
    private void setPagesInvisible(){
        pAddUsr.setVisible(false);
        pMngUsr.setVisible(false);
        pMngGrps.setVisible(false);
        this.setBtnsDefaultColor();
    }
    // CHANGE MENU SELECTION BUTTON COLOR
    private void setBtnsDefaultColor(){
        btn1.setBackground(new Color(84, 222, 253));
        btn2.setBackground(new Color(84, 222, 253));
        btn3.setBackground(new Color(84, 222, 253));
        btn3.setBackground(new Color(84, 222, 253));
    }
    // QUIT ICON
    private void quitIconMouseClicked(MouseEvent evt) {
        dispose();
        System.exit(0);
    }
    // DRAG WINDOW WORKAROUND
    private void frameDragMouseDragged(MouseEvent evt) {
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }
    // GET MOUSE POSITION
    private void frameDragMousePressed(MouseEvent evt) {
        xMouse = evt.getX();
        yMouse = evt.getY();
    }
    // CLEAR USER TABLE DATA
    private static void resetUsrTbl(){
        int nbRows = dtmUsers.getRowCount();
        for(int i = nbRows - 1; i >= 0; i--){
            dtmUsers.removeRow(i);
        }
    }
    // FILL USER TABLE MAP WITH DATA FROM DATA BASE
    private static void fillUsersTableData(){ //AL = ArrayList
        usersTableData.clear();
        for(FrontUser connectedFrontUser : RootRequest.connectedUsersAL){
            usersTableData.put(connectedFrontUser, "Connected"); //MAP <FrontUser, String>
        }
        for(FrontUser disconnectedFrontUser : RootRequest.disconectedUsersAL){
            usersTableData.put(disconnectedFrontUser, "Disconnected");
        }
    }
    // FILL JTABLE
    private static void setUsrTblData(){
        fillUsersTableData();
        Object[] data = new Object[dtmUsers.getColumnCount()];
        for (Map.Entry<FrontUser, String> entry : usersTableData.entrySet()){
            FrontUser usr = entry.getKey();
            String usrStatus = entry.getValue();
            data[0] = usr;
            data[1] = usrStatus;
            dtmUsers.addRow(data); //Ajoute Ligne par ligne !
        }
        usrTable.setModel(dtmUsers);
    }
    // USER TABLE MODEL
    public static void setUsrsList(){
        /* Set up of usrTblModel */
        resetUsrTbl();
        String[] header = {"Name & Surname","Status"};
        dtmUsers.setColumnIdentifiers(header);
        usrTable.setModel(dtmUsers);
        usrTable.setAutoCreateRowSorter(true);
        /* Fill table with data */
        setUsrTblData();
    }
    /*------------------ {END} ------------------*/

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainPanel = new JPanel();
        JPanel sideBarPanel = new JPanel();
        JLabel iconLabel = new JLabel();
        JLabel jLabel1 = new JLabel();
        btn1 = new JPanel();
        JLabel iconBtn1 = new JLabel();
        JLabel textBtn1 = new JLabel();
        btn2 = new JPanel();
        JLabel iconBtn2 = new JLabel();
        JLabel textBtn2 = new JLabel();
        btn3 = new JPanel();
        JLabel iconBtn4 = new JLabel();
        JLabel textBtn4 = new JLabel();
        JPanel headerPanel = new JPanel();
        JLabel quitIcon = new JLabel();
        JLabel frameDrag = new JLabel();
        centrePanel = new JPanel();
        pAddUsr = new JPanel();
        firstNameTxtField = new JTextField();
        JLabel jLabel2 = new JLabel();
        lastNameTxtField = new JTextField();
        JLabel jLabel3 = new JLabel();
        usrNameTxtField = new JTextField();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JButton registerUserButton = new JButton();
        passwordTxtField = new JPasswordField();
        grpSelectAddUser = new JComboBox<>();
        pMngGrps = new JPanel();
        grpNameTextFieldCreate = new JTextField();
        JLabel jLabel7 = new JLabel();
        JButton createGroupBtn = new JButton();
        grpListToRemove = new JComboBox<>();
        JButton btnRmvGrp = new JButton();
        pMngUsr = new JPanel();
        JScrollPane jScrollPane2 = new JScrollPane();
        usrTable = new JTable();
        usrListComboBox = new JComboBox<>();
        JButton btnRmvUsr = new JButton();
        grpListComboBoxMU = new JComboBox<>();
        JButton btnAddUserToGrp = new JButton();
        isUserAdminCheckBox = new JCheckBox();
        JButton btnRmvUsrFrmGrp = new JButton();

        mainPanel.setLayout(new AbsoluteLayout());

        sideBarPanel.setBackground(new Color(73, 198, 229));
        sideBarPanel.setLayout(new AbsoluteLayout());

        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setIcon(new ImageIcon(getClass().getResource("/icons/icons8-admin-64.png")));
        sideBarPanel.add(iconLabel, new AbsoluteConstraints(-2, 5, 130, 67));

        jLabel1.setBackground(new Color(255, 251, 250));
        jLabel1.setFont(new Font("Segoe UI Semibold", 2, 18));
        jLabel1.setForeground(new Color(255, 251, 250));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Dash Board");
        sideBarPanel.add(jLabel1, new AbsoluteConstraints(0, 70, 130, 20));

        btn1.setBackground(new Color(84, 222, 253));
        btn1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btn1MouseClicked(evt);
            }
        });
        btn1.setLayout(new AbsoluteLayout());

        iconBtn1.setHorizontalAlignment(SwingConstants.CENTER);
        iconBtn1.setIcon(new ImageIcon(getClass().getResource("/icons/icons8-add-user-male-50 (1).png")));
        btn1.add(iconBtn1, new AbsoluteConstraints(0, 0, 130, 40));

        textBtn1.setFont(new Font("Calibri", 1, 18));
        textBtn1.setForeground(new Color(255, 251, 250));
        textBtn1.setHorizontalAlignment(SwingConstants.CENTER);
        textBtn1.setText("Add User");
        btn1.add(textBtn1, new AbsoluteConstraints(0, 36, 124, 20));

        sideBarPanel.add(btn1, new AbsoluteConstraints(3, 100, 124, 60));

        btn2.setBackground(new Color(84, 222, 253));
        btn2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btn2MouseClicked(evt);
            }
        });
        btn2.setLayout(new AbsoluteLayout());

        iconBtn2.setHorizontalAlignment(SwingConstants.CENTER);
        iconBtn2.setIcon(new ImageIcon(getClass().getResource("/icons/icons8-registration-50 (1).png")));
        btn2.add(iconBtn2, new AbsoluteConstraints(0, 0, 130, 40));

        textBtn2.setFont(new Font("Calibri", 1, 18));
        textBtn2.setForeground(new Color(255, 251, 250));
        textBtn2.setHorizontalAlignment(SwingConstants.CENTER);
        textBtn2.setText("Manage Users");
        btn2.add(textBtn2, new AbsoluteConstraints(0, 36, 124, 20));

        sideBarPanel.add(btn2, new AbsoluteConstraints(3, 165, 124, 60));

        btn3.setBackground(new Color(84, 222, 253));
        btn3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                btn3MouseClicked(evt);
            }
        });
        btn3.setLayout(new AbsoluteLayout());

        iconBtn4.setHorizontalAlignment(SwingConstants.CENTER);
        iconBtn4.setIcon(new ImageIcon(getClass().getResource("/icons/icons8-add-user-group-man-man-50 (1).png")));
        btn3.add(iconBtn4, new AbsoluteConstraints(0, 0, 130, 40));

        textBtn4.setFont(new Font("Calibri", 1, 18));
        textBtn4.setForeground(new Color(255, 251, 250));
        textBtn4.setHorizontalAlignment(SwingConstants.CENTER);
        textBtn4.setText("Manage Groups");
        btn3.add(textBtn4, new AbsoluteConstraints(0, 36, 124, 20));

        sideBarPanel.add(btn3, new AbsoluteConstraints(3, 230, 124, 60));

        mainPanel.add(sideBarPanel, new AbsoluteConstraints(0, 0, 130, 600));

        headerPanel.setBackground(new Color(255, 251, 250));
        headerPanel.setLayout(new AbsoluteLayout());

        quitIcon.setHorizontalAlignment(SwingConstants.CENTER);
        quitIcon.setIcon(new ImageIcon(getClass().getResource("/icons/icons8-button-64 (1).png")));
        quitIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quitIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                quitIconMouseClicked(evt);
            }
        });
        headerPanel.add(quitIcon, new AbsoluteConstraints(862, 1, 40, -1));

        frameDrag.setText("jLabel2");
        frameDrag.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                frameDragMouseDragged(evt);
            }
        });
        frameDrag.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                frameDragMousePressed(evt);
            }
        });
        headerPanel.add(frameDrag, new AbsoluteConstraints(1, 1, 900, 35));

        mainPanel.add(headerPanel, new AbsoluteConstraints(1, 1, 900, 35));

        centrePanel.setLayout(new CardLayout());

        pAddUsr.setBackground(new Color(162, 221, 210));
        pAddUsr.setLayout(new AbsoluteLayout());

        firstNameTxtField.setBackground(new Color(222, 229, 229));
        pAddUsr.add(firstNameTxtField, new AbsoluteConstraints(130, 120, 480, -1));

        jLabel2.setFont(new Font("Segoe UI", 3, 14));
        jLabel2.setForeground(new Color(255, 251, 250));
        jLabel2.setText("First Name");
        pAddUsr.add(jLabel2, new AbsoluteConstraints(130, 100, -1, -1));

        pAddUsr.add(lastNameTxtField, new AbsoluteConstraints(130, 170, 480, -1));

        jLabel3.setFont(new Font("Segoe UI", 3, 14));
        jLabel3.setForeground(new Color(255, 251, 250));
        jLabel3.setText("Last Name");
        pAddUsr.add(jLabel3, new AbsoluteConstraints(130, 150, -1, -1));

        usrNameTxtField.setBackground(new Color(222, 229, 229));
        pAddUsr.add(usrNameTxtField, new AbsoluteConstraints(130, 220, 480, -1));

        jLabel4.setFont(new Font("Segoe UI", 3, 14));
        jLabel4.setForeground(new Color(255, 251, 250));
        jLabel4.setText("Username");
        pAddUsr.add(jLabel4, new AbsoluteConstraints(130, 200, -1, -1));

        jLabel5.setFont(new Font("Segoe UI", 3, 14));
        jLabel5.setForeground(new Color(255, 251, 250));
        jLabel5.setText("Password");
        pAddUsr.add(jLabel5, new AbsoluteConstraints(130, 250, -1, -1));

        jLabel6.setFont(new Font("Segoe UI", 3, 14));
        jLabel6.setForeground(new Color(255, 251, 250));
        jLabel6.setText("Default Group");
        pAddUsr.add(jLabel6, new AbsoluteConstraints(130, 300, -1, -1));
        
        isUserAdminCheckBox.setFont(new Font("Segoe UI", 3, 14));
        isUserAdminCheckBox.setForeground(new Color(255, 251, 250));
        isUserAdminCheckBox.setBackground(new Color(168, 220, 212));
        isUserAdminCheckBox.setText("Administrator");
        pAddUsr.add(isUserAdminCheckBox, new AbsoluteConstraints(130, 370, -1, -1));
        

        registerUserButton.setBackground(new Color(84, 222, 253));
        registerUserButton.setFont(new Font("Candara", 3, 18));
        registerUserButton.setForeground(new Color(255, 255, 255));
        registerUserButton.setText("Register User");
        registerUserButton.setOpaque(true);
        registerUserButton.setBorderPainted(false);
        registerUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                registerUserButtonActionPerformed(evt);
            }
        });
        pAddUsr.add(registerUserButton, new AbsoluteConstraints(450, 370, 160, 40));
        pAddUsr.add(passwordTxtField, new AbsoluteConstraints(130, 270, 480, -1));

        grpSelectAddUser.setBackground(new Color(255, 255, 255));
        grpSelectAddUser.setFont(new Font("Segoe UI", 0, 14));
        grpSelectAddUser.setModel(new DefaultComboBoxModel<>());
        pAddUsr.add(grpSelectAddUser, new AbsoluteConstraints(130, 320, 480, 30));

        centrePanel.add(pAddUsr, "card2");

        pMngGrps.setBackground(new Color(162, 221, 210));
        pMngGrps.setLayout(new AbsoluteLayout());
        pMngGrps.add(grpNameTextFieldCreate, new AbsoluteConstraints(40, 180, 500, 40));

        jLabel7.setFont(new Font("Segoe UI", 3, 18)); 
        jLabel7.setForeground(new Color(255, 251, 250));
        jLabel7.setText("Group Name");
        pMngGrps.add(jLabel7, new AbsoluteConstraints(40, 150, -1, -1));

        createGroupBtn.setBackground(new Color(132, 137, 74));
        createGroupBtn.setFont(new Font("Candara", 3, 20)); 
        createGroupBtn.setForeground(new Color(255, 255, 255));
        createGroupBtn.setText("Create Group");//createGroupBtnActionPerformed
        createGroupBtn.setOpaque(true);
        createGroupBtn.setBorderPainted(false);
        createGroupBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createGroupBtnActionPerformed(evt);
            }
        });
        pMngGrps.add(createGroupBtn, new AbsoluteConstraints(560, 180, 180, 40));

        grpListToRemove.setBackground(new Color(255, 255, 255));
        grpListToRemove.setFont(new Font("Candara", 0, 20)); 
        grpListToRemove.setModel(new DefaultComboBoxModel<>());
        pMngGrps.add(grpListToRemove, new AbsoluteConstraints(40, 255, 500, 40));

        btnRmvGrp.setBackground(new Color(147, 3, 46));
        btnRmvGrp.setFont(new Font("Candara", 3, 18)); 
        btnRmvGrp.setForeground(new Color(255, 255, 255));
        btnRmvGrp.setText("Remove Group");
        btnRmvGrp.setOpaque(true);
        btnRmvGrp.setBorderPainted(false);
        btnRmvGrp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRmvGrpActionPerformed(evt);
            }
        });
        pMngGrps.add(btnRmvGrp, new AbsoluteConstraints(560, 255, 180, 40));

        centrePanel.add(pMngGrps, "card2");

        pMngUsr.setBackground(new Color(162, 221, 210));
        pMngUsr.setLayout(new AbsoluteLayout());

        usrTable.setForeground(new Color(3, 76, 60));
        usrTable.setModel(new DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        usrTable.setGridColor(new Color(84, 222, 253));
        usrTable.setRowSelectionAllowed(false);
        usrTable.setSelectionBackground(new Color(84, 222, 253));
        usrTable.setSelectionForeground(new Color(148, 197, 149));
        usrTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                usrTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(usrTable);

        pMngUsr.add(jScrollPane2, new AbsoluteConstraints(20, 20, 420, 530));

        usrListComboBox.setBackground(new Color(255, 255, 255));
        usrListComboBox.setFont(new Font("Candara", 0, 18));
        usrListComboBox.setModel(new DefaultComboBoxModel<>());
        usrListComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) { getSelectedUser(evt); }
        });
        pMngUsr.add(usrListComboBox, new AbsoluteConstraints(450, 20, 180, 50));

        btnRmvUsr.setBackground(new Color(147, 3, 46));
        btnRmvUsr.setFont(new Font("Candara", 3, 16));
        btnRmvUsr.setForeground(new Color(255, 255, 255));
        btnRmvUsr.setText("Remove User");
        btnRmvUsr.setOpaque(true);
        btnRmvUsr.setBorderPainted(false);
        btnRmvUsr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRmvUsrActionPerformed(evt);
            }
        });
        pMngUsr.add(btnRmvUsr, new AbsoluteConstraints(635, 20, 125, 50));

        grpListComboBoxMU.setBackground(new Color(255, 255, 255));
        grpListComboBoxMU.setFont(new Font("Segoe UI", 0, 18));
        grpListComboBoxMU.setForeground(new Color(0, 0, 0));
        grpListComboBoxMU.setModel(new DefaultComboBoxModel<>());
        pMngUsr.add(grpListComboBoxMU, new AbsoluteConstraints(450, 80, 310, 50));

        btnAddUserToGrp.setBackground(new Color(84, 222, 253));
        btnAddUserToGrp.setFont(new Font("Candara", 3, 18));
        btnAddUserToGrp.setForeground(new Color(255, 255, 255));
        btnAddUserToGrp.setText("Add to Group");
        btnAddUserToGrp.setOpaque(true);
        btnAddUserToGrp.setBorderPainted(false);
        btnAddUserToGrp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnAddUserToGrpActionPerformed(evt);
            }
        });
        pMngUsr.add(btnAddUserToGrp, new AbsoluteConstraints(620, 140, 140, 50));

        btnRmvUsrFrmGrp.setBackground(new Color(147, 3, 46));
        btnRmvUsrFrmGrp.setFont(new Font("Candara", 3, 15));
        btnRmvUsrFrmGrp.setForeground(new Color(255, 255, 255));
        btnRmvUsrFrmGrp.setText("Remove from Group");
        btnRmvUsrFrmGrp.setOpaque(true);
        btnRmvUsrFrmGrp.setBorderPainted(false);
        btnRmvUsrFrmGrp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnRmvUserFrmGrpActionPerformed(evt);
            }
        });
        pMngUsr.add(btnRmvUsrFrmGrp, new AbsoluteConstraints(450, 140, 165, 50));

        centrePanel.add(pMngUsr, "card2");

        mainPanel.add(centrePanel, new AbsoluteConstraints(130, 35, 769, 565));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    /*------------------ {MANAGE USERS} ------------------*/
    // DISPLAY USER INFO WHEN CLICKED ON JTABLE
    private void usrTableMouseClicked(MouseEvent evt) {
        StringBuilder sb = new StringBuilder();
        boolean b = usrTable.isEditing();
        if(!b){
            int row = usrTable.getSelectedRow();
            int column = usrTable.getSelectedColumn();
            Object cellValue = dtmUsers.getValueAt(row,column);
            if(cellValue instanceof FrontUser usr) {
                if (usr.isAdmin) sb.append("[Admin]" + '\n');
                else sb.append("[User]" + '\n');
                sb.append(" -> Name: ").append(usr.name).append('\n');
                sb.append(" -> Surname: ").append(usr.surname).append('\n');
                sb.append(" -> ID: ").append(usr.id);
            } else {
                sb.append((String)cellValue);
            }
            JOptionPane.showMessageDialog(new JFrame(), sb.toString(), "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    // REGISTER USER REQUEST + SET DEFAULT GROUP
    public void registerUser(ArrayList<String> fields){
        selectedDefaultGroup = grpSelectAddUser.getItemAt(grpSelectAddUser.getSelectedIndex());
        if(selectedDefaultGroup != null) {
            // Writing User Creation Payload
            Map<String, String> userPayload = new HashMap<>();
            userPayload.put("username", fields.get(2));
            userPayload.put("name", fields.get(0));
            userPayload.put("surname", fields.get(1));
            userPayload.put("password", fields.get(3));
            userPayload.put("isAdmin", "" + isUserAdminCheckBox.isSelected());
            // Sending request: Create the User (Needed to generate ID)
            this.rootRequest.createUser(userPayload);
        } else { Utils.errorWindow("No group is selected","Error"); }
    }
    // SET USER INFO
    private ArrayList<String> getRegisteredUserInfo(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(firstNameTxtField.getText()); // First Name
        arrayList.add(lastNameTxtField.getText()); // Las Name
        arrayList.add(usrNameTxtField.getText()); // UserName
        arrayList.add(new String( passwordTxtField.getPassword())); // Password
        return arrayList;
    }
    // CHECK DATA VALIDITY
    private void registerUserButtonActionPerformed(ActionEvent evt) {
        // Checks Fields validity
        ArrayList<String> fieldsList = this.getRegisteredUserInfo();
        boolean dataIsCorrect = false;
        int passwordIndex = 0;
        for(String info : fieldsList){
            dataIsCorrect = true;
            if(info.equals("") || (passwordIndex != 3 && !Utils.isValidString(info))){
                dataIsCorrect = false;
                Utils.warningWindow("Invalid Syntax or Missing Fields","Error Syntax");
                break;
            }
            passwordIndex++;
        }
        if(dataIsCorrect){
            registerUser(fieldsList); // REGISTER USER (+ affect default group to a User)
            firstNameTxtField.setText("");
            lastNameTxtField.setText("");
            usrNameTxtField.setText("");
            passwordTxtField.setText("");
        }
    }

    //REMOVE USER
    private void btnRmvUsrActionPerformed(ActionEvent evt) {
        FrontUser selectedUser = usrListComboBox.getItemAt(usrListComboBox.getSelectedIndex());
        Map<String, String> payload = new HashMap<>();
        payload.put("id", "" + selectedUser.id);
        if(selectedUser.id != connectedUser.id) {
            if (!RootRequest.connectedUsersAL.contains(selectedUser)) this.rootRequest.removeUser(payload);
            else { this.rootRequest.removeConnectedUser(payload); }
            Utils.informationWindow("User removed successfully", "Information");

        } else { Utils.informationWindow("You cannot remove yourself dummy !", "Information"); }
    }
    private void getSelectedUser(ItemEvent evt){
        if(evt.getStateChange() == ItemEvent.SELECTED){
            FrontUser frontUser = (FrontUser) evt.getItem();
            this.rootRequest.getGroupsOfUser(frontUser);
        }
    }

    /*------------------ {MANAGE GROUPS} ------------------*/
    public static void addUserToGroupFromComboBox(FrontUser user, FrontGroup selectedGroup){
        // Writing Group assignment payload
        Map<String,String> defaultGroupPayload = new HashMap<>();
        defaultGroupPayload.put("groupId",""+selectedGroup.id);
        defaultGroupPayload.put("userId",""+user.id);
        RootRequest.addUserToGroup(defaultGroupPayload);
    }
    // In 'manage users' section
    private void btnAddUserToGrpActionPerformed(ActionEvent evt) {
        FrontUser selectedUser = usrListComboBox.getItemAt(usrListComboBox.getSelectedIndex());
        FrontGroup selectedGroup = grpListComboBoxMU.getItemAt(grpListComboBoxMU.getSelectedIndex());
        if(!selectedUser.equals(this.connectedUser)) {
            if(!selectedUserFrontGroups.contains(selectedGroup)) {
                if (selectedGroup != null) {
                    addUserToGroupFromComboBox(selectedUser, selectedGroup);
                    String msg = selectedUser + " successfully added to " + selectedGroup + " !";
                    Utils.informationWindow(msg, "Information");
                } else { Utils.errorWindow("No group is selected !", "Error"); }
            } else { Utils.warningWindow("User already belongs to "+selectedGroup+'.', "Error"); }
        } else { Utils.warningWindow("You cannot add a group to an Admin.","Error"); }
    }
    // In 'manage users' section
    private void btnRmvUserFrmGrpActionPerformed(ActionEvent evt) {
        FrontUser selectedUser = usrListComboBox.getItemAt(usrListComboBox.getSelectedIndex());
        FrontGroup selectedGroup = grpListComboBoxMU.getItemAt(grpListComboBoxMU.getSelectedIndex());
        if(!selectedUser.isAdmin){
            if(selectedUserFrontGroups.contains(selectedGroup)) {
                if (selectedGroup != null) {
                    // Writing payload
                    Map<String, String> payload = new HashMap<>();
                    payload.put("groupId", "" + selectedGroup.id);
                    payload.put("userId", "" + selectedUser.id);
                    RootRequest.removeUserFromGroup(payload);
                    String msg = selectedUser + " successfully removed from " + selectedGroup + " !";
                    Utils.informationWindow(msg, "Information");
                } else { Utils.errorWindow("No group is selected", "Error"); }
            } else { Utils.warningWindow("User does not belong to "+selectedGroup+'.', "Error"); }
        } else { Utils.warningWindow("You cannot remove a group from an Admin.","Error"); }
    }

    private void createGroupBtnActionPerformed(ActionEvent evt) {
        String groupName = grpNameTextFieldCreate.getText();
        Map<String,String> payload = new HashMap<>();
        if(Utils.isValidString(groupName)){
            payload.put("name",groupName);
            this.rootRequest.createGroup(payload);
            Utils.informationWindow("Group successfully created !", "Information");
        } else {
            String msg = "Group name must contain between " + Utils.MIN_CHARS +" and " +Utils.MAX_CHARS +" characters !\nAuthorized chars: [a-Z][0-9]-_";
            Utils.warningWindow(msg, "Information"); }
    }
    private void btnRmvGrpActionPerformed(ActionEvent evt) {
        FrontGroup selectedGroup = grpListToRemove.getItemAt(grpListToRemove.getSelectedIndex());
        if(selectedGroup != null) {
            Map<String, String> payload = new HashMap<>();
            payload.put("id", "" + selectedGroup.id);
            this.rootRequest.removeGroup(payload);
            Utils.informationWindow("Group successfully removed !", "Information");
        }
    }

    /*------------------ {SELECTION MENU} ------------------*/
    //Add user
    private void btn1MouseClicked(MouseEvent evt) {
        if(this.centrePanel.isVisible()){
            this.setPagesInvisible();
        } else {
            this.centrePanel.setVisible(true);
            this.setPagesInvisible();
        }
        btn1.setBackground(new Color(72, 159, 181));
        pAddUsr.setVisible(true);
    }

    //Manage users
    private void btn2MouseClicked(MouseEvent evt) {
        if(this.centrePanel.isVisible()){
            this.setPagesInvisible();
        } else {
            this.centrePanel.setVisible(true);
            this.setPagesInvisible();
        }
        setUsrsList(); //Sets table UsersTable: list of users
        btn2.setBackground(new Color(72, 159, 181));
        pMngUsr.setVisible(true);
    }
    //Manage groups
    private void btn3MouseClicked(MouseEvent evt) {
        if(this.centrePanel.isVisible()){
            this.setPagesInvisible();
        } else {
            this.centrePanel.setVisible(true);
            this.setPagesInvisible();
        }
        btn3.setBackground(new Color(72, 159, 181));
        pMngGrps.setVisible(true);
    }
    /* ------------------------------------------START------------------------------------------ */
}
