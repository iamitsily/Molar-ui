package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.haku.molar.R;

import java.util.ArrayList;

public class controller_patient_AgendarCitas extends AppCompatActivity {
    Spinner spinner;
    String matricula,nombre;
    EditText descripcion;
    ImageView IVBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_agendarcitas);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");

        spinner = findViewById(R.id.view_patient_agendarcitas_spinnerMotivo);
        descripcion = findViewById(R.id.view_patient_agendarcitas_descripcion);
        IVBtnBack = findViewById(R.id.AC1_iv1);

        ArrayList<motivo> Motivos = new ArrayList<>();
            Motivos.add(new motivo("Revision"));
        Motivos.add(new motivo("Caries"));
        Motivos.add(new motivo("Limpieza"));
        Motivos.add(new motivo("Muelas"));
        Motivos.add(new motivo("Molares"));
        Motivos.add(new motivo("Otro"));

        ArrayAdapter<motivo> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Motivos);
        spinner.setAdapter(adapter);

        IVBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backMenubtn();
            }
        });
    }
    public void fechaHorabtn(View view){

        Intent intentFechaHora = new Intent(getApplicationContext(), controller_patient_FechaHoraAgendarCita.class);
        intentFechaHora.putExtra("matricula",matricula);
        intentFechaHora.putExtra("nombre", nombre);
        intentFechaHora.putExtra("motivo", spinner.getSelectedItem().toString());
        intentFechaHora.putExtra("descripcion", descripcion.getText().toString().trim());
        startActivity(intentFechaHora);
        finish();
    }
    public void backMenubtn(){
        Intent intent = new Intent(this, controller_patient_OpcionesCitas.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        startActivity(intent);
        finish();
    }
    public class motivo{
        private String motivo;
        public motivo(String motivo) {
            this.motivo = motivo;
        }
        public String getMotivo() {
            return motivo;
        }
        public void setMotivo(String motivo) {
            this.motivo = motivo;
        }
        @Override
        public String toString() {
            return this.motivo;
        }
    }
}