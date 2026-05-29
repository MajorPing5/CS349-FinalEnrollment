/**
 * 
 */
package dao.factory;

import dao.implementation.CourseDAOImpl;
import dao.interfaces.CourseDAO;
import util.DbConnection;

/**
 * 
 */
public class CourseDAOFactory extends DAOFactory {
  private final DbConnection connection;
  
  public CourseDAOFactory() {
    this.connection = getConnection();
  }
  
  public CourseDAO createCourseDAO() {
    return new CourseDAOImpl(this.connection);
  }
}
