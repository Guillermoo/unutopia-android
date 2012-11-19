package com.merybere.app.services;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

// AsyncTask --> Tres tipos de datos extra:
// Primero: tipo de dato que se recibe como par�metro en la llamada de la ejecuci�n (url en nuestro caso)
// Segundo: progreso --> si la tarea dura, qu� hacer durante el progreso (ir incrementando una barra, mostrar un texto)
// Tercero: lo que esa tarea va a devolver cuando acabe
public class LoadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
	
	private ImageView imageView;

	// Hacemos el constructor: para poder interactuar necesitamos pasarle el objeto ImageView
	// donde queremos colocar la imagen (como par�metro)
	public LoadImageAsyncTask(ImageView imageView) {
		// guardar el image view
		this.imageView = imageView;
		
	}

	// Tarea pesada en segundo plano. Seg�n lo definido, va a devolver un bitmap, y se le manda una 
	// lista de par�metros de tipo string (pueden ser 1, 0 � varios)
	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap = null;
		
		// Cadena de la url (de los par�metros que me pasan, el primero)
		String urlString = params[0];
		
		try {
			// Convertir la string en una url
			URL url = new URL(urlString);
			
			// Apertura de una conexi�n
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// Conectar
			connection.connect();
			
			// Recibir el flujo de datos
			InputStream input = connection.getInputStream();
			
			// A trav�s de una factor�a, convertir el flujo de datos en un bitmap
			bitmap = BitmapFactory.decodeStream(input);
			
			connection.disconnect();
		} catch (Exception e) {
			bitmap = null;
			e.printStackTrace();
		}
		
		return bitmap;
	}

	// Los siguientes m�todos se ejecutar�n en primer plano (en el hilo principal),
	// por tanto se puede interactuar con ellos:
	
	// M�todo que se llama cuando se haya ejecutado una tarea
	//   Se le pasa un bitmap, que es lo que hemos definido que devuelve la tarea al acabar
	@Override
	protected void onPostExecute(Bitmap result) {
		
		super.onPostExecute(result);
		
		// asignar al imageView el bitmap, la imagen
		imageView.setImageBitmap(result);
	}

	// M�todo que se llama antes de ejecutar una tarea
	@Override
	protected void onPreExecute() {
		// Por si ya hubiera una imagen cargada, borrarla
		imageView.setImageBitmap(null);
		super.onPreExecute();
	}

	// M�todo que se llama cuando se produzca un progreso
	//   Como durante el progreso hemos definido que no se pasa nada, el par�metro es un void
	@Override
	protected void onProgressUpdate(Void... values) {
		
		super.onProgressUpdate(values);
	}
}
