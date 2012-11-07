package widget;

import com.merybere.apps.ejercicio_rss.R;

import data.ArticlesContract;
import data.ArticlesDbHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.DateUtils;
import android.widget.TextView;

// SimpleAdapter con el proceso de inicializaci�n
public class ArticlesAdapter extends SimpleCursorAdapter {

    // Correspondencia del HashMap con los ids de los objetos que se muestran:
    // - elementos del Array de HashMaps de eventos (from)
    // - elementos del dise�o en XML de cada una de las filas (to)
    private static final String[] FROM = new String[] {ArticlesContract.Articles.TITLE, ArticlesContract.Articles.PUB_DATE};
    private static final int[] TO = new int[]{R.id.article_row_title, R.id.article_row_date};
    
	private Context context;
    
	// Constructor: recibe un �nico
	//   - Contexto que se tiene que pasar cuando se instancia
	public ArticlesAdapter(Context context) {
		
		// Llamamos al super utilizando lo que hemos definido como constantes
		// El layout que se pasa es el que hemos definido para una l�nea de art�culo
		super(context, R.layout.article_row, null, FROM, TO, FLAG_REGISTER_CONTENT_OBSERVER);
		
		this.context = context;
		initArticlesCursor(context);
	}
	
	private void initArticlesCursor(Context context) {
		
		// Para obtener el cursor, montamos una query
        final ArticlesDbHelper helper = new ArticlesDbHelper(context);
        // Obtener la BD en modo lectura, para obtener el cursor, ya que los datos ya est�n insertados
        final SQLiteDatabase db = helper.getReadableDatabase();
		
        // Crear la consulta que nos devuelve los datos a mostrar
		String table = ArticlesContract.Articles.TABLE_NAME;
		String[] columns = new String[] { ArticlesContract.Articles._ID, ArticlesContract.Articles.TITLE, ArticlesContract.Articles.PUB_DATE};
		String selection = null;
		String[] selectionArgs = null;
		String orderBy = ArticlesContract.Articles.PUB_DATE + " DESC";
		String groupBy = null;
		String having = null;
		Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		
		// Cambiar el cursor que ten�a (null) por el cursor actual que tiene los datos que se van a mostrar
		this.swapCursor(cursor);
	}

	// M�todo que se utiliza para mostrar campos de texto
	@Override
	public void setViewText(TextView v, String text) {
		if(isDateView(v)) {
			text = getFormattedDate(text);
		}
		super.setViewText(v, text);
	}

	private String getFormattedDate(String text) {
		// Convertir el texto en un objeto fecha
		Long millis = Long.parseLong(text);
		
		// Relativo a la fecha actual, cu�nto tiempo ha transcurrido
		return (String)DateUtils.getRelativeTimeSpanString(context, millis);
	}

	// M�todo que comprueba si un campo de texto es la fecha
	private boolean isDateView(TextView v) {
		// Obtener el id de la vista que quiero comprobar si es fecha
		return v.getId() == R.id.article_row_date;
	}


}