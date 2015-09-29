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
import br.com.fourtech.fourfoot.model.Usuario;
import br.com.fourtech.fourfoot.server.JSONServer;

public class LoginActivity extends Activity{

	private EditText edtLogin;
	private EditText edtSenha;
	private Button btnLogin;
	private Button btnCadastre_se;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

		edtLogin = (EditText) findViewById(R.id.edtLogin);
		edtSenha = (EditText) findViewById(R.id.edtSenha);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnCadastre_se = (Button) findViewById(R.id.btnCadastre_se);
		
		edtLogin.setText("marcos");
		edtSenha.setText("123");

		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String login = edtLogin.getText().toString();
				String senha = edtSenha.getText().toString();
				
				JSONObject jRetorno = wsConnection(Config.URL + "login/" + login + "/" + senha);
				
				Usuario usuarioLogado = new Usuario();
				
				if (jRetorno != null) {
					try {
						usuarioLogado.setIdUsuario(jRetorno.getLong("idusuario"));
						usuarioLogado.setNome(jRetorno.getString("nome"));
						usuarioLogado.setSenha(jRetorno.getString("senha"));
						
					} catch (JSONException e) {
						Toast.makeText(LoginActivity.this, e.getMessage().replace("\"", "") , Toast.LENGTH_LONG).show();
					}
				
					Config.USUARIO_LOGADO = usuarioLogado;
					
					Intent i = new Intent(LoginActivity.this, PrincipalActivity.class);
					startActivity(i);
					finish();

				}else{ 
					Toast.makeText(LoginActivity.this, "Login e/ou Senha inválido", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		btnCadastre_se.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent ir = new Intent(LoginActivity.this, Cadastre_seActivity.class);
				startActivity(ir);
			}
		});
	}
	
	private JSONObject wsConnection(String url){
		
		JSONServer jServer = new JSONServer();
		JSONObject jobj = jServer.GetJSONObject(url , "GET", "","");
		
		return jobj;
	}
}