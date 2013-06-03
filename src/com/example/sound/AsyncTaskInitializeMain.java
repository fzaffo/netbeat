package com.example.sound;


import android.os.AsyncTask;


public class AsyncTaskInitializeMain extends AsyncTask<Void, Void, Void>
{
	private final MainActivity activity;
	
	public AsyncTaskInitializeMain(MainActivity mainActivity)
	{
		this.activity = mainActivity;
	}

	@Override
	protected Void doInBackground(Void... params) {
		activity.initStateSlidingLayer();
		activity.createMenuNotifiche();
	    activity.getNumberOfPost();
	    activity.createListOfPost();
		return null;
	}

	@Override
	protected void onPreExecute()
	{
	}

	protected void onPostExecute()
	{
	}
}