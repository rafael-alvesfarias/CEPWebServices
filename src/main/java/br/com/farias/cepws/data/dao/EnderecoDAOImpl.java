package br.com.farias.cepws.data.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.farias.cepws.model.Endereco;

//Classe DAO que simula uma interação com um banco de dados de CEP
public class EnderecoDAOImpl implements EnderecoDAO {
	
	private static final Map<String, Endereco> enderecos = new HashMap<String, Endereco>(1000);
	
	//MOCK DE ENDEREÇOS
	static {
		char rua = 'A';
		for (int i = 0, cep = 11111110 ; i < 10; i++, cep++, rua++) {
			Endereco e = new Endereco();
			e.setCep(String.valueOf(cep));
			e.setBairro("Pirituba");
			e.setRua("Rua" + rua);
			e.setCidade("São Paulo");
			e.setEstado("SP");
			enderecos.put(e.getCep(), e);
		}
	}

	@Override
	public void salvar(Endereco endereco) {
		//Tratamento de fluxos de exceção do método omitido
		enderecos.put(endereco.getCep(), endereco);
	}

	@Override
	public void excluir(Endereco endereco) {
		//Tratamento de fluxos de exceção do método omitido
		enderecos.remove(endereco.getCep());
	}

	//Método responsável por buscar o endereço por determinado CEP.
	//Coloca zeros à direita até encontrar o endereço ou lança EnderecoNaoEncontradoException
	@Override
	public Endereco buscar(String cep) throws EnderecoNaoEncontradoException{
		if(cep == null || !cep.matches("[0-9]{8}")) {
			throw new IllegalArgumentException("CEP informado inválido: " + cep);
		}
		StringBuilder sb = new StringBuilder(cep);
		Endereco encontrado = null;
		int posicao = 7;
		do {
			encontrado = enderecos.get(sb.toString());
			if (encontrado != null) {
				return encontrado;
			} else {
				sb.replace(posicao, posicao + 1, "0");
				posicao--;
				System.out.println(sb.toString());
			}
		} while(posicao >= 0);

		//Se não encontrado lança uma exceção checada
		throw new EnderecoNaoEncontradoException(cep);
	}

	@Override
	public List<Endereco> listar() {
		return new ArrayList<Endereco>(enderecos.values());
	}

}
