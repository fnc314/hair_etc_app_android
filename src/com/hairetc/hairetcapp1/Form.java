package com.hairetc.hairetcapp1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class Form extends Activity {
	
	// Global variables for getting date and time information
	final Calendar c = Calendar.getInstance();
	private EditText apptDate;
	private EditText apptTime;
	private Button apptSubmit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
		findViews();
	}
	
	// Create DatePickerDialogue that picks and shows date in associated text view
	public void datePick(View v) {
		new DatePickerDialog(Form.this, date, c.get(Calendar.YEAR), 
				c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
	}
	
	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
	    @Override
	    public void onDateSet( DatePicker view, int year, int monthOfYear, int dayOfMonth ) {
	      c.set( Calendar.YEAR, year );
	      c.set( Calendar.MONTH, monthOfYear );
	      c.set( Calendar.DAY_OF_MONTH, dayOfMonth );
	      showSelected();
	    }
	};
	
	// Create TimePickerDialogue that picks and shows time in associated text view
	public void timePick(View v) {
		new TimePickerDialog(Form.this, time, 
				c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false).show();
	}
	
	TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			c.set( Calendar.HOUR_OF_DAY, hourOfDay);
			c.set( Calendar.MINUTE, minute);
			showSelected();
		}
	};
	
	public void showSelected() {
		String dateFormat = "MM-dd-yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat( dateFormat, Locale.US );
		apptDate.setText( sdf.format( c.getTime() ) );
		
		String timeFormat = "hh:mm a";
		SimpleDateFormat stf = new SimpleDateFormat( timeFormat, Locale.US );
		apptTime.setText( stf.format( c.getTime() ) );
	}
	
	// Abstracted out functionality to avoid repetition
	public void findViews() {
		apptDate = (EditText)findViewById(R.id.apptDate);
		apptTime = (EditText)findViewById(R.id.apptTime);
	}
	  
	// Create Method That Sends email (built in separate class? -> passing values? (maybe as integers and strings))
	// Confirm with TOAST and redirect to main activity
	
	// Radio buttons -> Stylist
	// Checkboxes -> Services
	
}

