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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class ControllerRestUser {

  private static final Logger LOG = LoggerFactory.getLogger(ControllerRestUser.class);
  
  @Autowired
  private ServiceUser serviceUser;
  
  @Autowired
  private MapperDto mapper;
  
  @PostMapping(path = "/login")
  public ResponseEntity<String> authenticate(@RequestBody UserDto user) 
                                 throws SQLException, ParseException, IOException, ServletException {
      Token token = new Token();
      
      try {
        if (this.serviceUser.userExists(user.getLogin(),user.getPassword())) {
            user = this.serviceUser.getUser(user.getLogin());
            token = new Token(this.mapper.dtoToUser(user));
        } else {
          return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
      } catch (SQLException e) {
        LOG.error("SQL Exception : " + e.toString());
      } catch (InvalidKeyException e) {
        LOG.error("Invalid Key Exception : " + e.toString());
      } catch (NoSuchAlgorithmException e) {
        LOG.error("No Such Algorithm Exception : " + e.toString());
      }  

      return new ResponseEntity<String>("{ \"token\":\"" + token.getValue() + "\"}",HttpStatus.OK);
  }
  
  @PostMapping(path = "/refreshtoken")
  public ResponseEntity<String> refreshToken(HttpServletRequest req) throws Exception {
      System.out.println(req.getHeader("authorization"));  
      Token token = new Token(req.getHeader("authorization"));
      if ( token.isValidToken()) {
          token = token.refreshToken();
          System.out.println("NOUVEAU TOKEN : " + token.getValue());
          return new ResponseEntity<String>("{ \"token\":\"" + token.getValue() + "\"}",HttpStatus.OK);
      } else {
          return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
      }
      
  }
  
  @PostMapping(path = "/logout")
  public String logout(HttpServletRequest req) {
      Token token = new Token();
      return token.getValue();
  }
  
  @PostMapping(path = "/register") 
  public void register(@RequestBody UserDto user) throws SQLException {
      this.serviceUser.addUser(user);
  }
  
}
