package org.example.aplicacio1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Localitzacio extends Activity implements OnClickListener, OnLongClickListener, OnGesturePerformedListener{

	public static MagatzemPuntuacions magatzem = new MagatzemPuntuacionsArray();
	private Animation animacio, troll;
	private Button bSalir, bSobre, bConfigurar, bJugar;
	private ImageView android;
	private GestureLibrary llibreria;
	
	// Musica --------------------//
	private MediaPlayer mp;
	int pos;
	//------------------------------//
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		llibreria = GestureLibraries.fromRawResource(this, R.raw.gestures);
		
		bSobre = (Button)findViewById(R.id.button3);
		bSobre.setOnClickListener(this);
		bSobre.setOnLongClickListener(this);
		
		bSalir = (Button)findViewById(R.id.button4);
		bSalir.setOnClickListener(this);

		
		bConfigurar = (Button)findViewById(R.id.button2);
		bConfigurar.setOnClickListener(this);
		
		bJugar = (Button)findViewById(R.id.button1);
		bJugar.setOnClickListener(this);
		
		//animacio = AnimationUtils.loadAnimation(this,R.anim.animacio);
		android = (ImageView)findViewById(R.id.android);
		troll = AnimationUtils.loadAnimation(this,R.anim.troll);
		//bSobre.startAnimation(animacio);
		//bJugar.startAnimation(animacio);
		//bConfigurar.startAnimation(animacio);
		//bSalir.startAnimation(animacio);
		
		llibreria = GestureLibraries.fromRawResource(this, R.raw.gestures);

		if (!llibreria.load()) finish();

		GestureOverlayView gesturesView = (GestureOverlayView)findViewById(R.id.gestures);

		gesturesView.addOnGesturePerformedListener(this);
		
		Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
		
		mp=MediaPlayer.create(this, R.raw.audio);
	    mp.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		// Crea l'objecte en Java que representa el menu
		MenuInflater infl = getMenuInflater();
		// Associa el menu creat en XML a l'objecte Java
		infl.inflate(R.menu.menu, menu);
		// indica que es vol visualitzar (activar) el menu
		return true;
	}
	// Cada vegada que es selecciona el menu es crida el seguent metode0000000000000000000000000000000000000000000000000000000.............22222222222222222222222222222222222.
	
	// per que tracti els events capturats
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			// per cada item del menu crearem un case
		case R.id.sobre:
			llancarSobre(null);
			break;
			
		case R.id.config:
			llancarPreferencias(null);
            break;
		}
		// indica que l'event ha sigut tractat i que no s'ha de propagar mes
		return true;
	}
	

	public void llancarSobre(View view) {
		Intent i=new Intent(this, Sobre.class);
		startActivity(i);
	}
	
	public void llancarPreferencias(View view) {
		Intent i=new Intent(this, Preferencias.class);
		startActivity(i);
	}
	
	public void llancarPuntuacions(View view){
		Intent i=new Intent(this, Puntuacions.class);
		startActivity(i);
	}
	
	public void llansarJoc(View view){
		Intent i=new Intent(this, Joc.class);
		startActivity(i);
	}
	
	public void salir(View view) {
		//
		llancarPuntuacions(view);
		//finish();
	}
	
	public void mostrarPreferencies(View view) { 
		SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
				String str = "Escoltar Musica: "+ pref.getBoolean("musica", false) + " \n" +
						"Número de fragments " + pref.getString("fragments","") + " \n" + 
						"Tipus de grafics" + pref.getString("grafics","") ;

		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onLongClick(View v) {
		android.startAnimation(troll);
		return true;
	}

	@Override
	// metode sense emprar classe interna
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button1:
				llansarJoc(null);
				break;
			case R.id.button2:
				llancarPreferencias(null);
				break;
			case R.id.button3:
				llancarSobre(null);
				break;
			case R.id.button4:
				llancarPuntuacions(null);
				break;
		}
	}

	public void onGesturePerformed(GestureOverlayView ov, Gesture gesture) {
		ArrayList<Prediction> predictions = llibreria.recognize(gesture);
		Prediction prediction = predictions.get(0);
			if (prediction.name.equals("acercade")) {
				llancarSobre(null);
			} else if (prediction.name.equals("cancelar")) {
				llancarPreferencias(null);
			} else if (prediction.name.equals("configurar")) {
				llancarPreferencias(null);
			} else if (prediction.name.equals("jugar")) {
				llansarJoc(null);
			} else if (prediction.name.equals("troll")) {
				android.startAnimation(troll);
			}
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		super.onPause();
		mp.pause();
		pos=mp.getCurrentPosition();
	}


	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
		super.onRestart();
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		super.onResume();
		mp.seekTo(pos);
		mp.start();
		Toast.makeText(this, "posicio musica: " + pos, Toast.LENGTH_SHORT).show();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
		super.onStart();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		super.onStop();
	}
	
	protected void onDestroy() {
		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle guardarEstat) {
		super.onSaveInstanceState(guardarEstat);
		guardarEstat.putInt("posicio", pos);
	}
	@Override
	protected void onRestoreInstanceState(Bundle recEstat) {
		super.onRestoreInstanceState(recEstat);
		pos=recEstat.getInt("posicio");
	}
}
