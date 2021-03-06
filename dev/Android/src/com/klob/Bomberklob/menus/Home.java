package com.klob.Bomberklob.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;

public class Home extends Activity implements View.OnClickListener{

	private Button singlePlayer;
	private Button multiPlayer;
	private Button options;
	private Button createMap;
	private Button help;

	private Spinner accounts;
	private ImageButton addAccount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.home);

		this.singlePlayer = (Button)findViewById(R.id.HomeButtonSolo);        
		this.multiPlayer = (Button)findViewById(R.id.HomeButtonMulti);
		this.options = (Button)findViewById(R.id.HomeButtonOptions);
		this.createMap = (Button)findViewById(R.id.HomeButtonCreatingLevel);
		this.help = (Button)findViewById(R.id.HomeButtonHelp);

		this.singlePlayer.setOnClickListener(this);
		this.multiPlayer.setOnClickListener(this);
		this.options.setOnClickListener(this);
		this.createMap.setOnClickListener(this);
		this.help.setOnClickListener(this);

		this.accounts = (Spinner) findViewById( R.id.accounts );
		ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Model.getSystem().getDatabase().getAccountsPseudos());
		a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.accounts.setAdapter(a);
		this.accounts.setSelection(Model.getSystem().getLastUser()-1);
		this.accounts.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if ( !accounts.getSelectedItem().toString().equals(Model.getUser().getPseudo()) ) {
					Model.setUser(accounts.getSelectedItem().toString());
					Model.getSystem().getDatabase().setLastUser(accounts.getSelectedItem().toString());
				}
			}

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


	public void onClick(View v) {

		Intent intent = null;

		if ( this.addAccount == v ) {
			intent = new Intent(Home.this, NewAccountOffline.class);
		}
		else if ( this.multiPlayer == v ) {
			intent = new Intent(Home.this, Multiplayer.class);
		}
		else if ( this.options == v ) {
			intent = new Intent(Home.this, Options.class);
		}
		else if ( this.createMap == v ) {
			intent = new Intent(Home.this, Editor.class);
		}
		else if ( this.help == v ) {
			intent = new Intent(Home.this, Help.class);
		}
		else  if ( this.singlePlayer == v ) {
			if ( Model.getSystem().getDatabase().getMaps().size() > 0 ) {
				intent = new Intent(Home.this, SinglePlayer.class);
			}
			else {
				Toast.makeText(Home.this, R.string.ErrorNoMapFound, Toast.LENGTH_SHORT).show();
			}
		}

		if ( null != intent) {
			startActivity(intent);
			this.finish();
		}
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if ( event.getKeyCode() == KeyEvent.KEYCODE_HOME ) {
			
		}
		else if ( event.getKeyCode() == KeyEvent.KEYCODE_MENU ) {
			this.finish();
		}
		else if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
			
		}
		
		return false;
		
	}
	
}
