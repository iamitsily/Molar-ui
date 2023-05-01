package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.haku.molar.R;

import java.util.ArrayList;

public class controller_patient_AgendarCitas extends AppCompatActivity {
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_agendarcitas);

        spinner = findViewById(R.id.view_patient_agendarcitas_spinnerMotivo);

        ArrayList<motivo> Motivos = new ArrayList<>();
            Motivos.add(new motivo("Revisión"));
        Motivos.add(new motivo("Caries"));
        Motivos.add(new motivo("Limpieza"));
        Motivos.add(new motivo("Muelas"));
        Motivos.add(new motivo("Molares"));
        Motivos.add(new motivo("Otro"));

        ArrayAdapter<motivo> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Motivos);
        spinner.setAdapter(adapter);

    }
    public void fechaHorabtn(View view){
        Intent intent = new Intent(this, controller_patient_FechaHoraAgendarCita.class);
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