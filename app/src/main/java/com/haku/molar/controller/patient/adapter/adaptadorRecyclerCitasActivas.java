package com.haku.molar.controller.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haku.molar.R;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;

public class adaptadorRecyclerCitasActivas extends RecyclerView.Adapter<adaptadorRecyclerCitasActivas.ViewHolder> {
    ArrayList<model_cita> model_citas = new ArrayList<>();

    public adaptadorRecyclerCitasActivas(ArrayList<model_cita> model_citas) {
        this.model_citas = model_citas;
    }

    @NonNull
    @Override
    public adaptadorRecyclerCitasActivas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_listacitasactivas,parent,false);
        return new adaptadorRecyclerCitasActivas.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorRecyclerCitasActivas.ViewHolder holder, int position) {
        holder.dia.setText(model_citas.get(position).getDia());
        holder.mes.setText(model_citas.get(position).getDia());
        holder.hora.setText(model_citas.get(position).getHora());
        holder.asunto.setText(model_citas.get(position).getMotivo());
        holder.id.setText(model_citas.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return model_citas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dia, mes, hora, asunto, id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dia = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasDia);
            mes = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasMes);
            hora = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasHora);
            asunto = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasAsunto);
            id = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasidCita);
        }
    }
}
