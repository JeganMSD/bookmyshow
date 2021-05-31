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
import com.jrorg.bookmyshow.entity.Place;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.PlaceRequest;

public class PlacesManager<K extends Place,V extends PlaceRequest> implements BaseManager<K,V>{
	@Override
	public List<K> getList(int page_no,int limit) throws Exception {
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery("SELECT s FROM BMS_Places s ORDER BY s.id DESC",Place.class);
				query.setFirstResult((page_no-1)*limit);
				query.setMaxResults(limit);
				List<K> result_list =(List<K>)query.getResultList();
				return result_list;
			}
		});
	}
	
	@Override
	public K create(V place_request) throws Exception{
		K place = (K)new Place();
		place.from((BaseRequest)place_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				entitymanager.persist(place);
				return (K)entitymanager.find(Place.class, place.getId());
			}
		});
	}
	
	@Override
	public K update(V place_request) throws Exception{
		K place = (K)new Place();
		place.from((BaseRequest)place_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaUpdate<Place> update = builder.createCriteriaUpdate(Place.class);
				Root<Place> root = update.from(Place.class);
				if(place.getName()!=null) {
					update.set("name", place.getName());
				}
				if(place.getCountry()!=null) {
					update.set("country", place.getCountry());
				}
				if(place.getState()!=null) {
					update.set("state", place.getState());
				}
				if(place.getPincode()!=null) {
					update.set("pincode", place.getPincode());
				}
				if(place.getCity()!=null) {
					update.set("city", place.getCity());
				}
				update.where(builder.equal(root.get("id"), place.getId()));
				entitymanager.createQuery(update).executeUpdate(); 
				return (K)entitymanager.find(Place.class, place.getId());
			}
		});
	}
	
	@Override
	public void delete(long id) throws Exception{
		PersistenceManager.perform(new Transaction<Place>() {
			@Override
			public Place perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaDelete<Place> delete = builder.createCriteriaDelete(Place.class);
				Root<Place> root = delete.from(Place.class);
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
	public K show(V place_request) throws Exception {
		K place = (K)new Place();
		place.from((BaseRequest)place_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				return (K)entitymanager.find(Place.class, place.getId());
			}
		});
	}

}
