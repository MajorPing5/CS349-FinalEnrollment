package controller;

import java.util.ArrayList;

import model.RoleType;
import service.StudentService;
import view.AdminView;
import view.AdvisorView;
import view.LoginView;
import view.StudentView;
import util.PasswordHash;

public class LoginController implements WelcomeInterface {
  private final LoginView loginView;
  private final StudentService userService;
  private final PasswordHash passwordHash = new PasswordHash();
  private final String devPass = this.passwordHash.hash("yA!CmgAH817n");

  public LoginController() {
    StudentService userService = new StudentService();
    this.userService = userService;
    this.loginView = new LoginView(this);
    this.loginView.setVisible(true);
  }
  
  /**
   * Controller-based action listener method specific for Log-In Operations
   * @param userPasswordString The raw string from the user's input from a password box
   * @param role
   */
  @Override
  public void onLoginButtonClick() {
    String email = this.loginView.getTxtEmail();
    String userPassword = this.loginView.getTxtPassword().toString();
    String hashedPassword = this.passwordHash.hash(userPassword);
    
    ArrayList<String> credentials = userService.getStudentCredentials(email);
    RoleType role = RoleType.valueOf(credentials.get(2));
    // NOTE This is a security risk for late dev process. Use this for initial dev process
    if (hashedPassword == this.devPass) {
      role = RoleType.ADMIN;
    } else if (hashedPassword == credentials.get(1)) {
      
    }
    
    // For Database implementation:
    // TODO Create an if-then to compare passwords before authorizing
    // TODO Remove the RoleType assignment for Separation of Concerns (FP)
    // TODO Use the retrieved User information to specify via role what window should be loaded 
    
    switch (role) {
      case RoleType.STUDENT -> {
        StudentView studentView = new StudentView();
        studentView.setVisible(true);
      }
      case RoleType.ADVISOR -> {
        AdvisorView advisorView = new AdvisorView();
        advisorView.setVisible(true);
      }
      case RoleType.ADMIN -> {
        AdminView adminView = new AdminView();
        adminView.setVisible(true);
      }
      default -> throw new IllegalArgumentException("Unexpected value: " + role);
    }
    
    this.loginView.dispose();
  }

  /**
   * 
   */
  public void onRegisterButtonClick() {
    
    new RegisterController();
    
    loginView.dispose();
  }
}
