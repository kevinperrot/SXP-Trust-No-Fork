package controller.controllerInterface;

import java.util.ArrayList;

import model.data.item.Item;
import model.data.user.User;

public interface ManagerBridgeInterface {
	/**
	 * Add NEW User to current Manager
	 * @param nick
	 * @param password
	 * @param name
	 * @param firstName
	 * @param login
	 * @param login
	 */
	public void registration(String nick, String password, String name, String firstName, String email, String phone);
	/**
	 * Return true if, only if, login exists and password is good
	 * @param login
	 * @param password
	 * @return
	 */
	public boolean login(String nick, String password);
	
	//////////////////////////////////////////////////// ITEMS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Add an current user's item in the manager 
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param country
	 * @param contact
	 * @param lifeTime
	 * @param type
	 */
	public void addItem(String title, String category, String description, String image, String country, String contact, String lifeTime, String type );
	/**
	 * Remove item with title for the current User
	 * @param title
	 */
	public void removeItem(String title);
	/**
	 * Update item with title for the current user
	 * Care, the title can't be changed !
	 * @param title
	 * @param category
	 * @param description
	 * @param image
	 * @param country
	 * @param contact
	 * @param lifeTime
	 * @param type
	 */
	public void updateItem(String title, String category, String description, String image, String country, String contact, String lifeTime, String type );
	/**
	 * Get user's items
	 * @param publicKey of the user
	 * @return ArrayList<Item> user's (who has publicKey) items 
	 */
	public ArrayList<Item> getUserItems(String publicKey);
	/**
	 * Get current user's items
	 * @return ArrayList<Item> current user's items 
	 */
	public ArrayList<Item> getCurrentUserItem();
	////////////////////////////////////////////////// FAVORITES \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	/**
	 * Use to add an item in current user's Favorites
	 * @param item
	 */
	public void addFavoriteItem(Item item);
	/**
	 * Remove an item in current user's Favorite.
	 * @param itemKey - can be given with item.getItemKey()
	 */
	public void removeFavoriteItem(String itemKey);
	/**
	 * Return all item in current user's favorites
	 * @return
	 */
	public ArrayList<String> getFavoriteItemsKey();
	//////////////////////////////////////////////////// USERS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\	
	/**
	 * Get the currentUser, null if no user logged
	 * @return User currentUser
	 */
	public User getCurrentUser();
	/**
	 * Update the current account
	 * @param nick
	 * @param oldPassword
	 * @param newPassword
	 * @param name
	 * @param firstName
	 * @param email
	 * @param phone
	 * @return
	 */
	public boolean updateAccount(String nick, String oldPassword, String newPassword, String name, String firstName, String email, String phone);
	
}
