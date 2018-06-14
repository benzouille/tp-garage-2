package fr.ocr.ihm.listener;

import java.awt.event.ActionEvent;

import fr.banane.ihm.PopUpDetail;

public class ViewDetailVehiculeListener extends ButtonListener {
	private Integer id;

	public void actionPerformed(ActionEvent e) {
		this.id = Integer.valueOf((String) table.getValueAt(row, this.column-1));
		PopUpDetail detail = new PopUpDetail(null,"Détail",true, id);
		detail.setVisible(true);
	}

	public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	
	
}
