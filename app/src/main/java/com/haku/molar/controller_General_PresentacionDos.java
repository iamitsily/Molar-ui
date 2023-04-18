package com.haku.molar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.haku.molar.controller.patient.controller_patient_MenuPaciente;

public class controller_General_PresentacionDos extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView1,textView2;
    private Button button;
    int cont = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_general_presentacion_dos);

        imageView = (ImageView) findViewById(R.id.presentacionLayout_imageView);
        textView1 = (TextView) findViewById(R.id.presentacionLayout_textview1);
        textView2 = (TextView) findViewById(R.id.presentacionLayout_textview2);
        button = (Button) findViewById(R.id.presentacionLayout_btnSkip);
    }

    public void siguiente(View view){
        if (cont == 0){
            imageView.setImageResource(R.mipmap.view_patient_presentacionlayoutimageview2);
            textView1.setText("Servicio de Calidad");
            textView2.setText("Nuestro personal esta altamente capacitado con los mejores estandares de calidad");
            button.setVisibility(View.INVISIBLE);
            cont++;
        }else if (cont == 1){
            Intent intent = new Intent(this, controller_patient_MenuPaciente.class);
            startActivity(intent);
            finish();
        }
    }
    public void skip(View view){
        Intent intent = new Intent(this, controller_patient_MenuPaciente.class);
        startActivity(intent);
        finish();

    }
}