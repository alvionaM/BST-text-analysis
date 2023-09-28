import javax.swing.tree.TreeNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class BST implements WordCounter{

    private class TreeNode {
        WordFreq item;

        TreeNode left;           // pointer to left subtree
        TreeNode right;          // pointer to right subtree
        int subtreeSize;         // number of nodes in subtree starting at this node

        TreeNode(WordFreq word) {
            item = word;
            left = right = null;
            subtreeSize = 0;
        }

    }


    private class Stack<T>{

        private final LinkedList<T> stack = new LinkedList<>();

        public boolean isEmpty() {
            return stack.isEmpty();
        }

        public void push(T item) {
            stack.insertAtFront(item);
        }

        public T pop() throws NoSuchElementException {
            return stack.removeFromFront();
        }

        public int size() {
            return stack.getSize();
        }
    }


    private TreeNode head;      // root of the tree
    private LinkedList<String> stopWords = new LinkedList<>();      // list of stopwords
    private int totalWords = 0;

    private TreeNode save;      // keeps current from search()
    private WordFreq[] wordsByFreq;


    ///////////////////////////////////////////
    private boolean equals(String w, TreeNode t) {
        return w.compareToIgnoreCase(t.item.key()) == 0;
    }
    private boolean less(String w, TreeNode t) {
        return w.compareToIgnoreCase(t.item.key()) < 0;
    }

    private WordFreq visit(TreeNode t) {
        return t.item;
    }


    private WordFreq preorderMaxFreq(TreeNode t, WordFreq max) {
        //System.out.println("--------------------------");
        Stack<TreeNode> s = new Stack<>();
        s.push(t);
        while (!s.isEmpty()) {
            t = s.pop();
            WordFreq temp = visit(t);
            //System.out.println(max);
            if(temp.getFreq() > max.getFreq()){
                max = temp;
            }
            if (t.right != null)
                s.push(t.right);
            if (t.left != null)
                s.push(t.left); }

        return max;
    }


    private void inorderPrintByKey(TreeNode t, PrintStream stream) {
        if (t == null)
            return;

        inorderPrintByKey(t.left, stream);
        stream.println(visit(t));
        inorderPrintByKey(t.right, stream);
    }

    private void preorderPrintByFreq(TreeNode t) {
        if (t == null)
            return;

        WordFreq curr = visit(t);
        wordsByFreq[BST.i++] = curr;

        preorderPrintByFreq(t.left);
        preorderPrintByFreq(t.right);
    }


    private void modifySubTreeSize(String w, TreeNode t, int k) {
        TreeNode current = t;
        while (!equals(w, current)) {
            current.subtreeSize += k;
            if (less(w, current))
                current = current.left;
            else
                current = current.right;
        }
    }


    private void insertAtRoot(String w) {
        head = insertR(head, w);
    }

    // Insert at root (recursive)
    private TreeNode insertR(TreeNode t, String w) {
        if (t == null) {
            return new TreeNode(new WordFreq(w));
        }

        if (less(w, t)) {
            t.left = insertR(t.left, w);
            t = rotR(t);
        }
        else {
            t.right = insertR(t.right, w);
            t = rotL(t);
        }
        return t;
    }

    // RIGHT ROTATION
    private TreeNode rotR(TreeNode t) {
        TreeNode x = t.left;

        // fix t.subtreeSize
        t.subtreeSize = (t.right != null ? t.right.subtreeSize+1 : 0);
        t.subtreeSize += (x.right != null ? x.right.subtreeSize+1 : 0);

        t.left = x.right;

        // fix x.subtreeSize
        x.subtreeSize = t.subtreeSize+1;
        x.subtreeSize += (x.left != null ? x.left.subtreeSize+1 : 0);

        x.right = t;

        return x;
    }

    // LEFT ROTATION
    private TreeNode rotL(TreeNode t) {
        TreeNode x = t.right;

        // fix t.subtreeSize
        t.subtreeSize = (t.left != null ? t.left.subtreeSize+1 : 0);
        t.subtreeSize += (x.left != null ? x.left.subtreeSize+1 : 0);

        t.right = x.left;

        // fix x.subtreeSize
        x.subtreeSize = t.subtreeSize+1;
        x.subtreeSize += (x.right != null ? x.right.subtreeSize+1 : 0);

        x.left = t;

        return x;
    }
    /////////////////////////////////////////////




    public void insert(String w) {
        if (head == null) {
            head = new TreeNode(new WordFreq(w));
            modifySubTreeSize(w, head, 1);
            totalWords++;
            return;
        }

        TreeNode current = head;
        while (true) {

            if (equals(w, current)) {
                current.item.increaseFreq();
                totalWords++;
                return;
            }

            if (less(w, current)) {
                if (current.left == null) {
                    current.left = new TreeNode(new WordFreq(w));
                    modifySubTreeSize(w, head, 1);
                    totalWords++;
                    return;
                }
                else
                    current = current.left;
            }
            else {
                if (current.right == null) {
                    current.right = new TreeNode(new WordFreq(w));
                    modifySubTreeSize(w, head, 1);
                    totalWords++;
                    return;
                }
                else
                    current = current.right;
            }
        }
    }

    public WordFreq search(String w) {
        if (head == null)
            return null;

        TreeNode current = head;
        while (true) {
            if (current == null)
                return null;

            if (equals(w, current)) {
                if (current.item.getFreq() > getMeanFrequency()) {
                    save = new TreeNode(new WordFreq(current.item));

                    remove(w);
                    insertAtRoot(w);

                    totalWords += save.item.getFreq();
                    head.item.setFreq(save.item.getFreq());

                    return save.item;
                }

                return current.item;
            }

            if (less(w, current))
                current = current.left;
            else
                current = current.right;
        }
    }

    public void remove(String w) {
        // find node to delete and its parent
        TreeNode current = head;
        TreeNode parent = null;

        while (true) {
            if (current == null)
                return;

            if (equals(w, current))
                break;

            parent = current;

            if (less(w, current))
                current = current.left;
            else
                current = current.right;
        }

        modifySubTreeSize(current.item.key(), head, -1); //ancestors

        // node to replace with
        TreeNode replace = null;

        // only right
        if (current.left == null)
            replace = current.right;
        else if (current.right == null)
            replace = current.left;
        else {
            // find left most child of current right subtree!
            TreeNode findCurrent = current.right;
            TreeNode parentOf_findcurrent = current.right;
            while (true) {
                if (findCurrent.left != null) {
                    parentOf_findcurrent = findCurrent;
                    findCurrent = findCurrent.left;
                }else
                    break;
            }
            modifySubTreeSize(findCurrent.item.key(), current.right, -1); //FROM OLD ROOT'S RIGHT NODE TO NEW ROOT


            parentOf_findcurrent.left = findCurrent.right;


            findCurrent.left = current.left;
            findCurrent.right = current.right;

            replace = findCurrent;
        }
        // replace parents reference

        if (parent == null) { //root
            head = replace;
        } else {
            if (parent.left == current)
                parent.left = replace;

            if (parent.right == current)
                parent.right = replace;
        }
        if(replace!=null) {

            replace.subtreeSize = current.subtreeSize - 1;                        //NEW ROOT
        }
        totalWords = totalWords - current.item.getFreq();
        
    }

    public void load(String filename) {

        for (String w : stopWords) {
            remove(w);
        }

        try {
            FileReader fr = new FileReader(filename);
            BufferedReader buff = new BufferedReader(fr);

            StringTokenizer lineTokens;
            String token;
            String line;

            boolean eof = false;

            while (!eof) {
                line = buff.readLine();

                if (line == null) {
                    eof = true;
                } else {
                    lineTokens = new StringTokenizer(line);

                    while (lineTokens.hasMoreTokens()) {
                        token = lineTokens.nextToken();

                        if (token.matches(".*\\d.*"))
                            continue;

                        token = token.replaceAll("[^a-zA-Z']", "").toLowerCase();
                        if (token.length() == 0)
                            continue;

                        boolean found = stopWords.search(token);
                        if (!found)
                            insert(token);
                    }
                }
            }
        }
        catch (IOException ex) {
            System.err.println("\n\t\tError: file could not be parsed!\n");
        }

    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getDistinctWords() {
        return head.subtreeSize + 1;
    }

    public int getFrequency(String w) {
        WordFreq temp = search(w);

        if (temp == null)
            return 0;

        return temp.getFreq();
    }

    public WordFreq getMaximumFrequency() {
        WordFreq max = head.item;
        max = preorderMaxFreq(head, max);
        return max;
    }

    public double getMeanFrequency() {
        return (double) getTotalWords() / getDistinctWords();
    }

    public void addStopWord(String w) {
        stopWords.insertAtBack(w.toLowerCase());
    }

    public void removeStopWord(String w) {
        stopWords.remove(w.toLowerCase());
    }

    public void printTreeAlphabetically(PrintStream stream) {
        stream.println("----- Print tree alphabetically -----");
        inorderPrintByKey(head, stream);
    }

    static int i;
    public void printTreeByFrequency(PrintStream stream) {
        int N = getDistinctWords();
        wordsByFreq = new WordFreq[N+1];

        BST.i = 1;
        preorderPrintByFreq(head);

        Sort.heapsort(wordsByFreq);

        stream.println("----- Print tree by word frequency -----");
        for (int k = 1; k < wordsByFreq.length; k++) {
            stream.println(wordsByFreq[k]);
        }
        
        wordsByFreq = null;
    }
}
