package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

public class controller_patient_NotificacionesPaciente extends AppCompatActivity {
    String matricula,nombre;

    BottomNavigationView menuNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_notificaciones_paciente);
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_notificacion);

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_patient_home:
                    Intent intentHome = new Intent(this, controller_patient_MenuPaciente.class);
                    intentHome.putExtra("matricula",matricula);
                    intentHome.putExtra("nombre", nombre);
                    startActivity(intentHome);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_cita:
                    Intent intentCita = new Intent(this, controller_patient_OpcionesCitas.class);
                    intentCita.putExtra("matricula",matricula);
                    intentCita.putExtra("nombre", nombre);
                    startActivity(intentCita);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_historial:
                    Intent intentHistorial = new Intent(this, controller_patient_HistorialCitasLayout.class);
                    intentHistorial.putExtra("matricula",matricula);
                    intentHistorial.putExtra("nombre", nombre);
                    startActivity(intentHistorial);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();

            }
            return false;
        });
    }
}