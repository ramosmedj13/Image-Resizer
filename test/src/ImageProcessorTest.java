import imageresizer.ImageProcessor;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ImageProcessorTest {
    private static final String TEST_IMAGE_PATH = "src/test/resources/images/sample.jpg";
    private static final String TEST_OUTPUT_PATH = "src/test/resources/images/output.jpg";

    @Before
    public void setUp() throws IOException {
        // Create output directory if it doesn't exist
        Path outputDir = Paths.get(TEST_OUTPUT_PATH).getParent();
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }
    }

    @Test
    public void testLoadImage() {
        ImageProcessor imageProcessor = new ImageProcessor();
        BufferedImage image = imageProcessor.loadImage(TEST_IMAGE_PATH);

        assertNotNull("Loaded image should not be null", image);
        assertEquals("Image width should be 800", 800, image.getWidth());
        assertEquals("Image height should be 600", 600, image.getHeight());
    }

    @Test
    public void testSaveImage() throws IOException {
        ImageProcessor imageProcessor = new ImageProcessor();
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);

        try {
            imageProcessor.saveImage(image, TEST_OUTPUT_PATH, "jpg");
            File outputFile = new File(TEST_OUTPUT_PATH);

            assertTrue("Output file should exist", outputFile.exists());
            assertTrue("Output should be a file", outputFile.isFile());

            // Validate the saved image dimensions
            BufferedImage savedImage = ImageIO.read(outputFile);
            assertNotNull("Saved image should not be null", savedImage);
            assertEquals("Saved image width should be 100", 100, savedImage.getWidth());
            assertEquals("Saved image height should be 100", 100, savedImage.getHeight());
        } catch (IOException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
