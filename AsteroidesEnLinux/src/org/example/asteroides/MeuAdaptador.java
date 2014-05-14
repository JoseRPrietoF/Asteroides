package org.example.asteroides;

import java.util.Vector;

import org.example.aplicacio1.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MeuAdaptador extends BaseAdapter {

	private final Activity activitat;
	private final Vector<String> llista;
	
	public MeuAdaptador (Activity activitat, Vector<String> llista){
		super();
		this.activitat = activitat;
		this.llista = llista;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return llista.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return llista.elementAt(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// La classe Inflater permet accedir des de Java a un objecte crea en XML
		LayoutInflater inflater = activitat.getLayoutInflater();
		View view = inflater.inflate(R.layout.element_llista, null, true);
		TextView textView = (TextView)view.findViewById(R.id.titol);
		textView.setText(llista.elementAt(position));
		ImageView imageView = (ImageView)view.findViewById(R.id.icono);
		switch (Math.round( (float) Math.random()*3)){
			case 0:
				imageView.setImageResource(R.drawable.asteroide1);
				break;
			case 1:
				imageView.setImageResource(R.drawable.asteroide2);
				break;
			default:
				imageView.setImageResource(R.drawable.asteroide3);
				break;
		}
		return view;
	}

}
