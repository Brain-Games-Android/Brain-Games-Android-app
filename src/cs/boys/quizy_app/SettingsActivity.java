package cs.boys.quizy_app;

import cs.boys.quizy_app.MusicService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.CheckBox;


public class SettingsActivity extends Activity implements OnClickListener{//, ServiceConnection {

	
	// indicates whether the activity is linked to service player.
	private boolean mIsBound = false;
	
	// Saves the binding instance with the service.
	private MusicService mServ;
	boolean isSound=true;
	boolean isIntent=false;
	
	
	private Spinner spinner;
    //private static /*final*/ String[]paths;// = {"Easy", "Medium", "Hard", "YOLO"};
    private Spinner spinner2;
    //private static /*final*/ String[]paths2;// = {"football", "cs", "kati", "oxi"};
    Button ok,cancel;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
//		Intent music = new Intent(this, MusicService.class);
//		startService(music);
		//doBindService();
		
		//("http://85.74.105.202:8080/BrainGames/webresources/post_sub_diff");
		//("http://192.168.1.3:8080/BrainGames/webresources/post_sub_diff");
		//("http://localhost:8989/BrainGames/webresources/post_sub_diff");
		String serverURL = "http://85.74.125.123:8080/BrainGames/webresources/post_sub_diff";
		//String serverURL = "http://192.168.1.4:8989/BrainGames/webresources/post_sub_diff";
		
        // Use AsyncTask execute Method To Prevent ANR Problem
        new LongOperation().execute(serverURL);

        
        ok=(Button)findViewById(R.id.btnok);
        cancel=(Button)findViewById(R.id.btncancel);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        
        

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
        String fn = mcursor.getString(2);
        String ln = mcursor.getString(3);
        int diff=mcursor.getInt(4);
        int categ=mcursor.getInt(5);
        int music=mcursor.getInt(6);
    	spinner.setSelection(diff);
    	spinner2.setSelection(categ);
    	EditText username = (EditText)findViewById(R.id.unEditText);
    	EditText fname = (EditText)findViewById(R.id.fnEditText);
    	EditText lname = (EditText)findViewById(R.id.lnEditText);
    	CheckBox checkmusic = (CheckBox)findViewById(R.id.checkMusic);
    	username.setText(un);
    	fname.setText(fn);
    	lname.setText(ln);
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
		EditText fn = (EditText)findViewById(R.id.fnEditText);
		EditText ln = (EditText)findViewById(R.id.lnEditText);
    	CheckBox checkmusic = (CheckBox)findViewById(R.id.checkMusic);
		
		String sqlString = "SELECT count(*) FROM settings";
        Cursor mcursor = db.rawQuery(sqlString, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        
        
        
        if(icount>0){
        	Log.e("MyActivity","update name "+username.getText().toString());//+diff+" =a= "+music);
			ContentValues cv = new ContentValues();
			
			cv.put("username",username.getText().toString()); //These Fields should be your String values of actual column names
			cv.put("fn",fn.getText().toString()); //These Fields should be your String values of actual column names
			cv.put("ln",ln.getText().toString()); //These Fields should be your String values of actual column names
			cv.put("diff", spinner.getSelectedItemPosition());
			cv.put("categ", spinner2.getSelectedItemPosition());
			cv.put("categ_name", spinner.getSelectedItem().toString());
			cv.put("diff_name", spinner2.getSelectedItem().toString());
			
			if(checkmusic.isChecked()==true) cv.put("music",1);
			else cv.put("music",0);
			
			db.update("settings", cv, "id "+"="+1, null);
        }
        else{
        	int music;
			if(checkmusic.isChecked()==true) music=1;
			else music=0;  	
        	
			//username.setText(fn.getText().toString());
        	String sql ="INSERT or replace INTO settings " +//"INSERT or replace INTO settings " +
        			"(id, username,fn,ln, diff,categ, music, categ_name,diff_name) " +
        			"VALUES(1,'"+username.getText().toString()+ "','" +fn.getText().toString() + "','" +ln.getText().toString() + "'," + spinner.getSelectedItemPosition()+ "," + spinner2.getSelectedItemPosition() +","+music + ",'" + spinner.getSelectedItem().toString()+ "','" + spinner2.getSelectedItem().toString()+"')";       
            db.execSQL(sql);
            //Log.e("insert","VALUES(1,'"+username.getText().toString()+ "','" +fn.getText().toString() + "'," +ln.getText().toString() + "'," + spinner.getSelectedItemPosition()+ "," + spinner2.getSelectedItemPosition() +","+music+")");	
        }
		
	}
	
	public void onClick(View v){
		switch (v.getId())
		{
			case R.id.btnok:
				
				updateSettings();
				//isIntent = true;
				SettingsActivity.this.onBackPressed();
				break;
				
			case R.id.btncancel:
				//SQLiteDatabase db=openOrCreateDatabase("SettingsDB", Context.MODE_PRIVATE, null);
				//db.execSQL("DROP TABLE IF EXISTS 'settings'"); 
				//isIntent = true;
				SettingsActivity.this.onBackPressed();
				break;
		}
	}
	
	
	
	

	// Class with extends AsyncTask class
    
    private class LongOperation  extends AsyncTask<String, Void, Void> {
          
        // Required initialization
         
