package com.jrorg.bookmyshow.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import com.jrorg.bookmyshow.dbutils.PersistenceManager;
import com.jrorg.bookmyshow.dbutils.PersistenceManager.Transaction;
import com.jrorg.bookmyshow.entity.Seat;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.SeatRequest;

public class SeatsManager<K extends Seat,V extends SeatRequest> implements BaseManager<K,V>{
	public static final int BATCH_SIZE=100;

	@Override
	public List<K> getList(int page_no, int limit) throws Exception {
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery("SELECT s FROM BMS_Seats s ORDER BY s.id DESC",Seat.class);
				query.setFirstResult((page_no-1)*limit);
				query.setMaxResults(limit);
				List<K> result_list =(List<K>)query.getResultList();
				return result_list;
			}
		});
	}
	public K create(V seat_data) throws Exception{
		K seat = (K)new Seat();
		seat.from((BaseRequest)seat_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				entitymanager.persist(seat);
				return (K)entitymanager.find(Seat.class, seat.getId());
			}
		});
	}
	public List<K> batchCreate(List<V> seat_requests) throws Exception{
		List<K> seats= new ArrayList<>();
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				int i=0;
				for(V seat_data:seat_requests) {
					K seat = (K)new Seat();
					seat.from((BaseRequest)seat_data);
					if(i++ % BATCH_SIZE == 0) {
						entitymanager.flush();
						entitymanager.clear();
					}
					entitymanager.persist(seat);
					seats.add((K)entitymanager.find(Seat.class, seat.getId()));
					
				}
				return seats;
			}
		});
	}

	public K update(V seat_data) throws Exception{
		K seat = (K)new Seat();
		seat.from((BaseRequest)seat_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaUpdate<Seat> update = builder.createCriteriaUpdate(Seat.class);
				Root<Seat> root = update.from(Seat.class);
				if(seat.getName()!=null) {
					update.set("name", seat.getName());
				}
				update.where(builder.equal(root.get("id"), seat.getId()));
				entitymanager.createQuery(update).executeUpdate();
				return (K)entitymanager.find(Seat.class, seat.getId());
			}
		});
	}

	public void delete(long id) throws Exception{
		PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaDelete<Seat> delete = builder.createCriteriaDelete(Seat.class);
				Root<Seat> root = delete.from(Seat.class);
				delete.where(builder.equal(root.get("id"), id));
				entitymanager.createQuery(delete).executeUpdate();
				return null;
			}
		});
	}
	public K show(V seat_data) throws Exception{
		K seat = (K)new Seat();
		seat.from((BaseRequest)seat_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				return (K)entitymanager.find(Seat.class, seat.getId());
			}
		});
	}
}
