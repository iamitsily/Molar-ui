package com.haku.molar.controller.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.doctor.interfaces.Callback_doctor_detallesCitaPaciente;
import com.haku.molar.model.cita.model_cita;

import java.util.HashMap;

public class controller_doctor_detalles_cita_paciente extends AppCompatActivity implements Callback_doctor_detallesCitaPaciente {
    String matricula, nombre, rol,idCita, sexo,estado;
    ImageView ivBackBtn,ivPerfil;
    TextView tvNombre, tvMatricula, tvFolio, tvMedico, tvMotivp, tvHora, tvFecha;
    Button terminarCitabtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_doctor_detalles_cita_paciente);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");
        idCita = intent.getStringExtra("idCita");
        estado = intent.getStringExtra("estado");

        ivBackBtn = findViewById(R.id.doctor_detallesCita_back);
        tvNombre = findViewById(R.id.doctor_detallesCita_detallesCitaNombre);
        tvMatricula = findViewById(R.id.doctor_detallesCita_statusCita);
        tvFolio = findViewById(R.id.admin_detallesCita_folio);
        tvMedico = findViewById(R.id.admin_detallesCita_medico);
        tvMotivp = findViewById(R.id.admin_detallesCita_motivo);
        tvHora = findViewById(R.id.doctor_detallesCita_horaCita);
        tvFecha = findViewById(R.id.doctor_detallesCita_fecha);
        ivPerfil = findViewById(R.id.doctor_detallesCita_fotoPerfil);
        terminarCitabtn = findViewById(R.id.doctor_detallesCita_btnTerminar);

        inicioUI();
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backbtn();
            }
        });
        terminarCitabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model_cita model_cita = new model_cita(idCita, controller_doctor_detalles_cita_paciente.this, controller_doctor_detalles_cita_paciente.this);
                model_cita.terminarCita();
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, controller_doctor_historial_citas.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        intent.putExtra("rol",rol);
        intent.putExtra("sexo",sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void inicioUI(){
        if (estado.equals("1") || estado.equals("3")){
         terminarCitabtn.setVisibility(View.VISIBLE);
        }
        model_cita model_cita = new model_cita(idCita,this,this);
        model_cita.detallesCitaPaciente();
    }
    public void backbtn(){
        Intent i = new Intent(this, controller_doctor_historial_citas.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("sexo",sexo);
        startActivity(i);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    @Override
    public void onSuccessDetallesCita(String[] datos) {
        tvFolio.setText("Folio: "+datos[0]);
        tvMedico.setText("Medico: "+nombre);
        tvMotivp.setText("Motivo: "+datos[3]);
        tvHora.setText(datos[2]);
        tvFecha.setText(datos[1]);
        tvNombre.setText(datos[4]+" "+datos[5]);
        tvMatricula.setText(datos[6]);

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

        Pair<Integer, ImageView.ScaleType> opcionSexo = mapaSexo.get(datos[7]);

        if (opcionSexo == null) {
            Toast.makeText(this, "Elija una opción válida", Toast.LENGTH_SHORT).show();
        } else {
            ivPerfil.setScaleType(opcionSexo.second);
            ivPerfil.setImageResource(opcionSexo.first);
        }

    }
    @Override
    public void onErrorDetallesCita(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccesTerminarCita() {
        Toast.makeText(this, "Cita terminada", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, controller_doctor_historial_citas.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("sexo",sexo);
        startActivity(i);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }

    @Override
    public void OnErrorTerminarCita(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}