package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haku.molar.R;

public class controller_patient_AvisoCitaReagendada extends AppCompatActivity {
    String matricula="", nombre="", rol="",sexo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_aviso_cita_reagendada);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");
    }
    public void backBtn(View view){
        Intent i = new Intent(this,controller_patient_OpcionesCitas.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("sexo",sexo);
        i.putExtra("opcion","0");
        startActivity(i);
        finish();
    }
}