package blastandburn.controllers.ui.frontoffice.session;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import blastandburn.entities.session.Session;
import blastandburn.services.event.ServiceMail;
import blastandburn.services.session.ServiceSession;
import blastandburn.services.session.ServiceSessionChat;
import blastandburn.services.user.UserSession;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.teknikindustries.bulksms.SMS;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AddSessionFController implements Initializable {

    private ServiceSession ss = new ServiceSession();
    @FXML
    private FontAwesomeIconView close1;
    @FXML
    private JFXTextField price;
    @FXML
    private JFXTextField titleId;
    @FXML
    private JFXTextArea descriptionId;
    @FXML
    private JFXTextField numDays;
    private boolean titleSI = false,descriptionSI = false,daysSI = false,priceSI = false ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleValidatorSI();
        descriptionValidatorSI();
        priceValidatorSI();
        daysValidatorSI();
    }    

    @FXML
    private void closeAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) close1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) close1.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void addSessionAction(MouseEvent event) {
        ServiceSessionChat sc = new ServiceSessionChat();

        Session s = new Session();

        s.setTitle(titleId.getText());
        s.setDescription(descriptionId.getText());
        int n = Integer.parseInt(numDays.getText());
        s.setNumOfDays(n);
        int n1 = Integer.parseInt(price.getText());
        s.setPrice(n1);
        s.setCoachid(UserSession.getUser_id());
        ss.createSession(s);
        
        
        
          ServiceMail SM = new ServiceMail();

        String Msg = "Bonjour Mr/Mme , Il y'a une nouvelle planification bientôt , Veuillez consulter l'application pour plus de details  ";

        SM.sendmailfunc("ccandyxx1@gmail.com", Msg);
        
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle("Success");
        tray.setMessage("planification added successfully!");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
        
        
        
    }
     public void titleValidatorSI() {
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        titleId.setValidators(valid);
        valid.setMessage("Title is not valid");
        titleId.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    titleId.validate();
                    if (titleId.validate()) {
                        titleSI = true;
                    } else {
                        titleSI = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void descriptionValidatorSI() {
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        descriptionId.setValidators(valid);
        valid.setMessage("Description is not valid");
        descriptionId.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    descriptionId.validate();
                    if (descriptionId.validate()) {
                        descriptionSI = true;
                    } else {
                        descriptionSI = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void priceValidatorSI() {
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^(0|[1-9][0-9]*)$");
        price.setValidators(valid);
        valid.setMessage("price is not valid");
        price.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    price.validate();
                    if (price.validate()) {
                        priceSI = true;
                    } else {
                        priceSI = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void daysValidatorSI() {
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^(0|[1-9][0-9]*)$");
        numDays.setValidators(valid);
        valid.setMessage("Number of days is not valid");
        numDays.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    numDays.validate();
                    if (numDays.validate()) {
                        daysSI = true;
                    } else {
                        daysSI = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
}
