package br.com.fourtech.fourfoot.model;

public class Usuario {

	private Long idusuario;
	private String nome;
	private String senha;
	
	public Long getIdUsuario() {
		return idusuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idusuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
