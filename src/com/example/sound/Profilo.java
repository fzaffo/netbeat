package com.example.sound;


import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Profilo extends Activity implements OnClickListener  {
	
	Intent intentImgButtonImpostazioni;
	Intent intentImgButtonCommenti;
	Intent intentImgButtonLike;
	Bitmap img;
	LinearLayout info;
	LinearLayout listOfPost;
	Bitmap bitmapProfile;
	BitmapDrawable bitmpaDrawableProfileBlur;
	BitmapDrawable bitmapDrawableProfilePhoto;
	ImageView imgProfile;
	TextView settingsText;
	TextView txtUsername;
	Post post;
	Context context;
	int nElementi;
	Bundle bun;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profilo);
		bun = getIntent().getExtras();
		context = this;
		imgProfile = (ImageView) findViewById(R.id.imgProfile);
		listOfPost = (LinearLayout) findViewById(R.id.linearLayoutPost);
		img = BitmapFactory.decodeResource(this.getResources(),R.drawable.profilephoto);
		bitmapProfile = BitmapFactory.decodeResource(this.getResources(),R.drawable.profilephoto);
		intentImgButtonImpostazioni = new Intent(this, Impostazioni.class);
		intentImgButtonCommenti = new Intent(this, Commenti.class);
		intentImgButtonLike = new Intent(this, Like.class);
		settingsText = (TextView) findViewById(R.id.txtSettings);
		settingsText.setOnClickListener(this);
		txtUsername = (TextView) findViewById(R.id.txtUsername);
		txtUsername.setText(bun.getCharSequence("username"));
		//settingsText.setShadowLayer(1, 1, 1, Color.BLACK);
		RenderScript rs = RenderScript.create( this );
		Allocation input = Allocation.createFromBitmap( rs, img, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT );
		Allocation output = Allocation.createTyped( rs, input.getType() );
		ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create( rs, Element.U8_4( rs ) );
		script.setRadius( (float) 20 /* e.g. 3.f */ );
		script.setInput( input );
		script.forEach( output );
		output.copyTo( img );
		info = (LinearLayout) findViewById(R.id.principall);
		img = processingBitmap_Brightness(img,50);
		bitmapProfile = addWhiteBorder(bitmapProfile,3);
		bitmpaDrawableProfileBlur = new BitmapDrawable(getResources(),img);
		info.setBackground(bitmpaDrawableProfileBlur);
		bitmapDrawableProfilePhoto = new BitmapDrawable(getResources(),bitmapProfile);
		imgProfile.setBackground(bitmapDrawableProfilePhoto);
		getNumberOfPost();
		createListOfPost();
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
		//query.whereEqualTo("username", "username8");
		query.findInBackground(new FindCallback<ParseObject>() {		
		    public void done(final List<ParseObject> postList, com.parse.ParseException e) {
		        if (e == null) {
		        	for (ParseObject currentPost : postList) {
		        		post = new Post(context);
		    			String IDPost = String.valueOf(currentPost.getObjectId()) ;
		    	    	post.setContentDescription(IDPost);
		    	    	listOfPost.addView(post);//,post.setParams_Margins(LayoutParams.MATCH_PARENT,120, 0, 0, 0, 0));
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
	
	public void getNumberOfPost() {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
		try {
			nElementi = query.count();
		} catch (com.parse.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	private Bitmap addWhiteBorder(Bitmap bmp, int borderSize) {
	    Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
	    Canvas canvas = new Canvas(bmpWithBorder);
	    canvas.drawColor(Color.WHITE);
	    canvas.drawBitmap(bmp, borderSize, borderSize, null);
	    return bmpWithBorder;
	}
	
	
	private Bitmap processingBitmap_Brightness(Bitmap src, int brightnessValue){
	     Bitmap dest = Bitmap.createBitmap(
	       src.getWidth(), src.getHeight(), src.getConfig());
	          
	     for(int x = 0; x < src.getWidth(); x++){
	      for(int y = 0; y < src.getHeight(); y++){
	       int pixelColor = src.getPixel(x, y);
	       int pixelAlpha = Color.alpha(pixelColor);
	        
	       int pixelRed = Color.red(pixelColor) + brightnessValue;
	       int pixelGreen = Color.green(pixelColor) + brightnessValue;
	       int pixelBlue = Color.blue(pixelColor) + brightnessValue;
	        
	       if(pixelRed > 255){
	        pixelRed = 255;
	       }else if(pixelRed < 0){
	        pixelRed = 0;
	       }
	        
	       if(pixelGreen > 255){
	        pixelGreen = 255;
	       }else if(pixelGreen < 0){
	        pixelGreen = 0;
	       }
	        
	       if(pixelBlue > 255){
	        pixelBlue = 255;
	       }else if(pixelBlue < 0){
	        pixelBlue = 0;
	       }
	 
	       int newPixel = Color.argb(
	         pixelAlpha, pixelRed, pixelGreen, pixelBlue);
	        
	       dest.setPixel(x, y, newPixel);
	        
	      } 
	     }
	     return dest; 
	    }
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch(v.getId()){
		case R.id.txtSettings:
			startActivity(intentImgButtonImpostazioni);			
			break;
		}
		
	}
	
}