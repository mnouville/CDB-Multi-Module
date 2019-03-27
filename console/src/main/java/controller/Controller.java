package controller;

import dnl.utils.text.table.TextTable;
import exceptions.ValidationException;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import model.Company;
import model.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import service.ServiceCompany;
import service.ServiceComputer;
import validator.Validator;
//import springconfig.SpringConfig;
import view.View;

/**
 * Class that implement the console interface for the Application.
 * 
 * @author mnouville
 * @version 1.0
 */

@Component
public class Controller {

  @Autowired
  private ServiceComputer serviceComputer;
  
  @Autowired
  private ServiceCompany serviceCompany;
  
  @Autowired
  private Validator validator;
  
  private static final Logger LOG = LoggerFactory.getLogger(Controller.class);
  private View view;
  private Scanner sc;
  private static final String ENUM_LIST = "ADD,DELETE,UPDATE,GET,GETALL,EXIT";
  private AnnotationConfigApplicationContext vApplicationContext;
  /**
   * Main of the class Controller.
   * 
   * @param args String[]
   * @throws ValidationException 
   */
  public static void main(String[] args) throws SQLException, ValidationException {
      Controller ctrl = new Controller();
      ctrl.launchMenu();
  }

  /**
   * Constructor of the class View.
   * 
   * @param daoFactory DaoFactory
   */
  public Controller() {
    //this.vApplicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
    this.setView(new View());
  }

  /**
   * Method that display in console the Main Menu of the application.
   * @throws ValidationException 
   * 
   */
  public void launchMenu() throws SQLException, ValidationException {
    this.getView().displayMenu();

    sc = new Scanner(System.in);
    switch (sc.nextLine()) {
      case "1":
        launchMenuCompany();
        break;
      case "2":
        launchMenuComputer();
        break;
      case "3":
        vApplicationContext.close();
        System.exit(0);
        break;
      default:
        this.getView().errorMenu();
        launchMenu();
    }
  }

  /**
   * Method that display in console the Company Menu.
   * @throws ValidationException 
   * 
   */
  public void launchMenuCompany() throws SQLException, ValidationException {
    this.getView().displayMenuCompany();
    sc = new Scanner(System.in);
    String choice = sc.nextLine().toUpperCase();

    if (ENUM_LIST.contains(choice) && !choice.equals("")) {
      switch (Feature.valueOf(choice)) {
        case GETALL:
          List<Company> companies = this.serviceCompany.getCompanies();
          Object[][] data = new Object[companies.size()][5];
          for (int i = 0; i < companies.size(); i++) {
            data[i][0] = companies.get(i).getId();
            data[i][1] = companies.get(i).getName();
          }
          TextTable table = new TextTable(new String[] { "ID", "Name" }, data);
          this.getView().printTableDatabase(table);
          launchMenuCompany();
          break;
        case DELETE:
          deleteCompany();
          break;
        case EXIT:
          launchMenu();
          break;
        default:
          this.getView().errorCommand();
          launchMenuCompany();
      }
    } else {
      this.getView().errorCommand();
      launchMenuCompany();
    }
  }
  
  /**
   * Method that display in console the delete menu for companies.
   * @throws ValidationException 
   */
  public void deleteCompany() throws SQLException, ValidationException {
    System.out.print("Enter Company ID : ");
    Scanner sc = new Scanner(System.in);
    int companyid = Integer.parseInt(sc.nextLine());
    this.serviceCompany.deleteCompany(companyid);
    launchMenuCompany();
    sc.close();
  }

  /**
   * Method that display in console the Computer Menu.
   * @throws ValidationException 
   * 
   */
  public void launchMenuComputer() throws SQLException, ValidationException {
    this.getView().displayMenuComputer();
    sc = new Scanner(System.in);
    String choice = sc.nextLine().toUpperCase();

    if (ENUM_LIST.contains(choice) && !choice.equals("")) {
      switch (Feature.valueOf(choice)) {
        case ADD:
          addComputer();
          break;
        case DELETE:
          deleteComputer();
          break;
        case UPDATE:
          updateComputer();
          break;
        case GET:
          getComputer();
          break;
        case GETALL:
          getComputers();
          break;
        case EXIT:
          launchMenu();
          break;
        default:
          this.getView().errorCommand();
          launchMenuComputer();
      }
    } else {
      this.getView().errorCommand();
      launchMenuComputer();
    }
  }

