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
import com.jrorg.bookmyshow.entity.Screen;
import com.jrorg.bookmyshow.entity.Show;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.ShowRequest;

public class ShowsManager<K extends Show,V extends ShowRequest> implements BaseManager<K , V>{

	@Override
	public List<K> getList(int page_no, int limit) throws Exception {
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery("SELECT s FROM BMS_Shows s ORDER BY s.id DESC",Show.class);
				query.setFirstResult((page_no-1)*limit);
				query.setMaxResults(limit);
				List<K> result_list =(List<K>)query.getResultList();
				return result_list;
			}
		});
	}

	@Override
	public K create(V show_request) throws Exception {
		K show = (K)new Show();
		show.from((BaseRequest)show_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				entitymanager.persist(show);
				return (K)entitymanager.find(Show.class, show.getId());
			}
		});
	}

	@Override
	public List<K> batchCreate(List<V> v) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public K show(V show_request) throws Exception {
		K show = (K)new Show();
		show.from((BaseRequest)show_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				return (K)entitymanager.find(Show.class, show.getId());
			}
		});
	}

	@Override
	public K update(V show_request) throws Exception {
		K show = (K)new Show();
		show.from((BaseRequest)show_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaUpdate<Show> update = builder.createCriteriaUpdate(Show.class);
				Root<Show> root = update.from(Show.class);
				if(show.getMovie()!=null) {
					Movie movie = entitymanager.find(Movie.class, show.getMovie().getId());
					update.set("movie",movie);
				}
				if(show.getScreen()!=null) {
					Screen screen = entitymanager.find(Screen.class, show.getScreen().getId());
					update.set("screen",screen);
				}
				if(show.getStart_time_millis()!=null) {
					update.set("start_time_millis",show.getStart_time_millis());
				}
				if(show.getEnd_time_millis()!=null) {
					update.set("end_time_millis",show.getEnd_time_millis());
				}
				if(show.getDate_millis()!=null) {
					update.set("date_millis", show.getDate_millis());
				}
				update.where(builder.equal(root.get("id"), show.getId()));
				entitymanager.createQuery(update).executeUpdate(); 
				return (K)entitymanager.find(Show.class, show.getId());
			}
		});
	}

	@Override
	public void delete(long id) throws Exception {
		PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaDelete<Show> delete = builder.createCriteriaDelete(Show.class);
				Root<Show> root = delete.from(Show.class);
				delete.where(builder.equal(root.get("id"), id));
				entitymanager.createQuery(delete).executeUpdate();
				return null;
			}
		});
	}

}
