/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.session;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import blastandburn.controllers.ui.frontoffice.HomePageHolderController;
import blastandburn.controllers.ui.frontoffice.recipe.RecipeItemController;
import blastandburn.controllers.ui.frontoffice.report.RateAlertUIController;
import blastandburn.controllers.ui.frontoffice.report.RatePopupUIController;
import blastandburn.controllers.ui.frontoffice.report.ReportPopupUIController;
import blastandburn.entities.session.Session;
import blastandburn.services.report.RateService;
import blastandburn.services.session.ServiceSession;
import blastandburn.services.session.ServiceSessionChat;
import blastandburn.services.ui.UIService;
import blastandburn.services.user.UserSession;
import static com.google.zxing.client.result.ParsedResultType.SMS;
import com.teknikindustries.bulksms.SMS;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author user
 */
public class SessionItemController implements Initializable {

    @FXML
    private Label coachname;
    @FXML
    private Label sessionTitle;
    @FXML
    private Label sessionDesc;
    double xOffset, yOffset;
    public int id;
    ServiceSession ss = new ServiceSession();
    private int sessionId, coachid;
    int id1 = 0;
    private FontAwesomeIconView icon;
    @FXML
    private Label price;
    @FXML
    private Button update;
    @FXML
    private FontAwesomeIconView icon1;
    ServiceSessionChat scm = new ServiceSessionChat();
    @FXML
    private Button participer;

    Session session;
    SessionHolder tah = SessionHolder.getINSTANCE();
    @FXML
    private FontAwesomeIconView suprimer;
    @FXML
    private AnchorPane menuId;
    private boolean menuIsDisplayed = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        session = ss.searchSession(tah.getId());
    Session s = new Session();
s.setUserId(UserSession.getUser_id());

        if (UserSession.getRole().equals("coach") && UserSession.getUser_id() != coachid) {
            update.setVisible(true);
            icon1.setVisible(true);
            participer.setVisible(true);
            suprimer.setVisible(true);

        }
        if (UserSession.getRole().equals("moderator")) {
            suprimer.setVisible(true);
        }

    }

    public void setData(Session s,boolean icon) {


    icon1.setVisible(icon);
        UIService us = new UIService();
        sessionTitle.setText(s.getTitle());
        sessionDesc.setText(s.getDescription());
        price.setText(s.getPrice() + "dt");
        sessionId = s.getSessionId();
        coachid = s.getCoachid();


        try {
         coachname.setText(us.planning(s.getCoachid()));
        } catch (SQLException ex) {
            
            System.out.println(ex.getMessage());
                        System.out.println("hey guys probleme here");

        }
//         if (UserSession.getRole().equals("coach") ) {
//            update.setVisible(true);
//            icon1.setVisible(false);

        // }
    }

    public FontAwesomeIconView getIcon() {
        return icon;
    }

    public void setIcon(FontAwesomeIconView icon) {
        this.icon = icon;
    }

