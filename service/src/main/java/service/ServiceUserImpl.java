package service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import model.User;

@Service
public class ServiceUserImpl implements ServiceUser {

  @Autowired
  private UserDao userDao;

  @Override
  @Transactional
  public User getUser(String login) throws SQLException {
    return this.userDao.getUser(login);
  }
  
  @Override
  @Transactional
  public boolean userExists(String login, String password) throws SQLException, InvalidKeyException, NoSuchAlgorithmException  {
    return this.userDao.userExits(login,password);
  }
  
  @Override
  @Transactional
  public String hashPass(String s) throws NoSuchAlgorithmException, InvalidKeyException {
    return this.userDao.hashPass(s);
  }
}
