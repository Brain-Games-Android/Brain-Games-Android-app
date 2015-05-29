package cs.boys.quizy_app;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.CheckBox;

public class SettingsActivity extends Activity implements OnClickListener {

	private Spinner spinner;
    private static final String[]paths = {"Easy", "Medium", "Hard", "YOLO"};
    Button ok,cancel;

	
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
        
        ok=(Button)findViewById(R.id.btnok);
        cancel=(Button)findViewById(R.id.btncancel);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        
        SQLiteDatabase db=openOrCreateDatabase("SettingsDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS settings(id int primary key, username VARCHAR,diff int,music int);");
        String sqlString = "SELECT count(*) FROM settings  where id=1";
        Cursor mcursor = db.rawQuery(sqlString, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0){
        	loadPrevious(db);
        }
        else{
        	//populate table
        	//default values :P
        	Log.e("MyActivity","yolo");
        }

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
	
	public void loadPrevious(SQLiteDatabase db){
		String sqlString = "SELECT * FROM settings";
        Cursor mcursor = db.rawQuery(sqlString, null);
        mcursor.moveToFirst();
        String un = mcursor.getString(1);
        int diff=mcursor.getInt(2);
        int music=mcursor.getInt(3);
    	spinner.setSelection(diff);
    	EditText username = (EditText)findViewById(R.id.unEditText);
    	CheckBox checkmusic = (CheckBox)findViewById(R.id.checkMusic);
    	username.setText(un);
    	//8a mporousa na exw bool to music sth bash kai na to pernaw kateu8eian anti gia if
    	//alla den hksera ti paizei me ta bool kai bariomoun na to koitaksw htan prwi
    	if(music==1) checkmusic.setChecked(true);
    	else if(music==0) checkmusic.setChecked(false);
    	else Log.e("MyActivity","oti nanai");
    	Log.e("MyActivity","update name "+username.getText().toString());//+diff+" =a= "+music);
    	
    	
    	
	}
	
	public void updateSettings(){
		SQLiteDatabase db=openOrCreateDatabase("SettingsDB", Context.MODE_PRIVATE, null);
		
		EditText username = (EditText)findViewById(R.id.unEditText);
    	CheckBox checkmusic = (CheckBox)findViewById(R.id.checkMusic);
		
		String sqlString = "SELECT count(*) FROM settings";
        Cursor mcursor = db.rawQuery(sqlString, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        
        
        
        if(icount>0){
        	Log.e("MyActivity","update name "+username.getText().toString());//+diff+" =a= "+music);
			ContentValues cv = new ContentValues();
			
			cv.put("username",username.getText().toString()); //These Fields should be your String values of actual column names
			cv.put("diff", spinner.getSelectedItemPosition());
			
			if(checkmusic.isChecked()==true) cv.put("music",1);
			else cv.put("music",1);
			
			db.update("settings", cv, "id "+"="+1, null);
        }
        else{
        	int music;
			if(checkmusic.isChecked()==true) music=1;
			else music=0;  	
        	
        	String sql ="INSERT or replace INTO settings " +//"INSERT or replace INTO settings " +
        			"(id, username, diff, music) " +
        			"VALUES(1,'"+username.getText().toString()+ "'," +spinner.getSelectedItemPosition()+","+music+")";       
            db.execSQL(sql);
        }
		
	}
	
	public void onClick(View v){
		switch (v.getId())
		{
			case R.id.btnok:
				
				updateSettings();
				SettingsActivity.this.onBackPressed();
				break;
				
			case R.id.btncancel:
				//SQLiteDatabase db=openOrCreateDatabase("SettingsDB", Context.MODE_PRIVATE, null);
				//db.execSQL("DROP TABLE IF EXISTS 'settings'"); 
				SettingsActivity.this.onBackPressed();
				break;
		}
	}
		
}
