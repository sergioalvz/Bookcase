package com.bookcase.ui.foundbookslist.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bookcase.R;
import com.bookcase.model.Book;
import com.bookcase.ui.addbook.AddBookActivity;
import com.bookcase.util.Util;

public class FoundBooksListFragment extends ListFragment {

	private class MyDownloadManager extends AsyncTask<String, Void, String> {
		public MyDownloadManager() {
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				return downloadUrl(params[0]);
			} catch (IOException e) {
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}

		private String downloadUrl(String feed) throws IOException {
			BufferedReader input = null;
			StringBuilder response = new StringBuilder();

			try {
				URL url = new URL(feed);
				HttpURLConnection conn = configureHttpConnection(url);
				conn.connect();

				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					input = new BufferedReader(new InputStreamReader(
							conn.getInputStream()), 8192);
					String line = null;
					while ((line = input.readLine()) != null) {
						response.append(line);
					}
				}
				return response.toString();
			} finally {
				if (input != null) {
					input.close();
				}
			}
		}

		private HttpURLConnection configureHttpConnection(URL url)
				throws IOException {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			return conn;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				LinearLayout mainScreenLayout = (LinearLayout) getView()
						.findViewById(R.id.main_screen_layout);
				mainScreenLayout.removeView(progressBar);
				Collection<Book> books = processXmlResponse(result);
				adapter.addAll(books);
				adapter.notifyDataSetChanged();
				btAddManually.setVisibility(View.VISIBLE);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}
		}

		private Collection<Book> processXmlResponse(String result)
				throws IOException, ParserConfigurationException, SAXException {
			return Util.obtainBooksFromXml(result);
		}

	}

	private static final String URL_BASE = "https://isbndb.com/api/books.xml?access_key=FDWU5FKZ&index1=title&value1=";
	private ProgressBar progressBar;
	private Button btAddManually;
	private ArrayAdapter<Book> adapter;

	public FoundBooksListFragment() {

	}

	private void handleIntent() {
		Intent intent = getActivity().getIntent();
		String query = intent.getStringExtra("query");
		String feed = URL_BASE + Uri.encode(query);
		if (isNetworkAvailable()) {
			new MyDownloadManager().execute(feed);
		} else {
			Toast.makeText(getActivity(),
					getActivity().getString(R.string.no_network),
					Toast.LENGTH_SHORT).show();
			progressBar.setVisibility(View.INVISIBLE);
			btAddManually.setVisibility(View.VISIBLE);
		}
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected() ? true : false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment_found_books, container);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		adapter = new FoundBooksListAdapter(getActivity(),
				new ArrayList<Book>());

		setListAdapter(adapter);

		btAddManually = (Button) getView().findViewById(R.id.btAddManually);
		btAddManually.setVisibility(View.INVISIBLE);
		btAddManually.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AddBookActivity.class);
				startActivity(intent);
			}
		});

		progressBar = (ProgressBar) getView().findViewById(
				R.id.progressbar_download_booklist);
		progressBar.setVisibility(View.VISIBLE);

		handleIntent();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Book book = adapter.getItem(position);
		Intent intent = new Intent(getActivity(), AddBookActivity.class);
		intent.putExtra("bookTitle", book.getTitle());
		intent.putExtra("bookAuthor", book.getAuthor());
		intent.putExtra("bookIsbn", book.getIsbn());
		startActivity(intent);
	}
}
