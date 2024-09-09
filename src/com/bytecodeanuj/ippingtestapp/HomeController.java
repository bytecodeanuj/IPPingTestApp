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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
       clearBothTextFields();
        if (file != null){
            try{
                BufferedReader br = new BufferedReader(new FileReader(file));
                String ips[] = br.readLine().split(",");
                ExecutorService executor = Executors.newFixedThreadPool(10); // Adjust thread pool size if needed

            Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < ips.length; i++) {
                    final int progress = i + 1;
                    ips[i]=ips[i].trim();
                    if(isReachable(ips[i])){
                        reachableTextArea.setText(reachableTextArea.getText().concat(ips[i]+"\n"));
                    }else{
                        unreachableTextArea.setText(unreachableTextArea.getText().concat(ips[i]+"\n"));
                    }
                    
                    updateProgress(progress, ips.length);
//                    updateMessage("Checked " + progress + " IPs");
                    Thread.sleep(100); // Optional: sleep to simulate progress
                }
                executor.shutdown();
                return null;
            }
        };
            progressBar.progressProperty().bind(task.progressProperty());
            ScanIPBtn.setDisable(true);
            new Thread(task).start();
//            task.setOnSucceeded(null);
//            task.setOnFailed(null);
//            task.getException().printStackTrace();

            } catch (Exception e)
            {
                Notifications.create().darkStyle().owner(container).title("App Crashed").text("Please choose a valid IP List").showError();
                System.exit(0);
                e.printStackTrace();
            }
        }else{
            Notifications.create().darkStyle().owner(container).text("Please select IP File first").showInformation();
        }
        ScanIPBtn.setDisable(false);
    }

    public static boolean isReachable(String ip) {
        try
        {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(10000); // Timeout in milliseconds
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    private void clearBothTextFields(){
        reachableTextArea.clear();
        unreachableTextArea.clear();
    }

    @FXML
    private void clearLogs(ActionEvent event) {
        reachableTextArea.clear();
        unreachableTextArea.clear();
        file = null;
        FileChooseStatusLabel.setText("No File Choosen...");
        progressBar.progressProperty().unbind();
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
            JOptionPane.showMessageDialog(null, "Error on opening link {https://github.com/bytecodeanuj} in system browser.");
            e.printStackTrace();
        }

    }

    @FXML
    private void getAppDetails(ActionEvent event) {
        
    }

}
