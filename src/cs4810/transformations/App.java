package cs4810.transformations;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;

public class App extends Application {

	private Scene scene;
	public static int SCENE_WIDTH = 800;
	public static int SCENE_HEIGHT = 800;
	
	public static Graphics graphics;
	public static BufferedImage buffered;
	public static ImageView frame;
	public static VBox root;
	
	public static Shape shape;
	public static Shape lastShape;
	public static Viewport viewport;
	
	/**
	 * Contains all non-setup method calls for program to execute.
	 */
	public void execute() {
		Point a = new Point(0,0);
		Point b = new Point(300,300);
		Point c = new Point(0,300);
		Point d = new Point(300,0);
		
		Line l1 = new Line(a,b);
		Line l2 = new Line(c,d);
		
		shape = new Shape(l1, l2);
		shape.draw();
	}//execute
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage stage){
		//initialize stage and scene
		root = new VBox();
		scene = new Scene(root);

		//stage settings
		stage.setScene(scene);
		stage.setWidth(SCENE_WIDTH+1);
		stage.setHeight(SCENE_HEIGHT+1);
		stage.setTitle("Line App!");
		stage.show();
		
		TransformBar transformBar = new TransformBar();
		root.getChildren().add(transformBar);		
		
		//initialize a BufferedImage to serve as the canvas
		buffered = new BufferedImage(SCENE_WIDTH+1, SCENE_HEIGHT+1, BufferedImage.TYPE_INT_RGB);
		frame = new ImageView(SwingFXUtils.toFXImage(buffered, null));
		root.getChildren().add(frame);
		stage.sizeToScene();

		//line graphics settings
		graphics = buffered.getGraphics();
		
		//create initial viewport equal to scene dimensions
		graphics.setColor(Color.RED);		
		Line top = new Line(new Point(0,0), new Point(SCENE_WIDTH,0));
		Line bot = new Line(new Point(0,SCENE_HEIGHT), new Point(SCENE_WIDTH,SCENE_HEIGHT));
		Line right = new Line(new Point(SCENE_WIDTH, 0), new Point(SCENE_WIDTH, SCENE_HEIGHT));
		Line left = new Line(new Point(0,0), new Point(0,SCENE_HEIGHT));
		viewport = new Viewport(top,bot,right,left);
		viewport.draw();
		graphics.setColor(Color.GREEN);
		
		execute();
	}//start

	
	/**
	 * Sets the new active shape to the input shape, and colors the previous shape gray,
	 * while erasing the shape previous to that.
	 * @param newShape The new shape to set.
	 */
	public static void setShape(Shape newShape) {
		if(lastShape != null) {
			lastShape.drawOver(Color.BLACK);
		}
		lastShape = App.shape;
		lastShape.drawOver(Color.GRAY);
		shape = newShape;
		shape.draw();
		setViewport(viewport.xMin, viewport.xMax, viewport.yMin, viewport.yMax);
	}//setShape
	
	/**
	 * Sets the viewport to a new boundary.
	 * @param x0 Lowest x coordinate.
	 * @param x1 Highest x coordinate.
	 * @param y0 Lowest y coordinate.
	 * @param y1 Highest y coordinate.
	 */
	public static void setViewport(int x0, int x1, int y0, int y1) {
		//calculate new shape to set viewport as
		Point a = new Point(x0,y0);
		Point b = new Point(x0,y1);
		Point c = new Point(x1,y1);
		Point d = new Point(x1,y0);
		Viewport newViewport = new Viewport(new Line(a,b), new Line(b,c), new Line(c,d), new Line(d,a));
		
		//erase old viewport and draw new
		viewport.drawOver(Color.BLACK);
		viewport = newViewport;
		graphics.setColor(Color.RED);
		viewport.draw();
		graphics.setColor(Color.GREEN);
	}//setViewport
	
}//App
