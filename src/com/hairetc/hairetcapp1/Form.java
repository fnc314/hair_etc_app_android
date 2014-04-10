package com.hairetc.hairetcapp1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class Form extends Activity implements OnItemSelectedListener {
	
	// Global variables for getting date and time information
	final Calendar c = Calendar.getInstance();
	private EditText apptDate;
	private EditText apptTime;
	private Button apptSubmit;
	private Spinner spStylistSelect;
	private CheckBox cbHairCut, cbPerm, cbPartialHilites, cbHilites, cbColor, 
	cbPartialColor, cbUpdo, cbBrazilianBlowout, cbWax, cbExtensions;
	private String chosenStylist;
	private String chosenServices;
	private String chosenDate;
	private String chosenTime;
	private String toastString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
		findViews();
		populateSpinnerWithStylists();

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
		chosenDate = sdf.format( c.getTime() );
		apptDate.setText( sdf.format( c.getTime() ) );
		
		String timeFormat = "hh:mm a";
		SimpleDateFormat stf = new SimpleDateFormat( timeFormat, Locale.US );
		chosenTime = stf.format( c.getTime() );
		apptTime.setText( stf.format( c.getTime() ) );
	}
	
	// Abstracted out functionality to avoid repetition
	// Checkboxes -> Services
	public void findViews() {
		apptDate = (EditText)findViewById(R.id.apptDate);
		apptTime = (EditText)findViewById(R.id.apptTime);
		spStylistSelect = (Spinner)findViewById(R.id.spStylistSelect);
		cbHairCut = (CheckBox)findViewById(R.id.cbHairCut);
		cbPerm = (CheckBox)findViewById(R.id.cbPerm);
		cbPartialHilites = (CheckBox)findViewById(R.id.cbPartialHilites);
		cbHilites = (CheckBox)findViewById(R.id.cbHilites);
		cbColor = (CheckBox)findViewById(R.id.cbColor);
		cbPartialColor = (CheckBox)findViewById(R.id.cbPartialColor);
		cbUpdo = (CheckBox)findViewById(R.id.cbUpdo);
		cbBrazilianBlowout = (CheckBox)findViewById(R.id.cbBrazilianBlowout);
		cbWax = (CheckBox)findViewById(R.id.cbWax);
		cbExtensions = (CheckBox)findViewById(R.id.cbExtensions);		
	}
	
	// Populate Spinner with <string-array name="stylists"> in res/values/strings.xml
	public void populateSpinnerWithStylists() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.stylists, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spStylistSelect.setAdapter(adapter);
		spStylistSelect.setOnItemSelectedListener(this);
	}
	
	// Make temporary Toast to show customers what they chose
	
	public void tempToast() {
		toastString = chosenStylist + " on " + chosenDate + " at " + chosenTime;
		Toast toast = Toast.makeText(this, toastString, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public void scheduleAppt(View v) {
		tempToast();
	}
	
	// Implemented function from Spinner spStylistSelect.setOnItemSelectedListener
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		int stylistId = spStylistSelect.getSelectedItemPosition();
		switch (stylistId) {
		case 0:
			chosenStylist = "Theresa";
			break;
		case 1:
			chosenStylist = "Chrissy";
			break;
		case 2:
			chosenStylist = "Stacy";
			break;
		case 3:
			chosenStylist = "Sheri";
			break;
		case 4:
			chosenStylist = "Krista";
			break;
		case 5:
			chosenStylist = "Junae";
			break;
		}
		
	}

	// Implemented function from Spinner spStylistSelect.setOnItemSelectedListener
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	  
	// Create Method That Sends email (built in separate class? -> passing values? (maybe as integers and strings))
	// Confirm with TOAST and redirect to main activity
	
	
	
}

