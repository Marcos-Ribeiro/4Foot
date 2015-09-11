package br.com.fourtech.fourfoot;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fourtech.fourfoot.model.Jogador;
import br.com.fourtech.fourfoot.server.JSONServer;

public class LoginActivity extends Activity{

	private EditText edtLogin;
	private EditText edtSenha;
	private Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

		edtLogin = (EditText) findViewById(R.id.edtLogin);
		edtSenha = (EditText) findViewById(R.id.edtSenha);
		btnLogin = (Button) findViewById(R.id.btnLogin);

		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				JSONObject jRetorno = wsConnection("http://10.0.2.2:8080/4footserver/login/Marcos/123");
				
				Jogador jogadorLogado = new Jogador();
								
				try {
					
					jogadorLogado.setIdJogador(jRetorno.getLong("idJogador"));
					jogadorLogado.setNome(jRetorno.getString("nome"));
					jogadorLogado.setSenha(jRetorno.getString("senha"));
					jogadorLogado.setPosicao(jRetorno.getString("posicao"));
					jogadorLogado.setDataNascimento(jRetorno.getString("dataNascimento"));
					
				} catch (JSONException e) {
					Toast.makeText(LoginActivity.this, e.getMessage().replace("\"", "") , 1000).show();
				}
				
				if (jRetorno != null) {
					Intent i = new Intent(LoginActivity.this, PrincipalActivity.class);
					startActivity(i);
					//Toast.makeText(LoginActivity.this, "Login e/ou Senha inválido", LENGTH_LONG);
				} 
				Toast.makeText(LoginActivity.this, "Login e/ou Senha inválido", 1000).show();
			}
		});
	}
	
	private JSONObject wsConnection(String url){
		
		JSONServer jServer = new JSONServer();
		JSONObject jobj = jServer.GetJSONObject(url , "GET", "","");
		
		return jobj;
	}
}