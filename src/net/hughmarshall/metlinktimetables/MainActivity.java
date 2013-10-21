package net.hughmarshall.metlinktimetables;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends Activity implements OnItemSelectedListener {

	// String constant is the key intent.putExtra() in sendMessage()
	public final static String EXTRA_MESSAGE = "net.hughmarshall.NWEN243MetlinkApp.MESSAGE";
	public final static String LOG_TAG = "LogMainActivity";
	String timetableURL = "http://m.metlink.org.nz/timetables/";
	String mode, route, direction;

	@Override
	/**
	 * Called when the activity is created
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the spinners
		Spinner modeSpinner = (Spinner) findViewById(R.id.mode_spinner);
		Spinner routeSpinner = (Spinner) findViewById(R.id.route_spinner);
		Spinner directionSpinner = (Spinner) findViewById(R.id.direction_spinner);

		// Create ArrayAdapters using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> modeAdapter = ArrayAdapter.createFromResource(this, R.array.mode_spinner_array,
				android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> routeAdapter = ArrayAdapter.createFromResource(this, R.array.route_spinner_array,
				android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> directionAdapter = ArrayAdapter.createFromResource(this,
				R.array.direction_spinner_array, android.R.layout.simple_spinner_item);

		// Specify the layout to use when the list of choices appears
		modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		routeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		directionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Apply the adapter to the spinner
		modeSpinner.setAdapter(modeAdapter);
		routeSpinner.setAdapter(routeAdapter);
		directionSpinner.setAdapter(directionAdapter);

		// Set this as the listener of the spinner
		modeSpinner.setOnItemSelectedListener(this);
		routeSpinner.setOnItemSelectedListener(this);
		directionSpinner.setOnItemSelectedListener(this);

	}

	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		// If itemSelected is the default "Choose" string, don't do
		// anything
		if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Transport Type...")
				|| parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Route...")
				|| parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Direction...")) {

		} else {

			// mode_spinner
			if (parent.getId() == R.id.mode_spinner) {
				mode = parent.getItemAtPosition(pos).toString() + "/";
			}

			// route_spinner
			if (parent.getId() == R.id.route_spinner) {
				route = parent.getItemAtPosition(pos).toString() + "/";
			}
			// direction_spinner
			if (parent.getId() == R.id.direction_spinner) {
				if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("TO Wellington")){
					direction = "inbound";
				}else if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("FROM Wellington")){
					direction = "outbound";
				}
			}
		}
	}

	/**
	 * Called when the user clicks the View Timetable button
	 * 
	 * @param view
	 *            The view
	 */
	public void viewTimetable(View view) {
		// create a new intent, passing it this context and the class
		// of the app component to deliver the intent to
		Intent intent = new Intent(this, DisplayMessageActivity.class);

		// Message is URL for a timetable search + the contents of the
		// spinners
		timetableURL = timetableURL + mode + route + direction;
		intent.putExtra(EXTRA_MESSAGE, timetableURL);

		// For checking url being submitted in LogCat
		Log.i(MainActivity.LOG_TAG, timetableURL);

		// Reset timeTableURL to default string
		timetableURL = "http://m.metlink.org.nz/timetables/";
		// start an instance of DisplayMessageActivity as specified by the
		// intent
		startActivity(intent);
	}

	public void onNothingSelected(AdapterView<?> parent) {
		// another interface callback
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
