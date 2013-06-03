package com.example.sound;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.slidinglayer.SlidingLayer;
import com.slidingmenu.lib.SlidingMenu;


public class MainActivity extends Activity implements   OnSeekBarChangeListener, OnCompletionListener, OnRefreshListener<ScrollView>{

	
	static int indexOldOpenPost = -1;
	static String postUserOld;
	static String postDescriptionOld;
	static String postIDOld;
	static int postDurationOld;
	LinearLayout layout;
	static Context context;
	int i;
	String usernamme = null;
	int nElementi;
	View controlloGrandezzaPostAlCambio;
	static MediaPlayer player;
	Object[] list;
	static ParseObject postReadyForComment = null;
	ParseUser postUser;
	static Boolean commentLoaded = false;
	Post post;
	static SlidingLayer sliding;
	PullToRefreshScrollView refresh;
	OnClickListener postListener;
	static int index;
	static LinearLayout commenti;
	SlidingMenu menu;
	static BarraSuperiore menuSopra;
	static BarraInferiore menuSotto;
	static SeekBar seekbar;
	static String query;
	static JSONObject jsondata;
	static JSONArray jsonarray;
	static Date data;
	static SimpleDateFormat dateformat;
	static SimpleDateFormat timeformat;
	static int nCommenti;
	
