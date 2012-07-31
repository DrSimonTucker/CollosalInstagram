package uk.co.brotherlogic.collosalinstagram.core;

import java.util.Map;
import java.util.TreeMap;

public class Room
{
   String imageID;

   public String getDescription()
   {
      String description = "You are in " + getInstagramTitle() + " there are " + getExits().size()
            + " exits.";
      String firstExit = getExits().keySet().iterator().next();
      description += "You can go " + firstExit;
      for (String exit : getExits().keySet())
         if (!exit.equals(firstExit))
            description += ", " + exit;
   }

   private Map<String, Room> getExits()
   {
      Map<String, Room> exitMap = new TreeMap<String, Room>();
      return exitMap;
   }

   private Image getInstagramImage()
   {

   }

   private String getInstagramTitle()
   {

   }
}
