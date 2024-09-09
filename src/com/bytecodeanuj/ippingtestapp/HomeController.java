package com.bytecodeanuj.ippingtestapp;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXProgressBar;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ANUJ KUMAR
 */
public class HomeController implements Initializable {

    @FXML
    private JFXProgressBar progressBar;
    @FXML
    private Button chooseBtn;
    @FXML
    private Label FileChooseStatusLabel;
    @FXML
    private Button ScanIPBtn;
    @FXML
    private TextArea reachableTextArea;
    @FXML
    private TextArea unreachableTextArea;
    @FXML
    private VBox container;
    /**
     * Initializes the controller class.
     */
    private File file;
    private FileChooser fileChooser;
//    private String logs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void ChooseIPList(ActionEvent event) {
        unreachableTextArea.clear();
        reachableTextArea.clear();
        fileChooser = new FileChooser();
        //
        try
        {
            chooseBtn.setDisable(true);
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            file = fileChooser.showOpenDialog(null);
            if (file != null)
            {// condition for file choosen success 
                FileChooseStatusLabel.setText(file.getName());
                chooseBtn.setDisable(false);
  
                
            } else // condition for cancel the dailog or no file choosen
            {
                Notifications.create().darkStyle().owner(container).title("No File Choosen..").showError();
                FileChooseStatusLabel.setText("No File Choosen...");
                chooseBtn.setDisable(false);
            }

        } catch (Exception e)
        {
            Notifications.create().darkStyle().owner(container).title("File is not valid..").text("Please choose a valid IP list").showError();
            e.printStackTrace();
        }

    }

    @FXML
    private void ScanIPs(ActionEvent event) {
        ScanIPBtn.setDisable(true);
        if (file != null)
        {
            try (BufferedReader br = new BufferedReader(new FileReader(file)))
            {
                String line;
                while ((line = br.readLine()) != null){
                    String[] ips = line.split(",");
                    int totalIPs = ips.length;
                    double progress = 0;
                    progressBar.setProgress(progress);
                    
                    for (String ip : ips){
                        ip = ip.trim();
                        if (!ip.isEmpty()){

                            boolean reachable = isReachable(ip);
                            
                           
                            
                            if (reachable){
                                reachableTextArea.setText(reachableTextArea.getText().concat(ip+"\n"));
                            }else{
                                unreachableTextArea.setText(unreachableTextArea.getText().concat(ip+"\n"));
                            }
                          
                            progressBar.setProgress(100);
                              
//                            textArea.setText(logs);
                        }
                    }
                }
            } catch (IOException e)
            {
                e.printStackTrace();

                Notifications.create().darkStyle().owner(container).text("Please select valid IP File").showError();
            }
        } else
        {
            Notifications.create().darkStyle().owner(container).text("Please select IP File first").showInformation();
        }
        ScanIPBtn.setDisable(false);
    }

    public static boolean isReachable(String ip) {
        try
        {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(10000); // Timeout in milliseconds
        } catch (IOException e)
        {
            return false;
        }
    }

    @FXML
    private void clearLogs(ActionEvent event) {
        reachableTextArea.clear();
        unreachableTextArea.clear();
        file = null;
        FileChooseStatusLabel.setText("No File Choosen...");
        progressBar.setProgress(0);
    }

    @FXML
    private void wrapText(ActionEvent event) {
        reachableTextArea.wrapTextProperty();
        unreachableTextArea.wrapTextProperty();
    }

    @FXML
    private void gotodev(ActionEvent event) {
        try
        {
            URI uri = new URI("https://github.com/bytecodeanuj");
            Desktop.getDesktop().browse(uri);
        } catch (Throwable e)
        {
            JOptionPane.showMessageDialog(null, "Error on opening link '{}' in system browser.");
            e.printStackTrace();
        }

    }

    @FXML
    private void getAppDetails(ActionEvent event) {
    }

}
