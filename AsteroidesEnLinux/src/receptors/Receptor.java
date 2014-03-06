package receptors;
import org.example.aplicacio1.Sobre;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receptor extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, Sobre.class);
		context.startActivity(i);
		Toast.makeText(context, "Iniciant", Toast.LENGTH_SHORT).show();
	}

}
