/**
 * ImplementImageProcessor.java
 * @author Mr.W
 * @version 1.0
 */
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

import java.awt.Toolkit;

import imagereader.IImageProcessor;

public class ImplementImageProcessor implements IImageProcessor{
	/**
	 * This method override the showChanelR method
	 * @param sourceImage The source image
	 * @return image The processed image
	 */
	@Override
	public Image showChanelR(Image sourceImage) {
		MyImageFilter myImageFilter = new MyImageFilter(0xFFFF0000);
		ImageProducer producer = new FilteredImageSource(sourceImage.getSource(), myImageFilter);
		return Toolkit.getDefaultToolkit().createImage(producer);
	}
	
	/**
	 * This method override the showChanelG method
	 * @param sourceImage The source image
	 * @return image The processed image
	 */
	@Override
	public Image showChanelG(java.awt.Image sourceImage) {
		MyImageFilter myImageFilter = new MyImageFilter(0xFF00FF00);
		ImageProducer producer = new FilteredImageSource(sourceImage.getSource(), myImageFilter);
		return Toolkit.getDefaultToolkit().createImage(producer);
	}
	  
	/**
	 * This method override the showChanelB method
	 * @param sourceImage The source image
	 * @return image The processed image
	 */
	@Override
	public Image showChanelB(java.awt.Image sourceImage) {
		MyImageFilter myImageFilter = new MyImageFilter(0xFF0000FF);
		ImageProducer producer = new FilteredImageSource(sourceImage.getSource(), myImageFilter);
		return Toolkit.getDefaultToolkit().createImage(producer);
	}
  
	/**
	 * This method override the showGray method
	 * @param sourceImage The source image
	 * @return image The processed image
	 */
	@Override
	public Image showGray(java.awt.Image sourceImage) {
		MyImageFilter myImageFilter = new MyImageFilter(0);
		ImageProducer producer = new FilteredImageSource(sourceImage.getSource(), myImageFilter);
		return Toolkit.getDefaultToolkit().createImage(producer);
	}
}

/**
 * MyImageFilter
 * @author Mr.W
 *
 */
class MyImageFilter extends RGBImageFilter {
	/**
	 * 0xFFFF0000 for red channel; 
	 * 0xFF00FF00 for green channel; 
	 * 0xFF0000FF for blue channel; 
	 * 0 for grayscale.
	 */
	private int mask;
	
	/**
	 * Constructor with mask
	 * @param mask the color mask of the filter. 
	 * If mask == 0, this is a grayscale filter.
	 */
	public MyImageFilter(int mask) {
		this.mask = mask;
		// The filter's operation does not depend on the
		// pixel's location, so IndexColorModels can be
		// filtered directly.
		canFilterIndexColorModel = true;
	}
	
	/**
	 * (Super class: Subclasses must specify a method to convert a single 
	 * input pixel in the default RGB ColorModel to a single output pixel.)
	 * Override the filter method.
	 * @param x the X coordinate of the pixel
	 * @param y the Y coordinate of the pixel
	 * @param rgb the integer pixel representation in the default RGB color model
	 * @return a filtered pixel in the default RGB color model.
	 */
	@Override
	public int filterRGB(int x, int y, int rgb) {
		// rgb channel filter
		if (mask != 0) {
			return rgb & mask;
		}
		// grayscale filter
		else {
			// get the values of r, g, b
			int r = (0x00FF0000 & rgb) >> 16;
			int g = (0x0000FF00 & rgb) >> 8;
			int b = 0x000000FF & rgb;
			// use the formula suggested by NTSC: I = 0.299 * r + 0.587 * g + 0.114 * b
			int grayValue = (int) (0.299 * r + 0.587 * g + 0.114 * b);
			int filteredPixel = rgb & 0xFF000000;
			filteredPixel |= (grayValue << 16) | (grayValue << 8) | grayValue;
			return filteredPixel;
		}
	}
}