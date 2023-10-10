package gui;

import imageresizer.ImageResizerApp;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The ImageResizerGUI class is a JavaFX application that provides a graphical user interface (GUI) for resizing images.
 * Users can input the image path, target width, and height, and the application will resize the image and display the result.
 */
public class ImageResizerGUI extends Application {
    private ImageResizerApp app;

    /**
     * The main method of the application that launches the GUI.
     *
     * @param args The command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the ImageResizerGUI application.
     *
     * @param primaryStage The primary stage for the GUI.
     */
    @Override
    public void start(Stage primaryStage) {
        app = new ImageResizerApp();

        primaryStage.setTitle("Image Resizer - GUI");
        BorderPane root = new BorderPane();

        // Create UI components
        VBox vBox = new VBox(10);
        Label titleLabel = new Label("Image Resizer");
        Label imagePathLabel = new Label("Enter Image Path:");
        TextField imagePathTextField = new TextField();
        Label widthLabel = new Label("Target Width:");
        TextField widthTextField = new TextField();
        Label heightLabel = new Label("Target Height:");
        TextField heightTextField = new TextField();
        Label outputPathLabel = new Label("Output Path:");
        TextField outputPathTextField = new TextField();
        Button resizeButton = new Button("Resize");
        Label outputLabel = new Label("");
        ProgressIndicator progressIndicator = new ProgressIndicator();
        HBox progressBox = new HBox(progressIndicator);
        progressBox.setVisible(false); // Initially hidden

        // Configure components
        vBox.getChildren().addAll(
                titleLabel,
                imagePathLabel, imagePathTextField,
                widthLabel, widthTextField,
                heightLabel, heightTextField,
                outputPathLabel, outputPathTextField,
                resizeButton,
                outputLabel,
                progressBox
        );

        resizeButton.setOnAction(event -> {
            String imagePath = imagePathTextField.getText();
            int width = Integer.parseInt(widthTextField.getText());
            int height = Integer.parseInt(heightTextField.getText());
            String outputPath = outputPathTextField.getText();
            String outputFormat = "JPG";

            try {
                progressBox.setVisible(true); // Show progress bar
                Task<Void> resizingTask = new Task<>() {
                    @Override
                    protected Void call() {
                        app.resizeAndSaveImage(imagePath, width, height, outputPath, outputFormat);
                        return null;
                    }
                };

                resizingTask.setOnSucceeded(e -> {
                    outputLabel.setText("Image resized and saved successfully.");
                    progressBox.setVisible(false); // Hide progress bar
                });

                resizingTask.setOnFailed(e -> {
                    String errorMessage = "Error: ";
                    Throwable exception = resizingTask.getException();

                    if (exception != null) {
                        errorMessage += exception.getMessage();
                        // You can also log the exception for debugging purposes
                        exception.printStackTrace();
                    } else {
                        errorMessage += "An unknown error occurred.";
                    }

                    outputLabel.setText(errorMessage);
                    progressBox.setVisible(false); // Hide progress bar
                });

                new Thread(resizingTask).start();
            } catch (Exception e) {
                outputLabel.setText("Error: " + e.getMessage());
            }
        });

        root.setCenter(vBox);
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}