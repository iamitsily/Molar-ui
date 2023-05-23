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
    View viewAgendarCita, viewReagendarCita, viewCancelarCita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_opcionescitas);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_cita);
        viewAgendarCita = findViewById(R.id.view_agendarCita_patient_opcionescita);
        viewReagendarCita = findViewById(R.id.view_reagendarCita_patient_opcionescita);
        viewCancelarCita = findViewById(R.id.view_CancelarCita_patient_opcionescita);
        //Listeners
        viewAgendarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAgendarCita = new Intent(getApplicationContext(), controller_patient_AgendarCitas.class);
                intentAgendarCita.putExtra("matricula",matricula);
                intentAgendarCita.putExtra("nombre", nombre);
                startActivity(intentAgendarCita);
                finish();
            }
        });
        viewReagendarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAgendarCita = new Intent(getApplicationContext(), controller_patient_listarCitasActivas.class);
                intentAgendarCita.putExtra("matricula",matricula);
                intentAgendarCita.putExtra("nombre", nombre);
                intentAgendarCita.putExtra("opcion", "0");
                startActivity(intentAgendarCita);
                finish();
            }
        });
        viewCancelarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAgendarCita = new Intent(getApplicationContext(), controller_patient_listarCitasActivas.class);
                intentAgendarCita.putExtra("matricula",matricula);
                intentAgendarCita.putExtra("nombre", nombre);
                intentAgendarCita.putExtra("opcion", "1");
                startActivity(intentAgendarCita);
                finish();
            }
        });
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
                case R.id.menu_patient_historial:
                    Intent intentHistorial = new Intent(this, controller_patient_HistorialCitasLayout.class);
                    intentHistorial.putExtra("matricula",matricula);
                    intentHistorial.putExtra("nombre", nombre);
                    startActivity(intentHistorial);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_notificacion:
                    Intent intentNotificacion = new Intent(this, controller_patient_NotificacionesPaciente.class);
                    intentNotificacion.putExtra("matricula",matricula);
                    intentNotificacion.putExtra("nombre", nombre);
                    startActivity(intentNotificacion);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });
    }
}