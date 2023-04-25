package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haku.molar.R;

public class controller_patient_MenuPaciente extends AppCompatActivity {
    TextView tvNombre;
    String matricula;
    String nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_menu_paciente);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");

        tvNombre = (TextView) findViewById(R.id.tvNombre_patient_MenuPaciente);

        inicioUI();
    }
    public void inicioUI(){
        tvNombre.setText(nombre);
    }
    public void registrar(View view){
        startActivity(new Intent(this, controller_patient_RegistrarPaciente.class));
    }
}