package com.example.sound;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registrati extends Activity implements OnClickListener{
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrati);
		Button registrati = (Button) findViewById(R.id.registrazione);
		registrati.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		boolean presente = false;
		Toast toastPasswordDiverse = Toast.makeText(this, "Le password non corrispondono", Toast.LENGTH_LONG);
		Toast toastUsernamePresente = Toast.makeText(this, "Username già presente", Toast.LENGTH_LONG);
		Toast toastRegistrazioneEffettuata = Toast.makeText(this, "Registrazione effettuata con successo", Toast.LENGTH_SHORT);
		EditText editUser = (EditText) findViewById(R.id.userInserito);
		EditText editPass1 = (EditText) findViewById(R.id.passInserita1);
		EditText editPass2 = (EditText) findViewById(R.id.passInserita2);
		String username = editUser.getText().toString();
		String password1 = editPass1.getText().toString();
		String password2 = editPass2.getText().toString();
	}

}
