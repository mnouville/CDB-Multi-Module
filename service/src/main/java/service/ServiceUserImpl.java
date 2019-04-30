package service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import dto.UserDto;
import exceptions.ValidationException;
import mappers.MapperDto;
import validator.Validator;

@Service
public class ServiceUserImpl implements ServiceUser {

  @Autowired
  private UserDao userDao;
  
  @Autowired
  private MapperDto mapper;
  
  @Autowired
  private Validator validator;

  @Override
  @Transactional
  public UserDto getUser(String login) throws SQLException {
    return this.mapper.userToUserDto(this.userDao.getUser(login));
  }
  
  @Override
  @Transactional
  public void addUser(UserDto user) throws SQLException {
    try {
      this.validator.verifyUser(this.mapper.dtoToUser(user));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
    this.userDao.addUser(this.mapper.dtoToUser(user));
  }
  
  @Override
  @Transactional
  public boolean userExists(String login, String password) throws SQLException, InvalidKeyException, NoSuchAlgorithmException  {
    return this.userDao.userExits(login,password);
  }
  
  @Override
  @Transactional
  public int getMaxId() throws SQLException {
    return this.userDao.getMaxId();
  }
  
  @Override
  @Transactional
  public String hashPass(String s) throws NoSuchAlgorithmException, InvalidKeyException {
    return this.userDao.hashPass(s);
  }
}
