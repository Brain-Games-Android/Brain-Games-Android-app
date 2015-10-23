package cs.boys.quizy_app;


import cs.boys.quizy_app.MusicService;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MenuActivity extends Activity {//implements ServiceConnection {

	// indicates whether the activity is linked to service player.
		private boolean mIsBound = false;
		
		// Saves the binding instance with the service.
		private MusicService mServ;
		boolean isSound=true;
		boolean isIntent=false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		//music related

		
		//for the font
		Typeface tf = Typeface.createFromAsset(getAssets(),"earth aircraft universe.ttf");
		
		//font
		TextView tvStart = (TextView) findViewById(R.id.start);
		tvStart.setTypeface(tf);
		
		//START LISTENER
		tvStart.setOnClickListener(new View.OnClickListener () {
        	public void onClick (View v)
        	{
        		Intent Start;
        		//Bundle MyBundle;
        		//Start = new Intent (MenuActivity.this, GameActivity.class);
        		Start = new Intent (MenuActivity.this, LoadGameActivity.class);
        		isIntent=true;
        		startActivity(Start);
        		//finish ();
        	}
        });
		
		
		//font
		TextView tvSettings = (TextView) findViewById(R.id.settings);
		tvSettings.setTypeface(tf);
		
		//SETTINGS LISTENER
		tvSettings.setOnClickListener(new View.OnClickListener () {
        	public void onClick (View v)
        	{
        		Intent Start;
        		Start = new Intent (MenuActivity.this, SettingsActivity.class);
        		isIntent=true;
        		startActivity(Start);
        	}
        });
		
		//font
		TextView tvAbout = (TextView) findViewById(R.id.about);
		tvAbout.setTypeface(tf);
		
		//ABOUT LISTENER
		tvAbout.setOnClickListener(new View.OnClickListener () {
        	public void onClick (View v)
        	{
        		Intent Start;
        		Start = new Intent (MenuActivity.this, AboutActivity.class);
        		isIntent=true;
        		startActivity(Start);
        	}
        });
		
		//font
		TextView tvExit = (TextView) findViewById(R.id.exit);
		tvExit.setTypeface(tf);
		
		//EXIT LISTENER
		tvExit.setOnClickListener(new View.OnClickListener () {
        	public void onClick (View v)
        	{        		
        		//THIS IS LIKE PRESSING HOME BUTTON
        		MenuActivity.this.finish();
        	    Intent intent = new Intent(Intent.ACTION_MAIN);
        	    intent.addCategory(Intent.CATEGORY_HOME);
        	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	    startActivity(intent);
        	    
        	    //STOP MUSIC AS WELL
        	    //mServ.stop();
        	}
        	
        });
		
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
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
	
	@Override
	protected void onStart ()
    {
		Log.i("start","start it");
        super.onStart ();
        isIntent=false;
        //doBindService();
		Intent music = new Intent(this, MusicService.class);
		startService(music);
		//doBindService();
        //if (mServ!=null) 
//        if(mServ!=null){
//        	//if(mServ.isPlaying()==false)
//        		mServ.start();
//        }
    }
	
	@Override
	protected void onStop ()
    {
		if(isIntent)Log.i("INTENT","true"); else  Log.i("INTENT","false");
        super.onStop ();
        if (isSound) {
			if (!isIntent) {
					Log.i("stop","stop it");
					//mServ.pause();
					Intent music = new Intent(this, MusicService.class);
					stopService(music);
					//doUnbindService();
				isIntent = false;
			}
		}
        
		//doUnbindService();
    }
	
	@Override
	protected void onDestroy ()
    {
        super.onDestroy ();
        //doUnbindService();
    }
	
//	//WHEN SERVICE IS CONNECTED WE START THE MUSIC
//	public void onServiceConnected(ComponentName name, IBinder binder)
//	{
//		mServ = ((MusicService.ServiceBinder) binder).getService();
//		//START MUSIC
//		if(mServ.isPlaying()==false)
//			mServ.start();
//	}
//	
//	public void onServiceDisconnected(ComponentName name)
//	{
//		mServ=null;
//	}
//	
//	// local methods used in connection/disconnection activity with service.
//	
//	public void doBindService()
//	{
//		// activity connects to the service.
// 		Intent intent = new Intent(this, MusicService.class);
//		bindService(intent, this, Context.BIND_AUTO_CREATE);
//		mIsBound = true;
//	}
//	
//	public void doUnbindService()
//	{
//		// disconnects the service activity.
//		if(mIsBound)
//		{
//			unbindService(this);
//      		mIsBound = false;
//		}
//	}

	

}
	

