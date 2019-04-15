package validator;

import java.sql.SQLException;

import model.Computer;

import org.springframework.stereotype.Component;

import exceptions.ValidationException;

/**
 * Class Validator.java
 * @author mnouville
 * @version 1.0
 */

@Component
public class Validator {
  
  /**
   * Method for the validation of computer id.
   * @param name String
   */
  public void verifyIdNotNull(int id) throws ValidationException {
    if(id == 0) {
      throw new ValidationException("the id is null or zero");
    }
  }
  
  /**
   * Method for the validation of computer name.
   * @param name String
   */
  public void verifyName(String name) throws ValidationException {
    if(name == null || name.equals("")) {
      throw new ValidationException("the name is null or empty");
    }
  }
  
  /**
   * Method for the validation of computer introduced and discontinued dates.
   * @param computer Computer
   */
  public void verifyIntroBeforeDisco(Computer computer) throws ValidationException {
    if(computer.getDiscontinued() != null && (computer.getIntroduced() == null || !computer.getIntroduced().before(computer.getDiscontinued()))) {
      throw new ValidationException("the discontinued date is before the introduction");
    }
  }
  
  /**
   * Method for the validation of computer not null.
   * @param computer Computer
   */
  public void verifyComputerNotNull(Computer computer) throws ValidationException {
    if(computer == null) {
      throw new ValidationException("the computer is null");
    }
  }
  
  public void verifyComputer(Computer c) throws ValidationException, SQLException {
    this.verifyIdNotNull(c.getId());
    this.verifyName(c.getName());
    this.verifyIntroBeforeDisco(c);
    this.verifyComputerNotNull(c);
  }
}
