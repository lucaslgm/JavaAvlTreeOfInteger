public class AvlTreeOfInteger {
    private Node root;
    private  int count;

    public AvlTreeOfInteger() {
        this.root = null;
        this.count = 0;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //TODO método de depuração
    public String printArvore(){
        return printArvore(0, root);
    }

    private String printArvore(int level, Node aux){
        String  father = aux.getFather() != null ? Integer.toString(aux.getFather().getElement()) : "NULL";
        String str = String.format("[%d] (%d) <"+father+">\n", aux.getElement(), aux.getBalFactor());
        for (int i=0; i<level; i++){
            str += "\t";
        }
        if (aux.getLeft() != null){
            str += "+-ESQ: "+ printArvore(level + 1, aux.getLeft());
        }
        else{
            str += "+-ESQ: NULL";
        }
        str += "\n";
        for (int i=0; i<level; i++){
            str += "\t";
        }
        if (aux.getRight() != null){

            str += "+-DIR: "+ printArvore(level + 1, aux.getRight());
        }
        else{
            str += "+-DIR: NULL";
        }
        str += "\n";
        return str;
    }
    //////////////////////////////////////////////////////////////////////////////////

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public void clear() {
        count = 0;
        root = null;
    }

    public Integer height(){
        return height(root);
    }

    private Integer height(Node aux){
        if(aux != null)
        {
            if(aux.getLeft() == null && aux.getRight() == null){
                return 1;
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
        return 0;
    }

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

    public boolean contains(Integer element) {
        Node aux = searchNodeRef(element, root);
        return (aux != null);
    }

    public Integer getParent(Integer element) {
        Node aux = searchNodeRef(element, root);
        if(aux != null){
            if(aux.getFather() != null) {
                return aux.getFather().getElement();
            }
        }
        return null;
    }

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

    public void add(int element) {
        Node newNode = new Node(element);
        add(this.root, newNode);
        count++;
        System.out.println(String.format("---- ELEMENTO INSERIDO %d ----\n", element) + printArvore() + "------------------------------\n");
    }

    private void add(Node n, Node newNode) {
        if (n == null) {
            this.root = newNode;
        }
        else {
            if (newNode.getElement() < n.getElement()) {
                if (n.getLeft() == null) {
                    n.setLeft(newNode);
                    newNode.setFather(n);
                    toBalance(n);
                }
                else {
                    add(n.getLeft(), newNode);
                }
            }
            else if (newNode.getElement() > n.getElement()) {
                if (n.getRight() == null) {
                    n.setRight(newNode);
                    newNode.setFather(n);
                    toBalance(n);
                }
                else {
                    add(n.getRight(), newNode);
                }
            }
            //TODO: O nó já existe, lançar exceção
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

    public void addWithoutBalance(int element){
        Node newNode = new Node(element);
        addWithoutBalance(this.root, newNode);
        count++;
        balanceCalc();
        System.out.println(String.format("---- ELEMENTO INSERIDO %d ----\n", element) + printArvore() + "------------------------------\n");
    }

    private void addWithoutBalance(Node n, Node newNode){
        if (n == null) {
            this.root = newNode;
        }
        else {
            if (newNode.getElement() < n.getElement()) {
                if (n.getLeft() == null) {
                    n.setLeft(newNode);
                    newNode.setFather(n);
                }
                else {
                    addWithoutBalance(n.getLeft(), newNode);
                }
            }
            else if (newNode.getElement() > n.getElement()) {
                if (n.getRight() == null) {
                    n.setRight(newNode);
                    newNode.setFather(n);
                }
                else {
                    addWithoutBalance(n.getRight(), newNode);
                }
            }
            //TODO: O nó já existe, lançar exceção
        }
    }

    public void balanceCalc(){
        balanceCalc(root);
    }

    public void balanceCalc(Node aux){
        if(aux.getRight() == null && aux.getLeft() == null){
            aux.setBalFactor(0);
        }
        else if(aux.getRight() != null && aux.getLeft() == null){
            aux.setBalFactor(height(aux.getRight()));
        }
        else if(aux.getRight() == null){
            aux.setBalFactor(-height(aux.getLeft()));
        }
        else{
            aux.setBalFactor(height(aux.getRight()) - height(aux.getLeft()));
        }
        if(aux.getRight() != null){
            balanceCalc(aux.getRight());
        }
        if(aux.getLeft() != null){
            balanceCalc(aux.getLeft());
        }
    }

    /////////////////////////////////////////////////////////////////////////////////

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

    private void setBalFactor(Node node) {
        int balFactor = height(node.getRight()) - height(node.getLeft());
        node.setBalFactor(balFactor);
    }

    private void toBalance(Node aux) {
        setBalFactor(aux);
        int balFactor = aux.getBalFactor();

        if (balFactor == -2) {
            if (height(aux.getLeft().getLeft()) >= height(aux.getLeft().getRight())) {
                aux = simpleRightRotation(aux);
                System.out.println("Rotação Simples Direita");


            } else {
                aux = doubleLeftRotation(aux);
                System.out.println("Rotação Dupla Esquerda");
            }
        }
        else if (balFactor == 2) {
            if (height(aux.getRight().getRight()) >= height(aux.getRight().getLeft())) {
                aux = simpleLeftRotation(aux);
                System.out.println("Rotação Simples Esquerda");

            } else {
                aux = doubleRightRotation(aux);
                System.out.println("Rotação Dupla Direita");
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