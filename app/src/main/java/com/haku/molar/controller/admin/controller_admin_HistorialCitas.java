package com.haku.molar.controller.admin;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.admin.adapter.adaptador_admin_historialCita;
import com.haku.molar.controller.admin.interfaces.Callback_admin_historialCitas;
import com.haku.molar.controller.assistant.controller_assistant_menuCitas;
import com.haku.molar.model.admin.model_Admin;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.HashMap;

public class controller_admin_HistorialCitas extends AppCompatActivity implements Callback_admin_historialCitas {
    String matricula, nombre, rol, sexo;
    ImageView ivBackBtn, fotoPerfil;
    TextView tvNombre, tvMatricula;
    RecyclerView rvListaCitas;
    BottomNavigationView menuNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_admin_historial_citas);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        tvNombre = findViewById(R.id.admin_historialCitas_nombre);
        tvMatricula = findViewById(R.id.admin_historialCitas_matricula);
        fotoPerfil = findViewById(R.id.admin_historialCitas_perfil);
        rvListaCitas = findViewById(R.id.admin_historialCitas_listaCitasRv);

        inicioUI();

        menuNav = findViewById(R.id.menu_admin_menu);
        menuNav.setSelectedItemId(R.id.menu_admin_citas);

        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_admin_empleados:
                    Intent intent1 = new Intent(this, controller_admin_menuEmpleados.class);
                    intent1.putExtra("matricula",matricula);
                    intent1.putExtra("nombre", nombre);
                    intent1.putExtra("rol", rol);
                    intent1.putExtra("sexo", sexo);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_admin_usuarios:
                    Intent intent2 = new Intent(this, controller_admin_menuUsuario.class);
                    intent2.putExtra("matricula",matricula);
                    intent2.putExtra("nombre", nombre);
                    intent2.putExtra("rol", rol);
                    intent2.putExtra("sexo", sexo);
                    startActivity(intent2);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_admin_home:

                    Intent intent3 = new Intent(this, controller_admin_menuAdmin.class);
                    intent3.putExtra("matricula",matricula);
                    intent3.putExtra("nombre", nombre);
                    intent3.putExtra("rol", rol);
                    intent3.putExtra("sexo", sexo);
                    startActivity(intent3);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_admin_reportes:
                    Intent intent4 = new Intent(this, controller_admin_menuReportes.class);
                    intent4.putExtra("matricula",matricula);
                    intent4.putExtra("nombre", nombre);
                    intent4.putExtra("rol", rol);
                    intent4.putExtra("sexo", sexo);
                    startActivity(intent4);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
            }
            return false;
        });

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
        model_Admin model_admin = new model_Admin(this,this);
        model_admin.listarCitasTodas();
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

    @Override
    public void onSuccessListar(ArrayList<model_cita> listaActivas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListaCitas.setLayoutManager(linearLayoutManager);
        adaptador_admin_historialCita adaptador_admin_historialCita = new adaptador_admin_historialCita(listaActivas, this, new adaptador_admin_historialCita.ItemClickListenerHistorialCitas() {
            @Override
            public void OnItemClick(model_cita details) {
                Intent intent4 = new Intent(getApplicationContext(), controller_admin_DetallesCitaPaciente.class);
                intent4.putExtra("matriculaUser",matricula);
                intent4.putExtra("nombreUser", nombre);
                intent4.putExtra("rolUser", rol);
                intent4.putExtra("sexoUser", sexo);

                intent4.putExtra("id", details.getId());
                intent4.putExtra("dia", details.getDia());
                intent4.putExtra("hora", details.getHora());
                intent4.putExtra("motivo", details.getMotivo());
                intent4.putExtra("estado", details.getEstado());
                intent4.putExtra("descripcion", details.getDescripcion());
                intent4.putExtra("motivoCancelar", details.getMotivoCancelar());
                intent4.putExtra("nombre", details.getNombrePaciente());
                intent4.putExtra("apaterno", details.getApellidoPaciente());
                intent4.putExtra("email", details.getEmailPaciente());
                intent4.putExtra("matricula", details.getIdUusario());
                intent4.putExtra("sexo", details.getSexoPaciente());
                startActivity(intent4);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        rvListaCitas.setAdapter(adaptador_admin_historialCita);
    }

    @Override
    public void onErrorListar(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}