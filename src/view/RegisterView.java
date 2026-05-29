package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.WelcomeInterface;

public class RegisterView extends JFrame {
  // Fields
  private JTextField txtFullName = new JTextField();
  private JTextField txtEmail = new JTextField();
  private JPasswordField txtPassword = new JPasswordField();
  private JPasswordField txtConfirmPassword = new JPasswordField();
  private JButton btnLogin = new JButton();
  private JButton btnRegister = new JButton();
  
  // Controllers
  private Map<JButton, Runnable> controllerMap;
  
  // List & Maps for Components
  private final ArrayList<JButton> buttonList =
          new ArrayList<JButton>(
                  List.of(
                          this.btnLogin,
                          this.btnRegister));
  private final Map<JButton, String> buttonLabelMap =
          Map.of(
                  this.btnLogin, "Log-In",
                  this.btnRegister, "Register");
  
  private final ArrayList<JTextField> txtList =
          new ArrayList<JTextField>(
                  List.of(this.txtFullName,
                          this.txtEmail,
                          this.txtPassword,
                          this.txtConfirmPassword));
  
  private final Map<JTextField, String> txtLabelMap =
          Map.of(
                  this.txtFullName, "Full Name:",
                  this.txtEmail, "Email:",
                  this.txtPassword, "Password:",
                  this.txtConfirmPassword, "Confirm Password:");
  
  private final Map<JTextField, Integer> txtSizeMap =
          Map.of(
                  this.txtFullName, TEXTFIELDSIZE,
                  this.txtEmail, TEXTFIELDSIZE,
                  this.txtPassword, TEXTFIELDSIZE,
                  this.txtConfirmPassword, TEXTFIELDSIZE);
  
  private WelcomeInterface listener;
  
//Constants
  
 /**
  * {@code Window} TItle of Application Window.
  */
 private static final String FRAMETITLE = "UMKC Academic Scheduler - Register";
 
 /**
  * {@code Window} Width (x) Pixel Constant. 
  */
 private static final int WIDTH = 400;
 
 /**
  * {@code Window} Height (y) Pixel Constant. 
  */
 private static final int HEIGHT = 500;
 
 /**
  * {@code BorderLayout} Horizontal Pixel Gap Constant. 
  */
 private static final int HGAP = 10;
 
 /**
  * {@code BorderLayout} Vertical Pixel Gap Constant. 
  */
 private static final int VGAP = 10;
 
 /**
  * {@code FlowLayout} horizontal gap between elements.
  */
 private static final int XGAP = 8;
 
 /**
  * {@code FLowLayout} vertical gap between elements.
  */
 private static final int YGAP = 0;
 
 private static final int TEXTFIELDSIZE = 20;
 
 // ---Layout Settings---
 private static final LayoutManager BOTTOMLAYOUT = new BorderLayout(HGAP, VGAP);
 
 private static final LayoutManager BUTTONLAYOUT = new FlowLayout(FlowLayout.CENTER, XGAP, YGAP);
  
  public RegisterView(WelcomeInterface listener) {
    windowSettings();
    buildUI();
    this.listener = listener;
    
    addActionListeners();
    pack();
    setLocationRelativeTo(null);
  }
  
  /**
   * Global settings for this specific View, localized to just 
   */
  private void windowSettings() {
    setSize(WIDTH, HEIGHT);
    setTitle(FRAMETITLE);
    setDefaultCloseOperation(EXIT_ON_CLOSE);    
  }

  private void buildUI() {
    setLayout(new BorderLayout());
    
    // top components
    buildTopComps();
    
    // bottom components
    buildBottomComps();
  }
  
  /**
   * Constructs & Returns the top components of this panel
   */
  private void buildTopComps() {
    JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
    formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
    
    this.txtList
    .forEach(c -> {
              switch(c) {
                case JPasswordField pass -> buildJPasswordField(
                        this.txtLabelMap.get(c),
                        pass,
                        this.txtSizeMap.get(c).intValue(),
                        formPanel);
                case JTextField txt -> buildJTextField(
                        this.txtLabelMap.get(c),
                        txt,
                        this.txtSizeMap.get(c).intValue(),
                        formPanel);
              }
            }
            );

    add(formPanel, BorderLayout.NORTH);
  }
  
