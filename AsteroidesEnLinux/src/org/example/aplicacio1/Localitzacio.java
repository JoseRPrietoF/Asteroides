package org.example.aplicacio1;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Localitzacio extends Activity implements OnClickListener,
		OnLongClickListener, OnGesturePerformedListener {

	public static MagatzemPuntuacions magatzem = new MagatzemPuntuacionsArray();
	private Animation animacio, troll;
	private Button bSalir, bSobre, bConfigurar, bJugar;
	private ImageView android;
	private GestureLibrary llibreria;
	private SharedPreferences pref;

	// Musica --------------------//
	private boolean musica = false;
	private MediaPlayer mp;
	private String song = "";
	int pos;

	// ------------------------------//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		llibreria = GestureLibraries.fromRawResource(this, R.raw.gestures);

		bSobre = (Button) findViewById(R.id.button3);
		bSobre.setOnClickListener(this);
		bSobre.setOnLongClickListener(this);

		bSalir = (Button) findViewById(R.id.button4);
		bSalir.setOnClickListener(this);

		bConfigurar = (Button) findViewById(R.id.button2);
		bConfigurar.setOnClickListener(this);

		bJugar = (Button) findViewById(R.id.button1);
		bJugar.setOnClickListener(this);

		// animacio = AnimationUtils.loadAnimation(this,R.anim.animacio);
		android = (ImageView) findViewById(R.id.android);
		troll = AnimationUtils.loadAnimation(this, R.anim.troll);
		// bSobre.startAnimation(animacio);
		// bJugar.startAnimation(animacio);
		// bConfigurar.startAnimation(animacio);
		// bSalir.startAnimation(animacio);

		llibreria = GestureLibraries.fromRawResource(this, R.raw.gestures);

		if (!llibreria.load())
			finish();

		GestureOverlayView gesturesView = (GestureOverlayView) findViewById(R.id.gestures);

		gesturesView.addOnGesturePerformedListener(this);

		pref = getSharedPreferences("org.example.aplicacio1_preferences",
				Context.MODE_PRIVATE);
		mp = new MediaPlayer();
		
		elegirMusica();
		if (musica){
			mp.start();
		}
		
		startService(new Intent(Localitzacio.this, Servei.class));

	}

	private boolean elegirMusica() { // Retorna true si has elegit una canso
										// nova
										// Tamb� vigila que estigui la opcio de
										// musica enabled
		musica = pref.getBoolean("musica", false);

		if (musica) {
			// Preferencies
			String song = pref.getString("song_key", null);
			if (this.song.equals(song))
				return false;
			
			if (song.equals("daft_punk_getlucky"))
				mp = MediaPlayer.create(this, R.raw.daft_punk_getlucky);
			else if (song.equals("coldplay_paradise"))
				mp = MediaPlayer.create(this, R.raw.coldplay_paradise);
			else if (song.equals("europe_final_countdown"))
				mp = MediaPlayer.create(this, R.raw.europe_final_countdown);
			else if (song.equals("beatles_let_it_be"))
				mp = MediaPlayer.create(this, R.raw.beatles_let_it_be);

			this.song = song;
		}
		return true;
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

	// Cada vegada que es selecciona el menu es crida el seguent
	// metode0000000000000000000000000000000000000000000000000000000.............22222222222222222222222222222222222.

	// per que tracti els events capturats
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
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
		Intent i = new Intent(this, Sobre.class);
		startActivity(i);
	}

	public void llancarPreferencias(View view) {
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}

	public void llancarPuntuacions(View view) {
		Intent i = new Intent(this, Puntuacions.class);
		startActivity(i);
	}

	public void llansarJoc(View view) {
		Intent i = new Intent(this, Joc.class);
		//  Llança una act mitjançant un objecte intent
		startActivityForResult(i, 1234);
	}

	public void salir(View view) {
		//
		llancarPuntuacions(view);
		// finish();
	}

	public void mostrarPreferencies(View view) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		String str = "Escoltar Musica: " + pref.getBoolean("musica", false)
				+ " \n" + "Número de fragments "
				+ pref.getString("fragments", "") + " \n" + "Tipus de grafics"
				+ pref.getString("grafics", "");

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
		super.onPause();
		mp.pause();
		pos = mp.getCurrentPosition();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		musica = pref.getBoolean("musica", false);
		if (musica) {
			if (elegirMusica()) {
				pos = 0;
			} else
				mp.seekTo(pos);
			mp.start();
		}
	}
	
	@Override
	protected void onActivityResult(int resquestedCode, int resultCode, Intent data){
		super.onActivityResult(resquestedCode, resultCode, data);
		if (resquestedCode==1234 && resultCode== RESULT_OK && data != null){
			int puntuacio = data.getExtras().getInt("puntuacio");
			final String nom = "";
			// ALERT PER AL NOM
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
	       	 builder.setTitle("Donem el teu nom");
	
	       	 // Set up the input
	       	 final EditText input = new EditText(this);
	       	 // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
	       	 input.setInputType(InputType.TYPE_CLASS_TEXT);
	       	 input.setMinLines(6); // 6 lnes
	       	 input.setSingleLine(false);
	       	 input.setText("Jo");
	       	 builder.setView(input);
	
	       	 // Set up the buttons
	       	 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
	       	     @Override
	       	     public void onClick(DialogInterface dialog, int which) {
	       	    	 //arr.add(input.getText().toString());
	       	    	 nom.concat(input.getText().toString());
	       	     }
	       	 });
	       	 builder.show();
	       	 // ------- FI ALERT
	       	 magatzem.guardarPuntuacio(puntuacio, nom, System.currentTimeMillis());
	       	 llancarPuntuacions(null);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	protected void onDestroy() {
		super.onDestroy();
		stopService(new Intent(Localitzacio.this, Servei.class));
	}

	@Override
	protected void onSaveInstanceState(Bundle guardarEstat) {
		super.onSaveInstanceState(guardarEstat);
		guardarEstat.putInt("posicio", pos);
		guardarEstat.putString("song", song);
	}

	@Override
	protected void onRestoreInstanceState(Bundle recEstat) {
		super.onRestoreInstanceState(recEstat);
		pos = recEstat.getInt("posicio");
		song = recEstat.getString("song");
	}
}