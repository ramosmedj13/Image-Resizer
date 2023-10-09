package imageresizer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

public class ImageResizerApp {
    private ImageResizer imageResizer;
    private ImageProcessor imageProcessor;

    public ImageResizerApp() {
        imageResizer = new ImageResizer();
        imageProcessor = new ImageProcessor();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ImageResizer");
        System.out.print("Enter the path to the image you want to resize: ");
        String inputImagePath = scanner.nextLine();

        try {
            BufferedImage image = imageProcessor.loadImage(inputImagePath);

            System.out.print("Enter the target width: ");
            int targetWidth = scanner.nextInt();
            System.out.print("Enter the target height: ");
            int targetHeight = scanner.nextInt();

            BufferedImage resizedImage = imageResizer.resizeImage(image, targetWidth, targetHeight);

            System.out.print("Enter the output file path (e.g., output.jpg): ");
            String outputPath = scanner.next();

            System.out.print("Enter the output format (e.g., JPG): ");
            String outputFormat = scanner.next().toUpperCase();

            imageProcessor.saveImage(resizedImage, outputPath, outputFormat);
            System.out.println("Your image has been resized and saved successfully.");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
