package com.hairetc.hairetcapp1;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClientSignIn extends Activity {
	
	private String email;
	private String password;
	private String stuff_to_say;
	HttpClient client;
	Button clientSignIn;
	EditText clientEmail;
	EditText clientPassword;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		clientSignIn = (Button) findViewById(R.id.signInButton);
		clientEmail = (EditText) findViewById(R.id.clientEmailInput);
		clientPassword = (EditText) findViewById(R.id.clientPasswordInput);
		client = new DefaultHttpClient();
		clientSignIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				email = clientEmail.getText().toString();
				password = clientPassword.getText().toString();
				jsonRequestCreation(email, password);
//				stuff_to_say = email + " " + password;
//				Toast toast = Toast.makeText(ClientSignIn.this, stuff_to_say, Toast.LENGTH_LONG);
//				toast.show();
			}
		});
	}
	
	public void jsonRequestCreation(String email, String password) {
		
	}

}
