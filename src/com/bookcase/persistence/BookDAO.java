package com.bookcase.persistence;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bookcase.model.Book;
import com.bookcase.model.BookStatus;

public class BookDAO {

	private SQLiteDatabase db;

	public BookDAO(SQLiteDatabase db) {
		this.db = db;
	}

	public Collection<Book> getUserBookcase() {
		Collection<Book> result = new ArrayList<Book>();
		Cursor c = db.rawQuery("SELECT * FROM TBooks", null);
		while (c.moveToNext()) {
			Book book = configureBookFromCursor(c);
			result.add(book);
		}
		return result;
	}

	public void saveBook(Book book) {
		ContentValues values = new ContentValues();
		values.put("title", book.getTitle());
		values.put("author", book.getAuthor());
		values.put("isbn", book.getIsbn());
		values.put("rating", book.getRating());
		values.put("status", book.getStatus().toString());
		values.put("observations", book.getObservations());

		db.insert("TBooks", null, values);
	}

	public Collection<Book> getUserReadBooks() {
		Collection<Book> books = new ArrayList<Book>();
		Cursor c = db
				.rawQuery("SELECT * FROM TBooks WHERE status='READ'", null);
		while (c.moveToNext()) {
			Book book = configureBookFromCursor(c);
			books.add(book);
		}

		return books;
	}

	private Book configureBookFromCursor(Cursor c) {
		Book book = new Book();
		book.setTitle(c.getString(1));
		book.setAuthor(c.getString(2));
		book.setIsbn(c.getString(3));
		book.setRating(c.getDouble(4));
		book.setStatus(BookStatus.valueOf(c.getString(5)));
		book.setObservations(c.getString(6));
		return book;
	}

	public void updateBook(Book book) {
		ContentValues values = new ContentValues();
		values.put("title", book.getTitle());
		values.put("author", book.getAuthor());
		values.put("rating", book.getRating());
		values.put("observations", book.getObservations());
		values.put("status", book.getStatus().toString());

		String[] args = { book.getIsbn() };

		db.update("TBooks", values, "isbn=?", args);
	}

	public void deleteBook(Book item) {
		String[] args = { item.getIsbn() };
		db.delete("TBooks", "isbn=?", args);
	}
}
