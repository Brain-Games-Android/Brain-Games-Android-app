package cs.boys.quizy_app;

import android.app.Service;
import android.widget.MediaController;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;


public class MusicService extends Service   {
	    private static final String TAG = null;
	    MediaPlayer player;
	    public IBinder onBind(Intent arg0) {

	        return null;
	    }
	    @Override
	    public void onCreate() {
	        super.onCreate();
	        player = MediaPlayer.create(this, R.raw.zelda);
	        player.setLooping(true); // Set looping
	        player.setVolume(100,100);

	    }
	    public int onStartCommand(Intent intent, int flags, int startId) {
	        player.start();
	        return 1;
	    }

	    public void onStart(Intent intent, int startId) {
	        // TO DO
	    }
	    public IBinder onUnBind(Intent arg0) {
	        // TO DO Auto-generated method
	        return null;
	    }

	    public void onStop() {

	    }
	    public void onPause() {

	    }
	    @Override
	    public void onDestroy() {
	        player.stop();
	        player.release();
	    }

	    @Override
	    public void onLowMemory() {

	    }
	}




















/*
public class MusicService extends Service implements MediaPlayer.OnErrorListener,
	
	// Interface used by the visual representation of the player controls.
	MediaController.MediaPlayerControl,
	
	// implemented to possibly upgrade the media player interface.
	MediaPlayer.OnBufferingUpdateListener

{
// Connect service who to call onBind
private final IBinder mBinder = new ServiceBinder();

// Instance of media player
MediaPlayer mPlayer;

// Saves the data buffer of the media player (used by MediaController).
private int mBuffer = 0;

public MusicService() { }

// Called by the interface ServiceConnected when calling the service
public class ServiceBinder extends Binder {
 	 MusicService getService()
	 {
		return MusicService.this;
	 }
}

@Override
public IBinder onBind(Intent context)
{
	return mBinder;
}

@Override
public void onCreate () {
	super.onCreate();
	
	create();
}

@Override
public int onStartCommand (Intent intent, int flags, int startId) {
     return START_STICKY;
}

@Override
public void onDestroy () {
	super.onDestroy();
	if(mPlayer != null)
	{
		try{
			 mPlayer.stop();
			 mPlayer.release();
		}finally {
			mPlayer = null;
		}
	}
}

public boolean onError(MediaPlayer mp, int what, int extra) {
	Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
	
	if(mPlayer != null)
	{
		try{
			mPlayer.stop();
			mPlayer.release();
		}finally {
			mPlayer = null;
		}
	}
	return false;
}

public void create()
{
	mPlayer = MediaPlayer.create(this, R.raw.zelda);
	mPlayer.setOnErrorListener(this);

	mPlayer.setOnErrorListener(new OnErrorListener() {
		public boolean onError(MediaPlayer mp, int what, int extra) {
			onError(mPlayer, what, extra);
			return true;
		}
	});
}


public boolean canPause() {
	return true;
}

@Override
public boolean canSeekBackward() {
	return true;
}

@Override
public boolean canSeekForward() {
	return true;
}

@Override
public int getBufferPercentage() {
	return mBuffer;
}

@Override
public int getAudioSessionId() {
	return 0;
}

@Override
public int getCurrentPosition() {
	return mPlayer.getCurrentPosition();
}

@Override
public int getDuration() {
	return mPlayer.getDuration();
}

@Override
public boolean isPlaying() {
	return mPlayer.isPlaying();
}

@Override
public void pause() {
	if(mPlayer != null && isPlaying())
	{
		mPlayer.pause();
		
	}
}

@Override
public void seekTo(int pos) {
	mPlayer.seekTo(pos);
}

public void resume()
{
	if(isPlaying() == false)
	{
		seekTo(getCurrentPosition());
		start();
	}
}

@Override
public void start() {
	if (mPlayer == null) create();
	
	Thread th = new Thread(new Runnable() {
		@Override
		public void run()
		{
			mPlayer.start();
		}
	});
	th.start();
}

public void stop() {
	mPlayer.stop();
	mPlayer.release();
	mPlayer = null;
}

@Override
public void onBufferingUpdate(MediaPlayer mp, int percent) {
	mBuffer = percent;
	
}
}
*/