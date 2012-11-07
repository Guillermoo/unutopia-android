package com.merybere.app.mycontentprovider.data;

import com.merybere.app.mycontentprovider.data.MembersContract.UsersTable;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {

	private MyDbHelper mDbHelper;

	// Creaci�n de un objeto de tipo UriMatcher (helper que nos pemite, ante una url
	// de los tipos que estamos manejando en los test, saber de qu� tipo es)
	// Es algo que se va a usar a lo largo de todo el ContentProvider y no va a cambiar,
	// por tanto la definimos como est�tica
	private static final UriMatcher sUriMatcher;

	private static final int TYPE_USERS_COLLECTION = 1;

	private static final int TYPE_USERS_ITEM = 2; 
	// Inicializaci�n de sUriMatcher en un bloque est�tico
	static {
		// Se le pasa como par�metro un valor num�rico (como c�digo del tipo de uri);
		// por defecto, el valor que devolver� para una no v�lida
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		// A�adimos los tipos de uris que vamos creando en los tests
		// El tercer par�metro es el c�digo que asignamos a este tipo de url
		sUriMatcher.addURI(MembersContract.AUTHORITY, "users", TYPE_USERS_COLLECTION);
		// Para un registro en concreto:
		sUriMatcher.addURI(MembersContract.AUTHORITY, "users/#", TYPE_USERS_ITEM);
	}
	
	@Override
	public boolean onCreate() {
		// Instanciar todo lo que necesitemos en el ContentProvider.
		// El que estamos haciendo est� basado en el SQLite
		
		// Creamos el helper
		mDbHelper = new MyDbHelper(getContext());
		return true;
	}

	// Devolver un mimetype, que sirve para hacer los filtros de items
	@Override
	public String getType(Uri uri) {
		// Comprobar qu� tipo de uri recibimos como par�metro, y pasarlo a num�rico 
		switch(sUriMatcher.match(uri)) {
		case TYPE_USERS_COLLECTION:
			// la convenci�n para los grupos de datos es android.cursor.dir
			// (vnd de vendor)
			return "android.cursor.dir/vnd.com.merybere.app.mycontentprovider/users";
		case TYPE_USERS_ITEM:
			// la convenci�n para un solo �tem es android.cursor.dir
			return "android.cursor.item/vnd.com.merybere.app.mycontentprovider/users";
		default:
			return null;
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Sacamos el tipo de uri a una variable, para poder debugear y ver su valor
		int uriType = sUriMatcher.match(uri);

		// Obtener la BD en modo escritura
		final SQLiteDatabase db = mDbHelper.getWritableDatabase();
		int numRows;
					
		switch(uriType) {
		case TYPE_USERS_ITEM:
			String id = uri.getLastPathSegment();
			
			// Si ya han pasado una cl�usula where en el par�metro selection, concatenarle un AND,
			// para poder a�adirle nuestra condici�n con el id de la uri
			if(!TextUtils.isEmpty(selection)) {
				selection += " AND ";
			} else {
				// Si el where era nulo, asignarle cadena vac�a para que al concatenarlo no use el null
				selection = "";
			}
			
			selection += UsersTable._ID + "==" + id;
			// Delete (si funciona, devolver� un id, el autoincremental)
			numRows = db.delete(UsersTable.TABLE_NAME, selection, selectionArgs);
			return numRows;
		case TYPE_USERS_COLLECTION: 
			numRows = db.delete(UsersTable.TABLE_NAME, selection, selectionArgs);
			return numRows;
		default:
			return -1;
		}
		
	}

	// Convierte el ContentValues en un registro de BD, y devuelve una uri v�lida del ContentProvider, 
	// de forma que cuando se llame a ella devuelva �nicamente este registro
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// Sacamos el tipo de uri a una variable, para poder debugear y ver su valor
		int uriType = sUriMatcher.match(uri);
		// Comprobamos que la url sea del tipo definido
		if(uriType != TYPE_USERS_COLLECTION) {
			return null;
		}
		
		// Obtener la BD en modo escritura
		final SQLiteDatabase db = mDbHelper.getWritableDatabase();
		// Insert (si funciona, devolver� un id, el autoincremental)
		long id = db.insert(UsersTable.TABLE_NAME, null, values);
		
		// Para convertir la url en �nica, 
		Uri newUri = UsersTable.getUri(id);
		
		return newUri;
	}

	// M�todo de lanzar una consulta
	// - Proyection: las columnas del select
	// - Selection: el where
	// - sortOrder: el order by
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		// Obtener una bd en modo lectura, para lanzar todas las posibles queries
		final SQLiteDatabase db = mDbHelper.getReadableDatabase();
		
		String table;
		String groupBy;
		String having;
		Cursor cursor;
		
		// Cuando llamen a la query, ver de qu� tipo es
		switch(sUriMatcher.match(uri)) {
		case TYPE_USERS_ITEM:
			// Del id que nos han pasado en la uri, quedarnos con la �ltima parte
			// (es una cadena de texto con el id del registro)
			String id = uri.getLastPathSegment();
			
			// Si ya han pasado una cl�usula where en el par�metro selection, concatenarle un AND,
			// para poder a�adirle nuestra condici�n con el id de la uri
			if(!TextUtils.isEmpty(selection)) {
				selection += " AND ";
			} else {
				// Si el where era nulo, asignarle cadena vac�a para que al concatenarlo no use el null
				selection = "";
			}
			selection += UsersTable._ID + "==" + id;
			
			// Montar la query
			table = UsersTable.TABLE_NAME;
			groupBy = null;
			having = null;
			cursor = db.query(table, projection, selection, selectionArgs, groupBy, having, sortOrder);
			return cursor;
		case TYPE_USERS_COLLECTION: 
			// Montar la query
			table = UsersTable.TABLE_NAME;
			groupBy = null;
			having = null;
			cursor = db.query(table, projection, selection, selectionArgs, groupBy, having, sortOrder);
			return cursor;
		default:
			return null;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		// Sacamos el tipo de uri a una variable, para poder debugear y ver su valor
		int uriType = sUriMatcher.match(uri);
		
		// Obtener la BD en modo escritura
		final SQLiteDatabase db = mDbHelper.getWritableDatabase();
		
		switch(uriType) {
		case TYPE_USERS_ITEM:
			// Del id que nos han pasado en la uri, quedarnos con la �ltima parte (es una cadena)
			String id = uri.getLastPathSegment();
			
			// Si ya han pasado una cl�usula where en el par�metro selection, concatenarle un AND,
			// para poder a�adirle nuestra condici�n con el id de la uri
			if(!TextUtils.isEmpty(selection)) {
				selection += " AND ";
			} else {
				// Si el where era nulo, asignarle cadena vac�a para que al concatenarlo no use el null
				selection = "";
			}
			selection += UsersTable._ID + "==" + id;
			// Update: devuelve el n�mero de filas actualizadas
			int numRows = db.update(UsersTable.TABLE_NAME, values, selection, selectionArgs);
			return numRows;
		default:
			return -1;
		}
	}

}
