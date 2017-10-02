import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/** The GUI for the project.
 * 
 * Wentworth Institute of Technology
 * COMP 2000
 * Stack Project Calculator. GUI
 * 
 *  @author 	Pascal Bakker
 *  @author 	Ross Usinger
 *  @author 	Kevin Cotter
 * @version     %I%, %G%
 * @since       1.0
 */
public class GUI extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane border = new BorderPane();

		border.setCenter(createGUI());
		
		
        Scene scene = new Scene(border, 90*4, 575);
        stage.setTitle("My JavaFX Application");
        stage.setScene(scene);
        stage.show();
		
	}
	/** Method calls class that calculates the expression.
	 * 
	 * @param userInput
	 * @return
	 */
	public static double calculateSolution(String userInput){
		return Calculator.compute(userInput);
	}
	
	/** Creates Gridpane for calculator.
	 * 
	 * @return
	 */
	public static GridPane createGUI(){
		GridPane gridPane = new GridPane();
		//gridPane.setHgap(3); 
		//gridPane.setVgap(3); 
		int padding=40;
		gridPane.setVgap(2);
		gridPane.setHgap(2);
		gridPane.setAlignment(Pos.CENTER);
		//Display Label
		Label display = new Label("");
		display.setFont(new Font("Arial", 30));
		display.setPadding(new Insets(30,15,30,15));
		GridPane.setColumnSpan(display, GridPane.REMAINING);

		gridPane.add(display, 0, 0);
		
		//C button
		Button c = new Button("C");
		c.setPadding(new Insets(padding));
		gridPane.add(c, 0, 1);
		c.setOnAction(e->{
			display.setText("");
		});
		
		//Less than button
		Button lessthan = new Button("<");
		lessthan.setPadding(new Insets(padding));
		gridPane.add(lessthan, 1, 1);
		lessthan.setOnAction(e->{
			if(display.getText().length()!=0)
				display.setText(display.getText().substring(0,display.getText().length()-1));
		});
		
		//Q button
		Button q = new Button("Q");
		q.setPadding(new Insets(padding));
		gridPane.add(q, 2, 1);
		q.setOnAction(e->{
			display.setText(display.getText()+"q");
		});
		
		//Divide button
		Button divide = new Button("/");
		divide.setPadding(new Insets(padding));
		gridPane.add(divide, 3, 1);
		divide.setOnAction(e->{
			display.setText(display.getText()+" / ");
		});
		
		//Multiply Button
		Button multiply = new Button("*");
		multiply.setPadding(new Insets(padding));
		gridPane.add(multiply, 3, 2);
		multiply.setOnAction(e->{
			display.setText(display.getText()+" * ");
		});
		
		//Minus button
		Button minus = new Button("-");
		minus.setPadding(new Insets(padding));
		gridPane.add(minus, 3, 3);
		minus.setOnAction(e->{
			display.setText(display.getText()+" - ");
		});
		
		//Add
		Button add = new Button("+");
		add.setPadding(new Insets(padding));
		gridPane.add(add, 3, 4);
		add.setOnAction(e->{
			display.setText(display.getText()+" + ");
		});
		
		//Equals
		Button equals = new Button("=");
		equals.setPadding(new Insets(padding));
		gridPane.add(equals, 3, 5);
		equals.setOnAction(e->{
			String userInput = display.getText();
			double answer = calculateSolution(userInput);
			System.out.println(answer);
			display.setText(Double.toString(answer));
		});
		
		//Numbers 1-3
		for(int i=1;i<4;i++){
			Button numberI = new Button(Integer.toString(i));
			numberI.setPadding(new Insets(padding));
			gridPane.add(numberI, i-1, 4);
			final int var1 =i;
			numberI.setOnAction(e->{
				display.setText(display.getText()+Integer.toString(var1));
			});
		}
		//Numbers 4-6
		for(int i=4;i<7;i++){
			Button numberI = new Button(Integer.toString(i));
			numberI.setPadding(new Insets(padding));

			gridPane.add(numberI, i-1-3, 3);
			final int var1 =i;
			numberI.setOnAction(e->{
				display.setText(display.getText()+Integer.toString(var1));
			});	
		}
		//Numbers 7-9
		for(int i=7;i<10;i++){
			Button numberI = new Button(Integer.toString(i));
			numberI.setPadding(new Insets(padding));

			gridPane.add(numberI, i-7, 2);
			final int var1 =i;
			numberI.setOnAction(e->{
				display.setText(display.getText()+Integer.toString(var1));
			});
		}
		//Number 0
		Button zero = new Button("0");
		zero.setPadding(new Insets(padding));

		gridPane.add(zero, 0, 5);
		zero.setOnAction(e->{
			display.setText(display.getText()+"0");
		});
		
		//( Button
		Button openParen = new Button("(");
		openParen.setPadding(new Insets(padding));

		gridPane.add(openParen, 1, 5);
		openParen.setOnAction(e->{
			display.setText(display.getText()+" ( ");
		});
		
		//) Button
		Button closeParen = new Button(")");
		closeParen.setPadding(new Insets(padding));

		gridPane.add(closeParen, 2, 5);
		closeParen.setOnAction(e->{
			display.setText(display.getText()+" ) ");
		});
		return gridPane;
		
	}
	public static void main(String[] args){
		Application.launch(args);
	}
}
