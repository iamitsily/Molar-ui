package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

public class controller_patient_OpcionesCitas extends AppCompatActivity {
    String matricula,nombre;
    BottomNavigationView menuNav;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_opcionescitas);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_cita);
        view = findViewById(R.id.view_agendarCita_patient_opcionescita);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAgendarCita = new Intent(getApplicationContext(), controller_patient_AgendarCitas.class);
                intentAgendarCita.putExtra("matricula",matricula);
                intentAgendarCita.putExtra("nombre", nombre);
                startActivity(intentAgendarCita);
                finish();
            }
        });
        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_patient_home:
                    startActivity(new Intent(this, controller_patient_MenuPaciente.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_historial:
                    startActivity(new Intent(this, controller_patient_HistorialCitasLayout.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_notificacion:
                    startActivity(new Intent(this, controller_patient_NotificacionesPaciente.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });
    }
}