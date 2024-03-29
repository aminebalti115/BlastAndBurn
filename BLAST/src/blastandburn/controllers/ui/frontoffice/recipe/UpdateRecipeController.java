/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.frontoffice.recipe;

import blastandburn.entities.recipe.Recipe;
import blastandburn.services.recipe.RecipeService;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.io.FileUtils;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class UpdateRecipeController implements Initializable {

    @FXML
    private TextArea DescTF;
    @FXML
    private Label imgTF;
    @FXML
    private TextField CaloriesTF;
    @FXML
    private TextField TitleTF;
    @FXML
    private TextArea IngredientsTF;
    @FXML
    private TextArea StepsTF;
    @FXML
    private TextField DurationTF;
    @FXML
    private TextField PersonsTF;

    Recipe recipe;
    RecipeService rs = new RecipeService();
    RecipeHolder rh = RecipeHolder.getINSTANCE();
    private static String projectPath = System.getProperty("user.dir").replace("/", "\\");
    File f = null;
    @FXML
    private JFXButton updateBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        recipe = rs.getRecipe(rh.getId());
        TitleTF.setText(recipe.getTitle());
        imgTF.setText(recipe.getImgUrl());
        DescTF.setText(recipe.getDescription());
        IngredientsTF.setText(recipe.getIngredients());
        StepsTF.setText(recipe.getSteps());
        CaloriesTF.setText(String.valueOf(recipe.getCalories()));
        DurationTF.setText(String.valueOf(recipe.getDuration()));
        PersonsTF.setText(String.valueOf(recipe.getPersons()));

    }

    @FXML
    private void closeAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) PersonsTF.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        f = fileChooser.showOpenDialog(null);
        imgTF.setText(f.getName());
        recipe.setImgUrl(f.getName());

    }

    @FXML
    private void UpdateRecipeAction(ActionEvent event) {
        javafx.stage.Window owner = updateBtn.getScene().getWindow();
        if (recipe != null) {
            recipe = rs.getRecipe(rh.getId());
            Recipe r = new Recipe();
            r.setTitle(TitleTF.getText());
            r.setImgUrl(imgTF.getText());
            r.setDescription(DescTF.getText());
            r.setIngredients(IngredientsTF.getText());
            r.setSteps(StepsTF.getText());
            r.setCalories(Integer.valueOf(CaloriesTF.getText()));
            r.setDuration(Integer.valueOf(DurationTF.getText()));
            r.setPersons(Integer.valueOf(PersonsTF.getText()));
            File dest = null;
            if (f != null) {
                dest = new File(projectPath + "/src/blastandburn/resources/images/recipes/" + recipe.getImgUrl());
            } else {
                System.out.println("Empty!");
            }

            try {
                if (dest != null) {
                    if (FileUtils.contentEquals(f, dest)) {
                        System.out.println("exists!");
                    } else {
                        FileUtils.copyFile(f, dest);
                    }
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            rs.Update_Recipe(r, rh.getId());

            showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation!",
                    "Recipe modified successfully!");

        }
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
