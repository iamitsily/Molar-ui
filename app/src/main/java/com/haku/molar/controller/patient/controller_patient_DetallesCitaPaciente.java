package com.haku.molar.controller.patient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.haku.molar.R;
import com.haku.molar.controller.patient.interfaces.Callback_patient_detallesCita;
import com.haku.molar.model.cita.model_cita;

import java.util.HashMap;

public class controller_patient_DetallesCitaPaciente extends AppCompatActivity implements Callback_patient_detallesCita {
    String matricula, nombre, rol,idCita, sexo;
    ImageView ivBackBtn,ivPerfil;
    TextView tvNombre, tvMatricula, tvFolio, tvMedico, tvMotivp, tvHora, tvFecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_details_cita_paciente);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");
        idCita = intent.getStringExtra("idCita");

        ivBackBtn = findViewById(R.id.ivBackBtnDetallesCitaPatient);
        tvNombre = findViewById(R.id.admin_detallesCitaNombre);
        tvMatricula = findViewById(R.id.dotor_detallesCitaMatricula);
        tvFolio = findViewById(R.id.patient_detallesCita_folio);
        tvMedico = findViewById(R.id.patient_detallesCita_medico);
        tvMotivp = findViewById(R.id.patient_detallesCita_motivo);
        tvHora = findViewById(R.id.patient_detallesCita_hora);
        tvFecha = findViewById(R.id.patient_detallesCita_fecha);
        ivPerfil = findViewById(R.id.FotoPerfil);
        inicioUI();
        ivBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backbtn();
            }
        });
    }
    @Override
    public void onBackPressed() {
        backbtn();
    }
    public void inicioUI(){
        tvNombre.setText(nombre);
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
            ivPerfil.setScaleType(opcionSexo.second);
            ivPerfil.setImageResource(opcionSexo.first);
        }
        cargarDatosCita();
    }
    public void cargarDatosCita(){
        model_cita model_cita = new model_cita(idCita,this,this);
        model_cita.detallesCita();
    }
    public void backbtn(){
        Intent i = new Intent(this,controller_patient_HistorialCitasLayout.class);
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
        tvMedico.setText("Medico: "+datos[4]+" "+datos[5]);
        tvMotivp.setText("Motivo: "+datos[3]);
        tvHora.setText(datos[2]);
        tvFecha.setText(datos[1]);
    }

    @Override
    public void onErrorDetallesCita(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}

