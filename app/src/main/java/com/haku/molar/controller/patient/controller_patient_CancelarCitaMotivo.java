package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.haku.molar.R;

public class controller_patient_CancelarCitaMotivo extends AppCompatActivity {

    String matricula,nombre,rol,idCita,motivo,fecha,hora;
    ImageView imageView;
    EditText edtMotivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_cancelarcitamotivo);

        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        idCita = intent.getStringExtra("idCita");
        motivo = intent.getStringExtra("motivo");
        fecha = intent.getStringExtra("fecha");
        hora = intent.getStringExtra("hora");
        imageView = findViewById(R.id.imageButton8);
        edtMotivo = findViewById(R.id.edtMotivoCancelarCita);
    }
    public void cancelarCita(View view){
        if (edtMotivo.getText().toString().trim().equals("")){
            Toast.makeText(this, "Por favor escriba el motivo de cancelación", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¿Cancelar cita?");
            builder.setMessage("Revise los datos de la cita a cancelar.\nIdCita: "+idCita+"\n"+"Motivo: "+motivo+
                    "\nFecha: "+fecha+"\nHora: "+hora).setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(controller_patient_CancelarCitaMotivo.this, "Eliminado", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("Atras", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(getApplicationContext(), controller_patient_OpcionesCitas.class);
                    intent.putExtra("matricula",matricula);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("rol",rol);
                    startActivity(intent);
                    finish();
                }
            }).setCancelable(false).show();
        }
    }
    public void regresar(View view){
        Intent i = new Intent(this,controller_patient_listarCitasActivas.class);
        i.putExtra("matricula",matricula);
        i.putExtra("nombre",nombre);
        i.putExtra("rol",rol);
        i.putExtra("opcion","1");
        startActivity(i);
        finish();
    }

}