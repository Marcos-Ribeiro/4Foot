package br.com.fourtech.fourfoot;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fourtech.fourfoot.model.Grupo;
import br.com.fourtech.fourfoot.server.JSONServer;

public class GrupoActivity extends Activity {

	EditText edtNomeGrupo;
	Button btnSalvar; 
	Grupo grupoSelecionado = new Grupo();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grupo);
		
		Intent i = getIntent();
		grupoSelecionado = (Grupo) i.getSerializableExtra("grupoSelecionado");
		
		edtNomeGrupo = (EditText) findViewById(R.id.edtNomeGrupo);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
		
		if (grupoSelecionado != null) {
			edtNomeGrupo.setText(grupoSelecionado.getNome());
		}		
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Grupo grupo = new Grupo();
											
				if (grupoSelecionado != null) {
					grupo.setIdGrupo(grupoSelecionado.getIdGrupo());
					grupo.setNome(edtNomeGrupo.getText().toString());
					gravar("PUT", grupo);
				}else{
					grupo.setNome(edtNomeGrupo.getText().toString());
					gravar("POST", grupo);
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grupo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

    public void gravar(String metodo,Grupo grupo){
    	try {
    		JSONObject jsonEnvio = new JSONObject();
    		
    		if (metodo == "POST") {
        		jsonEnvio.put("nome", edtNomeGrupo.getText().toString());
			}else if(metodo == "PUT"){
				jsonEnvio.put("idgrupo",grupo.getIdGrupo().toString());
				jsonEnvio.put("nome", edtNomeGrupo.getText().toString());	    		
			}
    		
    		String retorno = connectionWS(Config.URL + "grupo","grupo",metodo, jsonEnvio.toString());
    		
    		if (retorno == "OK"){
    			finish();
    		}else{
    			Toast.makeText(this,"Atenção! Erro ao salvar categoria!",Toast.LENGTH_LONG).show();
    		}
    	} catch (JSONException e) {
    		e.printStackTrace();
    	}
    }

    public String connectionWS(String url, String type, String metodo, String jsonParam) {
        JSONServer jsonParser = new JSONServer();
        jsonParser.GetJSONObject(url, metodo, type, jsonParam);
        return "OK";
    }
}
