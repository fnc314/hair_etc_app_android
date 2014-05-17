package com.hairetc.hairetcapp1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClientSignIn extends Activity {
	
	// WORK IN THIS SECTION COMES FROM http://hmkcode.com/android-send-json-data-to-server/
	
	private String email;
	private String password;
	
	Button clientSignIn;
	EditText clientEmail;
	EditText clientPassword;
	
	// IP ADDRESS IS 10.0.2.2 (LocalHost)
	final static String URL = "https://hairetcapp.herokuapp.com/api/sessions.json";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		clientSignIn = (Button) findViewById(R.id.signInButton);
		clientEmail = (EditText) findViewById(R.id.clientEmailInput);
		clientPassword = (EditText) findViewById(R.id.clientPasswordInput);
		clientSignIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				email = clientEmail.getText().toString();
				password = clientPassword.getText().toString();
				if ( !validate(email, password) ) {
					Toast.makeText(ClientSignIn.this, "ENTER EMAIL AND PASSWORD", Toast.LENGTH_LONG).show();
				} else {
					if (isConnected()) {
//						Creating Toast Messages to verify where in the logic blocks I am
//						Toast toast = Toast.makeText(ClientSignIn.this, "Connection", Toast.LENGTH_LONG);
//						toast.show();
//						USE NEW ASYNCTASK
//						Call a function that takes in email/password and compiles a JSONOjbect
						new HttpAsyncTask().execute(URL, email, password);
					} else {
						Toast.makeText(ClientSignIn.this, "No Connection", Toast.LENGTH_LONG).show();
					}
				}
				
//				jsonRequestCreation(email, password);
//				stuff_to_say = email + " " + password;
//				Toast toast = Toast.makeText(ClientSignIn.this, stuff_to_say, Toast.LENGTH_LONG);
//				toast.show();
			}
		});
	}
	
	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validate(String email, String password) {
		if (email.equals("") ) {
			return false;
		} else if (password.equals("") ) {
			return false;
		} else {
			return true;
		}
	}

	public static String convertInputStreamToString(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null) {
			result += line;
		}
		inputStream.close();
		result = extractToken(result);
		return result;
	}
	
	public static String extractToken(String result) {
		String userToken = "";
		try {
			JSONObject jsonResponse = new JSONObject(result);
			userToken += jsonResponse.getString("authentication_token");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userToken;
		
	}

	public static String signClientIn(String URL, JSONObject params) {
		String result = "";
		InputStream inputStream = null;
		try {
			// Create HttpClient
			HttpClient httpClient = new DefaultHttpClient();
			
			// Make a post request to the URL
			HttpPost httpPost = new HttpPost(URL);
			
			// Turn JSON params into string
			String jsonParams = params.toString();
			
			// Set jsonParams to StringEntity
			StringEntity stringEntity = new StringEntity(jsonParams, HTTP.UTF_8);
			
			// Set httpPost Entity
			httpPost.setEntity(stringEntity);
			
			// Set headers for server <- Look Into This!!! <- Can Skip because of
			// skip_before_action :verify_authenticity_token in app/controllers/api_controllers.rb
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
			//httpPost.setHeader("X-CLIENT-EMAIL", "abc@def.com");
			//httpPost.setHeader("X-CLIENT-TOKEN", "");
			// REFER TO ANDROID BOARD
			
			// Execute POST
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			// Set inputStream to response value
			inputStream = httpResponse.getEntity().getContent();
			
			// Convert to string for toast
			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);
				// MAKE TOAST!
			} else {
				result = "Not Successful";
				// MAKE TOAST!
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONObject jsonParams = new JSONObject();
			try {
				jsonParams.put("email", params[1]);
				jsonParams.put("password", params[2]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return signClientIn(params[0], jsonParams);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			// super.onPostExecute(result);
			// Locally store the result passed in as the authentication_token
			// Upon success of saving, launch form
			String APP_TOKEN = getText(R.string.localFile).toString();
			String CLIENT_EMAIL = email;
			try {
				FileOutputStream fos = openFileOutput(APP_TOKEN, Context.MODE_PRIVATE);
				fos.write(result.getBytes());
				fos.write(">>".getBytes());
				fos.write(CLIENT_EMAIL.getBytes());
				fos.close();
				Toast.makeText(getBaseContext(), "Key Saved", Toast.LENGTH_LONG).show();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			} finally {
				try {
					Class secondActivity = Class.forName("com.hairetc.hairetcapp1.SecondActivity");
					Intent formLaunch = new Intent(ClientSignIn.this, secondActivity);
					startActivity(formLaunch);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			// Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
		}
	}
}
