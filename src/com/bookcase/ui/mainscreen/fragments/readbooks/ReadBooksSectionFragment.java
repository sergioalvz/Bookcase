package com.bookcase.ui.mainscreen.fragments.readbooks;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bookcase.logic.Logic;
import com.bookcase.model.Book;
import com.bookcase.ui.bookdetails.ShowBookDetailsActivity;

public class ReadBooksSectionFragment extends ListFragment {

	private ArrayAdapter<Book> adapter;
	private ArrayList<Book> books = new ArrayList<Book>();

	public ReadBooksSectionFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter = new ReadBooksAdapter(getActivity(), books);
		setListAdapter(adapter);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		updateReadBooks();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Book book = (Book) getListAdapter().getItem(position);
		Intent intent = new Intent(getActivity(), ShowBookDetailsActivity.class);
		intent.putExtra("title", book.getTitle());
		intent.putExtra("author", book.getAuthor());
		intent.putExtra("isbn", book.getIsbn());
		intent.putExtra("observations", book.getObservations());
		intent.putExtra("rating", book.getRating());
		intent.putExtra("status", book.getStatus().toString());
		startActivity(intent);
	}

	private void updateReadBooks() {
		books = (ArrayList<Book>) new Logic(getActivity()).getUserReadBooks();
		adapter.clear();
		adapter.addAll(books);
		adapter.notifyDataSetChanged();
	}

}
