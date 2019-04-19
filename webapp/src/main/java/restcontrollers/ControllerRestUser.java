package restcontrollers;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.UserDto;
import mappers.MapperDto;
import model.Token;
import service.ServiceUser;

@RestController
@RequestMapping("/api/users/")
public class ControllerRestUser {

  private static final Logger LOG = LoggerFactory.getLogger(ControllerRestUser.class);
  
  @Autowired
  private ServiceUser serviceUser;
  
  @Autowired
  private MapperDto mapper;
  
  @PostMapping(path = "/login")
  public String authenticate(@RequestBody UserDto user, HttpServletRequest req ) 
                                 throws SQLException, ParseException, IOException, ServletException {
      Token token = new Token();
      System.out.println(req.getHeader("authorization"));
      try {
        if (this.serviceUser.userExists(user.getLogin(),user.getPassword())) {
            user = this.serviceUser.getUser(user.getLogin());
            token = new Token(this.mapper.dtoToUser(user));
        } 
      } catch (SQLException e) {
        LOG.error("SQL Exception : " + e.toString());
      } catch (InvalidKeyException e) {
        LOG.error("Invalid Key Exception : " + e.toString());
      } catch (NoSuchAlgorithmException e) {
        LOG.error("No Such Algorithm Exception : " + e.toString());
      }  
      
      return token.getValue();
  }
  
}
