package gui;

import imageresizer.ImageResizerApp;
import javafx.application.Application;
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

        primaryStage.setTitle("Image Resizer");
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
        Label outputFormatLabel = new Label("Output Format:");
        TextField outputFormatTextField = new TextField();
        Label qualityLabel = new Label("Compression Quality (0.0 - 1.0):");
        TextField qualityTextField = new TextField();
        Button resizeButton = new Button("Resize");
        Label outputLabel = new Label("");

        // Configure components
        vBox.getChildren().addAll(
                titleLabel,
                imagePathLabel, imagePathTextField,
                widthLabel, widthTextField,
                heightLabel, heightTextField,
                outputPathLabel, outputPathTextField,
                outputFormatLabel, outputFormatTextField,
                qualityLabel, qualityTextField,
                resizeButton,
                outputLabel
        );

        resizeButton.setOnAction(event -> {
            String imagePath = imagePathTextField.getText();
            int width = Integer.parseInt(widthTextField.getText());
            int height = Integer.parseInt(heightTextField.getText());
            String outputPath = outputPathTextField.getText();
            String outputFormat = outputFormatTextField.getText().toUpperCase();
            float quality = Float.parseFloat(qualityTextField.getText());

            try {
                app.resizeAndSaveImage(imagePath, width, height, outputPath, outputFormat, quality);
                outputLabel.setText("Image resized and saved successfully.");
            } catch (Exception e) {
                outputLabel.setText("Error: " + e.getMessage());
            }
        });

        root.setCenter(vBox);
        Scene scene = new Scene(root, 400, 400); // Adjust the scene size
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}