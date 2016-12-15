package com.jeremiahxu.leisure.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jeremiahxu.leisure.repository.impl.PersistantObject;

public interface BaseRepository<T extends PersistantObject, ID extends Serializable> {

	/**
	 * 
	 * @param t
	 * @return
	 */
	public boolean save(T t);

	/**
	 * 
	 * @param t
	 * @return
	 */
	public boolean update(T t);

	/**
	 * 
	 * @param t
	 * @return
	 */
	public boolean remove(T t);

	/**
	 * load a po by jpql with parameters.
	 * 
	 * @param jpql
	 * @param params
	 * @return
	 */
	public T load(String jpql, Map<String, ? extends Object> params);

	/**
	 * load a po by id.
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public T loadById(Class<T> clazz, int id);

	/**
	 * query all results.
	 * 
	 * @param clazz
	 * @return
	 */
	public List<T> queryAll(Class<T> clazz);

	/**
	 * query by the jpql.
	 * 
	 * @param jpql
	 * @return
	 */
	public List<T> query(String jpql);

	/**
	 * query by the jpql with parameters.
	 * 
	 * @param jpql
	 * @param params
	 * @return
	 */
	public List<T> query(String jpql, Map<String, ? extends Object> params);

	/**
	 * query by the jpql and return the paged results.
	 * 
	 * @param jpql
	 * @param pageNumber
	 * @param numberPerPage
	 * @return
	 */
	public List<T> query(String jpql, int pageNumber, int numberPerPage);

	/**
	 * query by the jpql with parameters and return the paged results.
	 * 
	 * @param jpql
	 * @param params
	 * @param pageNumber
	 * @param numberPerPage
	 * @return
	 */
	public List<T> query(String jpql, Map<String, ? extends Object> params, int pageNumber, int numberPerPage);

}