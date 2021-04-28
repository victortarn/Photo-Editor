package a2;
import java.awt.Color;
import java.io.File;
import java.net.URI;
import java.net.URL;

public class A2 {
	/**
	 * The original image
	 */
	private static Picture orig;
	
	/**
	 * The image viewer class
	 */
	private static A2Viewer viewer;
	
	/**
	 * Returns a 300x200 image containing the Queen's flag (without the crown).
	 * 
	 * @return an image containing the Queen's flag
	 */
	public static Picture flag() {
		Picture img = new Picture(300, 200);
		int w = img.width();
		int h = img.height();

		// set the pixels in the blue stripe
		Color blue = new Color(0, 48, 95);
		for (int col = 0; col < w / 3; col++) {
		    for (int row = 0; row < h - 1; row++) {
		        img.set(col, row, blue);
		    }
		}

		// set the pixels in the yellow stripe
		Color yellow = new Color(255, 189, 17);
		for (int col = w / 3; col < 2 * w / 3; col++) {
		    for (int row = 0; row < h - 1; row++) {
		        img.set(col, row, yellow);
		    }
		}

		// set the pixels in the red stripe
		Color red = new Color(185, 17, 55);
		for (int col = 2 * w / 3; col < w; col++) {
		    for (int row = 0; row < h - 1; row++) {
		        img.set(col, row, red);
		    }
		}
		return img;
	}

