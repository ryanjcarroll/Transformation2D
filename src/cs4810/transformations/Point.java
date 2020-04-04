package cs4810.transformations;

/**
 * Class to create a point with x and y coordinate values. 
 * @author carroll
 *
 */
public class Point {

	public int x;
	public int y;
	
	/**
	 * Default constructor for the Point class.
	 * @param x The x coordinate of the point.
	 * @param y The y coordinate of the point.
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}//constructor
	
	/**
	 * Prints point coordinates.
	 */
	public void print() {
		System.out.print(x + "," + y);
	}//print
	
	/**
	 * Returns point info as a String
	 */
	public String toString() {
		return Integer.toString(x) + ", " + Integer.toString(y);
	}//toString
	
	/**
	 * Adjust the point according to the result of a point/matrix multiplication.
	 * @param m The matrix by which to multiply.
	 */
	public Point multiply(Matrix m) {
		int newX = (int)((m.data[0][0] * x) + (m.data[1][0] * y) + m.data[2][0]);
		int newY = (int)((m.data[0][1] * x) + (m.data[1][1] * y) + m.data[2][1]);
		return new Point(newX,newY);
	}//multiply
	
}//point
