package com.hairetc.hairetcapp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends Activity {

	Button createNewAppt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		createNewAppt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Class form = Class.forName("com.hairetc.hairetcapp1.Form");
					Intent formLaunch = new Intent(SecondActivity.this, form);
					startActivity(formLaunch);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
		});
	}

}
