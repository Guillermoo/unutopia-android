package com.guillermo.listactivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyListActivity  extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		String[] datos = getResources().getStringArray(R.array.datos);
		
		ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_gallery_item,datos);
		
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
			
		Toast.makeText(this, 
				(String) l.getItemAtPosition(position), 
				Toast.LENGTH_LONG).show();
	}

}
