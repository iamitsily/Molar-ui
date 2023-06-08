package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

public class controller_patient_OpcionesCitas extends AppCompatActivity {
    String matricula,nombre,rol,sexo;
    BottomNavigationView menuNav;
    View viewAgendarCita, viewReagendarCita, viewCancelarCita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_opcionescitas);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");
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
                intentAgendarCita.putExtra("rol", rol);
                intentAgendarCita.putExtra("sexo", sexo);
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
                intentAgendarCita.putExtra("rol", rol);
                intentAgendarCita.putExtra("sexo", sexo);
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
                intentAgendarCita.putExtra("rol", rol);
                intentAgendarCita.putExtra("sexo", sexo);
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
                    intentHome.putExtra("rol", rol);
                    intentHome.putExtra("sexo", sexo);
                    startActivity(intentHome);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_historial:
                    Intent intentHistorial = new Intent(this, controller_patient_HistorialCitasLayout.class);
                    intentHistorial.putExtra("matricula",matricula);
                    intentHistorial.putExtra("nombre", nombre);
                    intentHistorial.putExtra("rol", rol);
                    intentHistorial.putExtra("sexo", sexo);
                    startActivity(intentHistorial);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                    /*
                case R.id.menu_patient_notificacion:
                    Intent intentNotificacion = new Intent(this, controller_patient_NotificacionesPaciente.class);
                    intentNotificacion.putExtra("matricula",matricula);
                    intentNotificacion.putExtra("nombre", nombre);
                    intentNotificacion.putExtra("rol", rol);
                    intentNotificacion.putExtra("sexo", sexo);
                    startActivity(intentNotificacion);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;

                     */
            }
            return false;
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Molar");
        builder.setMessage("¿Desea salir de la aplicación?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(false).show();
    }
}