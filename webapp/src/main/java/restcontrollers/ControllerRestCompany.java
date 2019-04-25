package restcontrollers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import model.Company;
import model.Token;
import service.ServiceCompany;

@RestController
@RequestMapping("/api/companies/")
@CrossOrigin
public class ControllerRestCompany {

  private ServiceCompany serviceCompany;
  
  public ControllerRestCompany(ServiceCompany serviceCompany) {
    this.serviceCompany = serviceCompany;
  }
  
  @GetMapping(value = "/{id}")
  public ResponseEntity<Company> getCompanyRest(@PathVariable String id, HttpServletRequest req) throws Exception {
      Token token = new Token(req.getHeader("authorization"));
      if ( token.isValidToken()) {
          return new ResponseEntity<Company>(this.serviceCompany.getCompany(Integer.parseInt(id)), HttpStatus.OK);
      } else {
          return new ResponseEntity<Company>(HttpStatus.BAD_REQUEST);
      }
  }
  
  @GetMapping(value = "/")
  public ResponseEntity<List<Company>> getCompaniesRest(HttpServletRequest req) throws Exception {
      Token token = new Token(req.getHeader("authorization"));
      if ( token.isValidToken()) {
          return new ResponseEntity<List<Company>>(this.serviceCompany.getCompanies(), HttpStatus.OK);
      } else {
          return new ResponseEntity<List<Company>>(HttpStatus.BAD_REQUEST);
      }
  }
  
  @DeleteMapping("/{id}")
  public void deleteCompany(@PathVariable String id, HttpServletRequest req) throws Exception
  {
      Token token = new Token(req.getHeader("authorization"));
      if ( token.isValidToken()) {
          this.serviceCompany.deleteCompany(Integer.parseInt(id));
      }
  }
}
