package rl.cafesourire;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

//creates an image from a bit file
abstract public class ImageCreator {
	public static void createPictureFromByteFile(String pathBitFile,String imgName,int height, int width, int imageType){
		Path path = Paths.get(pathBitFile);
		try {
			//image comes in type BGR
			//BufferedImage img = new BufferedImage(height,width, BufferedImage.TYPE_INT_BGR);
			BufferedImage img = new BufferedImage(height,width, imageType);
			byte[] data = Files.readAllBytes(path);
			int i = 0;
			for (int y = 0; y < width; y++) {
				for (int x = 0; x < height; x++) {
					//signed bytes -> & 0xFF to unsign
					int blue = (data[i] & 0xFF);
				    int green = (data[i + 1] & 0xFF);
				    int red = (data[i + 2] & 0xFF);
				    int rgb = (red << 16) | (green << 8) | blue; 
				    	
				    i=i+3; 
				    img.setRGB(x, y, rgb);
				}
			}
			File outputFile = new File(imgName + ".jpg");
			System.out.println("Trying to save image '" + imgName + ".jpg'");
			ImageIO.write(img, "jpg", outputFile);
			System.out.println("Image '" + imgName + ".jpg' saved");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
