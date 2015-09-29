package br.com.fourtech.fourfoot;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import br.com.fourtech.fourfoot.model.Grupo;
import br.com.fourtech.fourfoot.model.Jogador;
import br.com.fourtech.fourfoot.server.JSONServer;

public class PrincipalActivity extends Activity {
	
	ListView lvGrupos;
	Button btnNovoGrupo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		getJogadorInfo();
		populaListView();
		
		lvGrupos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Grupo grupo = (Grupo) parent.getItemAtPosition(position);
				Intent ir = new Intent(PrincipalActivity.this, GrupoActivity.class);
				ir.putExtra("grupoSelecionado", grupo);
				
				startActivity(ir);
			}
		});
		
		lvGrupos.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this);

                builder.setTitle("Excluir");
                builder.setMessage("Deseja mesmo excluir esse item?");
                builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Grupo grupo = (Grupo) lvGrupos.getItemAtPosition(position);
                        JSONObject retorno = wsConnectionObj(Config.URL + "grupo/" + grupo.getIdGrupo(),"DELETE","grupo");

                        populaListView();
                        try {
                            Toast.makeText(PrincipalActivity.this, retorno.getString("msg"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {}
                    }
                });
                builder.setNeutralButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
			}
			
		});
		
		btnNovoGrupo = (Button) findViewById(R.id.btnNovoGrupo);
		btnNovoGrupo.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent ir = new Intent(PrincipalActivity.this, GrupoActivity.class);				
				startActivity(ir);
			}
		});
		
	}

	private Jogador getJogadorInfo() {
		JSONObject jRetorno = wsConnectionObj(Config.URL + "jogador/" + Config.USUARIO_LOGADO.getIdUsuario(), "GET", "");
		Jogador jogador = new Jogador();
		
		try {
			jogador.setIdjogador(jRetorno.getLong("idjogador"));
			jogador.setDataNascimento(jRetorno.getString("datanascimento"));
			jogador.setApelido(jRetorno.getString("apelido"));
			jogador.setPosicao(jRetorno.getString("posicao"));
			
			Config.JOGADOR_INFO = jogador;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		
		return jogador;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
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
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		populaListView();
	}
	
	private JSONArray wsConnection(String url, String method, String type){
	
		JSONServer jServer = new JSONServer();
		JSONArray jArray = jServer.GetJSONArray(url, method, type);
		
		return jArray;
	}
	
	private JSONObject wsConnectionObj(String url, String method, String type){
		
		JSONServer jServer = new JSONServer();
		JSONObject jObj = jServer.GetJSONObject(url, method, type,"");
		
		return jObj;
	}

	private void populaListView(){
		JSONArray jRetorno = wsConnection(Config.URL + "grupo", "GET", "grupo");
		
		List<Grupo> lista = new ArrayList<Grupo>();
		lvGrupos = (ListView) findViewById(R.id.lvGrupos);
		
		for (int i = 0; i < jRetorno.length(); i++) {
			Grupo grupo = new Grupo();
		
			try {
				grupo.setIdGrupo(jRetorno.getJSONObject(i).getLong("idgrupo"));
				grupo.setNome(jRetorno.getJSONObject(i).getString("nome"));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			lista.add(grupo);
		}
		
		lvGrupos.setAdapter(new AdapterGrupo(this, lista));
	}
}
