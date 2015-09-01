package br.com.farias.cepws.data.dao;

public class EnderecoNaoEncontradoException extends Exception {
	
	private static final long serialVersionUID = -370075449496866967L;

	public EnderecoNaoEncontradoException(String cep){
		super("Não foi encontrado nenhum endereço com o CEP informado: " + cep);
	}
}
