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
	private CheckBox cbHairCut, cbExtensions, cbColor, cbPartialColor, 
		cbHilites, cbPartialHilites, cbPerm, cbUpdo, cbBrazilianBlowout, cbWax;
	private String chosenStylist;
	private String chosenServices = "";
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
	
	// TASK: Create TimePickerDialogue that picks and shows time in associated text view
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
	// Checkboxes -> Services (necessary?)
	// Do not use any cb nor apptSubmit
	public void findViews() {
		apptDate = (EditText)findViewById(R.id.apptDate);
		apptTime = (EditText)findViewById(R.id.apptTime);
		spStylistSelect = (Spinner)findViewById(R.id.spStylistSelect);
		apptSubmit = (Button)findViewById(R.id.apptSubmit);
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
		toastString = chosenServices 
				+ "with " 
				+ chosenStylist 
				+ " on " 
				+ chosenDate 
				+ " at " 
				+ chosenTime;
		Toast toast = Toast.makeText(this, toastString, Toast.LENGTH_LONG);
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
			chosenStylist = getString(R.string.theresa);
			break;
		case 1:
			chosenStylist = getString(R.string.chrissy);
			break;
		case 2:
			chosenStylist = getString(R.string.stacy);
			break;
		case 3:
			chosenStylist = getString(R.string.sheri);
			break;
		case 4:
			chosenStylist = getString(R.string.krista);
			break;
		case 5:
			chosenStylist = getString(R.string.junae);
			break;
		}
		
	}

	// Implemented function from Spinner spStylistSelect.setOnItemSelectedListener
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		chosenStylist = "Theresa";
	}
	
	// Code implemented directly from http://developer.android.com/guide/topics/ui/controls/checkbox.html
	public void selectedServices(View v) {
		// Status of checkbox
		// true = checked false = unchecked
		boolean checked = ( ( CheckBox ) v ).isChecked();
		
		// Determine which check boxes were checked
		switch ( v.getId() ) {
		case R.id.cbHairCut:
			if (checked) {
				chosenServices += getString(R.string.haircut) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.haircut) + " "), "");
				break;
			}
		case R.id.cbExtensions:
			if (checked) {
				chosenServices += getString(R.string.hairExtensions) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.hairExtensions) + " "), "");
				break;
			}
		case R.id.cbColor:
			if (checked) {
				chosenServices += getString(R.string.color) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.color) + " "), "");
				break;
			}
		case R.id.cbPartialColor:
			if (checked) {
				chosenServices += getString(R.string.partialColor) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.partialColor) + " "), "");
				break;
			}
		case R.id.cbHilites:
			if (checked) {
				chosenServices += getString(R.string.hilites) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.hilites) + " "), "");
				break;
			}
		case R.id.cbPartialHilites:
			if (checked) {
				chosenServices += getString(R.string.partialHilites) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.partialHilites) + " "), "");
				break;
			}
		case R.id.cbPerm:
			if (checked) {
				chosenServices += getString(R.string.perm) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.perm) + " "), "");
				break;
			}
		case R.id.cbUpdo:
			if (checked) {
				chosenServices += getString(R.string.updo) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.updo) + " "), "");
				break;
			}
		case R.id.cbBrazilianBlowout:
			if (checked) {
				chosenServices += getString(R.string.brazilianBlowout) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.brazilianBlowout)+ " "), "");
				break;
			}
		case R.id.cbWax:
			if (checked) {
				chosenServices += getString(R.string.wax) + " ";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.wax) + " "), "");
				break;
			}
		}
	}
	// Create Method That Sends email (built in separate class? -> passing values? (maybe as integers and strings))
	// Confirm with TOAST and redirect to main activity
	
	
	
}

