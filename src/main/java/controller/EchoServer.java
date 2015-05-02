package controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import model.Application;
import model.user.User;
 
/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo
 */
@ServerEndpoint("/serv") 
public class EchoServer {
	 /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config){
        //System.out.println(session.getId() + " has opened a connection");
    	  System.out.println("Connection Established");
          
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     * @throws IOException 
     */
    @OnMessage
    public void onMessage(String message, Session session){
    	String[] contents = message.split(":");
    	switch (contents[0]) {
		case "/index":
			if(Verifying(contents[1], contents[2])){
				try {
	    			//String res=lire();
	    				//System.out.println("bien recu "+res+" voici");
					session.getBasicRemote().sendText("index.html");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
	    	}
			break;
		case "/register":
			if(add_new_user(contents[1],contents[2], contents[3], contents[4], contents[5], contents[6])){
				System.out.println("un nouveau utilisateur inscrit");
				try {
					session.getBasicRemote().sendText("Se_connecter.html#tologin");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			break;
			
		case "/newobjet":
			try {
				session.getBasicRemote().sendText("new_objet.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/newindex":
			try {
				session.getBasicRemote().sendText("index.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/newchat":
			try {
				session.getBasicRemote().sendText("chat.html");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
			
		default:
			break;
		}
    	
    	
       
    }
   /* @OnMessage
    public void echo(String message, Session session) throws IOException {
    	String[] contents = message.split(":");
    	System.out.println(contents[0]+" "+contents[1]);
    	if(verif(contents[0], contents[1])){
    		session.getBasicRemote().sendText("Se_connecter.html");		
    	}
    	
       
    }
   */
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        System.out.println("Session has ended");
    }
    
    //Verifying user account
    public boolean Verifying(String login, String password){
    	//System.out.println(login+":"+password);
    	if(login.equals("toto") && password.equals("toto"))
    		return true;
    	return false;
    }
    
    //add new user
    public boolean add_new_user(String nick,String password, String name, String firstName, String email, String phone){
    	User user = new User(nick, password, name, firstName, email, phone);
    	Application.getInstance().getManager().addUser(user);
    	return true;
    }
    
    public static String  lire(){
    	String chaine="";
		String fichier ="toto.txt";
		
		//lecture du fichier texte	
		try{
			InputStream ips=new FileInputStream(fichier); 
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				System.out.println(ligne);
				chaine+=ligne+"\n";
				System.out.println("heeeeeeey");
			}
			br.close(); 
		}		
		catch (Exception e){
			System.out.println(e.toString());
		}
		return chaine;
	
    }
    
    
    public static void main(String[] args){
    	
    }
    
}