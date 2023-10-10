package imageresizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.Scanner;

/**
 * The ImageResizerApp class is responsible for managing the command-line interface (CLI) for image resizing.
 */
public class ImageResizerApp {
    private final ImageResizer imageResizer;
    private final ImageProcessor imageProcessor;
    private static final Logger logger = LoggerFactory.getLogger(ImageProcessor.class);
    Scanner scanner;

    /**
     * Initializes an instance of ImageResizerApp.
     */
    public ImageResizerApp() {
        imageResizer = new ImageResizer();
        imageProcessor = new ImageProcessor();
        scanner = new Scanner(System.in);
    }

    /**
     * Starts the image resizing application and guides the user through the resizing process.
     */
    public void start() {
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
        } catch (IllegalArgumentException e) {
            logger.error("Invalid input: {}", e.getMessage());
            System.err.println("Invalid input: " + e.getMessage());
            return;
        } catch (Exception e) {
            logger.error("Error loading image: {}", e.getMessage(), e);
            System.err.println("An error occurred. Please check the logs for details.");
        } finally {
            scanner.close();
        }
    }

    /**
     * Checks if the provided output format is valid.
     *
     * @param format The output format to check.
     * @return {@code true} if the format is valid, {@code false} otherwise.
     */
    private boolean isValidFormat(String format) {
        String[] validFormats = {"JPG", "JPEG", "PNG", "GIF", "BMP", "TIFF"};
        for (String validFormat : validFormats) {
            if (format.equals(validFormat)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Resizes an image and saves it to the specified output path with the given parameters.
     *
     * @param imagePath    The path to the input image.
     * @param targetWidth  The desired width of the resized image.
     * @param targetHeight The desired height of the resized image.
     * @param outputPath   The path where the resized image will be saved.
     * @param outputFormat The output format for the resized image (e.g., "JPG", "PNG").
     */
    public void resizeAndSaveImage(String imagePath, int targetWidth, int targetHeight, String outputPath,
                                   String outputFormat) {
        try {
            BufferedImage image = imageProcessor.loadImage(imagePath);
            BufferedImage resizedImage = imageResizer.resizeImage(image, targetWidth, targetHeight);
            imageProcessor.saveImage(resizedImage, outputPath, outputFormat);
        } catch (Exception e) {
            logger.error("Error loading image: {}", e.getMessage(), e);
        }
    }
}
