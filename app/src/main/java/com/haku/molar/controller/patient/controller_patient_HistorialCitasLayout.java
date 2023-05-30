package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.haku.molar.R;
import com.haku.molar.controller.patient.adapter.adaptadorRecyclerHistorialCitas;
import com.haku.molar.controller.patient.interfaces.Callback_patient_historialCitas;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public class controller_patient_HistorialCitasLayout extends AppCompatActivity implements Callback_patient_historialCitas {
    String matricula,nombre,rol,sexo;
    ImageView ivPerfil;
    BottomNavigationView menuNav;
    RecyclerView RVLista;
    private com.haku.molar.controller.patient.adapter.adaptadorRecyclerHistorialCitas adaptadorRecyclerHistorialCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_historial_citas);
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");
        RVLista = findViewById(R.id.HC_RV1);
        ivPerfil = findViewById(R.id.patient_historialCitaPerfil);
        model_cita model_cita = new model_cita(matricula,this,this);
        model_cita.listarCitas();

        menuNav = findViewById(R.id.menu_patient_menu);
        menuNav.setSelectedItemId(R.id.menu_patient_historial);

        //Listeners
        menuNav.setOnItemSelectedListener(item ->{
            switch (item.getItemId()){
                case R.id.menu_patient_home:
                    Intent intentHome = new Intent(this, controller_patient_MenuPaciente.class);
                    intentHome.putExtra("matricula",matricula);
                    intentHome.putExtra("nombre", nombre);
                    intentHome.putExtra("rol", rol);
                    intentHome.putExtra("sexo", sexo);
                    startActivity(intentHome);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_cita:
                    Intent intentCita = new Intent(this, controller_patient_OpcionesCitas.class);
                    intentCita.putExtra("matricula",matricula);
                    intentCita.putExtra("nombre", nombre);
                    intentCita.putExtra("rol", rol);
                    intentCita.putExtra("sexo", sexo);
                    startActivity(intentCita);
                    overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                    finish();
                    break;
                case R.id.menu_patient_notificacion:
                    Intent intentNotificacion = new Intent(this, controller_patient_NotificacionesPaciente.class);
                    intentNotificacion.putExtra("matricula",matricula);
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
        iniciarUI();
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
    public void iniciarUI(){
        switch (sexo){
            case "12":
                ivPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ivPerfil.setImageResource(R.mipmap.hombredos);
                break;
            case "13":
                ivPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ivPerfil.setImageResource(R.mipmap.hombretres);
                break;
            case "14":
                ivPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ivPerfil.setImageResource(R.mipmap.hombrecuatro);
                break;
            case "15":
                ivPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ivPerfil.setImageResource(R.mipmap.hombrecinco);
                break;
            case "22":
                ivPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivPerfil.setImageResource(R.mipmap.mujerdos);
                break;
            case "23":
                ivPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivPerfil.setImageResource(R.mipmap.mujertres);
                break;
            case "24":
                ivPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivPerfil.setImageResource(R.mipmap.mujercuatro);
                break;
            case "25":
                ivPerfil.setScaleType(ImageView.ScaleType.FIT_CENTER);
                ivPerfil.setImageResource(R.mipmap.mujercinco);
                break;
            default:
                Toast.makeText(this, "Elija una opcion valida", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onSuccessHistorial(ArrayList<model_cita> historial) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RVLista.setLayoutManager(linearLayoutManager);
        adaptadorRecyclerHistorialCitas = new adaptadorRecyclerHistorialCitas(historial, matricula, nombre, rol, this, new adaptadorRecyclerHistorialCitas.ItemClickListenerHistorialCitas() {
            @Override
            public void OnItemClick(model_cita details) {
                Intent intent = new Intent(getApplicationContext(),controller_patient_DetallesCitaPaciente.class);
                intent.putExtra("matricula",matricula);
                intent.putExtra("nombre",nombre);
                intent.putExtra("rol",rol);
                intent.putExtra("idCita",details.getId());
                intent.putExtra("sexo", sexo);
                startActivity(intent);
                finish();
            }
        });
        RVLista.setAdapter(adaptadorRecyclerHistorialCitas);
    }

    @Override
    public void onErrorhoraHistorial(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

}