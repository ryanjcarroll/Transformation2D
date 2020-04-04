package cs4810.transformations;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * Contains buttons and controls to carry out transformations on a shape during runtime.
 * @author carroll
 */
public class TransformBar extends VBox{

	public static final String readFile = "ReadFile.txt";
	public static final String writeFile = "WriteFile.txt";

	/**
	 * Constructor, creates a TransformBar along with its menus and buttons.
	 */
	public TransformBar() {
		//Drop-down menu and Select button
		Button selectButton = new Button("Transform!");
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll("Translate","Scale","Rotate");
		VBox leftItems = new VBox(choiceBox, selectButton);

		//Spacer and viewport settings
		Pane spacerLeft = new Pane();
		spacerLeft.setMinSize((App.SCENE_WIDTH/2) - 140, 1);
		Button viewportButton = new Button("Adjust Viewport");
		viewportButton.setOnAction(viewportHandler);
		VBox middleItems = new VBox(viewportButton);

		//Spacer and read/write to file buttons
		Pane spacerRight = new Pane();
		spacerRight.setMinSize((App.SCENE_WIDTH/2) - 141, 1);
		Button readButton = new Button ("Read from File");
		readButton.setOnAction(readHandler);
		Button writeButton = new Button ("Write to File");
		writeButton.setOnAction(writeHandler);
		VBox rightItems = new VBox(readButton, writeButton);

		//Add all items to scene
		HBox buttonBar = new HBox(leftItems, spacerLeft, middleItems, spacerRight, rightItems);
		getChildren().addAll(buttonBar);

		//Handler for Translate! button, performs action selected by drop-down menu
		EventHandler<ActionEvent> buttonHandler = event ->
		{
			System.out.println(choiceBox.getValue());

			if (choiceBox.getValue() == "Translate") {
				displayTranslateOptions();
			} else if (choiceBox.getValue() == "Scale") {
				displayScaleOptions();
			} else if(choiceBox.getValue() == "Rotate") {
				displayRotateOptions();
			}
		};
		selectButton.setOnAction(buttonHandler);
	}//constructor

	/**
	 * Handler for button which displays viewport options and ability to adjust viewport.
	 */
	private EventHandler<ActionEvent> viewportHandler = event ->
	{
		//Stage and background field
		Stage vpStage = new Stage();
		VBox field = new VBox(10);

		//Range of x values for viewport
		Text xInfo = new Text(" Enter range of x values:");
		Text xText = new Text(" x: ");
		Text xToText = new Text(" to ");
		TextField x0Field = new TextField("0");
		TextField x1Field = new TextField(Integer.toString(App.SCENE_WIDTH));
		HBox xBox = new HBox(xText, x0Field, xToText, x1Field);

		//Range of y values for viewport
		Text yInfo = new Text(" Enter range of y values:");
		Text yText = new Text(" y: ");
		Text yToText = new Text(" to ");
		TextField y0Field = new TextField("0");
		TextField y1Field = new TextField(Integer.toString(App.SCENE_HEIGHT));
		HBox yBox = new HBox(yText, y0Field, yToText, y1Field);

		//Enter Button
		Button enterButton = new Button("Enter");
		enterButton.setAlignment(Pos.BASELINE_CENTER);
		EventHandler<ActionEvent> enterHandler = e ->
		{
			int x0 = Integer.parseInt(x0Field.getText());
			int x1 = Integer.parseInt(x1Field.getText());
			int y0 = Integer.parseInt(y0Field.getText());
			int y1 = Integer.parseInt(y1Field.getText());
			App.setViewport(x0,x1,y0,y1);
			App.setShape(App.shape.translate(0,0)); //re-draws the exact same shape
			vpStage.close();
		};
		enterButton.setOnAction(enterHandler);

		//Add elements to scene
		field.getChildren().addAll(xInfo, xBox, yInfo, yBox, enterButton);			
		Scene vpScene = new Scene(field,200,190);
		vpStage.setScene(vpScene);
		vpStage.show();
	};//viewportHandler

