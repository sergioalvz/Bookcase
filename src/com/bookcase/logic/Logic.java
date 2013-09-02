package com.bookcase.logic;

import java.util.Collection;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import com.bookcase.model.Book;
import com.bookcase.persistence.BookDAO;
import com.bookcase.persistence.MySQLiteHelper;

public class Logic {

	private Activity context;

	public Logic(Activity context) {
		this.context = context;
	}

	public Activity getContext() {
		return context;
	}

	public Collection<Book> getUserBookcase() {
		SQLiteDatabase db = new MySQLiteHelper(context).getReadableDatabase();
		Collection<Book> userBookcase = new BookDAO(db).getUserBookcase();
		db.close();
		return userBookcase;
	}

	public void saveBook(Book book) {
		SQLiteDatabase db = new MySQLiteHelper(context).getWritableDatabase();
		new BookDAO(db).saveBook(book);
		db.close();
	}

	public Collection<Book> getUserReadBooks() {
		SQLiteDatabase db = new MySQLiteHelper(context).getReadableDatabase();
		Collection<Book> books = new BookDAO(db).getUserReadBooks();
		db.close();
		return books;
	}

	public void updateBook(Book book) {
		SQLiteDatabase db = new MySQLiteHelper(context).getWritableDatabase();
		new BookDAO(db).updateBook(book);
		db.close();		
	}

	public void deleteBook(Book item) {
		SQLiteDatabase db = new MySQLiteHelper(context).getWritableDatabase();
		new BookDAO(db).deleteBook(item);
		db.close();		
	}
}
