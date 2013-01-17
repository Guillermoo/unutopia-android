package com.guillermo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		ImageView img = (ImageView) findViewById(R.id.imageView1);

		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pasaSiguienteLayout();
			}
		});	
		
		Handler handler = new Handler();
        handler.postDelayed(
            new Runnable() {
                public void run() {
                	pasaSiguienteLayout();
                }

				
            }, 3600L);
	}
	
	private void pasaSiguienteLayout() {
		
		Context context = SplashActivity.this;
		Intent intent = new Intent(context, ArticleListActivity.class);
		
		startActivity(intent);
		
	}

}
