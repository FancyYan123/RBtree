import java.util.*;
import java.io.File;

/**
 * @author :
 */
public class Intervals {
    RBTree T;
    Integer countID;
    Map<Integer, Interval> allIntervals;

    public Intervals(){
        T = new RBTree();
        countID = 0;
        this.allIntervals = new HashMap<Integer, Interval>();
    }

    public void intervalInsert(int a, int b){
        //Node(int color, int key, int p, Endpoint emax)
        Node A = new Node(1, a, 1, new Endpoint(a));
        Node B = new Node(1, b, -1, new Endpoint(b));
        A.val = 1; B.val = -1;
        A.left = A.right = T.getNILNode();
        B.left = B.right = T.getNILNode();

        T.insert(A);
        T.insert(B);

        Interval I = new Interval(a, b);
        allIntervals.put(countID, I);
        countID += 1;
    }

    public boolean intervalDelete(int intervalID){
        return false;
    }

    public int findPOM(){
        return T.root.getEmax().getValue();
    }

    public RBTree getRBTree(){
         return T;
    }

    /*
    public static void main(String[] args){
        Intervals all = new Intervals();
//        all.intervalInsert(0, 4);
//        all.intervalInsert(1, 6);
//        all.intervalInsert(3, 9);
//        all.intervalInsert(7, 11);
        all.intervalInsert(0, 3);
        all.intervalInsert(1, 5);
        all.intervalInsert(2, 5);
        all.intervalInsert(4, 8);
        all.intervalInsert(4, 10);
        all.intervalInsert(5, 8);
        all.intervalInsert(7,  10);
        System.out.println(all.findPOM());
        System.out.println(all.getRBTree().getHeight());
    }*/
    public static void main(String[] args) {
        Scanner sc = null;
        try{
            Intervals all = new Intervals();
            sc = new Scanner(new File("C:\\tmp\\res\\small_5.txt"));
            int ans = Integer.parseInt(sc.next());
            while(sc.hasNext()){
                int start = Integer.parseInt(sc.next());
                int end = Integer.parseInt(sc.next());
                all.intervalInsert(start, end);
            }
            System.out.println(all.findPOM());
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}

class Interval{
    int left;
    int right;
    public Interval(int left, int right){
        this.left = left;
        this.right = right;
    }
}
