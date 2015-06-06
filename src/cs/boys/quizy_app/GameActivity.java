package cs.boys.quizy_app;

import android.R.integer;
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
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import cs.boys.quizy_app.MusicService;



public class GameActivity extends Activity {//, ServiceConnection {
	
	private RadioGroup rGChoices;
	private TextView tVQuestion;
	private TextView tVChoice0;
	private TextView tVChoice1;
	private TextView tVChoice2;
	private TextView tVChoice3;
	
	
	
	
	private String questionString = "Question?";
	private String choice0 = "Choose.";
	private String choice1 = "Choose... really.";
	private String choice2 = "...";
	private String choice3 = "LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOONG";
	
	
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
		rGChoices = (RadioGroup) findViewById(R.id.rg_choices);
		tVQuestion = (TextView) findViewById(R.id.question);
		tVChoice0 = (TextView) findViewById(R.id.rb_choice0);
		tVChoice1 = (TextView) findViewById(R.id.rb_choice1);
		tVChoice2 = (TextView) findViewById(R.id.rb_choice2);
		tVChoice3 = (TextView) findViewById(R.id.rb_choice3);
		
		// !!!
		setQuestion();		
	}
	
	private void setQuestion() {
		tVQuestion.setText(questionString);
		
		tVChoice0.setText(choice0);
		tVChoice1.setText(choice1);
		tVChoice2.setText(choice2);
		tVChoice3.setText(choice3);
	}
	
	
	
	private boolean checkChoice() {
		Integer selected = rGChoices.getCheckedRadioButtonId();
		
		if (selected < 0) {
			return false;
		}
		else {
			// increment a counter so we set the fields with the 
			// new texts. Plus, keep the score. Probably in a shared
			// preference. and if it's the end the jump to another 
			// activity that sends the shared preference and that
			// activity sends the data to the server and awaits 
			// the results from the server.
			
			return true;
		}
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
