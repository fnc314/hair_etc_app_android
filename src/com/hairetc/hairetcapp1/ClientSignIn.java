package com.hairetc.hairetcapp1;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClientSignIn extends Activity {
	
	private String email;
	private String password;
	
	Button clientSignIn;
	EditText clientEmail;
	EditText clientPassword;
	
	final static String URL = "http://127.0.0.1:3000";
	
	
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
					Toast toast = Toast.makeText(ClientSignIn.this, "ENTER EMAIL AND PASSWORD", Toast.LENGTH_LONG);
					toast.show();
				} else {
					if (isConnected()) {
						Toast toast = Toast.makeText(ClientSignIn.this, "Connection", Toast.LENGTH_LONG);
						toast.show();
					} else {
						Toast toast = Toast.makeText(ClientSignIn.this, "No Connection", Toast.LENGTH_LONG);
						toast.show();
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
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
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
	
	public static void jsonRequestCreation(String email, String password) {
		JSONObject params = new JSONObject();
		try {
			params.put("email", email);
			params.put("password", password);
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			signClientIn(URL, params);
		}
		
	}

	public static String signClientIn(String URL, JSONObject params) {
		String toastText = "";
		InputStream inputStream = null;
		try {
			// Create HttpClient
			HttpClient client = new DefaultHttpClient();
			
			// Make a post request to the URL
			HttpPost httpPost = new HttpPost(URL);
			
			// Turn JSON params into string
			String jsonParams = params.toString();
			
			// Set jsonParams to StringEntity
			StringEntity paramsEntity = new StringEntity(jsonParams);
			
			// Set httpPost Entity
			httpPost.setEntity(paramsEntity);
			
			// Set headers for server <- Look Into This!!!
			//httpPost.setHeader(BLAH);
			//httpPost.setHeader(BLAH);
			// REFER TO ANDROID BOARD
			
			// Execute POST
			HttpResponse httpResponse = client.execute(httpPost);
			
			// Set inputStream to response value
			inputStream = httpResponse.getEntity().getContent();
			
			// Convert to string for toast
			if (inputStream != null) {
				toastText = inputStream.toString();
				// MAKE TOAST!
			} else {
				toastText = "Not Successful";
				// MAKE TOAST!
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toastText;
	}
}
//ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//if ( networkInfo != null && networkInfo.isConnected() ) {
//	StringBuilder url = new StringBuilder(URL);
//	
//	HttpPost post = new HttpPost(url.toString());
//} else {
//	Toast toast = Toast.makeText(ClientSignIn.this, "No Internet Connection", Toast.LENGTH_LONG);
//	toast.show();
//}