	public static Picture copy(Picture p) {
		Picture result = new Picture(p.width(), p.height());
		// complete the method
		int w = result.width();
		int h = result.height();
		
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
		    	Color  clr = p.get(col, row); 
		        result.set(col, row, clr);
		    }
		}
		
		return result;
		
	}
	
	// ADD YOUR METHODS HERE
	public static Picture border(Picture p, int Border) {
		int w = p.width();
		int h = p.height();
		
		for (int col = 0; col < w; col++) {
			for (int row = 0; row < Border; row++) {
				p.set(col, row, Color.BLUE);
				p.set(col, h - 1 - row, Color.BLUE);
			}
		}
		
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < Border; col++) {
				p.set(col, row, Color.BLUE);
				p.set(w - 1 - col, row, Color.BLUE);
			}
		}
		return p;
	}
	
	public static Picture grayscale(Picture p) {
		Picture result = new Picture(p.width(), p.height());
		
		int w = result.width();
		int h = result.height();
		
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				
		    	Color clr = p.get(col, row); 
		    	double red = clr.getRed();
		    	double green = clr.getGreen();
		    	double blue = clr.getBlue();
		    	
		    	int y = (int) Math.round(red * 0.2989 + green * 0.587 + blue * 0.114);
		    	
		    	clr = new Color(y, y, y);
		    	
		        result.set(col, row, clr);
		    }
		}
		return result;
	}
	
	public static Picture binary(Picture p) {
		Picture result = new Picture(p.width(), p.height());
		// complete the method
		int w = result.width();
		int h = result.height();
		
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
		    	Color  clr = p.get(col, row);
		    	if (clr.getRed() >= 128) {
		    		result.set(col, row, Color.WHITE);
		    	} 
		    	else {
		    		result.set(col, row, Color.BLACK);
		    	}
		    }
		}
		return result;
	}
	
	public static Picture flip(Picture p) {
		Picture result = new Picture(p.width(), p.height());
		
		int w = result.width();
		int h = result.height();
		
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {		
		    	Color clr = p.get(col, row);  
				result.set(col, h - 1 - row, clr);
			}
		}
		return result;
	}
	
	public static Picture rotate(Picture p) {
		Picture result = new Picture(p.height(), p.width());
		
		int w = result.width();
		int h = result.height();
		
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {		
		    	Color clr = p.get(row, col);  
				result.set(col, h - 1 - row, clr);
			}
		}
		return result;
	}
	
	public static Picture redEye(Picture p) {
		int w = p.width();
		int h = p.height();
		
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
		    	Color  clr = p.get(col, row);
		    	
		    	double red = clr.getRed();
		    	double green = clr.getGreen();
		    	double blue = clr.getBlue();
		  
		    	double redIntensity = red / (green + blue);
		    	
		    	if (redIntensity > 1.25) {
		    		p.set(col, row, Color.BLACK);
		    	} 
		    	else {
		    		p.set(col, row, clr);
		    	}
			}
		}
		return p;
	}
	
	
	
	public static Picture blur(Picture p, int radius) {
		Picture result = new Picture(p.width(), p.height());
		// complete the method 
		int w = result.width();
		int h = result.height();
		
		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				int colStart = col - radius < 0 ? 0 : col - radius;
				int colEnd = col + radius > w - 1 ? w - 1 : col + radius;
				int rowStart = row - radius < 0 ? 0 : row - radius;
				int rowEnd = row + radius > h - 1 ? h - 1 : row + radius;
				
				//k counts the number of pixels being blurred
				int k = 0;
				double redSum = 0;
				double greenSum = 0;		
				double blueSum = 0;
				
				for (int i = rowStart; i <= rowEnd; i++) {
					for (int j = colStart; j <= colEnd; j++) {
						Color clr = p.get(j, i);
						
				    	redSum += (double) clr.getRed();
						greenSum += (double) clr.getGreen();
						blueSum += (double) clr.getBlue();
						k++;
					}	
				}
				int redAvg = (int) Math.round(redSum / k);
				int greenAvg = (int) Math.round(greenSum / k);
				int blueAvg = (int) Math.round(blueSum / k);
				Color clr = new Color(redAvg, greenAvg, blueAvg);
				
		        result.set(col, row, clr);
		    }
		}
		
		return result;
	}
	
	
	/**
	 * A2Viewer class calls this method when a menu item is selected.
	 * This method computes a new image and then asks the viewer to
	 * display the computed image.
	 * 
	 * @param op the operation selected in the viewer
	 */
	public static void processImage(String op) {
		
		switch (op) {
		case A2Viewer.FLAG:
			// create a new image by copying the original image
			Picture p = A2.flag();
			A2.viewer.setComputed(p);
			break;
		case A2Viewer.COPY:
			// create a new image by copying the original image
			p = A2.copy(A2.orig);
			A2.viewer.setComputed(p);
			break;
		case A2Viewer.BORDER_1:
			// create a new image by adding a border of width 1 to the original image
			p = A2.copy(A2.orig);
			p = A2.border(p, 1);
			A2.viewer.setComputed(p);
			break;
		case A2Viewer.BORDER_5:
			// create a new image by adding a border of width 5 the original image
			p = A2.copy(A2.orig);
			p = A2.border(p, 5);
			A2.viewer.setComputed(p);
			
			break;
		case A2Viewer.BORDER_10:
			// create a new image by adding a border of width 10  the original image
			p = A2.copy(A2.orig);
			p = A2.border(p, 10);
			A2.viewer.setComputed(p);
			
			break;
		case A2Viewer.TO_GRAY:
			// create a new image by converting the original image to grayscale
			p = A2.copy(A2.orig);
			p = A2.grayscale(p);
			A2.viewer.setComputed(p);
			
			break;
		case A2Viewer.TO_BINARY:
			// create a new image by converting the original image to black and white
			p = A2.copy(A2.orig);
			p = A2.grayscale(p);
			p = A2.binary(p);
			A2.viewer.setComputed(p);
			
			break;
		case A2Viewer.FLIP_VERTICAL:
			// create a new image by flipping the original image vertically
			p = A2.copy(A2.orig);
			p = A2.flip(p);
			A2.viewer.setComputed(p);
			
			break;
		case A2Viewer.ROTATE_RIGHT:
			// create a new image by rotating the original image to the right by 90 degrees
			p = A2.copy(A2.orig);
			p = A2.rotate(p);
			A2.viewer.setComputed(p);
			
			break;
		case A2Viewer.RED_EYE:
			// create a new image by removing the redeye effect in the original image
			p = A2.copy(A2.orig);
			p = A2.redEye(p);
			A2.viewer.setComputed(p);
			
			break;
		case A2Viewer.BLUR_1:
			// create a new image by blurring the original image with a box blur of radius 1
			p = A2.copy(A2.orig);
			p = A2.blur(p, 1);
			A2.viewer.setComputed(p);
			
			break;
		case A2Viewer.BLUR_3:
			// create a new image by blurring the original image with a box blur of radius 3
			p = A2.copy(A2.orig);
			p = A2.blur(p, 3);
			A2.viewer.setComputed(p);
			
			break;
		case A2Viewer.BLUR_5:
			// create a new image by blurring the original image with a box blur of radius 5
			p = A2.copy(A2.orig);
			p = A2.blur(p, 5);
			A2.viewer.setComputed(p);
			
			break;
		default:
			// do nothing
		}
	}
	
	/**
	 * Starting point of the program. Students can comment/uncomment which image
	 * to use when testing their program.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		A2.viewer = new A2Viewer();
		A2.viewer.setVisible(true);
		
		
		URL img;
		// uncomment one of the next two lines to choose which test image to use (person or cat)
		img = A2.class.getResource("redeye-400x300.jpg");   
		//img = A2.class.getResource("cat.jpg");
		
		try {
			URI uri = new URI(img.toString());
			A2.orig = new Picture(new File(uri.getPath()));
			A2.viewer.setOriginal(A2.orig);
		}
		catch (Exception x) {
			// do nothing
		}
	}

}
