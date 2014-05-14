package org.example.asteroides;

import org.example.aplicacio1.R;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;


public class Servei extends Service {

	private NotificationManager nm;
	private static final int ID_NOTIFICACIO_CREAR = 1;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public void onDestroy() {
		nm.cancel(ID_NOTIFICACIO_CREAR); // Elimina la notificacio creada
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public int onStartCommand(Intent intent, int flags, int idArranc) {

		Notification.Builder builder = new Notification.Builder(this);
		builder.setContentTitle("Creant servei Asteroides")
								.setSmallIcon(R.drawable.asteroide1);
		Notification n = builder.build();
		// Informacio adicional
		PendingIntent intencioPendent = PendingIntent.getActivity(this, 0, new Intent(this, Localitzacio.class), 0);
		n.setLatestEventInfo(this, "Jugant a Asteroides", "Informaci� addicional", intencioPendent);
		// Pasa la notificacio creada al NM
		nm.notify(ID_NOTIFICACIO_CREAR,n);
		return START_STICKY;
	}

}
