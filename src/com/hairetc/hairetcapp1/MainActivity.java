package com.hairetc.hairetcapp1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends Activity {
	
	// private WebView view;
	Button button;

	// @SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.datePick);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Class clientSignIn = Class.forName("com.hairetc.hairetcapp1.ClientSignIn");
					Intent formLaunch = new Intent(MainActivity.this, clientSignIn);
					startActivity(formLaunch);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					Log.i("TAG", e.getMessage().toString());
				}
			}
		});

		// Heroku App
		// String url = "http://www.google.com/";
		// view = (WebView) this.findViewById(R.id.webView1);
		// view.setWebViewClient(new WebViewClient());
		// view.getSettings().setJavaScriptEnabled(true);
		// Debugging enabled for build purposes only
		// and should be false
		// Requires use of emulator/device running Android 4.4 or greater
		// WebView.setWebContentsDebuggingEnabled(false);
		// view.loadUrl(url);
	}
	
//	public void testButton() {
//		
//	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
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

//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_main, container,
//					false);
//			return rootView;
//		}
//	}

}