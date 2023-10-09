package imageresizer;

import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageResizerApp {
    private ImageResizer imageResizer;
    private ImageProcessor imageProcessor;
    private static final Logger logger = Logger.getLogger(ImageResizerApp.class.getName());

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

            System.out.print("Enter the compression quality (0.0 - 1.0): ");
            float quality = scanner.nextFloat();

            if (quality < 0.0 || quality > 1.0) {
                throw new IllegalArgumentException("Invalid compression quality.");
            }

            BufferedImage resizedImage = imageResizer.resizeImage(image, targetWidth, targetHeight);

            imageProcessor.saveImage(resizedImage, outputPath, outputFormat, quality);
            System.out.println("Your image has been resized and saved successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred: " + e.getMessage(), e);
            System.err.println("An error occurred. Please check the logs for details.");
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

    public void resizeAndSaveImage(String imagePath, int targetWidth, int targetHeight, String outputPath,
                                   String outputFormat, float quality) {
        try {
            BufferedImage image = imageProcessor.loadImage(imagePath);
            BufferedImage resizedImage = imageResizer.resizeImage(image, targetWidth, targetHeight);
            imageProcessor.saveImage(resizedImage, outputPath, outputFormat, quality);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred: " + e.getMessage(), e);
        }
    }
}
