package com.haku.molar.model.assistant;

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
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_menuAsistente;
import com.haku.molar.utils.MolarConfig;
import com.haku.molar.utils.MolarMail;
import com.haku.molar.utils.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class model_Assistant {
    int matricula, rol, sexo;
    String nombre, apellidoPaterno, apellidoMaterno, email, telefono, password, passwordNoCrypt, status;
    Context context;
    private Callback_assistant_ajustesPaciente callback_assistant_ajustesPaciente;
    private Callback_assistant_menuAsistente callback_assistant_menuAsistente;
    private Callback_assistant_buscarPacienteLista callback_assistant_buscarPacienteLista;
    MolarConfig molarConfig = new MolarConfig();

    //controller_assistant_ajustesMenuDatos
    public model_Assistant(int matricula, Context context, Callback_assistant_ajustesPaciente callback_assistant_ajustesPaciente) {
        this.matricula = matricula;
        this.context = context;
        this.callback_assistant_ajustesPaciente = callback_assistant_ajustesPaciente;
    }
    //controller_assistant_ajustesMenuDatos -> actualizarUsuario
    public model_Assistant(int matricula, String email, String telefono, String password, Context context, Callback_assistant_ajustesPaciente callback_assistant_ajustesPaciente) {
        this.matricula = matricula;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.context = context;
        this.callback_assistant_ajustesPaciente = callback_assistant_ajustesPaciente;
    }
    //controller_assistant_MenuAsistente
    public model_Assistant(Context context, Callback_assistant_menuAsistente callback_assistant_menuAsistente) {
        this.context = context;
        this.callback_assistant_menuAsistente = callback_assistant_menuAsistente;
    }
    //controller_Assistant_registrarPaciente
    public model_Assistant(int matricula, int rol, int sexo, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono, String password, Context context, String ContraseñaNoCrypt) {
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
        this.passwordNoCrypt = ContraseñaNoCrypt;
    }
    //controller_assistant_buscarPacienteLista
    public model_Assistant(Context context, Callback_assistant_buscarPacienteLista callback_assistant_buscarPacienteLista) {
        this.context = context;
        this.callback_assistant_buscarPacienteLista = callback_assistant_buscarPacienteLista;
    }
    //controller_assistant_buscarPacienteLista -> ArrayList -> modelAssistant
    public model_Assistant(int matricula, int rol, int sexo, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono, String password, String status) {
        this.matricula = matricula;
        this.rol = rol;
        this.sexo = sexo;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.password = password;
        this.status = status;
    }

    //controller_assistant_ajusteMenuDatos
    public void updateIconAssistant(String opc){
        String url = molarConfig.getDomainAzure()+"/patient/service_editarSexo.php";
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Actualizando foto");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Modificacion exitosa")){
                    callback_assistant_ajustesPaciente.onSuccessUpdateIcon();
                    progressDialog.dismiss();
                }else{
                    callback_assistant_ajustesPaciente.onErrorUpdateIcon("Error al actualizar la foto de perfil");
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
    public void buscarDatosAssistant(){
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
                        callback_assistant_ajustesPaciente.onErrorbuscarDatos("Datos no encontrados");
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
                            callback_assistant_ajustesPaciente.onSuccessbuscarDatos(res);
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
    public void obtenerPassAssistant(){
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
                        callback_assistant_ajustesPaciente.onErrorObternerPass("Verificar Usuario");
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String matricula = object.getString("matricula");
                            String nombre = object.getString("nombre");
                            String password = object.getString("password");
                            String rol = object.getString("rol");
                            String[] res = {matricula, nombre, password, rol};
                            callback_assistant_ajustesPaciente.onSuccessObternerPass(res);
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
    public void udpatebyUserAssistant(){
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
                    callback_assistant_ajustesPaciente.onSuccessUpdatebyUser();
                }else{
                    callback_assistant_ajustesPaciente.onErrorudpatebyUser("Error al actualizar la cuenta");
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
    //controller_assistant_registrarPaciente
    public void registrarPaciente(){
        //ProgressDialog progressDialog = new ProgressDialog(context);
        //progressDialog.setMessage("Registrando");
        //progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/patient/service_registrarPaciente.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registro exitoso")) {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    boolean isConnectedToWifi = Network.isConnectedToWifi(context);
                    boolean isConnectedToMobileData = Network.isConnectedToMobileData(context);
                    if (isConnectedToWifi) {
                        MolarMail molarMail = new MolarMail(email,passwordNoCrypt,String.valueOf(matricula),nombre,context);
                        molarMail.sendMailRegistro();
                        //progressDialog.dismiss();
                    } else if (isConnectedToMobileData) {
                        MolarMail molarMail = new MolarMail(email,passwordNoCrypt,String.valueOf(matricula),nombre,context);
                        molarMail.sendMailRegistroRedMovil();
                        //progressDialog.dismiss();
                    } else {
                        Toast.makeText(context, "No es posible enviar email, no hay conexión", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "No se puede registrar", Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();
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
    //controller_assistant_buscarPacienteLista
    public void listaPacientes(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Listando pacientes");
        progressDialog.show();

        String url = molarConfig.getDomainAzure()+"/patient/service_seleccionPacientes.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length()==0){
                        callback_assistant_buscarPacienteLista.onErrorLista("No hay pacientes registrados");
                        progressDialog.dismiss();
                    }else if (exito.equals("1")){
                        ArrayList<model_Assistant> listaPacientes = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            listaPacientes.add(new model_Assistant(Integer.parseInt(object.getString("matricula")),Integer.parseInt(object.getString("rol")),Integer.parseInt(object.getString("sexo")), object.getString("nombre"),object.getString("apellidoPaterno"),object.getString("apellidoMaterno"),object.getString("email"),object.getString("telefono"),object.getString("password"),object.getString("status")));
                        }
                        callback_assistant_buscarPacienteLista.onSuccessLista(listaPacientes);
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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void contarUserMed(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando Menu");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/patient/service_contarPacientes.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        callback_assistant_menuAsistente.onErrorContrarMenu("");
                        progressDialog.dismiss();
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String numUsuario = object.getString("NumUsuario");
                            object = jsonArray.getJSONObject(1);
                            String numMedico = object.getString("NumMedico");
                            callback_assistant_menuAsistente.onSuccessContarMenu(numUsuario,numMedico);
                            progressDialog.dismiss();
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("model_Patient -> obtenerMedico -> JSONException: "+e);
                    e.printStackTrace();
                    callback_assistant_menuAsistente.onErrorContrarMenu(e.getMessage());
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                {
                    System.out.println("model_general_usuario -> login -> onErrorResponse: "+error.getMessage());
                    if (error==null){
                        Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "No hay conexión con el servidor, intente mas tarde", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
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

    public String getPasswordNoCrypt() {
        return passwordNoCrypt;
    }

    public void setPasswordNoCrypt(String passwordNoCrypt) {
        this.passwordNoCrypt = passwordNoCrypt;
    }
}
