package com.bookcase.ui.foundbookslist.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bookcase.R;
import com.bookcase.model.Book;

public class FoundBooksListAdapter extends ArrayAdapter<Book> {

	private Activity context;
	private ArrayList<Book> books;

	public FoundBooksListAdapter(Activity context, ArrayList<Book> books) {
		super(context, R.layout.found_book_list_item, books);
		this.context = context;
		this.books = books;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(R.layout.found_book_list_item, null);

		TextView bookTitle = (TextView) item
				.findViewById(R.id.book_obtained_title);
		TextView bookAuthor = (TextView) item
				.findViewById(R.id.book_obtained_author);
		TextView bookIsbn = (TextView) item
				.findViewById(R.id.book_obtained_isbn);

		if (!books.isEmpty()) {
			Book current = books.get(position);
			bookTitle.setText(current.getTitle());
			bookAuthor.setText(current.getAuthor());
			bookIsbn.setText("ISBN: " + current.getIsbn());
		}

		return item;
	}

}
