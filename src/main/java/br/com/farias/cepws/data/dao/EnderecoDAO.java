package br.com.farias.cepws.data.dao;

import java.util.List;

import br.com.farias.cepws.model.Endereco;

public interface EnderecoDAO {
	public void salvar(Endereco endereco);
	
	public void excluir(Endereco endereco);
	
	public Endereco buscar(String cep) throws EnderecoNaoEncontradoException;
	
	public List<Endereco> listar();
	
}
