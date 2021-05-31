package com.jrorg.bookmyshow.manager;

import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.jrorg.bookmyshow.entity.Ticket;
import com.jrorg.bookmyshow.entity.User;
import com.jrorg.bookmyshow.request.BaseRequest;
import com.jrorg.bookmyshow.request.TicketBookingRequest;
import com.jrorg.bookmyshow.request.UserRequest;
import com.jrorg.bookmyshow.util.ThreadDetails;
import com.jrorg.bookmyshow.util.Utilities;

public class UsersManager<K extends User,V extends UserRequest>  implements BaseManager<K,V>{
	
	public static final int MAX_ALLOWED_BOOKINGS= 6;
	@Override
	public List<K> getList(int page_no, int limit) throws Exception {
		return PersistenceManager.perform(new Transaction<List<K>>() {
			@Override
			public List<K> perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery("SELECT s FROM BMS_Users s ORDER BY s.id DESC",User.class);
				query.setFirstResult((page_no-1)*limit);
				query.setMaxResults(limit);
				List<K> result_list =(List<K>)query.getResultList();
				return result_list;
			}
		});
	}

	@Override
	public K create(V user_request) throws Exception {
		K user = (K)new User();
		user.from((BaseRequest)user_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				user.setAuthToken(Base64.getEncoder().encodeToString(Utilities.generateToken().getBytes()));
				entitymanager.persist(user);
				return (K)entitymanager.find(User.class, user.getId());
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
		K user = (K)new User();
		user.from((BaseRequest)user_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				return (K)entitymanager.find(User.class, user.getId());
			}
		});
	}

	@Override
	public K update(V user_request) throws Exception {
		K user = (K)new User();
		user.from((BaseRequest)user_request);
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaUpdate<User> update = builder.createCriteriaUpdate(User.class);
				Root<User> root = update.from(User.class);
				if(user.getName()!=null) {
					update.set("name", user.getName());
				}
				if(user.getPassword()!=null) {
					update.set("password", user.getPassword());
				}
				if(user.getPhone_number()!=null) {
					update.set("phone_number", user.getPhone_number());
				}
				update.where(builder.equal(root.get("id"), user.getId()));
				entitymanager.createQuery(update).executeUpdate(); 
				return (K)entitymanager.find(User.class, user.getId());
			}
		});
	}

	@Override
	public void delete(long id) throws Exception {
		PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				CriteriaBuilder builder = entitymanager.getCriteriaBuilder();
				CriteriaDelete<User> delete = builder.createCriteriaDelete(User.class);
				Root<User> root = delete.from(User.class);
				delete.where(builder.equal(root.get("id"), id));
				entitymanager.createQuery(delete).executeUpdate();
				return null;
			}
		});
	}
	
	public User findUser(String authtoken) throws Exception {
		return PersistenceManager.perform(new Transaction<K>() {
			@Override
			public K perform(EntityManager entitymanager) {
				Query query = entitymanager.createQuery( "Select u " + "from BMS_Users u " + "where u.auth_token ='" + authtoken+"'",User.class );
				return (K)query.getSingleResult();
			}
		});
	}
	
	public Booking bookTickets(TicketBookingRequest request)throws Exception{
		return PersistenceManager.perform(new Transaction<Booking>() {
			@Override
			public Booking perform(EntityManager entitymanager) {
				try {
					request.setTicket_ids(request.getTicket_ids().subList(0, MAX_ALLOWED_BOOKINGS));
					Booking booking = new Booking();
					booking.setNo_of_seats(request.getTicket_ids().size());
					Show show = new Show();
					show.setId(request.getShow_id());
					booking.setShow(show);
					booking.setUser(ThreadDetails.getCurrentUser());
					booking.setBooked_time(System.currentTimeMillis());
					booking.setStatus(0);
					entitymanager.persist(booking);
					Long booking_id=booking.getId();
					List<Ticket> tickets = entitymanager.createQuery("SELECT t FROM BMS_Tickets t WHERE t.id IN (:values) AND booking_id IN (SELECT b from BMS_Bookings b where b.status='0' and show_id='"+request.getShow_id()+"')",Ticket.class)
					 .setParameter("values", request.getTicket_ids()).getResultList();
					if(tickets!=null && tickets.size()<request.getTicket_ids().size()) {
						entitymanager.createQuery("update BMS_Bookings set status = 2 where id = :booking_id").
						setParameter("booking_id", tickets.get(0).getBooking().getId()).executeUpdate();
					}
					entitymanager.createQuery("update BMS_Tickets  set booking_id = :booking_id where id IN (:values)")
			            .setParameter("booking_id", booking_id)
			            .setParameter("values", request.getTicket_ids())
			            .executeUpdate();
					return entitymanager.find(Booking.class, booking.getId());
				}
				catch(Exception e) {
					Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error occured", e);
					return null;
				}
			}
		});
	}

}
