package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
// Esta clase nos permite crear la BD, actualizarla, obtenerla en modo lectura/escritura, etc

public class ArticlesDbHelper extends SQLiteOpenHelper {

	// Versi�n de la base de datos
	private static final int DATABASE_VERSION = 4;

	// Constructor p�blico que recibe como par�metro el contexto
	public ArticlesDbHelper(Context context) {
		// El tercer par�metro es null, un gestor para los pointers
		// El �timo par�metro es la versi�n. Por defecto, una constante
		super(context, ArticlesContract.DB_NAME, null, DATABASE_VERSION);
	}

	// Recibimos la BD, y en ella hay que crear las tablas
	@Override
	public void onCreate(SQLiteDatabase db) {
		// En este m�todo se recibe la base de datos
		createTableArticles(db);
	}

	// Se recibe una BD ya creada, con un n�mero de versi�n antiguo y uno nuevo.
	// Aqu� se hacen las operaciones necesarias para actualizarla
	// Se usa, por ejemplo, para a�adir tablas en una actualizaci�n de la aplicaci�n
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		createTableArticles(db);
	}

	private void createTableArticles(SQLiteDatabase db) {
		// Si la tabla ya existe, eliminarla; as� se puede utilizar este m�todo tambi�n en el Upgrade
		db.execSQL("DROP TABLE IF EXISTS " + ArticlesContract.Articles.TABLE_NAME);
		
		// Creaci�n de la tabla Art�culos
		db.execSQL("CREATE TABLE " + ArticlesContract.Articles.TABLE_NAME + "("
					+ ArticlesContract.Articles._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ ArticlesContract.Articles.TITLE + " STRING,"
					+ ArticlesContract.Articles.LINK + " STRING,"
					+ ArticlesContract.Articles.PUB_DATE + " LONG,"
					+ ArticlesContract.Articles.DESCRIPTION + " TEXT,"
					+ ArticlesContract.Articles.CONTENT + " TEXT"
					+ ")"
					);
		
		// Cargar la tabla con los primeros datos de la clase DummyArticles
		DummyArticles.insertDummyArticles(db);
	}
}
