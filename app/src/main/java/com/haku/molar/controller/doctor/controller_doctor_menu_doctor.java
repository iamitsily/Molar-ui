package com.haku.molar.controller.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

public class controller_doctor_menu_doctor extends AppCompatActivity {
    TextView tvNombre;
    String matricula;
    String nombre;
    BottomNavigationView menuNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_menu_doctor);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");

        tvNombre = (TextView) findViewById(R.id.tvNombre_doctor_MenuDoctor);

        inicioUI();

        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_doctor_home);

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_doctor_pacientes:
                    startActivity(new Intent(this, controller_doctor_listaPacientes.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_doctor_historial:
                    startActivity(new Intent(this, controller_doctor_historial_citas.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_doctor_notificaciones:
                    startActivity(new Intent(this, controller_doctor_notificaciones.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;

            }
            return false;
        });
    }
    public void inicioUI(){
        tvNombre.setText("Dr. "+nombre);
    }
}