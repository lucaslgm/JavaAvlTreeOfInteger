public class AlvTreeApp {
    public static void main(String[] args) {
        AvlTreeOfInteger a = new AvlTreeOfInteger();
        AvlTreeOfInteger b = new AvlTreeOfInteger();
        AvlTreeOfInteger c = new AvlTreeOfInteger();
        AvlTreeOfInteger d = new AvlTreeOfInteger();

        //TODO: INSERT 10 ELEMENTOS BALANCEADOS

        System.out.println("\n\n---------- ÁRVORE A ----------\n\n");
        a.add(24);
        a.add(29);
        a.add(46);
        a.add(93);
        a.add(14);
        a.add(28);
        a.add(49);
        a.add(79);
        a.add(47);
        a.add(32);


        a.add(96);
        a.add(21);
        a.add(94);
        a.add(55);
        a.add(34);
        a.add(42);
        a.add(5);
        a.add(51);
        a.add(73);
        a.add(41);
        Integer pai = a.getParent(94);
        System.out.println(pai);

//        printTree(a,79,930);
//
//        //TODO: INSERT 10 ELEMENTOS
//
//        System.out.println("\n\n---------- ÁRVORE B ----------\n\n");
//        b.add(14);
//        b.add(96);
//        b.add(21);
//        b.add(94);
//        b.add(55);
//        b.add(34);
//        b.add(49);
//        b.add(79);
//        b.add(47);
//        b.add(32);
//
//        printTree(b,14, 96);
//
//        //TODO: INSERT 10 ELEMENTOS
//
//        System.out.println("\n\n---------- ÁRVORE C ----------\n\n");
//        c.add(42);
//        c.add(5);
//        c.add(51);
//        c.add(73);
//        c.add(41);
//        c.add(34);
//        c.add(49);
//        c.add(79);
//        c.add(47);
//        c.add(32);
//        printTree(c,42, 47);
//
//        System.out.println("\n\n---------- ÁRVORE PROFESSORA ----------\n\n");
//        d.add(1);
//        d.add(2);
//        d.add(3);
//        d.add(4);
//        d.add(5);
//        d.add(6);
//        d.add(7);
//        d.add(8);
//
//        printTree(d,6, 7);
    }

    public static void printTree(AvlTreeOfInteger tree, int son, int element){
        if(!tree.isEmpty()) {
            System.out.println("\n------------------------------------\n");
            System.out.println("Altura: " + tree.height());
            System.out.println("Tamanho: " + tree.size());
            System.out.println("Vazia: " + tree.isEmpty());
            System.out.println("Balanceada: " + tree.isBalanced());
            System.out.println(String.format("Contém o %d: ", element) + tree.contains(element));//
            String parent = tree.getParent(son) != null ? tree.getParent(son).toString() : "NULL";
            System.out.printf("Pai do %d: " + parent + "%n", son);
            System.out.println("\n------------------------------------\n\n");
            System.out.println("Positon Pre:");
            System.out.print(tree.positionsPre());
            System.out.println("Positon Pos:");
            System.out.print(tree.positionsPos());
            System.out.println("Positon Central:");
            System.out.print(tree.positionsCentral());
            System.out.println("Positon Width:");
            System.out.print(tree.positionsWidth());

            System.out.println("\n------------------------------------\n\n");
        }
        else{
            System.out.println("Árvore vazia.");
        }
    }
}