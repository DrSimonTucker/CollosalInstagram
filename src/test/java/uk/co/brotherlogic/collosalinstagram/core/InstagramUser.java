package uk.co.brotherlogic.collosalinstagram.core;

import java.util.LinkedList;
import java.util.List;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.oauth.OAuthService;

public class InstagramUser
{
   public static void main(String[] args)
   {
      InstagramUser user = new InstagramUser("brotherlogic");
      user.buildImages();
   }

   List<String> imageIDs = new LinkedList<String>();
   String userid;

   String username;

   public InstagramUser(String name)
   {
      username = name;
   }

   private void buildImages()
   {
      String id = getUserId();
      System.out.println(id);
   }

   public List<String> getImages()
   {
      if (imageIDs.size() == 0)
         buildImages();
      return imageIDs;
   }

   private String getUserId()
   {
      if (userid == null)
      {
         OAuthService service = new ServiceBuilder().provider(LinkedInApi.class)
               .apiKey(YOUR_API_KEY).apiSecret(YOUR_API_SECRET).build();
         System.out.println("Done");
      }

      return "emptyid";
   }
}
