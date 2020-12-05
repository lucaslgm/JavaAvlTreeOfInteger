public class Node {

    private Node left;
    private Node right;
    private Node father;
    private Integer element;
    private int balFactor;

    public Node(int element) {
        setLeft(setRight(setFather(null)));
        setBalFactor(0);
        setElement(element);
    }

    public String toString() {
        return Integer.toString(getElement());
    }

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public int getBalFactor() {
        return balFactor;
    }

    public void setBalFactor(int balFactor) {
        this.balFactor = balFactor;
    }

    public Node getFather() {
        return father;
    }

    public Node setFather(Node father) {
        this.father = father;
        return father;
    }

    public Node getRight() {
        return right;
    }

    public Node setRight(Node right) {
        this.right = right;
        return right;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }
}
