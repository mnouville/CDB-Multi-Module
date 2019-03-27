package dao;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.zaxxer.hikari.HikariDataSource;

import model.User;

@Repository
public class UserDaoImpl implements UserDao {
  
  private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);
  
  private final String get = "from User where login = :login";
  private final String count = "SELECT COUNT(id) FROM User";
  
  private HikariDataSource dataSource;
  private SessionFactory sessionFactory;
  private Session session;

  /**
   * Method that return the connection of Hikari
   * @return the connection to the database
   */
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
  
  /**
   * Constructor of ComputerDaoImpl.
   * 
   * @param daoFactory DaoFactory
   */
  UserDaoImpl(HikariDataSource dataSource, SessionFactory sessionFactory ) { 
    this.dataSource = dataSource;
    this.sessionFactory = sessionFactory;
    this.session = this.sessionFactory.openSession();
  }
  
  public void validateSession() {
    if(!this.session.isOpen()) {
      this.session = this.sessionFactory.openSession();
    }
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public User getUser(String login) throws SQLException {
    validateSession();
    Transaction tx = this.session.getTransaction();
    
    try {
      tx = this.session.beginTransaction();
      Query<User> query = this.session.createQuery(get);
      query.setParameter("login", login);
      List<User> result = (List<User>) query.list();
      return result.get(0);
    } catch (TransactionException hibernateException) {
      try {
        tx.rollback();
      } catch(RuntimeException runtimeEx){
        LOG.error("Couldn’t Roll Back Transaction", runtimeEx);
      }
      hibernateException.printStackTrace();
    } finally {
      this.session.close();
    }  
    
    return null;
  }
  
  @SuppressWarnings("rawtypes")
  @Override
  public boolean userExits(String login, String password) throws SQLException, InvalidKeyException, NoSuchAlgorithmException {
    LOG.info("Connection requested");
    String hashPassword = hashPass(password);
    validateSession();
    Query query = this.session.createQuery(count + " where login like '" + login + "' and password like '" + hashPassword + "'");
    return (Integer.parseInt(query.list().get(0).toString())>0);
  }
  
  public String hashPass(String s) throws NoSuchAlgorithmException, InvalidKeyException {
    String key = System.getenv("SHA256");
    Mac hasher = Mac.getInstance("HmacSHA256");
    hasher.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));
    byte[] hash = hasher.doFinal(s.getBytes());
    DatatypeConverter.printHexBinary(hash);
    return(DatatypeConverter.printBase64Binary(hash));
  }

}
