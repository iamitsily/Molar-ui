package com.haku.molar.model.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.haku.molar.controller.admin.interfaces.Callback_admin_menuAdmin;
import com.haku.molar.utils.MolarConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class model_Admin {
    String matricula, nombre, apellidoPaterno, appelidoMaterno, email, sexo, telefono, password;
    Context context;
    private Callback_admin_menuAdmin callback_admin_menuAdmin;
    MolarConfig molarConfig = new MolarConfig();

    //controller_admin_menuAdmin

    public model_Admin(Context context, Callback_admin_menuAdmin callback_admin_menuAdmin) {
        this.context = context;
        this.callback_admin_menuAdmin = callback_admin_menuAdmin;
    }

    //Funciones
    //controller_admin_menuAdmin
    public void contarUserMed(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando Menu");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = molarConfig.getDomainAzure()+"/patient/service_contarPacienteDoctor.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String exito = jsonObject.getString("exito");
                    JSONArray jsonArray = jsonObject.getJSONArray("datos");
                    if (jsonArray.length() == 0){
                        callback_admin_menuAdmin.onErrorContrarMenu("");
                        progressDialog.dismiss();
                    }else{
                        if (exito.equals("1")){
                            JSONObject object = jsonArray.getJSONObject(0);
                            String numUsuario = object.getString("NumUsuario");
                            object = jsonArray.getJSONObject(1);
                            String numMedico = object.getString("NumMedico");
                            callback_admin_menuAdmin.onSuccessContarMenu(numUsuario,numMedico);
                            progressDialog.dismiss();
                        }
                    }
                } catch (JSONException e) {
                    System.out.println("model_Patient -> obtenerMedico -> JSONException: "+e);
                    e.printStackTrace();
                    callback_admin_menuAdmin.onErrorContrarMenu(e.getMessage());
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

}
