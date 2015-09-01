package br.com.farias.cepws.data.dao;

public class EnderecoNaoEncontradoException extends Exception {
	
	private static final long serialVersionUID = -370075449496866967L;

	public EnderecoNaoEncontradoException(String cep){
		super("N�o foi encontrado nenhum endere�o com o CEP informado: " + cep);
	}
}
