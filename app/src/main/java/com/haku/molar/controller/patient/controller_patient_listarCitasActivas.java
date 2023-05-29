package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.patient.adapter.adaptadorRecyclerCitasActivas;
import com.haku.molar.controller.patient.interfaces.Callback_patient_listarCitasActivas;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public class controller_patient_listarCitasActivas extends AppCompatActivity implements Callback_patient_listarCitasActivas {
    String matricula, nombre, opcion,rol;
    RecyclerView RvListCitas;
    ImageView ivBackBtn;
    TextView tvNombre, tvMatricula;
    private adaptadorRecyclerCitasActivas adaptadorRecyclerCitasActivas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_listar_citas_activas);
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        opcion = intent.getStringExtra("opcion");
        rol = intent.getStringExtra("rol");
        RvListCitas = findViewById(R.id.patient_listarCitasActivasRV);
        ivBackBtn = findViewById(R.id.ivBackbtnListarCitasActivas);
        tvNombre = findViewById(R.id.patient_detallesCitaNombre);
        tvMatricula = findViewById(R.id.patient_detallesCitaMatricula);
        tvNombre.setText(nombre);
        tvMatricula.setText(matricula);
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), controller_patient_OpcionesCitas.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre",nombre);
                intent.putExtra("rol",rol);
                startActivity(intent);
                finish();
            }
        });
        model_cita model_cita = new model_cita(matricula, this, this);
        model_cita.listarCitasActivas();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso");
        builder.setMessage("Recuerde que solo puede reagendar y cancelar tres veces.\n\nPara consultar" +
                " mas información revise terminos y condiciones en perfil.").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(false).show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), controller_patient_OpcionesCitas.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        intent.putExtra("rol",rol);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccessListarCitasActivas(ArrayList<model_cita> listaActivas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RvListCitas.setLayoutManager(linearLayoutManager);
        adaptadorRecyclerCitasActivas = new adaptadorRecyclerCitasActivas(listaActivas, this, new adaptadorRecyclerCitasActivas.ItemClickListener() {
            @Override
            public void OnItemClick(model_cita details) {
                if (opcion.equals("1")){
                    Intent intentCancelar = new Intent(getApplicationContext(), controller_patient_CancelarCitaMotivo.class);
                    intentCancelar.putExtra("idCita",details.getId());
                    intentCancelar.putExtra("motivo",details.getMotivo());
                    intentCancelar.putExtra("fecha",details.getDia());
                    intentCancelar.putExtra("hora",details.getHora());
                    intentCancelar.putExtra("matricula",matricula);
                    intentCancelar.putExtra("nombre",nombre);
                    intentCancelar.putExtra("rol",rol);
                    startActivity(intentCancelar);
                    finish();
                }else{
                    Intent intentReagendar = new Intent(getApplicationContext(), controller_patient_ReagendarCitas.class);
                    intentReagendar.putExtra("idCita",details.getId());
                    intentReagendar.putExtra("matricula",matricula);
                    intentReagendar.putExtra("nombre",nombre);
                    intentReagendar.putExtra("rol",rol);
                    startActivity(intentReagendar);
                    finish();
                }
            }
        });
        RvListCitas.setAdapter(adaptadorRecyclerCitasActivas);
    }

    @Override
    public void onErrorListarCitasActivas(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}