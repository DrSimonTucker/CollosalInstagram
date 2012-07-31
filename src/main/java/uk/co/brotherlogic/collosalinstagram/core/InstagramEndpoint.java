package uk.co.brotherlogic.collosalinstagram.core;

import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;

public class InstagramEndpoint extends DefaultApi20
{

   @Override
   public String getAccessTokenEndpoint()
   {
      return "http://your-redirect-uri#access_token=689612.f59def8.4fb19095359546d8a23f746934107248";
   }

   @Override
   public String getAuthorizationUrl(OAuthConfig arg0)
   {
      return "https://api.instagram.com/oauth/authorize/?client_id=31281b4e487041cc8f68e18f5a38f619&redirect_uri=http://www.dcs.shef.ac.uk/~sat&response_type=code";
   }

}
