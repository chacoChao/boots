package com.chaco.chao.algorithms;

import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * author:zhaopeiyan001
 * Date:2020-04-17 15:04
 */
public class reConstructBinaryTree {
    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
     * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，
     * 则重建二叉树并返回。
     */
    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        TreeNode root = reConstructBinaryTree(pre, 0, pre.length - 1, in, 0, in.length - 1);
        return root;
    }

    private TreeNode reConstructBinaryTree(int[] pre, int startPre, int endPre, int[] in, int startIn, int endIn) {

        if (startPre > endPre || startIn > endIn) {
            return null;
        }
        TreeNode root = new TreeNode(pre[startPre]);

        for (int i = startIn; i <= endIn; i++) {
            if (in[i] == pre[startPre]) {
                root.left = reConstructBinaryTree(pre, startPre + 1, startPre + i - startIn, in, startIn, i - 1);
                root.right = reConstructBinaryTree(pre, i - startIn + startPre + 1, endPre, in, i + 1, endIn);
                break;
            }
        }
        return root;
    }

    public TreeNode reConstructBinaryTree1(int[] pre, int[] in) {
        //数组长度为0的时候要处理
        if (pre.length == 0) {
            return null;
        }

        int rootVal = pre[0];

        //数组长度仅为1的时候就要处理
        if (pre.length == 1) {
            return new TreeNode(rootVal);
        }

        //我们先找到root所在的位置，确定好前序和中序中左子树和右子树序列的范围
        TreeNode root = new TreeNode(rootVal);
        int rootIndex = 0;
        for (int i = 0; i < in.length; i++) {
            if (rootVal == in[i]) {
                rootIndex = i;
                break;
            }
        }
        //递归，假设root的左右子树都已经构建完毕，那么只要将左右子树安到root左右即可
        //这里注意Arrays.copyOfRange(int[],start,end)是[)的区间
        root.left = reConstructBinaryTree1(Arrays.copyOfRange(pre, 1, rootIndex + 1), Arrays.copyOfRange(in, 0, rootIndex));
        root.right = reConstructBinaryTree1(Arrays.copyOfRange(pre, rootIndex + 1, pre.length), Arrays.copyOfRange(in, rootIndex + 1, in.length));
        return root;
    }

    public TreeNode reConstructBinaryTree2(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        TreeNode node = new TreeNode(pre[0]);
        for (int i = 0; i < in.length; i++) {
            if (pre[0] == in[i]) {
                node.left = reConstructBinaryTree2(Arrays.copyOfRange(pre, 1, i + 1), Arrays.copyOfRange(in, 0, i));
                node.right = reConstructBinaryTree2(Arrays.copyOfRange(pre, i + 1, pre.length), Arrays.copyOfRange(in, i + 1, in.length));
            }
        }
        return node;
    }

    @Data
    @ToString
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    @Test
    public void reTree() {
        int[] preNode = {1,2,4,7,3,5,6,8};
        int[] midNode = {4,7,2,1,5,3,8,6};
//        TreeNode treeNode = this.reConstructBinaryTree2(preNode, midNode);
        TreeNode treeNode = this.reConstructBinaryTree(preNode, midNode);
        System.out.println("=======treeNode:" + treeNode.toString());
    }
}
