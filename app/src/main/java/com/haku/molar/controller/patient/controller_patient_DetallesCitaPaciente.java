package com.haku.molar.controller.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.haku.molar.R;

public class controller_patient_DetallesCitaPaciente extends AppCompatActivity {
    String matricula, nombre, rol;
    ImageView ivBackBtn;
    TextView tvNombre, tvMatricula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_details_cita_paciente);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");

        ivBackBtn = findViewById(R.id.ivBackBtnDetallesCitaPatient);
        tvNombre = findViewById(R.id.patient_detallesCitaNombre);
        tvMatricula = findViewById(R.id.patient_detallesCitaMatricula);
        inicioUI();
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backbtn();
            }
        });
    }
    public void inicioUI(){
        tvNombre.setText(nombre);
        tvMatricula.setText(matricula);
    }
    public void backbtn(){
        Intent i = new Intent(this,controller_patient_HistorialCitasLayout.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        startActivity(i);
        finish();
    }
}

