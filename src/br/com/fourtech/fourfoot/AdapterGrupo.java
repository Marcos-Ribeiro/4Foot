package br.com.fourtech.fourfoot;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.fourtech.fourfoot.model.Grupo;

public class AdapterGrupo extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<Grupo> itens;
    
	public AdapterGrupo(Context context, List<Grupo> i){
        this.itens = i;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itens.get(position).getIdGrupo();
    }

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		final ViewCampos campos;
        //se a view estiver nula (nunca criada), inflamos o layout nela.
        if (v == null){
            v = mInflater.inflate(R.layout.custom_list,null);

            campos = new ViewCampos();
            
            campos.txtIdGrupo = (TextView) v.findViewById(R.id.txtIdGrupo);
            campos.txtNomeGrupo = (TextView) v.findViewById(R.id.txtNomeGrupo);

            v.setTag(campos);
        } else{
            //se a view já existe pega os itens.
            campos = (ViewCampos) v.getTag();
            v.setTag(campos);
        }

        //pega os dados da lista //e define os valores nos itens.
        Grupo item = itens.get(position);
        
        campos.txtIdGrupo.setText("ID: " + item.getIdGrupo().toString());
        campos.txtNomeGrupo.setText("Grupo: " + item.getNome());

        return v;
	}
}

class ViewCampos{
	TextView txtIdGrupo;
	TextView txtNomeGrupo;	
}
