package org.example.aplicacio1;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class Preferencias extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferencies);
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
		super.onPause();
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
	
		super.onResume();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
	
		super.onStart();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub

		super.onStop();
	}
	
	protected void onDestroy() {
		
		super.onDestroy();
	}
}