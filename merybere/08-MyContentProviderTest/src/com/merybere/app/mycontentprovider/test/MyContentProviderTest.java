package com.merybere.app.mycontentprovider.test;

import com.merybere.app.mycontentprovider.data.MembersContract;
import com.merybere.app.mycontentprovider.data.MembersContract.UsersTable;
import com.merybere.app.mycontentprovider.data.MyContentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

public class MyContentProviderTest extends ProviderTestCase2<MyContentProvider> {

	// ContentResolver es el que se encarga de llamar uno u otro Provider,
	// en funci�n de lo que se le est� pidiendo.
	private MockContentResolver mContentResolver;

	// Constructor
	public MyContentProviderTest() {
		// Pasamos al super:
		// 	- la clase provider que vamos a probar
		//	- la authority, que es el dominio que tienen las urls de ContentProvider
		//    sobre el que vamos a trabajar
		super(MyContentProvider.class, MembersContract.AUTHORITY);
	}

	// M�todo que se ejecuta antes de cada test
	// Sirve para inicializar objetos
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		// (los Mock son conjuntos de datos que preparamos para no usar los datos reales)
		mContentResolver = getMockContentResolver();
	}

	
	// Cada m�todo que llamemos con el prefijo test se considerar� un test:
	//  - Si lanza una excepci�n se considerar� fallido
	//  - Si pasa por toda la programaci�n que contenga sin lanzar una excepci�n se considera ok
	
	// Test de la uri del ContentProvider
	// (con este test nos aseguramos que si m�s adelante cambi�ramos la constante AUTHORITY que
	//  estamos usando para definir la uri, el programa no falle)
	public void testUsersUri() {
		Uri expected = Uri.parse("content://com.merybere.app.mycontentprovider/users");
		Uri actual = UsersTable.getUri();
		
		assertEquals(expected, actual);
	}
	
	// Test de insertar datos
	public void testInsert() {
		// Necesitamos un objeto ContentValues, cargar en �l los datos que queremos insertar
		ContentValues values = new ContentValues();
		values.put(MembersContract.UsersTable.USERNAME, "Clark Kent");
		values.put(MembersContract.UsersTable.EMAIL, "super@man.com");
		
		// Necesitamos una uri, a la que pasarle los valores y tiene que hacer el insert
		Uri uri = UsersTable.getUri();
		
		// Llamada al insert.
		// Si va bien, devolver� una nueva uri que es la direcci�n del registro que acabamos de insertar
		Uri newUri = mContentResolver.insert(uri, values);
		
		// Comprobaci�n de que newUri es correcta
		assertNotNull(newUri);
	}
	
	// Test de select:
	//   - tras insertar un dato nuevo devolver� una uri
	//   - el test consiste en consultar por esa uri, y que devuelva un �nico registro con los
	//     datos que hemos insertado
	public void testSelectOne() {
		// Necesitamos un objeto ContentValues, cargar en �l los datos que queremos insertar
		ContentValues values = new ContentValues();
		values.put(MembersContract.UsersTable.USERNAME, "Clark Kent");
		values.put(MembersContract.UsersTable.EMAIL, "super@man.com");
		
		// Necesitamos una uri, a la que pasarle los valores y tiene que hacer el insert
		Uri uri = UsersTable.getUri();
		
		// Llamada al insert, que devolver� una uri de registro �nico
		Uri newUri = mContentResolver.insert(uri, values);
		
		// Comprobaci�n de que newUri es correcta
		assertNotNull(newUri);
		
		// Lanzar una query con esa uri, que devuelva todos los campos, sin l�mites de selecci�n
		// ni argumentos ni ordenaci�n, que devuelva un cursor para acceder a esos datos
		Cursor cursor = mContentResolver.query(newUri, null, null, null, null);
		
		// Comprobaci�n para asegurarse de que el n�mero de filas del cursor es 1 
		assertEquals(cursor.getCount(), 1);
		
		cursor.moveToFirst();
		// Como en la consulta para obtener el cursor no hemos forzado ning�n orden en las columnas,
		// obtener los campos nombre y email por b�squeda de los �ndices de esas columnas
		String email = cursor.getString(cursor.getColumnIndex(UsersTable.EMAIL));
		String name = cursor.getString(cursor.getColumnIndex(UsersTable.USERNAME));
		assertEquals("Clark Kent", name);
		assertEquals("super@man.com", email);
		
		cursor.close();		
	}

	// Test de borrado de un registro de la tabla
	public void testDeleteOne() {
		// Necesitamos un objeto ContentValues, cargar en �l los datos que queremos insertar
		ContentValues values = new ContentValues();
		values.put(MembersContract.UsersTable.USERNAME, "Clark Kent");
		values.put(MembersContract.UsersTable.EMAIL, "super@man.com");
		
		// Necesitamos una uri, a la que pasarle los valores y tiene que hacer el insert
		Uri uri = UsersTable.getUri();
		
		// Llamada al insert, que devolver� una uri de registro �nico
		Uri newUri = mContentResolver.insert(uri, values);
		
		// Comprobaci�n de que newUri es correcta
		assertNotNull(newUri);
		
		int dels = mContentResolver.delete(newUri, null, null);
		
		assertEquals(1, dels);
	}
	
	public void testUpdate() {
		// Necesitamos un objeto ContentValues, cargar en �l los datos que queremos insertar
		ContentValues values = new ContentValues();
		values.put(MembersContract.UsersTable.USERNAME, "Clark Kent");
		values.put(MembersContract.UsersTable.EMAIL, "super@man.com");
		
		// Necesitamos una uri, a la que pasarle los valores y tiene que hacer el insert
		Uri uri = UsersTable.getUri();
		
		// Llamada al insert, que devolver� una uri de registro �nico
		Uri newUri = mContentResolver.insert(uri, values);
		
		// Comprobaci�n de que newUri es correcta
		assertNotNull(newUri);
		
		ContentValues newValues = new ContentValues();
		newValues.put(MembersContract.UsersTable.USERNAME, "Superman");
		newValues.put(MembersContract.UsersTable.EMAIL, "clark@kent.com");
		int numRows = mContentResolver.update(newUri, newValues, null, null);
		
		assertEquals(1, numRows);
	}
}
