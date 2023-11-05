package com.haku.molar.model.doctor;

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
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_buscarPacienteLista;
import com.haku.molar.controller.doctor.interfaces.Callback_doctor_ajustesDoctor;
import com.haku.molar.controller.doctor.interfaces.Callback_doctor_listaPaciente;
import com.haku.molar.model.assistant.model_Assistant;
import com.haku.molar.utils.MolarConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class model_Doctor {
    String matricula, nombre, apellidoPaterno, appelidoMaterno, email, sexo, telefono, password;
    Context context;
    private Callback_doctor_listaPaciente callback_doctor_listaPaciente;
    private Callback_doctor_ajustesDoctor callback_doctor_ajustesDoctor;
    MolarConfig molarConfig = new MolarConfig();

    //controller_doctor_buscarPacienteLista
    public model_Doctor(Context context, Callback_doctor_listaPaciente callback_doctor_listaPaciente) {
        this.context = context;
        this.callback_doctor_listaPaciente = callback_doctor_listaPaciente;
    }
    //controller_doctor_buscarPacienteLista -> ArrayList -> modelAssistant
    public model_Doctor(String matricula, String nombre, String apellidoPaterno, String appelidoMaterno, String email, String sexo) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.appelidoMaterno = appelidoMaterno;
        this.email = email;
        this.sexo = sexo;
    }
    //controller_doctor_ajustesDatos
    public model_Doctor(String matricula, Context context, Callback_doctor_ajustesDoctor callback_doctor_ajustesDoctor) {
        this.matricula = matricula;
        this.context = context;
        this.callback_doctor_ajustesDoctor = callback_doctor_ajustesDoctor;
    }
    //controller_assistant_ajustesMenuDatos -> actualizarUsuario
    public model_Doctor(String matricula, String email, String telefono, String password, Context context,  Callback_doctor_ajustesDoctor callback_doctor_ajustesDoctor) {
        this.matricula = matricula;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.context = context;
        this.callback_doctor_ajustesDoctor = callback_doctor_ajustesDoctor;
    }
    //Funciones
    //controller_doctor_menu
    public void listaPacientes(String matriculaString){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Listando pacientes");
        progressDialog.show();

        String url = molarConfig.getDomainAzure()+"/patient/service_listarPacientesDoctor.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length()==0){
                        callback_doctor_listaPaciente.onErrorLista("No hay pacientes registrados");
                        progressDialog.dismiss();
                    }else if (exito.equals("1")){
                        ArrayList<model_Doctor> listaPacientes = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            listaPacientes.add(new model_Doctor(object.getString("matricula"),
                                    object.getString("nombre"),
                                    object.getString("apellidoPaterno"),
                                    object.getString("apellidoMaterno"),
                                    object.getString("email"),
                                    object.getString("sexo")));
                        }
                        callback_doctor_listaPaciente.onSuccessLista(listaPacientes);
                        progressDialog.dismiss();
                    }
                }catch (JSONException e){
                    System.out.println("model_assistant -> listaPacientes -> JSONException: "+e.getMessage());
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_assistant -> listaPacientes -> onErrorResponse: "+error.getMessage());
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
    //controller_doctor_ajustesDatos
    public void updateIconDoctor(String opc){
        String url = molarConfig.getDomainAzure()+"/doctor/service_editarSexo.php";
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Actualizando foto");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Modificacion exitosa")){
                    callback_doctor_ajustesDoctor.onSuccessUpdateIcon();
                    progressDialog.dismiss();
                }else{
                    callback_doctor_ajustesDoctor.onErrorUpdateIcon("Error al actualizar la foto de perfil");
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
                params.put("matricula",matricula);
                params.put("sexo",String.valueOf(opc));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void buscarDatosDoctor(){
        String url = molarConfig.getDomainAzure()+"/doctor/service_seleccionDoctor.php";

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
                        callback_doctor_ajustesDoctor.onErrorbuscarDatos("Datos no encontrados");
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
                            callback_doctor_ajustesDoctor.onSuccessbuscarDatos(res);
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
    public void obtenerPassDoctor(){
        String url = molarConfig.getDomainAzure()+"/general/login.php";

        StringRequest request = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        callback_doctor_ajustesDoctor.onErrorObternerPass("Verificar Usuario");
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String matricula = object.getString("matricula");
                            String nombre = object.getString("nombre");
                            String password = object.getString("password");
                            String rol = object.getString("rol");
                            String[] res = {matricula, nombre, password, rol};
                            callback_doctor_ajustesDoctor.onSuccessObternerPass(res);
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
    public void udpatebyUserDoctor(){
        String url = molarConfig.getDomainAzure()+"/doctor/service_modificacionDoctor.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Modificacion exitosa")){
                    callback_doctor_ajustesDoctor.onSuccessUpdatebyUser();
                }else{
                    callback_doctor_ajustesDoctor.onErrorudpatebyUser("Error al actualizar la cuenta");
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
                params.put("matricula",matricula);
                params.put("email",email);
                params.put("telefono",telefono);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public String getAppelidoMaterno() {
        return appelidoMaterno;
    }

    public void setAppelidoMaterno(String appelidoMaterno) {
        this.appelidoMaterno = appelidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
