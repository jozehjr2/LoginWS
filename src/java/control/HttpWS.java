/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import model.User;

/**
 *
 * @author Junior
 * 
 * CONSUMER OF WEB SERVICE
 */
public class HttpWS {
    
    private final String USER_AGENT = "Google Chrome 76.0";
    
    public static void main(String[] args) throws Exception{
        //POST
        //insertPost();
        
        //GET
        //showUser();
        
        //DELETE
        //deleteUser();
        
        //PUT
        updateUser();
    }
    
    //SHOW USER VIA GET
    private static void showUser() throws Exception{
        HttpWS http = new HttpWS(); //Instantiate the class itself - HTTP EXAMPLE
               
        Gson g = new Gson();
        User u = new User();
        Type userType = new TypeToken<User>(){}.getType();
        Scanner enterKey = new Scanner(System.in);
        
        System.out.println("\nType the login that you want to find: ");
        String login = enterKey.nextLine();
                
        //test GET
        String callWS = "http://localhost:8080/LoginWS/webresources/accounts/get/user/"+login;
        String json = http.sendGet(callWS,"GET");
        u = g.fromJson(json, userType);

        System.out.println("Nome: "+u.getName()+" | Login: "+u.getLogin());
    }
    
    //INSERT VIA POST
    private static void insertPost() throws Exception{
        HttpWS http = new HttpWS(); //Instantiate the class itself - HTTP EXAMPLE
               
        Gson g = new Gson();
        User user = new User();
        Type userType = new TypeToken<User>(){}.getType();
        Scanner enterKey = new Scanner(System.in);
        
        System.out.println("\nType your name: ");
        user.setName(enterKey.nextLine());
        
        System.out.println("\nType your login: ");
        user.setLogin(enterKey.nextLine());
        
        System.out.println("\nType your email: ");
        user.setEmail(enterKey.nextLine());
        
        System.out.println("\nType your password: ");
        user.setPassword(enterKey.nextLine());
        
        System.out.println("\nWhat your type of account? (Admin or Comum) ");
        user.setType(enterKey.nextLine());

        String json = g.toJson(user,userType);
        String url = "http://localhost:8080/LoginWS/webresources/accounts/post/user/insert";

        http.sendPost(url, json, "POST");
    }
    
    //HTTP PUT request
    public static void updateUser() throws Exception{
        HttpWS http = new HttpWS();
               
        Gson g = new Gson();
        User user = new User();

        Type userType = new TypeToken<User>(){}.getType();
        Scanner enterKey = new Scanner(System.in);
        
        //PUT
        System.out.println("\n --- UPDATE \n");
        System.out.println("\nName: ");
        user.setName(enterKey.nextLine());
        
        System.out.println("\nLogin: ");
        user.setLogin(enterKey.nextLine());
        
        System.out.println("\nEmail: ");
        user.setEmail(enterKey.nextLine());
        
        System.out.println("\nPassword: ");
        user.setPassword(enterKey.nextLine());
        
        System.out.println("\nType of account? (Admin or Comum) ");
        user.setType(enterKey.nextLine());
        
        String json = g.toJson(user,userType);
        String url = "http://localhost:8080/LoginWS/webresources/accounts/user/update";

        http.sendPost(url, json, "PUT");
    }
    
    //HTTP DELETE request
    public static void deleteUser() throws Exception{
        HttpWS http = new HttpWS();
               
        Gson g = new Gson();
        User u = new User();

        Type userType = new TypeToken<User>(){}.getType();
        Scanner enterKey = new Scanner(System.in);
        
        System.out.println("\nType the login that you want to delete: ");
        String login = enterKey.nextLine();        

        //DELETE
        String callWS = "http://localhost:8080/LoginWS/webresources/accounts/user/delete/"+login;
        String ret = http.sendGet(callWS,"DELETE");

        System.out.println(ret);
    }
    
    //HTTP GET request
    private String sendGet(String url, String method) throws Exception {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //Add request header
            con.setRequestMethod(method);
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();

            //print result
            return response.toString();

    }
    
    //HTTP POST request
    private void sendPost(String url, String urlParameters, String method) throws Exception {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //Add request header
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
    }
    
}
