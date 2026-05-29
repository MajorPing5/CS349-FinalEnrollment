/**
 * 
 */
package dao.implementation;

import dao.interfaces.MajStuAssignmentDAO;
import util.DbConnection;

/**
 * 
 */
public class MajStuAssignmentDAOImpl implements MajStuAssignmentDAO {
  private static final String MSA_COLUMNS = "";
  private final DbConnection connection;
  
  public MajStuAssignmentDAOImpl(DbConnection connection) {
    this.connection = connection;
  }
}
