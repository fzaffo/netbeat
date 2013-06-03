package com.example.sound;


import com.parse.ParseUser;

import main.java.com.u1aryz.android.lib.newpopupmenu.MenuItem;
import main.java.com.u1aryz.android.lib.newpopupmenu.PopupMenu;
import main.java.com.u1aryz.android.lib.newpopupmenu.PopupMenu.OnItemSelectedListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class BarraInferiore extends LinearLayout implements OnClickListener, OnItemSelectedListener{

	TextView temp;
	LinearLayout rowIMG;
	LinearLayout rowNAME;
	ImageView homeIMG;
	ImageView uploadIMG;
	ImageView profileIMG;
	TextView homeTXT;
	TextView uploadTXT;
	TextView profileTXT;
	LayoutParams generalParams;
	LayoutParams imgParams;
	LayoutParams txtParams;
	LayoutParams rowParams;
	PopupMenu popMenu;
	Context generalContext;
	ParseUser currentLoggeduser;
	
	public BarraInferiore(Context context, AttributeSet attrs){
		super(context, attrs);
		generalContext = context;
		this.setOrientation(LinearLayout.VERTICAL);
		generalParams = new LayoutParams (LayoutParams.MATCH_PARENT,PixelFromDP(44));
		imgParams = new LayoutParams (PixelFromDP(45),PixelFromDP(30));
		txtParams = new LayoutParams (PixelFromDP(45),LayoutParams.WRAP_CONTENT);
		rowParams = new LayoutParams (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		this.setLayoutParams(generalParams);
		this.setBackgroundResource(R.drawable.bar_upper);
		rowIMG = new LinearLayout(context);
		rowIMG.setGravity(Gravity.CENTER_HORIZONTAL);
		rowNAME = new LinearLayout(context);
		rowNAME.setGravity(Gravity.CENTER_HORIZONTAL);
		homeIMG = new ImageView(context);
		uploadIMG = new ImageView(context);
		profileIMG = new ImageView(context);
		homeTXT = new TextView(context);
		uploadTXT = new TextView(context);
		profileTXT = new TextView(context);
		homeIMG.setImageResource(R.drawable.home);
		uploadIMG.setImageResource(R.drawable.upload);
		profileIMG.setImageResource(R.drawable.profile);
		creaTXT(homeTXT, "Home");
		creaTXT(uploadTXT, "Upload");
		creaTXT(profileTXT, "Profile");
		rowIMG.setOrientation(LinearLayout.HORIZONTAL);
		rowNAME.setOrientation(LinearLayout.HORIZONTAL);
		rowIMG.setGravity(1);
		rowNAME.setGravity(Gravity.CENTER_HORIZONTAL);
		this.addView(rowIMG, rowParams);
		rowIMG.addView(uploadIMG, imgParams);
		rowIMG.addView(homeIMG, imgParams);
		rowIMG.addView(profileIMG, imgParams);
		this.addView(rowNAME, rowParams);
		rowNAME.addView(uploadTXT, txtParams);
		rowNAME.addView(homeTXT, txtParams);
		rowNAME.addView(profileTXT, txtParams);
		homeIMG.setId(200);
		uploadIMG.setId(100);
		profileIMG.setId(300);
		createPopMenu();
		homeIMG.setOnClickListener(this);
		profileIMG.setOnClickListener(this);
		uploadIMG.setOnClickListener(this);
		
		// TODO Auto-generated constructor stub
	}
	
	private int PixelFromDP(int i) {
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
		return (int)((i * displayMetrics.density) + 0.5);
	}
	
	public BarraInferiore(Context context) {
		super(context);
	}
	
	private void creaTXT(TextView txt, String titolo){
		txt.setText(titolo);
		txt.setGravity(Gravity.CENTER_HORIZONTAL);
		txt.setTextSize(10);
		txt.setTextColor(Color.WHITE);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case 100:	//UploadIMG
			popMenu.show();
			break;
		case 200:	//HomeIMG
			Intent home_intent = new Intent(generalContext, MainActivity.class);
            generalContext.startActivity(home_intent);
            
			break;
		case 300:	//ProfileIMG
			Intent profile_intent = new Intent(generalContext, Profilo.class);
			profile_intent.putExtra("username", ParseUser.getCurrentUser().getUsername());
            generalContext.startActivity(profile_intent);
			break;
		}
	}
	
	public void createPopMenu() {
		popMenu = new PopupMenu(generalContext);
		popMenu.setHeaderTitle("Carico da...");
		popMenu.setOnItemSelectedListener(this);
		popMenu.setWidth(250);
		popMenu.add(0, R.string.stringa_upload_libreria).setIcon(getResources().getDrawable(R.drawable.libreriamusicale));
		popMenu.add(1, R.string.stringa_upload_registra).setIcon(getResources().getDrawable(R.drawable.microfono));
	}

	public void onItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
        case 0:
        	Intent libreriaMusicale_intent = new Intent(generalContext, LibreriaMusicale.class);
            generalContext.startActivity(libreriaMusicale_intent);
            ((Activity) getContext()).finish();
            break;
        case 1:
        	Intent registrazione_intent = new Intent(generalContext, Registrazione.class);
            generalContext.startActivity(registrazione_intent);
            ((Activity) getContext()).finish();
            break;
        }
	}

}
