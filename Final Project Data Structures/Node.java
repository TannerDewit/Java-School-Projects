
/**
 * The Class Node stores the name, population, area, and next node of the node.
 * There is also an option to print the node.
 *
 * @author Tanner Dewit
 * @version 12/09/23
 */
public class Node {

	/** The country's name. */
	String name;

	/** The country's population. */
	long population;

	/** The country's area. */
	long area;

	/** This node's next node. */
	Node nextNode;

	/**
	 * Instantiates a new node.
	 *
	 * @param name       the name
	 * @param population the population
	 * @param area       the area
	 */
	public Node(String name, long population, long area) {
		this.name = name;
		this.population = population;
		this.area = area;
	}

	/**
	 * print a node’s name and its population density
	 */
	public void printNode() {
		System.out.printf("%-32s   %-14.4f\n", name, (float) population / area);
	}
}
