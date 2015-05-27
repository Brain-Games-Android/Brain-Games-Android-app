package cs.boys.quizy_app;


import cs.boys.quizy_app.MusicService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MenuActivity extends Activity implements ServiceConnection {

	// indicates whether the activity is linked to service player.
		private boolean mIsBound = false;
		
		// Saves the binding instance with the service.
		private MusicService mServ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		//music related
		Intent music = new Intent(this, MusicService.class);
		startService(music);
		doBindService();
		
		//for the font
		Typeface tf = Typeface.createFromAsset(getAssets(),"earth aircraft universe.ttf");
		
		//font
		TextView tvStart = (TextView) findViewById(R.id.start);
		tvStart.setTypeface(tf);
		
		//START LISTENER
		tvStart.setOnClickListener(new View.OnClickListener () {
        	public void onClick (View v)
        	{
        		Toast toast = Toast.makeText(getApplicationContext(), "Start clicked", Toast.LENGTH_LONG);
        		toast.setGravity(Gravity.CENTER, 0, 0);
        		toast.show();
        		Intent Start;
        		//Bundle MyBundle;
        		Start = new Intent (MenuActivity.this, GameActivity.class);
        		//MyBundle = new Bundle();
        		//MyBundle.putInt("SecondVal", N2);
        		//MyBundle.putInt("FirstVal", N1);
        		//MyBundle.putBoolean("ForResult", false);
        		//NextAct.putExtras(MyBundle);
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
        		Toast toast = Toast.makeText(getApplicationContext(), "Settings clicked", Toast.LENGTH_LONG);
        		toast.setGravity(Gravity.CENTER, 0, 0);
        		toast.show();
        		Intent Start;
        		Start = new Intent (MenuActivity.this, SettingsActivity.class);
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
        		Toast toast = Toast.makeText(getApplicationContext(), "About clicked", Toast.LENGTH_LONG);
        		toast.setGravity(Gravity.CENTER, 0, 0);
        		toast.show();
        		Intent Start;
        		Start = new Intent (MenuActivity.this, AboutActivity.class);
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
        		Toast toast = Toast.makeText(getApplicationContext(), "Exit clicked", Toast.LENGTH_LONG);
        		toast.setGravity(Gravity.CENTER, 0, 0);
        		toast.show();
        		
        		//THIS IS LIKE PRESSING HOME BUTTON
        		MenuActivity.this.finish();
        	    Intent intent = new Intent(Intent.ACTION_MAIN);
        	    intent.addCategory(Intent.CATEGORY_HOME);
        	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	    startActivity(intent);
        	    
        	    //STOP MUSIC AS WELL
        	    mServ.stop();
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
        super.onStart ();
        Toast.makeText (this, "On Start", Toast.LENGTH_LONG).show ();
       // mServ.;
        //doBindService(); //music
		//Intent music = new Intent();
		//music.setClass(this,MusicService.class);
		//startService(music);
        //mServ.start();
        
       // Intent objIntent = new Intent(this, MusicService.class);
		//startService(objIntent);
        
    }
	
	@Override
	protected void onDestroy ()
    {
        super.onStop ();
        Toast.makeText (this, "On destroy", Toast.LENGTH_LONG).show ();
        //mServ.stopMusic();
        
        //UNBIND THE SERVICE
        doUnbindService();
        
       // Intent objIntent = new Intent(this, MusicService.class);
        //MusicService.pauseMusic();
	//	stopService(objIntent);

    }
	
	//WHEN SERVICE IS CONNECTED WE START THE MUSIC
	public void onServiceConnected(ComponentName name, IBinder binder)
	{
		mServ = ((MusicService.ServiceBinder) binder).getService();
		//START MUSIC
		mServ.start();
	}
	
	public void onServiceDisconnected(ComponentName name)
	{
		mServ = null;
	}
	
	// local methods used in connection/disconnection activity with service.
	
	public void doBindService()
	{
		// activity connects to the service.
 		Intent intent = new Intent(this, MusicService.class);
		bindService(intent, this, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}
	
	public void doUnbindService()
	{
		// disconnects the service activity.
		if(mIsBound)
		{
			unbindService(this);
      		mIsBound = false;
		}
	}

	
//LET THEM BE
	
	/*public void playAudio(View view) {
		Intent objIntent = new Intent(this, MusicService.class);
		startService(objIntent);
		}

		public void stopAudio(View view) {
		Intent objIntent = new Intent(this, MusicService.class);
		stopService(objIntent);    
		}*/
	
/*	private ServiceConnection Scon =new ServiceConnection(){ //not finalk

		public void onServiceConnected(ComponentName name, IBinder binder) {
			ServiceBinder Loc_binder = (ServiceBinder) binder;
            mServ = Loc_binder.getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
	};

	void doBindService(){
		bindService(new Intent(this,MusicService.class),Scon,Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	void doUnbindService()
	{
		if(mIsBound)
		{
			unbindService(Scon);
      		mIsBound = false;
		}
	}*/
}
	

