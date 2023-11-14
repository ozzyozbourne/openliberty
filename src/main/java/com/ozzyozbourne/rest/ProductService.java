package com.ozzyozbourne.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@Path("/products")
@ApplicationScoped
public class ProductService {

    private final Logger log = Logger.getLogger("Product-Service");
    private final List<Product> productList = new ArrayList<>(List.of(new Product(1, "1"), new Product(2, "2")));

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts(){
        return productList;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(Product product){
        log.info("Creating the product");
        productList.add(product);
        return Response.status(Response.Status.CREATED)
                .entity("New product created -> " + product).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(Product product){
        log.info("Updating a Product");
        for (final Product product1: productList){
            if(product1.getId().equals(product.getId())){
                product1.setName(product.getName());
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("Product updated with id -> " + product.getId()).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Product with id -> "+product.getId()+" does not exist" ).build();
    }

    @DELETE
    @Path("products/{id}")
    public Response deleteProduct(@PathParam("id") Integer id){
        log.info("Deleting a Product");
        for(int i = 0; i < productList.size(); i++){
            if(productList.get(i).getId().equals(id)){
                productList.remove(id.intValue());
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("Product with id " + id + " deleted").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Product with id -> " + id + " does not exist").build();
    }
}
