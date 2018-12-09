package com.demo;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

/***    helloWorld Root Resource*/
@Path("/")
public class Resource{  
	private static ArrayList<DataModel> l1 = new ArrayList<>();
	
	static {
		File file  = new File("C:\\Users\\luo19\\OneDrive\\documents\\GitHub\\movie\\src\\com\\demo\\movieList.txt");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String st;
			try {
				while((st = br.readLine()) != null) {
					DataModel movie = new DataModel();
					
					movie.setName(st.substring(0, st.length()-2));
					
					if(st.charAt(st.length()-1) == '1') {
						movie.setWatched(true);
					}
					else
						movie.setWatched(false);
					
					l1.add(movie);
					
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GET  
	@Produces(MediaType.TEXT_PLAIN)  
	@Path("/get/{name}") 
	public String greet(@PathParam("name") String name){    
		return "GET!!! " + name;  
		}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/list")
	public Response listback(@DefaultValue("Not") @QueryParam("orderby") String Orderby,
			@DefaultValue("undefined")@QueryParam("value") String Value)
	{
		System.out.println("Order is "+Orderby+" Value is "+Value);
		if(Value.equals("undefined") || Orderby.equals("Not"))
		{
			return Response.ok(l1).build();
		}
		else
		{
			ArrayList<DataModel> l2 = new ArrayList<>();
			if(Orderby.equals("Name"))
			{
				String cap = Value.substring(0, 1).toUpperCase() + Value.substring(1);
					for(DataModel x:l1)
					{
						if(x.getName().startsWith(cap))
						{
							l2.add(x);
						}
					}
					return Response.ok(l2).build();
			}
			else {
				return Response.ok(l1).build();
			}
		}
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/put/name")
	public Response updateMovieName(@FormParam("name") String name,@FormParam("Changedname") String Newname)
	{      
		System.out.println("Before: ");
		this.print();
		for(DataModel movie: l1) {
			if(movie.getName().equals(name)) {
				movie.setName(Newname);
				return Response.status(202).entity("Movie changed successfully !!").build();
			}
		}
		System.out.println("After: ");
		this.print();
	    return Response.status(304).encoding("Movie not changed").build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/put/seen")
	public Response updateMovieSeen(@FormParam("name") String name)
	{      
		//System.out.println("Before: ");
		this.print();
		for(DataModel movie: l1) {
			if(movie.getName().equals(name)) {
				if(movie.isWatched())
				{
					return Response.status(202).entity("Movie already seen").build();
				}
				movie.setWatched(true);
				return Response.status(202).entity("Movie changed successfully !!").build();
			}
		}
		System.out.println("After: ");
		this.print();
	    return Response.status(304).encoding("Movie not changed").build();
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
	
	@DELETE
	@Path("/delete/{name}")
	public Response deleteMovieById(@PathParam("name") String name)
	{      
		for(DataModel movie: l1) {
			if((movie.getName().toLowerCase()).equals( name.toLowerCase())) {
				l1.remove(movie);
				System.out.println(name + " Deleted");
				return Response.status(202).entity("Movie deleted successfully !!").build();
			}
		}
	
	    return Response.status(304).entity("Movie not deleted").build();
	}

	
	public void print(){
		for(DataModel movie: l1) {
			System.out.print(movie.getName() +" ");
			System.out.println();
		}
	}
	
}
