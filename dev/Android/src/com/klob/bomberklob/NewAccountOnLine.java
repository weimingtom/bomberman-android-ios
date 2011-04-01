package com.klob.bomberklob;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.klob.bomberklob.model.Model;

public class NewAccountOnLine  extends Activity implements View.OnClickListener{

	private Model model;

	private TextView userPseudo;
	private EditText userAccountName;
	private EditText userAccountPassword1;
	private EditText userAccountPassword2;

	private CheckBox password;
	private CheckBox connectionAuto;

	private Button validate;
	private Button cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.newaccountonline);

		this.model = Model.getInstance(this);
		
		InputFilter filter = new InputFilter() {
		    @Override
			public CharSequence filter(CharSequence source, int start, int end,Spanned dest, int dstart, int dend) { 
		        for (int i = start; i < end; i++) { 
		             if (!Character.isLetterOrDigit(source.charAt(i)) && Character.isSpaceChar(source.charAt(i))) { 
		                 return "";     
		             }     
		        }		       
		        return null;   
		    }  
		};

		this.userPseudo = (TextView) findViewById(R.id.NewAccountOnLineUserName);
		this.userPseudo.setText(this.model.getUser().getPseudo());

		this.userAccountName = (EditText) findViewById(R.id.NewAccountOnLineEditTextName);
		this.userAccountName.setText(this.model.getUser().getUserName());
		this.userAccountName.setFilters(new InputFilter[]{filter});
		
		this.userAccountPassword1 = (EditText) findViewById(R.id.NewAccountOnLineEditTextPassword1);
		this.userAccountPassword1.setFilters(new InputFilter[]{filter});
		
		this.userAccountPassword2 = (EditText) findViewById(R.id.NewAccountOnLineEditTextPassword2);
		this.userAccountPassword2.setFilters(new InputFilter[]{filter});

		this.validate = (Button)findViewById(R.id.NewAccountOnLineButtonConnection);
		this.validate.setOnClickListener(this);

		this.cancel = (Button)findViewById(R.id.NewAccountOnLineButtonCancel);
		this.cancel.setOnClickListener(this);

		this.password = (CheckBox) findViewById(R.id.NewAccountOnLineCheckBoxPassword);
		this.password.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
			@Override 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked) {
					model.getUser().setRemenberPassword(true);
				}
				else {
					model.getUser().setRemenberPassword(false);
				}
				model.getSystem().getDatabase().updateUser(model.getUser());
			}
		});

		if ( this.model.getUser().getRemenberPassword() ) {
			this.password.setChecked(true);
		}

		this.connectionAuto = (CheckBox) findViewById(R.id.NewAccountOnLineCheckBoxConnection);
		this.connectionAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() { 
			@Override 
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { 
				if (isChecked && !userAccountPassword1.getText().toString().equals("") && !userAccountName.getText().toString().equals("")) {
					// FIXME encoder le mdp
					model.getUser().setPassword(userAccountPassword1.getText().toString());
					model.getUser().setUserName(userAccountName.getText().toString());
					model.getUser().setConnectionAuto(true);
				}
				else {
					connectionAuto.setChecked(false);
					model.getUser().setConnectionAuto(false);
					Toast.makeText(NewAccountOnLine.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
				}
				model.getSystem().getDatabase().updateUser(model.getUser());
			}
		});
	}

	@Override
	protected void onStop() {
		Log.i("NewAccountOnLine", "onStop");
		super.onStop();
	}

	@Override
	protected void onDestroy(){
		Log.i("NewAccountOnLine", "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onResume(){
		Log.i("NewAccountOnLine", "onResume");
		super.onResume();
	}

	@Override
	protected void onPause(){
		Log.i("NewAccountOnLine", "onPause");
		super.onPause();
	}


	@Override
	public void onClick(View view) {

		Intent intent = null;
		

		if( view == this.validate ) {
			
			if ( !this.userAccountName.getText().toString().equals("") && !this.userAccountPassword1.getText().toString().equals("")) {
				if ( !this.userAccountPassword1.getText().toString().equals(this.userAccountPassword2.getText().toString()) ) {
					this.model.getUser().setUserName(this.userAccountName.getText().toString());
					
					if (this.model.getUser().getRemenberPassword()) {
						// FIXME Encoder le mdp
						this.model.getUser().setPassword(this.userAccountPassword1.getText().toString());
					}
					
					this.model.getSystem().getDatabase().updateUser(this.model.getUser());
					//FIXME Connexion avec le serveur
				}
				else {
					//FIXME
				}
			}
			else {
				Toast.makeText(NewAccountOnLine.this, R.string.MultiPlayerConnectionErrorAutoConnection , Toast.LENGTH_SHORT).show();
			}
		}
		else if( view == this.cancel ) {
			intent = new Intent(NewAccountOnLine.this, MultiPlayerGame.class);
		}

		if ( intent != null ) {
			startActivity(intent);
			this.finish();
		}
	}
}
