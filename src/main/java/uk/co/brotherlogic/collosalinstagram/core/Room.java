package uk.co.brotherlogic.collosalinstagram.core;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class Room
{
   InstagramPhoto imageID;
   Map<String, Room> links;

   public Room(InstagramPhoto mainPhoto, Map<String, Room> links)
   {
      imageID = mainPhoto;
      this.links = new TreeMap<String, Room>(links);
   }

   public void addTraverse(String direction, Room rm)
   {
      links.put(direction, rm);
   }

   public String getDescription()
   {
      String description = "You are in \"" + getInstagramTitle() + "\" there are "
            + getExits().size() + " exits. ";
      if (getExits().size() > 0)
      {
         String firstExit = getExits().keySet().iterator().next();
         description += "You can go " + firstExit;
         for (String exit : getExits().keySet())
            if (!exit.equals(firstExit))
               description += ", " + exit;
      }
      return description;
   }

   private Map<String, Room> getExits()
   {
      return links;
   }

   public List<String> getFreeDirections()
   {
      List<String> directions = new LinkedList<String>();
      for (String direction : new String[]
      { "East", "West", "South", "North" })
         if (!links.keySet().contains(direction))
            directions.add(direction);
      return directions;
   }

   public BufferedImage getInstagramImage() throws IOException
   {
      return ImageIO.read(new URL(imageID.photoID));
   }

   private String getInstagramTitle()
   {
      return imageID.caption;
   }

   public Room traverse(String direction)
   {
      if (links.containsKey(direction))
         return links.get(direction);
      else
         return this;
   }
}
