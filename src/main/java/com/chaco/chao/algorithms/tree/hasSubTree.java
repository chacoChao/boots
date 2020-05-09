package com.chaco.chao.algorithms.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;

/**
 * author:zhaopeiyan001
 * Date:2020-04-29 20:00
 */
public class hasSubTree {
    /**
     * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
     *
     * @param root1
     * @param root2
     * @return
     */
    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root1 == null || root2 == null) {
            return false;
        }
        return DoesHasSonTree(root1, root2) || HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
    }

    private Boolean DoesHasSonTree(TreeNode root1, TreeNode root2) {
        if (root2 == null) {
            return true;
        }
        if (root1 == null) {
            return false;
        }
        return (root1.val == root2.val) && DoesHasSonTree(root1.left, root2.left) && DoesHasSonTree(root1.right, root2.right);
    }

    @Test
    public void test() {
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);

        TreeNode treeNode1 = new TreeNode(2);
        treeNode.left = new TreeNode(3);
        boolean b = this.HasSubtree(treeNode, treeNode1);
        boolean b1 = false || true || false;
        System.out.println(b1);
        System.out.println("========" + b);
    }

    @Data
    @AllArgsConstructor
    @ToString
    static
    class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }
    }
}
