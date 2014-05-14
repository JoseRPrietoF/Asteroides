package org.example.asteroides;

import java.util.Vector;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class MagatzemPuntuacionsPreferencies implements MagatzemPuntuacions{

	private final String SEPARADOR = "///";
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
		String s = preferencies.getString("puntuacio", "");
		s +=punts + " " + nom + SEPARADOR;
		Toast.makeText(context, s, Toast.LENGTH_LONG).show();
		editor.putString("puntuacio", s);
		editor.commit();

	}

	@Override
	public Vector<String> llistarPuntuacions(int quantitat) {
		Vector<String> result = new Vector<String>();
		SharedPreferences preferencies = context.getSharedPreferences(PREFERENCIES, Context.MODE_PRIVATE);
		String s = preferencies.getString("puntuacio", "");
		if (s != ""){
			//result.add(s);
			String[] strArr = s.split(SEPARADOR);
			for(int i =0; i < strArr.length; i++){
				result.add(strArr[i]);
			}
		}
		return result;
	}

}
