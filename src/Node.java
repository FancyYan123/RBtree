/**
 * Node is the base component of Red-black Tree.
 * for node color: red is 1, black is 0;
 * for left endpoint, p is 1, for right one it's -1;
 * @author      Firstname Lastname <address @ example.com>
 */

public class Node {
    int color, p, val, maxval, key;
    Endpoint emax = null;
    Node parent = null;
    Node left = null;
    Node right = null;
    public Node(int color, int key, int p, Endpoint emax){
        this.color = color;
        this.key = key;
        this.p = p;
        this.val = this.maxval = this.p;
        this.emax = emax;
    }

    public Node getParent(){ return this.parent; }

    public Node getLeft() { return this.left; }

    public Node getRight() { return this.right; }

    public int getKey() { return this.key; }

    public int getP() { return this.p; }

    public int getVal() { return this.val; }

    public int getMaxVal() { return this.maxval; }

    public Endpoint getEndpoint() {
        return new Endpoint(this.key);
    }

    public Endpoint getEmax() {
        return this.emax;
    }

    public int getColor() {
        if(this.color==1)
            return 0;
        else
            return 1;
    }
}
