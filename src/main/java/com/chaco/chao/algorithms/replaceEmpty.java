package com.chaco.chao.algorithms;

/**
 * author:zhaopeiyan001
 * Date:2020-04-16 17:11
 */
public class replaceEmpty {
    /**
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。
     * 例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     */
    public static String replaceSpace(StringBuffer str) {
        if (null == str || str.equals("")) {
            return str.toString();
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            if (' ' == str.charAt(i)) {
                sb.append("%20");
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("We are happy.");
        String s = replaceSpace(sb);
        System.out.println(s);
    }
}
