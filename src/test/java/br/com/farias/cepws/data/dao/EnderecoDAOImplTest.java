package br.com.farias.cepws.data.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.farias.cepws.model.Endereco;

public class EnderecoDAOImplTest {
	
	EnderecoDAO dao = new EnderecoDAOImpl();
	
	@Before
	public void init() {
		String[] rua = {"Rua X", "Rua Y", "Rua Z"};
		String[] cep = {"44444555","44444000", "44444770"};
		String[] bairro = {"Jardins","Centro", "Pirituba"};
		for (int i = 0; i < 3; i++) {
			Endereco e = new Endereco();
			e.setCep(cep[i]);
			e.setBairro(bairro[i]);
			e.setRua(rua[i]);
			e.setCidade("São Paulo");
			e.setEstado("SP");
			dao.salvar(e);
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBuscarCEPComUmCepComValorNulo() throws EnderecoNaoEncontradoException {		
		dao.buscar(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBuscarCEPComUmCepComTamnhoMenorQueOPertido() throws EnderecoNaoEncontradoException {
		String cep = "123";
		
		dao.buscar(cep);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBuscarCEPComUmCepComTamnhoMaiorQueOPermitido() throws EnderecoNaoEncontradoException {
		String cep = "123456789";
		
		dao.buscar(cep);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBuscarCEPComUmCepComCaracteresInvalidos() throws EnderecoNaoEncontradoException {
		String cep = "123ab678";
		
		dao.buscar(cep);
	}
	
	@Test(expected = EnderecoNaoEncontradoException.class)
	public void testBuscarCEPNaoExistente() throws EnderecoNaoEncontradoException {
		String cep = "11111222";
		
		dao.buscar(cep);
	}
	
	@Test()
	public void testBuscarCEPValido() throws EnderecoNaoEncontradoException {
		String cep = "44444555";
		Endereco enderecoEsperado = new Endereco();
		enderecoEsperado.setCep("44444555");
		enderecoEsperado.setBairro("Jardins");
		enderecoEsperado.setRua("Rua X");
		enderecoEsperado.setCidade("São Paulo");
		enderecoEsperado.setEstado("SP");
		
		Endereco enderecoObtido = dao.buscar(cep);
		
		assertEquals(enderecoEsperado, enderecoObtido);		
	}
	
	@Test()
	public void testBuscarCEPValidoComRecursao() throws EnderecoNaoEncontradoException {
		String cep = "44444777";
		Endereco enderecoEsperado = new Endereco();
		enderecoEsperado.setCep("44444770");
		enderecoEsperado.setBairro("Pirituba");
		enderecoEsperado.setRua("Rua Z");
		enderecoEsperado.setCidade("São Paulo");
		enderecoEsperado.setEstado("SP");
		
		Endereco enderecoObtido = dao.buscar(cep);
		
		assertEquals(enderecoEsperado, enderecoObtido);		
	}
	
	@Test()
	public void testBuscarCEPValidoComRecursao2() throws EnderecoNaoEncontradoException {
		String cep = "44444666";
		Endereco enderecoEsperado = new Endereco();
		enderecoEsperado.setCep("44444000");
		enderecoEsperado.setBairro("Centro");
		enderecoEsperado.setRua("Rua Y");
		enderecoEsperado.setCidade("São Paulo");
		enderecoEsperado.setEstado("SP");
		
		Endereco enderecoObtido = dao.buscar(cep);
		
		assertEquals(enderecoEsperado, enderecoObtido);		
	}
	
	
}
