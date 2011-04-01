package com.klob.bomberklob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.klob.bomberklob.engine.ObjectsGallery;
import com.klob.bomberklob.model.Model;


public class Home extends Activity implements View.OnClickListener{

	private Model model;
	
	private Button singlePlayer;
	private Button multiPlayer;
	private Button options;
	private Button statistics;
	private Button createMap;
	private Button help;
	
	private Spinner accounts;
	private ImageButton addAccount;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.home);

        this.model = Model.getInstance();
		
		this.singlePlayer = (Button)findViewById(R.id.HomeButtonSolo);        
		this.multiPlayer = (Button)findViewById(R.id.HomeButtonMulti);
		this.options = (Button)findViewById(R.id.HomeButtonOptions);
		this.statistics = (Button)findViewById(R.id.HomeButtonStatistics);
		this.createMap = (Button)findViewById(R.id.HomeButtonCreatingLevel);
		this.help = (Button)findViewById(R.id.HomeButtonHelp);
        
		this.singlePlayer.setOnClickListener(this);
		this.multiPlayer.setOnClickListener(this);
		this.options.setOnClickListener(this);
		this.statistics.setOnClickListener(this);       
		this.createMap.setOnClickListener(this);
		this.help.setOnClickListener(this);
        
        this.accounts = (Spinner) findViewById( R.id.accounts );
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.model.getSystem().getDatabase().getAccountsPseudos());
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.accounts.setAdapter(a);
        this.accounts.setSelection(this.model.getSystem().getDatabase().getLastUser()-1);
        this.accounts.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            	model.setUser(accounts.getSelectedItem().toString());
            	model.getSystem().getDatabase().setLastUser(accounts.getSelectedItem().toString());
            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
        });
        
        this.addAccount = (ImageButton) findViewById(R.id.addAccount);
        this.addAccount.setOnClickListener(this);  
    }
    
    @Override
	protected void onStop() {
		Log.i("Home", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("Home", "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onResume(){
		Log.i("Home", "onResume ");
		super.onResume();
	}
	
	@Override
	protected void onPause(){
		Log.i("Home", "onPause ");
		super.onPause();
	}


	@Override
	public void onClick(View v) {
		
		Intent intent = null;
		
		if ( this.addAccount == v ) {
			intent = new Intent(Home.this, NewAccountOffline.class);
		}
		else if ( this.singlePlayer == v ) {
			intent = new Intent(Home.this, SinglePlayerGame.class);
		}
		else if ( this.multiPlayer == v ) {
			intent = new Intent(Home.this, MultiPlayerGame.class);
		}
		else if ( this.options == v ) {
			intent = new Intent(Home.this, Options.class);
		}
		else if ( this.createMap == v ) {
			intent = new Intent(Home.this, CreateMap.class);
		}
		else if ( this.statistics == v ) {
			intent = new Intent(Home.this, Statistics.class);
		}
		else if ( this.help == v ) {
			intent = new Intent(Home.this, Help.class);
		}
		
		if ( null != intent) {
			startActivity(intent);
			this.finish();
		}
	}
}

