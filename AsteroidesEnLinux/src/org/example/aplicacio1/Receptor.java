package org.example.aplicacio1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Receptor extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// Treure informacio del intent
				String estat = "", numero = "";
				Bundle extras = intent.getExtras();
				if (extras != null) {
					estat = extras.getString(TelephonyManager.EXTRA_STATE);
					if (estat.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
						numero = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
						String info = estat + " " + numero;
						Log.d("ReceptorAnunci", info + " intent= " + intent);

						// Cream Notificacio
						NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
						Notification notificacio = new Notification(R.drawable.asteroide1, "Criada Entrant", System.currentTimeMillis());
						PendingIntent intencioPendent = PendingIntent.getActivity(context, 0, new Intent(context, Localitzacio.class), 0);
						notificacio.setLatestEventInfo(context, "cridada Entrant", info, intencioPendent);
						nm.notify(1, notificacio);
					}
				}
		/*Intent i = new Intent(context, Sobre.class);
		context.startActivity(i);
		Toast.makeText(context, "Iniciant", Toast.LENGTH_SHORT).show();*/
	}

}
