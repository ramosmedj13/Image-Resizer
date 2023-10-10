package imageresizer;

import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

/**
 * The ImageResizer class provides methods for resizing images using the ImgScalr library.
 */
public class ImageResizer {

    /**
     * Resizes the given image to the specified target width and height while preserving quality.
     *
     * @param image       The input image to resize.
     * @param targetWidth The desired width of the resized image.
     * @param targetHeight The desired height of the resized image.
     * @return The resized image.
     */
    public BufferedImage resizeImage(BufferedImage image, int targetWidth, int targetHeight) {
        if (image == null) {
            return null;
        }

        return Scalr.resize(image, Scalr.Method.QUALITY, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }
}
