package mymain;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class myMain {
    public static void main(String[] args) {
        //ABDGHCEIF
        //GDHBAEICF
        //GHDBIEFCA
        String pre = "ABDGHCEIF"; //前序遍历
        String mid = "GDHBAEICF"; //中序遍历
        String end = "GHDBIEFCA"; //后序遍历
        String level = "ABCDEFGHI"; //层序遍历

        Node defaultTree = buildDefaultTree();
        Node preMidTree = buildTreeByPreMid(pre.toCharArray(), 0, pre.length() - 1, mid.toCharArray(),
                0, mid.length() - 1);

        Node midEndTree = buildTreeByMidEnd(mid.toCharArray(), 0, mid.length() - 1, end.toCharArray(),
                0, end.length() - 1);

        Node midLevelTree = buildTreeByMidLevel(mid.toCharArray(), level.toCharArray(), 0, mid.length() - 1);

        System.out.println(defaultTree.equals(preMidTree));
        System.out.println(defaultTree.equals(midEndTree));
        System.out.println(defaultTree.equals(midLevelTree));

        System.out.println(midLevelTree.readLevel());
        System.out.println(midLevelTree.readMid());
    }

    /**
     * 根据层序遍历和中序遍历得到结果
     * @return
     */
    private static Node buildTreeByMidLevel(char[] mid, char[] level, int midBegin, int midEnd) {
        Node root = new Node(level[0] + "");

        int midLoc = -1;
        for (int i = midBegin; i <= midEnd; i++) {
            if (mid[i] == level[0]) {
                midLoc = i;
                break;
            }
        }

        if (level.length >= 2) {
            if (isLeft(mid, level[0], level[1])) {
                Node left = buildTreeByMidLevel(mid, getLevelStr(mid, midBegin, midLoc - 1, level), midBegin, midLoc - 1);
                root.leftChild = left;
                if (level.length >= 3 && !isLeft(mid, level[0], level[2])) {
                    Node right = buildTreeByMidLevel(mid, getLevelStr(mid, midLoc + 1, midEnd, level), midLoc + 1, midEnd);
                    root.rightChild = right;
                }
            } else {
                Node right = buildTreeByMidLevel(mid, getLevelStr(mid, midLoc + 1, midEnd, level), midLoc + 1, midEnd);
                root.rightChild = right;
            }
        }
        return root;
    }

    /**
     * 将中序序列中midBegin与MidEnd的字符依次从level中提取出来，保持level中的字符顺序不变
     */
    private static char[] getLevelStr(char[] mid, int midBegin, int midEnd, char[] level) {
        char[] result = new char[midEnd - midBegin + 1];
        int curLoc = 0;
        for (int i = 0; i < level.length; i++) {
            if (contains(mid, level[i], midBegin, midEnd)) {
                result[curLoc++] = level[i];
            }
        }
        return result;
    }

    /**
     * 如果str字符串的begin和end位置之间（包括begin和end）含有字符target,则返回true。
     */
    private static boolean contains(char[] str, char target, int begin, int end) {
        for (int i = begin; i <= end; i++) {
            if (str[i] == target) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果c在target的左边，则返回true，否则返回false
     */
    private static boolean isLeft(char[] str, char target, char c) {
        boolean findC = false;
        for (char temp : str) {
            if (temp == c) {
                findC = true;
            } else if (temp == target) {
                return findC;
            }
        }
        return false;
    }

    /**
     * 根据后序和中序遍历还原树
     */
    private static Node buildTreeByMidEnd(char[] mid, int midBegin, int midEnd, char[] end, int endBegin, int endEnd) {
        Node root = new Node();
        root.setName(end[endEnd] + "");
        int midRootLoc = 0;
        for (int i = midEnd; i >= midBegin; i--) {
            if (mid[i] == end[endEnd]) {
                midRootLoc = i;
                break;
            }
        }

        //还原左子树
        if (midRootLoc - 1 >= midBegin && (endBegin + (midRootLoc - midBegin) - 1 >= endBegin)) {
            Node leftChild = buildTreeByMidEnd(mid, midBegin, midRootLoc - 1, end, endBegin, endBegin + (midRootLoc - midBegin) - 1);
            root.leftChild = leftChild;
        }

        //还原右子树
        if (midEnd >= midRootLoc + 1 && (endEnd - 1 >= endEnd - (midEnd - midRootLoc))) {
            Node rightChild = buildTreeByMidEnd(mid, midRootLoc + 1, midEnd, end, endEnd - (midEnd - midRootLoc), endEnd - 1);
            root.rightChild = rightChild;
        }

        return root;
    }

    /**
     * 根据前序和中序排序表获取树
     */
    private static Node buildTreeByPreMid(char[] pre, int preBegin, int preEnd, char[] mid, int midBegin, int midEnd) {
        Node root = new Node();
        root.setName(pre[preBegin] + "");

        int midRootLoc = 0;
        for (int i = midBegin; i <= midEnd; i++) {
            if (mid[i] == pre[preBegin]) {
                midRootLoc = i;
                break;
            }
        }

        //递归得到左子树
        if (preBegin + (midRootLoc - midBegin) >= preBegin + 1 && (midRootLoc - 1) >= midBegin) {
            Node leftChild = buildTreeByPreMid(pre, preBegin + 1, preBegin + (midRootLoc - midBegin),
                    mid, midBegin, midRootLoc - 1);
            root.leftChild = leftChild;
        }

        //递归得到右子树
        if (preEnd >= (preEnd - (midEnd - midRootLoc) + 1) && (midEnd >= midRootLoc + 1)) {
            Node rightChild = buildTreeByPreMid(pre, preEnd - (midEnd - midRootLoc) + 1, preEnd,
                    mid, midRootLoc + 1, midEnd);
            root.rightChild = rightChild;
        }

        return root;
    }

    /**
     * 获取默认的树
     *
     * @return
     */
    private static Node buildDefaultTree() {
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");
        Node f = new Node("F");
        Node g = new Node("G");
        Node h = new Node("H");
        Node i = new Node("I");
        a.leftChild = b;
        a.rightChild = c;
        b.leftChild = d;
        d.leftChild = g;
        d.rightChild = h;
        c.leftChild = e;
        c.rightChild = f;
        e.rightChild = i;
        return a;
    }

}