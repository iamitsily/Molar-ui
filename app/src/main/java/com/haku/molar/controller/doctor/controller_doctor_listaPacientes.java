package com.haku.molar.controller.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

public class controller_doctor_listaPacientes extends AppCompatActivity {
    String matricula, nombre, rol,sexo;
    BottomNavigationView menuNav;
    RecyclerView rvListaPacientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_listapacientes);
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_doctor_pacientes);


        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_doctor_home:
                    Intent intentHome = new Intent(this, controller_doctor_menu_doctor.class);
                    intentHome.putExtra("matricula",matricula);
                    intentHome.putExtra("nombre", nombre);
                    intentHome.putExtra("rol", rol);
                    intentHome.putExtra("sexo", sexo);
                    startActivity(intentHome);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_doctor_historial:
                    Intent intentHistorial = new Intent(this, controller_doctor_historial_citas.class);
                    intentHistorial.putExtra("matricula",matricula);
                    intentHistorial.putExtra("nombre", nombre);
                    intentHistorial.putExtra("rol", rol);
                    intentHistorial.putExtra("sexo", sexo);
                    startActivity(intentHistorial);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_doctor_notificaciones:
                    Intent intentNotificaciones = new Intent(this, controller_doctor_notificaciones.class);
                    intentNotificaciones.putExtra("matricula",matricula);
                    intentNotificaciones.putExtra("nombre", nombre);
                    intentNotificaciones.putExtra("rol", rol);
                    intentNotificaciones.putExtra("sexo", sexo);
                    startActivity(intentNotificaciones);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });
    }
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