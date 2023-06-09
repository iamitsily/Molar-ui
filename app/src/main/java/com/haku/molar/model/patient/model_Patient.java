package com.haku.molar.model.patient;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_ajustesPaciente;
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_menuAsistente;
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_registrarPaciente;
import com.haku.molar.controller.doctor.interfaces.Callback_doctor_menuDoctor;
import com.haku.molar.controller.patient.interfaces.Callback_patient_ajustesPaciente;
import com.haku.molar.controller.patient.interfaces.Callback_patient_menu;
import com.haku.molar.model.cita.model_cita;
import com.haku.molar.utils.MolarConfig;
import com.haku.molar.utils.MolarMail;
import com.haku.molar.utils.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class model_Patient {
    int matricula, rol, sexo;
    String[] res = new String[4];
    String nombre, apellidoPaterno, apellidoMaterno, email, telefono, password, passwordNoCrypt;
    Context context;
    private Callback_patient_menu callback_patient_menu;
    private Callback_patient_ajustesPaciente callback_patient_ajustesPaciente;
    private Callback_doctor_menuDoctor callback_doctor_menuDoctor;
    private Callback_assistant_registrarPaciente callback_assistant_registrarPaciente;
    MolarConfig molarConfig = new MolarConfig();
    //Constructores
    public model_Patient() {
    }
    public model_Patient(int matricula, Context context, Callback_patient_menu callback_patient_menu) {
        this.matricula = matricula;
        this.context = context;
        this.callback_patient_menu = callback_patient_menu;
    }

    public model_Patient(int matricula, Context context, Callback_patient_ajustesPaciente callback_patient_ajustesPaciente) {
        this.matricula = matricula;
        this.context = context;
        this.callback_patient_ajustesPaciente = callback_patient_ajustesPaciente;
    }

    public model_Patient(int matricula, String email, String telefono, String password, Context context, Callback_patient_ajustesPaciente callback_patient_ajustesPaciente) {
        this.matricula = matricula;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.context = context;
        this.callback_patient_ajustesPaciente = callback_patient_ajustesPaciente;
    }
    //controller_doctor_menuDoctor

    public model_Patient(Context context, Callback_doctor_menuDoctor callback_doctor_menuDoctor) {
        this.context = context;
        this.callback_doctor_menuDoctor = callback_doctor_menuDoctor;
    }
    //Controller_assitant_registrarPaciente -> validacionesEmailTelefono

    public model_Patient(Context context, Callback_assistant_registrarPaciente callback_assistant_registrarPaciente) {
        this.context = context;
        this.callback_assistant_registrarPaciente = callback_assistant_registrarPaciente;
    }

    //Funciones
    //controller_patient_ajusteCuentaDatos
    public void updateIcon(String opc){
        String url = molarConfig.getDomainAzure()+"/patient/service_editarSexo.php";
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Actualizando foto");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Modificacion exitosa")){
                    callback_patient_ajustesPaciente.onSuccessUpdateIcon();
                    progressDialog.dismiss();
                }else{
                    callback_patient_ajustesPaciente.onErrorUpdateIcon("Error al actualizar la foto de perfil");
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_general_usuario -> udpatebyUser -> onErrorResponse: "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",String.valueOf(matricula));
                params.put("sexo",opc);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void buscarDatos(){
        String url = molarConfig.getDomainAzure()+"/patient/service_seleccionPaciente.php";

        String matricula = String.valueOf(getMatricula());
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Obteniendo datos");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        System.out.println("model_general_usuario -> buscarDatos -> datos vacío");
                        callback_patient_ajustesPaciente.onErrorbuscarDatos("Datos no encontrados");
                        progressDialog.dismiss();
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String matricula = object.getString("matricula");
                            String email = object.getString("email");
                            String telefono = object.getString("telefono");
                            String password = object.getString("password");
                            String[] res = {matricula, email, telefono, password};
                            progressDialog.dismiss();
                            callback_patient_ajustesPaciente.onSuccessbuscarDatos(res);
                        }
                    }
                }catch (JSONException e){
                    System.out.println("model_general_usuario -> buscarDatos -> JSONException: "+e);
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_general_usuario -> buscarDatos -> onErrorResponse: "+error.getMessage());
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",matricula);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void obtenerPass(){
        String url = "https://molarservices.azurewebsites.net/general/login.php";

        String matricula = String.valueOf(getMatricula());
        StringRequest request = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        callback_patient_ajustesPaciente.onErrorObternerPass("Verificar Usuario");
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String matricula = object.getString("matricula");
                            String nombre = object.getString("nombre");
                            String password = object.getString("password");
                            String rol = object.getString("rol");
                            String[] res = {matricula, nombre, password, rol};
                            callback_patient_ajustesPaciente.onSuccessObternerPass(res);
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_general_usuario -> login -> onErrorResponse: "+error.getMessage());
                if (error==null){
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",matricula);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }
    public void udpatebyUser(){
        System.out.println(matricula);
        System.out.println(email);
        System.out.println(telefono);
        System.out.println(password);
        String url = molarConfig.getDomainAzure()+"/patient/service_modificacionPaciente.php";
        //String url = "https://molarservices.azurewebsites.net/general/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Modificacion exitosa")){
                    callback_patient_ajustesPaciente.onSuccessUpdatebyUser();
                }else{
                    callback_patient_ajustesPaciente.onErrorudpatebyUser("Error al actualizar la cuenta");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_general_usuario -> udpatebyUser -> onErrorResponse: "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",String.valueOf(matricula));
                params.put("email",email);
                params.put("telefono",telefono);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    //controller_patient_menupaciente
    public void listarCitas(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando Menu");
        progressDialog.show();

        String url = molarConfig.getDomainAzure()+"/citas/service_listarCitasMenuPaciente.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length()==0){
                        callback_patient_menu.OneErrorlistarCitas("No hay citas agendadas para mostrar en el menu");
                        progressDialog.dismiss();
                    }else if (exito.equals("1")){
                        ArrayList<model_cita> citasPaciente = new ArrayList<>();
                        for (int i=0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            citasPaciente.add(new model_cita(
                                    object.getString("id"),
                                    object.getString("dia"),
                                    object.getString("hora"),
                                    object.getString("motivo"),
                                    "1",
                                    object.getString("nombre"),
                                    object.getString("apellidoPaterno")
                            ));
                        }
                        callback_patient_menu.OnSuccesslistarCitas(citasPaciente);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    System.out.println("model_general_usuario -> listarCitas -> JSONException: "+e);
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_general_usuario -> login -> onErrorResponse: "+error.getMessage());
                if (error==null){
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",String.valueOf(matricula));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    //controller_doctor_menuDoctor
    public void listarCitasMenuDoctor(String matriculaString){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando Menu");
        progressDialog.show();

        String url = molarConfig.getDomainAzure()+"/citas/service_listarCitasDoctor.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length()==0){
                        callback_doctor_menuDoctor.onErrorListar("No hay citas agendadas para mostrar en el menu");
                        progressDialog.dismiss();
                    }else if (exito.equals("1")){
                        ArrayList<model_cita> citasPaciente = new ArrayList<>();
                        for (int i=0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            citasPaciente.add(new model_cita(
                                    object.getString("id"),
                                    object.getString("dia"),
                                    object.getString("hora"),
                                    object.getString("motivo"),
                                    "1",
                                    object.getString("nombre"),
                                    object.getString("apellidoPaterno")
                            ));
                        }
                        callback_doctor_menuDoctor.onSuccesListar(citasPaciente);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    System.out.println("model_general_usuario -> listarCitas -> JSONException: "+e);
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_general_usuario -> login -> onErrorResponse: "+error.getMessage());
                if (error==null){
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",matriculaString);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void comprobarEmail(String email){
        String url = molarConfig.getDomainAzure()+"/patient/service_comprobarEmail.php";
        StringRequest request = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        //No hay datos
                        callback_assistant_registrarPaciente.onSuccessComprobarEmail();
                        //callback_assistant_registrarPaciente.onErrorComprobarTelefono("Este email ya esta vinculado a una cuenta.");
                    }else{
                        if (exito.equals("1")){
                        //Hay datos
                            //callback_assistant_registrarPaciente.onSuccessComprobarTelefono();
                            callback_assistant_registrarPaciente.onErrorComprobarEmail("Este email ya esta vinculado a una cuenta.");

                        }
                    }
                }catch (JSONException e){
                    System.out.println("model_general_usuario -> login -> JSONException: "+e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_general_usuario -> login -> onErrorResponse: "+error.getMessage());
                if (error==null){
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }
    public void comprobarTelefono(String telefono){
        String url = molarConfig.getDomainAzure()+"/patient/service_comprobarTelefono.php";
        StringRequest request = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        //No hay datos
                        callback_assistant_registrarPaciente.onSuccessComprobarTelefono();
                        //callback_assistant_registrarPaciente.onErrorComprobarEmail("Este numero de telefono ya esta vinculado a una cuenta.");
                    }else{
                        if (exito.equals("1")){
                            //Hay datos
                            //callback_assistant_registrarPaciente.onSuccessComprobarEmail();
                            callback_assistant_registrarPaciente.onErrorComprobarTelefono("Este numero de telefono ya esta vinculado a una cuenta.");
                        }
                    }
                }catch (JSONException e){
                    System.out.println("model_general_usuario -> login -> JSONException: "+e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_general_usuario -> login -> onErrorResponse: "+error.getMessage());
                if (error==null){
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                }
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("telefono",telefono);
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