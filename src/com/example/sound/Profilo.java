package com.example.sound;



import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Profilo extends Activity {
	

	Bitmap img;
	ImageView frame;
	LinearLayout info;
	LinearLayout listOfPost;
	Bitmap bitmapProfile;
	BitmapDrawable bitmpaDrawableProfileBlur;
	BitmapDrawable bitmapDrawableProfilePhoto;
	ImageView imgProfile;
	TextView txtUsername;
	Post post;
	Context context;
	int nElementi;
	Bundle bun;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "knDAr83ZUaOfuBIhOABDEBayycz2lwihKHxGfXkg", "pUsxmeRk5TfTSbx4dIoUYlMM6ZXA6VImBpaGWRyS");
		byte[] imgByte = null;
		setContentView(R.layout.profilo);
		bun = getIntent().getExtras();
		context = this;
		ParseObject user = (ParseObject) ParseUser.getCurrentUser();
		ParseFile dbFile = (ParseFile) user.get("image");
		try {
			imgByte = dbFile.getData();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		img=BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
		imgProfile = (ImageView) findViewById(R.id.imgProfile);
		bitmapProfile = img;
		bitmapProfile = addWhiteBorder(bitmapProfile,5);
		bitmapDrawableProfilePhoto = new BitmapDrawable(getResources(),bitmapProfile);
		imgProfile.setBackground(bitmapDrawableProfilePhoto);
		txtUsername = (TextView) findViewById(R.id.txtUsername);
		txtUsername.setText("#" + bun.getCharSequence("username"));
		RenderScript rs = RenderScript.create( this );
		Allocation input = Allocation.createFromBitmap( rs, img, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT );
		Allocation output = Allocation.createTyped( rs, input.getType() );
		ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create( rs, Element.U8_4( rs ) );
		script.setRadius( 20.f );
		script.setInput( input );
		script.forEach( output );
		output.copyTo( img );
		frame = (ImageView) findViewById(R.id.imageView1);
		info = (LinearLayout) findViewById(R.id.princ);
		img = processingBitmap_Brightness(img,40);
		
		bitmpaDrawableProfileBlur = new BitmapDrawable(getResources(),img);
		frame.setImageDrawable(bitmpaDrawableProfileBlur);
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
	
}