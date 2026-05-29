/**
 * 
 */
package dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dao.interfaces.StudentDAO;
import model.DegreeType;
import model.MajorDiscipline;
import model.MinorDiscipline;
import model.RoleType;
import model.Student;
import util.DbConnection;

/**
 * Data access operations for {@link Student}.
 */
public class StudentDAOImpl implements StudentDAO {
  private static final String USER_COLUMNS =
          "id, name, email, passwordHash, role, advisorId";

  private final DbConnection connection;

  public StudentDAOImpl(DbConnection connection) {
    this.connection = connection;
  }

  private static Student mapRowToStudent(ResultSet rs) throws SQLException {
    MajorDiscipline discipline = MajorDiscipline.valueOf(rs.getString("majorDiscipline"));
    DegreeType degreeType = DegreeType.valueOf(rs.getString("degreeType"));
    MinorDiscipline minorDiscipline = MinorDiscipline.valueOf(rs.getString("minorDiscipline"));
    Map<MajorDiscipline, DegreeType> degreeMap = new HashMap<>();
    ArrayList<MinorDiscipline> minors = new ArrayList<MinorDiscipline>(List.of(minorDiscipline));

    degreeMap.put(discipline, degreeType);


    return new Student(
            rs.getInt("id"),
            rs.getString("fullName"),
            rs.getString("email"),
            rs.getString("passwordHash"),
            RoleType.STUDENT,
            rs.getInt("advisorId"),
            degreeMap,
            minors,
            new ArrayList<String>(List.of(rs.getString("permission")))
            );
  }

  @Override
  public ArrayList<Student> selectAll() {
    // TODO Auto-generated method stub
    String query = "SELECT * FROM Student";
    ArrayList<Student> studentList = new ArrayList<>();

    try (Connection conn = this.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          studentList.add(mapRowToStudent(rs));
        }
      }
      return studentList;
    } catch (SQLException ex) {
      throw new IllegalStateException("Table Student was unable to be located.", ex);
    }
  }

  @Override
  public Optional<ArrayList<String>> selectOneCredentials(String email) {
    String query = "SELECT " + USER_COLUMNS + " FROM Student WHERE email = ?";
    
    try (Connection conn = this.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setString(1, email);
      
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          ArrayList<String> credentials = new ArrayList<>();
          credentials.add(rs.getString("email"));
          credentials.add(rs.getString("passwordHash"));
          credentials.add(rs.getString("role"));
          return Optional.of(credentials);
        }
      }
      return Optional.empty();
    } catch (SQLException ex) {
      throw new IllegalStateException("Table Student was unable to be located.", ex);
    }
  }
  
  @Override
  public Optional<Student> selectOneId(int id) {
    String query = "SELECT " + USER_COLUMNS + " FROM Student WHERE id = ?";

    try (Connection conn = this.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setInt(1, id);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRowToStudent(rs));
        }
      }

      return Optional.empty();
    } catch (SQLException ex) {
      throw new IllegalStateException("Unable to read Student by ID.", ex);
    }
  }

  @Override
  public Optional<Student> selectOneName(String name) {
    String query = "SELECT " + USER_COLUMNS + " FROM Student WHERE LOWER(fullName) = LOWER(?)";

    try (Connection conn = this.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

      ps.setString(1, name);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapRowToStudent(rs));
        }
      }

      return Optional.empty();
    } catch (SQLException ex) {
      throw new IllegalStateException("Unable to read Student by full name.", ex);
    }
  }

  @Override
  public void create(Student user) {
    String query = "INSERT INTO Student (id, name, email, password_hash, role)"
            + " VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = this.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

      ps.setInt(1, user.getId());
      ps.setString(2, user.getName());
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getPasswordHash());
      ps.setString(5, user.getRole().toString());

      int rows = ps.executeUpdate();
      if (rows == 0) {
        throw new IllegalStateException("Student insert failed: no rows were affected.");
      }
    } catch (SQLException ex) {
      throw new IllegalStateException("Unable to save user details.", ex);
    }
  }

  @Override
  public void update(Student student) {
    // TODO Auto-generated method stub
    String query = "UPDATE Student "
            + "SET name = ?, email = ?, advisorId = ?, ";
  }

  @Override
  public boolean delete(Student student) {
    String query = "DELETE from Student where id = ?";

    try (Connection conn = this.connection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {
      ps.setInt(1, student.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException ex) {
      throw new IllegalStateException("Student does not exist in table Student.");
    }
  }

  private String buildFilteredQuery(
          String id,
          String name,
          String email,
          String advisorName) {
    StringBuilder sql = new StringBuilder();
    sql.append("SELECT ").append(USER_COLUMNS).append(" FROM Student WHERE id = ?");

    if (isConcreteFilter(name)) {
      sql.append(" AND name = ?");
    }
    if (isConcreteFilter(email)) {
      sql.append(" AND priority = ?");
    }
    if (isConcreteFilter(advisorName)) {
      sql.append(" AND advisorName = ?");
    }
      sql.append(" ORDER BY id DESC");
    
    return sql.toString();
  }

  private static boolean isConcreteFilter(String value) {
    return value != null && !value.isBlank() && !Student.FILTER_ANY.equalsIgnoreCase(value.trim());
  }
}
