package org.example.aplicacio1;

import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;

public class MagatzemPuntuacionsPreferencies implements MagatzemPuntuacions{

	public static String PREFERENCIES = "puntuacions";
	private Context context;
	
	public MagatzemPuntuacionsPreferencies(Context context) {
		super();
		this.context = context;
	}

	@Override
	public void guardarPuntuacio(int punts, String nom, long data) {
		SharedPreferences preferencies = context.getSharedPreferences(PREFERENCIES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferencies.edit();
		editor.putString("puntuacio", punts + " " + nom);
		editor.commit();
	}

	@Override
	public Vector<String> llistarPuntuacions(int quantitat) {
		Vector<String> result = new Vector<String>();
		SharedPreferences preferencies = context.getSharedPreferences(PREFERENCIES, Context.MODE_PRIVATE);
		String s = preferencies.getString("puntuacio", "");
		if (s != ""){
			result.add(s);
		}
		return result;
	}

}
