/**
 * 
 */
package dao.implementation;

import dao.interfaces.AdvisorDAO;
import util.DbConnection;

/**
 * 
 */
public class AdvisorDAOImpl implements AdvisorDAO {
  private static final String ADVISOR_COLUMNS = "";
  private final DbConnection connection;
  
  public AdvisorDAOImpl(DbConnection connection) {
    this.connection = connection;
  }
}
