package com.demo;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/***    helloWorld Root Resource*/
@Path("/")
public class Resource{  
	private static ArrayList<DataModel> l1 = new ArrayList<>();
	@GET  
	@Produces(MediaType.TEXT_PLAIN)  
	@Path("/get/{name}") 
	public String greet(@PathParam("name") String name){    
		return "GET!!! " + name;  
		}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/list")
	public Response listback()
	{
			for(int i=0;i<l1.size();i++)
			{
				DataModel x = l1.get(i);
				System.out.println("name is "+x.getName());
				System.out.println("year is "+x.getYear());
				System.out.println("genre is "+x.getGenre());
			}
		return Response.status(Response.Status.OK).entity(l1).build();
	}
	
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/put")
	public String change(String x) {
		return "PUT!!! " + x;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/post")
	public String postback(
			@FormParam("name") String name,
			@FormParam("year") int year,
			@FormParam("genre") String genre,
			@FormParam("watched") Boolean watched
			){
		DataModel d1 = new DataModel();
		d1.setName(name);
		d1.setYear(year);
		d1.setGenre(genre);
		d1.setWatched(watched);
		l1.add(d1);
		return "POST!!! " + name + " " + year + " " + genre;
	}
	}