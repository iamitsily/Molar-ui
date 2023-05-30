package com.haku.molar.controller.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.haku.molar.R;
import com.haku.molar.controller.patient.interfaces.Callback_patient_detallesCita;
import com.haku.molar.model.cita.model_cita;

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
        tvNombre = findViewById(R.id.patient_detallesCitaNombre);
        tvMatricula = findViewById(R.id.patient_detallesCitaMatricula);
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

