package com.haku.molar.model.cita;

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
import com.haku.molar.controller.assistant.interfaces.Callback_assistant_listarCitasPorCancelar;
import com.haku.molar.controller.patient.interfaces.Callback_patient_cancelarCitas;
import com.haku.molar.controller.patient.interfaces.Callback_patient_detallesCita;
import com.haku.molar.controller.patient.interfaces.Callback_patient_fechaHoraAgendarCita;
import com.haku.molar.controller.patient.interfaces.Callback_patient_historialCitas;
import com.haku.molar.controller.patient.interfaces.Callback_patient_listarCitasActivas;
import com.haku.molar.controller.patient.interfaces.Callback_patient_reagendarCitas;
import com.haku.molar.utils.MolarConfig;
import com.haku.molar.utils.MolarMail;
import com.haku.molar.utils.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class model_cita {
    String id, dia, hora, motivo, estado, status, idUusario, idMedico, descripcion, motivoCancelar, motivoReagendar;
    String idPos,idDoctor, nombreDoctor, apellidoDoctor, nombrePaciente, apellidoPaciente, emailPaciente;
    Context context;
    Callback_patient_listarCitasActivas callback_patient_listarCitasActivas;
    Callback_patient_fechaHoraAgendarCita callback_patient_fechaHoraAgendarCita;
    Callback_patient_historialCitas callback_patient_historialCitas;
    Callback_patient_reagendarCitas callback_patient_reagendarCitas;
    Callback_patient_cancelarCitas callback_patient_cancelarCitas;
    Callback_patient_detallesCita callback_patient_detallesCita;
    Callback_assistant_listarCitasPorCancelar callback_assistant_listarCitasPorCancelar;
    String[] res = new String[6];
    MolarConfig molarConfig = new MolarConfig();
    public model_cita() {
    }

    //controller_patient_fechaHoraAgendarCita
    public model_cita(Context context, Callback_patient_fechaHoraAgendarCita callback_patient_fechaHoraAgendarCita) {
        this.context = context;
        this.callback_patient_fechaHoraAgendarCita = callback_patient_fechaHoraAgendarCita;
    }
    //controller_patient_fechaHoraAgendarCita
    public model_cita(String id, String dia, String hora, String motivo, String estado, String status, String idUusario, String idMedico) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.status = status;
        this.idUusario = idUusario;
        this.idMedico = idMedico;
    }

    public model_cita(String id, String dia, String hora, String motivo, String idDoctor, String nombreDoctor, String apellidoDoctor) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.motivo = motivo;
        this.idDoctor = idDoctor;
        this.nombreDoctor = nombreDoctor;
        this.apellidoDoctor = apellidoDoctor;
    }
    //controller_patient_fechaHoraAgendarCita
    public model_cita(String dia, Context context, Callback_patient_fechaHoraAgendarCita callback_patient_fechaHoraAgendarCita, String idPos) {
        this.dia = dia;
        this.context = context;
        this.callback_patient_fechaHoraAgendarCita = callback_patient_fechaHoraAgendarCita;
        this.idPos = idPos;
    }
    //controller_patient_FechaHoraAgendarCita
    public model_cita(String id, String dia, String hora, String motivo, String estado, String status, String idUusario,String idMedico, Context context, Callback_patient_fechaHoraAgendarCita callback_patient_fechaHoraAgendarCita, String descripcion) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.status = status;
        this.idUusario = idUusario;
        this.context = context;
        this.idMedico = idMedico;
        this.callback_patient_fechaHoraAgendarCita = callback_patient_fechaHoraAgendarCita;
        this.descripcion = descripcion;
    }
    public model_cita(String id, String dia, String hora, String motivo, String estado, String descripcion) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.descripcion = descripcion;
    }
    //controller_patient_HistorialCitasLayout
    public model_cita(String idUusario, Context context, Callback_patient_historialCitas callback_patient_historialCitas) {
        this.idUusario = idUusario;
        this.context = context;
        this.callback_patient_historialCitas = callback_patient_historialCitas;
    }
    //controller_patient_listarCitasActivas
    public model_cita(String idUusario, Context context, Callback_patient_listarCitasActivas callback_patient_listarCitasActivas) {
        this.idUusario = idUusario;
        this.context = context;
        this.callback_patient_listarCitasActivas = callback_patient_listarCitasActivas;
    }
    //controller_patient_reagendar_citas_horaDia -> consultar citas de un dia del calendario
    public model_cita(String dia, Context context, Callback_patient_reagendarCitas callback_patient_reagendarCitas) {
        this.dia = dia;
        this.context = context;
        this.callback_patient_reagendarCitas = callback_patient_reagendarCitas;
    }
    //controller_patient_reagendar_citas_horaDia -> reagendarCita
    public model_cita(String id, String dia, String hora, String estado, String motivo, Context context, Callback_patient_reagendarCitas callback_patient_reagendarCitas) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.estado = estado;
        this.motivo = motivo;
        this.context = context;
        this.callback_patient_reagendarCitas = callback_patient_reagendarCitas;
    }
    //controller_patient_cancelarCitas -> idCita

    public model_cita(String id,String motivo, Context context, Callback_patient_cancelarCitas callback_patient_cancelarCitas) {
        this.id = id;
        this.motivo = motivo;
        this.context = context;
        this.callback_patient_cancelarCitas = callback_patient_cancelarCitas;
    }
    //controller_patient_detallesCitaPaciente

    public model_cita(String id, Context context, Callback_patient_detallesCita callback_patient_detallesCita) {
        this.id = id;
        this.context = context;
        this.callback_patient_detallesCita = callback_patient_detallesCita;
    }
    //controller_assistant_listarCitasPorCancelar
    public model_cita(Context context, Callback_assistant_listarCitasPorCancelar callback_assistant_listarCitasPorCancelar) {
        this.context = context;
        this.callback_assistant_listarCitasPorCancelar = callback_assistant_listarCitasPorCancelar;
    }
    //controller_assistant_listarCitasPorCancelar -> model_cita -> listarCitasPorCancelar
    public model_cita(String id, String dia, String hora, String motivo, String estado, String descripcion, String motivoCancelar, String nombrePaciente, String apellidoPaciente, String emailPaciente) {
        this.id = id;
        this.dia = dia;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.descripcion = descripcion;
        this.motivoCancelar = motivoCancelar;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.emailPaciente = emailPaciente;
    }
    //controller_assistant_listarCitasPorCancelar -> model_cita -> cancelarCita
    public model_cita(String id, String emailPaciente, Context context, Callback_assistant_listarCitasPorCancelar callback_assistant_listarCitasPorCancelar) {
        this.id = id;
        this.emailPaciente = emailPaciente;
        this.context = context;
        this.callback_assistant_listarCitasPorCancelar = callback_assistant_listarCitasPorCancelar;
    }

    public void detallesCita(){

        System.out.println("modelCita: "+idUusario);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando detalles de cita");
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/citas/service_detallesCita.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(jsonArray.length() == 0){
                        System.out.println("model_cita -> detallesCita -> datos vacío");
                        callback_patient_detallesCita.onErrorDetallesCita("No hay datos registrados de esta cita");
                        progressDialog.dismiss();
                    } else if(exito.equals("1")){
                        String datos[] = new String[6];
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        datos[0]= jsonObject1.getString("id");
                        datos[1]= jsonObject1.getString("dia");
                        datos[2]= jsonObject1.getString("hora");
                        datos[3]= jsonObject1.getString("motivo");
                        datos[4]= jsonObject1.getString("nombre");
                        datos[5]= jsonObject1.getString("apellidoPaterno");
                        callback_patient_detallesCita.onSuccessDetallesCita(datos);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    System.out.println("model_general_usuario -> login -> JSONException: "+e);
                    callback_patient_detallesCita.onErrorDetallesCita(e.getMessage());
                    System.out.println("Error");
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: "+error.getMessage());
                progressDialog.dismiss();
                callback_patient_historialCitas.onErrorhoraHistorial("No hay conexión con el servidor");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idCita",id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void horasCita(){

        String url = molarConfig.getDomainAzure()+"/citas/service_citasDia.php";
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Revisando disponibilidad");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (exito.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String hora = object.getString("hora");
                            res[i] = hora;
                        }
                        callback_patient_fechaHoraAgendarCita.onSuccesshoraCitas(res);
                        progressDialog.dismiss();
                    }else{
                        callback_patient_fechaHoraAgendarCita.onErrorhoraCitas("");
                    }
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_cita -> horasCita -> onErrorResponse: "+error.getMessage());
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dia",getDia());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void horasCitaReagendar(){

        String url = molarConfig.getDomainAzure()+"/citas/service_citasDia.php";
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Revisando disponibilidad");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (exito.equals("1")){
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String hora = object.getString("hora");
                            res[i] = hora;
                        }
                        callback_patient_reagendarCitas.onSuccesshoraCitas(res);
                        progressDialog.dismiss();
                    }else{
                        callback_patient_reagendarCitas.onErrorhoraCitas("");
                    }
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_cita -> horasCita -> onErrorResponse: "+error.getMessage());
                progressDialog.dismiss();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dia",getDia());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void reagendarCita(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Reagendando cita");
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/citas/service_reagendarCita.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Modificacion exitosa")) {
                    progressDialog.dismiss();
                    callback_patient_reagendarCitas.onSuccessReagendarCita();
                } else {
                    progressDialog.dismiss();
                    callback_patient_reagendarCitas.onErrorReagendarCita("No se pudo reagendar");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("dia",dia);
                params.put("hora",hora);
                params.put("estado",estado);
                params.put("motivo",motivo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void listarCitasPorCancelar(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Obteniendo solicitudes");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/citas/service_listarCitasPorCancelar.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(jsonArray.length() == 0){
                        System.out.println("model_cita -> historial -> datos vacío");
                        callback_assistant_listarCitasPorCancelar.onErrorListar("No hay solicitudes de cancelación");
                        progressDialog.dismiss();
                    } else if(exito.equals("1")){
                        ArrayList<model_cita> cita = new ArrayList<>();
                        for (int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            cita.add(new model_cita(object.getString("id"),object.getString("dia"),object.getString("hora"),object.getString("motivo"),object.getString("estado"),object.getString("descripcion"),object.getString("motivoCancelar"), object.getString("nombre"), object.getString("apellidoPaterno"), object.getString("email")));
                        }
                        callback_assistant_listarCitasPorCancelar.onSuccessListar(cita);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    System.out.println("model_general_usuario -> listarCitasActivas -> JSONException: "+e);
                    callback_assistant_listarCitasPorCancelar.onErrorListar(e.getMessage());
                    System.out.println("Error");
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void pendienteCancelarCita(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cancelando cita");
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/citas/service_cancelarCita.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Modificacion exitosa")) {
                    progressDialog.dismiss();
                    callback_patient_cancelarCitas.onSuccessReagendarCita();
                } else {
                    progressDialog.dismiss();
                    callback_patient_cancelarCitas.onErrorReagendarCita("No se pudo cancelar");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("estado","4");
                params.put("motivo",motivo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void cancelarCita(String idCitaString, String motivoString,String diaString){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cancelando cita");
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/citas/service_cancelarCita.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Modificacion exitosa")) {
                    boolean isConnectedToWifi = Network.isConnectedToWifi(context);
                    boolean isConnectedToMobileData = Network.isConnectedToMobileData(context);
                    if (isConnectedToWifi){
                        MolarMail molarMail = new MolarMail(emailPaciente,context);
                        molarMail.mailCancelarCita(idCitaString, motivoString,diaString);
                    }else if (isConnectedToMobileData){
                        MolarMail molarMail = new MolarMail(emailPaciente,context);
                        molarMail.mailCancelarCitaRedMovil(idCitaString, motivoString,diaString);
                    }else{
                        Toast.makeText(context, "No es posible enviar email, no hay conexión", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    callback_assistant_listarCitasPorCancelar.onSuccessCancelar();
                } else {
                    progressDialog.dismiss();
                    callback_assistant_listarCitasPorCancelar.onErrorCancelar("No se pudo cancelar la cita");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("estado","2");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void listarCitas(){
        System.out.println("modelCita: "+idUusario);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando historial");
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/citas/service_listarCitasPacienteTodas.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(jsonArray.length() == 0){
                        System.out.println("model_cita -> historial -> datos vacío");
                        callback_patient_historialCitas.onErrorhoraHistorial("Historial vacio, no se han encontrado registros");
                        progressDialog.dismiss();
                    } else if(exito.equals("1")){
                        ArrayList<model_cita> cita = new ArrayList<>();
                        System.out.println(jsonArray.length());
                        for (int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            cita.add(new model_cita(object.getString("id"),object.getString("dia"),object.getString("hora"),object.getString("motivo"),object.getString("estado"),object.getString("descripcion")));
                        }

                        callback_patient_historialCitas.onSuccessHistorial(cita);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    System.out.println("model_general_usuario -> login -> JSONException: "+e);
                    callback_patient_historialCitas.onErrorhoraHistorial(e.getMessage());
                    System.out.println("Error");
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error: "+error.getMessage());
                progressDialog.dismiss();
                callback_patient_historialCitas.onErrorhoraHistorial("No hay conexión con el servidor");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",idUusario);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void listarCitasActivas(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Obteniendo citas activas");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/citas/service_listarCitasPaciente.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito =jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");

                    if(jsonArray.length() == 0){
                        System.out.println("model_cita -> historial -> datos vacío");
                        Toast.makeText(context, "Historial vacio, no se han encontrado registros", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    } else if(exito.equals("1")){
                        ArrayList<model_cita> cita = new ArrayList<>();
                        System.out.println(jsonArray.length());
                        for (int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            cita.add(new model_cita(object.getString("id"),object.getString("dia"),object.getString("hora"),object.getString("motivo"),object.getString("estado"),object.getString("descripcion")));
                        }

                        callback_patient_listarCitasActivas.onSuccessListarCitasActivas(cita);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    System.out.println("model_general_usuario -> listarCitasActivas -> JSONException: "+e);
                    callback_patient_listarCitasActivas.onErrorListarCitasActivas(e.getMessage());
                    System.out.println("Error");
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("matricula",idUusario);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void agendarCita(){
        System.out.println("modelCita: "+id + " "+dia+ " "+hora+ " "+motivo+ " "+estado+ " "+status+ " "+idUusario+" "+idMedico+" "+descripcion);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Agendando cita");
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/citas/service_crearcitasPaciente.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registro exitoso")) {
                    progressDialog.dismiss();
                    callback_patient_fechaHoraAgendarCita.onSuccessAgendarCita();
                } else {
                    Toast.makeText(context, "No se puede registrar", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    callback_patient_fechaHoraAgendarCita.onErrorAgendarCita("No se puede registrar");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error: "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("dia",dia);
                params.put("hora",hora);
                params.put("motivo",motivo);
                params.put("estado",estado);
                params.put("status",status);
                params.put("idUsuario",idUusario);
                params.put("idMedico",idMedico);
                params.put("descripcion",descripcion);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void obtenerNumDoctor(){
        String url = molarConfig.getDomainAzure()+"/doctor/service_contarDoctor.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        callback_patient_fechaHoraAgendarCita.onErrorNumDoctor("No hay medicos");
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String num = object.getString("Num");
                            System.out.println("CountDoctor: "+num);
                            callback_patient_fechaHoraAgendarCita.onSuccessNumDoctor(num);
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("model_Patient -> obtenerMedico -> JSONException: "+e);
                    e.printStackTrace();
                    callback_patient_fechaHoraAgendarCita.onErrorNumDoctor(e.getMessage());
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
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void disponibilidadDoctor(){
        String url = molarConfig.getDomainAzure()+"/doctor/service_disponibilidad.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    if (exito.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("datos");
                        JSONObject object = jsonArray.getJSONObject(0);
                        String matricula = object.getString("matricula");
                        callback_patient_fechaHoraAgendarCita.onSuccessdisponibilidadDoctor(matricula);
                    }else{
                        System.out.println("No hay exito en disponibilidadDoctor");
                        callback_patient_fechaHoraAgendarCita.onErrordisponibilidadDoctor("");
                    }
                }catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("model_cita -> horasCita -> onErrorResponse: "+error.getMessage());
                callback_patient_fechaHoraAgendarCita.onErrordisponibilidadDoctor(error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                System.out.println("idPos enviado: "+idPos);
                params.put("idPos",idPos);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdUusario() {
        return idUusario;
    }

    public void setIdUusario(String idUusario) {
        this.idUusario = idUusario;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(String idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getNombreDoctor() {
        return nombreDoctor;
    }

    public void setNombreDoctor(String nombreDoctor) {
        this.nombreDoctor = nombreDoctor;
    }

    public String getApellidoDoctor() {
        return apellidoDoctor;
    }

    public void setApellidoDoctor(String apellidoDoctor) {
        this.apellidoDoctor = apellidoDoctor;
    }

    public String getMotivoCancelar() {
        return motivoCancelar;
    }

    public void setMotivoCancelar(String motivoCancelar) {
        this.motivoCancelar = motivoCancelar;
    }

    public String getMotivoReagendar() {
        return motivoReagendar;
    }

    public void setMotivoReagendar(String motivoReagendar) {
        this.motivoReagendar = motivoReagendar;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public String getEmailPaciente() {
        return emailPaciente;
    }

    public void setEmailPaciente(String emailPaciente) {
        this.emailPaciente = emailPaciente;
    }
}
