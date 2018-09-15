package apijava.search.binarysearch.test;



class Test {
    public static void main(String[] args) {
        String test = "S E A R C H E X A M P L E";
        String[] keys = test.split(" ");
        for (int i = 0; i< keys.length; i++) {
            System.out.println(keys[i]);
        }

        BST<String, Integer> bst = new BST<>();
        for (int i = 0; i < keys.length; i++) {
            bst.put(keys[i], i);
        }

        System.out.println("size = " + bst.size());
        System.out.println("min = " + bst.min());
        System.out.println("max = " + bst.max());

        
        System.out.println("max = " + bst.root.size);
        
    }
}