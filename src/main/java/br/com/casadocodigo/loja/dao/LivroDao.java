package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.QueryHints;

import br.com.casadocodigo.loja.models.Livro;

@Stateful
public class LivroDao {
	
	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	public void salvar(Livro livro) {
		entityManager.persist(livro);
	}

	public List<Livro> listar() {
		String jpql = "SELECT distinct(l) from Livro l JOIN FETCH l.autores";
		return entityManager.createQuery(jpql, Livro.class).getResultList();
	}

	public List<Livro> ultimosLancamentos() {
		String jpql = "SELECT l FROM Livro l order by l.dataPublicacao desc";
		return entityManager.createQuery(jpql, Livro.class).setMaxResults(5).setHint(QueryHints.HINT_CACHEABLE, true).setHint(QueryHints.HINT_CACHE_REGION, "home").getResultList();
	}

	public void limpaCache() {
		Cache cache = entityManager.getEntityManagerFactory().getCache();
		cache.evict(Livro.class, 1l);
		
		SessionFactory sessionFactory = entityManager.getEntityManagerFactory().unwrap(SessionFactory.class);
		sessionFactory.getCache().evictAllRegions();
		sessionFactory.getCache().evictQueryRegion("home");
	}

	public List<Livro> demaisLivros() {
		String jpql = "SELECT l FROM Livro l order by l.dataPublicacao desc";
		return entityManager.createQuery(jpql, Livro.class).setFirstResult(5).setHint(QueryHints.HINT_CACHEABLE, true).setHint(QueryHints.HINT_CACHE_REGION, "home").getResultList();
	}

	public Livro buscarPorId(Integer id) {
		return entityManager.find(Livro.class, id);
		
//		String jpql = "SELECT l FROM Livro l JOIN FETCH l.autores WHERE l.id = :id";
//		return entityManager.createQuery(jpql, Livro.class).setParameter("id", id).getSingleResult();
	}

}
