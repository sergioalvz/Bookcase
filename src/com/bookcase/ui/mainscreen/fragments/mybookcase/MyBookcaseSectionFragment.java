package com.bookcase.ui.mainscreen.fragments.mybookcase;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bookcase.R;
import com.bookcase.logic.Logic;
import com.bookcase.model.Book;
import com.bookcase.ui.bookdetails.ShowBookDetailsActivity;

public class MyBookcaseSectionFragment extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		adapter = new MyBookcaseAdapter(getActivity(), books);
		setListAdapter(adapter);

		final ListView listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

			boolean itemDeleted = false;

			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {

			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.menu_delete:
					deleteSelectedItems();
					mode.finish();
					return true;
				default:
					return false;
				}
			}

			private void deleteSelectedItems() {
				SparseBooleanArray checkedItems = listView
						.getCheckedItemPositions();
				Logic logic = new Logic(getActivity());
				for (int i = 0; i < adapter.getCount(); i++) {
					if (checkedItems.get(i)) {
						logic.deleteBook(adapter.getItem(i));
						itemDeleted = true;
					}
				}

				Toast.makeText(getActivity(), R.string.delete_completed,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.action_menu, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				if (itemDeleted) {
					getActivity().recreate();
				}
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}
		});
	}

	private ArrayAdapter<Book> adapter;
	ArrayList<Book> books = new ArrayList<Book>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		updateBookcase();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	private void updateBookcase() {
		books = (ArrayList<Book>) new Logic(getActivity()).getUserBookcase();
		adapter.clear();
		adapter.addAll(books);
		adapter.notifyDataSetChanged();
	}
}
