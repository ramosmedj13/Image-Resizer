import imageresizer.ImageResizer;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.image.BufferedImage;

public class ImageResizerTest {

    @Test
    public void testResizeImageWithValidInput() {
        // Arrange
        ImageResizer imageResizer = new ImageResizer();
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        // Act
        BufferedImage resizedImage = imageResizer.resizeImage(image, 400, 300);

        // Assert
        assertNotNull(resizedImage);
        assertEquals(400, resizedImage.getWidth());
        assertEquals(300, resizedImage.getHeight());
    }

    @Test
    public void testResizeImageWithNullInput() {
        // Arrange
        ImageResizer imageResizer = new ImageResizer();

        // Act
        BufferedImage resizedImage = imageResizer.resizeImage(null, 400, 300);

        // Assert
        assertNull(resizedImage);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResizeImageWithInvalidTargetSize() {
        // Arrange
        ImageResizer imageResizer = new ImageResizer();
        BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        // Act and Assert
        imageResizer.resizeImage(image, -100, -100); // Expecting an IllegalArgumentException
    }
}

