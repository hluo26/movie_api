package com.demo;
import java.io.*;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response listback()
	{
		if(l1.size()>=1)
		{
			for(int i=0;i<l1.size();i++)
			{
				DataModel x = l1.get(i);
				System.out.println("name is "+x.getName());
				System.out.println("year is "+x.getYear());
				System.out.println("genre is "+x.getGenre());
			}
		}
		return Response.ok(l1).build();
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
	
	@DELETE
	@Path("/delete/{name}")
	public Response deleteMovieById(@PathParam("name") String name)
	{      
		System.out.println("Before: ");
		this.print();
		for(DataModel movie: l1) {
			if(movie.getName() == name) {
				l1.remove(movie);
				return Response.status(202).entity("Movie deleted successfully !!").build();
			}
		}
		System.out.println("After: ");
		this.print();
	    return Response.status(304).encoding("Movie not deleted").build();
	}
	
	public void print(){
		for(DataModel movie: l1) {
			System.out.print(movie.getName() +" ");
			System.out.println();
		}
	}
	
}
