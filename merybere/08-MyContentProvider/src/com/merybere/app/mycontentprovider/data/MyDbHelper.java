package com.merybere.app.mycontentprovider.data;

import com.merybere.app.mycontentprovider.data.MembersContract.UsersTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

	
	private static final int DATABASE_VERSION = 1;

	// Constructor p�blico que recibe como par�metro el contexto
	// Con esto ya pude acceder a las carpetas
	public MyDbHelper(Context context) {
		// El tercer par�metro es null, un gestor para los pointers
		// El �timo par�metro es la versi�n. Por defecto, una constante
		super(context, MembersContract.DB_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// En este m�todo se recibe la base de datos
		db.execSQL("CREATE TABLE " + UsersTable.TABLE_NAME + "("
					+ UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ UsersTable.USERNAME + " STRING,"
					+ UsersTable.EMAIL + " STRING"
					+ ")"
					);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// Este m�todo sirve para actualizar base de datos

	}

}
