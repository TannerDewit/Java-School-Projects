import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * The Class FileHandler creates a FileHandler and has a method called writeResults to add survey data to the survey_results.csv file
 */
public class FileHandler {

/** The survey file. */
private String surveyFile="survey_results.csv";

/** The print writer. */
private PrintWriter printWriter;

/** The file output. */
private FileWriter fileOutput;

/**
 * Instantiates a new file handler.
 *
 * @throws IOException Signals that an I/O exception has occurred.
 */
public FileHandler() throws IOException {
	//declares FileWriter and PrintWriter and adds header to results_survey.csv file 
	fileOutput= new FileWriter(surveyFile);
	printWriter= new PrintWriter(fileOutput);
	printWriter.println("DateTime,FirstName,LastName,PhoneNumber,Email,Sex,Water,Meals,Wheat,Sugar,Dairy,Miles,Weight");
	printWriter.flush();
	
}

/**
 * Write results of the survey to the survey_results.csv file
 *
 * @param surveyData the survey data
 * @throws IOException Signals that an I/O exception has occurred.
 */
public void writeResults(String surveyData) throws IOException {

	printWriter.println(surveyData);
	
	printWriter.flush();
}
}
