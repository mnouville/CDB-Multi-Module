package model;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class Token {

    private String value;
    private static Key secret = MacProvider.generateKey();
    
    public Token() {
      this.value = "";
    }
    
    public Token(String value) {
        this.setValue(value);
    }
    
    public Token(User user) throws UnsupportedEncodingException {
        this.value =  Jwts.builder()
                      .setSubject("users/TzMUocMF4p")
                      .setExpiration(new Date(System.currentTimeMillis()+100000))
                      .claim("login", user.getLogin())
                      .claim("password", user.getPassword())
                      .claim("firstname", user.getFirstname() )
                      .claim("lastname", user.getLastname() )
                      .claim("role", user.getRole())
                      .signWith(SignatureAlgorithm.HS256,secret)
                      .compact();
    }
    
    public Jws<Claims> validateJwtToken() {
      return Jwts.parser().setSigningKey(secret).parseClaimsJws(this.value);
    }
    
    public void destroyToken() {
      this.value = "";
    }
    
    public Integer getRole() {
      return this.validateJwtToken().getBody().get("role",Integer.class);
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
    
    public boolean isValidToken() throws Exception {
      boolean valid = false;
      
      if (this.value == null || this.value.equals("")) {
        throw new Exception("Missing or invalid Authorization header");
      }
  
      try {
          int role = this.getRole();
          if ( role == 1 || role == 2 || role == 3 ) {
            valid = true;
          }
      } catch (NullPointerException e) {
          throw new NullPointerException("Invalid Session");
      }
      
      return valid;
    }
  
}
