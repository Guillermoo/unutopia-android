package com.guillermo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Activity2 extends Activity implements OnClickListener {

	private static final String TAG = "Activity2";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		Log.d(TAG,"onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.d(TAG,"onPause");
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.d(TAG,"onRestart");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d(TAG,"onResume");
		super.onResume();
	}

	@Override
	protected void onStart() {
		//Primero lo del padre, y luego lo nuestro
		super.onStart();
		
		String text = getIntent().getStringExtra("EXTRA_TEXTO");
		Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
		toast.show();
		
	}

	@Override
	protected void onStop() {
		Log.d(TAG,"onStop");
		super.onStop();
	}

	//Esta es otra manera de pillar los onclicks.	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			Context context = this;
			Intent intent = new Intent(context, MainActivity.class);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}

}

/*
 * 	- Define una zona clickable en `SplashActivity` de tal forma que cuando el usuario pulse en ella salte a `ArticleListActivity`
	- Modifica el menú de `ArticleListActivity` para que tenga una opción con el texto "Acerca de..." cuando se pulse se deberá mostrar `AboutActivity`
	*/
