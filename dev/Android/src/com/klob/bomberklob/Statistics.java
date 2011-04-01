package com.klob.bomberklob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.klob.bomberklob.model.Model;

public class Statistics extends Activity implements View.OnClickListener {
	
	private Model model;
	
	private Button cancel;
	
	private TextView userPseudo;
	private TextView gamesWon;
	private TextView gamesLost;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
	            
	    setContentView(R.layout.statistics);
	    
		this.model = Model.getInstance(this);
		
		this.userPseudo = (TextView) findViewById(R.id.StatisticsPseudo);
		this.userPseudo.setText(this.model.getUser().getPseudo());
		
		this.gamesWon = (TextView) findViewById(R.id.StatisticsGamesWon);
		this.gamesWon.setText(String.valueOf(this.model.getUser().getGameWon()));
		
		this.gamesLost = (TextView) findViewById(R.id.StatisticsGamesLost);
		this.gamesLost.setText(String.valueOf(this.model.getUser().getGameLost()));
		
		this.cancel = (Button) findViewById(R.id.StatisticsButtonCancel);
		this.cancel.setOnClickListener(this);
    }
    
    @Override
	protected void onStop() {
		Log.i("Statistics", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("Statistics", "onDestroy ");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("Statistics", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("Statistics", "onPause ");
		super.onPause();
	}
    
    @Override
	public void onClick(View v) {
    	
    	Intent intent = null;
    	
    	if( v == this.cancel){
			intent = new Intent(Statistics.this, Home.class);
		}
		
		if (intent != null) {
			startActivity(intent);
			finish();
		}
    }

}
