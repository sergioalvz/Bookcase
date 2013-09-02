package com.bookcase.ui.mainscreen.fragments.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.bookcase.R;

public class SearchBookByTitleDialog extends DialogFragment implements
		OnEditorActionListener {

	public interface SearchBookByTitleDialogListener {
		void onFinishSearchDialog(String query);
	}

	EditText bookTitle;

	public SearchBookByTitleDialog() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_add_book, container);
		bookTitle = (EditText) view.findViewById(R.id.book_title);
		getDialog().setTitle(getString(R.string.introduce_title));

		bookTitle.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		bookTitle.setOnEditorActionListener(this);

		return view;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (EditorInfo.IME_ACTION_DONE == actionId) {
			// Return input text to activity
			SearchBookByTitleDialogListener activity = (SearchBookByTitleDialogListener) getActivity();
			activity.onFinishSearchDialog(bookTitle.getText().toString());
			this.dismiss();
			return true;
		}
		return false;
	}
}
