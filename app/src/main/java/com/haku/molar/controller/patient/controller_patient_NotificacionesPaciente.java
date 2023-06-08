package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.patient.adapter.adaptadorRecyclerNotificaciones;
import com.haku.molar.model.notificaciones.Callback_notificaciones;
import com.haku.molar.model.notificaciones.model_General_Notificaciones;

import java.util.ArrayList;

public class controller_patient_NotificacionesPaciente extends AppCompatActivity implements Callback_notificaciones {
    String matricula, nombre,rol, sexo;

    BottomNavigationView menuNav;

    RecyclerView RV_notis;
    private adaptadorRecyclerNotificaciones adaptadorRecyclerNotificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_notificaciones_paciente);
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");
        menuNav = findViewById(R.id.menu_patient_menu);
        //menuNav.setSelectedItemId(R.id.menu_patient_notificacion);
        RV_notis = findViewById(R.id.RV_Notis);

        model_General_Notificaciones model_General_Notificaciones = new model_General_Notificaciones(matricula,this, this);
        model_General_Notificaciones.listarNotificaciones();

        // Listeners
        menuNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_patient_home) {
                Intent intentHome = new Intent(this, controller_patient_MenuPaciente.class);
                intentHome.putExtra("matricula", matricula);
                intentHome.putExtra("nombre", nombre);
                intentHome.putExtra("rol", rol);
                intentHome.putExtra("sexo",sexo);
                startActivity(intentHome);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            } else if (itemId == R.id.menu_patient_cita) {
                Intent intentCita = new Intent(this, controller_patient_OpcionesCitas.class);
                intentCita.putExtra("matricula", matricula);
                intentCita.putExtra("nombre", nombre);
                intentCita.putExtra("rol", rol);
                intentCita.putExtra("sexo",sexo);
                startActivity(intentCita);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            } else if (itemId == R.id.menu_patient_historial) {
                Intent intentHistorial = new Intent(this, controller_patient_HistorialCitasLayout.class);
                intentHistorial.putExtra("matricula", matricula);
                intentHistorial.putExtra("nombre", nombre);
                intentHistorial.putExtra("rol", rol);
                intentHistorial.putExtra("sexo",sexo);
                startActivity(intentHistorial);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
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

    @Override
    public void onSuccessNotificaciones(ArrayList<model_General_Notificaciones> notificaciones) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RV_notis.setLayoutManager(linearLayoutManager);
        adaptadorRecyclerNotificaciones = new adaptadorRecyclerNotificaciones(notificaciones);
        RV_notis.setAdapter(adaptadorRecyclerNotificaciones);
    }

    @Override
    public void onErrorNotificaciones(String mensajeError) {
        //por si hay algun error
        Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();
    }
}
