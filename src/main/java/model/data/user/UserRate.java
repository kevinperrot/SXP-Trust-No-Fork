/* Copyright 2015 Schlechta.

   This file is part of SXP.

   SXP is free software: you can redistribute it and/or modify it 
   under the terms of the GNU Lesser General Public License as published 
   by the Free Software Foundation, version 3.

   SXP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
   without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
   PURPOSE.  See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with SXP. 
   If not, see <http://www.gnu.org/licenses/>. */

package model.data.user;

import model.advertisement.AbstractAdvertisement;
import org.jdom2.Element;

/**
 * This class contains a set of two rates that one user can give to another one.
 * The rapidity and conformity of a transaction is so evaluated during this process.
 */
public class UserRate extends AbstractAdvertisement{
	private float rapidity;
	private float conformity;

	/**
	 * Create a new couple of rating through two given values.
	 * @param rapidity
	 * @param conformity
	 */
	public UserRate(float rapidity, float conformity){
		super();
		this.rapidity = rapidity;
		this.conformity = conformity;
	}

	public UserRate(UserRate rate){
		super();
		this.rapidity = rate.getRapidity();
		this.conformity = rate.getConformity();
	}

	/**
	 * Create a default couple of rating both set to undefined value (ie: -1).
	 */
	public UserRate(){ 
		super();
		this.rapidity = -1;
		this.conformity = -1;
	}

	/**
	 * Construct a new personal user rating based on a XML, well and known formated string.
	 * @param XML
	 */
	public UserRate(String XML) {
		super(XML);
	}

	/**
	 * Construct a new personal user rating based on an XML element
	 * @param u
	 */
	public UserRate(Element u) {
		super(u);
	}

	@SuppressWarnings("rawtypes")
	public UserRate(net.jxta.document.Element u) {
		super(u);
	}

	// Getters
	public float getRapidity() {
		return rapidity;
	}

	public float getConformity() {
		return conformity;
	}

	// Setters
	public void setRapidity(float rapidity) {
		this.rapidity = (rapidity < 0 || rapidity > 5) ? -1 : rapidity;
	}

	public void setConformity(float conformity) {
		this.conformity = (conformity < 0 || conformity > 5) ? -1 : conformity;
	}

	// Advertisement

	/**
	 * Used to define Keys and initialize some values
	 */
	@Override
	protected void setKeys() {
		this.addKey("rapidity", true, true);
		this.addKey("conformity", true, true);
	}

	/**
	 * Used to add all keys
	 */
	@Override
	protected void putValues() {
		this.addValue("rapidity", Float.toString (this.getRapidity()));
		this.addValue("conformity", Float.toString (this.getConformity()));
	}

	@Override
	protected String getAdvertisementName() {
		return this.getClass().getName();
	}

	@Override
	protected boolean handleElement(org.jdom2.Element e) {
		String val = e.getText();
		switch(e.getName()) {
			case "rapidity": setRapidity(Float.parseFloat(val)); return true;
			case "conformity": setConformity(Float.parseFloat(val)); return true;
		}
		return false;
	}

	@Override
	public UserRate clone(){
		return new UserRate(this.getRapidity(), this.getConformity());
	}

	@Override
	public String getSimpleName() {
		return getClass().getSimpleName();
	}

	/**
	 * @return boolean 0 if both are identical, 1 else
	 */
	public int compareTo(UserRate rate) {
		if(this.equals(rate))
			return 0;
		if(this.getRapidity() != (rate.getRapidity()))
			return this.getRapidity() > rate.getConformity() ? 1 : -1;
		if(this.getConformity() != (rate.getRapidity()))
			return this.getConformity() > rate.getConformity() ? 1 : -1;

		return 0;
	}

	public boolean equals(Object o){
		if(!(o instanceof UserRate))
			return false;
		UserRate rate = (UserRate) o;

		if(this.getRapidity() != (rate.getRapidity()) ||
			this.getConformity() != (rate.getConformity()))
			return false;
		return true;
	}
}
