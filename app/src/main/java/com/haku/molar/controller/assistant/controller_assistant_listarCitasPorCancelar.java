package com.haku.molar.controller.assistant;

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

import com.haku.molar.R;
import com.haku.molar.controller.assistant.adapter.adaptadorRecyclerListarCitasCancelar;
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_listarCitasPorCancelar;
import com.haku.molar.controller.patient.adapter.adaptadorRecyclerCitasActivas;
import com.haku.molar.controller.patient.controller_patient_CancelarCitaMotivo;
import com.haku.molar.controller.patient.controller_patient_OpcionesCitas;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.HashMap;

public class controller_assistant_listarCitasPorCancelar extends AppCompatActivity implements Callback_assistant_listarCitasPorCancelar {
    String matricula, nombre, rol,sexo;
    RecyclerView RvListCitas;
    ImageView ivBackBtn, fotoPerfil;
    TextView tvNombre, tvMatricula;
    adaptadorRecyclerListarCitasCancelar adaptadorRecyclerCitasActivas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_listar_citas_por_cancelar);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        RvListCitas = findViewById(R.id.assistant_menu_istarCitasActivasRV);
        ivBackBtn = findViewById(R.id.assistant_menu_ivBackbtnListarCitasActivas);
        tvNombre = findViewById(R.id.assistant_menu_detallesCitaNombre);
        tvMatricula = findViewById(R.id.assistant_menu_detallesCitaMatricula);
        fotoPerfil = findViewById(R.id.assistant_menu_FotoPerfil);

        inicioUI();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,controller_assistant_menuCitas.class);
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
        model_cita.listarCitasPorCancelar();
    }

    @Override
    public void onSuccessListar(ArrayList<model_cita> listaActivas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RvListCitas.setLayoutManager(linearLayoutManager);
        adaptadorRecyclerCitasActivas = new adaptadorRecyclerListarCitasCancelar(listaActivas, this, new adaptadorRecyclerListarCitasCancelar.ItemClickListener() {
            @Override
            public void OnItemClick(model_cita details) {
                AlertDialog.Builder builder = new AlertDialog.Builder(controller_assistant_listarCitasPorCancelar.this);
                builder.setTitle("¿Cancelar cita?");
                builder.setMessage("Revise los datos de la cita a cancelar\n\nIdCita: "+details.getId()+"\n"+"\nDia: "+details.getDia()+"\n"+"\nHora: "+details.getHora()
                        +"\n"+"\nEstado: "+details.getEstado()+"\n"+"\nDescripción: "+details.getDescripcion()+"\n"+"\nMotivo de cancelación: "+details.getMotivoCancelar()).setPositiveButton("Confirmar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //model_cita model_cita = new model_cita(idCita,edtMotivo.getText().toString().trim(), controller_patient_CancelarCitaMotivo.this,controller_patient_CancelarCitaMotivo.this);
                        //model_cita.pendienteCancelarCita();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).show();
            }
        });
        RvListCitas.setAdapter(adaptadorRecyclerCitasActivas);
    }
    @Override
    public void onErrorListar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}