package br.com.fourtech.fourfoot;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import br.com.fourtech.fourfoot.model.Grupo;
import br.com.fourtech.fourfoot.server.JSONServer;

public class PrincipalActivity extends Activity {
	
	ListView lvGrupos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		List<Grupo> lista = new ArrayList<Grupo>();
		lvGrupos = (ListView) findViewById(R.id.lvGrupos);
		
		for (int i = 0; i < 10; i++) {
			Grupo grupo = new Grupo();
		
			Long id = (long) i;
			
			grupo.setIdGrupo(id);
			grupo.setNome("Grupo " + i);
			
			lista.add(grupo);
		}
		
		lvGrupos.setAdapter(new AdapterGrupo(this, lista));
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
		JSONArray jRetorno = wsConnection("http://10.0.2.2:8080/4footserver/grupo", "GET", "grupo");
		
	}
	
	private JSONArray wsConnection(String url, String method, String type){
	
		JSONServer jServer = new JSONServer();
		JSONArray jArray = jServer.GetJSONArray(url, method, type);
		
		return jArray;
	}
}
