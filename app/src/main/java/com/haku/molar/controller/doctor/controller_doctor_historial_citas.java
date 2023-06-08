package com.haku.molar.controller.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.haku.molar.controller.doctor.adapter.adaptadorHistorialCitas;
import com.haku.molar.controller.doctor.interfaces.Callback_doctor_historialCitas;
import com.haku.molar.controller.patient.controller_patient_DetallesCitaPaciente;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.HashMap;

public class controller_doctor_historial_citas extends AppCompatActivity implements Callback_doctor_historialCitas {
    String matricula, nombre, rol,sexo;
    BottomNavigationView menuNav;
    RecyclerView rvListaCitas;
    TextView tvNombre, tvMatricula;
    ImageView ivPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_historial_citas);
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");


        tvNombre = findViewById(R.id.doctor_historialCitas_detallesCitaNombre);
        tvMatricula = findViewById(R.id.doctor_historialCitas_detallesCitaMatricula);
        ivPerfil = findViewById(R.id.doctor_historialCitas_FotoPerfil);
        rvListaCitas = findViewById(R.id.doctor_listaCitasRV);

        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_doctor_historial);

        inicioUI();

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
                    break;*/
            }
            return false;
        });
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
            ivPerfil.setScaleType(opcionSexo.second);
            ivPerfil.setImageResource(opcionSexo.first);
        }
        model_cita model_cita = new model_cita(matricula,this,this);
        model_cita.historialCitas();
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

    @Override
    public void onSuccessHistorial(ArrayList<model_cita> listaCitas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListaCitas.setLayoutManager(linearLayoutManager);
        adaptadorHistorialCitas adaptadorHistorialCitas = new adaptadorHistorialCitas(listaCitas, this, new adaptadorHistorialCitas.ItemClickListenerHistorialCitas() {
            @Override
            public void OnItemClick(model_cita details) {
                Intent intent = new Intent(getApplicationContext(), controller_doctor_detalles_cita_paciente.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre",nombre);
                intent.putExtra("rol",rol);
                intent.putExtra("idCita",details.getId());
                intent.putExtra("sexo", sexo);
                intent.putExtra("estado",details.getEstado());
                startActivity(intent);
                finish();
            }
        });
        rvListaCitas.setAdapter(adaptadorHistorialCitas);
    }
    @Override
    public void onErrorhoraHistorial(String mensaje) {

    }
}