package imageresizer;

import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ImageResizerApp class is responsible for managing the command-line interface (CLI) for image resizing.
 */
public class ImageResizerApp {
    private ImageResizer imageResizer;
    private ImageProcessor imageProcessor;
    private static final Logger logger = Logger.getLogger(ImageResizerApp.class.getName());
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
     * @param quality      The compression quality (0.0 - 1.0) for the resized image.
     */
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
