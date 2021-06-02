package br.com.casadocodigo.loja.service;

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.casadocodigo.loja.dao.CompraDao;
import br.com.casadocodigo.loja.models.Compra;

@Path("/pagamento")
public class PagamentoService {

	@Context
	private ServletContext context;
	
	@Inject
	private CompraDao compraDao;
	
	@Inject
	private PagamentoGateway pagamentoGateway;
	
	private static ExecutorService executor = Executors.newFixedThreadPool(50);
	
	@POST
	public void pagar(@Suspended final AsyncResponse asyncResponse, @QueryParam("uuid")String uuid) {
		Compra compra = compraDao.buscaPorUuid(uuid);
		
		executor.submit(()-> { 
			try {
				pagamentoGateway.pagar(compra.getTotal());
				URI responseUri = UriBuilder.fromPath("http://localhost:8080"+context.getContextPath()+"/index.xhtml").queryParam("msg", "Compra realizada com sucesso").build();
				Response response = Response.seeOther(responseUri).build();
				asyncResponse.resume(response);
			} catch (Exception e) {
				asyncResponse.resume(new WebApplicationException(e));
			}
		});
	}
}
