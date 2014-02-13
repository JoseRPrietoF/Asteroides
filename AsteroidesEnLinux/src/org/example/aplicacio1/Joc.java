package org.example.aplicacio1;

import android.app.Activity;
import android.os.Bundle;

public class Joc extends Activity{
	
	private VistaJoc vistaJoc;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Mostra el layout del ListView
		setContentView(R.layout.joc);
		
		vistaJoc = (VistaJoc)findViewById(R.id.VistaJoc);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		vistaJoc.getFil().aturar();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		vistaJoc.getFil().pausar();
		vistaJoc.desactivarSensors();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		vistaJoc.getFil().reanudar();
		vistaJoc.activarSensors();
	}
	
	
}
