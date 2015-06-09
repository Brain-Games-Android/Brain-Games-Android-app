package cs.boys.quizy_app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class LoadGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_game);
		
		//username/firstname/lastname/subject/diff
		//("http://85.74.105.202:8080/BrainGames/webresources/post_sub_diff");
		//("http://192.168.1.3:8080/BrainGames/webresources/post_sub_diff");
		//("http://localhost:8989/BrainGames/webresources/post_sub_diff");
		String serverURL = "http://192.168.1.3:8080/BrainGames/webresources/getSettings";
		       
		// Use AsyncTask execute Method To Prevent ANR Problem
		      new LongOperation().execute(serverURL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load_game, menu);
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
	
	
// Class with extends AsyncTask class
    
    private class LongOperation  extends AsyncTask<String, Void, Void> {
          
        // Required initialization
         
       // private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(LoadGameActivity.this);
        //String data =""; 
        TextView uiUpdate = (TextView) findViewById(R.id.unTextView);
       // TextView jsonParsed = (TextView) findViewById(R.id.fnTextView);
       // int sizeData = 0;  
      //  EditText serverText = (EditText) findViewById(R.id.lnEditText);
         
         
        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
              
            //Start Progress Dialog (Message)
            
            Dialog.setMessage("Loading Game..");
            Dialog.show();
              
             
        }
  
        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
             
            ///************ Make Post Call To Web Server **********
            BufferedReader reader=null;
    
                 // Send data 
                try
                { 
                	
                	SQLiteDatabase db=openOrCreateDatabase("SettingsDB", Context.MODE_PRIVATE, null);
                	String tail=loadPrevious(db);
             
                	
                   // Defined URL  where to send data
                   URL url = new URL(urls[0]+"/"+tail);
                      
                  // Send POST data request
        
                  URLConnection conn = url.openConnection(); 

               
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
                  
                //uiUpdate.setText("Output : "+Error);
            	Log.e("MY ERROR", Error.toString());
                  
            } else {
               
                // Show Response Json On Screen (activity)
            	Log.e("RES WS", Content.toString());
                //uiUpdate.setText( Content );
        		String split_em[]=Content.split("##");
        		String quest_ans[]=split_em[0].split("#");//get diff's
        		//Log.e("QUEST",quest_ans[0]);
        		//Log.e("stats_id",split_em[1]);
        		String id=split_em[1];
            	SQLiteDatabase db=openOrCreateDatabase("QuestionsDB", Context.MODE_PRIVATE, null);
                
            		db.execSQL("DROP TABLE IF EXISTS questions;");
            		db.execSQL("DROP TABLE IF EXISTS stat_id;");
            	//   db.execSQL("drop TABLE settings IF EXISTS;");
                   db.execSQL("CREATE TABLE IF NOT EXISTS questions(id int primary key, question VARCHAR,ans1 VARCHAR," +
                   		" ans2 VARCHAR,ans3 VARCHAR,ans4 VARCHAR);");
                   db.execSQL("CREATE TABLE IF NOT EXISTS stat_id(stats_id int primary key);");
                   
                   
                   String sql ="INSERT or replace INTO stat_id " +//"INSERT or replace INTO settings " +
               			"(stats_id) VALUES("+id+");";
                   db.execSQL(sql);
                   
                   for(int i=0;i<60;i+=6){
                	   //Log.e("loopa", "VALUES("+quest_ans[i+0]+",'"+quest_ans[i+1]+"','"+quest_ans[i+2]+"','"+quest_ans[i+3]+"','"+quest_ans[i+4]+"');" );
                	   sql ="INSERT or replace INTO questions " +//"INSERT or replace INTO settings " +
                  			"(id, question, ans1,ans2,ans3,ans4) " +
                  			"VALUES("+quest_ans[i+0]+",'"+quest_ans[i+1]+"','"+quest_ans[i+2]+"','"+quest_ans[i+3]+"','"+quest_ans[i+4]+"','"+quest_ans[i+5]+"');";
                	   db.execSQL(sql);
                   }
   
                   Intent Start = new Intent (LoadGameActivity.this, GameActivity.class);
           		   startActivity(Start);
                   
             }
        }
          
    }
    
    
    
    public String loadPrevious(SQLiteDatabase db){
    	String un="";
        String fn="";
        String ln="";
        String diff_name="";
        String categ_name="";
        
        String sqlString2= "SELECT name FROM sqlite_master WHERE type='table' AND name='settings'";
        Cursor mcursor2 = db.rawQuery(sqlString2, null);
        if(mcursor2.getCount()<=0){
        	Log.e("check", "pame sta settings");
        	Intent Start = new Intent (LoadGameActivity.this, SettingsActivity.class);
    		startActivity(Start);
        }
		String sqlString = "SELECT * FROM settings";
        Cursor mcursor = db.rawQuery(sqlString, null);
        Log.e("check", "prin dw gia bash");
        /*if(mcursor.getCount()<=0){
        	Log.e("check", "pame sta settings");
        	Intent Start = new Intent (LoadGameActivity.this, SettingsActivity.class);
    		startActivity(Start);
        }
        else{*/
	        mcursor.moveToFirst();
	        un = mcursor.getString(1);
	        fn = mcursor.getString(2);
	        ln = mcursor.getString(3);
	        //int diff=mcursor.getInt(4);
	        //int categ=mcursor.getInt(5);
	        categ_name=mcursor.getString(7);
	        diff_name=mcursor.getString(8);
	
	        Log.e("WS", un+"/"+fn+"/"+ln+"/"+categ_name+"/"+diff_name);
	        
        //}
        return un+"/"+fn+"/"+ln+"/"+categ_name+"/"+diff_name;
    	//Log.e("MyActivity","update name "+username.getText().toString());//+diff+" =a= "+music);
    	
    	
    	
	}
    
    
}
