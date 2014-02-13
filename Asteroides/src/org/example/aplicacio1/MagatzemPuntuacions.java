package org.example.aplicacio1;

import java.util.Vector;

public interface MagatzemPuntuacions {
	public void guardarPuntuacio(int punts, String nomn, long data);
	public Vector<String> llistarPuntuacions(int quantitat);
}
