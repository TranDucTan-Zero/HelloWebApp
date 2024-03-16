package com.cgm.hello_web_app.restful;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cgm.hello_web_app.dao.ProductDAO;
import com.cgm.hello_web_app.eitities.Product;
@Path("/products")
public class ProdductServiceAPI {
	
	ProductDAO productDAO = new ProductDAO();
	//http://localhost:8080/HelloWebApp/rest/products
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProducts_JSON() {
		List<Product> list = null;
		list = productDAO.getLatestProductList();
		return list;
		
	}
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(Product product) {
        productDAO.addProduct(product);
        return Response.created(URI.create("/products/" + product.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") int id, Product product) {
        boolean updated = productDAO.updateProduct(id, product);
        if (updated) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") int id) {
        boolean deleted = productDAO.deleteProduct(id);
        if (deleted) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}


