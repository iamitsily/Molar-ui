package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.haku.molar.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class controller_patient_RegistrarPaciente extends AppCompatActivity {

    private ImageView ivRegresar;
    private Button btnRegistrar;
    private RadioButton rbtnHombre,rbtnMujer;
    private EditText etNombre,etAPaterno,etAMaterno,etCorreo,etNumero,etContraseña,etConfirmarContraseña;
    private TextView tvIniciarSesion;
    private String nombre,apaterno,amaterno,correo,numero,contraseña,confirmarContraseña,matricula;
    private int rol=1,sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_patient_registrar_paciente);

        ivRegresar = findViewById(R.id.regpac_iv1);

        btnRegistrar = findViewById(R.id.regpac_btn1);

        rbtnHombre = findViewById(R.id.regpac_rb1);
        rbtnMujer = findViewById(R.id.regpac_rb2);

        etNombre = findViewById(R.id.regpac_et1);
        etAPaterno = findViewById(R.id.regpac_et2);
        etAMaterno = findViewById(R.id.regpac_et3);
        etCorreo = findViewById(R.id.regpac_et4);
        etNumero = findViewById(R.id.regpac_et5);
        etContraseña = findViewById(R.id.regpac_et6);
        etConfirmarContraseña = findViewById(R.id.regpac_et7);

        tvIniciarSesion = findViewById(R.id.regpac_tv4);

    }

    public void botonRegistrar(View v){
        obtenerDatos();
    }
    private void obtenerDatos(){
        try {
            nombre = String.valueOf(etNombre.getText());
            apaterno = String.valueOf(etAPaterno.getText());
            amaterno = String.valueOf(etAMaterno.getText());
            correo = String.valueOf(etCorreo.getText());
            numero = String.valueOf(etNumero.getText());
            contraseña = String.valueOf(etContraseña.getText());
            confirmarContraseña = String.valueOf(etConfirmarContraseña.getText());

            //0 hombre 1 mujer
            if(rbtnHombre.isSelected()) {
                sexo = 0;
            } else if (rbtnMujer.isSelected()){
                sexo = 1;
            } else {
                //No se selecciono ninguno, mostrar falta
            }

            validarDatos();

            generarMatricula();

            enviarDatos();

        } catch (Exception e){
            System.out.println(e);
            //Utilizar un toast para notificar rectificacion de datos
        }
    }
    private void generarMatricula(){
        //año + mes + 4 ultimos digitos de telefono
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = simpleDateFormat.format(new Date());

        matricula = currentDateandTime.substring(6,9) + currentDateandTime.substring(3,4) + numero.substring(6,9);
    }

    private void enviarDatos(){
        //Aqui va el modelo del paciente y su funcion de enviar.
    }

    private void validarDatos(){
        //Revisar el plan de pruebas integrales
    }
}