package imageresizer;

import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

public class ImageResizer {
    public BufferedImage resizeImage(BufferedImage image, int targetWidth, int targetHeight) {
        return Scalr.resize(image, Scalr.Method.QUALITY, targetWidth, targetHeight);
    }
}
