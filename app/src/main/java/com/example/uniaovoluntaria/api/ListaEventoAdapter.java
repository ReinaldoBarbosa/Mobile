package com.example.uniaovoluntaria.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.Util;
import com.example.uniaovoluntaria.model.EventoModel;

import java.util.List;

public class ListaEventoAdapter extends RecyclerView.Adapter<ListaEventoAdapter.EventoViewHolder> {

    private List<EventoModel> listaEvento;
    private Fragment context;

    public ListaEventoAdapter(Fragment context, List<EventoModel> listaEvento) {
        this.context = context;
        this.listaEvento = listaEvento;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        EventoModel evento = listaEvento.get(position);
        holder.txtNome.setText(evento.getNome());
        holder.txtInfos.setText(evento.getInfos());

//        String imageUrl = evento.getFotoEvento();
        //Log.d("EventoAdapter", "Loading image from: " + imageUrl);
        if(evento.getFotoEvento()==null)
            holder.imgEvento.setImageDrawable(context.getResources().getDrawable(R.drawable.wave));
        else
            holder.imgEvento.setImageBitmap(Util.converterByteToBipmap(evento.getFotoEvento()));

        // Converte a URL hexadecimal para Base64
//        if (imageUrl != null && imageUrl.startsWith("0x")) {
//            try {
//                imageUrl = evento.convertHexToBase64(imageUrl);
//                Log.d("EventoAdapter", "Converted Base64: " + imageUrl);
//            } catch (IllegalArgumentException e) {
//                Log.e("EventoAdapter", "Error converting hex to Base64: " + e.getMessage());
//                imageUrl = null; // Se falhar na conversão, não carregar imagem
//            }
//        }
//
//        // Tenta carregar a imagem
//        if (imageUrl != null && !imageUrl.isEmpty()) {
//            Glide.with(context)
//                    .load("data:image/png;base64," + imageUrl) // Ajuste a string se necessário
//                    .placeholder(R.drawable.home) // Imagem de carregamento
//                    .error(R.drawable.email) // Imagem de erro
//                    .into(holder.imgEvento);
//        } else {
//            Log.e("EventoAdapter", "Image URL is null or empty.");
//            holder.imgEvento.setImageResource(R.drawable.bg_line); // Imagem padrão em caso de erro
//        }
    }

    @Override
    public int getItemCount() {
        return listaEvento != null ? listaEvento.size() : 0;
    }

    public static class EventoViewHolder extends RecyclerView.ViewHolder {
        TextView txtNome, txtInfos;
        ImageView imgEvento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.textNomeEvento);
            txtInfos = itemView.findViewById(R.id.textInfosEvento);
            imgEvento = itemView.findViewById(R.id.ftEvento);
        }
    }
}
