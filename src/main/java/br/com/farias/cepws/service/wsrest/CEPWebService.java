package br.com.farias.cepws.service.wsrest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.farias.cepws.data.dao.EnderecoDAO;
import br.com.farias.cepws.data.dao.EnderecoDAOImpl;
import br.com.farias.cepws.data.dao.EnderecoNaoEncontradoException;
import br.com.farias.cepws.model.Endereco;

@Path("/cep")
@Consumes("application/json")
public class CEPWebService {
	
	//Futuramente alterar para injeção de depedências para reduzir o acoplamento
	EnderecoDAO enderecoDAO = new EnderecoDAOImpl();
	
	@Path("{cep}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public RetornoWebService buscarCEP(@PathParam("cep") String cep) {
		if(cep == null || !cep.matches("[0-9]{8}")) {
			return RetornoWebService.retornarErro("CEP informado inválido: " + cep);
		}
		try {
			Endereco e = enderecoDAO.buscar(cep);
			return RetornoWebService.retornarSucesso(e);
		} catch (EnderecoNaoEncontradoException e) {
			return RetornoWebService.retornarErro(e.getMessage());
		}
	}
	
}
