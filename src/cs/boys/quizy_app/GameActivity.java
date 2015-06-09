package cs.boys.quizy_app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cs.boys.quizy_app.MusicService;



public class GameActivity extends Activity implements  OnClickListener{//, ServiceConnection {

	Button ansA ;
	Button ansB ;
	Button ansC ;
	Button ansD ;
	
	Integer question_id[]=new Integer[10];
	String questions[]=new String[10];
	String ans1[]=new String[10];
	String ans2[]=new String[10];
	String ans3[]=new String[10];
	String ans4[]=new String[10];
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
		
		load_questions();//first question
		
		show_question(0);
		
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
				ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				
				break;
				
			case R.id.buttonB:
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				break;
			case R.id.buttonC:
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));			
				break;
				
			case R.id.buttonD:
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));			
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
	

	
	public void load_questions(){
		SQLiteDatabase db=openOrCreateDatabase("QuestionsDB", Context.MODE_PRIVATE, null);
		String sqlString = "SELECT * FROM questions";
        Cursor mcursor = db.rawQuery(sqlString, null);
        Log.e("check", "prin dw gia bash");
        int i=0;
	        while(mcursor.moveToNext()){
		        question_id[i]= mcursor.getInt(0);
		        questions[i] = mcursor.getString(1);
		        ans1[i] = mcursor.getString(2);
		        ans2[i] = mcursor.getString(3);
		        ans3[i] = mcursor.getString(4);
		        ans4[i] = mcursor.getString(5);
		        i++;
	        }
	}
	
	public void show_question(int index){
		TextView question=(TextView)findViewById(R.id.question);
		ansA = (Button) findViewById(R.id.buttonA);
		 ansB = (Button) findViewById(R.id.buttonB);
		 ansC = (Button) findViewById(R.id.buttonC);
		 ansD = (Button) findViewById(R.id.buttonD);
		 question.setText(questions[index]);
		 ansA.setText(ans1[index]);
		 ansB.setText(ans2[index]);
		 ansC.setText(ans3[index]);
		 ansD.setText(ans4[index]);
	}
}
