package com.bookcase.ui.addbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bookcase.R;
import com.bookcase.logic.Logic;
import com.bookcase.model.Book;
import com.bookcase.model.BookStatus;

public class AddBookDetailsFragment extends Fragment {

	private String bookTitle = "";
	private String bookAuthor = "";
	private String bookIsbn = "";

	private EditText etBookTitle;
	private EditText etBookAuthor;
	private EditText etBookIsbn;
	private RadioGroup rbgStatus;
	private Button btAddBook;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		etBookTitle = (EditText) getView().findViewById(R.id.book_title);
		etBookAuthor = (EditText) getView().findViewById(R.id.book_author);
		etBookIsbn = (EditText) getView().findViewById(R.id.book_isbn);
		rbgStatus = (RadioGroup) getView().findViewById(R.id.rbg_status);
		btAddBook = (Button) getView().findViewById(R.id.btAddBook);
		btAddBook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Book book = new Book();
				book.setTitle(etBookTitle.getText().toString());
				book.setAuthor(etBookAuthor.getText().toString());
				book.setIsbn(etBookIsbn.getText().toString());
				book.setObservations("");
				book.setRating(0.0);

				BookStatus status = obtainStatus(rbgStatus
						.getCheckedRadioButtonId());
				book.setStatus(status);

				new Logic(getActivity()).saveBook(book);
				Toast.makeText(getActivity(),
						getActivity().getString(R.string.new_book_added),
						Toast.LENGTH_SHORT).show();
				getActivity().finish();
			}
		});

		handleIntent();
		fetchDetailsFields();
	}

	private void fetchDetailsFields() {
		etBookTitle.setText(bookTitle);
		etBookAuthor.setText(bookAuthor);
		etBookIsbn.setText(bookIsbn);
	}

	/**
	 * Gets the Intent and obtain the data about the book
	 */
	private void handleIntent() {
		Intent intent = getActivity().getIntent();
		if (!intent.hasExtra("bookTitle"))
			return;
		bookTitle = intent.getStringExtra("bookTitle");
		bookAuthor = intent.getStringExtra("bookAuthor");
		bookIsbn = intent.getStringExtra("bookIsbn");

	}

	private BookStatus obtainStatus(int checkedRadioButtonId) {
		switch (checkedRadioButtonId) {
		case R.id.rb_read:
			return BookStatus.READ;
		case R.id.rb_pending:
			return BookStatus.PENDING;
		case R.id.rb_in_use:
			return BookStatus.IN_USE;
		default:
			return null;
		}

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_add_book_details, container);
	}

}
