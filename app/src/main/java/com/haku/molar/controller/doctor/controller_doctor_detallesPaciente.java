package com.haku.molar.controller.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.assistant.controller_assistant_buscarPacienteLista;

import java.util.HashMap;

public class controller_doctor_detallesPaciente extends AppCompatActivity {
    String matricula, nombre, rol,sexo, matriculaUser, nombreUser, apellidoPUser, apellidoMuser, emailUser, sexoUser;
    ImageView ivPerfil, ivBackbtn;
    TextView tvnombre, tvmatricula, tvnombreCompleto, tvsexo, tvemail;
    Button btnHistorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_detalles_paciente);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        matriculaUser = intent.getStringExtra("matriculaUser");
        nombreUser = intent.getStringExtra("nombreUser");
        apellidoPUser = intent.getStringExtra("apellidoPUser");
        apellidoMuser = intent.getStringExtra("apellidoMuser");
        emailUser = intent.getStringExtra("emailUser");
        sexoUser = intent.getStringExtra("sexoUser");

        ivPerfil = findViewById(R.id.doctor_detallesPaciente_Foto);
        tvnombre = findViewById(R.id.doctor_detallesPaciente_nombre);
        tvmatricula = findViewById(R.id.doctor_detallesPaciente_matricula);
        tvnombreCompleto = findViewById(R.id.doctor_detallesPaciente_nombreCompleto);
        tvsexo = findViewById(R.id.doctor_detallesPaciente_sexo);
        tvemail = findViewById(R.id.doctor_detallesPaciente_email);
        ivBackbtn = findViewById(R.id.doctor_detallesPaciente_back);
        btnHistorial = findViewById(R.id.assistant_menu_ajustesCuentaGuardarBtn2);

        inicioUI();

        ivBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),controller_doctor_listaPacientes.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre", nombre);
                intent.putExtra("rol", rol);
                intent.putExtra("sexo", sexo);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),controller_doctor_citasDePaciente.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre", nombre);
                intent.putExtra("rol", rol);
                intent.putExtra("sexo", sexo);
                intent.putExtra("matriculaUser", matriculaUser);
                intent.putExtra("nombreUser", nombreUser);
                intent.putExtra("sexoUser", sexoUser);

                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), controller_doctor_listaPacientes.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre", nombre);
        intent.putExtra("rol", rol);
        intent.putExtra("sexo", sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void inicioUI(){
        tvnombre.setText(nombreUser);
        tvmatricula.setText(matriculaUser);
        tvnombreCompleto.setText("Nombre completo: \n"+nombreUser +" "+ apellidoPUser +" "+ apellidoMuser);
        tvemail.setText("Email: "+emailUser);
        if (sexoUser.equals("0") || sexoUser.equals("12") || sexoUser.equals("13") || sexoUser.equals("14") || sexoUser.equals("15")){
            tvsexo.setText("Sexo: Masculino");
        }else{
            tvsexo.setText("Sexo: Femenino");
        }
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

        Pair<Integer, ImageView.ScaleType> opcionSexo = mapaSexo.get(sexoUser);

        if (opcionSexo == null) {
            Toast.makeText(this, "Elija una opción válida", Toast.LENGTH_SHORT).show();
        } else {
            ivPerfil.setScaleType(opcionSexo.second);
            ivPerfil.setImageResource(opcionSexo.first);
        }
    }
}