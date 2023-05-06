package com.haku.molar.controller.assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.haku.molar.MolarCrypt;
import com.haku.molar.R;
import com.haku.molar.model.patient.model_Patient;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class controller_assistant_registrarPaciente extends AppCompatActivity {
    private ImageView ibRegresar;
    private Button btnRegistrar;
    private RadioButton rbtnHombre, rbtnMujer;
    private EditText etNombre, etAPaterno, etAMaterno, etCorreo, etNumero, etContraseña, etConfirmarContraseña;
    private TextView tvIniciarSesion;
    private String nombre, apaterno, amaterno, correo, numero, contraseña, confirmarContraseña, matricula, contraseñaNoCrypt;
    private int rol, sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_registrar_paciente);

        ibRegresar = findViewById(R.id.view_assistant_registrarPaciente_btnback);

        btnRegistrar = findViewById(R.id.view_assistant_registrarPaciente_btnRegistrar);

        rbtnHombre = findViewById(R.id.view_assistant_registrarPaciente_rbHombre);
        rbtnMujer = findViewById(R.id.view_assistant_registrarPaciente_rbMujer);

        etNombre = findViewById(R.id.view_assistant_registrarPaciente_edtnombre);
        etAPaterno = findViewById(R.id.view_assistant_registrarPaciente_edtApaterno);
        etAMaterno = findViewById(R.id.view_assistant_registrarPaciente_edtAmaterno);
        etCorreo = findViewById(R.id.view_assistant_registrarPaciente_email);
        etNumero = findViewById(R.id.view_assistant_registrarPaciente_telefono);
        etContraseña = findViewById(R.id.view_assistant_registrarPaciente_password);
        etConfirmarContraseña = findViewById(R.id.view_assistant_registrarPaciente_confirmedPass);

        tvIniciarSesion = findViewById(R.id.regpac_tv4);
    }

    public void Registrar(View v){
        
        obtenerDatos();
        /*
        System.out.println(matricula + nombre + apaterno + amaterno + correo + numero + contraseña + confirmarContraseña + contraseñaNoCrypt);
        if (contraseña.equals(confirmarContraseña)){
            try {
                contraseña = MolarCrypt.encrypt(contraseña);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                     InvalidAlgorithmParameterException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException e) {
                throw new RuntimeException(e);
            }
            model_Patient model_patient = new model_Patient(Integer.parseInt(matricula),1,sexo,nombre,apaterno,amaterno,correo,numero,contraseña,this, contraseñaNoCrypt);
            model_patient.registrarPaciente();
        }else{
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        */
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
            contraseñaNoCrypt = String.valueOf(etContraseña.getText());
            System.out.println(rbtnHombre.isChecked());
            System.out.println(rbtnMujer.isChecked());
            System.out.println(!rbtnHombre.isChecked());
            System.out.println(!rbtnMujer.isChecked());
            //0 hombre 1 mujer
            if ((!rbtnHombre.isChecked())&&(!rbtnMujer.isChecked())){
                Toast.makeText(this, "Seleccione sexo", Toast.LENGTH_SHORT).show();
            }else{

            }
            if(rbtnHombre.isChecked()) {
                sexo = 0;
            } else if (rbtnMujer.isChecked()){
                sexo = 1;
            } else {
                /*
                //No se selecciono ninguno, mostrar falta
                Context context = getApplicationContext();
                CharSequence text = "Algún dato no ha sido ingresado, por favor llena todos los " +
                        "campos solicitados";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show(); */
            }

            /*
            validarDatos();

            generarMatricula();
            */

        } catch (Exception e){
            System.out.println(e);
            //Utilizar un toast para notificar rectificacion de datos

        }
    }

    private void generarMatricula(){
        //año + mes + 4 ultimos digitos de telefono
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateandTime = simpleDateFormat.format(new Date());

        matricula = currentDateandTime.substring(6,10) + currentDateandTime.substring(3,5) + numero.substring(6,10);
        //System.out.println(currentDateandTime);
        //System.out.println(numero);
    }

    private void validarDatos(){
        boolean error = false;
        String aux = "@gmail.com", dominio="";
        int i=0;
        //Revisar el plan de pruebas integrales
        if(!(nombre != null && nombre.matches("^[a-zA-Z]*$"))){
            error = true;
            etNombre.setBackgroundColor(Color.RED);
        }
        if(!(apaterno != null && apaterno.matches("^[a-zA-Z]*$"))){
            error = true;
            System.out.println("B");
        }
        if(!(amaterno != null && amaterno.matches("^[a-zA-Z]*$"))){
            error = true;
            System.out.println("C");
        }
        if(nombre.length() < 3){
            error = true;
            System.out.println("D");
        }
        if(apaterno.length() < 4){
            error = true;
            System.out.println("E");
        }
        if(amaterno.length() < 4) {
            error = true;
            System.out.println("F");
        }
        i = correo.length();
        dominio = correo.substring((i-10),i);
        //System.out.println(dominio);
        if((dominio.equals(aux)) == false){
            error = true;
        }
        if(numero.length() != 10){
            error = true;
            System.out.println("H");
        }
        if(contraseña.length() > 30){
            error = true;
            System.out.println("I");
        }
        if(confirmarContraseña.length() > 30){
            error = true;
            System.out.println("J");
        }
        if(error == true){
            Context context = getApplicationContext();
            CharSequence text = "Los datos ingresados no son válidos, por favor revisar";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    public void regresar(View v){
        Intent intent = new Intent(this,com.haku.molar.controller.patient.controller_patient_MenuPaciente.class);
        startActivity(intent);
        finish();
    }
}