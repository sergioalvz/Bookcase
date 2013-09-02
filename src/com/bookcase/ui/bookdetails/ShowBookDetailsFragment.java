package com.bookcase.ui.bookdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bookcase.R;
import com.bookcase.logic.Logic;
import com.bookcase.model.Book;
import com.bookcase.model.BookStatus;

public class ShowBookDetailsFragment extends Fragment {

	private EditText bookTitle;
	private EditText bookAuthor;
	private EditText bookIsbn;
	private RatingBar bookRating;
	private EditText bookObservations;
	private RadioGroup rbgBookStatus;
	private Button btSave;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		bookTitle = (EditText) getView().findViewById(R.id.book_title);

		bookAuthor = (EditText) getView().findViewById(R.id.book_author);

		bookIsbn = (EditText) getView().findViewById(R.id.book_isbn);

		bookRating = (RatingBar) getView().findViewById(R.id.book_rating);

		bookObservations = (EditText) getView().findViewById(
				R.id.book_observations);

		rbgBookStatus = (RadioGroup) getView().findViewById(R.id.rbg_status);

		btSave = (Button) getView().findViewById(R.id.btSave);
		btSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				String title = bookTitle.getText().toString();
				String author = bookAuthor.getText().toString();
				String isbn = bookIsbn.getText().toString();
				String observations = bookObservations.getText().toString();
				double rating = bookRating.getRating();
				BookStatus status = obtainCheckedStatus(rbgBookStatus
						.getCheckedRadioButtonId());

				Book book = new Book();
				book.setTitle(title);
				book.setAuthor(author);
				book.setObservations(observations);
				book.setIsbn(isbn);
				book.setRating(rating);
				book.setStatus(status);

				new Logic(getActivity()).updateBook(book);
				Toast.makeText(getActivity(),
						getActivity().getString(R.string.book_updated),
						Toast.LENGTH_SHORT).show();

			}

			private BookStatus obtainCheckedStatus(int checkedRadioButtonId) {
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
		});

		handleIntent();

	}

	private void handleIntent() {
		Intent intent = getActivity().getIntent();
		String title = intent.getStringExtra("title");
		String author = intent.getStringExtra("author");
		String isbn = intent.getStringExtra("isbn");
		double rating = intent.getDoubleExtra("rating", 0.0);
		String observations = intent.getStringExtra("observations");
		BookStatus status = BookStatus.valueOf(intent.getStringExtra("status"));

		fetchBookDetails(title, author, isbn, rating, observations, status);

	}

	private void fetchBookDetails(String title, String author, String isbn,
			double rating, String observations, BookStatus status) {
		bookTitle.setText(title);
		bookAuthor.setText(author);
		bookIsbn.setText(isbn);
		bookRating.setRating((float) rating);
		bookObservations.setText(observations);

		int statusOption = obtainStatusOption(status);
		RadioButton selected = (RadioButton) getView().findViewById(
				statusOption);
		selected.setChecked(true);

	}

	private int obtainStatusOption(BookStatus status) {
		switch (status) {
		case PENDING:
			return R.id.rb_pending;
		case READ:
			return R.id.rb_read;
		case IN_USE:
			return R.id.rb_in_use;
		}
		return 0;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_book_details, container);
	}

}
