package com.haku.molar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.haku.molar.controller.patient.controller_patient_RegistrarPaciente;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, controller_patient_RegistrarPaciente.class);
        startActivity(intent);
        finish();
    }
}