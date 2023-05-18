package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.patient.adapter.adaptadorRecyclerMenuPaciente;
import com.haku.molar.model.cita.model_cita;
import com.haku.molar.model.patient.Callback_patient;
import com.haku.molar.model.patient.model_Patient;

import java.util.ArrayList;

public class controller_patient_MenuPaciente extends AppCompatActivity implements Callback_patient {
    TextView tvNombre;
    String matricula, nombre, rol;

    BottomNavigationView menuNav;
    RecyclerView RvListCitas;
    private com.haku.molar.controller.patient.adapter.adaptadorRecyclerMenuPaciente adaptadorRecyclerMenuPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_menu_paciente);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        tvNombre = (TextView) findViewById(R.id.tvNombre_patient_MenuPaciente);
        RvListCitas = findViewById(R.id.view_patient_menuPacienteReciclerView);

        inicioUI();

        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_home);
        //Listeners
        menuNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_patient_cita:
                    Intent intentCita = new Intent(this, controller_patient_OpcionesCitas.class);
                    intentCita.putExtra("matricula", matricula);
                    intentCita.putExtra("nombre", nombre);
                    intentCita.putExtra("rol", rol);
                    startActivity(intentCita);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_historial:
                    Intent intentHistorial = new Intent(this, controller_patient_HistorialCitasLayout.class);
                    intentHistorial.putExtra("matricula", matricula);
                    intentHistorial.putExtra("nombre", nombre);
                    intentHistorial.putExtra("rol", rol);
                    startActivity(intentHistorial);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_notificacion:
                    Intent intentNotificacion = new Intent(this, controller_patient_NotificacionesPaciente.class);
                    intentNotificacion.putExtra("matricula", matricula);
                    intentNotificacion.putExtra("nombre", nombre);
                    intentNotificacion.putExtra("rol", rol);
                    startActivity(intentNotificacion);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });

        listarCitas();
    }
    public void listarCitas(){
        model_Patient model_patient = new model_Patient(Integer.parseInt(matricula),this,this);
        model_patient.listarCitas();
    }
    public void inicioUI() {
        tvNombre.setText(nombre);
    }

    public void registrar(View view) {
        startActivity(new Intent(this, controller_patient_RegistrarPaciente.class));
        finish();
    }

    public void ajustesCuenta(View view) {
        Intent intentAjustesCuenta = new Intent(this, controller_patient_AjustesDeCuentaMenu.class);
        intentAjustesCuenta.putExtra("matricula", matricula);
        intentAjustesCuenta.putExtra("nombre", nombre);
        intentAjustesCuenta.putExtra("rol", rol);
        startActivity(intentAjustesCuenta);
        finish();
    }

    @Override
    public void onSuccessbuscarDatos(String[] datos) {}
    @Override
    public void onErrorbuscarDatos(String mensaje) {}
    @Override
    public void onErrorudpatebyUser(String mensaje) {}
    @Override
    public void OnSuccesslistarCitas(ArrayList<model_cita> citas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RvListCitas.setLayoutManager(linearLayoutManager);
        adaptadorRecyclerMenuPaciente = new adaptadorRecyclerMenuPaciente(citas);
        RvListCitas.setAdapter(adaptadorRecyclerMenuPaciente);
    }

    @Override
    public void OneErrorlistarCitas(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessObternerPass(String[] datos) {

    }

    @Override
    public void onErrorObternerPass(String mensaje) {

    }

    @Override
    public void onSuccessUpdatebyUser() {

    }

    @Override
    public void OnErrorUpdateByUser(String mensaje) {

    }
}