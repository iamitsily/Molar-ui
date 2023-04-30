package com.haku.molar.controller.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.patient.controller_patient_HistorialCitasLayout;
import com.haku.molar.controller.patient.controller_patient_MenuPaciente;
import com.haku.molar.controller.patient.controller_patient_OpcionesCitas;

public class controller_doctor_notificaciones extends AppCompatActivity {
    BottomNavigationView menuNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_notificaciones);

        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_notificacion);

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_patient_home:
                    startActivity(new Intent(this, controller_doctor_menu_doctor.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_cita:
                    startActivity(new Intent(this, controller_doctor_citas.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_historial:
                    startActivity(new Intent(this, controller_doctor_historial_citas.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;

            }
            return false;
        });
    }
}