//    private void msgEvent(MouseEvent event) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/session/SessionMessage.fxml"));
//        Parent root = loader.load();
//        Stage stage = new Stage();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.initStyle(StageStyle.TRANSPARENT);
//        scene.setFill(Color.TRANSPARENT);
//        HomePageHolderController hpc = new HomePageHolderController();
//        hpc.setStage(stage);
//        stage.show();
//        root.setOnMousePressed((MouseEvent mouseEvent) -> {
//            xOffset = mouseEvent.getSceneX();
//            yOffset = mouseEvent.getSceneY();
//        });
//        root.setOnMouseDragged((MouseEvent mouseEvent) -> {
//            stage.setX(mouseEvent.getScreenX() - xOffset);
//            stage.setY(mouseEvent.getScreenY() - yOffset);
//            stage.setOpacity(0.85f);
//        });
//        root.setOnMouseReleased((MouseEvent mouseEvent) -> {
//            stage.setOpacity(1.0f);
//        });
//        ss.modifierV(id);
//        
//    }
    @FXML
    private void UpdateEvent(MouseEvent event) throws IOException {
       Session s = new Session();

        s.setUserId(UserSession.getUser_id());
        if (UserSession.getUser_id() != coachid) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText(null);
            alert.setContentText("vous ne pouvez pas modifier cette session");

            alert.showAndWait();
            System.out.println("error");
        }
        else{
        SessionHolder th = SessionHolder.getINSTANCE();
        th.setId(sessionId);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/session/UpdateSessoio.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        HomePageHolderController hpc = new HomePageHolderController();
        hpc.setStage(stage);
        stage.show();
        root.setOnMousePressed((MouseEvent mouseEvent) -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent mouseEvent) -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
            stage.setOpacity(0.85f);
        });
        root.setOnMouseReleased((MouseEvent mouseEvent) -> {
            stage.setOpacity(1.0f);
        });}

    }

    @FXML
    private void participate(MouseEvent event) {

        Session s = new Session();

        s.setUserId(UserSession.getUser_id());
        if (UserSession.getUser_id() != coachid) {
            ss.participate(s, sessionId);
            scm.createSessionChat(sessionId);
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText(null);
            alert.setContentText("vous ne pouvez pas participer cette session");

            alert.showAndWait();
            System.out.println("error");
        }
   TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle("Success");
        tray.setMessage("YOU PARTICIPATED IN THIS EVENT WITH SUCCES");
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
}

    @FXML
    private void msgEvent(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/session/SessionMessage.fxml"));
        Parent root = loader.load();
        SessionMessageController msg=loader.getController();
        msg.setdata(sessionId);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        HomePageHolderController hpc = new HomePageHolderController();
        hpc.setStage(stage);
        stage.show();
        root.setOnMousePressed((MouseEvent mouseEvent) -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent mouseEvent) -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
            stage.setOpacity(0.85f);
        });
        root.setOnMouseReleased((MouseEvent mouseEvent) -> {
            stage.setOpacity(1.0f);
        });

    }
//     @FXML
//    private void SupprierSession(MouseEvent event) {
//       s.SupprimerSession(id);
//    }

    @FXML
    private void SupprierSession(MouseEvent event) {
        Session s = new Session();

        s.setUserId(UserSession.getUser_id());
        if (UserSession.getUser_id() == coachid) {
            ss.SupprimerSession(sessionId);
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText(null);
            alert.setContentText("vous ne pouvez pas supprimer cette session");

            alert.showAndWait();
            System.out.println("error");
        }

    }

    @FXML
    private void dotsAction(MouseEvent event) {
        if (menuIsDisplayed) {           
            menuIsDisplayed = false;
            new ZoomOut(menuId).play();
            menuId.setDisable(true);
        } else {
            menuId.setVisible(true);
            menuId.setDisable(false);
            menuIsDisplayed = true;
            new ZoomIn(menuId).play();
        }
    }

    @FXML
    private void reportAction(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/report/ReportPopupUI.fxml"));
        Parent root=null;
            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SessionItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        ReportPopupUIController c = loader.getController();
        c.setData(sessionId, UserSession.getUser_id(), "Session", sessionTitle.getText());
        Stage stage = new Stage();
        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            HomePageHolderController hpc = new HomePageHolderController();
            hpc.setStage(stage);
            stage.show();
            root.setOnMousePressed((MouseEvent mouseEvent) -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
                stage.setOpacity(0.85f);
            });
            root.setOnMouseReleased((MouseEvent mouseEvent) -> {
                stage.setOpacity(1.0f);
            });
    }

    @FXML
    private void rateAction(MouseEvent event) throws IOException {
        RateService rs = new RateService();
        if (rs.isRated(sessionId, UserSession.getUser_id(), "Session")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/report/RateAlertUI.fxml"));
            Parent root = loader.load();
            RateAlertUIController c = loader.getController();
            c.setData(sessionId, UserSession.getUser_id(), "Session");
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            HomePageHolderController hpc = new HomePageHolderController();
            hpc.setStage(stage);
            stage.show();
            root.setOnMousePressed((MouseEvent mouseEvent) -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
                stage.setOpacity(0.85f);
            });
            root.setOnMouseReleased((MouseEvent mouseEvent) -> {
                stage.setOpacity(1.0f);
            });
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/blastandburn/views/ui/frontoffice/report/RatePopupUI.fxml"));
            Parent root = loader.load();
            RatePopupUIController c = loader.getController();
            c.setData(sessionId, UserSession.getUser_id(), "Session");
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            HomePageHolderController hpc = new HomePageHolderController();
            hpc.setStage(stage);
            stage.show();
            root.setOnMousePressed((MouseEvent mouseEvent) -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });
            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
                stage.setOpacity(0.85f);
            });
            root.setOnMouseReleased((MouseEvent mouseEvent) -> {
                stage.setOpacity(1.0f);
            });
        }
    }

}
