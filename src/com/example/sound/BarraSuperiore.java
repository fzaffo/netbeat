package com.example.sound;


import com.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BarraSuperiore extends LinearLayout implements OnClickListener{

	Activity currentActivity;
	ImageView notifiche;
	ImageView Titolo;
	TextView titolo;
	static ImageView CD;
	LayoutParams parametri;
	LayoutParams parametriCD;
	LayoutParams parametriNotifiche;
	LayoutParams parametriTitolo;
	SlidingMenu menu;
	
	public BarraSuperiore(Context context) {
		super(context);
	}
	
	private int PixelFromDP(int i) {
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
		return (int)((i * displayMetrics.density) + 0.5);
	}
	
	public void createMenuNotifiche() {
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.sidebar_shadow);
        menu.setBehindOffset(80);
        menu.setFadeDegree(0.5f);
        menu.attachToActivity(currentActivity, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.notifiche);
	}

	public BarraSuperiore(Context context, AttributeSet attrs) {
		 
		super(context ,attrs);
		parametri = new LayoutParams(LayoutParams.MATCH_PARENT, PixelFromDP(40));
		this.setLayoutParams(parametri);
		parametriCD = new LayoutParams(PixelFromDP(40), PixelFromDP(26));
		parametriCD.topMargin=PixelFromDP(7);
		parametriNotifiche = new LayoutParams(PixelFromDP(40), PixelFromDP(26));
		parametriNotifiche.topMargin=PixelFromDP(7);
		parametriTitolo = new LayoutParams(PixelFromDP(240), PixelFromDP(35));
		//parametriTitolo.topMargin=PixelFromDP(2);
		this.setBackgroundResource(R.drawable.bar_upper);
		notifiche=new ImageView(context);
		notifiche.setImageResource(R.drawable.notifiche);
		notifiche.setId(10);
		titolo = new TextView(context);
		titolo.setText("netBeat");
		titolo.setLayoutParams(parametriTitolo);
		titolo.setTextAppearance(getContext(), R.style.TextNameUpperMenu);
		titolo.setGravity(Gravity.CENTER);
		CD = new ImageView(context);
		CD.setImageResource(R.drawable.cd);
		CD.setId(20);
		this.addView(notifiche, parametriNotifiche);
		this.addView(titolo);
		this.addView(CD, parametriCD);
		CD.setOnClickListener(this);
		notifiche.setOnClickListener(this);
		menu = new SlidingMenu(context);
		if(MainActivity.player.isPlaying())
			animazioneCDStart();
		}
	
		 
		public BarraSuperiore(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		public void setActivity(Activity activity) {
			// TODO Auto-generated method stub
			currentActivity=activity;
			createMenuNotifiche();
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()){
			case 10:	//ID immagine notifiche
				menu.showMenu();
				break;
			case 20:	//ID immagine del CD
				if(MainActivity.player.isPlaying())
					MainActivity.stopPlayer();
				break;
			}
		}
		
		public static void animazioneCDStart() {
			RotateAnimation animation= new RotateAnimation(0, 360,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
	        animation.setDuration(500);
	        animation.setFillAfter(false);
	        animation.setRepeatCount(Animation.INFINITE);
	        CD.startAnimation(animation);
		}
		
		public static void animazioneCDStop() {
			CD.clearAnimation();
		}

}
