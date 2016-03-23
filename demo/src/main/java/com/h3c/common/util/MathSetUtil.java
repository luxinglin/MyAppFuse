/***********************************************
 * 文件名：MathSetUtil.java
 * 描述：
 * 创建时间：2015年10月18日
 ************************************************/
package com.h3c.common.util;

import java.util.HashSet;
import java.util.Set;

/**
 * <Description> 集合运算，交集、并集、差集<br>
 *
 * @author Lu, Xing-Lin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年10月18日 <br>
 */
public class MathSetUtil {
    public static void main(String[] args) {

    }

    /**
     * Description: 求两个集合的交集,都含有的元素<br>
     *
     * @param set1 集合1
     * @param set2 集合2
     * @return 集合1、2的交集<br>
     * @author Lu, Xing-Lin<br>
     * @taskId <br>
     */
    public static Set getIntersectionResult(Set set1, Set set2) {
        Set<String> result = null;
        if (set1 == null || set2 == null)
            return result;

        result = new HashSet();
        result.addAll(set1);
        result.retainAll(set2);

        return result;

    }

    /**
     * Description: 求两个集合的并集,所有的元素<br>
     *
     * @param set1 集合1
     * @param set2 集合2
     * @return 集合1、2的并集<br>
     * @author Lu, Xing-Lin<br>
     * @taskId <br>
     */
    public static Set getUnionResult(Set set1, Set set2) {
        Set result = null;
        if (set1 == null)
            return set2;
        if (set2 == null)
            return set1;

        result = new HashSet();
        result.addAll(set1);
        result.addAll(set2);

        return result;

    }

    /**
     * Description: 求两个集合的差集,集合1中元素减去集合2中元素<br>
     *
     * @param set1 集合1
     * @param set2 集合2
     * @return 集合1-[除去]集合2的结果<br>
     * @author Lu, Xing-Lin<br>
     * @taskId <br>
     */
    public static Set getDifferResult(Set set1, Set set2) {
        Set result = null;
        if (set1 == null)
            return result;
        if (set2 == null)
            return set1;

        result = new HashSet();
        result.addAll(set1);
        result.removeAll(set2);

        return result;

    }

}