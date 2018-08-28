package mymain;

import java.util.LinkedList;
import java.util.Queue;

public class Node {
    private String name = "";
    public Node leftChild;
    public Node rightChild;

    public Node(String name) {
        this.name = name;
    }

    public Node() {
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 层序遍历
     */

    public String readLevel() {
        Queue<Node> queue = new LinkedList<>();
        StringBuilder result = new StringBuilder();
        queue.offer(this);
        while (!queue.isEmpty()) {
            Node curNode = queue.poll();
            result.append(curNode.name);
            if (curNode.leftChild != null) {
                queue.offer(curNode.leftChild);
            }
            if (curNode.rightChild != null) {
                queue.offer(curNode.rightChild);
            }
        }
        return result.toString();
    }

    /**
     * 前序遍历
     */
    public String readPre() {
        StringBuilder result = new StringBuilder();
        result.append(name); //前序遍历
        if (leftChild != null) {
            result.append(leftChild.readPre());
        }
        if (rightChild != null) {
            result.append(rightChild.readPre());
        }
        return result.toString();
    }

    /**
     * 中序遍历
     */
    public String readMid() {
        StringBuilder result = new StringBuilder();
        if (leftChild != null) {
            result.append(leftChild.readMid());
        }
        result.append(name); //中序遍历
        if (rightChild != null) {
            result.append(rightChild.readMid());
        }
        return result.toString();
    }

    /**
     * 后序遍历
     */
    public String readEnd() {
        StringBuilder result = new StringBuilder();
        if (leftChild != null) {
            result.append(leftChild.readEnd());
        }
        if (rightChild != null) {
            result.append(rightChild.readEnd());
        }
        result.append(name); //后序遍历
        return result.toString();
    }

    @Override
    public boolean equals(Object parm) {
        if (parm == null || !(parm instanceof Node)) {
            return false;
        }

        Node obj = (Node) parm;
        if (this.name == obj.name || this.name.equals(obj.name)) {
            if ((this.leftChild == null && obj.leftChild == null) || this.leftChild.equals(obj.leftChild)) {
                if ((this.rightChild == null && obj.rightChild == null) || this.rightChild.equals(obj.rightChild)) {
                    return true;
                }
            }
        }
        return false;
    }

}