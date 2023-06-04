package com.haku.molar.controller.patient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.haku.molar.general.controller_General_Login;
import com.haku.molar.model.assistant.model_Assistant;
import com.haku.molar.utils.MolarCrypt;
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


public class controller_patient_RegistrarPaciente extends AppCompatActivity {

    private ImageView ivRegresar;
    private Button btnRegistrar;
    private RadioButton rbtnHombre, rbtnMujer;
    private EditText etNombre, etAPaterno, etAMaterno, etCorreo, etNumero, etContraseña, etConfirmarContraseña;
    private TextView tvIniciarSesion;
    private String nombre, apaterno, amaterno, correo, numero, contraseña, confirmarContraseña, matricula, contraseñaNoCrypt;
    private int rol=1, sexo;

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

    public void Registrar(View v){
        obtenerDatos();
        System.out.println(matricula + nombre + apaterno + amaterno + correo + numero + contraseña + confirmarContraseña + contraseñaNoCrypt);
        if (contraseña.equals(confirmarContraseña)){

            try {
                contraseña = MolarCrypt.encrypt(contraseña);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                throw new RuntimeException(e);
            }
            model_Assistant model_Assistant = new model_Assistant(Integer.parseInt(matricula),1,sexo,nombre,apaterno,amaterno,correo,numero,contraseña,this, contraseñaNoCrypt);
            model_Assistant.registrarPaciente();
        }else{
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
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
            //0 hombre 1 mujer

            //System.out.println(rbtnHombre.isChecked());
            //System.out.println(rbtnMujer.isChecked());
            if(rbtnHombre.isChecked()) {
                sexo = 0;
            } else if (rbtnMujer.isChecked()){
                sexo = 1;
            } else {
                //No se selecciono ninguno, mostrar falta
                //Mete un perro toast
                Context context = getApplicationContext();
                CharSequence text = "Algún dato no ha sido ingresado, por favor llena todos los " +
                        "campos solicitados";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }


            validarDatos();

            generarMatricula();


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
            System.out.println("A");
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
    public void inicioSesion(View v){
        Intent intent = new Intent(this, controller_General_Login.class);
        startActivity(intent);
        finish();
    }

    public void regresar(View v){
        Intent intent = new Intent(this,com.haku.molar.controller.patient.controller_patient_MenuPaciente.class);
        startActivity(intent);
        finish();
    }
}