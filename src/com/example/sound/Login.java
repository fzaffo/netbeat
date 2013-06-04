package com.example.sound;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseUser;



public class Login extends Activity implements OnClickListener{
	
	EditText password;
	EditText username;
	ImageView loading;
	Button accedi;
	Toast toast;
	Intent intentRegistrazione;
	Intent intentMainActivity;
	TextView check;
	TextView registrati;
	Boolean memoryLog;
	ImageView progressbar;
	Animation animation;
	
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, "knDAr83ZUaOfuBIhOABDEBayycz2lwihKHxGfXkg", "pUsxmeRk5TfTSbx4dIoUYlMM6ZXA6VImBpaGWRyS");
		intentMainActivity = new Intent(this, MainActivity.class);
/*		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser != null)
		{
			startActivity(intentMainActivity);
    		overridePendingTransition(R.anim.fadein,R.anim.fadeout); 
		}*/
		intentRegistrazione = new Intent(this, Registrati.class);
		setContentView(R.layout.login);
		accedi = (Button) findViewById(R.id.controllo);
		registrati = (TextView) findViewById(R.id.registrati);
        animation = AnimationUtils.loadAnimation(this, R.drawable.custom_progressbar_login);
        progressbar = (ImageView) findViewById(R.id.progressbar);
		password = (EditText) findViewById(R.id.password);
		username = (EditText) findViewById(R.id.username);
		check = (TextView) findViewById(R.id.check);
		check.setTextAppearance(this, R.style.TextStayConnectedLoginNoSelected);
		toast = Toast.makeText(this, "Dati incorretti", Toast.LENGTH_SHORT);
		accedi.setOnClickListener(this); 
		registrati.setOnClickListener(this);
		check.setOnClickListener(this);
		}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.controllo:
			
			
			//AsyncTaskInitializeMain asyncTask = new AsyncTaskInitializeMain(this);
			//asyncTask.execute();
			ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
				  public void done(ParseUser user, com.parse.ParseException e) {
				    if (user != null) {
				      // Hooray! The user is logged in.
				    	{
				    		startActivity(intentMainActivity);
				    		overridePendingTransition(R.anim.fadein,R.anim.fadeout); 
				    		//loading.clearAnimation();
				    		//loading.setVisibility(View.INVISIBLE);
						}
				    } else {
				      // Signup failed. Look at the ParseException to see what happened.
				    	toast.show();
				    }
				  }
				});
			username.setVisibility(View.INVISIBLE);
			password.setVisibility(View.INVISIBLE);
			check.setVisibility(View.INVISIBLE);
			accedi.setVisibility(View.INVISIBLE);
			registrati.setVisibility(View.INVISIBLE);
			progressbar.setVisibility(View.VISIBLE);
			progressbar.setAnimation(animation);
			break;
		case R.id.registrati:
			startActivity(intentRegistrazione);
			break;
		case R.id.check:
			if(!memoryLog)
			{
				check.setText("Adesso rimarrai connesso");
				check.setAlpha(1);
				check.setShadowLayer(10, 0, 0, Color.WHITE);
				memoryLog = true;
			}
			else
			{
				check.setText("Così non rimarrai connesso");
				check.setAlpha((float) 0.2);
				check.setShadowLayer(0, 0, 0, Color.WHITE);
				memoryLog = false;
			}
			break;
		
	}
}

}


/*ParseUser user2 = null;
ParseUser user0 = new ParseUser();
user0.setUsername("username0");
user0.setPassword("password0");
try {
	user0.signUp();
} catch (ParseException e2) {
	// TODO Auto-generated catch block
	e2.printStackTrace();
}
for(int i = 1;i<10;i++){
	ParseUser user1 = new ParseUser();
	if(i==1)
	{
		try {
			ParseUser.logOut();
			ParseUser.logIn("username0", "password0");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user1 = ParseUser.getCurrentUser();
		try {
			user1.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	else
	{
		try {
			ParseUser.logOut();
			ParseUser.logIn("username"+(i-1), "password"+(i-1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user1 = ParseUser.getCurrentUser();
	}
	user2 = new ParseUser();
	user2.setUsername("username"+i);
	user2.setPassword("password"+i);
	try {
		user2.signUp();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	ParseRelation relationFollower = user2.getRelation("Follower");
	relationFollower.add(user1);

	try {
		
		user2.save();
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	if(i==1)
	{
		try {
			ParseUser.logOut();
			ParseUser.logIn("username0", "password0");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user1 = ParseUser.getCurrentUser();
		try {
			user1.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	else
	{
		try {
			ParseUser.logOut();
			ParseUser.logIn("username"+(i-1), "password"+(i-1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user1 = ParseUser.getCurrentUser();
	}
	
	
	ParseRelation relationFollow = user1.getRelation("Follow");
	relationFollow.add(user2);
	
	try {
		user1.save();
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
}
finish();*/



//CREAZIONE POST
/*for(int i =0 ; i<10; i++)
{
	try {
		ParseUser.logIn("username"+i, "password"+i);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	ParseUser user = ParseUser.getCurrentUser();
	for(int j=0 ; j<2; j++)
	{
		ParseObject post = new ParseObject("Post");
		post.put("description", "description"+j+"utente : username"+i);
		post.put("duration", (0+i+j));
		ParseRelation relationUser = post.getRelation("user");
		relationUser.add(user);
		ParseRelation relationLike = post.getRelation("likes");
		relationLike.add(user);
		for(int k=0; k<2; k++)
		{
			ParseObject comment = new ParseObject("Comment");
			comment.put("text", "commento "+k+" post "+j+" utente "+i);
			ParseRelation relationCommentUser = comment.getRelation("user");
			relationCommentUser.add(user);
			try {
				comment.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ParseRelation relationComment = post.getRelation("comment");
			relationComment.add(comment);
		}
		try {
			post.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}*/
