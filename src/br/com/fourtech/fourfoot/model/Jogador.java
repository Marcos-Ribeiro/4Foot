package br.com.fourtech.fourfoot.model;

public class Jogador {

	private Long idJogador;
	private String nome;
	private String dataNascimento;
	private String posicao;
	private String senha;
	
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Long getIdJogador() {
		return idJogador;
	}
	public void setIdJogador(Long coJogador) {
		this.idJogador = coJogador;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String noNome) {
		this.nome = noNome;
	}	
	public String getPosicao() {
		return posicao;
	}
	public void setPosicao(String noPosicao) {
		this.posicao = noPosicao;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}
