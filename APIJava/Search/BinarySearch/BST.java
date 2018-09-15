import java.util.NoSuchElementException;
import java.util.Queue;

public class BST<Key extends Comparable<Key>, Value > {
    public Node root;

    public class Node {
        public Key key; // sorted by key
        private Value value; // sorted by value
        private Node left, right; // left and right subtrees
        private int size; // number node in subtree

        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public BST() {}

    /**
     * return true if symbol table is empty.
     * @return
     */
    public boolean isEmpty() {
        return size() ==  0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     */

    public int size() {
        return size(root);
    } 

    // return number of key-value pairs in BST rooted at x.
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     * {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */

    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argrument to contains() is null");
        }
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key
     */

     public Value get(Key key) {
         return get(root, key);
     }

     private Value get(Node x, Key key) {
         if (key == null) 
            throw new IllegalArgumentException("calls get with a null key");
         if (x == null) 
            return null;
         int cmp = key.compareTo(x.key);
         if (cmp < 0)
            return get(x.left, key);
         else if (cmp > 0) 
            return get(x.right, key);
         else 
            return x.value;
     }

     /**
      * Inserts the specified key-value pair into the symbol table, overwriting the old 
      * value with the new value if the symbol table already contains the specified key.
      * Deletes the specified key (and its associated value) from this symbol table
      * if the specified value is {@code null}.
      * @param  key the key
      * @param  val the value
      * @throws IllegalArgumentException if {@code key} is {@code null}
      */

      public void put(Key key, Value value) {
          if (key == null) {
              throw new IllegalArgumentException("calls put() with a null key");
          }

          if (value == null) {
              delete(key);
              return;
          }

          root = put(root, key, value);
      }

    private Node put(Node x, Key key, Value value) {
        if ( x == null) 
            return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if (cmp > 0)
            x.right = put(x.right, key, value);
        else if (cmp < 0) 
            x.left = put(x.left, key, value);
        else
            x.value = value;

        x.size = 1 + size(x.left) + size(x.right);

        return x;
    }

    /**
     * Removes the smallest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */

    public void deleteMin() {
        if (isEmpty())
            throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }
    
    private Node deleteMin(Node x) {
        if (x.left == null) 
            return x.right;

        x.left = deleteMin(x.left);

        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    /**
     * Removes the largest key and associated value from the symbol table.
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) 
            throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) 
            return x.left;
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
        if (key == null) 
            throw new IllegalArgumentException("call delete() with a null key");
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) 
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else 
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

     /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public Key min() {
        if (isEmpty())
            throw new NoSuchElementException("call min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) 
            return x;
        else
            return min(x.left);
    }

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */

    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("call max() with empty symbol table");
        return max(root).key;
    } 

    private Node max(Node x) {
        if (x.right == null) 
            return x;
        else
            return max(x.right);
    }

     /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */

    public Key floor(Key key) {
        if (key == null)
            throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) 
            throw new NoSuchElementException("call floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null)
            return null;
        else
            return x.key;
    } 

    public Node floor(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp < 0)
            return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) 
            return t;
        else
            return x;
    }

    public Key floor2(Key key) {
        return floor2(root, key, null);
    }

    private Key floor2(Node x, Key key, Key best) {
        if (x == null)
            return best;        
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return floor2(x.left, key, best);
        else if (cmp > 0)
            return floor2(x.right, key, x.key);
        else
            return x.key;
    }

    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    // public Iterable<Key> levelOrder() {
    //     Queue<Key> keys = new Queue<Key>();
    //     Queue<Node> queue = new Queue<Node>();
    //     queue.enqueue();

    //     return keys;
    // }
}