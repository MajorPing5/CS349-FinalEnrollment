/**
 * 
 */
package dao.factory;

import dao.implementation.AdvisorDAOImpl;
import dao.implementation.DegreeDAOImpl;
import dao.implementation.MajStuAssignmentDAOImpl;
import dao.implementation.MinStuAssignmentDAOImpl;
import dao.implementation.MinorDisciplineDAOImpl;
import dao.implementation.PermissionDAOImpl;
import dao.implementation.StudentDAOImpl;
import dao.interfaces.AdvisorDAO;
import dao.interfaces.DegreeDAO;
import dao.interfaces.MajStuAssignmentDAO;
import dao.interfaces.MinStuAssignmentDAO;
import dao.interfaces.MinorDisciplineDAO;
import dao.interfaces.PermissionDAO;
import dao.interfaces.StudentDAO;
import model.Student;
import util.DbConnection;

/**
 * 
 */
public class StudentDAOFactory extends DAOFactory {
  private final DbConnection connection;
  
  public StudentDAOFactory() {
    this.connection = getConnection();
  }
  
  public StudentDAO createStudentDAO() {
    return new StudentDAOImpl(this.connection);
  }

  public AdvisorDAO createAdvisorDAO() {
    return new AdvisorDAOImpl(this.connection);
  }

  public MajStuAssignmentDAO createMSADAO() {
    return new MajStuAssignmentDAOImpl(this.connection);
  }

  public DegreeDAO createDegreeDAO() {
    return new DegreeDAOImpl(this.connection);
  }

  public MinStuAssignmentDAO createMNSADAO() {
    return new MinStuAssignmentDAOImpl(this.connection);
  }
  
  public MinorDisciplineDAO createMinDAO() {
    return new MinorDisciplineDAOImpl(this.connection);
  }

  public PermissionDAO createPermissionDAO() {
    return new PermissionDAOImpl(this.connection);
  }
}
