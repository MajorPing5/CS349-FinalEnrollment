/**
 * 
 */
package dao.interfaces;

import java.util.ArrayList;
import java.util.Optional;

import model.Student;

/**
 * 
 */
public interface StudentDAO {
  public ArrayList<Student> selectAll();
  public Optional<Student> selectOneId(int id);
  public Optional<Student> selectOneName(String name);
  public void create(Student student);
  public void update(Student student);
  public boolean delete(Student student);
  Optional<ArrayList<String>> selectOneCredentials(String email);
}
