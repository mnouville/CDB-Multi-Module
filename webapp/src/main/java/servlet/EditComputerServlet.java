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
import exceptions.ValidationException;
import model.Company;
import model.User;
import service.ServiceCompany;
import service.ServiceComputer;
import service.ServiceUser;

/**
 * Servlet implementation class AddComputerServlet.
 */
@Controller
@RequestMapping("/EditComputer")
public class EditComputerServlet {

  private ServiceComputer serviceComputer;
  private ServiceCompany serviceCompany;
  private ServiceUser serviceUser;

  public EditComputerServlet (ServiceComputer serviceComputer, ServiceCompany serviceCompany, ServiceUser serviceUser) {
    this.serviceComputer = serviceComputer;
    this.serviceCompany = serviceCompany;
    this.serviceUser = serviceUser;
  }
  
  @GetMapping
  protected ModelAndView doGet(WebRequest request, ModelAndView modelView, Principal principal) throws SQLException {
    if (request.getParameterMap().containsKey("id")) { 
      int id = Integer.parseInt(request.getParameter("id")); 
      Dto computer = this.serviceComputer.getComputer(id);
      modelView.addObject("name", computer.getName());
      modelView.addObject("introduced", computer.getIntroduced());
      modelView.addObject("discontinued", computer.getIntroduced());
      modelView.addObject("companyid", computer.getCompanyId());
      modelView.addObject("idcomputer", id);
    } else {
      modelView.addObject("error","true");
    }
    String login = principal.getName(); //get logged in username
    User user = this.serviceUser.getUser(login);
    modelView.addObject("user",user);
    
    List<Company> companies;
    companies = this.serviceCompany.getCompanies();
    modelView.addObject("companies", companies);
    modelView.setViewName("EditComputer");
    return modelView;
  }
  
  @PostMapping
  protected ModelAndView doPost(WebRequest request, ModelAndView modelView) throws SQLException, ValidationException {
    Company comp;
    
    comp = this.serviceCompany.getCompany(Integer.parseInt(request.getParameter("companyid")));
    System.out.println(request.getParameter("id"));
    Dto dto = new Dto(request.getParameter("id"),
                      request.getParameter("name"), 
                      request.getParameter("introduced"), 
                      request.getParameter("discontinued"), 
                      comp.getId()+"", comp.getName() );
    
    this.serviceComputer.updateComputer(dto);
    
    int totalComputer = this.serviceComputer.getCount();
    List<Dto> computers = this.serviceComputer.getComputers();
    modelView.addObject("computers", computers);
    modelView.addObject("maxcomputer", totalComputer);
    modelView.setViewName("Dashboard");
    return modelView;
  }
}
