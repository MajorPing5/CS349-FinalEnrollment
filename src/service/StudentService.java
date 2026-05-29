/**
 * 
 */
package service;

import java.util.ArrayList;

import dao.factory.DAOFactory;
import dao.factory.StudentDAOFactory;
import dao.interfaces.AdvisorDAO;
import dao.interfaces.DegreeDAO;
import dao.interfaces.MajStuAssignmentDAO;
import dao.interfaces.MinStuAssignmentDAO;
import dao.interfaces.MinorDisciplineDAO;
import dao.interfaces.PermissionDAO;
import dao.interfaces.StudentDAO;
import model.Student;

/**
 * 
 */
public class StudentService {
  private StudentDAOFactory stuFact;
  private final StudentDAO stuDao;
  private final AdvisorDAO advDao;
  private final MajStuAssignmentDAO majStuDAO;
  private final DegreeDAO degreeDAO;
  private final MinStuAssignmentDAO minStuDAO;
  private final MinorDisciplineDAO minDAO;
  private final PermissionDAO permsDAO;
  
  public StudentService() {
    this.stuFact = (StudentDAOFactory) DAOFactory.createDAOFactory(DAOFactory.STUDENT);
    
    this.stuDao = stuFact.createStudentDAO();
    this.advDao = stuFact.createAdvisorDAO();
    this.majStuDAO = stuFact.createMSADAO();
    this.degreeDAO = stuFact.createDegreeDAO();
    this.minStuDAO = stuFact.createMNSADAO();
    this.minDAO = stuFact.createMinDAO();
    this.permsDAO = stuFact.createPermissionDAO();
  }
  
  public Student getStudentById(int id) {
    return this.stuDao.selectOneId(id).orElse(null);
  }
  
  public ArrayList<String> getStudentCredentials(String email) {
    ArrayList<String> credentialsList = this.stuDao.selectOneCredentials(email).orElse(null);
    return credentialsList;
  }
}
