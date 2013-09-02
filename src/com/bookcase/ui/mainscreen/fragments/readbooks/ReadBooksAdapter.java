package com.bookcase.ui.mainscreen.fragments.readbooks;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bookcase.R;
import com.bookcase.model.Book;

public class ReadBooksAdapter extends ArrayAdapter<Book> {
	Activity context;
	ArrayList<Book> books;

	public ReadBooksAdapter(Activity context, ArrayList<Book> books) {
		super(context, R.layout.readbooks_list_item, books);
		this.context = context;
		this.books = books;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(R.layout.readbooks_list_item, null);

		TextView title = (TextView) item.findViewById(R.id.book_title);
		title.setText(books.get(position).getTitle());

		TextView author = (TextView) item.findViewById(R.id.book_author);
		author.setText(books.get(position).getAuthor());
		
		RatingBar rating = (RatingBar) item.findViewById(R.id.book_rating);
		rating.setRating((float) books.get(position).getRating());

		return item;
	}

}
