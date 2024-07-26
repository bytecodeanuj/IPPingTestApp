# IPPingTestApp Documentation

---


## Overview

The **IPPingTestApp** is a JavaFX-based application designed to check a list of IP addresses to check their availability. Users provide a list of IP addresses in text format, and the application checks each IP address for reachability, displaying results in the application window.

## Features

- **Graphical User Interface (GUI)**: Built using JavaFX for an intuitive user experience.
- **File Input**: Allows users to load a list of IP addresses from a file.
- **IP Pinging**: Checks the status of each IP address.
- **Result Display**: Shows scan results in the application window.

## System Requirements

- **Operating System**: Windows
- **Java Version**: JDK 1.8 
- **JavaFX**: Included in the project dependencies

## Usage

### GUI Overview

1. **File Input**:
   - Click on the "Choose IP List" button to select a file containing IP addresses with comma separated.

2. **Start Scan**:
   - Click the "Test IP" button to begin scanning the IP addresses.

3. **Result Display**:
   - The results will be displayed in a table format within the application window.

### Input File Format

The input file should be a plain text file where each IP address separated with a comma. For example:

```
192.168.1.1,
10.0.0.1,
172.16.0.1
```

### Example Usage

1. **Load IP List**:
   - Click the "Choose IP List" button and select a file with IP addresses.

2. **Start Scanning**:
   - Click "Test IP" to initiate the scan. The application will display the results in a table.

## Code Structure

### Main Application Class

- **`App.java`**: The entry point of the application, containing the `main` method and JavaFX initialization code.

### Controller Class

- **`HomeController.java`**: Manages user interactions, such as loading files and starting the scan. Updates the UI with results.


### FXML Layout

- **`Home.fxml`**: Defines the layout of the user interface using JavaFX FXML.

## Error Handling

- **File Issues**: Ensure the file exists and contains valid IP addresses.
- **Network Issues**: The test may fail if there are network connectivity problems.

## Troubleshooting

- **Application Not Running**: Verify that JavaFX is correctly configured and that you are using a compatible JDK version.
- **Invalid IP Addresses**: Ensure IP addresses are correctly formatted in the input file.


## Contact

For questions or support, please contact [anujv8692@gmail.com](mailto:anujv8692@gmail.com).

---

Thank you for using the IPPingTest App!
