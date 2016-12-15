package com.jeremiahxu.leisure.repository.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jeremiahxu.leisure.repository.BaseRepository;

/**
 * 数据持久化操作实现类。
 * 
 * @author Jeremiah Xu
 * 
 */
@Repository("repo")
public class BaseRepositoryImpl<T extends PersistantObject, ID extends Serializable> implements BaseRepository<T, ID> {
	@PersistenceContext
	protected EntityManager em;
	protected Logger log = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#queryAll(java.lang.Class )
	 */
	@Override
	public List<T> queryAll(Class<T> clazz) {
		String jpql = "SELECT o FROM " + clazz.getSimpleName() + " o";
		return query(jpql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#query(java.lang.String)
	 */
	@Override
	public List<T> query(String jpql) {
		return query(jpql, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#query(java.lang.String, java.util.Map)
	 */
	@Override
	public List<T> query(String jpql, Map<String, ? extends Object> params) {
		return query(jpql, params, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#query(java.lang.String, int, int)
	 */
	@Override
	public List<T> query(String jpql, int pageNumber, int numberPerPage) {
		return query(jpql, null, pageNumber, numberPerPage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#query(java.lang.String, java.util.Map, int, int)
	 */
	@Override
	public List<T> query(String jpql, Map<String, ? extends Object> params, int pageNumber, int numberPerPage) {
		Query query = em.createQuery(jpql);
		if (params != null && !params.isEmpty()) {// 如果有条件参数
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		if (pageNumber >= 0 && numberPerPage > 0) {
			int firstResult = (pageNumber - 1) * numberPerPage;
			int maxResult = pageNumber * numberPerPage;
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
		}
		@SuppressWarnings("unchecked")
		List<T> rs = query.getResultList();
		return rs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#load(java.lang.String, java.util.Map)
	 */
	@Override
	public T load(String jpql, Map<String, ? extends Object> params) {
		List<T> result = this.query(jpql, params);
		if (result.size() == 1) {
			return result.get(0);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#loadById(java.lang.Class , int)
	 */
	@Override
	public T loadById(Class<T> clazz, int id) {
		return em.find(clazz, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#save(com.jeremiahxu. leisure.repository.impl.PersistantObject)
	 */
	@Override
	public boolean save(T t) {
		if (em.contains(t)) {
			return false;
		} else {
			em.persist(t);
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#update(com.jeremiahxu .leisure.repository.impl.PersistantObject)
	 */
	@Override
	public boolean update(T t) {
		em.merge(t);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jeremiahxu.leisure.repository.BaseRepository#remove(com.jeremiahxu .leisure.repository.impl.PersistantObject)
	 */
	@Override
	public boolean remove(T t) {
		if (em.contains(t)) {
			em.remove(t);
		} else {
			PersistantObject po = em.find(t.getClass(), t.getId());
			em.remove(po);
		}
		return true;
	}
}
