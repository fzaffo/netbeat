package com.example.sound;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Registrazione extends Activity implements OnClickListener{

	ImageView imgMicrofono ;
	boolean registro = false;
	static MediaRecorder mrec;
	static File audiofile;
	TextView txt;
	ContentValues values;
	ImageView play;
	ImageView ok;
	static MediaPlayer player;
	Context context;
	static ProgressBar progressBar;
	BarraSuperiore menuSopra;
	BarraInferiore menuSotto;
	RelativeLayout layout;
	
	private final Handler handler = new Handler();
    private final Runnable updatePositionRunnable = new Runnable() {
            public void run() {
                    updatePosition();
            }
    };
	
	public void onCreate(Bundle savedInstanceStated) {
		super.onCreate(savedInstanceStated);
		setContentView(R.layout.registrazione);
		menuSopra = (BarraSuperiore) findViewById(R.id.barraSuperiore1);
		menuSopra.setActivity(this);
		menuSotto = (BarraInferiore) findViewById(R.id.barraInferiore1);
		layout = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		context=this;
		imgMicrofono = (ImageView) findViewById(R.id.imgMicrofono);
		imgMicrofono.setOnClickListener(this);
		play = (ImageView) findViewById(R.id.play);
		play.setOnClickListener(this);
		ok = (ImageView) findViewById(R.id.ok);
		ok.setOnClickListener(this);
		//txt = (TextView) findViewById(R.id.TextView01);
		values = new ContentValues(3);
		mrec = new MediaRecorder();
		player = new MediaPlayer();
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		progressBar.setMax(30);
		
	}

	protected void updatePosition() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(updatePositionRunnable);
        progressBar.setProgress(progressBar.getProgress()+1);
        if(progressBar.getProgress()<30)
        	handler.postDelayed(updatePositionRunnable, 1000);
        else
        	stopReg();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.imgMicrofono:
		if(registro==false)
		{
			try {
				startReg();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			stopReg();
		
		break;
//		case R.id.home:
//			Intent home_intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(home_intent);
//			break;
		case R.id.ok:
			Intent ok_intent = new Intent(getApplicationContext(), ConfermaUpload.class);
            startActivity(ok_intent);
			break;
		case R.id.play:
			if(audiofile!=null)
			{
				player.reset();
				try {
					
					player.setDataSource(audiofile.getAbsolutePath());
					player.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			player.start();
			}
			else
				//myToast.show();
			break;
		
		}
	}
	
	
	public void startReg() throws IOException{
		   mrec.setAudioSource(MediaRecorder.AudioSource.MIC);
		   mrec.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		   mrec.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		   audiofile=null;
		   File sampleDir = Environment.getExternalStorageDirectory();
		   try 
		   { 
		      audiofile = File.createTempFile("audio", ".3gp", sampleDir);
		   }
		   catch (IOException e) 
		   {
		      return;
		   }
		   mrec.setOutputFile(audiofile.getAbsolutePath());
		   mrec.prepare();
		   progressBar.setProgress(0);
		   mrec.start();
		   registro=true;
		   updatePosition();
	}
	
	public void stopReg() {
		handler.removeCallbacks(updatePositionRunnable);
		mrec.stop();
		mrec.release();
		registro=false;
		processaudiofile();
	}
	
	protected void processaudiofile()
	{
	   
	   long current = System.currentTimeMillis();
	   values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
	   values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
	   values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
	   values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
	   ContentResolver contentResolver = getContentResolver();
	   
	   Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	   Uri newUri = contentResolver.insert(base, values);
	   
	   sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
	}
	
	
}
