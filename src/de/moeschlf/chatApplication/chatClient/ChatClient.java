/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.moeschlf.chatApplication.chatClient;

import de.moeschlf.chatApplication.utils.*;
import java.util.Arrays;
import java.util.HashMap;
/**
 *
 * @author Florian Möschler
 */
public class ChatClient extends Client{

    
    
    private HashMap<String,List<String>> messages;
    private List<String> users;
    private String username;
    private boolean userListIsChanged;
    private boolean messagesHaveChanged;
    
    /**
     * Creates an instanc of the chatClient
     * @param pServerIP
     * @param pServerPort 
     */
    public ChatClient(String pServerIP, int pServerPort) {
        super(pServerIP, pServerPort);
        messages = new HashMap<>();
        users = new List<>();
        username="";
        userListIsChanged= false;
        messagesHaveChanged = false;
    }
    
    /**
     * Returns the userlist as a String.
     * @return 
     */
    public String usersToString(){
        String out = "";
        users.toFirst();
        while (!users.isEmpty() && users.hasAccess()) {
            out += users.getContent() + "\n";
            users.next();
        }
        return out;
    }
    
    /**
     * Returns the users as an array.
     * @return 
     */
    public String[] usersToArray(){
        return usersToString().split("\n");
    }
    
    
    /**
     * Returns the message list between this user and an other user.
     * @param name
     * @return 
     */
    public List<String> getMessages(String name){
        if(messages.containsKey(name) && (messages.get(name) instanceof List)){
            return messages.get(name);
        }
        return null;
    }
    
    /**
     * Returns the messages list as a String.
     * @param name
     * @return 
     */
    public String messagesToString(String name){
        List<String> messages = getMessages(name);
        if(messages == null)
            return "";
        messages.toFirst();
        String out = "";
        while(!messages.isEmpty() && messages.hasAccess()){
            out += messages.getContent();
            out += "\n";
            messages.next();
        }
        
        return out;
    }
    
    
    /**
     * is called when a new message arrives
     * @param pMessage 
     */
    @Override
    public void processMessage(String pMessage) {
        String[] message = pMessage.split(" "); 
        switch(message[0]){
            case "CONNECTION" :
                newUser(message);
                break;
            case "MESSAGE":
                newMessage(message);
                break;
            case "DISCONNECTED":
                deleteClient(message);
                break;
            case "GLOBAL":
                newGlobalMessage(message);
                break;
            default:
                System.out.println(pMessage);
                System.out.println(message[0]);
                throw new IllegalArgumentException("Unknown Identifier");
        }
    }
    /**
     * Sets the username elements that will be returned by getUsername(). 
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the username
     * @return 
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Is called from processMessage() if the first part of the message is "MESSAGE".
     * Adds a message to the users chat.
     * @param message 
     */
    private void newMessage(String[] message) {
        String sender = message[1];
        String out = "";
        List<String> tmp;
        for (int i = 2; i<message.length;i++){
            out += message[i] + " ";
        }
        if(out.startsWith("#8410"))
            out = out.replace("#8410", "i:");
        else out = sender + ": "+ out;

        System.out.println(sender + " says: " + out);
        if(messages.containsKey(sender)){
            tmp = messages.get(sender);
        }else
            tmp = new List<>();
        tmp.append(out);
        messages.put(sender, tmp);
        messagesHaveChanged = true;
        System.out.println("New Message");
    }

    /**
     * Is called from processMessage() if a user disconnect.
     * Removes a user from the user list and deletes the chat.
     * @param message 
     */
    private void deleteClient(String[] message) {
        String sender = message[1];
        users.toFirst();
        while(!users.isEmpty() && users.hasAccess()){
            if(sender.equalsIgnoreCase(users.getContent())){
                users.remove();
                messages.remove(sender);
                System.out.println(messages.toString());
                return;
            }
            users.next();
        }
    }
    
    /**
     * Is called from processMessage() if a user connects.
     * Adds an entry to the user list and creates a default chat.
     * @param message 
     */
    private void newUser(String[] message) {
        String sender = message[1];
        if(!username.equalsIgnoreCase(message[1])){
            users.toFirst();
            while(!users.isEmpty() && users.hasAccess()){
                if(users.getContent().equalsIgnoreCase(sender)){
                    return;
                }
                users.next();
            }
            users.append(message[1]);
            List<String> tmp = new List<>();
            tmp.append("Chatbeginn");
            messages.put(sender, tmp);
            System.out.println(message[1]);
            userListIsChanged = true;
        }
    }

    /**
     * Adds the own message to the chat.
     * @param receiver
     * @param message 
     */
    public void addOwnMessage(String receiver, String message){
        String tmp = "MESSAGE " +receiver + " #8410 " + message;
        newMessage(tmp.split(" "));
    }
    
    /**
     * Returns if the user list was changed.
     * @return true, if the user list was changed, otherwise false
     */
    public boolean isUserListChanged() {
        return userListIsChanged;
    }

    /**
     * Sets the value that will be returned by isUserListChanged().
     * @param isChanged 
     */
    public void setUserListIsChanged(boolean isChanged) {
        this.userListIsChanged = isChanged;
    }

   /**
    * Returns if the messages list was changed.
     * @return true, if the messages list was changed, otherwise false
    */
    public boolean areMessagesChanged() {
        return messagesHaveChanged;
    }
    /**
     * Sets the value that will be returned by areMessagesChanged().
     * @param messagesHaveChanged 
     */
    public void setMessagesHaveChanged(boolean messagesHaveChanged) {
        this.messagesHaveChanged = messagesHaveChanged;
    }

    /**
     * Is called from processMessage() if the first part of the message is "GLOBAL".
     * Adds a message to the global chat.
     * @param message 
     */
    private void newGlobalMessage(String[] message) {
        List<String> tmp = messages.get("global");
        String out = "";
        for (int i = 1; i<message.length;i++){
            out += message[i] + " ";
        }
        tmp.append(out);
        messages.put("global", tmp);
        messagesHaveChanged = true;
    }
    
}
