package com.haku.molar.controller.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;

import java.util.HashMap;

public class controller_doctor_menu_doctor extends AppCompatActivity {
    TextView tvNombre, tvMatricula;
    ImageView ibPerfil;
    String matricula, nombre, rol,sexo;
    BottomNavigationView menuNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_menu_doctor);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        tvNombre = findViewById(R.id.tvNombre_doctor_MenuDoctor);
        tvMatricula = findViewById(R.id.tvMatricula_doctor_MenuDoctor);
        ibPerfil = findViewById(R.id.doctor_menu_ibIcon);

        inicioUI();

        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_doctor_home);

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_doctor_pacientes:
                    Intent intentPaciente = new Intent(this, controller_doctor_listaPacientes.class);
                    intentPaciente.putExtra("matricula",matricula);
                    intentPaciente.putExtra("nombre", nombre);
                    intentPaciente.putExtra("rol", rol);
                    intentPaciente.putExtra("sexo", sexo);
                    startActivity(intentPaciente);
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
    public void inicioUI(){
        tvNombre.setText("Dr. "+nombre);
        tvMatricula.setText(matricula+ " | Doctor");

        HashMap<String, Pair<Integer, ImageView.ScaleType>> mapaSexo = new HashMap<>();
        mapaSexo.put("0", new Pair<>(R.mipmap.hombre, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("1", new Pair<>(R.mipmap.mujer, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("12", new Pair<>(R.mipmap.hombredos, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("13", new Pair<>(R.mipmap.hombretres, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("14", new Pair<>(R.mipmap.hombrecuatro, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("15", new Pair<>(R.mipmap.hombrecinco, ImageView.ScaleType.CENTER_CROP));
        mapaSexo.put("22", new Pair<>(R.mipmap.mujerdos, ImageView.ScaleType.FIT_CENTER));
        mapaSexo.put("23", new Pair<>(R.mipmap.mujertres, ImageView.ScaleType.FIT_CENTER));
        mapaSexo.put("24", new Pair<>(R.mipmap.mujercuatro, ImageView.ScaleType.FIT_CENTER));
        mapaSexo.put("25", new Pair<>(R.mipmap.mujercinco, ImageView.ScaleType.FIT_CENTER));

        Pair<Integer, ImageView.ScaleType> opcionSexo = mapaSexo.get(sexo);

        if (opcionSexo == null) {
            Toast.makeText(this, "Elija una opción válida", Toast.LENGTH_SHORT).show();
        } else {
            ibPerfil.setScaleType(opcionSexo.second);
            ibPerfil.setImageResource(opcionSexo.first);
        }
    }
}