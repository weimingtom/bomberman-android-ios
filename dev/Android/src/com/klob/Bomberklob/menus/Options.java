package com.klob.Bomberklob.menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;

import com.klob.Bomberklob.R;
import com.klob.Bomberklob.model.Model;

public class Options extends Activity implements View.OnClickListener{

	private Button cancel;
	private Button managementProfile;

	private SeekBar soundVolume;
	private CheckBox mute;

	private Spinner languages;

	private String[] languagesTab;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.options);

		this.managementProfile = (Button) findViewById(R.id.OptionsButtonProfil);
		this.managementProfile.setOnClickListener(this);

		this.cancel = (Button) findViewById(R.id.OptionsButtonCancel);
		this.cancel.setOnClickListener(this);   

		this.soundVolume = (SeekBar) findViewById(R.id.OptionsSeekBarVolume);
		this.soundVolume.setMax(100);
		this.soundVolume.setProgress(Model.getSystem().getVolume());
		this.soundVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {


			public void onStopTrackingTouch(SeekBar arg0) {}

	
			public void onStartTrackingTouch(SeekBar arg0) {}


			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				Model.getSystem().setVolume(arg1);
				Model.getSystem().getDatabase().setVolume(arg1);
			}
		});

		this.languagesTab = getResources().getStringArray(R.array.languages);

		this.languages = (Spinner) findViewById( R.id.OptionsSpinnerLanguages );
		ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.languagesTab);
		a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.languages.setAdapter(a);
		this.languages.setOnItemSelectedListener(new OnItemSelectedListener() {


			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Model.getSystem().setLanguage(languages.getSelectedItem().toString());
				Model.getSystem().getDatabase().setLanguage(languages.getSelectedItem().toString());
			}


			public void onNothingSelected(AdapterView<?> arg0) {}
		});

		for (int i = 0 ; i < this.languagesTab.length ; i++ ) {
			if (this.languages.getItemAtPosition(i).toString().equals(Model.getSystem().getLanguage())) {
				this.languages.setSelection(i);
				break;
			}
		}

		this.mute = (CheckBox) findViewById(R.id.OptionsCheckBoxVolume);
		this.mute.setOnCheckedChangeListener(new OnCheckedChangeListener() { 

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked) {
					//FIXME On fait quoi ???
				}
				else {

				}
			}
		});
	}

	@Override
	protected void onStop() {
		Log.i("Options", "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy(){
		Log.i("Options", "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onResume(){
		Log.i("Options", "onResume");
		super.onResume();
	}

	@Override
	protected void onPause(){
		Log.i("Options", "onPause");
		super.onPause();
	}

	public void onClick(View v) {

		Intent intent = null;

		Resources res = this.getResources();
		Configuration conf = res.getConfiguration();
		conf.locale = Model.getSystem().getLocalLanguage();
		res.updateConfiguration(conf, res.getDisplayMetrics());

		try {
			Context context = this.createPackageContext(getPackageName(), Context.CONTEXT_INCLUDE_CODE);

			if(v == managementProfile){
				intent = new Intent(context, ProfilManagement.class);
			}
			else if( v == cancel)	{
				intent = new Intent(context, Home.class);
			}
		}
		catch (NameNotFoundException e) {
			e.printStackTrace();
		}			

		if ( intent != null ) {
			this.startActivity(intent);
			this.finish();
		}
	}
}

