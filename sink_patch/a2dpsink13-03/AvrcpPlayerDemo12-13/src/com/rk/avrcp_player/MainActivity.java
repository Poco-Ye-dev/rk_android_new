package com.rk.avrcp_player;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import android.media.session.PlaybackState;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.MediaMetadata;




import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "AvrcpPlayerDemo";
    private Button btnPlayOrPause;
	
	private int ctPlayStatus = PlaybackState.STATE_STOPPED;
	
	private TextView songnameText;
	private TextView artistnameText;
	private TextView albumnameText;
	private TextView songlengthText;


	private MediaBrowser mMediaBrowser;
	private MediaController mediaController;
	

	private String getTimeString(long time){
		String timeStr = "";
        long h = time / 3600;
        long m = time % 3600 / 60;
        long s = time % 60;
        timeStr = "" + (h > 0 ? h : "") +( h>0 ?":":"") + (m > 9 ? m : "0" + m) + ":"
                + (s > 9 ? s : "0" + s);
		return timeStr;
	}

	private final MediaController.Callback mCb = new MediaController.Callback() {
				@Override
				public void onPlaybackStateChanged(PlaybackState state) {
					Log.d(TAG, "Received new playback state"+state);
					if(state != null){
						int playState = state.getState();
						Log.e(TAG, "ACTION_TRACK_EVENT playState "+playState);
					    if(playState == PlaybackState.STATE_STOPPED
							|| playState == PlaybackState.STATE_PAUSED
							|| playState == PlaybackState.STATE_PLAYING)
							ctPlayStatus = playState;
					}
				}
		
				@Override
				public void onMetadataChanged(MediaMetadata metadata) {
					Log.d(TAG, "Received new metadata "+metadata);
					if(metadata != null){
						Log.e(TAG, "ACTION_TRACK_EVENT MediaMetadata "+metadata.getDescription());
						songnameText.setText(metadata.getString(MediaMetadata.METADATA_KEY_TITLE));
						artistnameText.setText(metadata.getString(MediaMetadata.METADATA_KEY_ARTIST));
						albumnameText.setText(metadata.getString(MediaMetadata.METADATA_KEY_ALBUM));
						songlengthText.setText(getTimeString(metadata.getLong(MediaMetadata.METADATA_KEY_DURATION)/1000));
					}
				}
		
				@Override
				public void onSessionDestroyed() {
					super.onSessionDestroyed();
					Log.d(TAG, "Session was destroyed, resetting to the new session token");
					mediaController.unregisterCallback(mCb);
				}
			};
	
		private MediaBrowser.ConnectionCallback mConnectionCallback =
				new MediaBrowser.ConnectionCallback() {
			@Override
			public void onConnected() {
				Log.d(TAG, "onConnected: session token " + mMediaBrowser.getSessionToken());
							
				if (mMediaBrowser.getSessionToken() == null) {
					throw new IllegalArgumentException("No Session token");
				}
				mediaController = new MediaController(MainActivity.this, mMediaBrowser.getSessionToken());
				//this.setMediaController(mediaController);
				PlaybackState state = mediaController.getPlaybackState();
				if(state != null){
						int playState = state.getState();
						Log.e(TAG, "getPlaybackState playState "+playState);
					    if(playState == PlaybackState.STATE_STOPPED
							|| playState == PlaybackState.STATE_PAUSED
							|| playState == PlaybackState.STATE_PLAYING)
							ctPlayStatus = playState;
				}
				MediaMetadata metadata = mediaController.getMetadata();
				if(metadata != null){
						Log.e(TAG, "getMetadata MediaMetadata "+metadata.getDescription());
						songnameText.setText(metadata.getString(MediaMetadata.METADATA_KEY_TITLE));
						artistnameText.setText(metadata.getString(MediaMetadata.METADATA_KEY_ARTIST));
						albumnameText.setText(metadata.getString(MediaMetadata.METADATA_KEY_ALBUM));
						songlengthText.setText(getTimeString(metadata.getLong(MediaMetadata.METADATA_KEY_DURATION)/1000));
				}
				mediaController.registerCallback(mCb);
			}
	
			@Override
			public void onConnectionFailed() {
				Log.d(TAG, "onConnectionFailed");
			}
	
			@Override
			public void onConnectionSuspended() {
				Log.d(TAG, "onConnectionSuspended");
				//this.setMediaController(null);
			}
		};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlayOrPause = (Button)this.findViewById(R.id.BtnPlayorPause);
        
		songnameText =(TextView)this.findViewById(R.id.bt_songname_text);
		artistnameText =(TextView)this.findViewById(R.id.bt_artistname_text);
		albumnameText = (TextView)this.findViewById(R.id.bt_albumname_text);
		songlengthText = (TextView)this.findViewById(R.id.bt_song_length);
		
        Log.d(TAG, "onCreate"); 

        mMediaBrowser = new MediaBrowser(this,
        new ComponentName("com.android.bluetooth", "com.android.bluetooth.avrcpcontroller.BluetoothMediaBrowserService"),
        mConnectionCallback, null);
    }



	
   
    @Override
    protected void onResume() {
    	super.onResume();
    }

    public void onClick(View view) {
        Log.d(TAG, "onClick: ctPlayStatus = " + ctPlayStatus);
        switch (view.getId()) {
            case R.id.BtnPlayorPause:
                if (ctPlayStatus == PlaybackState.STATE_PLAYING) {
                    mediaController.getTransportControls().pause();
                } else {
                    mediaController.getTransportControls().play();
                }
                break;
            case R.id.btnPre:
                mediaController.getTransportControls().skipToPrevious();
                break;
            case R.id.btnNext:
                mediaController.getTransportControls().skipToNext();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();    
    }

	
    @Override
    public void onStart() {
        super.onStart();
        mMediaBrowser.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMediaBrowser.disconnect();
    }
}
