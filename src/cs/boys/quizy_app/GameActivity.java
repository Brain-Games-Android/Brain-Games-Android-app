package cs.boys.quizy_app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cs.boys.quizy_app.MusicService;



public class GameActivity extends Activity implements  OnClickListener{//, ServiceConnection {

	Button ansA ;
	Button ansB ;
	Button ansC ;
	Button ansD ;
	// indicates whether the activity is linked to service player.
	//private boolean mIsBound = false;
	
	// Saves the binding instance with the service.
	//private MusicService mServ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		//Intent objIntent = new Intent(this, MusicService.class);
		//startService(objIntent);
		
		//music related
			//	Intent music = new Intent(this, MusicService.class);
				//startService(music);
				//doBindService();
		
		 ansA = (Button) findViewById(R.id.buttonA);
		 ansB = (Button) findViewById(R.id.buttonB);
		 ansC = (Button) findViewById(R.id.buttonC);
		 ansD = (Button) findViewById(R.id.buttonD);
		
		//START LISTENER
		ansA.setOnClickListener(this);
		ansB.setOnClickListener(this);
		ansC.setOnClickListener(this);
		ansD.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
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
	
	public void onClick(View v) {
		
		//GIA NA FUGOUN TA WARNINGS 8ELEI 
		//setBackground ANTI GIA setBackgroundDrawable ALLA 8ELEI API16 KAI PANW
		//KAI EXOUME MIN TO 15
		Button tmp;
		switch (v.getId())
		{
			case R.id.buttonA:
				//tmp=(Button)v; //yolo mode
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
        		Toast toast = Toast.makeText(getApplicationContext(), "correct clicked", Toast.LENGTH_LONG);
        		toast.setGravity(Gravity.CENTER, 0, 0);
        		toast.show();
				break;
				
			case R.id.buttonB:
				ansA = (Button) findViewById(R.id.buttonA);
				ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				//tmp=(Button)v; //yolo mode
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnwrong));
				break;
				
			case R.id.buttonC:
				ansA = (Button) findViewById(R.id.buttonA);
				ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				//tmp=(Button)v; //yolo mode
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnwrong));				
				break;
				
			case R.id.buttonD:
				ansA = (Button) findViewById(R.id.buttonA);
				ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				//tmp=(Button)v; //yolo mode
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnwrong));				
				break;
		}
	}
    
	
	@Override
	protected void onStart ()
    {
        super.onStart ();
        Toast.makeText (this, "On Start", Toast.LENGTH_LONG).show ();
        //if (mServ!=null) mServ.start();
    }
	
	@Override
	protected void onStop ()
    {
        super.onStop ();
        Toast.makeText (this, "On Start", Toast.LENGTH_LONG).show ();
        //mServ.pause();
    }
	
	@Override
	protected void onDestroy ()
    {
        super.onDestroy ();
        Toast.makeText (this, "On destroy", Toast.LENGTH_LONG).show ();
        //mServ.stopMusic();
        
        //mServ.pause();
        //UNBIND THE SERVICE
        //doUnbindService();

    }
	
	//WHEN SERVICE IS CONNECTED WE START THE MUSIC
/*		public void onServiceConnected(ComponentName name, IBinder binder)
		{
			mServ = ((MusicService.ServiceBinder) binder).getService();
			//START MUSIC
			//mServ.start();
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

8*/
}
