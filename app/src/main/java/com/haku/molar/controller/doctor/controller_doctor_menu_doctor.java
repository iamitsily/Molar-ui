package com.haku.molar.controller.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.doctor.adapter.adaptadorrMenuDoctor;
import com.haku.molar.controller.doctor.interfaces.Callback_doctor_menuDoctor;
import com.haku.molar.model.cita.model_cita;
import com.haku.molar.model.patient.model_Patient;

import java.util.ArrayList;
import java.util.HashMap;

public class controller_doctor_menu_doctor extends AppCompatActivity implements Callback_doctor_menuDoctor {
    TextView tvNombre, tvMatricula;
    ImageView ibPerfil;
    String matricula, nombre, rol,sexo;
    BottomNavigationView menuNav;
    RecyclerView rvLista;
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
        rvLista = findViewById(R.id.rvDoctorMenuDoctor);

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
                    /*
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
*/
            }
            return false;
        });
        ibPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAjustes = new Intent(getApplicationContext(), controller_doctor_ajustesMenu.class);
                intentAjustes.putExtra("matricula",matricula);
                intentAjustes.putExtra("nombre", nombre);
                intentAjustes.putExtra("rol", rol);
                intentAjustes.putExtra("sexo", sexo);
                startActivity(intentAjustes);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
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
        model_Patient model_patient = new model_Patient(this,this);
        model_patient.listarCitasMenuDoctor(matricula);
    }

    @Override
    public void onSuccesListar(ArrayList<model_cita> listaCitas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvLista.setLayoutManager(linearLayoutManager);
        adaptadorrMenuDoctor adaptadorrMenuDoctor = new adaptadorrMenuDoctor(listaCitas, this, new adaptadorrMenuDoctor.ItemClickListener() {
            @Override
            public void OnItemClick(model_cita details) {
                Toast.makeText(controller_doctor_menu_doctor.this, details.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        rvLista.setAdapter(adaptadorrMenuDoctor);
    }

    @Override
    public void onErrorListar(String mensaje) {

    }
}