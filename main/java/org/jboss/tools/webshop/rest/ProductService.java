package org.jboss.tools.webshop.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.tools.webshop.model.Product;

@Path("/products")
@RequestScoped

public class ProductService {
	
	@Inject
	private EntityManager em;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAllEvents() {
		final List<Product> results =
				em.createQuery(
						"select p from Product p order by p.name").getResultList();
		return results;
	}
}
