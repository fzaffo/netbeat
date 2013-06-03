package com.example.sound;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Impostazioni extends Activity implements OnClickListener {
	
	Intent intentFotoProfilo;
	Intent intentInvitaAmico;
	Intent intentBloccaPersona;
	Intent intentEliminaCronologiaRicerca;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.impostazioni);
		intentFotoProfilo = new Intent(this, FotoProfilo.class);
		intentInvitaAmico = new Intent(this, InvitaAmico.class);
		intentBloccaPersona = new Intent(this, BloccaPersona.class);
		intentEliminaCronologiaRicerca = new Intent(this, EliminaCronologiaRicerca.class);
}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		
		case R.id.ButtonFotoProfilo:
			startActivity(intentFotoProfilo);
			
		case R.id.ButtonInvitaAmico:
			startActivity(intentInvitaAmico);
			
		case R.id.ButtonBloccaPersona:
			startActivity(intentBloccaPersona);
			
		case R.id.ButtonEliminaCronologiaRicerca:
			startActivity(intentEliminaCronologiaRicerca);
			
			break;
		}
	}
}