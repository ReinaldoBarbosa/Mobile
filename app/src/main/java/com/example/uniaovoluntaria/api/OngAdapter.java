package com.example.uniaovoluntaria.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.model.UsuarioModel;

import java.util.List;

public class OngAdapter extends RecyclerView.Adapter<OngAdapter.OngViewHolder> {
    private List<UsuarioModel> listaOng; // Renomeado para OngModel
    private Fragment context;

    public OngAdapter(Fragment context, List<UsuarioModel> listaOng) { // Renomeado para OngModel
        this.context = context;
        this.listaOng = listaOng; // Renomeado para OngModel
    }

    @NonNull
    @Override
    public OngViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ong, parent, false); // Supondo que você tenha um layout para a ONG
        return new OngViewHolder(view); // Renomeado para OngViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull OngViewHolder holder, int position) {
        UsuarioModel ong = listaOng.get(position); // Renomeado para OngModel
        holder.txtNome.setText(ong.getNome()); // Renomeado para OngModel

        byte[] imageBytes = ong.getFotoPerfil(); // Obtém o array de bytes da foto

        // Tenta carregar a imagem
        if (imageBytes != null && imageBytes.length > 0) {
            // Converte o array de bytes em um Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            Glide.with(context)
                    .load(bitmap) // Carrega o bitmap
                    .placeholder(R.drawable.home) // Imagem de carregamento
                    .error(R.drawable.email) // Imagem de erro
                    .into(holder.imgEvento);
        } else {
            Log.e("OngAdapter", "Image bytes are null or empty.");
            holder.imgEvento.setImageResource(R.drawable.bg_line); // Imagem padrão em caso de erro
        }
    }


    @Override
    public int getItemCount() {
        return listaOng != null ? listaOng.size() : 0; // Renomeado para OngModel
    }

    public static class OngViewHolder extends RecyclerView.ViewHolder { // Renomeado para OngViewHolder
        TextView txtNome, txtInfos;
        ImageView imgEvento;

        public OngViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.textNomeOng); // Certifique-se de que o ID do TextView esteja correto
            imgEvento = itemView.findViewById(R.id.ftOng); // Certifique-se de que o ID do ImageView esteja correto
        }
    }
}
