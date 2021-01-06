/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.weather;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author Luciana
 */
@Path("/weather")
public class WeatherRecords {

    @GET
    @Path("/{param}")
    public Response showTheRecords(@PathParam("param") String city) {
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric&appid=1bcaa2d234ad1c2c9551ed244e6f67e4";

        String answer = showAnswer(url);

        return Response.status(200).entity(answer).build();
    }

    private String showAnswer(String url) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        String ans = response.readEntity(String.class);
        
        JSONObject obj = new JSONObject(ans);
        if (obj.get("message").toString().equals("city not found"))
            return ("You typed an invalid city");
        else return formatAnswer(obj);
      
       

    }

    private String formatAnswer(JSONObject obj) {
        JSONArray a = obj.getJSONArray("list");
        JSONObject o;
        
        String output = "<br>CURRENT & FORECAST WEATHER<br>USING OPEN WEATHER API<br>";
       
        output += "<br>City: "+obj.getJSONObject("city").get("name");
        output+="<br>Country: "+obj.getJSONObject("city").get("country").toString()+"<br>";
       
        
        for (int i = 0; i < a.length(); i++) {
            o = a.getJSONObject(i);
           
           
           output += "<br>=============================================<br>";
           output+= "<br><b>Date: "+o.get("dt_txt").toString()+"</b>";
           output+= "<br><br>Temperature: "+o.getJSONObject("main").get("temp").toString()+" C";
           output+= "<br>Feels like: "+o.getJSONObject("main").get("feels_like").toString()+" C";
           output+= "<br>Min temperature: "+o.getJSONObject("main").get("temp_min").toString()+" C";
           output+= "<br>Max temp: "+o.getJSONObject("main").get("temp_max").toString()+" C";
           output+= "<br>Humidity: "+o.getJSONObject("main").get("humidity").toString()+"%";
           output+= "<br>Description: "+o.getJSONArray("weather").getJSONObject(0).get("description").toString();
           output+="<br>";
        }
        return output;
        }
}

        
     
    
    

