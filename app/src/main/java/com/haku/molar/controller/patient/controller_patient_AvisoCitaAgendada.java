package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haku.molar.R;

public class controller_patient_AvisoCitaAgendada extends AppCompatActivity {
    String matricula, nombre, rol,sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_avisocitaagendada);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");
    }
    public void terminarMenu(View view){
        Intent intent = new Intent(this, controller_patient_MenuPaciente.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        intent.putExtra("rol",rol);
        intent.putExtra("sexo",sexo);
        startActivity(intent);
        finish();
    }
}