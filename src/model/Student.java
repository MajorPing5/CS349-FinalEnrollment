/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * 
 */
public class Student extends User {
  /** Generic filter option used by the Student list screen. */
  public static final String FILTER_ANY = "Any";
  
  private int advisorId;
  private Map<MajorDiscipline, DegreeType> degreeMap;
  private ArrayList<MinorDiscipline> minors;
  private ArrayList<String> permission;
  /**
   * Full constructor for entire Student information in one sitting
   * @param id
   * @param name
   * @param email
   * @param passwordHash
   * @param role
   * @param advisorId
   * @param majors
   * @param minors
   * @param degreeTypes
   * @param permission
   */
  public Student(int id,
          String name,
          String email,
          String passwordHash,
          RoleType role,
          int advisorId,
          Map<MajorDiscipline, DegreeType> degreeMap,
          ArrayList<MinorDiscipline> minors,
          ArrayList<String> permission) {
    // Model specific rules, ensuring that no student is able to be created at all without this critical information.
    userStateValidation(id, name, email, passwordHash, role);
    studentStateValidation(degreeMap, advisorId);
    
    // Only proceeds when model specific rules have been satisfied.
    super(id, name, email, passwordHash, role);
    this.advisorId = advisorId;
    this.minors = minors;
    this.permission = permission;

    this.degreeMap = degreeMap;
  }

  /**
   * Ensures that all potential information relevant to student exclusive fields that are
   * business rule critical are not left null or empty at creation.
   *
   * @param degreeMap
   * @param advisorId
   * @throws IllegalArgumentException
   */
  private static void studentStateValidation(Map<MajorDiscipline, DegreeType> degreeMap,
          int advisorId) throws IllegalArgumentException {
    if (degreeMap.isEmpty()) {
      throw new IllegalArgumentException(
              "A Student is required to declare at least one major at creation.");
    }
    if (advisorId == 0) {
      throw new IllegalArgumentException(
              "A Student is required to be assigned an Advisor at creation");
    }
  }
  
  /**
   * Ensures that all information relevant for User inherited fields
   * are not left blank or null under any circumstances.
   *
   * @param id
   * @param name
   * @param email
   * @param passwordHash
   * @param roleType
   * 
   * @throws IllegalArgumentException
   */
  private static void userStateValidation(int id,
          String name,
          String email,
          String passwordHash,
          RoleType roleType) throws IllegalArgumentException {
    if (id == 0) {
      throw new IllegalArgumentException(
              "A Student is required to have an assigned ID at creation.");
    }
    if (name == null) {
      throw new IllegalArgumentException(
              "A Student is required to have a name, email, and hashed password at creation.");
    }
    if (email == null) {
      throw new IllegalArgumentException(
              "A Student is required to have an email at creation.");
    }
    if (passwordHash == null) {
      throw new IllegalArgumentException(
              "A Student is required to have a password at creation.");
    }
    if (roleType == null || roleType != RoleType.STUDENT) {
      throw new IllegalArgumentException(
              "A Student is required to have an assigned role of Student at creation.");
    }
  }

  /**
   * @return the advisor ID
   */
  public int getAdvisorId() {
    return this.advisorId;
  }
  
  /**
   * @return an immutable copy of the degreeMap
   */
  public Map<MajorDiscipline, DegreeType> getDegreeMap() {
    return Collections.unmodifiableMap(this.degreeMap);
  }
  
  /**
   * @return the {@code ArrayList} of all Majors in order
   */
  public ArrayList<MajorDiscipline> getAllMajors() {
    return new ArrayList<>(this.degreeMap.keySet());
  }
  
  public boolean hasMajor(MajorDiscipline major) {
    return this.degreeMap.containsKey(major);
  }
  
  public boolean hasDegreeType(DegreeType type) {
    return this.degreeMap.containsValue(type);
  }
  
  /**
   * @return the {@code ArrayList} of all Degree Types in order
   */
  public ArrayList<DegreeType> getAllDegreeTypes() {
    return new ArrayList<>(this.degreeMap.values());
  }
  
  /**
   * @param major the Major to search for in {@code Map degreeMap} 
   * @return {@code DegreeType} linked to appropriate {@code Major}
   */
  public DegreeType getDegreeType(MajorDiscipline major) {
    return this.degreeMap.get(major);
  }

