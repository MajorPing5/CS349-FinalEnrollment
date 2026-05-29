/**
 * 
 */
package controller;

import javax.swing.JOptionPane;

import service.StudentService;
import view.LoginView;
import view.RegisterView;

/**
 * 
 */
public class RegisterController implements WelcomeInterface {
  RegisterView registerView;
  StudentService userService;
  
  public RegisterController() {
    StudentService userService = new StudentService();
    this.userService = userService;
    this.registerView = new RegisterView(this);
    this.registerView.setVisible(true);
  }
  
  public void onRegisterButtonClick() {
    JOptionPane.showMessageDialog(this.registerView, "Registration Successful");
  }
  
  public void onLoginButtonClick() {
    new LoginController();
    this.registerView.dispose();
  }
}
