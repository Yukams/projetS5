/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package front.server;

import front.client.RootRequest;
import front.frontobjects.FrontGroup;
import front.frontobjects.FrontUser;
import front.utils.Utils;

import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ServerInterface extends javax.swing.JFrame {

    private int xMouse;
    private int yMouse;
    protected DefaultTableModel dtmUsers = new DefaultTableModel();
    protected Map<String,String> usersTableData = new HashMap<>();
    protected String[] groups;
    private final String connected = "Connected";
    private final String disconnected = "Disconnected";
    Map<String, String> payload = new HashMap<>();

    private RootRequest rootRequest;

    /**
     * Creates new form ServerInterface
     */

    public ServerInterface() {
        this.rootRequest = new RootRequest();
        this.rootRequest.setUsersList();
        this.groups = this.rootRequest.askGroupsFromServer();


        initComponents();
        this.centrePanel.setVisible(false);



    }
    
    private void resetUsrTbl(){
        int nbRows = dtmUsers.getRowCount();
        for(int i = nbRows - 1; i >= 0; i--){
            dtmUsers.removeRow(i);
        }
    }
    
    private void setUsrTblModel(){
        resetUsrTbl();
        String[] header = {"Username","Status"};
        dtmUsers.setColumnIdentifiers(header);
        usrTable.setModel(dtmUsers);
    }
    
    private void fillUsersTableData(){ //AL = ArrayList

        for(FrontUser connectedFrontUser : this.rootRequest.connectedUsersAL){
            usersTableData.put(connectedFrontUser.name + " " + connectedFrontUser.surname, connected);
        }
        for(FrontUser disconnectedFrontUser : this.rootRequest.disconectedUsersAL){
            usersTableData.put(disconnectedFrontUser.name + " " + disconnectedFrontUser.surname, disconnected);
        }
    }
    
    private void setUsrTblData(){
        fillUsersTableData();
        Object[] data = new Object[dtmUsers.getColumnCount()];
        for (Map.Entry<String, String> entry : usersTableData.entrySet()){
            String usr = entry.getKey();
            String usrStatus = entry.getValue();
            data[0] = usr;
            data[1] = usrStatus;
            dtmUsers.addRow(data); //Ajoute Ligne par ligne !
        }
        usrTable.setModel(dtmUsers);
    }

    private void usrTableMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO
        boolean b = usrTable.isEditing();
        if(!b){
            int row = usrTable.getSelectedRow();
            int column = usrTable.getSelectedColumn();
            String usr = (String) dtmUsers.getValueAt(row,column);

            JOptionPane.showMessageDialog(null, usr);
        }
    }

    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        sideBarPanel = new javax.swing.JPanel();
        iconLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn1 = new javax.swing.JPanel();
        iconBtn1 = new javax.swing.JLabel();
        textBtn1 = new javax.swing.JLabel();
        btn2 = new javax.swing.JPanel();
        iconBtn2 = new javax.swing.JLabel();
        textBtn2 = new javax.swing.JLabel();
        btn3 = new javax.swing.JPanel();
        iconBtn4 = new javax.swing.JLabel();
        textBtn4 = new javax.swing.JLabel();
        headerPanel = new javax.swing.JPanel();
        quitIcon = new javax.swing.JLabel();
        frameDrag = new javax.swing.JLabel();
        centrePanel = new javax.swing.JPanel();
        pAddUsr = new javax.swing.JPanel();
        firstNameTxtField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lastNameTxtField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        usrNameTxtField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        registerUserButton = new javax.swing.JButton();
        passwordTxtField = new javax.swing.JPasswordField();
        groupSelectAddUser = new javax.swing.JComboBox<>();
        pMngGrps = new javax.swing.JPanel();
        grpNameTextFieldCreate = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        createGroupBtn = new javax.swing.JButton();
        grpListToRemove = new javax.swing.JComboBox<>();
        btnRmvGrp = new javax.swing.JButton();
        pMngUsr = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        usrTable = new javax.swing.JTable();
        usrListComboBox = new javax.swing.JComboBox<>();
        btnRmvUsr = new javax.swing.JButton();
        grpListAdd2Usr = new javax.swing.JComboBox<>();
        btnAddGroup = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sideBarPanel.setBackground(new java.awt.Color(73, 198, 229));
        sideBarPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-admin-64.png"))); // NOI18N
        sideBarPanel.add(iconLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 5, 130, 67));

        jLabel1.setBackground(new java.awt.Color(255, 251, 250));
        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 2, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 251, 250));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dash Board");
        sideBarPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 130, 20));

        btn1.setBackground(new java.awt.Color(84, 222, 253));
        btn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn1MouseClicked(evt);
            }
        });
        btn1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconBtn1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-add-user-male-50 (1).png"))); // NOI18N
        btn1.add(iconBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 40));

        textBtn1.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        textBtn1.setForeground(new java.awt.Color(255, 251, 250));
        textBtn1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textBtn1.setText("Add User");
        btn1.add(textBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 36, 124, 20));

        sideBarPanel.add(btn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 100, 124, 60));

        btn2.setBackground(new java.awt.Color(84, 222, 253));
        btn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn2MouseClicked(evt);
            }
        });
        btn2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconBtn2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-registration-50 (1).png"))); // NOI18N
        btn2.add(iconBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 40));

        textBtn2.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        textBtn2.setForeground(new java.awt.Color(255, 251, 250));
        textBtn2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textBtn2.setText("Manage Users");
        btn2.add(textBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 36, 124, 20));

        sideBarPanel.add(btn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 165, 124, 60));

        btn3.setBackground(new java.awt.Color(84, 222, 253));
        btn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn3MouseClicked(evt);
            }
        });
        btn3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-add-user-group-man-man-50 (1).png"))); // NOI18N
        btn3.add(iconBtn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 40));

        textBtn4.setFont(new java.awt.Font("Calibri", 1, 18)); // NOI18N
        textBtn4.setForeground(new java.awt.Color(255, 251, 250));
        textBtn4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textBtn4.setText("Manage Groups");
        btn3.add(textBtn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 36, 124, 20));

        sideBarPanel.add(btn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 230, 124, 60));

        mainPanel.add(sideBarPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 600));

        headerPanel.setBackground(new java.awt.Color(255, 251, 250));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        quitIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quitIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8-button-64 (1).png"))); // NOI18N
        quitIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        quitIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quitIconMouseClicked(evt);
            }
        });
        headerPanel.add(quitIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(862, 1, 40, -1));

        frameDrag.setText("jLabel2");
        frameDrag.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                frameDragMouseDragged(evt);
            }
        });
        frameDrag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                frameDragMousePressed(evt);
            }
        });
        headerPanel.add(frameDrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 900, 35));

        mainPanel.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 1, 900, 35));

        centrePanel.setLayout(new java.awt.CardLayout());

        pAddUsr.setBackground(new java.awt.Color(162, 221, 210));
        pAddUsr.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        firstNameTxtField.setBackground(new java.awt.Color(222, 229, 229));
        firstNameTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstNameTxtFieldActionPerformed(evt);
            }
        });
        pAddUsr.add(firstNameTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 480, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 251, 250));
        jLabel2.setText("First Name");
        pAddUsr.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, -1, -1));

        lastNameTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastNameTxtFieldActionPerformed(evt);
            }
        });
        pAddUsr.add(lastNameTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 480, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 251, 250));
        jLabel3.setText("Last Name");
        pAddUsr.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, -1));

        usrNameTxtField.setBackground(new java.awt.Color(222, 229, 229));
        usrNameTxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usrNameTxtFieldActionPerformed(evt);
            }
        });
        pAddUsr.add(usrNameTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 480, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 251, 250));
        jLabel4.setText("Username");
        pAddUsr.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 251, 250));
        jLabel5.setText("Password");
        pAddUsr.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 251, 250));
        jLabel6.setText("Default Group");
        pAddUsr.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, -1, -1));

        registerUserButton.setBackground(new java.awt.Color(84, 222, 253));
        registerUserButton.setFont(new java.awt.Font("Candara", 3, 18)); // NOI18N
        registerUserButton.setForeground(new java.awt.Color(255, 255, 255));
        registerUserButton.setText("Register User");
        registerUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerUserButtonActionPerformed(evt);
            }
        });
        pAddUsr.add(registerUserButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 160, 40));
        pAddUsr.add(passwordTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 480, -1));

        groupSelectAddUser.setBackground(new java.awt.Color(255, 255, 255));
        groupSelectAddUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        groupSelectAddUser.setModel(new javax.swing.DefaultComboBoxModel<>(this.groups));
        pAddUsr.add(groupSelectAddUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 480, 30));

        centrePanel.add(pAddUsr, "card2");

        pMngGrps.setBackground(new java.awt.Color(162, 221, 210));
        pMngGrps.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pMngGrps.add(grpNameTextFieldCreate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 500, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 251, 250));
        jLabel7.setText("Group Name");
        pMngGrps.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        createGroupBtn.setBackground(new java.awt.Color(132, 137, 74));
        createGroupBtn.setFont(new java.awt.Font("Candara", 3, 20)); // NOI18N
        createGroupBtn.setForeground(new java.awt.Color(255, 255, 255));
        createGroupBtn.setText("Create Group");
        pMngGrps.add(createGroupBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 180, 40));

        grpListToRemove.setBackground(new java.awt.Color(255, 255, 255));
        grpListToRemove.setFont(new java.awt.Font("Candara", 0, 20)); // NOI18N
        grpListToRemove.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TD", "SPORT", "SERVICE", "PEDAGOGIQUE" }));
        pMngGrps.add(grpListToRemove, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 500, 40));

        btnRmvGrp.setBackground(new java.awt.Color(147, 3, 46));
        btnRmvGrp.setFont(new java.awt.Font("Candara", 3, 18)); // NOI18N
        btnRmvGrp.setForeground(new java.awt.Color(255, 255, 255));
        btnRmvGrp.setText("Remove Group");
        btnRmvGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmvGrpActionPerformed(evt);
            }
        });
        pMngGrps.add(btnRmvGrp, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 220, 180, 40));

        centrePanel.add(pMngGrps, "card2");

        pMngUsr.setBackground(new java.awt.Color(162, 221, 210));
        pMngUsr.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usrTable.setForeground(new java.awt.Color(3, 76, 60));
        usrTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {}
            },
            new String [] {

            }
        ));
        usrTable.setGridColor(new java.awt.Color(84, 222, 253));
        usrTable.setRowSelectionAllowed(false);
        usrTable.setSelectionBackground(new java.awt.Color(84, 222, 253));
        usrTable.setSelectionForeground(new java.awt.Color(148, 197, 149));
        usrTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usrTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(usrTable);

        pMngUsr.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 420, 530));

        usrListComboBox.setBackground(new java.awt.Color(255, 255, 255));
        usrListComboBox.setFont(new java.awt.Font("Candara", 0, 20)); // NOI18N
        usrListComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Test", "Test2" }));
        pMngUsr.add(usrListComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, 310, 50));

        btnRmvUsr.setBackground(new java.awt.Color(147, 3, 46));
        btnRmvUsr.setFont(new java.awt.Font("Candara", 3, 18)); // NOI18N
        btnRmvUsr.setForeground(new java.awt.Color(255, 255, 255));
        btnRmvUsr.setText("Remove User");
        btnRmvUsr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRmvUsrActionPerformed(evt);
            }
        });
        pMngUsr.add(btnRmvUsr, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 140, 50));

        grpListAdd2Usr.setBackground(new java.awt.Color(255, 255, 255));
        grpListAdd2Usr.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        grpListAdd2Usr.setForeground(new java.awt.Color(0, 0, 0));
        grpListAdd2Usr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TD", "SPORT", "SERVICE", "PEDAGOGIQUE" }));
        pMngUsr.add(grpListAdd2Usr, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 140, 310, 50));

        btnAddGroup.setBackground(new java.awt.Color(84, 222, 253));
        btnAddGroup.setFont(new java.awt.Font("Candara", 3, 20)); // NOI18N
        btnAddGroup.setForeground(new java.awt.Color(255, 255, 255));
        btnAddGroup.setText("Add Group");
        pMngUsr.add(btnAddGroup, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, 140, 50));

        centrePanel.add(pMngUsr, "card2");

        mainPanel.add(centrePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 35, 769, 565));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void quitIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quitIconMouseClicked
        dispose();
    }//GEN-LAST:event_quitIconMouseClicked

    private void frameDragMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_frameDragMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_frameDragMouseDragged

    private void frameDragMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_frameDragMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_frameDragMousePressed

    private void firstNameTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTxtFieldActionPerformed

    private void lastNameTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameTxtFieldActionPerformed

    private void usrNameTxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usrNameTxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usrNameTxtFieldActionPerformed

    private void registerUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerUserButtonActionPerformed
        // Checks Fields validity
        ArrayList<String> fieldsList = this.getRegisteredUserInfo();
        boolean dataIsCorrect = false;
        for(String info : fieldsList){
            dataIsCorrect = true;
            if(info.equals("") || !Utils.isValidString(info)){
                dataIsCorrect = false;
                Utils.missingFieldsErrorMessage();
                break;
            }
        }
        if(dataIsCorrect){
            registerUser(fieldsList);
        }
    }//GEN-LAST:event_registerUserButtonActionPerformed

    public void registerUser(ArrayList<String> fields){
        // Writing User Creation Payload
        Map<String,String> userPayload = new HashMap<>();
        userPayload.put("username",fields.get(2));
        userPayload.put("name",fields.get(0));
        userPayload.put("surname",fields.get(1));
        userPayload.put("password",fields.get(3));
        // Sending request: Create the User (Needed to generate ID)
        this.rootRequest.createUser(userPayload);
        /* Associating the user to an initial default group */
        String selectedGroup = groupSelectAddUser.getItemAt(groupSelectAddUser.getSelectedIndex());
        FrontUser user = this.rootRequest.createdUser; // Just Created User
        this.addUserToGroupFromComboBox(user,selectedGroup);

    }

    public void addUserToGroupFromComboBox(FrontUser user, String selectedGroup){
        // Getting the selected group id
        for(FrontGroup frontGroup : this.rootRequest.frontGroupsAL){
            if(frontGroup.name.equals(selectedGroup)){
                // Writing Group assignment payload
                Map<String,String> defaultGroupPayload = new HashMap<>();
                defaultGroupPayload.put("groupId",""+frontGroup.id);
                defaultGroupPayload.put("userId",""+user.id);
                this.rootRequest.addUserToGroup(defaultGroupPayload);
                JOptionPane.showMessageDialog(null, "User Successfully created !");
                break;
            }
        }

    }

    private ArrayList<String> getRegisteredUserInfo(){
        ArrayList<String> arrayList = new ArrayList<>();
        String firstName = firstNameTxtField.getText();
        arrayList.add(firstName);
        String lastName = lastNameTxtField.getText();
        arrayList.add(lastName);
        String username = usrNameTxtField.getText();
        arrayList.add(username);
        String password = new String( passwordTxtField.getPassword());
        arrayList.add(password);
        return arrayList;
    }

    //Add user
    private void btn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseClicked
        if(this.centrePanel.isVisible()){
            this.setPagesInvisible();
        } else {
            this.centrePanel.setVisible(true);
            this.setPagesInvisible();
        }
        btn1.setBackground(new java.awt.Color(72, 159, 181));
        pAddUsr.setVisible(true);
    }//GEN-LAST:event_btn1MouseClicked

    //Manage users
    private void btn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseClicked
        if(this.centrePanel.isVisible()){
            this.setPagesInvisible();
        } else {
            this.centrePanel.setVisible(true);
            this.setPagesInvisible();
        }
        setUsrTblModel(); //Sets table UsersTable Headers
        setUsrTblData(); //Updates data each time clicks on button
        btn2.setBackground(new java.awt.Color(72, 159, 181));
        pMngUsr.setVisible(true);
    }//GEN-LAST:event_btn2MouseClicked
    //Manage groups
    private void btn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3MouseClicked
        if(this.centrePanel.isVisible()){
            this.setPagesInvisible();
        } else {
            this.centrePanel.setVisible(true);
            this.setPagesInvisible();
        }
        btn3.setBackground(new java.awt.Color(72, 159, 181));
        pMngGrps.setVisible(true);
    }//GEN-LAST:event_btn3MouseClicked

        //REMOVE USER
    private void btnRmvUsrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmvUsrActionPerformed
        // TODO add your handling code here:
        String selectedUsername = usrListComboBox.getItemAt(usrListComboBox.getSelectedIndex());
        JOptionPane.showMessageDialog(null, selectedUsername);
    }//GEN-LAST:event_btnRmvUsrActionPerformed

    private void btnRmvGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRmvGrpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRmvGrpActionPerformed
    
    private void setPagesInvisible(){
        pAddUsr.setVisible(false);
        pMngUsr.setVisible(false);
        pMngGrps.setVisible(false);
        this.setBtnsDefaultColor();
    }
    private void setBtnsDefaultColor(){
        btn1.setBackground(new java.awt.Color(84, 222, 253));
        btn2.setBackground(new java.awt.Color(84, 222, 253));
        btn3.setBackground(new java.awt.Color(84, 222, 253));
        btn3.setBackground(new java.awt.Color(84, 222, 253));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btn1;
    private javax.swing.JPanel btn2;
    private javax.swing.JPanel btn3;
    private javax.swing.JButton btnAddGroup;
    private javax.swing.JButton btnRmvGrp;
    private javax.swing.JButton btnRmvUsr;
    private javax.swing.JPanel centrePanel;
    private javax.swing.JButton createGroupBtn;
    private javax.swing.JTextField firstNameTxtField;
    private javax.swing.JLabel frameDrag;
    private javax.swing.JComboBox<String> groupSelectAddUser;
    private javax.swing.JComboBox<String> grpListAdd2Usr;
    private javax.swing.JComboBox<String> grpListToRemove;
    private javax.swing.JTextField grpNameTextFieldCreate;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JLabel iconBtn1;
    private javax.swing.JLabel iconBtn2;
    private javax.swing.JLabel iconBtn4;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lastNameTxtField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel pAddUsr;
    private javax.swing.JPanel pMngGrps;
    private javax.swing.JPanel pMngUsr;
    private javax.swing.JPasswordField passwordTxtField;
    private javax.swing.JLabel quitIcon;
    private javax.swing.JButton registerUserButton;
    private javax.swing.JPanel sideBarPanel;
    private javax.swing.JLabel textBtn1;
    private javax.swing.JLabel textBtn2;
    private javax.swing.JLabel textBtn4;
    private javax.swing.JComboBox<String> usrListComboBox;
    private javax.swing.JTextField usrNameTxtField;
    private javax.swing.JTable usrTable;
    // End of variables declaration
}
