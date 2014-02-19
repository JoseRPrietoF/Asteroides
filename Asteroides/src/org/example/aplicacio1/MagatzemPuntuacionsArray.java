package org.example.aplicacio1;

import java.util.Vector;

public class MagatzemPuntuacionsArray implements MagatzemPuntuacions {

	private Vector<String> puntuacions;
	
	
	
	public MagatzemPuntuacionsArray() {
		puntuacions = new Vector<String>();
		puntuacions.add("123000 Pepito Dominguez");
		puntuacions.add("111000 Pedro Martínez");
		puntuacions.add("011000 Paco Pérez");
	}
	
	@Override
	public void guardarPuntuacio(int punts, String nom, long data) {
		// TODO Auto-generated method stub
		puntuacions.add(0, punts + " " + nom);
	}

	@Override
	public Vector<String> llistarPuntuacions(int quantitat) {
		// TODO Auto-generated method stub
		return puntuacions;
	}

}
