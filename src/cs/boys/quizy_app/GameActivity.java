package cs.boys.quizy_app;



import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
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

	
	// indicates whether the activity is linked to service player.
	private boolean mIsBound = false;
	
	// Saves the binding instance with the service.
	private MusicService mServ;
	boolean isSound=true;
	boolean isIntent=false;
	
	Button ansA ;
	Button ansB ;
	Button ansC ;
	Button ansD ;
	
	Button submit;
	Button pass;
	TextView time;
	
	boolean init_flag=false;
	boolean end_flag=false;
	boolean chose=false;
	int currentQuestion;
	
	
	int question_id[]=new int[10];
	String questions[]=new String[10];
	boolean questions_confirmed[]=new boolean[10];
	String questions_ans[]=new String[10];
	String ans1[]=new String[10];
	String ans2[]=new String[10];
	String ans3[]=new String[10];
	String ans4[]=new String[10];
	
	
	int time_left;
    
    Timer MyTimer;
    
    class TimerJob extends TimerTask
    {
        @Override
        public void run ()
        {
            time_left--;
        	if(time_left==0){
        		Bundle MyBundle;
				Intent Start = new Intent (GameActivity.this, ResultsActivity.class);
				MyBundle = new Bundle();
				MyBundle.putIntArray("question_id", question_id);
				MyBundle.putStringArray("questions_ans", questions_ans);
				//MyBundle.putBoolean("ForResult", false);
				Start.putExtras(MyBundle);	
				isIntent = true;
				 MyTimer.cancel();
        		startActivity(Start);
        		finish();
        	}
        	else{
        		ModifyTimer(time_left);
        	}
            
        }
    };
	
    
    TimerJob MyJob = new TimerJob ();    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		//Intent objIntent = new Intent(this, MusicService.class);

		MyTimer = new Timer ();
        MyTimer.schedule (MyJob, 0, 1000);
		
		if(init_flag==false){
			for(int i=0;i<10;i++){
				questions_confirmed[i]=false;
			}
			init_flag=true;
		}
		
		 ansA = (Button) findViewById(R.id.buttonA);
		 ansB = (Button) findViewById(R.id.buttonB);
		 ansC = (Button) findViewById(R.id.buttonC);
		 ansD = (Button) findViewById(R.id.buttonD);
		 
		 submit = (Button) findViewById(R.id.submitButton);
		 pass = (Button) findViewById(R.id.passButton);
		 
		
		//START LISTENER
		ansA.setOnClickListener(this);
		ansB.setOnClickListener(this);
		ansC.setOnClickListener(this);
		ansD.setOnClickListener(this);
		submit.setOnClickListener(this);
		pass.setOnClickListener(this);
		
		load_questions();//first question
		
		currentQuestion=0;
		show_question(currentQuestion);
		
		SQLiteDatabase db=openOrCreateDatabase("QuestionsDB", Context.MODE_PRIVATE, null);
		String sqlString = "SELECT time FROM diff_time";
        Cursor mcursor = db.rawQuery(sqlString, null);
        
	    mcursor.moveToFirst();
		time_left=mcursor.getInt(0);
		
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
		Button yo=(Button)v;
		
		switch (v.getId())
		{
			case R.id.buttonA:
				chose=true;
				//tmp=(Button)v; //yolo mode
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				questions_ans[currentQuestion]=yo.getText().toString();
				
				break;
				
			case R.id.buttonB:
				chose=true;
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				questions_ans[currentQuestion]=yo.getText().toString();
				break;
			case R.id.buttonC:
				chose=true;
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));		
				questions_ans[currentQuestion]=yo.getText().toString();
				break;
				
			case R.id.buttonD:
				chose=true;
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
				ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
				ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));	
				questions_ans[currentQuestion]=yo.getText().toString();
				break;
			
			case R.id.passButton:
				do{
					currentQuestion++;
					if(currentQuestion>=10) currentQuestion=0;
				}while(questions_confirmed[currentQuestion]);
				
				show_question(currentQuestion);
				if(questions_ans[currentQuestion]!=null && !questions_ans[currentQuestion].equals("")){
					if(questions_ans[currentQuestion].equals(ansA.getText().toString())){
						ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
						ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						chose=true;
					}
					else if(questions_ans[currentQuestion].equals(ansB.getText().toString())){
						ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
						ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						chose=true;
					}
					else if(questions_ans[currentQuestion].equals(ansC.getText().toString())){
						ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
						ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						chose=true;
					}
					else {
						ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
						ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						chose=true;
					}
						
				}
				else{
					ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					chose=false;
				}
				break;
			
			case R.id.submitButton:
				if(chose==false) break;
				questions_confirmed[currentQuestion]=true;

				//Log.e("pathmeno",ansA.getResources().getDrawable());
				int keep=currentQuestion;
				do{
					Log.e("while1","8a bgw?  "+currentQuestion+"  "+keep);
					currentQuestion++;
					if(currentQuestion>=10) currentQuestion=0;
					if(keep==currentQuestion){
						end_flag=true;
						//Bundle MyBundle;
		        		//Start = new Intent (MenuActivity.this, GameActivity.class);
						Bundle MyBundle;
						Intent Start = new Intent (GameActivity.this, ResultsActivity.class);
						MyBundle = new Bundle();
						MyBundle.putIntArray("question_id", question_id);
						MyBundle.putStringArray("questions_ans", questions_ans);
						//MyBundle.putBoolean("ForResult", false);
						Start.putExtras(MyBundle);	
						isIntent = true;
						 MyTimer.cancel();
		        		startActivity(Start);
		        		finish();
		        		break;//xD WTF
					}
					//if(currentQuestion>=10) currentQuestion=0;
				}while(questions_confirmed[currentQuestion]);
				
				show_question(currentQuestion);
				if(questions_ans[currentQuestion]!=null && !questions_ans[currentQuestion].equals("")){
					if(questions_ans[currentQuestion].equals(ansA.getText().toString())){
						ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
						ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					}
					else if(questions_ans[currentQuestion].equals(ansB.getText().toString())){
						ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
						ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					}
					else if(questions_ans[currentQuestion].equals(ansC.getText().toString())){
						ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
						ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					}
					else {
						ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.btncorrect));
						ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
						ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					}
					chose=true;
					Log.i("MAMA","TRUE");
				}
				else{
					ansD.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					ansA.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					ansC.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					ansB.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttonshape));
					chose=false;
					Log.i("MAMA","FALSE");
				}
				break;
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
        MyJob.cancel();
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
		question.setMovementMethod(new ScrollingMovementMethod());
		ansA = (Button) findViewById(R.id.buttonA);
		 ansB = (Button) findViewById(R.id.buttonB);
		 ansC = (Button) findViewById(R.id.buttonC);
		 ansD = (Button) findViewById(R.id.buttonD);
		 Log.e("index re",index+" "+questions[index]);
		 question.setText("Question "+(currentQuestion+1)+"\n"+questions[index]);
		 ansA.setText(ans1[index]);
		 ansB.setText(ans2[index]);
		 ansC.setText(ans3[index]);
		 ansD.setText(ans4[index]);
	}
	
	
	Handler MyHandler = new Handler ()
    {
        @Override
        public void handleMessage (Message Msg)
        {
            Bundle bu = Msg.getData ();
            int tl = bu.getInt ("tl");
    		time=(TextView)findViewById(R.id.time);
    		time.setText(Integer.toString(tl));
        }
    };
    
    public void ModifyTimer (int tl)
    {
        Message msg = new Message ();
        Bundle b = new Bundle ();
        b.putInt ("tl", tl);
        msg.setData (b);
        MyHandler.sendMessage (msg);
    }
    

}
