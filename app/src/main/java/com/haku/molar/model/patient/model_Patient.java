package com.haku.molar.model.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haku.molar.MainActivity;

import java.util.HashMap;
import java.util.Map;

public class model_Patient {
    int matricula, rol, sexo;
    String nombre, apellidoPaterno, apellidoMaterno, email, telefono, password;
    Context context;
    //Constructores
        //Constructor general

    public model_Patient() {
    }

    public model_Patient(int matricula, int rol, int sexo, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono, String password, Context context) {
        this.matricula = matricula;
        this.rol = rol;
        this.sexo = sexo;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.context = context;
    }


    //Funciones
        //Ejemplo - Funcion al back para registrarPaciente
    public void registrarPaciente(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registrando");
        progressDialog.show();
        String url = "https://molarservices.azurewebsites.net/patient/service_registrarPaciente.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registro exitoso")) {
                    Toast.makeText(context, "Datos insertados", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(context, "No se puede registrar", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                System.out.println("model_Patient->registro->getparams: "+String.valueOf(matricula)+apellidoPaterno+apellidoMaterno+email+telefono+rol+sexo+password);
                params.put("matricula",String.valueOf(matricula));
                params.put("nombre",nombre);
                params.put("apellido_paterno",apellidoPaterno);
                params.put("apellido_materno",apellidoMaterno);
                params.put("email",email);
                params.put("telefono",telefono);
                params.put("rol",String.valueOf(rol));
                params.put("sexo",String.valueOf(sexo));
                params.put("password",password);
                params.put("status","1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    //Getters y setters
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
