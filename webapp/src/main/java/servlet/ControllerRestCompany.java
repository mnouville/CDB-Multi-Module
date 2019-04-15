package servlet;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Company;
import service.ServiceCompany;

@RestController
@RequestMapping("/webservice/company/")
public class ControllerRestCompany {

  private ServiceCompany serviceCompany;
  
  public ControllerRestCompany(ServiceCompany serviceCompany) {
    this.serviceCompany = serviceCompany;
  }
  
  @GetMapping(value = "/{id}")
  public Company getCompanyRest(@PathVariable String id) throws NumberFormatException, SQLException {
      Company company = this.serviceCompany.getCompany(Integer.parseInt(id));
      return company;
  }
  
  @GetMapping(value = "/")
  public List<Company> getCompaniesRest() throws NumberFormatException, SQLException {
      List<Company> listCompanies= this.serviceCompany.getCompanies();
      return listCompanies;
  }
  
  @DeleteMapping("/{id}")
  public void deleteCompany(@PathVariable String id) throws NumberFormatException, SQLException
  {
      this.serviceCompany.deleteCompany(Integer.parseInt(id));
  }
}
