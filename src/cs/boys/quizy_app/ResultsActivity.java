package cs.boys.quizy_app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ResultsActivity extends Activity {
	
	
	// indicates whether the activity is linked to service player.
	private boolean mIsBound = false;
	
	// Saves the binding instance with the service.
	private MusicService mServ;
	boolean isSound=true;
	boolean isIntent=false;
	
	Button playAgain;
	Button backMenu;
	
	TextView score;
	
	TextView a1;
	TextView a2;
	TextView a3;
	TextView a4;
	TextView a5;
	TextView a6;
	TextView a7;
	TextView a8;
	TextView a9;
	TextView a10;
	
	private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
              = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);                
      //username/firstname/lastname/subject/diff
      		//("http://85.74.105.202:8080/BrainGames/webresources/post_sub_diff");
      		//("http://192.168.1.3:8080/BrainGames/webresources/post_sub_diff");
      		//("http://localhost:8989/BrainGames/webresources/post_sub_diff");
      		String serverURL = "http://85.74.125.123:8080/BrainGames/webresources/CheckResults";
      		//String serverURL = "http://192.168.1.4:8989/BrainGames/webresources/CheckResults";       
      		// Use AsyncTask execute Method To Prevent ANR Problem
      		      new LongOperation().execute(serverURL);
      		      
      		playAgain=(Button)findViewById(R.id.PlayAgain);
      		backMenu=(Button)findViewById(R.id.backMenu);
      		playAgain.setOnClickListener(new View.OnClickListener () {
            	public void onClick (View v)
            	{
            		
            		Intent Start;
            		//Bundle MyBundle;
            		//Start = new Intent (MenuActivity.this, GameActivity.class);
            		Start = new Intent (ResultsActivity.this, LoadGameActivity.class);
            		isIntent = true;
            		startActivity(Start);
            		finish ();
            	}
            });
      		backMenu.setOnClickListener(new View.OnClickListener () {
            	public void onClick (View v)
            	{
            		
            		Intent Start;
            		//Bundle MyBundle;
            		//Start = new Intent (MenuActivity.this, GameActivity.class);
            		ResultsActivity.this.onBackPressed();
            		finish ();
            	}
            });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
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
        private ProgressDialog Dialog = new ProgressDialog(ResultsActivity.this);
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
        @SuppressLint("NewApi")
		protected Void doInBackground(String... urls) {
             
            ///************ Make Post Call To Web Server **********
            BufferedReader reader=null;
    
                 // Send data 
                try
                { 
                	
                	//SQLiteDatabase db=openOrCreateDatabase("SettingsDB", Context.MODE_PRIVATE, null);
            		Intent Int1 = getIntent();
                    Bundle Bu = Int1.getExtras();
                    int question_id[] = Bu.getIntArray ("question_id");
                    Log.e("TEST", "edw1");
                    String questions_ans[] = Bu.getStringArray ("questions_ans");
                    String tail="";
                    Log.e("TEST", "edw2");
                    for(int i=0;i<10;i++){
                    	//sthn parakatw grammh GAMIETAI O DIAS
                    	//Log.e("WS", questions_ans[i]);
                    	if(questions_ans[i]!=null){
                    		tail+=question_id[i]+"/"+URLEncoder.encode(questions_ans[i], "UTF-8")+"/";
                    	}
                    	else{
                    		tail+=question_id[i]+"/"+URLEncoder.encode("#", "UTF-8")+"/";
                    	}
                    		
                    	//org.apache.commons.httpclient.util.URIUtil
                        //URIUtil.encodeQuery(input);
                    }
                    Log.e("TEST", "edw3");
                    SQLiteDatabase db=openOrCreateDatabase("QuestionsDB", Context.MODE_PRIVATE, null);
            		String sqlString = "SELECT * FROM stat_id";
                    Cursor mcursor = db.rawQuery(sqlString, null);
                    
            	    mcursor.moveToFirst();
            		tail+=mcursor.getInt(0);
            		Log.e("WS", tail);
            		tail=tail.replaceAll(" " , "%20");

            		//URIUtil.encodeQuery("http://www.google.com?q=a b");
                	//String tail=loadPrevious(db);
             
                	
                   // Defined URL  where to send data
                   URL url = new URL(urls[0]+"/"+tail);
            		//URL url = new URL(Uri.parse(urls[0]+"/"+tail).buildUpon().build().toString());
            		//URL url = new URL(urls[0]+"/"+Uri.encode(tail));
                  // Send POST data request
                 //  Log.e("open","conn");
                   URLConnection conn = url.openConnection(); 
                   conn.setConnectTimeout(10000);
                 // Log.e("conn","oppned");
                  //conn.setRequestProperty("Accept-Charset", "UTF-8");
                  //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
               
                  // Get the server response 
                  //new InputStreamReader(urlConn.getInputStream(), "UTF-8")); 
                  //Log.e("FUCK","1  "+conn.getInputStream().toString());
                  reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));//, StandardCharsets.UTF_8));
                 // Log.e("FUCK","2");
                  StringBuilder sb = new StringBuilder();
                  //Log.e("FUCK","3");
                  String line = null;
                 
                    // Read Server Response
                    //while((line = reader.readLine()) != null)
                  Log.e("prin","katastrafhka");
                    	 while((line = reader.readLine()) != null)
                        {
                               // Append server response in string
            					//Log.e("apelpisia",line);
                               sb.append(line + " ");
                        }
                    Log.e("GOD","katastrafhka");
                    // Append Server Response To Content String 
                   Content = sb.toString();
                }
                catch(Exception ex)
                {
                    Error = ex.toString();
                    //Log.e("MARIKA",reader.readLine());
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
        		
                //uiUpdate.setText("Output : "+Error);
            	Log.e("MY ERROR", Error.toString());
            	
        		
            	ResultsActivity.this.onBackPressed();
        		finish();
                  
            } else {
               
                // Show Response Json On Screen (activity)
            	Log.e("RES WS", Content.toString());
                //uiUpdate.setText( Content );
        		String split_em[]=Content.split("#");
        		Gson g=new Gson();
        		String wrong[]= g.fromJson(split_em[0], String[].class);
        		
        		score=(TextView)findViewById(R.id.score);
        		score.setText(split_em[1]);
        		score.setTextColor(Color.rgb(0, 0, 0));     
        		score.setPadding(10, 10, 0, 0);     
        		
        		a1=(TextView)findViewById(R.id.a1);        		
        		a2=(TextView)findViewById(R.id.a2);
        		a3=(TextView)findViewById(R.id.a3);
        		a4=(TextView)findViewById(R.id.a4);
        		a5=(TextView)findViewById(R.id.a5);
        		a6=(TextView)findViewById(R.id.a6);
        		a7=(TextView)findViewById(R.id.a7);
        		a8=(TextView)findViewById(R.id.a8);
        		a9=(TextView)findViewById(R.id.a9);
        		a10=(TextView)findViewById(R.id.a10);
        		
        		Intent Int1 = getIntent();
                Bundle Bu = Int1.getExtras();
                //int question_id[] = Bu.getIntArray ("question_id");
                String questions_ans[] = Bu.getStringArray ("questions_ans");
        		
        		String toShow[]= new String[10];
        		for(int i=0;i<10;i++){
        			if(wrong[i]==null){
        				toShow[i]=questions_ans[i];
        			}
        			else{
        				if(questions_ans[i]!=null)
        					toShow[i]=questions_ans[i]+"/Correct:"+wrong[i];
        				else
        					toShow[i]="N.A./Correct:"+wrong[i];
        			}
        		}
        		
        		a1.setText(toShow[0]);
        		a2.setText(toShow[1]);
        		a3.setText(toShow[2]);
        		a4.setText(toShow[3]);
        		a5.setText(toShow[4]);
        		a6.setText(toShow[5]);
        		a7.setText(toShow[6]);
        		a8.setText(toShow[7]);
        		a9.setText(toShow[8]);
        		a10.setText(toShow[9]);
        		
        		
        		a1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a1.setTextColor(Color.rgb(0, 0, 0));     
        		a1.setPadding(10, 10, 0, 0);     
        		a1.setEllipsize(TruncateAt.MARQUEE);     
        		a1.setSingleLine();     
        		a1.setMarqueeRepeatLimit(-1);     
        		a1.setHorizontallyScrolling(true);     
        		a1.setFocusableInTouchMode(true);
        		a1.setSelected(true);
        		
        		a2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a2.setTextColor(Color.rgb(0, 0, 0));     
        		a2.setPadding(10, 10, 0, 0);     
        		a2.setEllipsize(TruncateAt.MARQUEE);     
        		a2.setSingleLine();     
        		a2.setMarqueeRepeatLimit(-1);     
        		a2.setHorizontallyScrolling(true);     
        		a2.setFocusableInTouchMode(true);
        		a2.setSelected(true);
        		    
        		a3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a3.setTextColor(Color.rgb(0, 0, 0));     
        		a3.setPadding(10, 10, 0, 0);     
        		a3.setEllipsize(TruncateAt.MARQUEE);     
        		a3.setSingleLine();     
        		a3.setMarqueeRepeatLimit(-1);     
        		a3.setHorizontallyScrolling(true);     
        		a3.setFocusableInTouchMode(true);
        		a3.setSelected(true);
        	   
        		a4.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a4.setTextColor(Color.rgb(0, 0, 0));     
        		a4.setPadding(10, 10, 0, 0);     
        		a4.setEllipsize(TruncateAt.MARQUEE);     
        		a4.setSingleLine();     
        		a4.setMarqueeRepeatLimit(-1);     
        		a4.setHorizontallyScrolling(true);     
        		a4.setFocusableInTouchMode(true);
        		a4.setSelected(true);
        		  
        		a5.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a5.setTextColor(Color.rgb(0, 0, 0));     
        		a5.setPadding(10, 10, 0, 0);     
        		a5.setEllipsize(TruncateAt.MARQUEE);     
        		a5.setSingleLine();     
        		a5.setMarqueeRepeatLimit(-1);     
        		a5.setHorizontallyScrolling(true);     
        		a5.setFocusableInTouchMode(true);
        		a5.setSelected(true);
        		
        		a6.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a6.setTextColor(Color.rgb(0, 0, 0));     
        		a6.setPadding(10, 10, 0, 0);     
        		a6.setEllipsize(TruncateAt.MARQUEE);     
        		a6.setSingleLine();     
        		a6.setMarqueeRepeatLimit(-1);     
        		a6.setHorizontallyScrolling(true);     
        		a6.setFocusableInTouchMode(true);
        		a6.setSelected(true);
        	
        		a7.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a7.setTextColor(Color.rgb(0, 0, 0));     
        		a7.setPadding(10, 10, 0, 0);     
        		a7.setEllipsize(TruncateAt.MARQUEE);     
        		a7.setSingleLine();     
        		a7.setMarqueeRepeatLimit(-1);     
        		a7.setHorizontallyScrolling(true);     
        		a7.setFocusableInTouchMode(true);
        		a7.setSelected(true);
        	
        		a8.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a8.setTextColor(Color.rgb(0, 0, 0));     
        		a8.setPadding(10, 10, 0, 0);     
        		a8.setEllipsize(TruncateAt.MARQUEE);     
        		a8.setSingleLine();     
        		a8.setMarqueeRepeatLimit(-1);     
        		a8.setHorizontallyScrolling(true);     
        		a8.setFocusableInTouchMode(true);
        		a8.setSelected(true);
        		    
        		a9.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a9.setTextColor(Color.rgb(0, 0, 0));     
        		a9.setPadding(10, 10, 0, 0);     
        		a9.setEllipsize(TruncateAt.MARQUEE);     
        		a9.setSingleLine();     
        		a9.setMarqueeRepeatLimit(-1);     
        		a9.setHorizontallyScrolling(true);     
        		a9.setFocusableInTouchMode(true);
        		a9.setSelected(true);
        	    
        		a10.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);     
        		a10.setTextColor(Color.rgb(0, 0, 0));     
        		a10.setPadding(10, 10, 0, 0);     
        		a10.setEllipsize(TruncateAt.MARQUEE);     
        		a10.setSingleLine();     
        		a10.setMarqueeRepeatLimit(-1);     
        		a10.setHorizontallyScrolling(true);     
        		a10.setFocusableInTouchMode(true);
        		a10.setSelected(true);
                   
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
	
}
