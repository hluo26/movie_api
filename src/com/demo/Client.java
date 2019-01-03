package com.demo;

import java.util.ArrayList;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class Client {
	
	public static ArrayList<DataModel> getAllList(javax.ws.rs.client.Client c1)
	{
		Response r1 = c1.target("http://localhost:8080/movie/service/get/list").request().get();
		ArrayList<DataModel> l2 = r1.readEntity(ArrayList.class);
		return l2;
	}
	
	public static Object getByorder(javax.ws.rs.client.Client c1, String order, String value)
	{
		String url = "http://localhost:8080//movie/service/get/list?orderby="+order+"&value="+value;
		Response r1 = c1.target(url).request().get();
		return r1.readEntity(ArrayList.class).get(0);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		javax.ws.rs.client.Client c1 = ClientBuilder.newClient();
		/*These lines of code are using for checking whether each HTTP method works
		 *under Jersey client API
		 */
		//test for get method
		ArrayList<DataModel> l1 = getAllList(c1);
		/*for(int i=0;i<l1.size();i++)
		{
			System.out.println(l1.get(i));
		}*/
		//test for get method with parameter (orderby = name, value = "Dirty Harry")
		Object d1 = getByorder(c1,"Name","Dirty%20Harry");
		System.out.println(d1);
		//test for post method with parameter (name = "aaa", year=2014, genre="Horror",watched=false)
		
	}

}
