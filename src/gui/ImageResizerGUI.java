package gui;

import imageresizer.ImageResizerApp;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ImageResizerGUI extends Application {
    private ImageResizerApp app;

    public static void main(String[] args) {
        launch(args);
    }

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
                resizeButton,
                outputLabel,
                progressBox
        );

        resizeButton.setOnAction(event -> {
            String imagePath = imagePathTextField.getText();
            int width = Integer.parseInt(widthTextField.getText());
            int height = Integer.parseInt(heightTextField.getText());
            String outputPath = "output.jpg";
            String outputFormat = "JPG";
            float quality = 1.0f;

            try {
                progressBox.setVisible(true); // Show progress bar
                Task<Void> resizingTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        app.resizeAndSaveImage(imagePath, width, height, outputPath, outputFormat, quality);
                        return null;
                    }
                };

                resizingTask.setOnSucceeded(e -> {
                    outputLabel.setText("Image resized and saved successfully.");
                    progressBox.setVisible(false); // Hide progress bar
                });

                resizingTask.setOnFailed(e -> {
                    outputLabel.setText("Error: " + e.getSource().getException().getMessage());
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