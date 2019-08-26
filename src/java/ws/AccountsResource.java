/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.google.gson.Gson;
import dao.UserDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.User;

/**
 * REST Web Service
 *
 * @author Junior
 */
@Path("accounts")
public class AccountsResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AccountsResource
     */
    public AccountsResource() {
        
    }

    //TESTING GET WITH PARAMETERS
    @GET
    @Path("get/test/{word}")
    @Produces("text/html")
    public String getTest(@PathParam("word") String word) {
        return "Typed Word: <span style='color:red'>"+word+"</span>";
    }

    //TESTING GET WITH JSON
    @GET
    @Path("get/user/{login}")
    @Produces("application/json")
    public String getUser(@PathParam("login") String login){
        User user = new User();
        user.setLogin(login);
        
        UserDAO dao = new UserDAO();
        user = dao.search(user);
        
        Gson g = new Gson(); //Convert to Json
        
        return g.toJson(user);
    }
    
    //TESTING GET WITH JSON - LISTING AND STORING IN FILE.json
    @GET
    @Path("get/listing")
    @Produces("application/json")
    public String getListing(){
        List<User> list = new ArrayList<User>();
        
        UserDAO dao = new UserDAO();
        list = dao.listing(); //Listing logins
        
        Gson gson = new Gson();
        
        String json = gson.toJson(list); //String receiving the arraylist
        
        try{
            FileWriter writer = new FileWriter("G:\\file.json"); //Storing content in file.json
            writer.write(json);
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        
        return json;
    }
    
    //SEND POST - INSERT USERS
    @POST
    @Consumes("application/json")
    @Path("post/user/insert")
    public boolean insert(String content){
        Gson g = new Gson();
        User user = (User) g.fromJson(content, User.class);
        UserDAO dao = new UserDAO();
        
        return dao.insert(user);
    }
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
        
    }
}
