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
import com.jrorg.bookmyshow.entity.Screen;
import com.jrorg.bookmyshow.entity.Theatre;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.ScreenRequest;

public class ScreensManager<K extends Screen,V extends ScreenRequest> implements BaseManager<K,V>{

	@Override
	public List<K> getList(int page_no, int limit) throws Exception {
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery("SELECT s FROM BMS_Screens s ORDER BY s.id DESC",Screen.class);
				query.setFirstResult((page_no-1)*limit);
				query.setMaxResults(limit);
				List<K> result_list =(List<K>)query.getResultList();
				return result_list;
			}
		});
	}
	public K create(V screen_data) throws Exception{
		K screen=(K)new Screen();
		screen.from((BaseRequest)screen_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				entitymanager.persist(screen);
				return (K)entitymanager.find(Screen.class, screen.getId());
			}
		});
	}

	public K update(V screen_data) throws Exception{
		K screen=(K)new Screen();
		screen.from((BaseRequest)screen_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaUpdate<Screen> update = builder.createCriteriaUpdate(Screen.class);
				Root<Screen> root = update.from(Screen.class);
				if(screen.getName()!=null) {
					update.set("name", screen.getName());
				}
				if(screen.getTheatre()!=null) {
					Theatre theatre = entitymanager.find(Theatre.class, screen.getTheatre().getId());
					update.set("theatre",theatre);
				}
				update.where(builder.equal(root.get("id"), screen.getId()));
				entitymanager.createQuery(update).executeUpdate();
				return (K)entitymanager.find(Screen.class, screen.getId());
			}
		});
	}

	public void delete(long id) throws Exception{
		PersistenceManager.perform(new Transaction<Screen>() {
			@Override
			public Screen perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaDelete<Screen> delete = builder.createCriteriaDelete(Screen.class);
				Root<Screen> root = delete.from(Screen.class);
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
	
	public K show(V screen_data) throws Exception{
		K screen=(K)new Screen();
		screen.from((BaseRequest)screen_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				return (K)entitymanager.find(Screen.class, screen.getId());
			}
		});
	}
}
