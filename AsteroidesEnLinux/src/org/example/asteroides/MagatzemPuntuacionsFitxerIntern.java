package org.example.asteroides;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.entity.InputStreamEntity;

import android.content.Context;
import android.util.Log;

public class MagatzemPuntuacionsFitxerIntern implements MagatzemPuntuacions{

	private static String FITXER = "puntuacions.txt";
	private Context context;
	
	public MagatzemPuntuacionsFitxerIntern(Context context) {
		this.context = context;
	}

	@Override
	public void guardarPuntuacio(int punts, String nom, long data) {
		try {
			FileOutputStream f = context.openFileOutput(FITXER, Context.MODE_APPEND);
			String text = punts + " " + nom + "\n";
			f.write(text.getBytes());
			f.close();
		}catch(Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
	}

	@Override
	public Vector<String> llistarPuntuacions(int quantitat) {
		Vector<String> result = new Vector<String>();
		try{
			FileInputStream f = context.openFileInput(FITXER);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(f));
			int n= 0;
			String linia;
			do {
				linia = entrada.readLine();
				if (linia != null){
					result.add(linia);
					n++;
				} 
			} while(n < quantitat && linia != null);
			f.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
		return result;
	}
	
	
	
}
