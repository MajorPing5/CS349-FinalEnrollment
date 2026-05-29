/**
 * 
 */
package dao.factory;

import util.DbConfig;
import util.DbConnection;

/**
 * 
 */
public abstract class DAOFactory {
  // List of DAO Types supported by this Factory
  /** Student Domain Object -> Factory Constructor */
  public static final int STUDENT = 1;
  
  /** Advisor Domain Object -> Factory Constructor */
  public static final int ADVISOR = 2;
  
  /** Course Domain Object -> Factory Constructor */
  public static final int COURSE = 3;
  
  /**
   *  Refer to the following for more DAOFactory and Abstract Factory guidance:
   *  https://www.oracle.com/java/technologies/dataaccessobject.html
   */
  
  public static DAOFactory createDAOFactory(int whichFactory) {    
    switch (whichFactory) {
      case STUDENT:
        return new StudentDAOFactory();
      case ADVISOR:
        return new AdvisorDAOFactory();
      case COURSE:
        return new CourseDAOFactory();
      default:
        return null;
    }
  }
  
  public static DbConnection getConnection() {
    DbConfig config = new DbConfig();
    return new DbConnection(config);
  }
}
