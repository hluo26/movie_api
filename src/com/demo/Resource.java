package com.demo;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

/***    helloWorld Root Resource*/
@Path("/")
public class Resource{  
	private static HashMap<String,DataModel> h1 = new HashMap<>();

	static {
		Class<?> x = MethodHandles.lookup().lookupClass();
		String path = x.getResource("movieList.txt").getPath();
		
		//In order to format the path correctly (replaces %20 with a space)
		String decodedPath = null;
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file  = new File(decodedPath);
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String movieDetailsStr;
			try {
				while((movieDetailsStr = br.readLine()) != null) {
					DataModel movie = new DataModel();
					
					String[] movieDetailsArray = movieDetailsStr.split("\\|\\|\\|");
					if(movieDetailsArray.length == 4) {
						movie.setName(movieDetailsArray[0]);
						//Checks if the year string can be converted to an integer
						try {
							movie.setYear(Integer.parseInt(movieDetailsArray[1]));
						}catch(Exception e){
							//System.out.println("Year is not an Integer");
						}finally {
							movie.setGenre(movieDetailsArray[2]);
							if(movieDetailsArray[3].equals("1")) 
								movie.setWatched(true);
							else
								movie.setWatched(false);
							//check if there is duplicate movie from the movie list
							if(!h1.containsKey(movieDetailsArray[0]))
							{
								h1.put(movieDetailsArray[0],movie);
							}
							else
							{
								System.out.println("Duplicate");
							}
						}
					}
					else
						System.out.println("Could not Parse movie correctly");
					
					//movie.setName(movieDetailsStr.substring(0, movieDetailsStr.length()-2));
//					if(movieDetailsStr.charAt(movieDetailsStr.length()-1) == '1') {
//						movie.setWatched(true);
//					}
//					else
//						movie.setWatched(false);
									
					
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
	@Produces(MediaType.APPLICATION_JSON)  
	@Path("/get/{name}") 
	public Response greet(@PathParam("name") String name){
		DataModel d1 = new DataModel();
		String decodedname = null;
		try {
			decodedname = URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(h1.size()>0) {
			for(String x:h1.keySet())
			{
				if(x.equals(decodedname))
				{
					d1 = h1.get(x);
				}
			}
		}
		return Response.ok(d1).build();
		}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get/list")
	public Response listback(@DefaultValue("Not") @QueryParam("orderby") String Orderby,
			@DefaultValue("undefined") @QueryParam("value") String Value)
	{
		//System.out.println("Order is "+Orderby+" Value is "+Value);
		
		ArrayList<DataModel> l2 = new ArrayList<>();
		//if not special request for get method, just return the whole movie list
		if(Value.equals("") || Value.equals("undefined") || Orderby.equals("Not"))
		{
			for(String x:h1.keySet())
			{
				l2.add(h1.get(x));
			}
			return Response.ok(l2).build();
		}
		else
		{
			//Capitalize the first letter of the query parameter
			String cap = Value.substring(0, 1).toUpperCase() + Value.substring(1);
			if(Orderby.equals("Name"))
			{
					for(String x:h1.keySet())
					{
						if(x.startsWith(cap))
						{
							l2.add(h1.get(x));
						}
					}
					return Response.ok(l2).build();
			}
			else if(Orderby.equals("Year"))
			{
					for(String x:h1.keySet())
					{
						DataModel y = h1.get(x);
						if((y.getYear() + "").startsWith(cap)){
							l2.add(y);
						}
					}
					return Response.ok(l2).build();
			}
			else if(Orderby.equals("Genre"))
			{
				for(String x:h1.keySet())
				{
					DataModel y = h1.get(x);
					if((y.getGenre() + "").startsWith(cap)){
						l2.add(y);
					}
				}
				return Response.ok(l2).build();
			}
			else if(Orderby.equals("Watched"))
			{
				if(cap.equals("Seen")||cap.equals("True")||cap.equals("Yes")||cap.equals("Y"))
				{
					for(String x:h1.keySet())
					{
						DataModel y = h1.get(x);
						if(y.isWatched())
						{
							l2.add(y);
						}
					}
					return Response.ok(l2).build();
				}
				else if(cap.equals("Unseen")||cap.equals("False")||cap.equals("No")||cap.equals("N"))
				{
					for(String x:h1.keySet())
					{
						DataModel y = h1.get(x);
						if(!y.isWatched())
						{
							l2.add(y);
						}
					}
					return Response.ok(l2).build();
				}
				else
				{
					return Response.ok(l2).build();
				}
			}
			else {
				return Response.ok(l2).build();
			}
		}
	}
	
//	@PUT
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Path("/put/name")
//	public Response updateMovieName(
//			@FormParam("name") String name,
//			@FormParam("year") int year,
//			@FormParam("genre") String genre,
//			@FormParam("watched") Boolean watched)
//	{      
//		System.out.println("Before: ");
//		this.print();
//		for(DataModel movie: l1) {
//			if(movie.getName().equals(name)) {
//				movie.setName(Newname);
//				return Response.status(202).entity("Movie changed successfully !!").build();
//			}
//		}
//		System.out.println("After: ");
//		this.print();
//	    return Response.status(304).encoding("Movie not changed").build();
//	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/put/movie")
	public Response updateMovieName(
			@FormParam("name") String name,
			@FormParam("changedName") String changedName,
			@FormParam("changedYear") int changedYear,
			@FormParam("changedGenre") String changedGenre,
			@FormParam("changedWatched") Boolean changedWatched)
	{      
		System.out.println("Before: ");
		this.print();
		for(String x:h1.keySet())
		{
			//checking duplicate
			if(x.equals(name)) {
				if(h1.containsKey(changedName))
				{
					return Response.status(Response.Status.CONFLICT).build();
				}
				else
				{
					DataModel movie = h1.get(x);
					movie.setName(changedName);
					movie.setYear(changedYear);
					movie.setGenre(changedGenre);
					movie.setWatched(changedWatched);
					
					return Response.ok().build();
				}
			}
		}
		System.out.println("After: ");
		this.print();
	    return Response.status(304).encoding("Movie not updated").build();
	}
	
	/*@PUT
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
	}*/

	
	@POST
	@Path("/post")
	public Response postback(
			@QueryParam("name") String name,
			@QueryParam("year") int year,
			@QueryParam("genre") String genre,
			@QueryParam("watched") Boolean watched
			){
		//checking duplicate
		if(h1.containsKey(name))
		{
			return Response.status(Response.Status.CONFLICT).build();
		}
		else
		{
			DataModel d1 = new DataModel();
			d1.setName(name);
			d1.setYear(year);
			d1.setGenre(genre);
			d1.setWatched(watched);
			h1.put(name,d1);
			return Response.ok().build();
		}
	}
	
	@DELETE
	@Path("/delete")
	public Response deleteMovieById(@DefaultValue("noName") @QueryParam("name") String name)
	{   
		ArrayList<DataModel> l2 = new ArrayList<>();
		if(name != "noName") {
			for(String movie: h1.keySet()) {
				if((movie.toLowerCase()).equals( name.toLowerCase())) {
					h1.remove(movie);
					System.out.println(name + " Deleted");
					for(String x:h1.keySet())
					{
						l2.add(h1.get(x));
					}
					return Response.ok(l2).build();
	//				return Response.status(202).entity("Movie deleted successfully !!").build();
				}
			}
		}
	    return Response.status(304).entity("Movie not deleted").build();
	}

	
	public void print(){
		for(String movie:h1.keySet()) {
			System.out.print(movie +" ");
			System.out.println();
		}
	}
	
}
