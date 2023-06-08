package com.haku.molar.controller.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.model.admin.model_Admin;
import com.haku.molar.utils.MolarCrypt;

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

public class controller_admin_RegistrarPaciente extends AppCompatActivity {
    String matriculaMain, nombreMain, rolMain, sexoMain;
    ImageView back;
    private RadioButton rbtnHombre, rbtnMujer;
    private EditText etNombre, etAPaterno, etAMaterno, etCorreo, etNumero, etContraseña, etConfirmarContraseña;
    private String nombre, apaterno, amaterno, correo, numero, contraseña, confirmarContraseña, matricula, contraseñaNoCrypt, nombreString, matriculaString, rolString, sexoString;
    private int rol, sexo;
    Button btnRegistrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_admin_registrar_paciente);

        Intent intent = getIntent();
        matriculaMain = intent.getStringExtra("matricula");
        nombreMain = intent.getStringExtra("nombre");
        rolMain = intent.getStringExtra("rol");
        sexoMain = intent.getStringExtra("sexo");

        back = findViewById(R.id.admin_registroPaciente_back);

        rbtnHombre = findViewById(R.id.admin_registroPaciente_hombre);
        rbtnMujer = findViewById(R.id.admin_registroPaciente_mujer);

        etNombre = findViewById(R.id.admin_registroPaciente_nombre);
        etAPaterno = findViewById(R.id.admin_registroPaciente_aPaterno);
        etAMaterno = findViewById(R.id.admin_registroPaciente_aMaterno);
        etCorreo = findViewById(R.id.admin_registroPaciente_email);
        etNumero = findViewById(R.id.admin_registroPaciente_telefono);
        etContraseña = findViewById(R.id.admin_registroPaciente_password);
        etConfirmarContraseña = findViewById(R.id.admin_registroPaciente_passwordConfirmed);
        btnRegistrar = findViewById(R.id.admin_registroPaciente_btnRegistrar);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), controller_admin_menuUsuario.class);
                intent1.putExtra("matricula",matriculaMain);
                intent1.putExtra("nombre", nombreMain);
                intent1.putExtra("rol", rolMain);
                intent1.putExtra("sexo", sexoMain);
                startActivity(intent1);
                overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
                finish();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrar(view);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(getApplicationContext(), controller_admin_menuUsuario.class);
        intent1.putExtra("matricula",matriculaMain);
        intent1.putExtra("nombre", nombreMain);
        intent1.putExtra("rol", rolMain);
        intent1.putExtra("sexo", sexoMain);
        startActivity(intent1);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
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
                model_Admin model_admin = new model_Admin(matricula,nombre,apaterno,amaterno,correo,numero,contraseña,confirmarContraseña,"1",String.valueOf(sexo),this);
                model_admin.registrarPaciente();
            limpiarDatos();
        }
    }
    private void imprimirDatos(){
        System.out.println("Matricula: "+matricula);
        System.out.println("Nombre: "+nombre);
        System.out.println("Apaterno: "+apaterno);
        System.out.println("Amaterno: "+amaterno);
        System.out.println("Correo: "+correo);
        System.out.println("Numero: "+numero);
        System.out.println("Contraseña: "+contraseña);
        System.out.println("ContraseñaConfirmed: "+confirmarContraseña);
        System.out.println("Rol: "+rol);
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
            etNombre.setHint("Revisar nombre "+nombre);
            etNombre.setHintTextColor(Color.RED);
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
            error = true;
            etConfirmarContraseña.setHintTextColor(Color.RED);
            //Toast.makeText(this, "Llene el campo confirmar contraseña", Toast.LENGTH_SHORT).show();
        }
        if (!contraseña.equals(confirmarContraseña)){
            error=true;
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        if(error == true){
            Toast.makeText(this, "Los datos ingresados no son validos, por favor revisar", Toast.LENGTH_SHORT).show();
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
        etNombre.setHintTextColor(Color.parseColor("#A9A9A9"));
        etAPaterno.setText("");
        etAPaterno.setHint("Apellido paterno");
        etAPaterno.setHintTextColor(Color.parseColor("#A9A9A9"));
        etAMaterno.setText("");
        etAMaterno.setHint("Aplelido materno");
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
        etConfirmarContraseña.setHint("Contraseña");
        etConfirmarContraseña.setHintTextColor(Color.parseColor("#A9A9A9"));
    }
}