package com.bookcase.ui.mainscreen.fragments.mybookcase;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookcase.R;
import com.bookcase.model.Book;
import com.bookcase.model.BookStatus;

public class MyBookcaseAdapter extends ArrayAdapter<Book> {

	private Activity context;
	private ArrayList<Book> books;

	public MyBookcaseAdapter(Activity context, ArrayList<Book> books) {
		super(context, R.layout.mybookcase_list_item, books);
		this.context = context;
		this.books = books;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View item = inflater.inflate(R.layout.mybookcase_list_item, null);

		TextView title = (TextView) item.findViewById(R.id.book_title);
		title.setText(books.get(position).getTitle());

		TextView author = (TextView) item.findViewById(R.id.book_author);
		author.setText(books.get(position).getAuthor());

		TextView status = (TextView) item.findViewById(R.id.book_status);
		status.setText(context.getString(R.string.status)
				+ ": "
				+ context.getString(obtainStatusString(books.get(position)
						.getStatus())));

		ImageView bookIcon = (ImageView) item.findViewById(R.id.book_icon);
		setBookIcon(bookIcon, position);

		return item;
	}

	private void setBookIcon(ImageView bookIcon, int position) {
		Book book = books.get(position);
		String resource = "";
		switch (book.getStatus()) {
		case PENDING:
			resource = "ic_pending";
			break;
		case IN_USE:
			resource = "ic_in_use";
			break;
		default:
			resource = "ic_read";
		}

		int id = context.getResources().getIdentifier(
				"com.bookcase:drawable/" + resource, null, null);
		bookIcon.setImageResource(id);
	}

	private int obtainStatusString(BookStatus status) {
		switch (status) {
		case PENDING:
			return R.string.pending;
		case READ:
			return R.string.read;
		case IN_USE:
			return R.string.in_use;
		case LENT:
			return R.string.lent;
		}
		throw new IllegalAccessError("Invalid book status");
	}

}
