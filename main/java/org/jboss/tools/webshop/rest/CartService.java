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

import org.jboss.tools.webshop.model.CartItem;
import org.jboss.tools.webshop.model.Product;


@Path("/cart")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class CartService {

	@Inject
	private EntityManager em;
	private List<CartItem> cartlist ;

	@GET
	public Response getProduct() {
		List<CartItem> result = em.createQuery("SELECT c FROM CartItem c").getResultList();
		cartlist = result;
		System.out.println(cartlist);
		return Response.ok(result).build();
		
	}



	@POST
	@Consumes("application/json")
	public Response create(CartItem entity) { //was Product entity
		cartlist = em.createQuery("SELECT c FROM CartItem c").getResultList();
		if (!cartlist.isEmpty()){
			for(CartItem it:cartlist){
				if (it.getName().equals(entity.getName())){
					
					
					entity.setAmount(it.getAmount()+1);;
					System.out.println(entity.getAmount());
				} else System.out.println("Er gebeurt helemaal niks!");
			}
		}
		
		CartItem item = em.merge(entity);
		
		System.out.println(item.getName() + item.getAmount());
		return Response.created(
				UriBuilder.fromResource(CartService.class)
				.path(String.valueOf(item.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9]*}")
	@Consumes("application/json")
	public Response deleteById(@PathParam("id") Long id) {
		CartItem entity = em.find(CartItem.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		
		return Response.noContent().build();
	}

}
