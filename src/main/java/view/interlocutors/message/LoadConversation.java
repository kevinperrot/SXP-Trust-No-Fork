package view.interlocutors.message;

import java.util.ArrayList;

import model.data.user.UserMessage;
import view.interlocutors.AbstractInterlocutor;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadConversation extends AbstractInterlocutor {

	public LoadConversation() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
			try {
				ArrayList<UserMessage> messages = ManagerBridge.getConversation();
				//AsymKeysImpl key = ManagerBridge.getCurrentUser().getKeys();
				for (UserMessage message : messages) {
				JSONObject data = new JSONObject();
					data.put("query", "conversationLoaded");
					
					JSONObject content = new JSONObject();
					content.put("date", message.getDate());
					content.put("id", message.getID());
					content.put("from", message.getSender().getPublicKey());
					content.put("subject", message.getSubject());
					data.put("content", content);
					com.sendText(data.toString());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				this.reset();
			}
	}

}