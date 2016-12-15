package com.jeremiahxu.leisure.repository.impl;

import java.io.Serializable;

import com.jeremiahxu.leisure.exception.RepositoryException;
import com.jeremiahxu.leisure.repository.BaseRepository;
import com.jeremiahxu.leisure.util.ContextUtil;

/**
 * @author jeremiah
 * 
 */
public class PersistantObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private BaseRepository<PersistantObject, Integer> repo;
	private Integer id;

	public PersistantObject() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@SuppressWarnings("unchecked")
	public BaseRepository<PersistantObject, Integer> getRepo() {
		if (repo == null) {
			repo = (BaseRepository<PersistantObject, Integer>) ContextUtil.getBean("repo");
		}
		return repo;
	}

	public void setRepo(BaseRepository<PersistantObject, Integer> repo) {
		this.repo = repo;
	}

	public void save() {
		if (!this.getRepo().save(this)) {
			throw new RepositoryException(this.getClass().getSimpleName() + " saving is failure.");
		}
	}

	public void update() {
		if (!this.getRepo().update(this)) {
			throw new RepositoryException(this.getClass().getSimpleName() + " updating is failure.");
		}
	}

	public void remove() {
		if (!this.getRepo().remove(this)) {
			throw new RepositoryException(this.getClass().getSimpleName() + " removing is failure.");
		}
	}

}
