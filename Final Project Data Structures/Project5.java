import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * COP 3530: Project 5 – Hash Tables
 * <p>
 * Project 5 allows users to interacts with a hash table. The hash table's data
 * is read from a csv file entered by the user. The hash table can be printed by
 * the user. The hash table's data can also be manipulated either by deleting or
 * inserting countries. The delete function uses a name to search for the
 * country and delete it. The insert function asks for the name, population, and
 * area of a country before inserting it into the hash table. The user is also
 * capable of searching for countries by name, the program will either respond that
 * it has not been found or that it has been found and its hash value and
 * population density. The user can print the number of empty and collided cells
 * in the hash table. The user can also end the program.
 * <p>
 *
 * @author Tanner Dewit
 * @version 12/09/23
 */
public class Project5 {

	private static HashTable hashTable = new HashTable(257);

	/**
	 * The main method produces the title of the project along with the instructor
	 * and class. Prompts the user for a file name and reads the file putting the
	 * countries into a HashTable object. The program provides a menu for users to
	 * interact with the hash table. Users can input choices (1-6) to perform
	 * operations. Option 1 prints the hash table. Option 2 prompts users to enter a
	 * country name to be deleted from the hash table. Option 3 lets users input
	 * their own country using name, population, and area. Invalid inputs reprompt
	 * for a valid number. Option 4 gives the user the ability to see if a country
	 * is a part of the hash table through a given name.If found the program prints
	 * the found country's hash value and population density. Option 5 displays the
	 * number of empty cells and collided cells. Option 6 terminates the program.
	 * 
	 * @param args the arguments
	 * @throws FileNotFoundException the file not found exception
	 */
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("COP3530 Project 5");
		System.out.println("Instructor: Xudong Liu\n");
		System.out.println("Hash Tables");
		Scanner userInput = new Scanner(System.in);
		readDataFromFile(userInput);
		int choice = 0;

		boolean continueRunning = true;
		displayMenu();
		while (continueRunning) {
			if (userInput.hasNextInt()) {
				choice = userInput.nextInt();
				switch (choice) {
				case 1:
					hashTable.display();
					displayMenu();
					break;
				case 2:
					userInput.nextLine();
					System.out.print("Enter country name: ");
					String name = userInput.nextLine();
					hashTable.delete(name);
					displayMenu();
					break;
				case 3:
					do {
						userInput.nextLine();
						System.out.print("Enter the country's name: ");
						String countryName = userInput.nextLine();
						System.out.print("Enter country population: ");
						if (userInput.hasNextLong()) {
							Long population = Long.parseLong(userInput.nextLine());
							System.out.print("Enter country area: ");
							if (userInput.hasNextLong()) {
								Long area = Long.parseLong(userInput.nextLine());
								hashTable.insert(countryName, population, area);
								System.out.println("\n" + countryName + " is inserted to hash table");
								displayMenu();
								break;
							} else {
								System.out.println("Invalid input. Please enter a valid area next time.\n");
							}
						} else {
							System.out.println("Invalid input. Please enter a valid population next time.\n");
						}
					} while (true);

					break;
				case 4:
					userInput.nextLine();
					System.out.print("Enter country name: ");
					String countrySearch = userInput.nextLine();
					hashTable.find(countrySearch);
					displayMenu();
					break;
				case 5:
					hashTable.printEmptyAndCollidedCells();
					displayMenu();
					break;
				case 6:
					continueRunning = false;
					userInput.close();
					System.out.print("\nHave a good day!");
					break;
				default:
					System.out.print("Invalid choice enter 1-6: ");
					break;
				}
			} else {
				System.out.print("Invalid choice enter 1-6: ");
				userInput.next();
			}
		}
	}

	/**
	 * Reads data from file if the file is found. Reads countries from file and
	 * inserts the countries into the hash table and counts each country. Then
	 * prints the amount of countries added to the hash table.
	 *
	 * @param userInput the user scanner
	 */
	private static void readDataFromFile(Scanner userInput) {
		String fileName = null;
		boolean fileFound = false;
		while (!fileFound) {
			try {
				System.out.print("Enter the file name: ");
				fileName = userInput.nextLine();

				File file = new File(fileName);

				if (file.exists() && file.isFile()) {
					fileFound = true;
				} else {
					System.out.println("File not found. Please re-enter the file name.");
				}
			} catch (Exception e) {
				System.out.println("An error occurred: " + e.getMessage());
			}
		}

		try {
			FileReader reader = new FileReader(fileName);
			Scanner fr = new Scanner(reader);
			fr.nextLine();
			int count = 0;
			while (fr.hasNextLine()) {
				String line = fr.nextLine();
				String[] values = line.split(",");

				if (values.length == 6) {
					hashTable.insert(values[0], Long.parseLong(values[2]), Long.parseLong(values[4]));
					count++;
				}
			}
			fr.close();
			System.out.println("\nThere were " + count + " country records read into the hash table.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints the 6 options for the user to choose from and prompts user for an
	 * input.
	 */
	private static void displayMenu() {
		System.out.print("\n1) Print hash table\n" + "2) Delete a country of a given name\n"
				+ "3) Insert a country of its name, population, and area\n"
				+ "4) Search and print a country and its population density for a given name\n"
				+ "5) Print numbers of empty cells and collided cells\n" + "6) Exit" + "\nEnter your choice: ");
	}

}
