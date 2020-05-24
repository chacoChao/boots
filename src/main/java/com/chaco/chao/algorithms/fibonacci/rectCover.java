package com.chaco.chao.algorithms.fibonacci;

import org.junit.jupiter.api.Test;

/**
 * author:zhaopeiyan001
 * Date:2020-04-22 11:39
 */
public class rectCover {
    /**
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     *
     * 比如n=3时，2*3的矩形块有3种覆盖方法：
     */
    /**
     * 【1】根据前几项1,2,3,5,8,13，……推出公式：f(n)=f(n-1)+f(n-2),n>=3 ; f(n)=n,n<3
     *
     * 【2】画图推导公式
     *  https://blog.csdn.net/feng_zhiyu/article/details/82086059?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-5
     */
    public int RectCover(int target) {
        if (target < 3) {
            return target;
        }
        return RectCover(target - 1) + RectCover(target - 2);
    }


    @Test
    public void testRectCover() {
        int i = this.RectCover(19);
        System.out.println(i);
    }
}
