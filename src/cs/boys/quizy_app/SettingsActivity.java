package cs.boys.quizy_app;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class SettingsActivity extends Activity {

	private Spinner spinner;
    private static final String[]paths = {"Easy", "Medium", "Hard", "YOLO"};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(SettingsActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
        	
        	 public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        	        switch (position) {
        	            case 0:
        	                // Whatever you want to happen when the first item gets selected
        	                break;
        	            case 1:
        	                // Whatever you want to happen when the second item gets selected
        	                break;
        	            case 2:
        	                // Whatever you want to happen when the thrid item gets selected
        	                break;

        	        }
        	    }
        	 public void onNothingSelected(AdapterView<?> parent) {
                 // Do nothing, just another required interface callback
             }
        	
        });

    }

   


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
}
