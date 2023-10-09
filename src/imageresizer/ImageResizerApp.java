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

        try {
            System.out.print("Enter the path to the image you want to resize: ");
            String inputImagePath = scanner.nextLine();

            BufferedImage image = imageProcessor.loadImage(inputImagePath);

            System.out.print("Enter the target width: ");
            int targetWidth = scanner.nextInt();
            System.out.print("Enter the target height: ");
            int targetHeight = scanner.nextInt();

            if (targetWidth <= 0 || targetHeight <= 0) {
                throw new IllegalArgumentException("Invalid target width or height.");
            }

            scanner.nextLine(); // Consume the newline character.

            System.out.print("Enter the output file path (e.g., output.jpg): ");
            String outputPath = scanner.next();

            System.out.print("Enter the output format (e.g., JPG): ");
            String outputFormat = scanner.next().toUpperCase();

            if (!isValidFormat(outputFormat)) {
                throw new IllegalArgumentException("Invalid output format.");
            }

            BufferedImage resizedImage = imageResizer.resizeImage(image, targetWidth, targetHeight);

            imageProcessor.saveImage(resizedImage, outputPath, outputFormat);
            System.out.println("Your image has been resized and saved successfully.");
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private boolean isValidFormat(String format) {
        String[] validFormats = {"JPG", "JPEG", "PNG", "GIF", "BMP", "TIFF"};
        for (String validFormat : validFormats) {
            if (format.equals(validFormat)) {
                return true;
            }
        }

        return false;
    }
}
