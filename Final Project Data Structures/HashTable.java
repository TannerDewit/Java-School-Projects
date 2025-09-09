/**
 * The HashTable class allows you to create a hash table at a given size. The
 * HashTable object stores an array of LinkList objects and the array's size at
 * arraySize. The HashTable allows you to insert Node objects into its LinkList
 * objects based on its hash value which is determined by taking the unicode
 * value of the name string and moduloing by the arraySize. This class allows
 * you to finds a country by its name using its hash value. If the country
 * exists it prints the hash value it was found at as well as the population
 * density of the country. If not found method prints that the country is not
 * found. This class also allows you to delete nodes based on the hash value of
 * the name given. If the country is not found, the method prints that the
 * country does not exist. If the country is found it is deleted and declared
 * that it is deleted. This class also can display all the countries in the hash
 * table with their position in the array as well as their population density.
 * This class has a function to find the number of empty and collided cells.
 * This class also has a method to find the hash value of a given string.
 * 
 * @author Tanner Dewit
 * @version 12/09/23
 */
public class HashTable {

	/** The hash array. */
	private LinkList[] hashArray;

	/** The array size. */
	private int arraySize;

	/**
	 * Instantiates a new hash table.
	 *
	 * @param size the size of the hash table
	 */
	public HashTable(int size) {
		hashArray = new LinkList[size];
		arraySize = size;
	}

	/**
	 * Inserts a new node created using the country's name, population, and area
	 * based on its hash value. instantiates a new linked list if it doesn't exist
	 * already.
	 *
	 * @param country    the country's name
	 * @param population the population
	 * @param area       the area
	 */
	public void insert(String country, long population, long area) {
		Node newNode = new Node(country, population, area);
		int hashVal = hashFunc(country);
		if (hashArray[hashVal] == null) {
			hashArray[hashVal] = new LinkList();
		}
		hashArray[hashVal].insert(newNode);
	}

	/**
	 * Finds the country by its name using its hash value. If the country exists it
	 * prints the hash value it was found at as well as the population density of
	 * the country. If not found method prints that the country is not found. At the
	 * hash value determined, the linked list at that hash value is searched through
	 * for the country's name.
	 *
	 * @param country the country
	 * @return the hashval or -1 if not found
	 */
	public int find(String country) {
		int hashVal = hashFunc(country);
		if (hashArray[hashVal] == null) {
			System.out.println("\n" + country + " is not found");
			return -1;
		}
		Node comp = hashArray[hashVal].first;
		while (comp != null) {
			if (comp.name.equals(country)) {
				System.out.printf("\n%s is found at index %d with population density of %-14.4f\n", country, hashVal,
						(float) comp.population / comp.area);
				return hashVal;
			}
			comp = comp.nextNode;
		}
		System.out.println("\n" + country + " is not found");
		return -1;
	}

	/**
	 * Deletes the country node based on the hash value of the name. If there is no
	 * linked list at the hash value, method prints that the country does not exist.
	 * If a linked list exists at the hash value it searches through the list for
	 * the country, if found deletes country's node and declares that. If the
	 * country is not found after searching through the entire linked list, method
	 * declares that the country does not exist.
	 *
	 * @param country the country
	 */
	public void delete(String country) {
		int hashVal = hashFunc(country);
		if (hashArray[hashVal] == null) {
			System.out.println("\n" + country + " is not a country");
		} else {
			Node current = hashArray[hashVal].first;
			Node previous = null;

			while (current != null) {
				if (current.name.equals(country)) {

					if (previous == null) {
						hashArray[hashVal].first = current.nextNode;
					} else {
						previous.nextNode = current.nextNode;
					}

					current = null;

					System.out.println("\n" + country + " is deleted from hash table");
					return;
				}
				previous = current;
				current = current.nextNode;
			}

			System.out.println("\n" + country + " is not a country");
		}
	}

	/**
	 * Displays all the countries in the hash table along with their position in the
	 * array as well as their population density. If the linked list is non existent
	 * or empty prints that the node is an empty node. If there are nodes in the
	 * linked list at that hash value then it prints them from first to last in the
	 * linked list.
	 */
	public void display() {
		System.out.println();
		for (int j = 0; j < arraySize; j++) {
			if (hashArray[j] != null) {
				Node current = hashArray[j].first;
				if (current == null) {
					System.out.printf("%3d.   Empty Node\n", j);
				} else {
					System.out.printf("%3d.   ", j);
					current.printNode();
					current = current.nextNode;
					while (current != null) {
						System.out.print("       ");
						current.printNode();
						current = current.nextNode;
					}
				}
			} else {
				System.out.printf("%3d.   Empty Node\n", j);
			}
		}
	}

	/**
	 * Prints the number of empty and collided cells. If linked list is empty or non
	 * existent at a hash value, it is considered empty and adds to the total count
	 * of empty cells. If a linked list has two or more nodes in it than it is a
	 * collided cell and is added to the count of collided cells.
	 */
	public void printEmptyAndCollidedCells() {
		int X = 0;
		int Y = 0;
		for (int j = 0; j < arraySize; j++) {
			if (hashArray[j] != null) {
				Node current = hashArray[j].first;
				if (current != null && current.nextNode != null) {
					Y++;
				} else if (current == null) {
					X++;
				}
			} else {
				X++;
			}
		}
		System.out.println("\nThere are " + X + " empty cells and " + Y + " collisions in the hash table");
	}

	/**
	 * Hash function gets the unicode value and uses modulus by the array size to
	 * determine the hash value of a given string and returns that as the hash
	 * value.
	 *
	 * @param name the name
	 * @return the hash code value
	 */
	public int hashFunc(String name) {
		int unicodeValue = 0;
		for (int i = 0; i < name.length(); i++) {
			unicodeValue += name.codePointAt(i);
		}
		return unicodeValue %= arraySize;
	}
}