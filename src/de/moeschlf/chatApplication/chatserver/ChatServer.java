/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.moeschlf.chatApplication.chatserver;

import de.moeschlf.chatApplication.utils.Server;
import de.moeschlf.chatApplication.utils.List;

/**
 *
 * @author Florians Möschler
 */
public class ChatServer extends Server{

    private List<String[]> clients;
    private List<String> messages;
    
    /**
     * Creates a new ChatServer object.
     * and adds a global client
     * @param port 
     */
    public ChatServer(int port) {
        super(port);
        clients = new List<>();
        messages = new List<>();
        this.addConnection("0.0.0.0", 0, "global");
    }
    /**
     * Adds an entry to the client list
     * @param ip
     * @param port
     * @param name 
     */
    public void addConnection(String ip, int port, String name){
        if(isNameUnique(name)){
            String[] tmp = {ip, String.valueOf(port),name};
            this.sendToAll("CONNECTION " + name);
            for(String s : clientNamestoArray()){
                if(s != null && s !="")
                this.send(ip, port, ("CONNECTION " + s));

            }
            clients.append(tmp);
        }
    }
    
    /**
     * removes an entry from the client list
     * @param ip
     * @param port 
     */
    public void removeConnection(String ip, int port){
        clients.toFirst();
        while(! clients.isEmpty() && clients.hasAccess()){
            if(ip.equalsIgnoreCase(clients.getContent()[0]) && String.valueOf(port).equalsIgnoreCase(clients.getContent()[1])){
                String name = clients.getContent()[2];
                this.sendToAll("DISCONNECTED " + name);
                clients.remove();
                return;
            }    
            clients.next();
        }
    }
    
    /**
     * Returns the clients as a String.
     * @return 
     */
    public String clientstoString(){
        String out = "";
        clients.toFirst();
        while (clients.hasAccess() && !clients.isEmpty()) {
            out += clients.getContent()[2] + ":   " +clients.getContent()[0] + ":" + clients.getContent()[1];
            out += "\n";
            clients.next();
        }
        return out;
    }
    /**
     * Returns the client Names as an array
     * @return 
     */
    public String[] clientNamestoArray(){
        String tmp = "";
        clients.toFirst();
        while (clients.hasAccess() && !clients.isEmpty()) {
            tmp += clients.getContent()[2] + " ";
            clients.next();
        }
        String[] out = tmp.split(" ");
        return out;
    }
    /**
     * Returns the messages as a String
     * @return 
     */
    public String messagesToString(){
        String out = "";
        messages.toFirst();
        while (!messages.isEmpty() && messages.hasAccess()) {
            out += messages.getContent();
            out += "\n";
            messages.next();
        }
        return out;
    }
    
    
    /**
     * Is called when a new connection is established from a client to the server.
     * @param pClientIP
     * @param pClientPort 
     */
    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {
    }

    /**
     * is called when a new message arrives
     * @param pClientIP
     * @param pClientPort
     * @param pMessage 
     */
    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        messages.append(pMessage);
        String[] message = pMessage.split(" ");
        switch(message[0]){
            case "CONNECT":
                addConnection(pClientIP, pClientPort, message[1]);
                break;
            case "MESSAGE":
                newMessage(message, pClientIP, pClientPort);
                break;
            default:
                throw new IllegalArgumentException("Unknown Identifier");
        }
    }

    /**
     * is called when an existing connection is terminated
     * @param pClientIP
     * @param pClientPort 
     */
    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {
        removeConnection(pClientIP, pClientPort);
    }

    /**
     * Returns the client List
     * @return 
     */
    public List<String[]> getClients() {
        return clients;
    }
    
    /**
     * checks whether a name is unique
     * @param name
     * @return true if name is unique, otherwise false
     */
    private boolean isNameUnique(String name){
        clients.toFirst();
        boolean isUnique = true;
        while(clients.hasAccess() && !clients.isEmpty() && isUnique){
            if(clients.getContent()[2].equalsIgnoreCase(name))
                isUnique = false;
            clients.next();
        }
        return isUnique;
    }
    /**
     * Returns the client's port
     * @param name
     * @return 
     */
    public int getClientPort(String name){
        clients.toFirst();
        while(clients.hasAccess() && !clients.isEmpty()){
            if(clients.getContent()[2].equalsIgnoreCase(name))
                return Integer.valueOf(clients.getContent()[1]);
            clients.next();
        }
        return -1;
    }
    /**
     * Returns the client's IP
     * @param name
     * @return 
     */
    public String getClientIP(String name){
        clients.toFirst();
        while(clients.hasAccess() && !clients.isEmpty()){
            if(clients.getContent()[2].equalsIgnoreCase(name))
                return clients.getContent()[0];
            clients.next();
        }
        return null;
    }

    /**
     * Is called from processMessage() if the first part of the message is "MESSAGE".
     * Sends the message to the right receiver.
     * @param message
     * @param ip
     * @param port 
     */
    private void newMessage(String[] message, String ip, int port) {
        clients.toFirst();
        String sender = "";
        String receiver = message[1];
        while (!clients.isEmpty() && clients.hasAccess()) {
            if(clients.getContent()[0].equalsIgnoreCase(ip) && clients.getContent()[1].equalsIgnoreCase(String.valueOf(port))){
                sender = clients.getContent()[2];
                message[1] = sender;
                break;
            }
            clients.next();
        }
        String out = "";
        for(String s : message){
            out += s + " ";
        }
        out= out.trim();
        if(receiver.equalsIgnoreCase("global")){
            out = out.replace("MESSAGE", "GLOBAL");
            out = out.replace(sender, sender +":");
            clients.toFirst();
            while (!clients.isEmpty() && clients.hasAccess()) {
                if(!sender.equalsIgnoreCase(clients.getContent()[2])){
                    this.send(clients.getContent()[0], Integer.valueOf(clients.getContent()[1]), out);
                }
                clients.next();
            }
        }else
            this.send(getClientIP(receiver), getClientPort(receiver), out);
    }

    

    
}
