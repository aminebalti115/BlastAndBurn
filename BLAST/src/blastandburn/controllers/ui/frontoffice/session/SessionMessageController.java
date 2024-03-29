/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.session;

import blastandburn.entities.session.SessionMessage;
import blastandburn.services.session.ServiceSessionMessage;
import blastandburn.services.user.UserSession;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author fatma
 */
public class SessionMessageController implements Initializable {

    @FXML
    private Pane textId;
    @FXML
    private TextField msgId;
    private Timer timer = new Timer();
    ServiceSessionMessage smg = new ServiceSessionMessage();
    @FXML
    private JFXListView<String> chatArea;
    ObservableList<String> s;
    int Sessionid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        s = FXCollections.observableArrayList();
        for (int i = 0; i < smg.listMessage(Sessionid).size(); i++) {
            s.add(smg.listMessage(Sessionid).get(i).getMsg());
        }
        chatArea.setItems(s);
    }
    public void setdata(int i)
            
    {
        Sessionid=i;
        System.out.println(i);
    }
            

    @FXML
    private void envoyerMessageAction(ActionEvent event) {
        ServiceSessionMessage sem = new ServiceSessionMessage();

        SessionMessage sm = new SessionMessage();
sm.setChatId(sem.getChatid(Sessionid));
        sm.setMsg(msgId.getText());
        sm.setUserId(UserSession.getUser_id());
        sem.createSessionMesage(sm);
        s = FXCollections.observableArrayList();
        for (int i = 0; i < smg.listMessage(Sessionid).size(); i++) {
            s.add(smg.listMessage(Sessionid).get(i).getMsg());

        }
        chatArea.setItems(s);
        msgId.setText("");
    }

}
