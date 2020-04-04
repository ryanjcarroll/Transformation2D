package cs4810.transformations;

import javafx.embed.swing.SwingFXUtils;

/**
 * Class to create line objects.
 * Scan-converts the line into a visible on-screen image using Bresenham's algorithm.
 * @author ryan7
 *
 */
public class Line {

	public Point a;
	public Point b;

	/**
	 * Default constructor for line objects.
	 * @param a The first point in the line.
	 * @param b The second point in the line.
	 */
	public Line(Point a, Point b) {
		this.a = a;
		this.b = b;
	}//constructor


	/**
	 * Prints info about line endpoints.
	 */
	public void print() {
		a.print();
		System.out.print(" to ");
		b.print();
		System.out.println();
	}//print

	/**
	 * Returns info about line endpoints as a string.
	 */
	public String toString() {
		return a.toString() + ", " + b.toString();
	}//toString

	/**
	 * Activates pixels using a line drawing algorithm and scan-converts line onto screen.
	 */
	public void draw(){
		int x0 = a.x;
		int y0 = a.y;
		int x1 = b.x;
		int y1 = b.y;

		drawBasicLine(x0,y0,x1,y1, false);
	}//draw

	/**
	 * Activates pixels using Bresenham's algorithm and scan-converts line onto screen.
	 * Only activates pixels which are within the current viewport.
	 */
	public void drawInViewport() {
		int x0 = a.x;
		int y0 = a.y;
		int x1 = b.x;
		int y1 = b.y;

		drawBasicLine(x0,y0,x1,y1, true);
	}//drawInViewport
	

	/**
	 * Activates the pixel at the specified coordinates.
	 * @param x The x coordinate of the pixel to activate.
	 * @param y The y coordinate of the pixel to activate.
	 * @param type The type of line generating the pixels - triggers a specific graphics entity.
	 */
	public void activatePixel(int x, int y) {
		App.graphics.drawLine(x, y, x, y);
	}//activatePixel

	/**
	 * Activates the pixel at the specified coordinates only if it lies within the viewport.
	 * @param x The x coordinate of the pixel to activate.
	 * @param y The y coordinate of the pixel to activate.
	 */
	public void activatePixelInViewport(int x, int y) {
		if((App.viewport.xMin <= x)
			&&(x <= App.viewport.xMax)
			&&(App.viewport.yMin <= y)
			&&(y <= App.viewport.yMax)) 
		{
			App.graphics.drawLine(x, y, x, y);	
		}//if
	}//activatePixel


	/**
	 * Draws a line between the given coordinates using the basic line-drawing algorithm.
	 * @param x0 The first x coordinate.
	 * @param y0 The first y coordinate.
	 * @param x1 The second x coordinate.
	 * @param y1 The second y coordiante.
	 * @param viewportOnly boolean true if should only draw pixels within the viewport
	 */
	public void drawBasicLine(int x0, int y0, int x1, int y1, boolean viewportOnly) {
		int dx = Math.abs(x1-x0);
		int dy = Math.abs(y1-y0);
		int x, y;
		double m;

		//always draw left to right
		if(x0 > x1) {
			int tempX = x1;
			int tempY = y1;
			x1 = x0;
			x0 = tempX;
			y1 = y0;
			y0 = tempY;
		}

		//set slope value based on orientation
		if(dx < dy) {
			m = (double)dx/(double)dy;
		} else {
			m = (double)dy/(double)dx;
		}

		//main pixel activation loop
		if(dx < dy) {
			//vertical orientation loop
			for(int i = 0; i <= dy; i++) {
				if(y0 < y1) { //if drawn top to bottom
					y = y0 + i;
				} else { //if drawn bottom to top
					y = y0 - i;
				}
				x = (int)(x0 + (m * i));
				//activate appropriate pixels
				if(viewportOnly) {
					activatePixelInViewport(x,y);
				} else {
					activatePixel(x,y);
				}
			}
		} else {
			//horizontal orientation loop (and neither)
			for(int i = 0; i <= dx; i++) {
				x = x0 + i;
				if(y0 < y1) { //if drawn top to bottom
					y = (int)(y0 + (m * i));
				} else { //if drawn bottom to top
					y = (int)(y0 - (m * i));
				}
				//activate appropriate pixels
				if(viewportOnly) {
					activatePixelInViewport(x,y);
				} else {
					activatePixel(x,y);
				}
			}
		}
		App.frame.setImage(SwingFXUtils.toFXImage(App.buffered, null));
	}//drawBasicLine


}//Line
