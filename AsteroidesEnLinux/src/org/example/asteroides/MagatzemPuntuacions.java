package org.example.asteroides;

import java.util.Vector;

public interface MagatzemPuntuacions {
	public void guardarPuntuacio(int punts, String nomn, long data);
	public Vector<String> llistarPuntuacions(int quantitat);
}
