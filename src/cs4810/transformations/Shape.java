package cs4810.transformations;

import java.awt.Color;

/**
 * Class to create a polygon using a list of {@code Point}s.
 * @author carroll
 *
 */
public class Shape {

	public Line[] lines;
	public int numLines;

	/**
	 * Default constructor for Shape objects.
	 * @param a List of points which compose the shape, in order which to be drawn.
	 */
	public Shape(Line ...lines) {
		this.lines = lines;
		numLines = lines.length;
	}//constructor


	/**
	 * Scan-converts the list of lines into an on-screen image, only displaying in viewport area.
	 */
	public void draw() {
		for(Line line : lines) {
			line.drawInViewport();
		}//for
	}//draw	

	/**
	 * Draws over shape with a color.  Useful for erasing.
	 * Notably, draws over even if not in viewport area.
	 * @param color The color to draw with.
	 */
	public void drawOver(Color color) {
		App.graphics.setColor(color);
		for(Line line : lines) {
			line.draw();
		}//for
		App.graphics.setColor(Color.GREEN);
	}//drawOver
	
	/** 
	 * Tester method which prints each line in the shape.
	 */
	public void printLines() {
		for(Line line : lines) {
			line.print();
		}
	}//printLines


	/**
	 * Transforms all points in a shape according to the input matrix.
	 * @param m The matrix by which to multiply each point.
	 * @return The transformed Shape object.
	 */
	private Shape transform (Matrix m) {	
		Line[] newLines = new Line[numLines];
		
		for(int i = 0; i < numLines; i++) {
			Point a = lines[i].a;
			Point b = lines[i].b;
			newLines[i] = new Line(a.multiply(m), b.multiply(m)); 
		}//for
		
		return new Shape(newLines);
	}//transform

	/**
	 * Translates shape by set amount.
	 * @param Tx Horizontal shift.
	 * @param Ty Vertical shift.
	 * @return The transformed Shape object.
	 */
	private Shape basicTranslate(double Tx, double Ty) {
		Matrix m = new Matrix();
		m.data[2][0] = Tx;
		m.data[2][1] = Ty;

		return transform(m);
	}//BasicTranslate

	/**
	 * Scales shape by set amount.
	 * @param Sx Horizontal scale factor.
	 * @param Sy Vertical scale factor.
	 * @return The transformed Shape object.
	 */
	private Shape basicScale(double Sx, double Sy) {
		Matrix m = new Matrix();
		m.data[0][0] = Sx;
		m.data[1][1] = Sy;

		return transform(m);
	}//Basic Scale

	/**
	 * Rotates shape by an angle in degrees (clockwise).
	 * @param angle Angle by which to rotate, relative to the origin.
	 * @return The transformed Shape object.	 
	 */
	private Shape basicRotate(double angle) {
		double degrees = -(angle * 3.1415) / 180;
		double sin = Math.sin(degrees);
		double cos = Math.cos(degrees);

		Matrix m = new Matrix();
		m.data[0][0] = cos;
		m.data[0][1] = -sin;
		m.data[1][0] = sin;
		m.data[1][1] = cos;

		return transform(m);
	}//basicRotate
	
	/**
	 * Translates a shape by x and y shift amounts.
	 * @param Tx The horizontal shift
	 * @param Ty The vertical shift.
	 * @return The translated shape
	 */
	public Shape translate(int Tx, int Ty) {
		return basicTranslate(Tx, Ty);
	}//translate
	
	/**
	 * Scales shape relative to a fixed point.
	 * @param Sx Horizontal scale factor
	 * @param Sy Vertical scale factor
	 * @param Cx X coordinate of pivot point
	 * @param Cy Y coordinate of pivot point
	 * @return The translated shape
	 */
	public Shape scale(double Sx, double Sy, int Cx, int Cy) {
		return basicTranslate(-Cx,-Cy).basicScale(Sx,Sy).basicTranslate(Cx,Cy);			
	}//scale

	/**
	 * Rotates shape around a fixed point.
	 * @param angle The angle (in degrees) to rotate clockwise.
	 * @param Cx X coordinate of pivot point
	 * @param Cy Y coordinate of pivot point
	 * @return The translated shape
	 */
	public Shape rotate(double angle, int Cx, int Cy) {
		return basicTranslate(-Cx,-Cy).basicRotate(angle).basicTranslate(Cx,Cy);		
	}//rotate
	

	
}//Shape
