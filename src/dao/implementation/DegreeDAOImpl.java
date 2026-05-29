/**
 * 
 */
package dao.implementation;

import dao.interfaces.DegreeDAO;
import util.DbConnection;

/**
 * 
 */
public class DegreeDAOImpl implements DegreeDAO {
  private static final String DEGREE_COLUMNS = "";
  private final DbConnection connection;
  
  public DegreeDAOImpl(DbConnection connection) {
    this.connection = connection;
  }
}
