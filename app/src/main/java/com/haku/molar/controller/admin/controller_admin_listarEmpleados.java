package com.haku.molar.controller.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.admin.adapter.adaptador_admin_listaEmpleados;
import com.haku.molar.controller.admin.interfaces.Callback_admin_listaEmpleados;
import com.haku.molar.controller.assistant.controller_assistant_menuPacientes;
import com.haku.molar.model.admin.model_Admin;
import com.haku.molar.model.assistant.model_Assistant;

import java.util.ArrayList;
import java.util.HashMap;

public class controller_admin_listarEmpleados extends AppCompatActivity implements Callback_admin_listaEmpleados {
    String matricula, nombre, rol, sexo;
    ImageView ivBackBtn, fotoPerfil;
    TextView tvNombre, tvMatricula;
    RecyclerView rvListaCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_admin_listar_empleados);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        ivBackBtn = findViewById(R.id.admin_listaEmpleados_back);
        tvNombre = findViewById(R.id.admin_listaEmpleados_nombre);
        tvMatricula = findViewById(R.id.admin_listaEmpleados_matricula);
        fotoPerfil = findViewById(R.id.admin_listaEmpleados_foto);
        rvListaCitas = findViewById(R.id.admin_listaEmpleados_rvLista);

        inicioUI();

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), controller_admin_menuEmpleados.class);
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
        Intent intent = new Intent(getApplicationContext(),controller_admin_menuEmpleados.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre", nombre);
        intent.putExtra("rol", rol);
        intent.putExtra("sexo", sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void inicioUI(){
        tvNombre.setText(nombre + " | Admin");
        tvMatricula.setText(matricula);

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
            fotoPerfil.setScaleType(opcionSexo.second);
            fotoPerfil.setImageResource(opcionSexo.first);
        }
        model_Admin model_admin = new model_Admin(this,this);
        model_admin.listarEmpleados();
    }

    @Override
    public void onSuccessLista(ArrayList<model_Admin> model_assistants) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListaCitas.setLayoutManager(linearLayoutManager);
        adaptador_admin_listaEmpleados adaptador_admin_listaEmpleados = new adaptador_admin_listaEmpleados(model_assistants, this, new adaptador_admin_listaEmpleados.ItemClickListener() {
            @Override
            public void OnItemClick(model_Admin details) {

            }
        });
        rvListaCitas.setAdapter(adaptador_admin_listaEmpleados);
    }

    @Override
    public void onErrorLista(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}