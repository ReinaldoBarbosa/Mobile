package com.example.uniaovoluntaria.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.Util;
import com.example.uniaovoluntaria.api.Auxiliares;
import com.example.uniaovoluntaria.dao.UsuarioDao;
import com.example.uniaovoluntaria.model.UsuarioModel;
import com.example.uniaovoluntaria.model.UsuarioSession;

public class ProfileFragment extends Fragment {

    TextView nome, email, telefone, bio;
    Button btnLogout, btn_editar;
    ImageView ftPerfil;
    UsuarioDao usuarioDao;
    UsuarioModel usuario; // Variável de instância para armazenar o usuário logado

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inicializar componentes
        nome = view.findViewById(R.id.textNome);
        email = view.findViewById(R.id.textEmail);
        telefone = view.findViewById(R.id.textNumero);
        bio = view.findViewById(R.id.textBio);
        ftPerfil = view.findViewById(R.id.FtUserPerfil);
        btnLogout = view.findViewById(R.id.btn_logout);
        btn_editar = view.findViewById(R.id.btn_editar);

        usuarioDao = new UsuarioDao();

        // Carregar as informações do usuário
        carregaDadosUsuario();

        // Configurar botão editar para abrir a Activity de edição
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario != null) {
                    Log.d("ProfileFragment", "ID do usuário: " + usuario.getId());
                    Intent intent = new Intent(getActivity(), Editar.class);
                    intent.putExtra("usuario", usuario.getId());
                    startActivity(intent);
                } else {
                    Auxiliares.alert(getContext(), "Erro: Usuário não carregado");
                }
            }
        });

        // Configurar botão de logout
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    private void carregaDadosUsuario() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1); // Obtém o ID do usuário

        if (userId != -1) {
            usuario = usuarioDao.carregaPorId(userId); // Carrega o usuário com base no ID
            if (usuario != null) {
                UsuarioSession.getInstance().setUsuario(usuario); // Armazena o usuário na sessão
                mostrar(usuario); // Atualiza a UI com os dados do usuário
            } else {
                Auxiliares.alert(getContext(), "Usuário não encontrado");
            }
        } else {
            Auxiliares.alert(getContext(), "ID do usuário não encontrado");
        }
    }



    private void mostrar(UsuarioModel usuario) {
        if (usuario != null) {
            nome.setText(usuario.getNome());
            email.setText(usuario.getEmail());
            telefone.setText(usuario.getTelefone());
            bio.setText((usuario.getInfos()));
            if (usuario.getFotoPerfil() != null) {
                // Define a foto de perfil no ImageView
                ftPerfil.setImageBitmap(Util.converterByteToBipmap(usuario.getFotoPerfil()));
            } else {
                // Exibe uma imagem padrão caso o usuário não tenha foto
                if (getContext() != null) {
                    ftPerfil.setImageDrawable(getContext().getResources().getDrawable(R.drawable.wave)); // Substitua pela sua imagem padrão
                }
            }
        }
    }

    private void logout() {
        // Limpar dados do usuário nos SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Limpa todas as entradas
        editor.apply();

        // Navegar de volta para a LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity2.class);
        startActivity(intent);
        getActivity().finish(); // Opcional: termina a MainActivity
    }
}
