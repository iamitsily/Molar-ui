package com.haku.molar.controller.assistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.haku.molar.R;
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_ajustesPaciente;
import com.haku.molar.controller.patient.controller_patient_AjustesDeCuentaMenu;
import com.haku.molar.model.assistant.model_Assistant;
import com.haku.molar.model.patient.model_Patient;
import com.haku.molar.utils.MolarCrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class controller_assistant_ajustesMenuDatos extends AppCompatActivity implements Callback_assistant_ajustesPaciente {
    EditText edt_telefono, edt_email, edt_passActual, edt_passNueva, edt_passconfirmed;
    String matricula, nombre, rol, passwordActual,passNueva,passwordConfirmar, email, telefonno,sexo;
    Button btnGuardar;
    ProgressDialog progressDialog;
    ImageView imageViewPerfil, ivCerrarCV, hombre2,hombre3,hombre4,hombre5,mujer2,mujer3,mujer4,mujer5;
    ImageButton ibCambiarPerfil;
    CardView cdIconos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assistant_ajustes_menu_datos);

        //Intent
        Intent intent = getIntent();
        matricula = intent.getStringExtra("matricula");
        nombre = intent.getStringExtra("nombre");
        rol = intent.getStringExtra("rol");
        sexo = intent.getStringExtra("sexo");

        //Cast
        edt_telefono = findViewById(R.id.assistant_menu_ajustesCuentaTelefono);
        edt_email = findViewById(R.id.assistant_menu_ajustesCuentaEmail);
        edt_passNueva = findViewById(R.id.assistant_menu_ajustesCuentaPassword);
        edt_passconfirmed = findViewById(R.id.assistant_menu_ajustesCuentaConfirmedPassword);
        edt_passActual = findViewById(R.id.assistant_menu_ajustesCuentaPasswordActual);
        btnGuardar = findViewById(R.id.assistant_menu_ajustesCuentaGuardarBtn);
        imageViewPerfil = findViewById(R.id.assistant_menu_ajustesCuentaPerfil);
        ibCambiarPerfil = findViewById(R.id.assistant_menu_ibAjusteDatosCambiarPerfil);
        cdIconos = findViewById(R.id.assistant_menu_ajustesCuentaDatosCv);
        ivCerrarCV = findViewById(R.id.assistant_menu_ajustesCuenta_cerrarCV);
        hombre2 = findViewById(R.id.assistant_menu_ivHombreDos);
        hombre3 = findViewById(R.id.assistant_menu_ivHombreTres);
        hombre4 = findViewById(R.id.assistant_menu_ivHombreCuatro);
        hombre5 = findViewById(R.id.assistant_menu_ivHombreCinco);
        mujer2 = findViewById(R.id.assistant_menu_ivMujerDos);
        mujer3 = findViewById(R.id.assistant_menu_ivMujerTres);
        mujer4 = findViewById(R.id.assistant_menu_ivMujerCuatro);
        mujer5 = findViewById(R.id.assistant_menu_ivMujerCinco);

        inicioUI();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarDatos();
            }
        });
        ibCambiarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdIconos.setVisibility(View.VISIBLE);
                ibCambiarPerfil.setEnabled(false);
            }
        });
        ivCerrarCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdIconos.setVisibility(View.INVISIBLE);
                ibCambiarPerfil.setEnabled(true);
            }
        });
        hombre2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("12");
            }
        });
        hombre3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("13");
            }
        });
        hombre4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("14");
            }
        });
        hombre5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("15");
            }
        });
        mujer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("22");
            }
        });
        mujer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("23");
            }
        });
        mujer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("24");
            }
        });
        mujer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateIcon("25");
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aviso").setIcon(R.mipmap.logoapp);
        builder.setMessage("Por favor antes de actualizar sus datos, asegúrese " +
                "de que el email y número de teléfono sean correctos.").setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(true).show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, controller_assistant_ajustesMenu.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        intent.putExtra("rol",rol);
        intent.putExtra("sexo",sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void updateIcon(String opc){
        model_Assistant model_Assistant = new model_Assistant(Integer.parseInt(matricula),this,this);
        HashMap<String, Pair<Integer, ImageView.ScaleType>> mapaOpciones = new HashMap<>();
        mapaOpciones.put("12", new Pair<>(R.mipmap.hombredos, ImageView.ScaleType.CENTER_CROP));
        mapaOpciones.put("13", new Pair<>(R.mipmap.hombretres, ImageView.ScaleType.CENTER_CROP));
        mapaOpciones.put("14", new Pair<>(R.mipmap.hombrecuatro, ImageView.ScaleType.CENTER_CROP));
        mapaOpciones.put("15", new Pair<>(R.mipmap.hombrecinco, ImageView.ScaleType.CENTER_CROP));
        mapaOpciones.put("22", new Pair<>(R.mipmap.mujerdos, ImageView.ScaleType.FIT_CENTER));
        mapaOpciones.put("23", new Pair<>(R.mipmap.mujertres, ImageView.ScaleType.FIT_CENTER));
        mapaOpciones.put("24", new Pair<>(R.mipmap.mujercuatro, ImageView.ScaleType.FIT_CENTER));
        mapaOpciones.put("25", new Pair<>(R.mipmap.mujercinco, ImageView.ScaleType.FIT_CENTER));

        Pair<Integer, ImageView.ScaleType> opcion = mapaOpciones.get(opc);
        if (opcion != null) {
            model_Assistant.updateIconAssistant(opc);
            imageViewPerfil.setScaleType(opcion.second);
            imageViewPerfil.setImageResource(opcion.first);
            sexo = opc;
        } else {
            Toast.makeText(this, "Elija una opción válida", Toast.LENGTH_SHORT).show();
        }
    }
    public void backCuentaMenuBtn(View view){
        Intent intent = new Intent(this, controller_assistant_ajustesMenu.class);
        intent.putExtra("matricula",matricula);
        intent.putExtra("nombre",nombre);
        intent.putExtra("rol",rol);
        intent.putExtra("sexo",sexo);
        startActivity(intent);
        overridePendingTransition(R.anim.menu_patient_slide_in_right, R.anim.menu_patient_slide_out_left);
        finish();
    }
    public void inicioUI(){
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
            imageViewPerfil.setScaleType(opcionSexo.second);
            imageViewPerfil.setImageResource(opcionSexo.first);
        }
        model_Assistant model_Assistant= new model_Assistant(Integer.parseInt(matricula),this,this);
        model_Assistant.buscarDatosAssistant();
    }
    public void actualizarDatos(){
        telefonno = edt_telefono.getText().toString().trim();
        email = edt_email.getText().toString().trim();
        passwordActual = edt_passActual.getText().toString().trim();
        passNueva = edt_passNueva.getText().toString().trim();
        passwordConfirmar = edt_passconfirmed.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Actualizando datos");
        progressDialog.show();
        model_Assistant model_Assistant = new model_Assistant(Integer.parseInt(matricula),this,this);
        model_Assistant.obtenerPassAssistant();
    }
    public void actualizarUsuario(){
        String passCrypt = "";
        try {
            passCrypt = MolarCrypt.encrypt(passNueva);
            model_Assistant model_Assistant = new model_Assistant(Integer.parseInt(matricula),email,telefonno,passCrypt,this,this);
            model_Assistant.udpatebyUserAssistant();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException |
                 InvalidAlgorithmParameterException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("Error al encriptar la contraseña");
        }
    }
    public boolean validarDatos(String pass) {
        boolean error = false;

        if (passwordActual.equals("") || passNueva.equals("") || passwordConfirmar.equals("") || telefonno.equals("") || email.equals("")) {
            //Toast.makeText(this, "Rellena todos los campos ", Toast.LENGTH_SHORT).show();
            error = true;
        }
        if (!pass.equals(passwordActual)) {
            //Toast.makeText(this, "Contraseña actual no coincide", Toast.LENGTH_SHORT).show();
            error = true;
        }
        if (!passNueva.equals(passwordConfirmar)) {
            //Toast.makeText(this, "Coontraseñas no coindiden", Toast.LENGTH_SHORT).show();
            error = true;
        }
        if (!validarEmail(email)) {
            error = true;
            edt_email.setText("");
            edt_email.setHint("Verifique email");
            edt_email.setHintTextColor(Color.RED);
        }
        if(telefonno.length()<10){
            error = true;
            edt_telefono.setText("");
            edt_telefono.setHint("Numero a 10 digitos");
            edt_telefono.setHintTextColor(Color.RED);
        }
        return error;
    }
    public static boolean validarEmail(String email) {
        String regex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    public void onSuccessbuscarDatos(String[] datos) {
        System.out.println("controller_General_Login"+datos[0] + datos[1] + datos[2] + datos[3]);
        edt_email.setText(datos[1]);
        edt_telefono.setText(datos[2]);
    }

    @Override
    public void onErrorbuscarDatos(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessUpdatebyUser() {
        progressDialog.dismiss();
        Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorudpatebyUser(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessObternerPass(String[] datos) {
        try {
            if(!validarDatos(MolarCrypt.decrypt(datos[2]))){
                actualizarUsuario();
            }else{
                Toast.makeText(this, "No es posible realizar las modificaciones, se incumplen criterios", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
        }
    }
    @Override
    public void onErrorObternerPass(String datos) {
        Toast.makeText(this, datos, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessUpdateIcon() {
        cdIconos.setVisibility(View.INVISIBLE);
        ibCambiarPerfil.setEnabled(true);
    }

    @Override
    public void onErrorUpdateIcon(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}