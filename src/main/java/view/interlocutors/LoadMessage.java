package view.interlocutors;

import java.io.IOException;

import javax.websocket.Session;

import model.data.user.UserMessage;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;

public class LoadMessage extends AbstractInterlocutor {

	public LoadMessage() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String id = c.getString("id");
			UserMessage message = ManagerBridge.getMessage(id);
			JSONObject data = new JSONObject();
			JSONObject content = new JSONObject();
			if(message == null){
				data.put("query", "messageUnLoaded");
				content.put("error", "unknow message");
			}else{
				data.put("query", "messageLoaded");
				content.put("id", message.getID());
				content.put("message", message.getContent());
				content.put("date", message.getDate());
				content.put("from", message.getSender().getPublicKey());
			}
			data.put("content", content);
			com.sendText(data.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			this.reset();
		}
	}

}
