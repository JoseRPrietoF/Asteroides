package org.example.aplicacio1;

import java.util.List;
import java.util.Set;
import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class VistaJoc extends View implements SensorEventListener{
	// FILLS I TEMPS
	// FILL encarregat de processar el joc
	private ThreadJoc fil = new ThreadJoc();
	// cada quan volem processar canvis (ms)
	private static int PERIODE_PROCES = 50;
	// Quan es va realitzar el darrer poces
	private long darrerProces = 0;
	// Variables per ASTEROIDES
	private Vector<Grafic> Asteroides; // Vector amb els asteroides
	private int numAsteroides = 15; // Numero inicial de Asteroides
	private int numFragments = 3; // Fragments en que es divideix
	private Drawable drawableAsteroide[] = new Drawable[numFragments];
	// Variables per la NAU
	private Grafic nau;
	private int girNau; // increment de direcio
	private float acceleracioNau;
	// increment estandard de gir i acceleracio
	private static final int PAS_GIR_NAU = 5;
	private static final float PAS_ACCELERACIO_NAU = 0.5f;
	// Manejador d'events de la pantalla tactil per la nau
	private float mX = 0, mY = 0;
	private boolean dispar = false;
	
	private boolean hihaValorInicial = false;
	private float valorInicial;
	
	// Peferencies
	private boolean musica = false;
	private boolean multijugadors = false;
	private int maxJugador = 3;
	
	// missil
	private Grafic missil;
	private int tempsMissil;
	private Vector<Grafic> missils;
	private static int PAS_VELOCITAT_MISSIL = 12;
	private Vector<Integer> tempsMissils;
	Drawable drawableMissil;
	
	private boolean teclat = false,tactil = true ,sensors = false;
	Context context;
	
	// Sensors
	SensorManager mSensorManager;
	
	//Variables pel so
	SoundPool soundPool;
	int idDispar, idExplosio;
	final int MAX_REPRODUCCIONS = 5;
	
	public VistaJoc(Context context, AttributeSet attrs) {
		super(context, attrs);
		// Declara i obte les imatges
		this.context = context;
		Drawable drawableNau;
		
		SharedPreferences pref = context.getSharedPreferences("org.example.aplicacio1_preferences", Context.MODE_PRIVATE);
		if (pref.getString("grafics","1").equals("0")) {
			Path pathAsteroide = new Path();
			pathAsteroide.moveTo((float)0.3, (float)0.0);
			pathAsteroide.lineTo((float)0.6, (float)0.0);
			pathAsteroide.lineTo((float)0.6, (float)0.3);
			pathAsteroide.lineTo((float)0.8, (float)0.2);
			pathAsteroide.lineTo((float)1.0, (float)0.4);
			pathAsteroide.lineTo((float)0.8, (float)0.6);
			pathAsteroide.lineTo((float)0.9, (float)0.9);
			pathAsteroide.lineTo((float)0.8, (float)1.0);
			pathAsteroide.lineTo((float)0.4, (float)1.0);
			pathAsteroide.lineTo((float)0.0, (float)0.6);
			pathAsteroide.lineTo((float)0.0, (float)0.2);
			pathAsteroide.lineTo((float)0.3, (float)0.0);
			
			for (int i=0; i<3; i++) {
				ShapeDrawable dAsteroide = new ShapeDrawable (new PathShape(pathAsteroide,1 ,1));
				dAsteroide.getPaint().setColor(Color.WHITE);
				dAsteroide.getPaint().setStyle(Style.STROKE);
				dAsteroide.setIntrinsicWidth(50-i*14);
				dAsteroide.setIntrinsicHeight(50-i*14);
				drawableAsteroide[1] = dAsteroide;
			}
			
			setBackgroundColor(Color.BLACK);
			// Nau
			Path pathNau = new Path();
			pathNau.lineTo((float)1.0, (float)0.5);
			pathNau.lineTo((float)0.0, (float)0.1);
			pathNau.lineTo((float)0.0, (float)0.0);
			
			ShapeDrawable dNau = new ShapeDrawable (new PathShape(pathNau,1 ,1));
			dNau.getPaint().setColor(Color.BLUE);
			dNau.getPaint().setStyle(Style.STROKE);
			dNau.setIntrinsicWidth(50);
			dNau.setIntrinsicHeight(35);
			drawableNau = dNau;
			
			// Grafic vectorial missil
			ShapeDrawable dMissil = new ShapeDrawable(new RectShape());
			dMissil.getPaint().setColor(Color.WHITE);
			dMissil.getPaint().setStyle(Style.STROKE);
			dMissil.setIntrinsicWidth(15);
			dMissil.setIntrinsicHeight(3);
			drawableMissil = dMissil;
			
		} else {
			drawableAsteroide[0] = context.getResources().getDrawable(R.drawable.asteroide1);
			drawableAsteroide[1] = context.getResources().getDrawable(R.drawable.asteroide2);
			drawableAsteroide[2] = context.getResources().getDrawable(R.drawable.asteroide3);
			drawableNau = context.getResources().getDrawable(R.drawable.nau);
			drawableMissil = context.getResources().getDrawable(R.drawable.missil1);
		}
		
		// inicialitza nau
		nau = new Grafic(this, drawableNau);
		// missil
		missils = new Vector<Grafic>();
		tempsMissils = new Vector<Integer>();
		missil = new Grafic(this, drawableMissil);
		// inicialitza els asteroides
		Asteroides = new Vector<Grafic>();
		for (int i = 0; i<numAsteroides; i++) {
			Grafic asteroide = new Grafic(this, drawableAsteroide[0]);
			asteroide.setIncY(Math.random()*4-2);
			asteroide.setIncX(Math.random()*4-2);
			asteroide.setAngle((int) (Math.random()*360));
			asteroide.setRotacio((int) (Math.random()*8-4));
			Asteroides.add(asteroide);
		}
		
		
		// Register el sensor dorientacio i indica gestio d'events
		activarSensors();
		
		
		// Preferencies
		Set<String> set = pref.getStringSet("control_key",null);
		for (String str: set){
			if (str.equals("1")) teclat = true;
			if (str.equals("2")) tactil = true;
			if (str.equals("3")) sensors = true;
		}
		
		multijugadors = pref.getBoolean("activarMultKey",false);

		musica = pref.getBoolean("p1_key",false);

		maxJugador = pref.getInt("maxJugadorsKey", 3);
		numFragments = pref.getInt("p3_key", 3);
		
		// Inicialitza so
		soundPool = new SoundPool(MAX_REPRODUCCIONS, AudioManager.STREAM_MUSIC, 0);
		idDispar = soundPool.load(context, R.raw.dispar, 0);
		idExplosio = soundPool.load(context, R.raw.explosio, 0);
	}
	
	public boolean onTouchEvent(MotionEvent mevent) {
		super.onTouchEvent(mevent);
		if (!tactil) return true;
		float x = mevent.getX();
		float y = mevent.getY();
		// canviar 6 2 i 25 per ajustar els moviments
		switch(mevent.getAction()) {
		case MotionEvent.ACTION_DOWN:
			dispar = true;
			break;
		case MotionEvent.ACTION_MOVE:
			float dx = Math.abs(x-mX);
			float dy = Math.abs(y-mY);
			if (dy<6 && dx>6) {
				girNau = Math.round( (x-mX) / 2);
				dispar = false;
			} else if (dx < 6  && dy > 6) {
				acceleracioNau = Math.round((mY - y) / 25);
				dispar = false;
			}
			break;
		case MotionEvent.ACTION_UP:
			girNau = 0;
			acceleracioNau = 0;
			if (dispar) {
				ActivaMissil();
			}
			break;
		}
		mX = x;
		mY = y;
		return true;
		
	}
		
	// ACUTALITZA ELS VALORS DELS ELEMENTS
	// ES A DIR, GESTIONA ELS MOVIMENTS
	synchronized protected void actualitzaFisica() {
		// Hora actual en milisegons
		long ara = System.currentTimeMillis();
		//No fer res si el perdiode de proces NO s'ha complert
		if ( darrerProces + PERIODE_PROCES > ara) {
			return;
		}
		// per una execucio en temps real calculem el retard
		double retard = (ara - darrerProces) / PERIODE_PROCES;
		darrerProces = ara; // Per la propera vegada
		// actualitzem velocitat i direccio de la nau a parttir de 
		// girNau i acceleració segons i l'entrada del jugador
		nau.setAngle( (int)(nau.getAngle() + girNau*retard));
		double nIncX = nau.getIncX() + acceleracioNau * Math.cos(Math.toRadians(nau.getAngle())) * retard;
		double nIncY = nau.getIncY() + acceleracioNau * Math.sin(Math.toRadians(nau.getAngle())) * retard;
		// Actualitzem si el modul de la velocitat no passa el maxim
		if (Math.hypot(nIncX, nIncY) <= Grafic.getMaxVelocitat()) {
			nau.setIncX(nIncX);
			nau.setIncY(nIncY);
		}
		// Actualitzem les posicion X i Y
		nau.incrementaPos(retard);
		for (Grafic asteroide: Asteroides) {
			asteroide.incrementaPos(retard);
		}
		
		// Actualizem posicio del missil
		// El missil i els seu temps van a la par
		if (!missils.isEmpty()) {
			for (Grafic missil : missils) {
				missil.incrementaPos(retard);
			}
			for (int i = 0; i < tempsMissils.size(); i++) {
				tempsMissils.set(i, tempsMissils.get(i) - 1);
				// Fall al destruir els dos, crear un aray per definir quins destruirem i despres aplicarlo. removeALL(array)
				if (tempsMissils.elementAt(i) < 0) {
					missils.remove(i);
					tempsMissils.remove(i);
				} else {
					for (int e = 0; e < Asteroides.size(); e++)
						for (int j = 0; j < missils.size(); j++) {
							if (missils.elementAt(j).verificaColisio(Asteroides.elementAt(e))) {
								detrueixAsteroide(e);
								destrueixMissil(j);
								break;
							}
						}
				} // Fi else 

			} // fi for i - > tempsMissils

		}
		
	}
	
	// Metodes auxiliars
	private void detrueixAsteroide(int i) {
		int tam;
		if (Asteroides.get(i).getDrawable()!=drawableAsteroide[2]){
			if (Asteroides.get(i).getDrawable()==drawableAsteroide[1]){
				tam = 2;
			}else {
				tam = 1;
			}
			for (int n=0; n<numFragments; n++) {
				Grafic asteroide = new Grafic (this, drawableAsteroide[tam]);
				asteroide.setPosX(Asteroides.get(i).getPosX());
				asteroide.setPosY(Asteroides.get(i).getPosY());
				asteroide.setIncX(Math.random()*7.2-tam);
				asteroide.setIncY(Math.random()*7.2-tam);
				asteroide.setAngle((int)(Math.random()*360));
				asteroide.setRotacio((int)(Math.random()*8-4));
				Asteroides.add(asteroide);
			}
		}
		Asteroides.remove(i);
	}
	// El missil i els seu temps van a la par
	private void destrueixMissil(int i){
		soundPool.play(idExplosio, 1, 1, 0, 0, 1);
		missils.remove(i);
		tempsMissils.remove(i);
	}
	
	private void ActivaMissil() {
		soundPool.play(idDispar, 1, 1, 1, 0, 1);
		missil = new Grafic(this, drawableMissil);
		missil.setPosX(nau.getPosX() + nau.getAmplada() / 2 - missil.getAmplada()/2);
		missil.setPosY(nau.getPosY() + nau.getAltura() / 2 - missil.getAltura()/2);
		missil.setAngle(nau.getAngle());
		missil.setIncX(Math.cos(Math.toRadians(missil.getAngle())) * PAS_VELOCITAT_MISSIL);
		missil.setIncY(Math.sin(Math.toRadians(missil.getAngle())) * PAS_VELOCITAT_MISSIL);
		missils.add(missil);
		
		tempsMissil = (int)Math.min(this.getWidth() / Math.abs(missil.getIncX()), this.getHeight() / Math.abs(missil.getIncY())) - 2;
		tempsMissils.add(tempsMissil);
	}
	
	// fi metodes auxulars
	protected void onSizeChanged(int ample, int alt, int ample_alt, int alt_ant) {
		super.onSizeChanged(ample, alt, ample_alt, alt_ant);
		// Una vegada que coneixem la nstra amplada i altura
		// Posiciona els asteroides
		nau.setPosX(ample / 2);
		nau.setPosY(alt / 2);
		for (Grafic asteroide : Asteroides) {
			// veirifiquem que els asteroides no surtin molt aprop de la nau
			do {
				asteroide.setPosX(Math.random()
						* (ample - asteroide.getAmplada()));
				asteroide
						.setPosY(Math.random() * (alt - asteroide.getAltura()));
			} while (asteroide.distancia(nau) < (ample + alt) / 5);

		}
		// Posicionem la nau en el centre de la vista

		// Lansa un nou fill
		darrerProces = System.currentTimeMillis();
		fil.start();

	}
		

	private Context getAplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	synchronized protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Dibuixa els asteroides
		for (Grafic asteroide: Asteroides) {
			asteroide.dibuixaGrafic(canvas);
		}
		// Dibuixem la nau
		nau.dibuixaGrafic(canvas);
		if (!missils.isEmpty()){
			for (Grafic misil: missils)
				misil.dibuixaGrafic(canvas);
		}
	}
	

	protected void desactivarSensors(){
		if (mSensorManager == null ) return;
		mSensorManager.unregisterListener(this);
	}
		
	protected void activarSensors() {
		if (!sensors) return;
		mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> llistaSensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if(!llistaSensors.isEmpty()) {
			Sensor orientacioSensor = llistaSensors.get(0);
			mSensorManager.registerListener(this, orientacioSensor, SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	// Classe que crea un nou fil
	class ThreadJoc extends Thread {
		
		private boolean pausa, corrent;
		
		public synchronized void pausar() {
			pausa = true;
		}
		public synchronized void reanudar() {
			pausa = false;
			notify();
		}
		public synchronized void aturar() {
			corrent = false;
			if (pausa) reanudar();
		}
		public void run() {
			corrent = true;
			while (corrent) {
				actualitzaFisica();
				synchronized (this) {
					while (pausa) {
						try {
							wait();
						} catch (Exception e){
							System.out.println("Excepcio del Wait()");
						}
					}
				}
			}
		}
	}

	// Gestio devents
	public boolean onKeyDown (int codiTecla, KeyEvent event ) {
		super.onKeyDown(codiTecla, event);
		// Suposem que processem la pulsacio;
		boolean processada = true;
		switch (codiTecla) {
		case KeyEvent.KEYCODE_DPAD_UP:
			acceleracioNau =+ PAS_ACCELERACIO_NAU;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			girNau =- PAS_GIR_NAU;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			girNau =+ PAS_GIR_NAU;
			break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
		case KeyEvent.KEYCODE_ENTER:
			ActivaMissil();
			break;
		default:
			// Si arriba aqui, o hi ha pulsacio que interesi
			processada = false;
			break;
		}
		return processada; // hem processat levent
	}
	
	public boolean  onKeyUp(int codiTecla, KeyEvent event) {
		super.onKeyUp(codiTecla, event);
		if (!teclat) return true;
		// Suposem que processem la pulsació
		boolean processada=true;
		switch (codiTecla) {
		case KeyEvent.KEYCODE_DPAD_UP:
			acceleracioNau = 0;
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			girNau = 0;
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			girNau = 0;
			break;
		case KeyEvent.KEYCODE_DPAD_CENTER:
		case KeyEvent.KEYCODE_ENTER:
			ActivaMissil();
			break;
		default:
			// Si arriba aqui, o hi ha pulsacio que interesi
			processada = false;
			break;
		}
       	return processada; // Hem processat l'event
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if (!sensors) return;
		float valor = event.values[1]; // eix Y
		if (!hihaValorInicial) {
			valorInicial = valor;
			hihaValorInicial = true;
		}
		girNau = (int) (valor - valorInicial) / 3;
	}

	public ThreadJoc getFil() {
		return fil;
	}
	
	
}
