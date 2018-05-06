/**
 * ImageProcessorTest.java
 * @author Mr.W
 */

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The ImageProcessorTest class
 * @author Mr.W
 *
 */
public class ImageProcessorTest {
	// The bitmap's path
	private static final String SOURCE_PATH_1 = "bmptest/1.bmp";
	private static final String SOURCE_PATH_2 = "bmptest/2.bmp";
	
	private static final String GOAL_RED_PATH_1 = "bmptest/goal/1_red_goal.bmp";
	private static final String GOAL_GREEN_PATH_1 = "bmptest/goal/1_green_goal.bmp";
	private static final String GOAL_BLUE_PATH_1 = "bmptest/goal/1_blue_goal.bmp";
	private static final String GOAL_GRAY_PATH_1 = "bmptest/goal/1_gray_goal.bmp";
	
	private static final String GOAL_RED_PATH_2 = "bmptest/goal/2_red_goal.bmp";
	private static final String GOAL_GREEN_PATH_2 = "bmptest/goal/2_green_goal.bmp";
	private static final String GOAL_BLUE_PATH_2 = "bmptest/goal/2_blue_goal.bmp";
	private static final String GOAL_GRAY_PATH_2 = "bmptest/goal/2_gray_goal.bmp";
	
	private static final String MY_GOAL_RED_PATH_1 = "bmptest/myGoal/1r";
	private static final String MY_GOAL_GREEN_PATH_1 = "bmptest/myGoal/1g";
	private static final String MY_GOAL_BLUE_PATH_1 = "bmptest/myGoal/1b";
	private static final String MY_GOAL_GRAY_PATH_1 = "bmptest/myGoal/1gr";
	
	private static final String MY_GOAL_RED_PATH_2 = "bmptest/myGoal/2r";
	private static final String MY_GOAL_GREEN_PATH_2 = "bmptest/myGoal/2g";
	private static final String MY_GOAL_BLUE_PATH_2 = "bmptest/myGoal/2b";
	private static final String MY_GOAL_GRAY_PATH_2 = "bmptest/myGoal/2gr";
	
	private static final int RED_PROCESS_TYPE = 0;
	private static final int GREEN_PROCESS_TYPE = 1;
	private static final int BLUE_PROCESS_TYPE = 2;
	private static final int GRAY_PROCESS_TYPE = 3;
	
	@Before
	public void setUp() throws Exception {
	}
	
	// picture1 test
	
	/**
	 * Test the picture1 with red processing
	 * @throws IOException
	 */
	@Test
	public void testShowChanelR1() throws IOException {
		new MyTest(RED_PROCESS_TYPE, SOURCE_PATH_1, MY_GOAL_RED_PATH_1, GOAL_RED_PATH_1);
	}

	/**
	 * Test the picture1 with green processing
	 * @throws IOException
	 */
	@Test
	public void testShowChanelG1() throws IOException {
		new MyTest(GREEN_PROCESS_TYPE, SOURCE_PATH_1, MY_GOAL_GREEN_PATH_1, GOAL_GREEN_PATH_1);
	}

	/**
	 * Test the picture1 with blue processing
	 * @throws IOException
	 */
	@Test
	public void testShowChanelB1() throws IOException {
		new MyTest(BLUE_PROCESS_TYPE, SOURCE_PATH_1, MY_GOAL_BLUE_PATH_1, GOAL_BLUE_PATH_1);
	}

	/**
	 * Test the picture1 with gray processing
	 * @throws IOException
	 */
	@Test
	public void testShowGray1() throws IOException {
		new MyTest(GRAY_PROCESS_TYPE, SOURCE_PATH_1, MY_GOAL_GRAY_PATH_1, GOAL_GRAY_PATH_1);
	}
		
	// picture2 test
	
	/**
	 * Test the picture2 with red processing
	 * @throws IOException
	 */
	@Test
	public void testShowChanelR2() throws IOException {
		new MyTest(RED_PROCESS_TYPE, SOURCE_PATH_2, MY_GOAL_RED_PATH_2, GOAL_RED_PATH_2);
	}

