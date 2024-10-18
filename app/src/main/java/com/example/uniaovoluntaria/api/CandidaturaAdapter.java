 package com.example.uniaovoluntaria.api;

 import android.content.Context;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.BaseAdapter;

 import com.example.uniaovoluntaria.R;
 import com.example.uniaovoluntaria.model.CandidaturaModel;

 import java.io.Serializable;
 import java.util.List;

public class CandidaturaAdapter extends BaseAdapter implements Serializable {
    private static final long serialVersionUID = 1251583434;

    private List<CandidaturaModel> Candidatura;
    private Context context;
    private LayoutInflater layout;

    public CandidaturaAdapter(List<CandidaturaModel> Candidatura, Context context) {
        this.Candidatura = Candidatura;
        this.context = context;
        this.layout = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // Verifica se listaEvento é nula para evitar NullPointerException
        if (Candidatura == null) {
            return 0;
        }
        return Candidatura.size();
    }

    @Override
    public CandidaturaModel getItem(int i) {
        return Candidatura.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        CandidaturaModel candidatura = Candidatura.get(i);
        View v = layout.inflate(R.layout.item_evento,null);




        return v;
    }
}
