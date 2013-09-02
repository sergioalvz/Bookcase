package com.bookcase.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private final static String DB_NAME = "bookcase";

	public MySQLiteHelper(Context context) {
		super(context, DB_NAME, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createBooksTable(db);
	}

	private void createBooksTable(SQLiteDatabase db) {
		String sql = "CREATE TABLE TBooks("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT," + "title TEXT,"
				+ "author TEXT," + "isbn TEXT NOT NULL UNIQUE,"
				+ "rating DOUBLE," + "status TEXT NOT NULL,"
				+ "observations TEXT); ";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "ALTER TABLE TBooks RENAME TO tmp_TBooks";
		db.execSQL(sql);
		sql = "CREATE TABLE TBooks(id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "title TEXT," + "author TEXT," + "isbn TEXT,"
				+ "rating DOUBLE," + "status TEXT NOT NULL,"
				+ "observations TEXT); ";
		db.execSQL(sql);
		sql = "INSERT INTO TBooks (title, author, isbn, rating, status, observations)"
				+ "SELECT title, author, isbn, rating, status, observations "
				+ "FROM tmp_TBooks ORDER BY id asc";
		db.execSQL(sql);
		sql = "DROP TABLE tmp_TBooks";
		db.execSQL(sql);
	}

}
