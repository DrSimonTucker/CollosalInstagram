package uk.co.brotherlogic.collosalinstagram.core;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InstagramUser
{
   public static void main(String[] args)
   {
      InstagramUser user = new InstagramUser("brotherlogic");
      user.buildImages();
   }

   String accessToken;

   List<InstagramPhoto> photos = new LinkedList<InstagramPhoto>();
   String userid;
   String username;

   public InstagramUser(String name)
   {
      username = name;
   }

   private void buildImages()
   {
      String id = getUserId();

      try
      {
         String res = "";
         BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(
               "https://api.instagram.com/v1/users/" + userid + "/media/recent/?access_token="
                     + accessToken).openStream()));
         for (String line = reader.readLine(); line != null; line = reader.readLine())
            res += line;
         reader.close();

         JSONParser parser = new JSONParser();
         JSONObject obj = (JSONObject) parser.parse(res);

         for (Object obj2 : ((JSONArray) obj.get("data")))
         {
            JSONObject caption = (JSONObject) ((JSONObject) obj2).get("caption");
            String captionText = (String) caption.get("text");
            JSONObject imageLink = (JSONObject) ((JSONObject) obj2).get("images");
            String imageRef = (String) ((JSONObject) imageLink.get("standard_resolution"))
                  .get("url");
            photos.add(new InstagramPhoto(captionText, imageRef));
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      catch (ParseException e)
      {
         e.printStackTrace();
      }
   }

   public Room buildRooms()
   {
      buildImages();
      Collections.shuffle(photos);

      Map<String, String> reverseMap = new TreeMap<String, String>();
      reverseMap.put("East", "West");
      reverseMap.put("West", "East");
      reverseMap.put("North", "South");
      reverseMap.put("South", "North");

      Room currRoom = new Room(photos.get(0), new TreeMap<String, Room>());
      Room firstRoom = currRoom;

      for (int i = 1; i < photos.size(); i++)
      {
         Room newRoom = new Room(photos.get(i), new TreeMap<String, Room>());
         while (currRoom.getFreeDirections().size() == 0)
         {
            List<String> nDirections = new LinkedList<String>(currRoom.links.keySet());
            Collections.shuffle(nDirections);
            currRoom = currRoom.traverse(nDirections.get(0));
         }
         List<String> nDirections = currRoom.getFreeDirections();
         Collections.shuffle(nDirections);
         String direction = nDirections.get(0);

         currRoom.addTraverse(direction, newRoom);
         newRoom.addTraverse(reverseMap.get(direction), currRoom);

         // 50% chance we stay where we are
         if (Math.random() > 0.5)
            currRoom = newRoom;

      }

      return firstRoom;

   }

   public List<InstagramPhoto> getImages()
   {
      if (photos.size() == 0)
         buildImages();
      return photos;
   }

   private String getUserId()
   {
      try
      {
         if (userid == null)
            Desktop
                  .getDesktop()
                  .browse(
                        new URI(
                              "https://instagram.com/oauth/authorize/?client_id=31281b4e487041cc8f68e18f5a38f619&redirect_uri=http://www.dcs.shef.ac.uk/~sat&response_type=token"));
      }
      catch (URISyntaxException e)
      {
         e.printStackTrace();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      accessToken = JOptionPane.showInputDialog("Enter the access code");

      try
      {
         String res = "";
         BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(
               "https://api.instagram.com/v1/users/search?q=" + username + "&access_token="
                     + accessToken).openStream()));
         for (String line = reader.readLine(); line != null; line = reader.readLine())
            res += line;
         reader.close();

         JSONParser parser = new JSONParser();
         JSONObject obj = (JSONObject) parser.parse(res);

         JSONObject obj2 = (JSONObject) ((JSONArray) obj.get("data")).get(0);
         userid = (String) obj2.get("id");
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      catch (org.json.simple.parser.ParseException e)
      {
         e.printStackTrace();
      }
      return userid;
   }
}
