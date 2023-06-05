package com.haku.molar.controller.assistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.haku.molar.R;
import com.haku.molar.model.cita.model_cita;

import java.util.ArrayList;
import java.util.Calendar;

public class adaptadorListarCitasCancelarDirecto extends RecyclerView.Adapter<adaptadorListarCitasCancelarDirecto.ViewHolder>{
    ArrayList<model_cita> model_citas = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public adaptadorListarCitasCancelarDirecto(ArrayList<model_cita> model_citas, Context context, ItemClickListener itemClickListener) {
        this.model_citas = model_citas;
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public adaptadorListarCitasCancelarDirecto.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_listacitasactivas,parent,false);
        return new adaptadorListarCitasCancelarDirecto.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptadorListarCitasCancelarDirecto.ViewHolder holder, int position) {
        holder.dia.setText(divdirDate(model_citas.get(position).getDia()));
        holder.mes.setText(diaSemana(model_citas.get(position).getDia())+"\n"+model_citas.get(position).getHora());
        holder.hora.setText(model_citas.get(position).getNombrePaciente()+" "+model_citas.get(position).getApellidoPaciente());
        holder.asunto.setText(model_citas.get(position).getMotivo());
        holder.id.setText(model_citas.get(position).getId());
        holder.cita.setOnClickListener(view -> {
            itemClickListener.OnItemClick(model_citas.get(position));
        });
    }
    public interface ItemClickListener{
        void OnItemClick(model_cita details);
    }
    @Override
    public int getItemCount() {
        return model_citas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dia, mes, hora, asunto, id;
        CardView cita;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dia = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasDia);
            mes = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasMes);
            hora = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasHora);
            asunto = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasAsunto);
            id = (TextView) itemView.findViewById(R.id.recyclerViewListarCitasActivasidCita);
            cita = (CardView) itemView.findViewById(R.id.cv_itempatient_citasActivas);
        }
    }
    public String diaSemana(String date){
        String diaSemn = "";
        String[] dateParts = date.split("-");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int year = Integer.parseInt(dateParts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month - 1, day);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[]dias = { "Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb" };
        return dias[dayOfWeek -1];
    }
    public String divdirDate(String date){
        String[] dateParts = date.split("-");
        return dateParts[0];
    }
}
