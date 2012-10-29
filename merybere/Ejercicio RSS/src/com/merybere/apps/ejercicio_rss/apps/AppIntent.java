package com.merybere.apps.ejercicio_rss.apps;
// Centralizaci�n de los intents o mensajes de la aplicaci�n

import android.content.Intent;

public class AppIntent extends Intent {

	// Estos textos son los que se tienen que corresponder con los definidos en el manifiesto
	private static final String ACTION_ARTICLE_LIST = "unutopia.intent.action.ARTICLE_LIST";
	
	// Funci�n est�tica que monta el intent
	// Llamada a la actividad de listado de art�culos
	
	public static Intent getArticleListIntent() {
		return new Intent(ACTION_ARTICLE_LIST);
	}
}
