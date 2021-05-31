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
import com.jrorg.bookmyshow.entity.Booking;
import com.jrorg.bookmyshow.entity.Seat;
import com.jrorg.bookmyshow.entity.Show;
import com.jrorg.bookmyshow.entity.Ticket;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.TicketRequest;

public class TicketsManager<K extends Ticket,V extends TicketRequest> implements BaseManager<K, V>{
	public static final int BATCH_SIZE=100;

	@Override
	public List<K> getList(int page_no, int limit) throws Exception {
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery("SELECT s FROM BMS_Tickets s ORDER BY s.id DESC",Ticket.class);
				query.setFirstResult((page_no-1)*limit);
				query.setMaxResults(limit);
				List<K> result_list =(List<K>)query.getResultList();
				return result_list;
			}
		});
	}
	public K create(V ticket_data) throws Exception{
		K ticket = (K)new Ticket();
		ticket.from((BaseRequest)ticket_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				entitymanager.persist(ticket);
				return (K)entitymanager.find(Ticket.class, ticket.getId());
			}
		});
	}
	public List<K> batchCreate(List<V> ticket_requests) throws Exception{
		List<K> seats= new ArrayList<>();
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				int i=0;
				for(V ticket_data:ticket_requests) {
					K ticket = (K)new Ticket();
					ticket.from((BaseRequest)ticket_data);
					if(i++ % BATCH_SIZE == 0) {
						entitymanager.flush();
						entitymanager.clear();
					}
					entitymanager.persist(ticket);
					seats.add((K)entitymanager.find(Ticket.class, ticket.getId()));
					
				}
				return seats;
			}
		});
	}

	public K update(V ticket_data) throws Exception{
		K ticket = (K)new Ticket();
		ticket.from((BaseRequest)ticket_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaUpdate<Ticket> update = builder.createCriteriaUpdate(Ticket.class);
				Root<Ticket> root = update.from(Ticket.class);
				if(ticket.getBooking()!=null) {
					Booking booking = entitymanager.find(Booking.class, ticket.getBooking().getId());
					update.set("booking",booking);
				}
				if(ticket.getShow()!=null) {
					Show show = entitymanager.find(Show.class, ticket.getShow().getId());
					update.set("show",show);
				}
				if(ticket.getSeat()!=null) {
					Seat seat = entitymanager.find(Seat.class, ticket.getSeat().getId());
					update.set("seat",seat);
				}
				if(ticket.getCost()!=null) {
					update.set("cost",ticket.getCost());
				}
				update.where(builder.equal(root.get("id"), ticket.getId()));
				entitymanager.createQuery(update).executeUpdate();
				return (K)entitymanager.find(Ticket.class, ticket.getId());
			}
		});
	}

	public void delete(long id) throws Exception{
		PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaDelete<Ticket> delete = builder.createCriteriaDelete(Ticket.class);
				Root<Ticket> root = delete.from(Ticket.class);
				delete.where(builder.equal(root.get("id"), id));
				entitymanager.createQuery(delete).executeUpdate();
				return null;
			}
		});
	}
	public K show(V ticket_data) throws Exception{
		K ticket = (K)new Ticket();
		ticket.from((BaseRequest)ticket_data);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				return (K)entitymanager.find(Ticket.class, ticket.getId());
			}
		});
	}

}
