package com.example.uniaovoluntaria.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uniaovoluntaria.R;
import com.example.uniaovoluntaria.api.Auxiliares;
import com.example.uniaovoluntaria.dao.UsuarioDao;
import com.example.uniaovoluntaria.model.UsuarioModel;
import com.example.uniaovoluntaria.model.UsuarioSession;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;

public class Editar extends AppCompatActivity {
    EditText txtNome, txtInfos;
    Button btnSalvar, btnExcluir, selecionar;
    ImageView ft;

    UsuarioModel usuarioEditar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        txtNome = findViewById(R.id.txtNome);
        txtInfos = findViewById(R.id.txtInfos);
        btnSalvar = findViewById(R.id.btnAlterar);
        btnExcluir = findViewById(R.id.btnExcluir);
        ft = findViewById(R.id.FT); // Corrigido para "FT"
        selecionar = findViewById(R.id.selecionarFT); // Corrigido para "selecionarFT"

        carregaBundle();

        // Configurar o botão de salvar
        btnSalvar.setOnClickListener(view -> {
            try {
                salvar();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar o botão de excluir
        btnExcluir.setOnClickListener(view -> {
            try {
                excluir();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Erro ao excluir: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar o botão para selecionar imagem
        selecionar.setOnClickListener(view -> {
            // Aqui você pode adicionar a lógica para selecionar a imagem
            // Como exemplo, deixamos a imagem padrão na ImageView
            // Você pode implementar o código para abrir uma galeria ou câmera
        });
    }

    private void carregaBundle() {
        // Obter o usuário da sessão
        usuarioEditar = UsuarioSession.getInstance().getUsuario();
        if (usuarioEditar != null) {
            mostrar(usuarioEditar); // Mostra os dados do usuário
        } else {
            Auxiliares.alert(getApplicationContext(), "Usuário não encontrado");
        }
    }

    private void mostrar(UsuarioModel e) {
        txtNome.setText(e.getNome());
        txtInfos.setText(e.getInfos());
    }

    private void salvar() throws Exception {
        UsuarioModel e;

        if (usuarioEditar != null) {
            e = usuarioEditar;
        } else {
            e = new UsuarioModel();
        }

        e.setNome(txtNome.getText().toString());
        e.setInfos(txtInfos.getText().toString());

        // Obter a imagem da ImageView e convertê-la em um array de bytes
        Bitmap bitmap = ((BitmapDrawable) ft.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // ou JPEG
        e.setFotoPerfil(stream.toByteArray()); // Define a foto de perfil como byte array

        UsuarioDao dao = new UsuarioDao();
        dao.alterar(e); // Atualiza o usuário com a foto de perfil

        Intent intent = new Intent(Editar.this, Eventos.class);
        startActivity(intent);
    }

    private void excluir() throws Exception {
        if (usuarioEditar != null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Remover");
            alert.setMessage("Deseja realmente excluir?");
            alert.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    UsuarioDao dao = new UsuarioDao();
                    try {
                        dao.excluir(usuarioEditar);
                        Toast.makeText(getApplicationContext(), "Usuário excluído com sucesso", Toast.LENGTH_SHORT).show();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    dialogInterface.dismiss();

                    // Redireciona para a Activity de Eventos após a exclusão
                    Intent intent = new Intent(Editar.this, Eventos.class);
                    startActivity(intent);
                }
            });
            alert.setNegativeButton("não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            alert.show();
        }
    }
}
