package com.klob.Bomberklob.menus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
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
	private AudioManager mAudioManager;

	private Spinner languages;

	private String[] languagesTab;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);

		this.managementProfile = (Button) findViewById(R.id.OptionsButtonProfil);
		this.managementProfile.setOnClickListener(this);

		this.cancel = (Button) findViewById(R.id.OptionsButtonCancel);
		this.cancel.setOnClickListener(this);   

		this.soundVolume = (SeekBar) findViewById(R.id.OptionsSeekBarVolume);
//		this.soundVolume.setMax(100);
		soundVolume.setOnSeekBarChangeListener(OnSeekBarProgress);
		
		this.mute = (CheckBox) findViewById(R.id.OptionsCheckBoxVolume);
		this.mute.setOnClickListener(this);
	        
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		/* initialisation de la barre de menu en fonction de la valeur de la bd */
		if(Model.getSystem().getVolume() == 0){
        	mute.setChecked(true);
        	soundVolume.setEnabled(false);
        	soundVolume.setProgress(0);
        	mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
        }
        else{
        	mute.setChecked(false);
        	soundVolume.setEnabled(true);
        	soundVolume.setProgress(Model.getSystem().getVolume());
        	mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Model.getSystem().getVolume()/6, AudioManager.FLAG_SHOW_UI);
        }

		this.languagesTab = getResources().getStringArray(R.array.Languages);
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
		
		/* 
		 * écouteur du Check mute 
		 */
		this.mute.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
                    if (isChecked) {
                    	soundVolume.setProgress(0);
        				soundVolume.setEnabled(false);
        				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
                    }
                    else {
                    	soundVolume.setProgress(Model.getSystem().getVolume());
        				soundVolume.setEnabled(true);
        				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 1, AudioManager.FLAG_SHOW_UI);
                    }
            }
		});
	}
	
	/* 
	 * écouteur de changement de valeur de la SeekBar 
	 */
	OnSeekBarChangeListener OnSeekBarProgress =
    	new OnSeekBarChangeListener() {

	    	public void onProgressChanged(SeekBar s, int progress, boolean touch){
		    	if(touch){
		    		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress/6, AudioManager.FLAG_SHOW_UI);
		    	}
	    	}
	    	public void onStartTrackingTouch(SeekBar s){
	    	}
	    	public void onStopTrackingTouch(SeekBar s){
	
	    	}
    	};
	
	/* 
	 * les boutons up and down du son
	 * TODO a perfectionner notamment avec le mute 
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(!mute.isChecked()){
			switch( keyCode ){
	    		case 24:
	    				this.mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
	    				soundVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)-7);
	    				return true;
	    				
	    		case 25:
	    				this.mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
	    				soundVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)-7);
	    				return true;
	    			
	    		default:
	    			break;
			}
		}
		return false;
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event){
		if(!mute.isChecked()){
			switch( keyCode ){
	    		case 24:
	    			Log.i("","24 up");
	    			this.mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
	    			soundVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)*7);
	    			return true;
	    		
	    		case 25:
	    			Log.i("","25 up");
	    			this.mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
	    			soundVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC)*7);
	    			return true;
	    			
	    		default:
	    			break;
			}
		}
			return false;
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
				intent = new Intent(context, ProfileManager.class);
			}
			else if( v == cancel)	{
				Model.getSystem().setVolume(soundVolume.getProgress());
	    		Model.getSystem().getDatabase().setVolume(soundVolume.getProgress());
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

