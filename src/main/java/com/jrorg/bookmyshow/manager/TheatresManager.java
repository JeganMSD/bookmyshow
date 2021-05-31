package com.jrorg.bookmyshow.manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.hibernate.exception.ConstraintViolationException;

import com.jrorg.bookmyshow.dbutils.PersistenceManager;
import com.jrorg.bookmyshow.dbutils.PersistenceManager.Transaction;
import com.jrorg.bookmyshow.entity.Place;
import com.jrorg.bookmyshow.entity.Theatre;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.TheatreRequest;
import com.jrorg.bookmyshow.util.RestAPIException;
import com.jrorg.bookmyshow.util.RestAPIException.Type;

public class TheatresManager<K extends Theatre,V extends TheatreRequest> implements BaseManager<K,V>{

	@Override
	public List<K> getList(int page_no, int limit) throws Exception {
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery("SELECT s FROM BMS_Theatres s ORDER BY s.id DESC",Theatre.class);
				query.setFirstResult((page_no-1)*limit);
				query.setMaxResults(limit);
				List<K> result_list =(List<K>)query.getResultList();
				return result_list;
			}
		});
	}
	public K create(V theatre_data) throws Exception{
		K theatre = (K)new Theatre();
		theatre.from((BaseRequest)theatre_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) throws Exception {
				try {
					entitymanager.persist(theatre);
					return (K)entitymanager.find(Theatre.class, theatre.getId());
				}
				catch(PersistenceException e) {
					if(e.getCause() instanceof ConstraintViolationException) {
						throw new RestAPIException(Type.INVALID_RESOURCE_ID,"place_id",null);
					}
					throw e;
				}
			}
		});
	}

	public K update(V theatre_data) throws Exception{
		K theatre = (K)new Theatre();
		theatre.from((BaseRequest)theatre_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaUpdate<Theatre> update = builder.createCriteriaUpdate(Theatre.class);
				Root<Theatre> root = update.from(Theatre.class);
				if(theatre.getName()!=null) {
					update.set("name", theatre.getName());
				}
				if(theatre.getPlace()!=null) {
					Place place = entitymanager.find(Place.class, theatre.getPlace().getId());
					update.set("place",place);
				}
				update.where(builder.equal(root.get("id"), theatre.getId()));
				entitymanager.createQuery(update).executeUpdate();
				return (K)entitymanager.find(Theatre.class, theatre.getId());
			}
		});
	}

	public void delete(long id) throws Exception{
		PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaDelete<Theatre> delete = builder.createCriteriaDelete(Theatre.class);
				Root<Theatre> root = delete.from(Theatre.class);
				delete.where(builder.equal(root.get("id"), id));
				entitymanager.createQuery(delete).executeUpdate();
				return null;
			}
		});
	}

	@Override
	public List<K> batchCreate(List<V> t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public K show(V theatre_data) throws Exception {
		K theatre = (K)new Theatre();
		theatre.from((BaseRequest)theatre_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) throws Exception {
					return (K)entitymanager.find(Theatre.class, theatre.getId());
			}
		});
	}
}
