/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice;

import animatefx.animation.ZoomIn;
import blastandburn.controllers.ui.frontoffice.event.EventItemController;
import blastandburn.controllers.ui.frontoffice.recipe.RecipeItemController;
import blastandburn.controllers.ui.frontoffice.session.SessionItemController;
import blastandburn.entities.event.Event;
import blastandburn.entities.recipe.Recipe;
import blastandburn.entities.session.Session;
import blastandburn.services.ui.UIService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class HomePageController implements Initializable {

    @FXML
    private ScrollPane homePane;
    //@FXML
   // private HBox bookHBox;
    //@FXML
    //private HBox taskHBox;
    @FXML
    private HBox eventHBox;
    @FXML
    private HBox recipeHBox;
    @FXML
    private HBox sessionHBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new ZoomIn(homePane).play();
      
        try {
            sessionHBox();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            eventHBox();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        recipeHBox();
    }

    
    public void eventHBox() throws SQLException {
        UIService us = new UIService();
        List<Event> events;
        events = us.upcomingEvent();
        for (int i = 0; i < events.size(); i++) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/event/EventItem.fxml"));
            try {
                Pane pane = loader.load();
                EventItemController c = loader.getController();
                c.setData(events.get(i));
                eventHBox.getChildren().add(pane);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    
    public void recipeHBox(){
        UIService us=new UIService();
        List<Recipe> recipes;       
        recipes=us.topThreeRecipe();
        for(int i=0;i<recipes.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/recipe/RecipeItem.fxml"));
            try {
                Pane pane=loader.load();
                RecipeItemController c=loader.getController();
                c.setData(recipes.get(i));
                recipeHBox.getChildren().add(pane);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void sessionHBox() throws SQLException {
        UIService us=new UIService();
        List<Session> sessions;       
        sessions=us.newSessions();
        for(int i=0;i<sessions.size();i++){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/blastandburn/views/ui/frontoffice/session/SessionItem.fxml"));
            try {
                AnchorPane pane=loader.load();
                SessionItemController c=loader.getController();
                c.setData(sessions.get(i),false);
                sessionHBox.getChildren().add(pane);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
