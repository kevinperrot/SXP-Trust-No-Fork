package controller;

import model.Objet;
import model.User;
import view.Application;

/**
 * Fen�tre de cr�ation/�dition d'une annonce
 * @author 
 * 
 */

public class AnnonceEditor implements Validator{
	
	private boolean proposition, souhait;
	private boolean troc, vente;
	private String title;
	private String resume;
	private String desc;
	private String img;
	private User user;
	private Objet obj = null; /* si l'on souhaite modifier un objet */
	
	public boolean errorProposition, errorSouhait, errorTroc, errorVente, errorTitle, 
					errorResume, errorDesc, errorImg;
	
	public AnnonceEditor(boolean proposition, boolean souhait, 
			boolean troc, boolean vente, String title, 
			String resDesc, String desc, String img) {
		
		this.proposition = proposition;
		this.souhait = souhait;
		this.troc = troc;
		this.vente = vente;
		this.title = title;
		this.resume = resDesc;
		this.desc = desc;
		this.img = img;
		
		user = Application.getInstance().getUsers().getConnectedUser();
		
		errorProposition = errorSouhait = errorTroc = errorVente = errorTitle 
		= errorResume = errorDesc = errorImg = false;
	}

	
	public boolean validate() {
		
		checkCheckBox();
		checkTitle();
		checkResume();
		checkDescription();
		checkImg();
		
		return !(errorProposition || errorSouhait || errorTroc || errorVente || errorTitle 
				|| errorResume || errorDesc || errorImg);
	}
	
	/**
	 * Au moins une case doit �tre coch�e
	 */
	private void checkCheckBox() {
		if(!(troc || vente) || (troc && vente)) errorTroc = errorVente = true;
	}
	
	/**
	 * Le titre doit être d'au moins 3 caractères.
	 */
	private void checkTitle() {
		if(title.length() < 3) errorTitle = true;
	}
	
	/**
	 * Le résumé doit faire au moins 10 caractères.
	 */
	private void checkResume() {
		if(resume.length() < 10) errorResume = true;
	}
	
	/**
	 * La description doit faire au moins 10 caractères.
	 */
	private void checkDescription() {

		String plaintText = desc.replaceAll("<[^>]*>", "").replaceAll("\n", "").replaceAll(" ", "");
		if(plaintText.length() < 10) errorDesc = true;
	}
	
	/**
	 * Le champ image peut être vide, sinon l'image doit être valide
	 */
	private void checkImg() {
		
		if(img == null) return;
	}

	public void setEditObjet(Objet obj) {
		this.obj = obj;
	}
	
	public boolean process() {
		if(user == null) return false;
		
		if(obj == null) {
			Objet obj = new Objet(proposition, souhait, troc, vente, title, resume, desc, img, user);
			obj.setDate(System.currentTimeMillis());
			user.getObjets().add(obj);
			obj.publish(Application.getInstance().getPeer().getDiscovery());
		}
		else {
			obj.setProposition(proposition);
			obj.setSouhait(souhait);
			obj.setTroc(troc);
			obj.setVente(vente);
			obj.setTitre(title);
			obj.setResume(resume);
			obj.setDesc(desc);
			obj.setImg(img);
			obj.update(Application.getInstance().getPeer().getDiscovery());
			
		}	
		return true;	
	}
	
	public String toString() {
		String res = "";
		res += "Proposition: " + proposition + " " + "Souhait: " + souhait + "\n";
		res += "Troc: " + troc + " Argent: " + vente + "\n";
		res += "Titre: " + title + "\n";
		res += "R\u00E9sum\u00E9: " + resume + "\n\n";
		res += "Description: \n" + desc + "\n";
		res += "Image: " + img;
		
		return res;	
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getTrocVente(){
		if(troc)
			return "Troc";
		return "Vente";
	}
	
	public String getPropSouhait(){
		if(proposition)
			return "Proposition";
		return "Souhait";
	}
	
	public String getResDescr(){
		return resume;
	}
	
	public String getDescrComp(){
		return desc;
	}
}