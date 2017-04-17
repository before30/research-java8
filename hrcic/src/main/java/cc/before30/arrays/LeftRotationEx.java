package cc.before30.arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by before30 on 17/04/2017.
 */
public class LeftRotationEx {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        int size = s.nextInt();
        int nRotate = s.nextInt();

        List<Integer> list = new ArrayList<>();
        for (int i=0; i<size; i++) {
            list.add(s.nextInt());
        }

        List<Integer> heads = list.subList(0, nRotate%size);
        List<Integer> tail = list.subList(nRotate%size+1, list.size());
        tail.addAll(heads);
        for (int i=0; i<size; i++) {
            System.out.print(tail.get(i) + " ");
        }
//        List<Integer> result = nRoatate(list, nRotate%size);
//        for (int i=0; i<result.size(); i++) {
//            System.out.print(result.get(i) + " ");
//        }

    }

    public static List<Integer> nRoatate(List<Integer> list, int n) {
        for (int i=0; i<n; i++) {
            list = rotate(list);
        }

        return list;
    }

    public static List<Integer> rotate(List<Integer> list) {
        List<Integer> result = new ArrayList<Integer>();
        for (int i=1; i<list.size(); i++) {
            result.add(list.get(i));
        }

        result.add(list.get(0));
        return result;
    }
}
