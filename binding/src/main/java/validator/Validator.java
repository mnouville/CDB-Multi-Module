package validator;

import java.sql.SQLException;

import model.Computer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import exceptions.ValidationException;
import service.ServiceCompany;

/**
 * Class Validator.java
 * @author mnouville
 * @version 1.0
 */

@Component
public class Validator {
  
  @Autowired
  private ServiceCompany serviceCompany;
  
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
  
  /**
   * Method for the validation of company id Parameter.
   * @param id int
   */
  public void verifyValidCompanyId(int id) throws SQLException, ValidationException {
    try {
      // check if the id is a valid company that exist in the table company
      if (!serviceCompany.companyExist(id)) {
        throw new ValidationException("the computer is null");
      } 
    } catch (NumberFormatException nfe) {
      throw new ValidationException("the computer is null");
    }
  }
  
  public void verifyComputer(Computer c) throws ValidationException, SQLException {
    this.verifyIdNotNull(c.getId());
    this.verifyName(c.getName());
    this.verifyIntroBeforeDisco(c);
    this.verifyComputerNotNull(c);
    this.verifyValidCompanyId(c.getCompany().getId());
  }
}
