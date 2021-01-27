/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.moeschlf.chatApplication.chatClient;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Florian Möschler
 */
public class ChatClientOberflaeche extends javax.swing.JFrame {

    private ChatClient chatClient;
    private boolean isConnected;
    /**
     * Creates new form ChatClientOberflaeche
     */
    public ChatClientOberflaeche() {
        isConnected = false;
        initComponents();
        usersComboBox.removeAllItems();
        
    }
    /**
     * This method is called from the loginbutton actionhandler to start a Thread, which updates the messagesTextArea.
     */
    private void updateMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        if(chatClient.areMessagesChanged()){
                            messagesTextArea.setText(chatClient.messagesToString(String.valueOf(usersComboBox.getSelectedItem())));
                            chatClient.setMessagesHaveChanged(false);
                            System.out.println(chatClient.messagesToString(String.valueOf(usersComboBox.getSelectedItem())));
                            System.out.println("Nachrichten aktualisiert");
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ChatClientOberflaeche.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }
    /**
     * This method is called from the loginbutton actionhandler to start a Thread, which updates the onlineUserTextArea and the usersComboBox.
     */
    private void updateClients() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        if(chatClient.isUserListChanged()){
                           String users = chatClient.usersToString();
                           users = users.replace("global\n", "");
                            onlineUsersTextArea.setText(users);
                           
                            usersComboBox.removeAllItems();
                            for(String s : chatClient.usersToArray()){
                                usersComboBox.addItem(s);
                            }
                            chatClient.setUserListIsChanged(false);
                        }
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ChatClientOberflaeche.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        onlineUsersTextArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messagesTextArea = new javax.swing.JTextArea();
        usersComboBox = new javax.swing.JComboBox<>();
        sendMessageTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        usernameTextfield = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        ipTextField = new javax.swing.JTextField();
        portTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("online"));

        onlineUsersTextArea.setEditable(false);
        onlineUsersTextArea.setColumns(20);
        onlineUsersTextArea.setRows(5);
        jScrollPane2.setViewportView(onlineUsersTextArea);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("messages"));

        messagesTextArea.setEditable(false);
        messagesTextArea.setColumns(20);
        messagesTextArea.setRows(5);
        jScrollPane1.setViewportView(messagesTextArea);

        usersComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        usersComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersComboBoxActionPerformed(evt);
            }
        });

        sendMessageTextField.setEditable(false);

        sendButton.setText("send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(usersComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(sendMessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(usersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendMessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        usernameTextfield.setText(" ");
        usernameTextfield.setBorder(javax.swing.BorderFactory.createTitledBorder("Username"));

        loginButton.setText("login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        ipTextField.setBorder(javax.swing.BorderFactory.createTitledBorder("IP"));

        portTextField.setBorder(javax.swing.BorderFactory.createTitledBorder("Port"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usernameTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(ipTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(portTextField)
                            .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Actionhandler for the sendButton.
     * Sends a message to the Server.
     * @param evt 
     */
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        String text = sendMessageTextField.getText();
        text = text.trim();
        String receiver = String.valueOf(usersComboBox.getSelectedItem());
        System.out.println(text);
        if(text!=null && !text.equalsIgnoreCase("") && !text.equalsIgnoreCase(" ")){
            sendMessageTextField.setText("");
            chatClient.send("MESSAGE " +receiver + " " +  text);
            chatClient.addOwnMessage(receiver, text);
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    /**
     * Actionhandler for the loginButton.
     * Creates a new connection to the server.
     * @param evt 
     */
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        if(!isConnected){
             String tmp = usernameTextfield.getText();
            tmp = tmp.toLowerCase().trim();
            tmp = tmp.replace(" ", "");
            String port = portTextField.getText().trim();
            String ip = ipTextField.getText().trim();
            if(tmp != null && !"".equals(tmp) && !" ".equals(tmp) ){
                if(!ip.equalsIgnoreCase("") && ip != null && ip.equalsIgnoreCase(" ")&& !port.equalsIgnoreCase("") && port != null && port.equalsIgnoreCase(" "))
                    chatClient = new ChatClient(ip , Integer.valueOf(port));
                else
                    chatClient = new ChatClient("0.0.0.0", 9000);
                isConnected = chatClient.isConnected();
                usersComboBox.removeAllItems();
                updateClients();
                updateMessages();
                ipTextField.setEditable(false);
                portTextField.setEditable(false);
                usernameTextfield.setEditable(false);
                chatClient.send("CONNECT " + tmp);
                chatClient.setUsername(tmp);
                sendMessageTextField.setEditable(true);
            }
            
        }
    }//GEN-LAST:event_loginButtonActionPerformed
    
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    /**
     * Actionhandler for the usersComboBox.
     * Updates the messagesTextArea if the selected Item changes.
     * @param evt 
     */
    private void usersComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersComboBoxActionPerformed
        if(isConnected)
            messagesTextArea.setText(chatClient.messagesToString(String.valueOf(usersComboBox.getSelectedItem())));
    }//GEN-LAST:event_usersComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatClientOberflaeche.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatClientOberflaeche.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatClientOberflaeche.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatClientOberflaeche.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClientOberflaeche().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ipTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton loginButton;
    private javax.swing.JTextArea messagesTextArea;
    private javax.swing.JTextArea onlineUsersTextArea;
    private javax.swing.JTextField portTextField;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField sendMessageTextField;
    private javax.swing.JTextField usernameTextfield;
    private javax.swing.JComboBox<String> usersComboBox;
    // End of variables declaration//GEN-END:variables

    

    
}