       // private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(SettingsActivity.this);
        //String data =""; 
        TextView uiUpdate = (TextView) findViewById(R.id.unTextView);
       // TextView jsonParsed = (TextView) findViewById(R.id.fnTextView);
       // int sizeData = 0;  
      //  EditText serverText = (EditText) findViewById(R.id.lnEditText);
         
         
        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
              
            //Start Progress Dialog (Message)
            
            Dialog.setMessage("Please wait..");
            Dialog.show();
              
             
        }
  
        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
             
            ///************ Make Post Call To Web Server **********
            BufferedReader reader=null;
    
                 // Send data 
                try
                { 
                   
                   // Defined URL  where to send data
                   URL url = new URL(urls[0]);
                      
                  // Send POST data request
        
                  URLConnection conn = url.openConnection(); 
                  conn.setConnectTimeout(10000);
               
                  // Get the server response 
                    
                  reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                 
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                        {
                               // Append server response in string
                               sb.append(line + " ");
                        }
                    sb.setLength(sb.length() - 1);
                     
                    // Append Server Response To Content String 
                   Content = sb.toString();
                }
                catch(Exception ex)
                {
                    Error = ex.getMessage();
                }
                finally
                {
                    try
                    {
          
                        reader.close();
                    }
        
                    catch(Exception ex) {}
                }
             
          //  /****************************************************
            return null;
        }
          
        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
              
            // Close progress dialog 
            Dialog.dismiss();
              
            if (Error != null) {
            	
            	boolean netstat = isNetworkAvailable();
            	Toast toast;
            	if(!netstat)
                {
                 // emfanisei katallhlou toast mhnymatos
            		toast = Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG);
                }
            	else {
            		toast = Toast.makeText(getApplicationContext(), "Unable to connect to the server.", Toast.LENGTH_LONG);
            	}
            	toast.setGravity(Gravity.CENTER, 0, 0);
        		toast.show();
        		
            	Intent Start = new Intent (SettingsActivity.this, MenuActivity.class);
            	isIntent = true;
        		startActivity(Start);
        		finish();
                  
            } else {
               
                // Show Response Json On Screen (activity)
            	Log.e("WS", Content);
                //uiUpdate.setText( Content );
        		String split_em[]=Content.split("##");
        		String paths[]=split_em[0].split("#");//get diff's
        		String paths2[]=split_em[1].split("#");//get categ's
        		
        		for(int i=0;i<paths.length;i++)
        			paths[i]=paths[i].replaceAll("\"", "");
        		
        		for(int i=0;i<paths2.length;i++)
        			paths2[i]=paths2[i].replaceAll("\"", "");
        		
        		spinner = (Spinner)findViewById(R.id.spinner);
                ArrayAdapter<String>adapter = new ArrayAdapter<String>(SettingsActivity.this,
                        android.R.layout.simple_spinner_item,paths);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                
                spinner2 = (Spinner)findViewById(R.id.spinner2);
                ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(SettingsActivity.this,
                        android.R.layout.simple_spinner_item,paths2);

                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner2.setAdapter(adapter2);

                
                SQLiteDatabase db=openOrCreateDatabase("SettingsDB", Context.MODE_PRIVATE, null);
                //   db.execSQL("drop TABLE settings IF EXISTS;");
                   db.execSQL("CREATE TABLE IF NOT EXISTS settings(id int primary key, username VARCHAR,fn VARCHAR,ln VARCHAR,diff int,categ int,music int,  diff_name VARCHAR, categ_name VARCHAR);");
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
        }
          
    }
	

	@Override
	protected void onStart ()
    {
        super.onStart ();
        //doBindService();
//        if (isSound) {
			Intent SoundServ = new Intent(this, MusicService.class);
			startService(SoundServ);
//			mServ.resume();
//		}
        //if (mServ!=null)mServ.start();
    }
	
	@Override
	protected void onStop ()
    {
		
        super.onStop ();
        if (isSound) {
			if (!isIntent) {
				Log.i("stop","stop it");
				//if(mServ!=null)
					//mServ.pause();
					//doBindService();
					Intent music = new Intent(this, MusicService.class);
					stopService(music);
				isIntent = false;
			}
		}
        
        //doUnbindService();

        //mServ.pause();
    }
	
    @Override  
    public void onBackPressed() {
        super.onBackPressed();   
        
        isIntent = true; //KSEROUME OTI TO MENU DEN EXEI GINEI FINISH!!!
    }

	//WHEN SERVICE IS CONNECTED WE START THE MUSIC
//		public void onServiceConnected(ComponentName name, IBinder binder)
//		{
//			mServ = ((MusicService.ServiceBinder) binder).getService();
//			//START MUSIC
//			mServ.start();
//		}
//		
//		public void onServiceDisconnected(ComponentName name)
//		{
//			mServ = null;
//		}
//		
//		// local methods used in connection/disconnection activity with service.
//		
//		public void doBindService()
//		{
//			// activity connects to the service.
//	 		Intent intent = new Intent(this, MusicService.class);
//			bindService(intent, this, Context.BIND_AUTO_CREATE);
//			mIsBound = true;
//		}
//		
//		public void doUnbindService()
//		{
//			// disconnects the service activity.
//			if(mIsBound)
//			{
//				unbindService(this);
//	      		mIsBound = false;
//			}
//		}	
}


