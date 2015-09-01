package br.com.farias.cepws.service.wsrest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.farias.cepws.data.dao.EnderecoDAOImpl;
import br.com.farias.cepws.data.dao.EnderecoNaoEncontradoException;
import br.com.farias.cepws.model.Endereco;

@RunWith(JMockit.class)
public class CEPWebServiceTest {
	
	@Tested
	CEPWebService cepWS = new CEPWebService();
	
	@Mocked
	EnderecoDAOImpl enderecoDAO;
	
	@Test
	public void testBuscarCEPComUmCepComValorNulo() {
		RetornoWebService retornoEsperado = RetornoWebService.retornarErro("CEP informado inválido: null");
		
		RetornoWebService retornoObtido = cepWS.buscarCEP(null);
		
		assertFalse(retornoObtido.isSuccess());
		assertEquals(retornoEsperado.getMessage(), retornoObtido.getMessage());
	}
	
	@Test
	public void testBuscarCEPComUmCepComTamnhoMenorQueOPertido() {
		String cep = "123";
		RetornoWebService retornoEsperado = RetornoWebService.retornarErro("CEP informado inválido: 123");
		
		RetornoWebService retornoObtido = cepWS.buscarCEP(cep);
		
		assertFalse(retornoObtido.isSuccess());
		assertEquals(retornoEsperado.getMessage(), retornoObtido.getMessage());
	}
	
	@Test
	public void testBuscarCEPComUmCepComTamnhoMaiorQueOPermitido() {
		String cep = "123456789";
		RetornoWebService retornoEsperado = RetornoWebService.retornarErro("CEP informado inválido: 123456789");
		
		RetornoWebService retornoObtido = cepWS.buscarCEP(cep);
		
		assertFalse(retornoObtido.isSuccess());
		assertEquals(retornoEsperado.getMessage(), retornoObtido.getMessage());
	}
	
	@Test
	public void testBuscarCEPComUmCepComCaracteresInvalidos() {
		String cep = "123ab678";
		RetornoWebService retornoEsperado = RetornoWebService.retornarErro("CEP informado inválido: 123ab678");
		
		RetornoWebService retornoObtido = cepWS.buscarCEP(cep);
		
		assertFalse(retornoObtido.isSuccess());
		assertEquals(retornoEsperado.getMessage(), retornoObtido.getMessage());
	}
	
	@Test
	public void testBuscarCEPNaoExistente() throws EnderecoNaoEncontradoException {
		RetornoWebService retornoEsperado = RetornoWebService.retornarErro("Não foi encontrado nenhum endereço com o CEP informado: 00000000");
		//Espera que uma exceção do tipo EnderecoNaoEncontradoException seja lançada
		new Expectations() {{
			enderecoDAO.buscar(anyString); result = new EnderecoNaoEncontradoException("00000000");
		}};
		
		RetornoWebService retornoObtido = cepWS.buscarCEP("00000000");
		
		assertFalse(retornoObtido.isSuccess());
		assertEquals(retornoEsperado.getMessage(), retornoObtido.getMessage());
	}
	
	@Test
	public void testBuscarCEPValido() throws EnderecoNaoEncontradoException {
		String cep = "11111111";
		Endereco enderecoEsperado = new Endereco();
		new Expectations() {{
			enderecoDAO.buscar(anyString); result = new Endereco();
		}};
		
		RetornoWebService retornoObtido = cepWS.buscarCEP("11111111");
		
		assertTrue(retornoObtido.isSuccess());
		assertEquals(enderecoEsperado, retornoObtido.getData());	
	}

}
