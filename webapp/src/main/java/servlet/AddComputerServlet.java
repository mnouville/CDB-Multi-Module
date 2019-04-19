package servlet;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import dto.Dto;
import dto.UserDto;
import exceptions.ValidationException;
import model.Company;
import service.ServiceCompany;
import service.ServiceComputer;
import service.ServiceUser;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Servlet implementation class AddComputerServlet. 
 */
@Controller
@ApiIgnore
@RequestMapping("/AddComputer")
public class AddComputerServlet  {

  private ServiceComputer serviceComputer;
  private ServiceCompany serviceCompany;
  private ServiceUser serviceUser;

  public AddComputerServlet(ServiceComputer serviceComputer, ServiceCompany serviceCompany, ServiceUser serviceUser) {
    this.serviceComputer = serviceComputer;
    this.serviceCompany = serviceCompany;
    this.serviceUser = serviceUser;
  }
  
  @GetMapping
  protected ModelAndView doGet(ModelAndView modelView, Principal principal) throws SQLException {
    List<Company> companies;
    companies = this.serviceCompany.getCompanies();
    
    String login = principal.getName(); //get logged in username
    UserDto user = this.serviceUser.getUser(login);
    modelView.addObject("user",user);
    modelView.addObject("companies", companies);
    modelView.setViewName("AddComputer");
    return modelView;
  }
  
  @PostMapping
  protected ModelAndView doPost(WebRequest request, ModelAndView modelView) throws SQLException, ValidationException {
    Company comp;
    
    comp = this.serviceCompany.getCompany(Integer.parseInt(request.getParameter("companyid")));
    Dto dto = new Dto(this.serviceComputer.getMaxId()+"",
                      request.getParameter("name"), 
                      request.getParameter("introduced"), 
                      request.getParameter("discontinued"), 
                      comp.getId()+"", comp.getName() );
    
    this.serviceComputer.addComputer(dto);
    
    int totalComputer = this.serviceComputer.getCount();
    List<Dto> computers = this.serviceComputer.getComputers();
    modelView.addObject("computers", computers);
    modelView.addObject("maxcomputer", totalComputer);
    modelView.setViewName("Dashboard");
    return modelView;
  }
}
