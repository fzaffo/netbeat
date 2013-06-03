package com.example.sound;



import com.parse.ParseUser;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class Post extends LinearLayout implements OnClickListener{

	LinearLayout TEXT;
	LinearLayout info;
	String query;
	ParseUser user;
	String postUser;
	String postDescription;
	int postDuration;
	String postID;
	Intent intentProfile;
	Context context;
	

	
	public Post(Context context) {
		super(context);
		this.context = context;
		setOrientation(LinearLayout.HORIZONTAL);
		TEXT=new LinearLayout(context);
		TEXT.setOrientation(LinearLayout.VERTICAL);
		TEXT.setMinimumHeight(PixelFromDP(60));
		info = new LinearLayout(context);
		info.setOrientation(LinearLayout.HORIZONTAL);
		setBackgroundResource(R.drawable.bordi_post);
		addView(TEXT, 0, setParams_Margins(PixelFromDP(250), LayoutParams.WRAP_CONTENT, PixelFromDP(5), PixelFromDP(2), PixelFromDP(2), PixelFromDP(2)));
		this.setOnClickListener(this);
		intentProfile = new Intent(context, Profilo.class);
	}
	
	
	public LayoutParams setParams_Margins(int width, int height, int marginLeft, int marginRight, int marginTop, int marginBottom) {
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width, height);
		param.setMargins(marginLeft, marginTop, marginRight, marginBottom);
		return param;
	}
	
	public void createText(final String username, String description, int duration, String ID){
		final ImageView imgLikes = new ImageView(getContext());
		final ImageView imgComm = new ImageView(getContext());
		QuickContactBadge contactUser = new QuickContactBadge(getContext());
		imgLikes.setImageResource(R.drawable.like);
		imgComm.setImageResource(R.drawable.comment);
		contactUser.setImageResource(R.drawable.profilephoto);
		final TextView nameText = new TextView(getContext());
		nameText.setTextAppearance(getContext(), R.style.TextNameSmallPost);
		final TextView descText = new TextView(getContext());
		descText.setTextAppearance(getContext(), R.style.TextDescriptionSmallPost);
		descText.setMinimumHeight(PixelFromDP(30));
		descText.setMaxLines(2);
		final TextView likesText = new TextView(getContext());
		likesText.setTextAppearance(getContext(), R.style.TextDateLikesCommentsSmallPost);
		final TextView commText = new TextView(getContext());
		commText.setTextAppearance(getContext(), R.style.TextDateLikesCommentsSmallPost);
		
		this.addView(contactUser, 1, setParams(PixelFromDP(60),LayoutParams.MATCH_PARENT));
		TEXT.addView(nameText, 0);//, setParams(LayoutParams.WRAP_CONTENT,PixelFromDP(20)));
		TEXT.addView(descText, 1);//, setParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		TEXT.addView(info, 2, setParams(LayoutParams.MATCH_PARENT,PixelFromDP(10)));
		info.addView(imgComm, 0, setParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		info.addView(commText, 1);//, setParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		info.addView(imgLikes, 2, setParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		info.addView(likesText, 3);
		contactUser.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							intentProfile.putExtra("username", username);
							context.startActivity(intentProfile);
						}
		});
		nameText.setText(username);
		postUser=username;
		descText.setText(description);
		postDescription = description;
		likesText.setText(String.valueOf(duration));
		commText.setText("3");
		postDuration = duration;
		postID = ID;
	}
	
	
	public LayoutParams setParams(int width, int height) {
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(width, height);
		return param;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int index;
		LinearLayout layout = (LinearLayout) this.getParent();
		Context context = this.getContext();
		if(MainActivity.indexOldOpenPost!=-1)
		{
			View backView = layout.getChildAt(MainActivity.indexOldOpenPost);
			index = layout.indexOfChild(backView);
			layout.removeViewAt(MainActivity.indexOldOpenPost);		
			Post post = new Post(context);
			post.createText(MainActivity.postUserOld, MainActivity.postDescriptionOld, MainActivity.postDurationOld, MainActivity.postIDOld);
			layout.addView(post,index);//,post.setParams_Margins(LayoutParams.MATCH_PARENT,120, 0, 0, 0, 0));
		}
		index = layout.indexOfChild(v);
		layout.removeViewAt(index);
		PostBig postBig= new PostBig(context);
		layout.addView(postBig,index,postBig.setParams_Margins(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT, 0, 0, 0, 0));
		postBig.createText( postBig, "http://netbeat.sitotop.com/user/1/1.mp3",postUser, postDescription, postDuration, postID);
		MainActivity.indexOldOpenPost = index;
		MainActivity.postUserOld = postUser;
		MainActivity.postDescriptionOld = postDescription;
		MainActivity.postDurationOld = postDuration;
		MainActivity.postIDOld = postID;
	}

	
	private int PixelFromDP(int i) {
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
		return (int)((i * displayMetrics.density) + 0.5);
	}

}
