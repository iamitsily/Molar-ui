package com.haku.molar.controller.assistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.patient.controller_patient_HistorialCitasLayout;
import com.haku.molar.controller.patient.controller_patient_NotificacionesPaciente;
import com.haku.molar.controller.patient.controller_patient_OpcionesCitas;

public class controller_assistant_MenuAsistente extends AppCompatActivity {
    TextView tvNombre;
    CardView cvRegistrarPaciente, cvGestionCitas;
    String matricula, nombre, rol;
    BottomNavigationView menuNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_menu_asistente);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");


        menuNav = findViewById(R.id.menu_assistant_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_home);
        tvNombre = (TextView) findViewById(R.id.tv_assistant_nombreAsistente);
        cvRegistrarPaciente = findViewById(R.id.assistant_menu_cvRegistrarPaciente);
        cvGestionCitas = findViewById(R.id.assistant_menu_cvGestionarCita);
        inicioUI();

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_assistant_paciente:
                    startActivity(new Intent(this, controller_assistant_menuPacientes.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_assistant_citas:
                    startActivity(new Intent(this, controller_assistant_menuCitas.class));
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });
        cvRegistrarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),controller_assistant_registrarPaciente.class);
                startActivity(intent);
                finish();
            }
        });
        cvGestionCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void inicioUI(){
        tvNombre.setText(nombre);
    }
}