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
import com.jrorg.bookmyshow.entity.Booking;
import com.jrorg.bookmyshow.entity.Show;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.BookingRequest;

public class BookingsManager<K extends Booking,V extends BookingRequest> implements BaseManager<K, V>{

	@Override
	public List<K> getList(int page_no, int limit) throws Exception {
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery("SELECT s FROM BMS_Bookings s ORDER BY s.id DESC",Booking.class);
				query.setFirstResult((page_no-1)*limit);
				query.setMaxResults(limit);
				List<K> result_list =(List<K>)query.getResultList();
				return result_list;
			}
		});
	}

	@Override
	public K create(V user_request) throws Exception {
		K booking = (K)new Booking();
		booking.from((BaseRequest)user_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				if(booking.getBooked_time()==null) {
					booking.setBooked_time(System.currentTimeMillis());
				}
				if(booking.getStatus()==null) {
					booking.setStatus(0);
				}
				entitymanager.persist(booking);
				return (K)entitymanager.find(Booking.class, booking.getId());
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
		K booking = (K)new Booking();
		booking.from((BaseRequest)user_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				return (K)entitymanager.find(Booking.class, booking.getId());
			}
		});
	}

	@Override
	public K update(V user_request) throws Exception {
		K booking = (K)new Booking();
		booking.from((BaseRequest)user_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaUpdate<Booking> update = builder.createCriteriaUpdate(Booking.class);
				Root<Booking> root = update.from(Booking.class);
				if(booking.getNo_of_seats()!=null) {
					update.set("no_of_seats", booking.getNo_of_seats());
				}
				if(booking.getBooked_time()!=null) {
					update.set("language", booking.getBooked_time());
				}
				if(booking.getStatus()!=null) {
					update.set("status", booking.getStatus());
				}
				if(booking.getShow()!=null) {
					Show show = entitymanager.find(Show.class, booking.getShow().getId());
					update.set("show",show);
				}
				update.where(builder.equal(root.get("id"), booking.getId()));
				entitymanager.createQuery(update).executeUpdate(); 
				return (K)entitymanager.find(Booking.class, booking.getId());
			}
		});
	}

	@Override
	public void delete(long id) throws Exception {
		PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaDelete<Booking> delete = builder.createCriteriaDelete(Booking.class);
				Root<Booking> root = delete.from(Booking.class);
				delete.where(builder.equal(root.get("id"), id));
				entitymanager.createQuery(delete).executeUpdate();
				return null;
			}
		});
	}

}
