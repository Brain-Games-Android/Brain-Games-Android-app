package cs.boys.quizy_app;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.View;
import android.os.IBinder;
import android.widget.Button;
import android.widget.Toast;

// DEN 8A NAI ETSI TO ABOUT APLA TO XW GIA DEBBUGING Xd



// HEHE HIHI HOHO GOGO! 
public class AboutActivity extends Activity implements Button.OnClickListener{//,ServiceConnection {
	// indicates whether the activity is linked to service player.
	// indicates whether the activity is linked to service player.
	private boolean mIsBound = false;
	
	// Saves the binding instance with the service.

	boolean isSound=true;
	boolean isIntent=false;
	
	// Saves the binding instance with the service.
	private MusicService mServ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		
		// Starting the service of the player, if not already started.
		Intent music = new Intent(this, MusicService.class);
		startService(music);
		
		
		doBindService();
		
		
		// implementing the interface methods onClickListener
		findViewById(R.id.btn_play).setOnClickListener(this);
		findViewById(R.id.btn_pause).setOnClickListener(this);
		findViewById(R.id.btn_stop).setOnClickListener(this);
		//mServ.start();
		    
		
	}
	// interface connection with the service activity
	public void onServiceConnected(ComponentName name, IBinder binder)
	{
		//mServ = ((MusicService.ServiceBinder) binder).getService();
		Toast toast = Toast.makeText(getApplicationContext(), "NOT NULL", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	public void onServiceDisconnected(ComponentName name)
	{
		mServ = null;
		Toast toast = Toast.makeText(getApplicationContext(), "NULL", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	// local methods used in connection/disconnection activity with service.
	
	public void doBindService()
	{
		// activity connects to the service.
 		Intent intent = new Intent(this, MusicService.class);
		//bindService(intent, this, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}
	
	public void doUnbindService()
	{
		// disconnects the service activity.
		if(mIsBound)
		{
			//unbindService(this);
      		mIsBound = false;
		}
	}
	// when closing the current activity, the service will automatically shut down(disconnected).
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		
	}
	// interface buttons that call methods of service control on the activity.
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.btn_play:
				
				//mServ.start();
				break;
				
			case R.id.btn_pause:
				//mServ.pause();
				break;
				
			case R.id.btn_stop:
				//mServ.stop();
				break;
		}
	}
	// menu default
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	
	@Override
	protected void onStart ()
    {
        super.onStart ();
//        if (isSound) {
//			Intent SoundServ = new Intent(this, MusicService.class);
//			startService(SoundServ);
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
					//mServ.pause();
				isIntent = false;
			}
		}
        //mServ.pause();
    }
	
    @Override  
    public void onBackPressed() {
        super.onBackPressed();   
        // Do extra stuff here
        if (isSound) {
			if (!isIntent) {
				if(mServ!=null)
					//mServ.pause();
				isIntent = false;
			}
		}
    }
}