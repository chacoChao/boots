package com.chaco.chao.algorithms.tree;

import org.junit.jupiter.api.Test;

/**
 * author:zhaopeiyan001
 * Date:2020-04-30 17:49
 */
public class Mirror {
    /**
     * 操作给定的二叉树，将其变换为源二叉树的镜像。
     */
    public void Mirror(hasSubTree.TreeNode root) {
        if (null == root) {
            return;
        }
        if (root.left != null) {
            Mirror(root.left);
        }
        if (root.right != null) {
            Mirror(root.right);
        }
        hasSubTree.TreeNode tmp;
        tmp = root.left;
        root.left = root.right;
        root.right = tmp;
    }

    @Test
    public void test() {
        hasSubTree.TreeNode root = new hasSubTree.TreeNode(2);
        hasSubTree.TreeNode left = new hasSubTree.TreeNode(1);
        hasSubTree.TreeNode right = new hasSubTree.TreeNode(3);
        root.left = left;
        root.right = right;
        this.Mirror(root);
        System.out.println(root.toString());
    }
}
