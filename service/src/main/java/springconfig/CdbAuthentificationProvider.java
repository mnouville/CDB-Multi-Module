package springconfig;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import service.ServiceUser;

@Component
public class CdbAuthentificationProvider implements AuthenticationProvider {
  
  @Autowired
  private ServiceUser serviceUser;
  
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      String login = authentication.getName();
      String password = authentication.getCredentials().toString();
      
      try {
        if (this.serviceUser.userExists(login,password)) {
          return new UsernamePasswordAuthenticationToken(login, password, new ArrayList<>());
        } else {
          return null;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (InvalidKeyException e) {
        e.printStackTrace();
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
      
      return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