  /**
   * Constructs & Returns the bottom components of this panel
   */  
  private void buildBottomComps() {
    JPanel ops = new JPanel(BOTTOMLAYOUT);
    ops.setBorder(BorderFactory.createEmptyBorder(12, 6, 6, 12));
    JLabel lblStatus = new JLabel("Fill out your details and either click register"
            + " or login if you already have an account");
    
    ops.add(lblStatus, BorderLayout.NORTH);
    JPanel buttons = buildButtonPanel();
    ops.add(buttons, BorderLayout.CENTER);
    add(ops, BorderLayout.SOUTH);
  }
  
  private JPanel buildButtonPanel() {
    JPanel panelVar = new JPanel(BUTTONLAYOUT);

    this.buttonList.forEach(e -> buildButton(this.buttonLabelMap.get(e), e, panelVar));
    return panelVar;
  }
  
  /**
   * Creates and assigns all action listeners to their appropriate buttons.
   */
  private void addActionListeners() {
    this.controllerMap = buildActionMap();
    this.buttonList.forEach(e -> {
      Runnable method = this.controllerMap.get(e);
      e.addActionListener(i -> method.run());
    });
  }
  
  /**
   * Creates the mapping for all Action Listeners paired with their non-null button variable name.
   * @return {@code Map} of JButtons with their Action Listener Operation defined in controller
   */
  private Map<JButton, Runnable> buildActionMap() {
    return Map.of(
            this.btnLogin,      (Runnable) this.listener::onLoginButtonClick,
            this.btnRegister,   (Runnable) this.listener::onRegisterButtonClick
            );
  }
  
  /**
   * Auto constructs & adds a JLabel to a provided panel, right aligned
   * 
   * @param lblName   
   * @param panelName 
   */
  private static JLabel buildRJLabel(String lblName) {
    JLabel label = new JLabel(lblName);
    label.setHorizontalAlignment(SwingConstants.RIGHT);
    return label;
  }
  
  /**
   * Auto constructs & adds a JLabel to a provided panel, center aligned
   * 
   * @param lblName   
   * @param panelName 
   */
  private static JLabel buildCJLabel(String lblName) {
    JLabel label = new JLabel(lblName);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    return label;
  }


  /**
   * Auto constructs & adds a JTextField to a provided panel
   * 
   * @param lblName   The string name of the label to display
   * @param fieldName The TextField to correlate the label to
   * @param panelName The panel that the {@code lblName} and {@code fieldName} should be added to
   */
  private static void buildJTextField(
          String lblName,
          JTextField fieldName,
          int sizeConst,
          JPanel panelName) {
    JLabel lblVar = buildRJLabel(lblName);
    fieldName.setColumns(sizeConst);
    
    JPanel wrap = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 0, 0));
    wrap.add(fieldName);
    addElement(lblVar, panelName);
    addElement(wrap, panelName);
  }
  
  /**
   * Auto constructs & adds a JPasswordField with a JLabel to a provided panel
   * 
   * @param lblName   The string name of the label to display
   * @param fieldName The PasswordField to correlate the label to
   * @param panelName The panel that the {@code lblName} and {@code fieldName} should be added to
   */
  private static void buildJPasswordField(
          String lblName,
          JPasswordField fieldName,
          int sizeConst,
          JPanel panelName) {
    JLabel lblVar = buildRJLabel(lblName);
    fieldName.setColumns(sizeConst);

    JPanel wrap = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 0, 0));
    wrap.add(fieldName);
    panelName.add(lblVar);
    panelName.add(wrap);
  }
  

  /**
   * Auto constructs & adds a JButton to a provided panel.
   *
   * @param btnName   The button name of the object to use
   * @param btnVar    Class local variable to actively use throughout creation
   * @param panelName The panel to add the button to
   */
  private static void buildButton(String btnName, JButton btnVar, JPanel panelName) {
    btnVar.setText(btnName);
    addElement(btnVar, panelName);
  }
  
  /**
   * Helper Method that adds the provided component to the panel.
   *
   * @param compVar   The component variable name 
   * @param panelName The panel name
   */
  private static void addElement(Component compVar, JPanel panelName) {
    panelName.add(compVar);
  }
}