  /**
   * Method that display in console menu for the addition of a new Computer Menu.
   * @throws ValidationException 
   * 
   */
  public void addComputer() throws SQLException, ValidationException {
    System.out.print("Enter Name : ");
    Scanner sc = new Scanner(System.in);
    String name = sc.nextLine();
    System.out.print("\nIntroduced : ");
    Timestamp ts1 = null;
    SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // Check if the value is a valid timestamp
    try {
      String introduced = sc.nextLine();
      if (!introduced.equals("")) {
        format.parse(introduced);
        ts1 = java.sql.Timestamp.valueOf(introduced);
      }
    } catch (ParseException e) {
      this.getView().errorFormatTimestamp();
      updateComputer();
    }

    System.out.print("\nDiscontinued : ");
    Timestamp ts2 = null;
    // Check if the value is a valid timestamp
    try {
      String discontinued = sc.nextLine();
      if (!discontinued.equals("")) {
        format.parse(discontinued);
        ts2 = java.sql.Timestamp.valueOf(discontinued);
      }
    } catch (ParseException e) {
      this.getView().errorFormatTimestamp();
      updateComputer();
    }

    System.out.print("\nCompany ID : ");

    // Check is the value is an integer
    try {
      int companyid = Integer.parseInt(sc.nextLine());
      // check if the id is a valid company that exist in the table company
      if (this.serviceCompany.companyExist(companyid)) {
        Computer c = new Computer(this.serviceComputer.getMaxId(), name, ts1, ts2,
            this.serviceCompany.getCompany(companyid));
        // check if all informations are valid
        this.validator.verifyComputer(c);
        LOG.info("Computer Add Request : " + c.toString());
        this.serviceComputer.addComputer(c);
        this.getView().computerAdded();
        launchMenuComputer();
      } else {
        LOG.error("The following Company Does not exist : " + companyid);
        this.getView().companyDoesNotExist();
        addComputer();
      }
    } catch (NumberFormatException nfe) {
      this.getView().errorDigit();
      addComputer();
    }
    sc.close();
  }

  /**
   * Method that display the suppresion of a Computer Menu.
   * @throws ValidationException 
   * 
   */
  public void deleteComputer() throws SQLException, ValidationException {
    System.out.print("Enter Computer ID : ");
    Scanner sc = new Scanner(System.in);
    try {
      int i = Integer.parseInt(sc.nextLine());
      LOG.info("Request delete Computer : " + i);
      this.serviceComputer.deleteComputer(i);
      this.getView().computerDeleted();
      launchMenuComputer();
    } catch (NumberFormatException nfe) {
      this.getView().errorDigit();
      launchMenuComputer();
    }
    sc.close();
  }

