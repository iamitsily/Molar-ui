package com.haku.molar.controller.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.assistant.controller_assistant_MenuAsistente;
import com.haku.molar.controller.assistant.controller_assistant_listarCitasPorCancelar;
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_listarCitasPorCancelar;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.HashMap;

public class controller_admin_DetallesCitaPaciente extends AppCompatActivity implements Callback_assistant_listarCitasPorCancelar {
    String matriculaUser, nombreUser, rolUser, sexoUser, id, dia, hora, motivo, estado, descripcion, motivoCancelar, nombre, aPaterno, email, matricula, sexo;
    TextView tvnombre, tvmatricula, tvfolio, tvEstado, tvMotivo, tvHora, tvFecha;
    ImageView back, perfil;
    Button btnCancelar, btnReagendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_admin_details_cita_paciente);

        Intent intent = getIntent();
        matriculaUser = intent.getStringExtra("matriculaUser");
        nombreUser = intent.getStringExtra("nombreUser");
        rolUser = intent.getStringExtra("rolUser");
        sexoUser = intent.getStringExtra("sexoUser");

        id = intent.getStringExtra("id");
        dia = intent.getStringExtra("dia");
        hora = intent.getStringExtra("hora");
        motivo = intent.getStringExtra("motivo");
        estado = intent.getStringExtra("estado");
        descripcion = intent.getStringExtra("descripcion");
        motivoCancelar = intent.getStringExtra("motivoCancelar");
        nombre = intent.getStringExtra("nombre");
        aPaterno = intent.getStringExtra("apaterno");
        email = intent.getStringExtra("email");
        matricula = intent.getStringExtra("matricula");
        sexo = intent.getStringExtra("sexo");

        tvnombre = findViewById(R.id.admin_detallesCita_nombre);
        tvmatricula = findViewById(R.id.admin_detallesCita_matricula);
        tvfolio = findViewById(R.id.admin_detallesCita_folio);
        tvEstado = findViewById(R.id.admin_detallesCita_medico);
        tvMotivo = findViewById(R.id.admin_detallesCita_motivo);
        tvHora = findViewById(R.id.admin_detallesCita_hora);
        tvFecha = findViewById(R.id.admin_detallesCita_fecha);

        back = findViewById(R.id.admin_detallesCita_back);
        perfil = findViewById(R.id.admin_detallesCita_perfil);

        btnCancelar = findViewById(R.id.admin_detallesCita_btnCancelar);
        btnReagendar = findViewById(R.id.admin_detallesCita_btnReagendar);

        inicioUI();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(controller_admin_DetallesCitaPaciente.this);
                builder.setTitle("¿Cancelar cita?");
                builder.setMessage("Esta acción cancelara la cita actual.").setPositiveButton("Confirmar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        model_cita model_cita = new model_cita(id,email,controller_admin_DetallesCitaPaciente.this,controller_admin_DetallesCitaPaciente.this);
                        model_cita.cancelarCita(id,"[Cita cancelada por Administrador]",dia,matricula);
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).show();
            }
        });
        btnReagendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), controller_admin_HistorialCitas.class);
                intent.putExtra("matricula",matriculaUser);
                intent.putExtra("nombre", nombreUser);
                intent.putExtra("rol", rolUser);
                intent.putExtra("sexo", sexoUser);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
    }
    public void inicioUI(){
        tvnombre.setText(nombre + " " + aPaterno);
        tvmatricula.setText(matricula);
        tvfolio.setText("Fólio: "+id);
        switch(estado){
            case "0": //Terminada
                tvEstado.setText("Estado: Terminada");
                break;
            case "1": //Pendiente
                tvEstado.setText("Estado: Pendiente");
                btnReagendar.setVisibility(View.VISIBLE);
                btnCancelar.setVisibility(View.VISIBLE);
                break;
            case "2": //Cancelada
                tvEstado.setText("Estado: Cancelada");
                break;
            case "3": //Reagendada
                tvEstado.setText("Estado: Reagendada");
                btnCancelar.setVisibility(View.VISIBLE);
                break;
            case "4": //Pendiente a cancelar
                tvEstado.setText("Estado: Pendiente a cancelar");
                break;
            default:
                tvEstado.setText("Estado desconocido");
                break;
        }
        tvMotivo.setText("Motivo de su cita: "+motivo);
        tvHora.setText(hora);
        tvFecha.setText(dia);
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
            perfil.setScaleType(opcionSexo.second);
            perfil.setImageResource(opcionSexo.first);
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), controller_admin_HistorialCitas.class);
        intent.putExtra("matricula",matriculaUser);
        intent.putExtra("nombre", nombreUser);
        intent.putExtra("rol", rolUser);
        intent.putExtra("sexo", sexoUser);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }

    @Override
    public void onSuccessListar(ArrayList<model_cita> listaActivas) {

    }

    @Override
    public void onErrorListar(String mensaje) {

    }

    @Override
    public void onSuccessCancelar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(controller_admin_DetallesCitaPaciente.this);
        builder.setTitle("Cancelación");
        builder.setMessage("Cita cancelada, se enviara un email de confirmación al paciente").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), controller_admin_HistorialCitas.class);
                intent.putExtra("matricula",matriculaUser);
                intent.putExtra("nombre", nombreUser);
                intent.putExtra("rol", rolUser);
                intent.putExtra("sexo", sexoUser);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        }).setCancelable(false).show();
    }

    @Override
    public void onErrorCancelar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}