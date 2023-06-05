package com.haku.molar.controller.assistant;

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

import com.haku.molar.R;
import com.haku.molar.controller.assistant.adapter.adaptadorBuscarPacienteLista;
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_buscarPacienteLista;
import com.haku.molar.model.assistant.model_Assistant;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.HashMap;

public class controller_assistant_buscarPacienteLista extends AppCompatActivity implements Callback_assistant_buscarPacienteLista {
    String matricula, nombre, rol,sexo;
    ImageView ivBackBtn, fotoPerfil;
    TextView tvNombre, tvMatricula;
    RecyclerView rvListaPacientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_buscar_paciente_lista);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        ivBackBtn = findViewById(R.id.assistant_buscarPacienteLista_ivBackbtnListarCitasActivas);
        tvNombre = findViewById(R.id.assistant_buscarPacienteLista_detallesCitaNombre);
        tvMatricula = findViewById(R.id.assistant_buscarPacienteLista_detallesCitaMatricula);
        fotoPerfil = findViewById(R.id.assistant_buscarPacienteLista_FotoPerfil);
        rvListaPacientes = findViewById(R.id.assistant_buscarPacienteLista_istarCitasActivasRV);
        inicioUI();

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),controller_assistant_menuPacientes.class);
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
        Intent intent = new Intent(getApplicationContext(),controller_assistant_menuPacientes.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre", nombre);
        intent.putExtra("rol", rol);
        intent.putExtra("sexo", sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void inicioUI(){
        tvNombre.setText(nombre + " | Asistente");
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
        model_Assistant model_assistant = new model_Assistant(this,this);
        model_assistant.listaPacientes();
    }

    @Override
    public void onSuccessLista(ArrayList<model_Assistant> model_assistants) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListaPacientes.setLayoutManager(linearLayoutManager);
        adaptadorBuscarPacienteLista adaptadorBuscarPacienteLista = new adaptadorBuscarPacienteLista(model_assistants, this, new adaptadorBuscarPacienteLista.ItemClickListener() {
            @Override
            public void OnItemClick(model_Assistant details) {
                Intent intent = new Intent(getApplicationContext(),controller_assistant_detallesPaciente.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre", nombre);
                intent.putExtra("rol", rol);
                intent.putExtra("sexo", sexo);
                intent.putExtra("matriculaUser", String.valueOf(details.getMatricula()));
                intent.putExtra("nombreUser", details.getNombre());
                intent.putExtra("apellidoPUser", details.getApellidoPaterno());
                intent.putExtra("apellidoMuser", details.getApellidoMaterno());
                intent.putExtra("emailUser", details.getEmail());
                intent.putExtra("telefonoUser", details.getTelefono());
                intent.putExtra("sexoUser", String.valueOf(details.getSexo()));
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        rvListaPacientes.setAdapter(adaptadorBuscarPacienteLista);
    }

    @Override
    public void onErrorLista(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}