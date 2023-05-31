package com.haku.molar.controller.assistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_menuAsistente;
import com.haku.molar.controller.patient.controller_patient_HistorialCitasLayout;
import com.haku.molar.controller.patient.controller_patient_NotificacionesPaciente;
import com.haku.molar.controller.patient.controller_patient_OpcionesCitas;
import com.haku.molar.model.patient.model_Patient;

import java.util.HashMap;

public class controller_assistant_MenuAsistente extends AppCompatActivity implements Callback_assistant_menuAsistente {
    TextView tvNombre,tvMatricula,totalUser,totalMedicos;
    CardView cvRegistrarPaciente, cvGestionCitas;
    String matricula, nombre, rol,sexo;
    ImageButton ibPerfil;
    BottomNavigationView menuNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_menu_asistente);
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");


        menuNav = findViewById(R.id.menu_assistant_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_home);
        tvNombre = findViewById(R.id.tv_assistant_nombreAsistente);
        tvMatricula = findViewById(R.id.tv_assistant_matricula);
        cvRegistrarPaciente = findViewById(R.id.assistant_menu_cvRegistrarPaciente);
        cvGestionCitas = findViewById(R.id.assistant_menu_cvGestionarCita);
        ibPerfil = findViewById(R.id.assistant_menu_ibIcon);
        totalUser = findViewById(R.id.assistant_menu_tvUsersTotal);
        totalMedicos = findViewById(R.id.assistant_menu_tvMedTotal);
        inicioUI();

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_assistant_paciente:
                    Intent intent1 = new Intent(this, controller_assistant_menuPacientes.class);
                    intent1.putExtra("matricula",matricula);
                    intent1.putExtra("nombre", nombre);
                    intent1.putExtra("rol", rol);
                    intent1.putExtra("sexo", sexo);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_assistant_citas:
                    Intent intent2 = new Intent(this, controller_assistant_menuCitas.class);
                    intent2.putExtra("matricula",matricula);
                    intent2.putExtra("nombre", nombre);
                    intent2.putExtra("rol", rol);
                    intent2.putExtra("sexo", sexo);
                    startActivity(intent2);
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
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre", nombre);
                intent.putExtra("rol", rol);
                intent.putExtra("sexo", sexo);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        cvGestionCitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),controller_assistant_listarCitasPorCancelar.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre", nombre);
                intent.putExtra("rol", rol);
                intent.putExtra("sexo", sexo);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        ibPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), controller_assistant_ajustesMenu.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre", nombre);
                intent.putExtra("rol", rol);
                intent.putExtra("sexo", sexo);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
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
    public void inicioUI(){
        tvNombre.setText(nombre);
        tvMatricula.setText(matricula+ " | Asistente");
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
        model_patient.contarUserMed();
    }

    @Override
    public void onSuccessContarMenu(String numPacientes, String numMedicos) {
        totalUser.setText(numPacientes);
        if (numMedicos.equals("0")){
            totalMedicos.setText(numMedicos);
            totalMedicos.setTextColor(Color.parseColor("#37A12B"));
        }else{
            totalMedicos.setText(numMedicos);
        }

    }

    @Override
    public void onErrorContrarMenu(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}