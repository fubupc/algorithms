import java.util.Stack;

class MyBST {
    static class Node {
        public int key;
        public Node left;
        public Node right;

        public Node(int k, Node l, Node r) {
            key = k;
            left = l;
            right = r;
        }

        public Node(int k) {
            this(k, null, null);
        }
    }

    private Node root;

    public MyBST() {
        root = null;
    }

    public void insert(int k) {
        root = insert(root, k);
    }

    private Node insert(Node n, int k) {
        if (n == null)
            return new Node(k);

        if (k < n.key) {
            n.left = insert(n.left, k);
        } else if (k > n.key) {
            n.right = insert(n.right, k);
        }

        return n;
    }

    public void preOrder() {
        preOrder(root);
    }

    public void preOrder2() {
        preOrder2(root);
    }

    /* basic idea is checking CHILD OF CURRENT node to determine PUSH or POP
     * NOT ELEGANT */
    private void preOrder(Node n) {
        Stack<Node> st = new Stack<Node>();

        Node curr = n;
        while (curr != null) {
            System.out.print(curr.key + " ");

            if (curr.left != null) {
                st.push(curr);
                curr = curr.left;
            } else if (curr.right == null) {
                if (st.empty()) break;

                curr = st.pop().right;
            } else if (curr.right != null) {
                curr = curr.right;
            }
        }

        System.out.println();
    }

    /* basic idea is checking CURRENT node to determine PUSH or POP
     * BETTER!
     * */
    private void preOrder2(Node n) {
        Node curr = n;
        Stack<Node> st = new Stack<Node>();

        while (curr != null || !st.empty()) {
            if (curr != null) {
                System.out.print(curr.key + " ");
                st.push(curr);
                curr = curr.left;
            } else {
                curr = st.pop();
                curr = curr.right;
            }
        }
        System.out.println();
    }

    public void inOrder() {
        inOrder(root);
    }

    /* same as preOrder2() */
    private void inOrder(Node n) {
        Node curr = n;
        Stack<Node> st = new Stack<Node>();

        while (curr != null || !st.empty()) {
            if (curr != null) {
                st.push(curr);
                curr = curr.left;
            } else {
                curr = st.pop();
                System.out.print(curr.key + " ");
                curr = curr.right;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MyBST tree = new MyBST();

        tree.insert(4);
        tree.insert(2);
        tree.insert(1);
        tree.insert(3);
        tree.insert(6);
        tree.insert(5);
        tree.insert(7);

        System.out.print("preOrder(): \t");
        tree.preOrder();

        System.out.print("preOrder2(): \t");
        tree.preOrder2();

        System.out.print("inOrder(): \t");
        tree.inOrder();
    }

}


