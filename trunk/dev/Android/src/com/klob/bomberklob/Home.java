package com.klob.bomberklob;

import java.io.IOException;

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

import com.klob.bomberklob.model.Model;


public class Home extends Activity implements View.OnClickListener{

	private Model model;
	
	private Button singlePlayer;
	private Button multiPlayer;
	private Button options;
	private Button stats;
	private Button createLevel;
	private Button help;
	
	private Spinner accounts;
	private ImageButton addAccount;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.home);
        
        try {
			this.model = Model.getInstance(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		this.singlePlayer = (Button)findViewById(R.id.boutonSolo);        
		this.multiPlayer = (Button)findViewById(R.id.buttonMulti);
		this.options = (Button)findViewById(R.id.buttonOptions);
		this.stats = (Button)findViewById(R.id.buttonStats);
		this.createLevel = (Button)findViewById(R.id.buttonCreerNiveaux);
		this.help = (Button)findViewById(R.id.buttonAide);
        
		this.singlePlayer.setOnClickListener(this);
		this.multiPlayer.setOnClickListener(this);
		this.options.setOnClickListener(this);
		this.stats.setOnClickListener(this);       
		this.createLevel.setOnClickListener(this);
		this.help.setOnClickListener(this);
        
        this.accounts = (Spinner) findViewById( R.id.accounts );
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.model.getSystem().getDatabase().accounts());
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
        
        System.out.println("Langue " +this.getResources().getConfiguration().locale);
        System.out.println("Langue " +this.model.getSystem().getLocalLanguage());
    }
    
    @Override
	protected void onStop() {
		Log.i("Home", "onStop ");
		super.onStop();
	}
	
	@Override
	protected void onDestroy(){
		Log.i("Home", "onDestroy Accueil");
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
		
		System.out.println("Langue " +this.getResources().getConfiguration().locale);
        System.out.println("Langue " +this.model.getSystem().getLocalLanguage());
		
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
		
		if ( null != intent) {
			startActivity(intent);
			this.finish();
		}
	}
}