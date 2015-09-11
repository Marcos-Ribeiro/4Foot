package br.com.fourtech.fourfoot.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class JogadorDAO extends SQLiteOpenHelper{

	private static final String TABELA = "jogador";
	private static final String DATABASE = "fourfoot";
	private static final int VERSAO = 1;
	
	public JogadorDAO(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String ddl = "CREATE TABLE "
				+ TABELA
				+ " (idjogador INTEGER PRIMARY KEY,"
				+ "nome TEXT,"
				+ "senha TEXT,"
				+ "apelido TEXT,"
				+ "posicao TEXT,"
				+ "datanascimento DATE);";
		
		db.execSQL(ddl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
