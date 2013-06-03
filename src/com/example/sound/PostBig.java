package com.example.sound;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PostBig extends LinearLayout implements OnClickListener{

	
	LinearLayout scroll;
	int indice;
	boolean premuto;
	Button bottone;
	int durataMassima;
	String pathToFile;
	String ID;

    
	
	
	public PostBig(Context context) {
		super(context);
		scroll = new LinearLayout(context);
		setBackgroundResource(R.drawable.post_big);
		setOrientation(LinearLayout.HORIZONTAL);
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		int width = metrics.widthPixels;
		addView(scroll, 0, setParams_Margins(width-PixelFromDP(40), LayoutParams.WRAP_CONTENT, 5, 2, 2, 0));
		premuto=false;
		
	}
	
	
	public LayoutParams setParams_Margins(int width, int height, int marginLeft, int marginRight, int marginTop, int marginBottom) {
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width, height);
		param.setMargins(marginLeft, marginTop, marginRight, marginBottom);
		return param;
	}
	
	public void createText(PostBig layoutPrincipale, String path, String username, String desc, int duration, String postID){
		scroll.setOrientation(LinearLayout.VERTICAL);
		scroll.setGravity(0);
		TextView nome = new TextView(getContext());
		TextView description = new TextView(getContext());
		Button play = new Button(getContext());
		bottone = new Button(getContext());
		scroll.addView(nome, 0, setParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		scroll.addView(description, setParams_Margins(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 0, 0, 0, PixelFromDP(2)));
		scroll.addView(play, setParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layoutPrincipale.addView(bottone,1,setParams(PixelFromDP(40), LayoutParams.MATCH_PARENT));
		nome.setText(username);
		description.setText(desc);
		play.setText("Riproduci");
		play.setBackgroundResource(R.drawable.buttonbackground);
		play.setOnClickListener(this);
		play.setContentDescription("Play");
		bottone.setOnClickListener(this);
		bottone.setContentDescription("Bottone");
		pathToFile = path;
		ID = postID;
	}
	
	
	public LayoutParams setParams(int width, int height) {
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width, height);
		return param;
	}




	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getContentDescription().equals("Play"))
		{
			MainActivity.startPlayer(pathToFile);
		}
		else
		{
			MainActivity.sliding(ID);
		}
	}
		

	public void getIndex(int i) {
		indice=i;
	}
	
	private int PixelFromDP(int i) {
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
		return (int)((i * displayMetrics.density) + 0.5);
	}


}