	/**
	 * Test the picture2 with green processing
	 * @throws IOException
	 */
	@Test
	public void testShowChanelG2() throws IOException {
		new MyTest(GREEN_PROCESS_TYPE, SOURCE_PATH_2, MY_GOAL_GREEN_PATH_2, GOAL_GREEN_PATH_2);
	}
	
	/**
	 * Test the picture2 with blue processing
	 * @throws IOException
	 */
	@Test
	public void testShowChanelB2() throws IOException {
		new MyTest(BLUE_PROCESS_TYPE, SOURCE_PATH_2, MY_GOAL_BLUE_PATH_2, GOAL_BLUE_PATH_2);
	}

	/**
	 * Test the picture2 with gray processing
	 * @throws IOException
	 */
	@Test
	public void testShowGray2() throws IOException {
		new MyTest(GRAY_PROCESS_TYPE, SOURCE_PATH_2, MY_GOAL_GRAY_PATH_2, GOAL_GRAY_PATH_2);
	}
}

/**
 * The MyTest class
 * @author Mr.W
 */
class MyTest {
	private static final int RED_PROCESS_TYPE = 0;
	private static final int GREEN_PROCESS_TYPE = 1;
	private static final int BLUE_PROCESS_TYPE = 2;
	private static final int GRAY_PROCESS_TYPE = 3;
	
	private BufferedImage myImg;
	private BufferedImage goalImg;
	private int myWidth;
	private int myHeight;
	private int goalWidth;
	private int goalHeight;
	
	/**
	 * The constructor of MyTest class
	 * First, process the image and store it into the myGoal folder.
	 * Then test my goal image with comparing with the standard goal image.
	 * Test the image's width, height and the rgb. 
	 * @param processType the process type (red, green, blue or gray) of the image.
	 * @param sourcePath the source image path that is going to be processed.
	 * @param myGoalPath my goal image path
	 * @param goalPath the standard goal image path
	 * @throws IOException
	 */
	public MyTest(int processType, String sourcePath, String myGoalPath, String goalPath) throws IOException {
		// read the image
		ImplementImageIO imageIO = new ImplementImageIO();
		ImplementImageProcessor imageProcessor = new ImplementImageProcessor();
		Image sourceImage = imageIO.myRead(sourcePath);
		Image processedImage = sourceImage;
		
		// process the image
		switch (processType) {
		case RED_PROCESS_TYPE:
			processedImage = imageProcessor.showChanelR(sourceImage);
			break;
		case GREEN_PROCESS_TYPE:
			processedImage = imageProcessor.showChanelG(sourceImage);
			break;
		case BLUE_PROCESS_TYPE:
			processedImage = imageProcessor.showChanelB(sourceImage);
			break;
		case GRAY_PROCESS_TYPE:
			processedImage = imageProcessor.showGray(sourceImage);
			break;
		default:
			break;
		}
		// write the processed image
		imageIO.myWrite(processedImage, myGoalPath);
		
		// read the image
		myImg = ImageIO.read(new File(myGoalPath + ".bmp"));
		goalImg = ImageIO.read(new File(goalPath));
		
		// get the image's width and height
		myWidth = myImg.getWidth();
		myHeight = myImg.getHeight();
		goalWidth = goalImg.getWidth();
		goalHeight = goalImg.getHeight();
		
		// test the image
		testWidth();
		testHeight();
		testRGB();
	}
	
	public void testWidth() {
		Assert.assertEquals(goalWidth, myWidth);
	}
	
	public void testHeight() {
		Assert.assertEquals(goalHeight, myHeight);
	}
	
	/**
	 * get the rgb data first
	 * and then compare them
	 */
	public void testRGB() {
		int[] myRGBData = new int[myWidth * myHeight];
		int[] goalRGBData = new int[goalWidth * goalHeight];
		myImg.getRGB(0, 0, myWidth, myHeight, myRGBData, 0, myWidth);
		goalImg.getRGB(0, 0, goalWidth, goalHeight, goalRGBData, 0, goalWidth);
		Assert.assertArrayEquals(goalRGBData, myRGBData);
	}
}
