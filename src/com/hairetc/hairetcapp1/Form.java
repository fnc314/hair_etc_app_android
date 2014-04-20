package com.hairetc.hairetcapp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
	private String chosenStylist;
	private String chosenServices = "";
	private String chosenDate;
	private String chosenTime;
	String[] stylist_id = {""};
	String[] offering_ids = {"0","0","0","0","0","0","0","0","0","0"};
	String[] apptDateTime = {chosenDate, chosenTime};
	
	// IP ADDRESS IS 10.0.2.2
	final static String[] URL = {"http://10.0.2.2:3000/api/appointments.json"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
		findViews();
		populateSpinnerWithStylists();
		apptSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if ( isConnected() ) {
					new HttpAsyncTask().execute(URL, stylist_id, offering_ids, apptDateTime);
				} else {
					
				}
				
			}
		});

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
	public void findViews() {
		apptDate = (EditText)findViewById(R.id.apptDate);
		apptTime = (EditText)findViewById(R.id.apptTime);
		spStylistSelect = (Spinner)findViewById(R.id.spStylistSelect);
		apptSubmit = (Button)findViewById(R.id.apptSubmit);	
	}
	
	// Check for internet connection
	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
	
	// Populate Spinner with <string-array name="stylists"> in res/values/strings.xml
	public void populateSpinnerWithStylists() {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.stylists, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spStylistSelect.setAdapter(adapter);
		spStylistSelect.setOnItemSelectedListener(this);
	}
	
	// Implemented function from Spinner spStylistSelect.setOnItemSelectedListener
	// stylist_id value based on ActiveRecord Model Stylist
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		int stylistId = spStylistSelect.getSelectedItemPosition();
		switch (stylistId) {
		case 0:
			chosenStylist = getString(R.string.theresa);
			stylist_id[0] = "1";
			break;
		case 1:
			chosenStylist = getString(R.string.chrissy);
			stylist_id[0] = "2";
			break;
		case 2:
			chosenStylist = getString(R.string.stacy);
			stylist_id[0] = "3";
			break;
		case 3:
			chosenStylist = getString(R.string.sheri);
			stylist_id[0] = "5";
			break;
		case 4:
			chosenStylist = getString(R.string.krista);
			stylist_id[0] = "6";
			break;
		case 5:
			chosenStylist = getString(R.string.junae);
			stylist_id[0] = "4";
			break;
		}
		
	}

	// Implemented function from Spinner spStylistSelect.setOnItemSelectedListener
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		chosenStylist = getString(R.string.theresa);
		stylist_id[0] = "1";
	}
	
	// Code implemented directly from http://developer.android.com/guide/topics/ui/controls/checkbox.html
	public void selectedServices(View v) {
		// Status of checkbox
		// true = checked false = unchecked
		boolean checked = ( ( CheckBox ) v ).isChecked();
		
		// Determine which check boxes were checked
		// Order of `case` statements based on Left-to-Right reading of layout/form.xml
		// Order of offering_ids (value and index) are based off Offering model (Rails)
		// EXAMPLE: A value of 0 at index 3 in int[] offering_ids means the 
		// ActiveRecord object Offering.find(id: 4) is NOT selected...
		// Rails API will ignore 0 values
		switch ( v.getId() ) {
		case R.id.cbHairCut:
			if (checked) {
				chosenServices += getString(R.string.haircut) + " ";
				offering_ids[0] = "1";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.haircut) + " "), "");
				break;
			}
		case R.id.cbExtensions:
			if (checked) {
				chosenServices += getString(R.string.hairExtensions) + " ";
				offering_ids[9] = "10";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.hairExtensions) + " "), "");
				break;
			}
		case R.id.cbColor:
			if (checked) {
				chosenServices += getString(R.string.color) + " ";
				offering_ids[1] = "2";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.color) + " "), "");
				break;
			}
		case R.id.cbPartialColor:
			if (checked) {
				chosenServices += getString(R.string.partialColor) + " ";
				offering_ids[2] = "3";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.partialColor) + " "), "");
				break;
			}
		case R.id.cbHilites:
			if (checked) {
				chosenServices += getString(R.string.hilites) + " ";
				offering_ids[3] = "4";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.hilites) + " "), "");
				break;
			}
		case R.id.cbPartialHilites:
			if (checked) {
				chosenServices += getString(R.string.partialHilites) + " ";
				offering_ids[4] = "5";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.partialHilites) + " "), "");
				break;
			}
		case R.id.cbPerm:
			if (checked) {
				chosenServices += getString(R.string.perm) + " ";
				offering_ids[7] = "8";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.perm) + " "), "");
				break;
			}
		case R.id.cbUpdo:
			if (checked) {
				chosenServices += getString(R.string.updo) + " ";
				offering_ids[6] = "7";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.updo) + " "), "");
				break;
			}
		case R.id.cbBrazilianBlowout:
			if (checked) {
				chosenServices += getString(R.string.brazilianBlowout) + " ";
				offering_ids[8] = "9";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.brazilianBlowout)+ " "), "");
				break;
			}
		case R.id.cbWax:
			if (checked) {
				chosenServices += getString(R.string.wax) + " ";
				offering_ids[5] = "6";
				break;
			} else {
				chosenServices = chosenServices.replaceAll((getString(R.string.wax) + " "), "");
				break;
			}
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
		return result;
	}
	
	public static String sendAppointment(String URL, JSONObject params) {
		String result = "";
		InputStream inputStream = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			
			HttpPost httpPost = new HttpPost(URL);
			
			String jsonParams = params.toString();
			
			StringEntity stringEntity = new StringEntity(jsonParams, HTTP.UTF_8);
			
			httpPost.setEntity(stringEntity);
			
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			inputStream = httpResponse.getEntity().getContent();
			
			if (inputStream != null) {
				result = convertInputStreamToString(inputStream);
			} else {
				result = "Not Successful!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private class HttpAsyncTask extends AsyncTask<String[], Void, String> {

		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			JSONObject jsonParams = new JSONObject();
			try {
				jsonParams.put("stylist_id", params[1][0]);
				jsonParams.put("offering_ids", Arrays.toString(params[2]));
				jsonParams.put("date", params[3][0]);
				jsonParams.put("time", params[3][1]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return sendAppointment(params[0][0], jsonParams);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Toast.makeText(getBaseContext(), "DONE", Toast.LENGTH_LONG).show();
		}
		
		
		
	}
	
}

