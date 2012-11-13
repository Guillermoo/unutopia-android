package com.merybere.app.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {

	private static final String TAG = "MyBroadcastReceiver";

	// Este m�todo se invocar� cuando se cumpla la condici�n de llamadas
	// - Se pasa el contexto sobre el que se produce la llamada
	// - Intent con los par�metros, en funci�n del tipo de llamada
	@Override
	public void onReceive(Context context, Intent intent) {
		// Log que marca que ha entrado en el m�todo onReceive, con el tag de la clase
		Log.d(TAG, "onReceive");

		// El TelephonyManager nos da acceso a los servicios del tel�fono, sobre el intent.
		// La clave EXTRA_STATE nos devuelve una string con el estado de llamada
		String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
		
		// Para saber si es una llamada entrante, y el n�mero:
		if(state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
			// Capturamos el tel�fono, que nos viene tambi�n en el intent
			String tlf = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
			Log.d(TAG, "llamada entrante de " + tlf);
		}
	}

}
