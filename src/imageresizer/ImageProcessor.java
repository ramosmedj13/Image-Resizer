package imageresizer;

import exception.FileNotFoundException;
import exception.UnsupportedImageFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;

/**
 * The ImageProcessor class handles loading and saving images.
 * It provides methods for loading an image from a file and saving an image to a file.
 */
public class ImageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ImageProcessor.class);

    /**
     * Loads an image from the specified file path.
     *
     * @param imagePath The path to the image file to load.
     * @return The loaded BufferedImage, or null if an error occurs.
     */
    public BufferedImage loadImage(String imagePath) {
        try {
            File file = new File(imagePath);

            if (!file.exists()) {
                throw new FileNotFoundException("File not found: " + imagePath);
            }

            String fileExtension = getFileExtension(imagePath);


            try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(file)) {
                Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(fileExtension);

                if (!readers.hasNext()) {
                    throw new UnsupportedImageFormatException("Unsupported image format: " + fileExtension);
                }

                ImageReader reader = readers.next();
                reader.setInput(imageInputStream);

                return reader.read(0);
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", e.getMessage(), e);
        } catch (UnsupportedImageFormatException e) {
            logger.error("Unsupported image: {}", e.getMessage(), e);
        } catch (IOException e) {
            logger.error("Error loading image: {}", e.getMessage(), e);
        }
        return null;
    }


    /**
     * Extracts the file extension from a file path.
     *
     * @param filePath The path of the file.
     * @return The file extension (e.g., "jpg", "png").
     * @throws IllegalArgumentException If the file path is invalid.
     */
    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == filePath.length() - 1) {
            return ""; // Return empty string for invalid file paths
        }
        return filePath.substring(dotIndex + 1).toUpperCase(); // Normalize to uppercase
    }

    /**
     * Saves an image to the specified file path with the given output format and compression quality.
     *
     * @param image       The BufferedImage to save.
     * @param outputPath  The path where the image will be saved.
     * @param outputFormat The output image format (e.g., "jpg", "png").
     * @throws IOException If an error occurs while saving the image.
     */
    public void saveImage(BufferedImage image, String outputPath, String outputFormat) throws IOException {
        File file = new File(outputPath);

        // Auto-detect the format based on the file extension
        String fileExtension = getFileExtension(outputPath);
        if (fileExtension.isEmpty()) {
            throw new IllegalArgumentException("Invalid output file path: " + outputPath);
        }

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(fileExtension);

        if (!writers.hasNext()) {
            throw new IllegalArgumentException("Unsupported image format: " + fileExtension);
        }

        ImageWriter writer = writers.next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();

        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(file);
        writer.setOutput(imageOutputStream);

        writer.write(null, new IIOImage(image, null, null), writeParam);

        imageOutputStream.close();
        writer.dispose();
    }

}
