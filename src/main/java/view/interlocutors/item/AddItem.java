/* Copyright 2015 Pablo Arrighi, Sarah Boukris, Mehdi Chtiwi, 
   Michael Dubuis, Kevin Perrot, Julien Prudhomme.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */
package view.interlocutors.item;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.ManagerBridge;
import view.interlocutors.AbstractInterlocutor;

public class AddItem extends AbstractInterlocutor {
	
	public AddItem() {
		super();
	}

	@Override
	public void run() {
		if(!isInitialized()) return;
		try {
			JSONObject c = getJSON(content);
			String category;
			category = c.getString("category");
		
			String contact = c.getString("contact");
			String country = c.getString("country");
			String description = c.getString("description");
			String image = c.getString("image");
			String lifeTime = c.getString("lifetime");
			String title = c.getString("title");
			String type = c.getString("type");
			String itemKey = ManagerBridge.addItem(title, category, description, image, country, contact, lifeTime, type);
			
			if(itemKey == null || itemKey.isEmpty()){
				// Send error message
			}else{
				JSONObject data = new JSONObject();
				data.put("query", "itemAdded");
				JSONObject content = new JSONObject();
				content.put("itemKey", itemKey);
				content.put("title", title);
				content.put("description", description);
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
