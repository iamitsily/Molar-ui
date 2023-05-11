package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

public class controller_patient_MenuPaciente extends AppCompatActivity {
    TextView tvNombre;
    String matricula, nombre, rol;

    BottomNavigationView menuNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_menu_paciente);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        tvNombre = (TextView) findViewById(R.id.tvNombre_patient_MenuPaciente);

        inicioUI();

        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_home);

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
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

    public void inicioUI(){
        tvNombre.setText(nombre);
    }
    public void registrar(View view){
        startActivity(new Intent(this, controller_patient_RegistrarPaciente.class));
        finish();
    }
    public void ajustesCuenta(View view){
        Intent intentAjustesCuenta = new Intent(this, controller_patient_AjustesDeCuentaMenu.class);
        intentAjustesCuenta.putExtra("matricula",matricula);
        intentAjustesCuenta.putExtra("nombre", nombre);
        intentAjustesCuenta.putExtra("rol",rol);
        startActivity(intentAjustesCuenta);
        finish();
    }
}