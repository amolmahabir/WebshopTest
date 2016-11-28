package org.jboss.tools.webshop.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.tools.webshop.model.Customer;



@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class CustomerService {

	@Inject
	private EntityManager em;
	private List<Customer> customer ;

	@GET
	public Response getProduct() {
		List<Customer> result = em.createQuery("SELECT c FROM customer c").getResultList();
		customer = result;
		System.out.println(customer);
		return Response.ok(result).build();
		
	}



	@POST
	@Consumes("application/json")
	public Response create(Customer entity) { //was Product entity
		customer = em.createQuery("SELECT c FROM customer c").getResultList();
		Customer cust = em.merge(entity);
		
		System.out.println(cust.getFirstname() + cust.getLastname());
		return Response.created(
				UriBuilder.fromResource(CartService.class)
				.path(String.valueOf(cust.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9]*}")
	@Consumes("application/json")
	public Response deleteById(@PathParam("id") Long id) {
		Customer entity = em.find(Customer.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		
		return Response.noContent().build();
	}

}
