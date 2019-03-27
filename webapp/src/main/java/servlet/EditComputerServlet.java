package servlet;

import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import dto.Dto;
import exceptions.ValidationException;
import mappers.MapperDto;
import model.Company;
import model.Computer;
import service.ServiceCompany;
import service.ServiceComputer;
import validator.Validator;

/**
 * Servlet implementation class AddComputerServlet.
 */
@Controller
@RequestMapping("/EditComputer")
public class EditComputerServlet {

  @Autowired
  private ServiceComputer serviceComputer;
  
  @Autowired
  private ServiceCompany serviceCompany;
  
  @Autowired
  private Validator validator;
  
  @Autowired
  private MapperDto mapper;

  @GetMapping
  protected ModelAndView doGet(WebRequest request, ModelAndView modelView) throws SQLException {
    int id = Integer.parseInt(request.getParameter("id"));
    Computer computer = this.serviceComputer.getComputer(id);
    modelView.addObject("idcomputer", id);
    modelView.addObject("name", computer.getName());
    modelView.addObject("introduced", computer.getIntroduced()  == null ? null : computer.getIntroduced().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    modelView.addObject("discontinued", computer.getIntroduced()  == null ? null : computer.getDiscontinued().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    modelView.addObject("companyid", computer.getCompany().getId());
    List<Company> companies;
    companies = this.serviceCompany.getCompanies();
    modelView.addObject("companies", companies);
    modelView.setViewName("EditComputer");
    return modelView;
  }
  
  @PostMapping
  protected ModelAndView doPost(WebRequest request, ModelAndView modelView) throws SQLException, ValidationException {
    Company comp;
    int companyid = Integer.parseInt(request.getParameter("companyid"));
    
    validator.verifyValidCompanyId(companyid);
    comp = this.serviceCompany.getCompany(Integer.parseInt(request.getParameter("companyid")));
    System.out.println(request.getParameter("id"));
    Dto dto = new Dto(request.getParameter("id"),
                      request.getParameter("name"), 
                      request.getParameter("introduced"), 
                      request.getParameter("discontinued"), 
                      comp.getId()+"", comp.getName() );
    
    Computer computer = mapper.dtoToComputer(dto);
    this.validator.verifyComputerNotNull(computer);
    this.validator.verifyIdNotNull(computer.getId());
    this.validator.verifyName(computer.getName());
    this.validator.verifyIntroBeforeDisco(computer);
    this.serviceComputer.updateComputer(computer);
    
    int totalComputer = this.serviceComputer.getCount();
    List<Dto> computers = this.mapper.computersToDtos(this.serviceComputer.getComputers());
    modelView.addObject("computers", computers);
    modelView.addObject("maxcomputer", totalComputer);
    modelView.setViewName("Dashboard");
    return modelView;
  }
}
