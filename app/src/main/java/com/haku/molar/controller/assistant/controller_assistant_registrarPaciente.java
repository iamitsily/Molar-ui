package com.haku.molar.controller.assistant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.haku.molar.controller.assistant.interfaces.Callback_assistant_registrarPaciente;
import com.haku.molar.model.assistant.model_Assistant;
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

public class controller_assistant_registrarPaciente extends AppCompatActivity implements Callback_assistant_registrarPaciente {
    private ImageView ibRegresar;
    private RadioButton rbtnHombre, rbtnMujer;
    private EditText etNombre, etAPaterno, etAMaterno, etCorreo, etNumero, etContraseña, etConfirmarContraseña;
    private String nombre, apaterno, amaterno, correo, numero, contraseña, confirmarContraseña, matricula, contraseñaNoCrypt, nombreString, matriculaString, rolString, sexoString;
    private int rol, sexo;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_registrar_paciente);
        Intent intent = getIntent();
        matriculaString = intent.getStringExtra("matricula");
        nombreString = intent.getStringExtra("nombre");
        rolString = intent.getStringExtra("rol");
        sexoString = intent.getStringExtra("sexo");

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando");
        progressDialog.setIcon(R.mipmap.logoapp);

        ibRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),controller_assistant_menuPacientes.class);
                intent.putExtra("matricula",matriculaString);
                intent.putExtra("nombre", nombreString);
                intent.putExtra("rol", rolString);
                intent.putExtra("sexo", sexoString);
                startActivity(intent);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,controller_assistant_menuPacientes.class);
        intent.putExtra("matricula",matriculaString);
        intent.putExtra("nombre", nombreString);
        intent.putExtra("rol", rolString);
        intent.putExtra("sexo", sexoString);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void Registrar(View v){
        obtenerDatos();
        if(validarDatos()){
            System.out.println("Datos incorrectos");
            Toast.makeText(this, "Los datos ingresados no son validos, por favor revisar", Toast.LENGTH_SHORT).show();
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
            progressDialog.show();
            comprobarEmail();
        }
    }
    public void comprobarEmail(){
        model_Patient model_patient = new model_Patient(this,this);
        model_patient.comprobarEmail(correo);
    }
    public void comprobarTelefono(){
        model_Patient model_patient = new model_Patient(this, this);
        model_patient.comprobarTelefono(numero);
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

    }
    private boolean validarDatos(){
        boolean error = false;

        //Revisar el plan de pruebas integrales
        if(nombre.equals("") || !nombre.matches("^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]*$")){
            error = true;
            etNombre.setText("");
            //etNombre.setHint("Revisar nombre "+nombre);
            //etNombre.setHintTextColor(Color.RED);
        }
        if(apaterno.equals("") || !apaterno.matches("^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]*$")){
            error = true;
            etAPaterno.setText("");
            etAPaterno.setHint("Revisar Apaterno "+apaterno);
            etAPaterno.setHintTextColor(Color.RED);
        }
        if(amaterno.equals("") || !amaterno.matches("^[a-zA-ZáÁéÉíÍóÓúÚñÑüÜ\\s]*$")){
            error = true;
            etAMaterno.setText("");
            etAMaterno.setHint("Revisar Amaterno "+amaterno);
            etAMaterno.setHintTextColor(Color.RED);
        }
        if(nombre.length() < 3){
            error = true;
            //Toast.makeText(this, "Nombre mayor o igual a 2 caracteres", Toast.LENGTH_SHORT).show();
        }
        if(apaterno.length() < 4){
            error = true;
            //Toast.makeText(this, "Apaterno mayor o igual a 3 caracteres", Toast.LENGTH_SHORT).show();
        }
        if(amaterno.length() < 4) {
            error = true;
            //Toast.makeText(this, "Amaterno mayor o igual a 3 caracteres", Toast.LENGTH_SHORT).show();
        }
        if (!validarEmail(correo)) {
            error = true;
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
            etContraseña.setHintTextColor(Color.RED);
            //Toast.makeText(this, "Llene el campo contraseña", Toast.LENGTH_SHORT).show();
        }
        if (confirmarContraseña.equals("")){
            error=true;
            etConfirmarContraseña.setHintTextColor(Color.RED);
            //Toast.makeText(this, "Llene el campo confirmar contraseña", Toast.LENGTH_SHORT).show();
        }
        if (!contraseña.equals(confirmarContraseña)){
            error=true;
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
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
        etNombre.setHint("Nombre");
        etContraseña.setHintTextColor(Color.parseColor("#A9A9A9"));
        etAPaterno.setText("");
        etAPaterno.setHint("Apellido Paterno");
        etAPaterno.setHintTextColor(Color.parseColor("#A9A9A9"));
        etAMaterno.setText("");
        etAMaterno.setHint("Apellido Materno");
        etAMaterno.setHintTextColor(Color.parseColor("#A9A9A9"));
        etCorreo.setText("");
        etCorreo.setHint("Correo electrónico");
        etCorreo.setHintTextColor(Color.parseColor("#A9A9A9"));
        etNumero.setText("");
        etNumero.setHint("+52");
        etNumero.setHintTextColor(Color.parseColor("#A9A9A9"));
        rbtnHombre.setChecked(false);
        rbtnMujer.setChecked(false);
        etContraseña.setText("");
        etContraseña.setHint("Contraseña");
        etContraseña.setHintTextColor(Color.parseColor("#A9A9A9"));
        etConfirmarContraseña.setText("");
        etConfirmarContraseña.setHint("Confirmar contraseña");
        etConfirmarContraseña.setHintTextColor(Color.parseColor("#A9A9A9"));
    }
    public void regresar(View v){
        Intent intent = new Intent(this,controller_assistant_menuPacientes.class);
        intent.putExtra("matricula",matriculaString);
        intent.putExtra("nombre", nombreString);
        intent.putExtra("rol", rolString);
        intent.putExtra("sexo", sexoString);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }

    @Override
    public void onSuccessComprobarEmail() {
        System.out.println("onSuccessComprobarEmail");
        comprobarTelefono();
    }

    @Override
    public void onErrorComprobarEmail(String mensaje) {
        System.out.println("onErrorComprobarEmail");
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    @Override
    public void onSuccessComprobarTelefono() {
        System.out.println("onSuccessComprobarTelefono");
        model_Assistant model_Assistant = new model_Assistant(Integer.parseInt(matricula),1,sexo,nombre,apaterno,amaterno,correo,numero,contraseña,this, contraseñaNoCrypt);
        model_Assistant.registrarPaciente();
        progressDialog.dismiss();
        limpiarDatos();
    }

    @Override
    public void onErrorComprobarTelefono(String mensaje) {
        System.out.println("onErrorComprobarTelefono");
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }
}