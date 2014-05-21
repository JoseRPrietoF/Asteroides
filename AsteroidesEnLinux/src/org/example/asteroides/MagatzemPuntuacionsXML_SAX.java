package org.example.asteroides;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.util.Log;

public class MagatzemPuntuacionsXML_SAX implements MagatzemPuntuacions{
	
	private static String FITXER="puntuacions.xml";
	private Context context;
	private LlistaPuntuacions llista;
	private boolean carregadaLlista;
	
	public MagatzemPuntuacionsXML_SAX(Context context){
		this.context = context;
		llista = new LlistaPuntuacions();
		carregadaLlista = false;
	}

	@Override
	public void guardarPuntuacio(int punts, String nom, long data) {
		try{
			if (!carregadaLlista){
				llista.llegirXML(context.openFileInput(FITXER));
			} 
		}catch (FileNotFoundException e) {
				Log.e("Asteroides", e.getMessage(), e);
		} catch (Exception e) {
				Log.e("Asterides", e.getMessage(), e);
		}
		
		llista.nou(punts,nom,data);
		
		try{
			llista.escriureXML(context.openFileOutput(FITXER, Context.MODE_PRIVATE));
		} catch(Exception e){
			Log.e("Asteroides", e.getMessage(), e);
		}
	}

	@Override
	public Vector<String> llistarPuntuacions(int quantitat) {
		if (!carregadaLlista){
			try {
				llista.llegirXML(context.openFileInput(FITXER));
			} catch (Exception e) {
				Log.e("Asteroides", e.getMessage(), e);
			}
		}
		return llista.aVectorString();
	}
	
	private class LlistaPuntuacions {
		
		private class Puntuacio {
			int punts;
			String nom;
			long data;
		}
		
		class ManejadorXML extends DefaultHandler {
			private StringBuilder cadena;
			private Puntuacio puntuacio;
			
			 
			
		}
		
		private List<Puntuacio> llistaPuntuacions;
		
		public LlistaPuntuacions() {
			llistaPuntuacions = new ArrayList<Puntuacio>();
		}
		
		public void nou (int punts, String nom, long data){
			Puntuacio puntuacio = new Puntuacio();
			puntuacio.punts = punts;
			puntuacio.nom = nom;
			puntuacio.data = data;
			llistaPuntuacions.add(puntuacio);
		}
		
		public Vector<String> aVectorString(){
			Vector<String> result = new Vector<String>();
			for (Puntuacio puntuacio: llistaPuntuacions) {
				result.add(puntuacio.nom + " " + puntuacio.punts);
			}
			return result;
		}
		
		public void llegirXML(InputStream entrada) throws Exception {
			SAXParserFactory fabrica = SAXParserFactory.newInstance();
			SAXParser parser = fabrica.newSAXParser();
			XMLReader lector = parser.getXMLReader();
			ManejadorXML manejadorXML = new ManejadorXML();
			lector.setContentHandler(manejadorXML);
			lector.parse( new InputSource(entrada));
			carregadaLlista = true;
		}
		
		public void escriureXML(OutputStream sortida){
			// TODO all
		}
		
		
		
	}

}
