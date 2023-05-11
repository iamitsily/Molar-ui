package com.haku.molar.controller.assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.haku.molar.utils.MolarCrypt;
import com.haku.molar.R;
import com.haku.molar.model.patient.model_Patient;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class controller_assistant_registrarPaciente extends AppCompatActivity {
    private ImageView ibRegresar;
    private RadioButton rbtnHombre, rbtnMujer;
    private EditText etNombre, etAPaterno, etAMaterno, etCorreo, etNumero, etContraseña, etConfirmarContraseña;
    private String nombre, apaterno, amaterno, correo, numero, contraseña, confirmarContraseña, matricula, contraseñaNoCrypt;
    private int rol, sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_registrar_paciente);

        ibRegresar = findViewById(R.id.view_assistant_registrarPaciente_btnback);

        rbtnHombre = findViewById(R.id.view_assistant_registrarPaciente_rbHombre);
        rbtnMujer = findViewById(R.id.view_assistant_registrarPaciente_rbMujer);

        etNombre = findViewById(R.id.view_assistant_registrarPaciente_edtnombre);
        etAPaterno = findViewById(R.id.view_assistant_registrarPaciente_edtApaterno);
        etAMaterno = findViewById(R.id.view_assistant_registrarPaciente_edtAmaterno);
        etCorreo = findViewById(R.id.view_assistant_registrarPaciente_email);
        etNumero = findViewById(R.id.view_assistant_registrarPaciente_telefono);
        etContraseña = findViewById(R.id.view_assistant_registrarPaciente_password);
        etConfirmarContraseña = findViewById(R.id.view_assistant_registrarPaciente_confirmedPass);

        ibRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),controller_assistant_MenuAsistente.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Registrar(View v){

        obtenerDatos();
        if(validarDatos()){
            System.out.println("Datos incorrectos");
        }else{
            System.out.println("Datos correctos");
            generarMatricula();
            try {
                contraseña = MolarCrypt.encrypt(contraseña);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                     InvalidAlgorithmParameterException | InvalidKeyException |
                     IllegalBlockSizeException | BadPaddingException e) {
                throw new RuntimeException(e);
            }
            nombre = palabraMayuscula(nombre);
            apaterno = palabraMayuscula(apaterno);
            amaterno = palabraMayuscula(amaterno);
            imprimirDatos();
            model_Patient model_patient = new model_Patient(Integer.parseInt(matricula),1,sexo,nombre,apaterno,amaterno,correo,numero,contraseña,this, contraseñaNoCrypt);
            model_patient.registrarPaciente();
            limpiarDatos();
        }
    }
    private void imprimirDatos(){
        System.out.println("Nombre: "+nombre);
        System.out.println("Apaterno: "+apaterno);
        System.out.println("Amaterno: "+amaterno);
        System.out.println("Correo: "+correo);
        System.out.println("Numero: "+numero);
        System.out.println("Contraseña: "+contraseña);
        System.out.println("ContraseñaConfirmed: "+confirmarContraseña);
        System.out.println("Hombre: "+rbtnHombre.isChecked());
        System.out.println("Mujer: "+rbtnMujer.isChecked());

    }

    private void obtenerDatos(){
        try {
            nombre = etNombre.getText().toString().trim();
            apaterno = etAPaterno.getText().toString().trim();
            amaterno = etAMaterno.getText().toString().trim();
            correo = etCorreo.getText().toString().trim();
            numero = etNumero.getText().toString().trim();
            contraseña = etContraseña.getText().toString().trim();
            confirmarContraseña = etConfirmarContraseña.getText().toString().trim();
            contraseñaNoCrypt = etContraseña.getText().toString().trim();
            //0 hombre 1 mujer
            if ((!rbtnHombre.isChecked())&&(!rbtnMujer.isChecked())){
                //Toast.makeText(this, "Seleccione sexo", Toast.LENGTH_SHORT).show();
            }else{
                if (rbtnHombre.isChecked()){
                    sexo=0;
                }else{
                    sexo=1;
                }
            }
        } catch (Exception e){
            System.out.println(e);
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

    private boolean validarDatos(){
        boolean error = false;

        //Revisar el plan de pruebas integrales
        if(nombre.equals("") || !nombre.matches("^[a-zA-Z]*$")){
            error = true;
            etNombre.setText("");
            etNombre.setHint("Revisar nombre "+nombre);
            etNombre.setHintTextColor(Color.RED);
        }
        if(apaterno.equals("") || !apaterno.matches("^[a-zA-Z]*$")){
            error = true;
            etAPaterno.setText("");
            etAPaterno.setHint("Revisar apaterno "+apaterno);
            etAPaterno.setHintTextColor(Color.RED);
        }
        if(amaterno.equals("") || !amaterno.matches("^[a-zA-Z]*$")){
            error = true;
            etAMaterno.setText("");
            etAMaterno.setHint("Revisar amaterno "+amaterno);
            etAMaterno.setHintTextColor(Color.RED);
        }
        if(nombre.length() < 3){
            error = true;
            Toast.makeText(this, "Nombre mayor o igual a 2 caracteres", Toast.LENGTH_SHORT).show();
        }
        if(apaterno.length() < 4){
            error = true;
            Toast.makeText(this, "Apaterno mayor o igual a 3 caracteres", Toast.LENGTH_SHORT).show();
        }
        if(amaterno.length() < 4) {
            error = true;
            Toast.makeText(this, "Amaterno mayor o igual a 3 caracteres", Toast.LENGTH_SHORT).show();
        }
        if (!validarEmail(correo)) {
            etCorreo.setText("");
            etCorreo.setHint("Revisar correo "+correo);
            etCorreo.setHintTextColor(Color.RED);
        }
        if(numero.length() < 10){
            error = true;
            etNumero.setText("");
            etNumero.setHint("Numero a 10 digitos");
            etNumero.setHintTextColor(Color.RED);
        }
        if(contraseña.length() > 30){
            error = true;
            etContraseña.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Maximo 30 caracteres en contraseña", Toast.LENGTH_SHORT).show();
        }
        if(confirmarContraseña.length() > 30){
            error = true;
            Toast.makeText(this, "Maximo 30 caracteres en confirmación de contraseña", Toast.LENGTH_SHORT).show();
        }
        if (contraseña.equals("")){
            error = true;
            Toast.makeText(this, "Llene el campo contraseña", Toast.LENGTH_SHORT).show();
        }
        if (confirmarContraseña.equals("")){
            Toast.makeText(this, "Llene el campo confirmar contraseña", Toast.LENGTH_SHORT).show();
        }
        if (!contraseña.equals(confirmarContraseña)){
            error=true;
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        if(error == true){
            Toast.makeText(this, "Revisar registro", Toast.LENGTH_SHORT).show();
        }
        System.out.println(error);
        return error;
    }
    public static boolean validarEmail(String email) {
        String regex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public String palabraMayuscula(String string){
        String ofiString="";
        ofiString = string.substring(0, 1).toUpperCase() + string.substring(1);
        return ofiString;
    }
    public void limpiarDatos(){
        etNombre.setText("");
        etAPaterno.setText("");
        etAMaterno.setText("");
        etCorreo.setText("");
        etNumero.setText("");
        rbtnHombre.setChecked(false);
        rbtnMujer.setChecked(false);
        etContraseña.setText("");
        etConfirmarContraseña.setText("");
    }
    public void regresar(View v){
        Intent intent = new Intent(this,com.haku.molar.controller.patient.controller_patient_MenuPaciente.class);
        startActivity(intent);
        finish();
    }
}