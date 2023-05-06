package com.haku.molar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.haku.molar.controller.assistant.controller_assistant_MenuAsistente;
import com.haku.molar.controller.assistant.controller_assistant_registrarPaciente;
import com.haku.molar.controller.doctor.controller_doctor_menu_doctor;
import com.haku.molar.controller.patient.controller_patient_OpcionesCitas;
import com.haku.molar.controller.patient.controller_patient_RegistrarPaciente;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, controller_General_Login.class);
        startActivity(intent);
        finish();
    }
}