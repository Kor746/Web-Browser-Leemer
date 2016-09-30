/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dan;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author Daniel
 */
public class MainFormController implements Initializable {
    
    @FXML
    private Button backButton;
    @FXML
    private Button forwardButton;
    @FXML
    private TextField textField;
    @FXML
    private Button GoButton;
    @FXML
    private WebView mainWebView;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ProgressBar pb;
    @FXML
    private Button refreshButton;
    
    WebBrowserModel webModel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webModel = new WebBrowserModel();
        final WebEngine engine = mainWebView.getEngine();
        Image imageRefresh = new Image("com/dan/images/refresh.png");
        ImageView image = new ImageView(imageRefresh);
        refreshButton.setGraphic(image);
        image.setFitWidth(10);
        image.setFitHeight(10);
        //default button state
        backButton.setDisable(true);
        forwardButton.setDisable(true);
        //CSS for progress bar
        pb.getStylesheets().add("com/dan/style.css");
        pb.getStyleClass().add("progressbar");
        //This allows the progress bar to be visible when loading a page
        pb.progressProperty().bind(engine.getLoadWorker().progressProperty());
        pb.visibleProperty().bind(engine.getLoadWorker().runningProperty());
    }    
    //This event will allow the user to move back in their web history when clicking the '<' button.
    @FXML
    private void handleBackAction(ActionEvent event) {
        final WebEngine engine = mainWebView.getEngine(); 
        engine.load(webModel.goBack());
        textField.setText(engine.getLocation());
        //makes sure the back button is disabled if there is no element before it
        if(webModel.currentIndex()-1 < 0)
        {
            backButton.setDisable(true);
        }  
        //on click of back button, ensures fwd button is enabled
        forwardButton.setDisable(false);
    }
    //This event will allow the user to move forward in their web history when clicking the '>' button.
    @FXML
    private void handleForwardAction(ActionEvent event) {
         final WebEngine engine = mainWebView.getEngine();
         engine.load(webModel.goForward());
         textField.setText(engine.getLocation());
         //makes sure the fwd button is disabled if there is no element after it
         if(webModel.currentIndex()+1 > webModel.getSize()-1)
         {
            forwardButton.setDisable(true);
         }
         //on click of fwd button, ensures back button is enabled
         backButton.setDisable(false);
    }
    
    //This allows the user to load a specified URL when pressing the "ENTER" key on the text field
    @FXML
    private void handleURLAction(ActionEvent event) {
       //reference same Action event from handleGo
       handleGo(event);
    }
    //This allows the user to load a URL when pressing the "Go" button
    @FXML
    private void handleGo(ActionEvent event) {
       final WebEngine engine = mainWebView.getEngine();
       mainWebView.getStylesheets().add("com/dan/style.css");
       //extract text value to string variable
       String webUrl = textField.getText();
       String http = "http://";
       //This checks if the URL string is empty
       if(webUrl != null && !webUrl.isEmpty())
       {
            //This changes the opacity of the css property once Go is clicked
            mainWebView.getStyleClass().add("mainWeb");
            
            // This validates the string in being a URL
            if(!(webUrl.startsWith(http) || webUrl.startsWith("https://")))
            {
                //This will append "http://" if it is missing in the given URL
                engine.load(http + webUrl);
                textField.setText(engine.getLocation());
                //push url to stack
                webModel.stackPush(engine.getLocation()); 
                //If size of stack is more than 2 it will make bck btn active
                if(webModel.getSize() >= 2)
                {
                    backButton.setDisable(false);
                }
                //if the index after is greater than the last index, fwd is disabled
                if(webModel.currentIndex() +1 > webModel.getSize()-1)
                {
                    forwardButton.setDisable(true);
                }
            }
            else
            {
                //This allows the user to manually input 'http://' or 'https://' 
                engine.load(webUrl);
                //push url to stack
                webModel.stackPush(engine.getLocation()); 
                if(webModel.getSize() >= 2)
                {
                    backButton.setDisable(false);
                }
                if(webModel.currentIndex() +1 > webModel.getSize()-1)
                {
                    forwardButton.setDisable(true);
                }
                
            }  
       }  
    }
    //If the user clicks this button it will reload the current page
    @FXML
    private void handleRefresh(ActionEvent event) {
        final WebEngine engine = mainWebView.getEngine();
        String webUrl = textField.getText();
        String currentUrl = engine.getLocation();
        engine.reload();
        //if there is a url, but it is not accurate it will add the correct url
        if(webUrl != null && !webUrl.isEmpty())
        {
            if(!webUrl.equals(currentUrl))
            {
                textField.setText(currentUrl);
            }
        }
        else //if there is no url, it will add the current url
        {
            textField.setText(currentUrl);
        }
    }
}