	/**
	 * Handler for button which writes currently displayed lines to a file.
	 */
	private EventHandler<ActionEvent> writeHandler = event -> 
	{
		try {
			PrintWriter writer = new PrintWriter(writeFile);		
			for(Line line : App.shape.lines) {
				writer.println(line.toString());
			}
			writer.close();
			System.out.println("Done writing");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	};//writeHandler

	/**
	 * Handler for button which reads inpuit and sets shape based on info in ReadFile.txt
	 */
	private EventHandler<ActionEvent> readHandler = event ->
	{
		System.out.println("Reading...");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(readFile));

			//count number of lines
			int numLines = 0;
			String currentLine = reader.readLine();
			Line[] inputLines = new Line[0];
			Line[] previousLines;

			try {
				while (currentLine != null) {
					numLines++;
					//retain all previous lines
					previousLines = inputLines;
					inputLines = new Line[numLines];
					for(int i = 0; i < numLines-1; i++) {
						inputLines[i] = previousLines[i];
					}
					//create a new line and add to the array
					String[] lineInfo = currentLine.trim().split("\\s*,\\s*");
					int x0 = Integer.valueOf(lineInfo[0]);
					int y0 = Integer.valueOf(lineInfo[1]);
					int x1 = Integer.valueOf(lineInfo[2]);
					int y1 = Integer.valueOf(lineInfo[3]);
					System.out.println(x0 + ", " + y0 + ", " + x1 + ", " + y1);
					Point a = new Point(x0,y0);
					Point b = new Point(x1,y1);
					Line newLine = new Line(a,b);
					inputLines[numLines-1] = newLine;

					currentLine = reader.readLine(); //advance to next line
				}//while

				//create a shape with the input lines and set as the current shape
				App.setShape(new Shape(inputLines));
				reader.close();

			} catch (NumberFormatException n) { //if ReadFile is improperly formatted
				System.err.println("Error - invalid format in ReadFile.txt");
				System.err.println("Please format as: \"x0,y0,x1,y1\" seperated by commas,");
				System.err.println(" with each set of coordinates on a new line.");
			}catch (ArrayIndexOutOfBoundsException b){
				System.err.println("Error - too many lines were input.  Max is 1000 lines.");
			}//try catch

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException i) {
			i.printStackTrace();
		}//try catch
	};

	/**
	 * Displays a dialog box that takes input for horizontal and vertical shifts.
	 */
	public void displayTranslateOptions() {
		//Stage and background field
		Stage translateStage = new Stage();
		VBox field = new VBox(10);

		//Text and Inputs for Shift
		Text shiftInfo = new Text(" Enter coordinates of shift:");
		Text xText = new Text(" x-direction: ");
		Text yText = new Text(" y-direction: ");
		TextField xField = new TextField("0");
		TextField yField = new TextField("0");
		HBox xBox = new HBox(xText, xField);
		HBox yBox = new HBox(yText, yField);

		//Enter Button
		Button enterButton = new Button("Enter");
		enterButton.setAlignment(Pos.BASELINE_CENTER);
		EventHandler<ActionEvent> enterHandler = e ->
		{
			System.out.println("Tx: " + xField.getText());
			System.out.println("Ty: " + yField.getText());
			Shape newShape = App.shape.translate(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
			App.setShape(newShape);
			translateStage.close();
		};
		enterButton.setOnAction(enterHandler);

		//Add elements to scene
		field.getChildren().addAll(shiftInfo, xBox, yBox, enterButton);			
		Scene translateScene = new Scene(field,200,190);
		translateStage.setScene(translateScene);
		translateStage.show();
	}//displayTranslateOptions

	/**
	 * Displays a dialog box that takes input for scale factors and center of scale.
	 */
	public void displayScaleOptions() {
		//Stage and background field
		Stage scaleStage = new Stage();
		VBox field = new VBox(10);

		//Text and Inputs for Scale Factors
		Text scaleInfo = new Text(" Enter scale factors:");
		Text horzText = new Text(" horizontal: ");
		Text vertText = new Text(" vertical: ");
		TextField horzField = new TextField("1");
		TextField vertField = new TextField("1");
		HBox horzBox = new HBox(horzText, horzField);
		HBox vertBox = new HBox(vertText, vertField);

		//Text and Inputs for Center
		Text centerInfo = new Text(" Enter coordinates of center:");
		Text xText = new Text(" x-coord: ");
		Text yText = new Text(" y-coord: ");
		TextField xField = new TextField("0");
		TextField yField = new TextField("0");
		HBox xBox = new HBox(xText, xField);
		HBox yBox = new HBox(yText, yField);

		//Enter Button
		Button enterButton = new Button("Enter");
		enterButton.setAlignment(Pos.BASELINE_CENTER);
		EventHandler<ActionEvent> enterHandler = e ->
		{
			System.out.println("Sx: " + horzField.getText());
			System.out.println("Sy: " +vertField.getText());
			System.out.println("Cx: " + xField.getText());
			System.out.println("Cy: " + yField.getText());
			Shape newShape = App.shape.scale(Double.parseDouble(horzField.getText()), Double.parseDouble(vertField.getText()), Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
			App.setShape(newShape);;
			scaleStage.close();
		};
		enterButton.setOnAction(enterHandler);

		//Add elements to scene
		field.getChildren().addAll(scaleInfo, horzBox, vertBox, centerInfo, xBox, yBox, enterButton);			
		Scene scaleScene = new Scene(field,200,230);
		scaleStage.setScene(scaleScene);
		scaleStage.show();
	}//displayScaleOptions


	/**
	 * Displays a dialog box that takes input for angle and center of rotation.
	 */
	public void displayRotateOptions() {
		//Stage and background field
		Stage rotateStage = new Stage();
		VBox field = new VBox(10);

		//Text and Inputs for Angle
		Text angleInfo = new Text(" Enter angle in degrees clockwise:");
		Text angleText = new Text(" angle: ");
		TextField angleField = new TextField("0");
		HBox angleBox = new HBox(angleText, angleField);

		//Text and Inputs for Center
		Text centerInfo = new Text(" Enter coordinates of center:");
		Text xText = new Text(" x-coord: ");
		Text yText = new Text(" y-coord: ");
		TextField xField = new TextField("0");
		TextField yField = new TextField("0");
		HBox xBox = new HBox(xText, xField);
		HBox yBox = new HBox(yText, yField);

		//Enter Button
		Button enterButton = new Button("Enter");
		enterButton.setAlignment(Pos.BASELINE_CENTER);
		EventHandler<ActionEvent> enterHandler = e ->
		{
			System.out.println("A: " + angleField.getText());
			System.out.println("Cx: " + xField.getText());
			System.out.println("Cy: " + yField.getText());
			Shape newShape = App.shape.rotate(Double.parseDouble(angleField.getText()), Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
			App.setShape(newShape);
			rotateStage.close();
		};
		enterButton.setOnAction(enterHandler);

		//Add elements to scene
		field.getChildren().addAll(angleInfo, angleBox, centerInfo, xBox, yBox, enterButton);			
		Scene rotateScene = new Scene(field,200,190);
		rotateStage.setScene(rotateScene);
		rotateStage.show();
	}//displayRotateOptions

}//TransformBar