package cs4810.transformations;

import cs4810.transformations.Shape;

/**
 * Class extending Shape which represents rectangular objects for use as a viewport.
 * Only rectangular objects may be set as a viewport.
 * Contains variables for each corner coordinates to more easily check if a pixel is inside.
 * @author carroll
 *
 */
public class Viewport extends Shape {

	public Line[] lines;
	public int numLines;
	
	//max and min possible values for each
	public int xMin = App.SCENE_WIDTH;
	public int xMax = 0;
	public int yMin = App.SCENE_HEIGHT;
	public int yMax = 0;

	
	/**
	 * Default constructor for viewport objects
	 * @param x0 Lowest x coordinate.
	 * @param x1 Highest x coordinate.
	 * @param y0 Lowest y coordinate.
	 * @param y1 Highest y coordinate.
	 */
	public Viewport(Line ...lines){
		super(lines);
		
		//find the min and max x/y values within the scene
		for(Line line : lines) {
			if(line.a.x < xMin) {
				xMin = line.a.x;
			}
			if(line.a.x > xMax) {
				xMax = line.a.x;
			}
			if(line.a.y < yMin) {
				yMin = line.a.y;
			}
			if(line.a.y > yMax) {
				yMax = line.a.y;
			}
		}//for
	}//constructor

	


}//Viewport

