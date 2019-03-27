package dao;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import model.User;

public interface UserDao {

  User getUser(String login) throws SQLException;
  
  boolean userExits(String login, String password) throws SQLException, InvalidKeyException, NoSuchAlgorithmException;
  
  String hashPass(String s) throws NoSuchAlgorithmException, InvalidKeyException;
}
