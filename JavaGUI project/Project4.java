import java.io.IOException;

/*
 * Author: Tanner Dewit
 * Course: Cop3503
 * Project #: 4
 * Title: GUIs
 * Due Date: 5/1/2023
 * 
 * GUI survey that asks for the personal information and dietary questions and then adds that data to a excel sheet
 * when the submit button is pressed or clears the data if the clear button is pressed.
 */
/**
 * calls CustomJFrame class and intializes a new GUI survey
 */
public class Project4 {


public static void main(String[] args){
	try {
		CustomJFrame frame = new CustomJFrame();
	} catch (IOException e) {
		System.out.print("Input Output Exception");
	}
}
}
