package com.guillermo.intend;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		
		//Necesitamos alguien que sepa atender a la acción de ver
		//Intent intent = new Intent(Intent.ACTION_VIEW);
		//intent.setData(Uri.parse("www.google.com"));
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
		//intent = new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.com"));
		
		startActivity(intent);
		
	}


}
