package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import model.advertisement.AdvertisementInstaciator;
import model.data.manager.Manager;
import model.data.manager.SharingManager;
import model.network.Network;
import model.network.communication.Communication;
import model.network.communication.service.ChatService;
import model.network.communication.service.TransmitAccountService;
import model.network.communication.service.UpdateUser;
import util.VARIABLES;

/**
 * The main class of the software. This class can be instancied only once. (singleton)
 * Instantiate this class will launch the P2P network.
 * @author Prudhomme Julien
 *
 */
public class Application {
	private static Application instance = null; //the current instance of this class.
	
	private EmbeddedRunner server = null;
	private Network network;
	private Manager manager;
	private Communication com;
	private SharingManager sharingManager;
	
	/**
	 * Launch the application.
	 */
	@SuppressWarnings("unchecked")
	public Application(boolean startLocalServer) {
		if(instance != null) {
			throw new RuntimeException("this class can be instancied only once");
		}
		startNetwork();
		AdvertisementInstaciator.RegisterAllAdv();
		manager = new Manager(network);
		manager.recovery(VARIABLES.ManagerFileName);
		startCommunication();
		com.getService(TransmitAccountService.class.getName()).addListener(manager);
		network.addGroup("items");
		network.addGroup("users");
		sharingManager = new SharingManager(manager, network, com,  VARIABLES.ReplicationsAccount, VARIABLES.CheckTimeAccount);
		sharingManager.startSharing();
		
		if(startLocalServer)
			startLocalServer();	
		
		instance = this;
	}
	
	/**
	 * Start the communication package.
	 */
	private void startCommunication() {
		try {
			this.com = new Communication(network);
		} catch (Exception e) {
			e.printStackTrace();
		}
		com.addService(new ChatService());
		com.addService(new TransmitAccountService()); // TODO FIXED ERROR BUT NOT NEEDED, RIGHT ?
		com.addService(new UpdateUser(manager));
	}
	
	/**
	 * Start the network and keep instance reference.
	 * TODO keep reference ?
	 */
	private void startNetwork() {
		network = new Network(9700 + new Random().nextInt(100), VARIABLES.NetworkFolderName, VARIABLES.NetworkPeerName);
		network.setLogger(Level.INFO);
		network.start();
	}
	
	/**
	 * Launch the localServer for websocket communication between HTML5 UI and the P2P network.
	 */
	private void startLocalServer() {
		try {
			server = new EmbeddedRunner(8080).init().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the unique Application instance
	 * @return the Application instance.
	 */
	public static Application getInstance() {
		if(instance == null) {
			try {
				throw new Exception("there is no instance of this class");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	/**
	 * Get the current Network.
	 * @return An instanced and started Network.
	 */
	public Network getNetwork() {
		return network;
	}
	
	/**
	 * Get the current Manager.
	 * @return a manager.
	 */
	public Manager getManager() {
		return manager;
	}
	
	/**
	 * Get the current Communication
	 * @return
	 */
	public Communication getCommunication(){
		return com;
	}
	
	public void stopServer() {
		if(server == null) return;
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Properly close the app : closing network & server, and saving datas.
	 */
	public void close() {
		System.out.println("closing ...");
		stopServer();
		network.stop();
		sharingManager.stopSharing();
		
		File f = new File(".data");
		FileWriter fw = null;
		try {
			fw = new FileWriter(f);
			fw.write(manager.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("bye !");
		
	}
	
	/**
	 * We start the app here !
	 * @param args
	 */
	public static void main(String[] args) {
		new Application(false);
		Network n = Application.getInstance().getNetwork();

		/*try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Application.getInstance().close(); */
	}
}
