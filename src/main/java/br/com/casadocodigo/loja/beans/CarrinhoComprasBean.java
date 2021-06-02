package br.com.casadocodigo.loja.beans;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import br.com.casadocodigo.loja.dao.LivroDao;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Livro;

@Model
public class CarrinhoComprasBean {
	
	@Inject
	private LivroDao livroDao;
	
	@Inject
	private CarrinhoCompras carrinhoCompras;
	
	
	public String add(Integer id) {
		Livro livro = livroDao.buscarPorId(id);
		CarrinhoItem carrinhoItem = new CarrinhoItem(livro);
		carrinhoCompras.add(carrinhoItem);		
		return "carrinho?faces-redirect=true";
	}
	
	public List<CarrinhoItem> getItens(){
		return carrinhoCompras.getItens();
	}
	
	public void remover(CarrinhoItem item) {
		carrinhoCompras.remover(item);
	}
}
