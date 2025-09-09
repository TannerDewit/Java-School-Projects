/**
 * The Class LinkList allows the user to create a new linked list that stores
 * the first and last node. Allows the user to insert a new node into the list,
 * properly linking the previous nodes to the new node.
 * 
 * @author Tanner Dewit
 * @version 12/09/2023
 */
public class LinkList {

	/** The first link in the list. */
	public Node first;

	/** The last link in the list. */
	public Node last;

	/**
	 * Instantiates a new link list.
	 */
	public LinkList() {
		first = null;
		last = null;
	}

	/**
	 * Inserts the new node as the first and last node if empty. If second node
	 * makes the new node the last node and links first node to the new node. If any
	 * other situation new node is inserted as last node and previous last node is
	 * updated to have its next node point to to the new node.
	 *
	 * @param newNode the new node
	 */
	public void insert(Node newNode) {
		if (first == null) {
			first = newNode;
			last = newNode;
		} else if (first.nextNode == null) {
			first.nextNode = newNode;
			last = newNode;
		} else {
			last.nextNode = newNode;
			last = newNode;
		}
	}
}