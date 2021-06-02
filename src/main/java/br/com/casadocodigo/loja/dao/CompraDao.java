package br.com.casadocodigo.loja.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.casadocodigo.loja.models.Compra;

public class CompraDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void salvar(Compra compra) {
		
		entityManager.persist(compra);
	}

	public Compra buscaPorUuid(String uuid) {
		return entityManager.createQuery("SELECT c FROM Compra c WHERE c.uuid = :uuid", Compra.class).setParameter("uuid", uuid).getSingleResult();
	}
	
}