  /**
   * @return the full list of minors
   */
  public ArrayList<MinorDiscipline> getAllMinors() {
    return this.minors;
  }

  /**
   * 
   * @param i index in Minors list
   * @return the MinorDiscipline enum constant of element i in ArrayList majors
   */
  public MinorDiscipline getMinor(int i) {
    return this.minors.get(i);
  }

  /**
   * @return the permission
   */
  public ArrayList<String> getPermissions() {
    return this.permission;
  }

  /**
   * @param advisorId the advisorId to set
   */
  public void setAdvisorId(int advisorId) throws IllegalArgumentException {
    if (advisorId == 0) {
      String message = "Domain Violation: All students must have an assigned Advisor!";
      throw new IllegalArgumentException(message);
    }
    this.advisorId = advisorId;
  }

  /**
   * @param degreeMap the degreeMap to set
   *
   * @throws IllegalStateException if {@code degreeMap < 2}
   */
  public void setDegreeMap(Map<MajorDiscipline, DegreeType> degreeMap) throws IllegalArgumentException {
    if (degreeMap.size() < 1) {
      String message = "Domain Violation: Student would be without "
              + "ANY declared major OR associated degree type!";
      throw new IllegalArgumentException(message);
    }
    this.degreeMap = degreeMap;
  }
  
  /**
   * @param major the major to change the Degree Type assignment
   */
  public void updateDegreeType(MajorDiscipline major, DegreeType newDegree) throws IllegalArgumentException {
    if (newDegree == null) {
      String message = "Domain Violation: Student would be declared a major without an appropriate degree type to match!";
      throw new IllegalArgumentException(message);
    }
    if (major == null) {
      String message = "Domain Violation: Student would be without ANY declared major!";
      throw new IllegalArgumentException(message);
    }
    DegreeType oldDegreeType = getDegreeType(major);
    this.degreeMap.replace(major, oldDegreeType, newDegree);
  }
  
  /**
   * @param major the major to remove from the map, with its corresponding Degree Type
   */
  public DegreeType deleteMajor(MajorDiscipline major) throws IllegalArgumentException {
    if (this.degreeMap.size() < 2) {
      String message = "Domain Violation: Student would be without ANY Degree or Major!";
      throw new IllegalArgumentException(message);
    }
    return this.degreeMap.remove(major);
  }

  /**
   * @param minor the full minor list to set
   */
  public void updateAllMinors(ArrayList<MinorDiscipline> minors) {
    this.minors = minors;
  }

  /**
   * @param i     index of element n in ArrayList minors
   * @param minor MinorDiscipline enum constant of desired minor 
   */
  public void updateOneMinor(int i, MinorDiscipline minor) {
    this.minors.set(i, minor);
  }

  /**
   * @param i index of element n in ArrayList minors
   */
  public void deleteOneMinor(int i) {
    this.minors.remove(i);
  }

  public void deleteAllMinors() {
    this.minors.clear();
    this.minors.trimToSize();
  }

  /**
   * @param permission the permission to set
   */
  public void setPermissions(ArrayList<String> permission) {
    this.permission = permission;
  }
  
  /**
   * int id,
          String name,
          String email,
          String passwordHash,
          RoleType role,
          int advisorId,
          Map<MajorDiscipline, DegreeType> degreeMap,
          ArrayList<MinorDiscipline> minors,
          ArrayList<String> permission
   * @return
   */
  public Object[] toAdvisorTableRow() {
    ArrayList<String> majorList = new ArrayList<>();
    ArrayList<MajorDiscipline> majorDisciplineList = getAllMajors();
    ArrayList<MinorDiscipline> minorList = getAllMinors();
    
    majorDisciplineList.forEach(e -> {
      DegreeType degree = this.degreeMap.get(e);
      String degreeCombo = degree.toString().concat("-").concat(e.toString());
      majorList.add(degreeCombo);
    });
    
    return new Object[] {
      this.getId(),
      this.getName(),
      this.getEmail(),
      this.getRole(),
      // advisorId
      this.advisorId,
      // degree type + discipline
      majorList,
      // minor disciplines
      minorList,
      // permissions
      
    };
  }
}
