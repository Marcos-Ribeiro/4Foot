package br.com.fourtech.fourfoot;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fourtech.fourfoot.model.Jogador;
import br.com.fourtech.fourfoot.model.Usuario;
import br.com.fourtech.fourfoot.server.JSONServer;

public class Cadastre_seActivity extends Activity {

	private EditText edtNome, edtSenha, edtConfirmaSenha, edtApelido, edtPosicao, edtDataNascimento;
	private Button btnSalvar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastre_se);
		
		edtNome = (EditText) findViewById(R.id.edtNomeUsuario);
		edtSenha = (EditText) findViewById(R.id.edtSenha);
		edtConfirmaSenha = (EditText) findViewById(R.id.edtConfirmaSenha);
		edtApelido = (EditText) findViewById(R.id.edtApelido);
		edtPosicao = (EditText) findViewById(R.id.edtPosicao);
		edtDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
		
		final Usuario usuario = new Usuario();
		final Jogador jogador = new Jogador();
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (edtSenha.getText().toString() == edtConfirmaSenha.getText().toString()) {
					usuario.setNome(edtNome.getText().toString());
					usuario.setSenha(edtSenha.getText().toString());
					
					jogador.setApelido(edtApelido.getText().toString());
					jogador.setPosicao(edtPosicao.getText().toString());
					jogador.setDataNascimento(edtDataNascimento.getText().toString());
					
					String paramJson = null;
					
					JSONObject jRetorno = wsConnection(Config.URL + "usuario", "POST", paramJson);
				}else {
					Toast.makeText(Cadastre_seActivity.this, "Os campos de senha são diferentes!", Toast.LENGTH_LONG);
				}
				
				
			}
		});
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastre_se, menu);
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

	private JSONObject wsConnection(String url, String metodo, String paramJson){
		JSONServer server = new JSONServer();
		JSONObject json = server.GetJSONObject(url, metodo, "", paramJson);
		return json;
	}

}
