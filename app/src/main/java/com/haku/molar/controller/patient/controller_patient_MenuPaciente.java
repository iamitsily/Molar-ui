package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.patient.adapter.adaptadorRecyclerMenuPaciente;
import com.haku.molar.controller.patient.interfaces.Callback_patient_menu;
import com.haku.molar.model.cita.model_cita;
import com.haku.molar.model.patient.model_Patient;

import java.util.ArrayList;
import java.util.HashMap;

public class controller_patient_MenuPaciente extends AppCompatActivity implements Callback_patient_menu {
    TextView tvNombre, tvMatricula;
    String matricula, nombre, rol,sexo;
    ImageButton ibAjustesMenu;
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
        sexo = intent.getStringExtra("sexo");
        tvNombre = (TextView) findViewById(R.id.tvNombre_patient_MenuPaciente);
        tvMatricula = findViewById(R.id.tvMatricula_patient_MenuPaciente);
        RvListCitas = findViewById(R.id.view_patient_menuPacienteReciclerView);
        ibAjustesMenu = findViewById(R.id.patient_menu_ibAjustesCuenta);

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
                    intentCita.putExtra("sexo", sexo);
                    startActivity(intentCita);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_historial:
                    Intent intentHistorial = new Intent(this, controller_patient_HistorialCitasLayout.class);
                    intentHistorial.putExtra("matricula", matricula);
                    intentHistorial.putExtra("nombre", nombre);
                    intentHistorial.putExtra("rol", rol);
                    intentHistorial.putExtra("sexo", sexo);
                    startActivity(intentHistorial);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_notificacion:
                    Intent intentNotificacion = new Intent(this, controller_patient_NotificacionesPaciente.class);
                    intentNotificacion.putExtra("matricula", matricula);
                    intentNotificacion.putExtra("nombre", nombre);
                    intentNotificacion.putExtra("rol", rol);
                    intentNotificacion.putExtra("sexo", sexo);
                    startActivity(intentNotificacion);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });

        listarCitas();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Molar");
        builder.setMessage("¿Desea salir de la aplicación?").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(false).show();
    }
    public void listarCitas(){
        model_Patient model_patient = new model_Patient(Integer.parseInt(matricula),this,this);
        model_patient.listarCitas();
    }
    public void inicioUI() {
        tvNombre.setText(nombre);
        tvMatricula.setText(matricula + " | Paciente");
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
            ibAjustesMenu.setScaleType(opcionSexo.second);
            ibAjustesMenu.setImageResource(opcionSexo.first);
        }

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
        intentAjustesCuenta.putExtra("sexo", sexo);
        startActivity(intentAjustesCuenta);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
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

}