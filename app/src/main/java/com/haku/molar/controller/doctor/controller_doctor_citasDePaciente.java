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

import com.haku.molar.R;
import com.haku.molar.controller.assistant.controller_assistant_listarCitasPorCancelar;
import com.haku.molar.controller.assistant.controller_assistant_menuCitas;
import com.haku.molar.controller.patient.adapter.adaptadorRecyclerHistorialCitas;
import com.haku.molar.controller.patient.controller_patient_DetallesCitaPaciente;
import com.haku.molar.controller.patient.interfaces.Callback_patient_historialCitas;
import com.haku.molar.model.cita.model_cita;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;

public class controller_doctor_citasDePaciente extends AppCompatActivity implements Callback_patient_historialCitas {
    String matricula, nombre, rol,sexo, nombreUser, matriculaUser, sexoUser;
    ImageView ivBackBtn, fotoPerfil;
    TextView tvNombre, tvMatricula;
    RecyclerView rvListaCitas;
    private com.haku.molar.controller.patient.adapter.adaptadorRecyclerHistorialCitas adaptadorRecyclerHistorialCitas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_citas_de_paciente);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        nombreUser = intent.getStringExtra("nombreUser");
        matriculaUser = intent.getStringExtra("matriculaUser");
        sexoUser = intent.getStringExtra("sexoUser");

        ivBackBtn = findViewById(R.id.doctor_citasDePaciente_back);
        tvNombre = findViewById(R.id.doctor_citasDePaciente_nombre);
        tvMatricula = findViewById(R.id.doctor_citasDePaciente_matricula);
        fotoPerfil = findViewById(R.id.doctor_citasDePaciente_perfil);
        rvListaCitas = findViewById(R.id.doctor_citasDePaciente_listarv);

        inicioUI();

        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), controller_doctor_listaPacientes.class);
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
        Intent intent = new Intent(getApplicationContext(),controller_doctor_listaPacientes.class);
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
        model_cita model_cita = new model_cita(matriculaUser,this,this);
        model_cita.listarCitas();
    }

    @Override
    public void onSuccessHistorial(ArrayList<model_cita> historial) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListaCitas.setLayoutManager(linearLayoutManager);
        adaptadorRecyclerHistorialCitas = new adaptadorRecyclerHistorialCitas(historial, matricula, nombre, rol, this, new adaptadorRecyclerHistorialCitas.ItemClickListenerHistorialCitas() {
            @Override
            public void OnItemClick(model_cita details) {
                String aux = "";
                if (details.getEstado().equals("0")){
                    aux = "Terminada";
                }else if (details.getEstado().equals("1")){
                    aux = "Pendiente";
                }else if (details.getEstado().equals("2")){
                    aux = "Cancelada";
                }else if (details.getEstado().equals("3")){
                    aux = "Reagendada";
                }else if (details.getEstado().equals("4")){
                    aux = "Pendiente de cancelación";
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(controller_doctor_citasDePaciente.this);
                builder.setTitle("Detalles Cita");
                builder.setMessage("Folio: "+details.getId()+
                        "\n\nDia: "+details.getDia()+
                        "\nHora: "+details.getHora()+
                        "\nMotivo: "+details.getMotivo()+
                        "\n\nDescripcion: "+details.getDescripcion()+
                        "\n\nEstado: "+aux).setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setCancelable(false).show();
            }
        });
        rvListaCitas.setAdapter(adaptadorRecyclerHistorialCitas);
    }

    @Override
    public void onErrorhoraHistorial(String mensaje) {

    }
}