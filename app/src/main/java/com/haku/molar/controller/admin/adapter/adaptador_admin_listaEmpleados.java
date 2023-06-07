package com.haku.molar.controller.admin.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.haku.molar.R;
import com.haku.molar.controller.assistant.adapter.adaptadorBuscarPacienteLista;
import com.haku.molar.model.admin.model_Admin;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.HashMap;

public class adaptador_admin_listaEmpleados extends RecyclerView.Adapter<adaptador_admin_listaEmpleados.ViewHolder> {
    ArrayList<model_Admin>model_empleados = new ArrayList<>();
    Context context;
    private ItemClickListener itemClickListener;

    public adaptador_admin_listaEmpleados(ArrayList<model_Admin> model_empleados, Context context, ItemClickListener itemClickListener) {
        this.model_empleados = model_empleados;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public adaptador_admin_listaEmpleados.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assistant_buscarpacientelista,parent,false);
        return new adaptador_admin_listaEmpleados.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptador_admin_listaEmpleados.ViewHolder holder, int position) {
        holder.nombre.setText(model_empleados.get(position).getNombre());
        holder.apellido.setText(model_empleados.get(position).getApellidoPaterno()+" "+model_empleados.get(position).getApellidoMaterno());
        holder.email.setText(model_empleados.get(position).getEmail());
        if (model_empleados.get(position).getRol().equals("2")){
            holder.matricula.setText(String.valueOf(model_empleados.get(position).getMatricula())+" | Doctor");

        }else{
            holder.matricula.setText(String.valueOf(model_empleados.get(position).getMatricula())+" | Asistente");

        }
        holder.constraintLayout.setOnClickListener(view -> {
            itemClickListener.OnItemClick(model_empleados.get(position));
        });
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

        Pair<Integer, ImageView.ScaleType> opcionSexo = mapaSexo.get(String.valueOf(model_empleados.get(position).getSexo()));
        if (opcionSexo == null) {
            Toast.makeText(context, "Elija una opción válida", Toast.LENGTH_SHORT).show();
        } else {
            holder.fotoPerfil.setScaleType(opcionSexo.second);
            holder.fotoPerfil.setImageResource(opcionSexo.first);
        }
    }
    public interface ItemClickListener{
        void OnItemClick(model_Admin details);
    }
    @Override
    public int getItemCount() {
        return model_empleados.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoPerfil;
        TextView nombre, apellido, email, matricula;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoPerfil = itemView.findViewById(R.id.buscarPacienteLista_ivPerfil);
            nombre = itemView.findViewById(R.id.buscarPacienteLista_tvNombre);
            apellido = itemView.findViewById(R.id.buscarPacienteLista_apellidos);
            email = itemView.findViewById(R.id.buscarPacienteLista_email);
            matricula = itemView.findViewById(R.id.buscarPacienteLista_matricula);
            constraintLayout = itemView.findViewById(R.id.buscarPacienteLista_itemPaciente);
        }
    }
}
