package com.bytecodeanuj.ippingtestapp;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ANUJ KUMAR
 */
public class HomeController implements Initializable {

	@FXML
	private ProgressBar progressBar;
	@FXML
	private Button chooseBtn;
	@FXML
	private Label FileChooseStatusLabel;
	@FXML
	private Button ScanIPBtn;
	@FXML
	private TextArea textArea;

	/**
	 * Initializes the controller class.
	 */
	private File file;
	private FileChooser fileChooser;
	private String logs;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	@FXML
	private void ChooseIPList(ActionEvent event) {
		fileChooser = new FileChooser();
		//
		try {
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
			file = fileChooser.showOpenDialog(null);
			if (file != null) {// condition for cancel the dailog or no file choosen
				FileChooseStatusLabel.setText(file.getName());
			} else {
				JOptionPane.showMessageDialog(null, "No File Choosen...");
				FileChooseStatusLabel.setText("No File Choosen...");
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "File couldn't be loaded...");
			e.printStackTrace();
		}

	}

	@FXML
	private void ScanIPs(ActionEvent event) {
		if (file != null) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] ips = line.split(",");
					int totalIPs = ips.length;
					double progress = 0;
					for (String ip : ips) {
						ip = ip.trim();
						if (!ip.isEmpty()) {
							logs = textArea.getText().concat("Checking IP : " + ip + "\r\n");
							textArea.setText(logs);

							boolean reachable = isReachable(ip);

							if (reachable) {
								logs = logs.concat(ip + " is Reachable \r\n");
							} else {
								logs = logs.concat(ip + " is not Reachable \r\n");

							}
							progress++;
							progressBar.setProgress(progress);
							textArea.setText(logs);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please select IP File first");
		}

	}

	public static boolean isReachable(String ip) {
		try {
			InetAddress address = InetAddress.getByName(ip);
			return address.isReachable(10000); // Timeout in milliseconds
		} catch (IOException e) {
			return false;
		}
	}

	@FXML
	private void clearLogs(ActionEvent event) {
		textArea.clear();
		file = null;
		FileChooseStatusLabel.setText("No File Choosen...");
		progressBar.setProgress(0);
	}

	@FXML
	private void wrapText(ActionEvent event) {
		textArea.wrapTextProperty();
	}

	@FXML
	private void gotodev(ActionEvent event) {
		try {
			URI uri = new URI("https://github.com/bytecodeanuj");
			Desktop.getDesktop().browse(uri);
		} catch (Throwable e) {
			JOptionPane.showMessageDialog(null, "Error on opening link '{}' in system browser.");
			e.printStackTrace();
		}

	}

}
