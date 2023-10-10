import imageresizer.ImageResizerApp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ImageResizerAppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testResizeAndSaveImage() {
        ImageResizerApp app = new ImageResizerApp();
        String inputImagePath = "src/test/resources/images/sample.jpg";
        int targetWidth = 400;
        int targetHeight = 300;
        String outputPath = "src/test/resources/images/output.jpg";
        String outputFormat = "JPG";

        try {
            // Create directories if they don't exist
            Path outputDir = Paths.get(outputPath).getParent();
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }

            app.resizeAndSaveImage(inputImagePath, targetWidth, targetHeight, outputPath, outputFormat);

            File outputFile = new File(outputPath);
            assertTrue(outputFile.exists());
            assertTrue(outputFile.isFile());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testAppWithInvalidInput() {
        String input = "invalid\n-100\n-100\ninvalid-path\nINVALID_FORMAT\n-1.5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ImageResizerApp app = new ImageResizerApp();

        app.start();

        String consoleErrorOutput = errContent.toString();
        assertTrue(consoleErrorOutput.contains("Invalid input:"));
    }

}
