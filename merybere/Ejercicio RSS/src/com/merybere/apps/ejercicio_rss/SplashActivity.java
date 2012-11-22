package com.merybere.apps.ejercicio_rss;

import data.RSSInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.ResultReceiver;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import app.AppIntent;

public class SplashActivity extends Activity {

    private View titulo;
	private CountDownTimer timer = null;
	
	// Objeto parcelable, que pasaremos en el intent
	MyResultReceiver resultReceiver = new MyResultReceiver();

	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.splash);
        
        // Servicio de carga de art�culos
        final Intent feedService = AppIntent.getFeedIntent();
        // ResultReceiver
        feedService.putExtra(RSSInterface.INTENT_RESULTRECEIVER, resultReceiver);
        startService(feedService);
        
        // Cachear el objeto t�tulo clickable
        titulo = findViewById(R.id.titulo);
        
        // Listener para responder al evento click
        titulo.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				
				nextActivity();
			}
        });
    }

    @Override
	protected void onPause() {
		super.onPause();
		
		if(null != timer) {
			timer.cancel();
			timer = null;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// Contador a 10 segundos
		timer = new SplashTimer(RSSInterface.SPLASH_TIMER, RSSInterface.SPLASH_TIMER);
		timer.start();
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }
	
	private void nextActivity() {
		
		// Mensajero (se crea el mensaje que se va a pasar)
		final Intent intent = AppIntent.getArticleListIntent();
		startActivity(intent);
	}
	
	//Timer Class inside my Activity
    class SplashTimer extends CountDownTimer{

        public SplashTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            nextActivity();
        }

        @Override
        public void onTick(long millisUntilFinished) {
        	
        }
    }
    
    // Clase que android utiliza internamente para enviar y recibir resultados
    class MyResultReceiver extends ResultReceiver {

    	private static final String TAG = "MyResultReceiver";

		// Constructor: pide un handler, para comunicar desde hilos secundarios al hilo principal
		public MyResultReceiver() {
			super(new Handler());
		}

		// resultCode es un resultado num�rico con el que interactuamos (id del estado)
		// resultData nos podr�a pasar otros datos extra 
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			Log.d(TAG, "onReceiveResult");
			super.onReceiveResult(resultCode, resultData);
			
			// Mostrar y ocultar la barra de progreso al recibir el c�digo correspondiente
			switch (resultCode) {
			case RSSInterface.CODE_END_TASK:
				// Lanzar la siguiente actividad cuando acaba la tarea
				nextActivity();
				break;
			default:
				break;
			}
		}
    }
}
