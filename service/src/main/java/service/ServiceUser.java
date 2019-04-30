package service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import dto.UserDto;

public interface ServiceUser {

  UserDto getUser(String login) throws SQLException;
  
  void addUser(UserDto user) throws SQLException;
  
  boolean userExists(String login, String password) throws SQLException, InvalidKeyException, NoSuchAlgorithmException;
  
  int getMaxId() throws SQLException;
 
  String hashPass(String s) throws NoSuchAlgorithmException, InvalidKeyException;
}
