package org.example.aplicacio1;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Puntuacions extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Mostra el layout del ListView
		setContentView(R.layout.puntuacions);
		setListAdapter(new MeuAdaptador(this,
				Localitzacio.magatzem.llistarPuntuacions(10) ) );
		
	}
	
	protected void onListItemClick(ListView listView, View view,
									int position, long id) {
		super.onListItemClick(listView, view, position, id);
		Object o =  getListAdapter().getItem(position);
		Toast.makeText(this, "Seleccion: " + Integer.toString(position) +
				" - " + o.toString(), Toast.LENGTH_SHORT).show();	
	}
}