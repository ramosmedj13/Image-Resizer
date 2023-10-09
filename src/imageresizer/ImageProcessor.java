package imageresizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    public BufferedImage loadImage(String imagePath) throws IOException {
        File file = new File(imagePath);
        return ImageIO.read(file);
    }

    public void saveImage(BufferedImage image, String outputPath, String format) throws IOException {
        File file = new File(outputPath);
        ImageIO.write(image, format, file);
    }
}