	private final static Handler handler = new Handler();
    private final static Runnable updatePositionRunnable = new Runnable() {
            public void run() {
                    updatePosition();
            }
    };

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "knDAr83ZUaOfuBIhOABDEBayycz2lwihKHxGfXkg", "pUsxmeRk5TfTSbx4dIoUYlMM6ZXA6VImBpaGWRyS");
		ParseAnalytics.trackAppOpened(getIntent());
		player = new MediaPlayer();
		setContentView(R.layout.activity_main);
		context = this;	
		post = new Post(context);		
		dateformat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		layout = (LinearLayout) findViewById(R.id.linear);
		menuSopra = (BarraSuperiore) findViewById(R.id.barraSuper);
		menuSopra.setActivity(this);
		sliding = (SlidingLayer) findViewById(R.id.slidingLayer1);
		refresh = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
		commenti = (LinearLayout) findViewById(R.id.layoutCommenti);
		menu = new SlidingMenu(this);
		seekbar = (SeekBar) findViewById(R.id.seekbar);
	    refresh.setOnRefreshListener(this);
	    seekbar.setOnSeekBarChangeListener(this);
	    player.setOnCompletionListener(this);
	    AsyncTaskInitializeMain asyncTask = new AsyncTaskInitializeMain(this);
		asyncTask.execute();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static void startPlayer(String pathToFile){
		player.reset();	
		try {
			player.setDataSource(context, Uri.parse(pathToFile));
			player.prepareAsync();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		seekbar.setProgress(0);
		seekbar.setMax(player.getDuration());
		player.start();
		menuSopra.animazioneCDStart();
		updatePosition();
		
	}
	
	public static void updatePosition(){
        handler.removeCallbacks(updatePositionRunnable);
        seekbar.setProgress(player.getCurrentPosition());    
        handler.postDelayed(updatePositionRunnable, 1000);
        }
	
	public static void stopPlayer(){
		player.stop();
		menuSopra.animazioneCDStop();
		handler.removeCallbacks(updatePositionRunnable);
        seekbar.setProgress(0);
	}
	
	public static void animazioneCDStart(ImageView img) {
		RotateAnimation animation= new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setFillAfter(false);
        animation.setRepeatCount(Animation.INFINITE);
        img.startAnimation(animation);
	}
	
	public static void animazioneCDStop(ImageView img) {
		img.clearAnimation();
	}
	
	public static void sliding(String ID) {
		sliding.openLayer(true);
		nCommenti = getNumberOfComment(ID);
		String[][] comm = new String[nCommenti][2];
		getComment(postReadyForComment,nCommenti,comm);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT-5, 40);
		commenti.removeAllViews();
		TextView tv = new TextView(context);
		tv.setText("Nessun commento");
		commenti.addView(tv,param);
	}
	
	
	public static void createTextOfComment(String[][] comm) {
		commenti.removeAllViews();
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT-5, 40);
		LinearLayout.LayoutParams paramLayoutCommenti = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT-5, 120);
		for(int i = 0; i < comm.length; i++)
		{
			if(comm[i][0]!=null)
			{
				LinearLayout commLayout = new LinearLayout(context);
				commLayout.setOrientation(LinearLayout.VERTICAL);
				TextView tv1 = new TextView(context);
				TextView tv2 = new TextView(context);
				TextView tv3 = new TextView(context);
				tv1.setText(comm[i][0]);
				tv2.setText(comm[i][1]);
				//tv3.setText(StringFromDate(comm[i][2]));
				commLayout.addView(tv1,param);
				commLayout.addView(tv2,param);
				//commLayout.addView(tv3,param);
				commenti.addView(commLayout,paramLayoutCommenti);
			}
			else
				break;
		}
	}
	
	private static String StringFromDate(String string) {
		// TODO Auto-generated method stub
		try {
			data = dateformat.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.toString();
	}

	void initStateSlidingLayer() {
        LayoutParams rlp = (LayoutParams) sliding.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        sliding.setShadowWidthRes(R.dimen.shadow_width);
        sliding.setShadowDrawable(R.drawable.sidebar_shadow);
        sliding.setLayoutParams(rlp);
    }

	public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
		// TODO Auto-generated method stub
		sliding.closeLayer(true);
		stopPlayer();
		layout.removeAllViews();
		createListOfPost();
		refresh.onRefreshComplete();
		
	}
	
	
	public void createListOfPost()  {
		float n = (float) nElementi/10;	//10 is the number of post to vieww
		Math.ceil(n);
		for(int j = 0; j<n; j++)
		{
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
		query.setLimit(10);
		query.setSkip(j*10);
		query.include("user");
		query.findInBackground(new FindCallback<ParseObject>() {		
		    public void done(final List<ParseObject> postList, com.parse.ParseException e) {
		        if (e == null) {
		        	for (ParseObject currentPost : postList) {
		        		post = new Post(context);
		    			String IDPost = String.valueOf(currentPost.getObjectId()) ;
		    	    	post.setContentDescription(IDPost);
		    	    	layout.addView(post);//,post.setParams_Margins(LayoutParams.MATCH_PARENT,120, 0, 0, 0, 0));
		    	    	ParseObject user = currentPost.getParseObject("user");
		    	    	String usernameString;
		    	    	usernameString = user.getString("username");
    	    	        post.createText(usernameString, currentPost.getString("description"), currentPost.getInt("duration"), currentPost.getObjectId());
		    	    }
		            //Log.d("score", "Retrieved " + scoreList.size() + " scores");
		        }
		    }
		});
		}
		
	}
	

	
	public void createMenuNotifiche() {
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.sidebar_shadow);
        menu.setBehindOffset(80);
        menu.setFadeDegree(0.5f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.notifiche);
	}
	
	public void getNumberOfPost() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
		try {
			nElementi = query.count();
		} catch (com.parse.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getPathAudioOfPost(int IDCanzone) {
	        query = "SELECT Link " +
	        		"FROM canzone " +
	        		"WHERE ID = " + IDCanzone;
	        jsonarray = CustomHttpClient.sendQuery(query);
	        try {
				jsondata=jsonarray.getJSONObject(0);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        try {
				return jsondata.getString("Link");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
	
	public static void getComment(ParseObject currentPost,final int nComm, final String[][] risultato) {
			      ParseRelation<ParseObject> relation = currentPost.getRelation("comment");
			      ParseQuery<ParseObject> query2;
			      query2 = relation.getQuery();
			      query2.include("user");
			      query2.findInBackground(new FindCallback<ParseObject>() {
			    	    public void done(List<ParseObject> results, com.parse.ParseException e1) {
			    	      if (e1 != null) {
			    	        // There was an error
			    	      } else {
			    	        for(int i = 0; i<nComm; i++)
			    	        {
			    	        	ParseObject currentComment = results.get(i);
			    	        	//ParseObject currentUser = currentComment.getParseObject("user");
			    	        	risultato[i][0] =  "fabio zafferi";//currentUser.getString("username");
			    	        	risultato[i][i] = currentComment.getString("text");
			    	        }
			    	        createTextOfComment(risultato);
			    	      }
			    	    }
			    	});
			      
			    
	}

	

	private static int getNumberOfComment(String postID) {
		int n = 0;
		ParseRelation<ParseObject> relationPostComment = null;
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
		try {
			postReadyForComment = query.get(postID);
			relationPostComment = postReadyForComment.getRelation("comment");
			n = relationPostComment.getQuery().count();
		} catch (com.parse.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return n;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (fromUser) {
            player.seekTo(progress);
        }
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		stopPlayer();
	}

	

}
