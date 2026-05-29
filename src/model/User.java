package model;

public abstract class User {
  // Fields
  private int id;
  private String email;
  private String name;
  private String passwordHash;
  RoleType role;

  // Constructors
  /**
   * Full constructor for User
   * @param id
   * @param email
   * @param name
   * @param username
   */
  public User(int id,
          String name,
          String email,
          String passwordHash,
          RoleType role) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.passwordHash = passwordHash;
    this.role = role;
  }

  // Getters & Setters
  // Getters Only
  /**
   * @return the id
   */
  public int getId() {
    return this.id;
  }
  
  public String getEmail() {
    return this.email;
  }

  public String getName() {
    return this.name;
  }

  /**
   * @return the password
   */
  public String getPasswordHash() {
    return this.passwordHash;
  }
  
  // Setters Only
  /**
   * @param id the id to set
   */
  public void setId(int id) {
    this.id = id;
  }
  
  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the role
   */
  public RoleType getRole() {
    return this.role;
  }

  /**
   * @param password the password to set
   */
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  /**
   * @param role the role to set
   */
  public void setRole(RoleType role) {
    this.role = role;
  }
}
