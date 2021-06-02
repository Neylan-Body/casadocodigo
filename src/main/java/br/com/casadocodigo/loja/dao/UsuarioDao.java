package br.com.casadocodigo.loja.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.Usuario;

public class UsuarioDao {

	@PersistenceContext
	private EntityManager entityManager;

	public void salvar(Usuario usuario) {
		entityManager.persist(usuario);
	}
	
		
	
}
