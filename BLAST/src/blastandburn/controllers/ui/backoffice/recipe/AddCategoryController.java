/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blastandburn.controllers.ui.backoffice.recipe;

import blastandburn.entities.recipe.RecipeCategory;
import static blastandburn.services.recipe.Constants.projectPath;
import blastandburn.services.recipe.RecipeCategoryService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
public class AddCategoryController implements Initializable {

    @FXML
    private JFXTextField Title;
    @FXML
    private Label imageTxt;
    File f = null;
    RecipeCategory rc = new RecipeCategory();
    @FXML
    private JFXButton AddBtn;

    boolean name = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameValidator();
    }

    @FXML
    private void addImage(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        f = chooser.showOpenDialog(null);
        imageTxt.setText(f.getName());
        System.out.println(f.getName());
        rc.setImgUrl(f.getName());
        System.out.println(rc.getImgUrl());
    }

    @FXML
    private void addRecipeCategoryAction(ActionEvent event) {
        javafx.stage.Window owner = AddBtn.getScene().getWindow();
        if (Title.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Error!",
                    "Enter a title for your category!");
            return;
        }

        RecipeCategoryService rcs = new RecipeCategoryService();
        rc.setName(Title.getText());
        File dest = new File(projectPath + "/src/blastandburn/resources/images/recipes/" + f.getName());
        try {
            FileUtils.copyFile(f, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rcs.Create_RecipeCategory(rc);

        showAlert(Alert.AlertType.CONFIRMATION, owner, "Confirmation!",
                "Category added successfully!");

    }

    @FXML
    private void closeAction(MouseEvent event) {
        Stage stage = new Stage();
        stage = (Stage) AddBtn.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    //contrôle saisie 
    public void nameValidator() { //name Empty string
        RegexValidator valid = new RegexValidator();
        valid.setRegexPattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$");
        Title.setValidators(valid);
        valid.setMessage("Enter the title");
        Title.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    Title.validate();
                    if (Title.validate()) {
                        name = true;
                    } else {
                        name = false;
                    }
                }
            }
        });
        try {
            Image errorIcon = new Image(new FileInputStream("src/blastandburn/resources/images/cancel.png"));
            valid.setIcon(new ImageView(errorIcon));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AddCategoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
