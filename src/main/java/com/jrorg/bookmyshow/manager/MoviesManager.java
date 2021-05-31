package com.jrorg.bookmyshow.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.jrorg.bookmyshow.dbutils.PersistenceManager;
import com.jrorg.bookmyshow.dbutils.PersistenceManager.Transaction;
import com.jrorg.bookmyshow.entity.Movie;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.MovieRequest;

public class MoviesManager<K extends Movie, V extends MovieRequest> implements BaseManager<K, V>{

	@Override
	public List<K> getList(int page_no, int limit) throws Exception {
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery("SELECT s FROM BMS_Movies s ORDER BY s.id DESC",Movie.class);
				query.setFirstResult((page_no-1)*limit);
				query.setMaxResults(limit);
				List<K> result_list =(List<K>)query.getResultList();
				return result_list;
			}
		});
	}

	@Override
	public K create(V user_request) throws Exception {
		K movie = (K)new Movie();
		movie.from((BaseRequest)user_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				entitymanager.persist(movie);
				return (K)entitymanager.find(Movie.class, movie.getId());
			}
		});
	}

	@Override
	public List<K> batchCreate(List<V> v) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K show(V user_request) throws Exception {
		K movie = (K)new Movie();
		movie.from((BaseRequest)user_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				return (K)entitymanager.find(Movie.class, movie.getId());
			}
		});
	}

	@Override
	public K update(V user_request) throws Exception {
		K movie = (K)new Movie();
		movie.from((BaseRequest)user_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaUpdate<Movie> update = builder.createCriteriaUpdate(Movie.class);
				Root<Movie> root = update.from(Movie.class);
				if(movie.getName()!=null) {
					update.set("name", movie.getName());
				}
				if(movie.getLanguage()!=null) {
					update.set("language", movie.getLanguage());
				}
				if(movie.getGenre()!=null) {
					update.set("genre", movie.getGenre());
				}
				update.where(builder.equal(root.get("id"), movie.getId()));
				entitymanager.createQuery(update).executeUpdate(); 
				return (K)entitymanager.find(Movie.class, movie.getId());
			}
		});
	}

	@Override
	public void delete(long id) throws Exception {
		PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaDelete<Movie> delete = builder.createCriteriaDelete(Movie.class);
				Root<Movie> root = delete.from(Movie.class);
				delete.where(builder.equal(root.get("id"), id));
				entitymanager.createQuery(delete).executeUpdate();
				return null;
			}
		});
	}

}
