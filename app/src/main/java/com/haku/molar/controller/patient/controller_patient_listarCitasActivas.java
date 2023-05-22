package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.patient.adapter.adaptadorRecyclerCitasActivas;
import com.haku.molar.controller.patient.interfaces.Callback_patient_listarCitasActivas;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public class controller_patient_listarCitasActivas extends AppCompatActivity implements Callback_patient_listarCitasActivas {
    String matricula, nombre;
    RecyclerView RvListCitas;
    private adaptadorRecyclerCitasActivas adaptadorRecyclerCitasActivas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_listar_citas_activas);

        RvListCitas = findViewById(R.id.patient_listarCitasActivasRV);
        matricula="2023057300";
        model_cita model_cita = new model_cita(matricula, this, this);
        model_cita.listarCitasActivas();
    }

    @Override
    public void onSuccessListarCitasActivas(ArrayList<model_cita> listaActivas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RvListCitas.setLayoutManager(linearLayoutManager);
        adaptadorRecyclerCitasActivas = new adaptadorRecyclerCitasActivas(listaActivas);
        RvListCitas.setAdapter(adaptadorRecyclerCitasActivas);
    }

    @Override
    public void onErrorListarCitasActivas(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}