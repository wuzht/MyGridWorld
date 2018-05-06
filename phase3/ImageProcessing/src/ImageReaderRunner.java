/**
 * ImagaReaderRunner.java
 * @author Mr.W
 * @version 1.0
 */
import imagereader.IImageIO;
import imagereader.IImageProcessor;
import imagereader.Runner;

/**
 * ImageReaderRunner
 * @author Mr.W
 */
public final class ImageReaderRunner {
	/**
	 * the private constructor
	 */
	private ImageReaderRunner() {
		
	}
	
	/**
	 * The main method
	 * @param args
	 */
    public static void main(String[] args) {
        IImageIO imageioer = new ImplementImageIO();
        IImageProcessor processor = new ImplementImageProcessor();
        Runner.run(imageioer, processor);
    }
}