  /**
   * Method that display the update of computer Menu.
   * @throws ValidationException 
   * 
   */
  public void updateComputer() throws SQLException, ValidationException {
    System.out.print("Select a computer ID : ");
    Scanner sc = new Scanner(System.in);
    // check if the value is an integer
    try {
      int i = Integer.parseInt(sc.nextLine());

      System.out.print("\nEnter name : ");
      String name = sc.nextLine();

      System.out.print("\nEnter introduced : ");
      Timestamp ts1 = null;

      SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      // check if the value is a valid timestamp
      try {
        String introduced = sc.nextLine();
        if (!introduced.equals("")) {
          format.parse(introduced);
          ts1 = java.sql.Timestamp.valueOf(introduced);
        }
      } catch (ParseException e) {
        this.getView().errorFormatTimestamp();
        updateComputer();
      }

      System.out.print("\nEnter discontinued : ");
      Timestamp ts2 = null;
      // check if the value is a valid timestamp
      try {
        String discontinued = sc.nextLine();
        if (!discontinued.equals("")) {
          format.parse(discontinued);
          ts2 = java.sql.Timestamp.valueOf(discontinued);
        }
      } catch (ParseException e) {
        this.getView().errorFormatTimestamp();
        updateComputer();
      }

      System.out.print("\nEnter Company_ID : ");
      // check is the value is a valid integer
      try {
        int companyid = Integer.parseInt(sc.nextLine());
        // check is the value is a valid company id that exist in the table company
        if (this.serviceCompany.companyExist(companyid)) {
          Computer c = new Computer(i, name, ts1, ts2,
              this.serviceCompany.getCompany(companyid));
          // check if all informations are valid
          this.validator.verifyComputer(c);
          LOG.info("Request update Computer : " + c.getId() + " with datas : " + c.toString());
          this.serviceComputer.updateComputer(c);
          this.getView().computerUpdated();
          launchMenuComputer();
          
        } else {
          LOG.error("The following Company Does not exist : " + companyid);
          this.getView().companyDoesNotExist();
          updateComputer();
        }
      } catch (NumberFormatException nfe) {
        this.getView().errorDigit();
        updateComputer();
      }
    } catch (NumberFormatException nfe) {
      this.getView().errorDigit();
      updateComputer();
    }
    sc.close();
  }

  /**
   * Method that display a unique Computer by Id.
   * @throws ValidationException 
   * 
   */
  public void getComputer() throws SQLException, ValidationException {
    System.out.print("Enter computer ID : ");
    Scanner sc = new Scanner(System.in);
    try {
      int id = Integer.parseInt(sc.nextLine());
      LOG.info("Request computer : " + id);
      Computer c = this.serviceComputer.getComputer(id);
      this.getView().displayComputer(c);
      launchMenuComputer();
    } catch (NumberFormatException nfe) {
      this.getView().errorDigit();
      getComputer();
    }
    sc.close();
  }

  /**
   * Method that display every computers in the console.
   * @throws ValidationException 
   * 
   */
  public void getComputers() throws SQLException, ValidationException {
    LOG.info("Request Computers List");
    List<Computer> computers = this.serviceComputer.getComputers();
    Object[][] data = new Object[computers.size()][5];
    for (int i = 0; i < computers.size(); i++) {
      data[i][0] = computers.get(i).getId();
      data[i][1] = computers.get(i).getName();
      data[i][2] = computers.get(i).getIntroduced();
      data[i][3] = computers.get(i).getDiscontinued();
      data[i][4] = computers.get(i).getCompany().getId();
    }
    TextTable table = new TextTable(
        new String[] { "ID", "Name", "Introduced", "Discontinued", "Company_Id" }, data);
    this.getView().printTableDatabase(table);

    launchMenuComputer();
  }

  /**
   * Setter of the class Controller, set manually the attribute DaoFactory.
   * 
   * @return Object View
   */
  public View getView() {
    return view;
  }

  /**
   * Setter of the class Controller, set manually the attribute DaoFactory.
   * 
   * @param view View Object
   */
  public void setView(View view) {
    this.view = view;
  }

  /**
   * Getter of the class Controller, get the ServiceComputer.
   * 
   * @return Object ServiceComputer
   */
  public ServiceComputer getServiceComputer() {
    return serviceComputer;
  }

  /**
   * Setter of the class Controller, set manually the ServiceComputer.
   * 
   * @param serviceComputer Object
   */
  public void setServiceComputer(ServiceComputer serviceComputer) {
    this.serviceComputer = serviceComputer;
  }

  /**
   * Getter of the class Controller, get the ServiceCompany.
   * 
   * @return Object ServiceCompany
   */
  public ServiceCompany getServiceCompany() {
    return serviceCompany;
  }

  /**
   * Setter of the class Controller, set manually the ServiceCompany.
   * 
   * @param serviceCompany Object
   */
  public void setServiceCompany(ServiceCompany serviceCompany) {
    this.serviceCompany = serviceCompany;
  }
}
