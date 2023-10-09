package imageresizer;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The ImageProcessor class handles loading and saving images.
 * It provides methods for loading an image from a file and saving an image to a file.
 */
public class ImageProcessor {
    private static final Logger logger = Logger.getLogger(ImageProcessor.class.getName());

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
                throw new IOException("File not found: " + imagePath);
            }

            String fileExtension = getFileExtension(imagePath);

            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(fileExtension);

            if (!readers.hasNext()) {
                throw new IllegalArgumentException("Unsupported image format: " + fileExtension);
            }

            ImageReader reader = readers.next();
            reader.setInput(ImageIO.createImageInputStream(file));

            return reader.read(0);
        } catch (IOException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error loading image: " + e.getMessage(), e);
            return null;
        }
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
            throw new IllegalArgumentException("Invalid file path: " + filePath);
        }
        return filePath.substring(dotIndex + 1);
    }

    /**
     * Saves an image to the specified file path with the given output format and compression quality.
     *
     * @param image       The BufferedImage to save.
     * @param outputPath  The path where the image will be saved.
     * @param outputFormat The output image format (e.g., "jpg", "png").
     * @param quality     The compression quality (0.0 - 1.0).
     * @throws IOException If an error occurs while saving the image.
     */
    public void saveImage(BufferedImage image, String outputPath, String outputFormat, float quality) throws IOException {
        File file = new File(outputPath);
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(outputFormat);

        if (!writers.hasNext()) {
            throw new IllegalArgumentException("Unsupported image format: " + outputFormat);
        }

        ImageWriter writer = writers.next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParam.setCompressionQuality(quality);

        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(file);
        writer.setOutput(imageOutputStream);

        writer.write(null, new IIOImage(image, null, null), writeParam);

        imageOutputStream.close();
        writer.dispose();
    }
}
