package com.haku.molar.controller.assistant;

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

import com.haku.molar.R;
import com.haku.molar.controller.assistant.adapter.adaptadorListarCitasCancelarDirecto;
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_listarCitas;
import com.haku.molar.model.assistant.model_Assistant;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.HashMap;

public class controller_assistant_listarCitas extends AppCompatActivity implements Callback_assistant_listarCitas {
    String matricula, nombre, rol,sexo;
    ImageView ivBackBtn, fotoPerfil;
    TextView tvNombre, tvMatricula;
    RecyclerView rvListaCitas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_listar_citas);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        ivBackBtn = findViewById(R.id.assistant_listaCitas_ivBackbtn);
        tvNombre = findViewById(R.id.assistant_listaCitas_detallesCitaNombre);
        tvMatricula = findViewById(R.id.assistant_listaCitas_detallesCitaMatricula);
        fotoPerfil = findViewById(R.id.assistant_listaCitas_FotoPerfil);
        rvListaCitas = findViewById(R.id.assistant_listaCitas_istarCitasActivasRV);

        inicioUI();

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),controller_assistant_menuCitas.class);
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
        Intent intent = new Intent(getApplicationContext(),controller_assistant_menuCitas.class);
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
        model_cita model_cita = new model_cita(this,this);
        model_cita.listarCitasCancelar();
    }

    @Override
    public void onSuccessListar(ArrayList<model_cita> listaActivas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListaCitas.setLayoutManager(linearLayoutManager);
        adaptadorListarCitasCancelarDirecto adaptadorListarCitasCancelarDirecto = new adaptadorListarCitasCancelarDirecto(listaActivas, this, new adaptadorListarCitasCancelarDirecto.ItemClickListener() {
            @Override
            public void OnItemClick(model_cita details) {
                AlertDialog.Builder builder = new AlertDialog.Builder(controller_assistant_listarCitas.this);
                builder.setTitle("¿Cancelar cita?");
                builder.setMessage("Revise los datos de la cita a cancelar\n\nPaciente: "+details.getNombrePaciente()+" "+details.getApellidoPaciente()+"\n\nId Cita: "+details.getId()+"\n"+"Dia: "+details.getDia()+"\n"+"Hora: "+details.getHora()
                        +"\n"+"Estado: "+details.getEstado()+"\n"+"\nDescripción: "+details.getDescripcion()+"\n"+"\nMotivo de cancelación: "+details.getMotivoCancelar()).setPositiveButton("Confirmar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        model_cita model_cita = new model_cita(details.getId(),details.getEmailPaciente(),controller_assistant_listarCitas.this,controller_assistant_listarCitas.this);
                        model_cita.cancelarCitaDirecto(details.getId(),details.getMotivo(),details.getDia(),details.getIdUusario());
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).show();
            }
        });
        rvListaCitas.setAdapter(adaptadorListarCitasCancelarDirecto);
    }

    @Override
    public void onErrorListar(String mensaje) {
        Toast.makeText(this, "No hay citas por listar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessCancelar() {
        Toast.makeText(this, "Cancelación exitosa", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),controller_assistant_menuCitas.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre", nombre);
        intent.putExtra("rol", rol);
        intent.putExtra("sexo", sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    @Override
    public void onErrorCancelar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}