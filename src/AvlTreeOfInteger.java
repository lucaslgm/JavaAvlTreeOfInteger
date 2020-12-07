public class AvlTreeOfInteger {
    private Node root;
    private  int count;

    public AvlTreeOfInteger() {
        this.root = null;
        this.count = 0;
    }

    //O(1)
    public int size() {
        return count;
    }

    //O(1)
    public boolean isEmpty() {
        return (root == null);
    }

    //O(1)
    public void clear() {
        count = 0;
        root = null;
    }

    //O(n²)
    public Integer height(){
        return height(root);
    }

    private Integer height(Node aux){
        if(aux != null)
        {
            if(aux.getLeft() == null && aux.getRight() == null){
                return 0;
            }
            else if(aux.getLeft() != null && aux.getRight() == null){
                return 1 + height(aux.getLeft());
            }
            else if(aux.getLeft() == null && aux.getRight() != null){
                return 1 + height(aux.getRight());
            }
            else{
                return 1 + Math.max(height(aux.getLeft()), height(aux.getRight()));
            }
        }
        return -1;
    }

    //O(1)
    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node aux) {
        if(aux.getBalFactor() >= 2 || aux.getBalFactor() <= -2){
            return false;
        }
        if(aux.getLeft() != null){
            isBalanced(aux.getLeft());
        }
        if(aux.getRight() != null){
            isBalanced(aux.getRight());
        }
        return true;
    }

    //O(1)
    public boolean contains(Integer element) {
        Node aux = searchNodeRef(element, root);
        return (aux != null);
    }

    //O(1)
    public Integer getParent(Integer element) {
        Node aux = searchNodeRef(element, root);
        if(aux != null){
            if(aux.getFather() != null) {
                return aux.getFather().getElement();
            }
        }
        return null;
    }

    //O(log n)
    private Node searchNodeRef(Integer element, Node target) {
        if (element == null || target == null) {
            return null;
        }
        Integer tElement = target.getElement();
        int r = tElement.compareTo(element);

        if (r == 0) {
            return target;
        } else if (r > 0) {
            return searchNodeRef(element, target.getLeft());
        } else {
            return searchNodeRef(element, target.getRight());
        }
    }

    //O(n log n)
    public void add(int element) {
        boolean added = false;
        Node newNode = new Node(element);
        added = add(this.root, newNode, added);
        count++;

        if(!added){
            count--;
            System.out.println("Árvore já possui o elemento: " + element);
        }
    }

    private boolean add(Node n, Node newNode, boolean added) {
        if (n == null) {
            this.root = newNode;
            added = true;
        }
        else {
            if (newNode.getElement() < n.getElement()) {
                if (n.getLeft() == null) {
                    n.setLeft(newNode);
                    newNode.setFather(n);
                    toBalance(n);
                    added =  true;
                }
                else {
                    added = add(n.getLeft(), newNode, added);
                }
            }
            else if (newNode.getElement() > n.getElement()) {
                if (n.getRight() == null) {
                    n.setRight(newNode);
                    newNode.setFather(n);
                    toBalance(n);
                    added = true;
                }
                else {
                    added = add(n.getRight(), newNode, added);
                }
            }
            else if (newNode.getElement() == n.getElement()) {
                //TODO: O nó já existe, lançar exceção
                added = false;
            }
        }
        return added;
    }

    /////////////////////////////////////////////////////////////////////////////////

    //0(1)
    public Node simpleLeftRotation(Node aux) {
        Node right = aux.getRight();
        right.setFather(aux.getFather());

        aux.setRight(right.getLeft());

        if (aux.getRight() != null) {
            aux.getRight().setFather(aux);
        }

        right.setLeft(aux);
        aux.setFather(right);

        if (right.getFather() != null) {

            if (right.getFather().getRight() == aux) {
                right.getFather().setRight(right);

            } else if (right.getFather().getLeft() == aux) {
                right.getFather().setLeft(right);
            }
        }

        setBalFactor(aux);
        setBalFactor(right);

        return right;
    }

    public Node simpleRightRotation(Node aux) {
        Node left = aux.getLeft();
        left.setFather(aux.getFather());


        aux.setLeft(left.getRight());

        if (aux.getLeft() != null) {
            aux.getLeft().setFather(aux);
        }

        left.setRight(aux);
        aux.setFather(left);

        if (left.getFather() != null) {

            if (left.getFather().getRight() == aux) {
                left.getFather().setRight(left);

            } else if (left.getFather().getLeft() == aux) {
                left.getFather().setLeft(left);
            }
        }

        setBalFactor(aux);
        setBalFactor(left);

        return left;
    }

    public Node doubleLeftRotation(Node aux) {
        Node newLeftNode = simpleLeftRotation(aux.getLeft());
        aux.setLeft(newLeftNode);
        return simpleRightRotation(aux);
    }

    public Node doubleRightRotation(Node aux) {
        Node newRightNode = simpleRightRotation(aux.getRight());
        aux.setRight(newRightNode);
        return simpleLeftRotation(aux);
    }

    //O(n log n)
    private void setBalFactor(Node node) {
        int balFactor = height(node.getRight()) - height(node.getLeft());
        node.setBalFactor(balFactor);
    }
    //O(n log n)
    private void toBalance(Node aux) {
        setBalFactor(aux);
        int balFactor = aux.getBalFactor();

        if (balFactor == -2) {
            if (height(aux.getLeft().getLeft()) >= height(aux.getLeft().getRight())) {
                aux = simpleRightRotation(aux);
            } else {
                aux = doubleLeftRotation(aux);
            }
        }
        else if (balFactor == 2) {
            if (height(aux.getRight().getRight()) >= height(aux.getRight().getLeft())) {
                aux = simpleLeftRotation(aux);
            } else {
                aux = doubleRightRotation(aux);
            }
        }
        if (aux.getFather() != null) {
            toBalance(aux.getFather());
        }
        else {
            this.root = aux;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////

    //O(1)
    public LinkedListOfInteger positionsPre() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPreAux(root, res);
        return res;
    }
    private void positionsPreAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            res.add(n.getElement()); //Visita o nodo
            positionsPreAux(n.getLeft(), res); //Visita a subárvore da esquerda
            positionsPreAux(n.getRight(), res); //Visita a subárvore da direita
        }
    }

    public LinkedListOfInteger positionsPos() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsPosAux(root, res);
        return res;
    }
    private void positionsPosAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsPosAux(n.getLeft(), res); //Visita a subárvore da esquerda
            positionsPosAux(n.getRight(), res); //Visita a subárvore da direita
            res.add(n.getElement()); //Visita o nodo
        }
    }

    public LinkedListOfInteger positionsCentral() {
        LinkedListOfInteger res = new LinkedListOfInteger();
        positionsCentralAux(root, res);
        return res;
    }
    private void positionsCentralAux(Node n, LinkedListOfInteger res) {
        if (n != null) {
            positionsCentralAux(n.getLeft(), res); //Visita a subárvore da esquerda
            res.add(n.getElement()); //Visita o nodo
            positionsCentralAux(n.getRight(), res); //Visita a subárvore da direita
        }
    }

    public LinkedListOfInteger positionsWidth() {
        Queue<Node> fila = new Queue<>();
        Node atual = null;
        LinkedListOfInteger res = new LinkedListOfInteger();
        if (root != null) {
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                atual = fila.dequeue();
                if (atual.getLeft() != null) {
                    fila.enqueue(atual.getLeft());
                }
                if (atual.getRight() != null) {
                    fila.enqueue(atual.getRight());
                }
                res.add(atual.getElement());
            }
        }
        return res;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
}