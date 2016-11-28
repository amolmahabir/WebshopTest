
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

import org.jboss.tools.webshop.model.Purchase;



@Path("/purchase")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class PurchaseService  {

	@Inject
	private EntityManager em;
	private List<Purchase> purchase ;

	@GET
	public Response getProduct() {
		List<Purchase> result = em.createQuery("SELECT p FROM purchase p").getResultList();
		purchase = result;
		System.out.println(purchase);
		return Response.ok(result).build();

	}



	@POST
	@Consumes("application/json")
	public Response create(Purchase entity) { //was Product entity
		purchase = em.createQuery("SELECT p FROM purchase p").getResultList();
		Purchase purch = em.merge(entity);
		return Response.created(
				UriBuilder.fromResource(CartService.class)
				.path(String.valueOf(purch.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9]*}")
	@Consumes("application/json")
	public Response deleteById(@PathParam("id") Long id) {
		Purchase entity = em.find(Purchase.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);

		return Response.noContent().build();
	}

}
