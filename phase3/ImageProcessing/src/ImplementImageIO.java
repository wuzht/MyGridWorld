/**
 * ImplementImageIO.java
 * @author Mr.W
 * @version 1.0
 */
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.awt.image.MemoryImageSource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.io.File;

import imagereader.IImageIO;

public class ImplementImageIO implements IImageIO {
	/**
	 * This method override the myRead method
	 * @param filePath The file path
	 * @return image The opened image
	 * @exception IOException
	 */
	@Override
	public Image myRead(String filePath) throws IOException {
		// create the file input stream with the filePath
		FileInputStream fileInputStream = new FileInputStream(filePath);
		// create the bufferedInputStream with the fileInputStream
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		
		// 14 bytes for the bitmap file header
		byte[] bitMapFileHeader = new byte[14];
		bufferedInputStream.read(bitMapFileHeader, 0, 14);
		
		// get the size of a bitmap file, in bytes
		int bfSize = readBytes(bitMapFileHeader, 2, 4);
		
		// 40 bytes for the bitmap info header
		byte[] bitMapInfoHeader = new byte[40];
		bufferedInputStream.read(bitMapInfoHeader, 0, 40);
		
		// get the biWidth and the biHeight
		int biWidth = readBytes(bitMapInfoHeader, 4, 4);
		int biHeight = readBytes(bitMapInfoHeader, 8, 4);
		
		// get the biBitCount(the number of bits per pixel
		int biBitCount = readBytes(bitMapInfoHeader, 14, 2);
		
		// the bitMapSize, in pixels
		int bitMapSize = biWidth * biHeight;
		// pixels: the rgb value of each pixels
		int[] pixels = new int[bitMapSize];
		
		if (biBitCount == 24) {	
			// read the rgbData

			// the formula of widthBytes
			int widthBytes = (biWidth * biBitCount + 31) / 32 * 4;
			byte[] rgbData = new byte[widthBytes * biHeight];
			bufferedInputStream.read(rgbData, 0, widthBytes * biHeight);
			
			int offset = 0;
			for (int i = biHeight - 1; i >= 0; i--) {
				for (int j = 0; j < biWidth; j++) {
					byte[] tempByte = {rgbData[i * widthBytes + j * 3],
							rgbData[i * widthBytes + j * 3 + 1],
							rgbData[i * widthBytes + j * 3 + 2],
							(byte)0xFF};
					pixels[offset] = readBytes(tempByte, 0, 4);
					offset++;
				}
			}
		}
		else {
			bufferedInputStream.close();
			throw new IOException("biBitCount must be 24!");
		}
		bufferedInputStream.close();
		ImageProducer producer = new MemoryImageSource(biWidth, biHeight, pixels, 0, biWidth);
		return Toolkit.getDefaultToolkit().createImage(producer);
	}
	
	/**
	 * This method turn the specific segment of bytes into integer
	 * @param buffer the bytes to read
	 * @param startOffset the start offset of the bytes
	 * @param length the length of bytes to read
	 * @return the integer value of this segment of bytes
	 */
	private int readBytes(byte[] buffer, int startOffset, int length) {
		// notice that the data is stored in Little Endian
		int ret = 0;
		for (int i = 0; i < length; i++) {
			ret += (buffer[startOffset + i] & 0xFF) << i * 8;
		}
		return ret;
	}
	
	/**
	 * This method override the myWrite method
	 * @param image The image which is going to write
	 * @param filePath The file path
	 * @return image The written image
	 * @exception IOException
	 */
	@Override
	public Image myWrite(Image image, String filePath) throws IOException {
		try {
			// create buffered image (turn Image into BufferedImage)
			BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_BGR);
			
			// draw image
			Graphics2D bGr = bufferedImage.createGraphics();
			bGr.drawImage(image, 0, 0, null);
			bGr.dispose();

			ImageIO.write(bufferedImage, "bmp", new File(filePath + ".bmp"));
		}
		catch (Exception e) {
			return null;
		}
		return image;
	}
}
