package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

public class controller_patient_NotificacionesPaciente extends AppCompatActivity {

    BottomNavigationView menuNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_notificaciones_paciente);

        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_notificacion);

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_patient_home:
                    startActivity(new Intent(this, controller_patient_MenuPaciente.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_cita:
                    startActivity(new Intent(this, controller_patient_OpcionesCitas.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_historial:
                    startActivity(new Intent(this, controller_patient_HistorialCitasLayout.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;

            }
            return false;
        });
    }
}