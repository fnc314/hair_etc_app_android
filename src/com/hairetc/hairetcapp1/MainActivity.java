package com.hairetc.hairetcapp1;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.os.Build;

public class MainActivity extends Activity {
	
	private WebView view;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Heroku App
		String url = "http://www.google.com/";
		view = (WebView) this.findViewById(R.id.webView1);
		
		// Debugging enabled for build purposes only
		// and should be false
		// Requires use of emulator/device running Android 4.4 or greater		
		// WebView.setWebContentsDebuggingEnabled(false);
		
		
		view.setWebViewClient(new WebViewClient());
		view.getSettings().setJavaScriptEnabled(true);
		view.loadUrl(url);
		
	}
	
//	public void testButton() {
//		String url = "http://hairetcapp.herokuapp.com/signup";
//		view = (WebView) this.findViewById(R.id.webView1);
//		view.setWebViewClient(new WebViewClient());
//		view.getSettings().setJavaScriptEnabled(true);
//		view.loadUrl(url);
//	}
	
	@Override
	public void onBackPressed() {
		if (view.canGoBack()) {
			view.goBack();
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}