package view;

import dnl.utils.text.table.TextTable;
import model.Computer;

/**
 * Class that display the application in console mod.
 * 
 * @author mnouville
 * @version 1.0
 */
public class View {

  /**
   * Empty constructor of the class View.
   */
  public View() {

  }

  /**
   * Display the main menu of the application.
   */
  public void displayMenu() {
    System.out.println("");
    System.out.println("|------------------------------------------|");
    System.out.println("|-------------------Menu-------------------|");
    System.out.println("|------------------------------------------|");
    System.out.println("");
    System.out.println("\t1 - Menu Company ");
    System.out.println("\t2 - Menu Computer ");
    System.out.println("\t3 - Exit ");
    System.out.println("");
    System.out.println("|------------------------------------------|");
    System.out.println("");
  }

  /**
   * Display the menu of companies.
   */
  public void displayMenuCompany() {
    System.out.println("");
    System.out.println("|------------------------------------------|");
    System.out.println("|---------------Menu Company---------------|");
    System.out.println("|------------------------------------------|");
    System.out.println("");
    System.out.println("\tGETALL - Get All Companies");
    System.out.println("\tDELETE - Delete a specific company");
    System.out.println("\tEXIT   - Back to Main Menu ");
    System.out.println("");
    System.out.println("|------------------------------------------|");
    System.out.println("");
  }

  /**
   * Display the menu of Computers.
   */
  public void displayMenuComputer() {
    System.out.println("");
    System.out.println("|-----------------------------------------|");
    System.out.println("|--------------Menu Computer--------------|");
    System.out.println("|-----------------------------------------|");
    System.out.println("");
    System.out.println("\tADD     - Add Computer ");
    System.out.println("\tDELETE  - Delete Computer ");
    System.out.println("\tUPDATE  - Update Computer ");
    System.out.println("\tGET     - Get a particular Computer ");
    System.out.println("\tGETALL  - Get All Computers");
    System.out.println("\tEXIT    - Back to Main Menu");
    System.out.println("");
    System.out.println("|-----------------------------------------|");
    System.out.println("");
  }

  /**
   * Display a precise computer in console.
   * 
   * @param c Computer Object
   */
  public void displayComputer(Computer c) {
    System.out.println(c.toString());
  }

  /**
   * Display a validation message when a computer is added.
   */
  public void computerAdded() {
    System.out.println("Computer Added !");
  }

  /**
   * Display a validation message when a computer is deleted.
   */
  public void computerDeleted() {
    System.out.println("Computer Deleted !");
  }

  /**
   * Display a validation message when a computer is updated.
   */
  public void computerUpdated() {
    System.out.println("Computer Updated !");
  }

  /**
   * Display an error message for invalid datas.
   */
  public void invalidDatas() {
    System.out.println("Invalid Data's !");
  }

  /**
   * Display an error message when a user want to add a computer to a company that does not exist.
   */
  public void companyDoesNotExist() {
    System.out.println("This company does not exist !");
  }

  /**
   * Display an error message for invalid digit.
   */
  public void errorDigit() {
    System.out.println("Please enter a digit Number !");
  }

  /**
   * Display an error message for invalid choices.
   */
  public void errorMenu() {
    System.out.println("Please enter a valid choice!");
  }

  /**
   * Display an error message for command errors.
   */
  public void errorCommand() {
    System.out.println("Please enter a valid command ! ");
  }

  /**
   * Display an error message for invalid timestamp.
   */
  public void errorFormatTimestamp() {
    System.out.println("Introduced format must be yyyy-mm-dd hh:mm:ss ");
  }

  /**
   * Display a table in console.
   */
  public void printTableDatabase(TextTable table) {
    table.printTable();
    System.out.println("");
  }
}
