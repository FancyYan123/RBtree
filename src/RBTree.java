import java.util.*;

/**
 * NOTE: 5 principles for Red-black Tree
 * - 1. Every node are colored: red or black;
 * - 2. The root node is black;
 * - 3. Each leaf node is black;
 * - 4. If a node is red, its child node must be black;
 * - 5. All the paths from root to leaf contains the same amount of black nodes.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @version     2019.03.30
 * @since       1.8
 */

public class RBTree {
    Node root;
    int size = 0;

    //nil is an internal node which represents leaf node.
    private Node nil;

    Endpoint nil_emax = new Endpoint(Integer.MIN_VALUE);

    public RBTree(){
        nil = new Node(0, Integer.MIN_VALUE, 0, null);
        nil.left = nil.right = nil.parent = nil;
        nil.emax = nil_emax;
        root = nil;
    }

    public Node getRoot(){
        return root;
    }

    public Node getNILNode() {
        //return the nil node of RBtree.
        return this.nil;
    }

    public int getSize(){ return this.size; }

    public int getHeight() {
        return getSubHeight(root);
    }

    int getSubHeight(Node a){
        if(a==nil)
            return 1;
        else
            return 1+Math.max(getSubHeight(a.left), getSubHeight(a.right));
    }

    public void insert(Node z){
        this.size += 1;
        //first step: insert n as insert it to an binary search tree.
        //make sure the color of node is red.
        //TODO: set the initial maxval and emax of node z.
        assert(z.color==1 && z.val == z.p);
        assert(z.left == nil && z.right == nil);
        List<Node> path = new ArrayList<Node>();
        Node y = nil;
        Node x = root;
        while(x != nil){
            x.val += z.p;
            path.add(x);
            y = x;
            if (z.key<x.key || (z.key==x.key && z.p>x.p)) {
                x = x.left;
            }
            else{
                x = x.right;
            }
        }
        //path.add(z);
        z.parent = y;
        if(y == nil){
            root = z;
        }
        else if (z.key<y.key){
            y.left = z;
        }
        else{
            y.right = z;
        }

        //track back the path and renew maxval and emax:
        for(int i = path.size()-1; i>=0; i--){
            Node n = path.get(i);
            MaxSubNode newMax = calculateMaxValue(n);
            n.maxval= newMax.maxval;
            n.emax = newMax.emax;
        }

        z.left = nil; z.right = nil;

        insert_fixup(z);
    }

    public void insert_fixup(Node z){
        while(z.parent.color == 1){
            if(z.parent.parent.left == z.parent){
                Node y = z.parent.parent.right;
                if(y.color == 1){
                    //case 1: z's uncle is red
                    z.parent.color = 0;
                    y.color = 0;
                    z.parent.parent.color = 1;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.right) {
                        //case 2: change it to case 3
                        z = z.parent;
                        leftRotate(z);
                    }
                    //case 3:
                    z.parent.color = 0;
                    z.parent.parent.color = 1;
                    rightRotate(z.parent.parent);
                }
            }
            else{
                Node y = z.parent.parent.left;
                if(y.color == 1){
                    //case 1: z's uncle is red
                    z.parent.color = 0;
                    y.color = 0;
                    z.parent.parent.color = 1;
                    z = z.parent.parent;
                }
                else {
                    if (z == z.parent.left) {
                        //case 2: change it to case 3
                        z = z.parent;
                        rightRotate(z);
                    }
                    //case 3:
                    z.parent.color = 0;
                    z.parent.parent.color = 1;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = 0;
    }

    MaxSubNode calculateMaxValue(Node v){
        int val1 = v.left.maxval;
        int val2 = v.left.val+v.p;
        int val3 = v.left.val+v.p+v.right.maxval;
        if(val1>=val2 && val1>=val3)
            return new MaxSubNode(val1, v.left.emax);
        else if(val2>=val1 && val2>=val3)
            return new MaxSubNode(val2, v.getEndpoint());
        else
            return new MaxSubNode(val3, v.right.emax);
    }

    void leftRotate(Node x){
        Node y = x.right;
        assert (y!=nil);
        //renew val of nodes before rotating:
        int valTmp = x.val;
        x.val = x.left.val+y.left.val+x.p;
        y.val = valTmp;

        //do the left rotation.
        x.right = y.left;
        y.left.parent = x;
        y.parent = x.parent;
        if(this.root == x)
            root = y;
        else if(x == x.parent.left)
            x.parent.left = y;
        else
            x.parent.right = y;
        y.left = x;
        x.parent = y;

        //renew maxval and emax after rotating.
        MaxSubNode xMaxSubNode = calculateMaxValue(x);
        MaxSubNode yMaxSubNode = calculateMaxValue(y);
        x.maxval = xMaxSubNode.maxval; x.emax = xMaxSubNode.emax;
        y.maxval = yMaxSubNode.maxval; y.emax = yMaxSubNode.emax;
    }

    void rightRotate(Node y){
        Node x = y.left;
        assert(x!=nil);
        //renew val of nodes before rotating:
        int valTmp = y.val;
        y.val = x.right.val + y.right.val+y.p;
        x.val = valTmp;

        //do the right notation:
        y.left = x.right;
        x.right.parent = y;
        x.parent = y.parent;
        if(y==root)
            root = x;
        else if(y == y.parent.right)
            y.parent.right = x;
        else
            y.parent.left = x;
        x.right = y;
        y.parent = x;

        //renew maxval and emax after rotating.
        MaxSubNode xMaxSubNode = calculateMaxValue(x);
        MaxSubNode yMaxSubNode = calculateMaxValue(y);
        x.maxval = xMaxSubNode.maxval; x.emax = xMaxSubNode.emax;
        y.maxval = yMaxSubNode.maxval; y.emax = yMaxSubNode.emax;
    }
}

class MaxSubNode{
    int maxval;
    Endpoint emax;
    public MaxSubNode(int maxval, Endpoint emax){
        this.maxval = maxval;
        this.emax = emax;
    }
}
