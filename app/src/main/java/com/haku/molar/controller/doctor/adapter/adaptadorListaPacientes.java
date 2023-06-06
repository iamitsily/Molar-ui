package com.haku.molar.controller.doctor.adapter;

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
import com.haku.molar.model.assistant.model_Assistant;
import com.haku.molar.model.doctor.model_Doctor;

import java.util.ArrayList;
import java.util.HashMap;

public class adaptadorListaPacientes extends RecyclerView.Adapter<adaptadorListaPacientes.ViewHolder>{
    ArrayList<model_Doctor> listaPacientes;
    Context context;
    private ItemClickListener itemClickListener;

    public adaptadorListaPacientes(ArrayList<model_Doctor> listaPacientes, Context context, ItemClickListener itemClickListener) {
        this.listaPacientes = listaPacientes;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public adaptadorListaPacientes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assistant_buscarpacientelista,parent,false);
        return new adaptadorListaPacientes.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorListaPacientes.ViewHolder holder, int position) {
        holder.nombre.setText(listaPacientes.get(position).getNombre());
        holder.apellido.setText(listaPacientes.get(position).getApellidoPaterno()+" "+listaPacientes.get(position).getAppelidoMaterno());
        holder.email.setText(listaPacientes.get(position).getEmail());
        holder.matricula.setText(String.valueOf(listaPacientes.get(position).getMatricula()));
        holder.constraintLayout.setOnClickListener(view -> {
            itemClickListener.OnItemClick(listaPacientes.get(position));
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

        Pair<Integer, ImageView.ScaleType> opcionSexo = mapaSexo.get(String.valueOf(listaPacientes.get(position).getSexo()));
        if (opcionSexo == null) {
            Toast.makeText(context, "Elija una opción válida", Toast.LENGTH_SHORT).show();
        } else {
            holder.fotoPerfil.setScaleType(opcionSexo.second);
            holder.fotoPerfil.setImageResource(opcionSexo.first);
        }
    }
    public interface ItemClickListener{
        void OnItemClick(model_Doctor details);
    }
    @Override
    public int getItemCount() {
        return listaPacientes.size();
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
