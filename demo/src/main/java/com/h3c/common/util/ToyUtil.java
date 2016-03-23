/***********************************************
 * 文件名：ToyUtil.java
 * 描述：
 * 创建时间：2015年9月28日
 ************************************************/
package com.h3c.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <Description> 常用工具类<br>
 *
 * @author Lu, Xing-Lin<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年10月12日 <br>
 */
public class ToyUtil {
    private final static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd");

    /**
     * 静态方法测试
     *
     * @param args
     */
    public static void main(String[] args) {
    }

    /**
     * Description:将字符串准换为字符串集合 <br>
     * 将字符串按照","拆分，加入到字符串集合中返回<br>
     * 如果字符串为null或者空，返回null<br>
     *
     * @param inStr 输入字符串
     * @return <br>
     * @author Lu, Xing-Lin<br>
     * @taskId <br>
     */
    public static Set<String> convertStr2Set(String inStr) {
        final String delimeter = ",";
        return convertStr2Set(delimeter, inStr);
    }

    /**
     * Description:将特定字符分隔的字符串信息转换为字符串集合 <br>
     *
     * @param delimeter 分隔符
     * @param inStr     输入字符串
     * @return <br>
     * @author Lu, Xing-Lin<br>
     * @taskId <br>
     */
    public static Set<String> convertStr2Set(final String delimeter,
                                             String inStr) {
        Set<String> set = null;
        if (inStr == null)
            return set;

        set = new HashSet<String>();
        if (inStr.trim().isEmpty())
            return set;

        String[] inStrArr = inStr.split(delimeter);
        for (String str : inStrArr) {
            if (ToyUtil.stringNullOrEmpty(str))
                continue;

            set.add(str);
        }
        return set;
    }

    /**
     * Description: 判断字符串是否为null或者纯空格字符串，<br>
     * 如果不为null且不全为空格字符串，返回true；否则返回false<br>
     *
     * @param testStr 待检测字符串
     * @return <br>
     * @author Lu, Xing-Lin<br>
     * @taskId <br>
     */
    public static boolean stringNotEmpty(String testStr) {
        return testStr != null && !testStr.trim().isEmpty();
    }

    /**
     * Description: 判断字符串是否为null或者纯空格字符串，<br>
     * 如果为null或者全为空格字符串，返回true；否则返回false<br>
     *
     * @param testStr 待检测字符串
     * @return <br>
     * @author Lu, Xing-Lin<br>
     * @taskId <br>
     */
    public static boolean stringNullOrEmpty(String testStr) {
        return !stringNotEmpty(testStr);
    }

    /**
     * 字符串压缩
     *
     * @param primStr
     * @return
     */
    public static String gzip(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new sun.misc.BASE64Encoder().encode(out.toByteArray());
    }

    /**
     * 字符串解压缩
     *
     * @param compressedStr
     * @return
     */
    public static String gunzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        GZIPInputStream ginzip = null;
        byte[] compressed = null;
        String decompressed = null;
        try {
            compressed = new sun.misc.BASE64Decoder()
                    .decodeBuffer(compressedStr);
            in = new ByteArrayInputStream(compressed);
            ginzip = new GZIPInputStream(in);

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            decompressed = out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ginzip != null) {
                try {
                    ginzip.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }

        return decompressed;
    }

    /**
     * 校验源字符串是否包含特定字符串
     *
     * @param sourceString 原字符串
     * @param checkString  待检验字符串
     * @return 保护的话返回true，否则返回false
     */
    public static boolean hasSubString(String sourceString, String checkString) {
        return hasSubString(sourceString, checkString, ",");
    }

    public static boolean hasSubString(String sourceString, String checkString,
                                       String delim) {
        if (sourceString == null) {
            return false;
        }

        if (stringNullOrEmpty(delim)) {
            delim = ",";
        }
        // 替换字符串中所有空格串
        sourceString = sourceString.replaceAll("\\s*", "");

        String regEx = "(^" + checkString + delim + ")|(" + delim + checkString
                + delim + ")|(" + delim + checkString + "$)|(^" + checkString
                + "$)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(sourceString);
        boolean result = m.find();
        return result;
    }

    public static String toStr(Object o) {
        return o == null ? "" : o.toString();
    }

    public static Integer toInt(Object o) {
        return o == null || o.equals("") ? null : Integer
                .parseInt(o.toString());
    }

    public static Long toLong(Object o) {
        return o == null || o.equals("") ? null : Long.parseLong(o.toString());
    }

    public static Double toDouble(Object o) {
        return o == null || o.equals("") ? null : Double.parseDouble(o
                .toString());
    }

    public static BigDecimal toBigDecimal(Object o) {
        return o == null || o.equals("") ? null : BigDecimal.valueOf(Double
                .parseDouble(o.toString()));
    }

    public static Date toDate(Object o) throws ParseException {
        return o == null || o.equals("") ? null : new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss").parse(o.toString());
    }

    public static Date toDate(Object o, SimpleDateFormat sdf)
            throws ParseException {
        return o == null || o.equals("") ? null : sdf.parse(o.toString());
    }

    public static String getNowDateStr() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String toDateStr(Object o) throws ParseException {
        return o == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format((Date) o);
    }

    public static String toDateStr(Object o, SimpleDateFormat sdf)
            throws ParseException {
        return o == null ? null : sdf.format((Date) o);
    }

    public static String getStr(Map<String, Object> map, Object key) {
        Object value = null;

        if (key != null && map.containsKey(key.toString()))
            value = map.get(key.toString());

        return value == null ? "" : value.toString();
    }

    public static Date getStartDateTimeOf(Date date) throws ParseException {
        if (date == null)
            return null;

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        return ToyUtil.toDate(year + "-" + (month + 1) + "-" + day
                + " 00:00:00");
    }

    public static Date getEndDateTimeOf(Date date) throws ParseException {
        if (date == null)
            return null;

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        return ToyUtil.toDate(year + "-" + (month + 1) + "-" + day
                + " 23:59:59");
    }

    public static Date getWorkDate(Calendar c, int days, List<String> swlDays,
                                   List<String> swkDays) {
        if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || c
                .get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            if (swkDays != null) {
                if (!swkDays.contains(sdf.format(c.getTime()))) {
                    days--;
                }
            }
        }
        return getNextNdaysWorkdayFromNow(c, days, swlDays, swkDays);
    }

    /**
     * 功能描述
     *
     * @param beginStr
     * @param endStr
     * @param delimeter
     * @return String
     * @version Revision: 2014-10-23
     * @Company name: Hewlett-Packard Company
     * @author <a href="mailto:xinglin.lu@hp.com">Lu, Xing-Lin</a>
     */
    public static String mergeStrWithoutDuplicate(String beginStr,
                                                  String endStr, String delimeter) {
        if (isNullOrEmpty(beginStr))
            return endStr;

        if (isNullOrEmpty(endStr))
            return beginStr;

        if (isNullOrEmpty(delimeter))
            return beginStr + endStr;

        StringBuffer sb = new StringBuffer();
        String[] beginStrArr = beginStr.split(delimeter);
        String[] endStrArr = endStr.split(delimeter);

        Set<String> strSet = new HashSet<String>();
        for (String begin : beginStrArr) {
            if (!strSet.contains(begin)) {
                if (!sb.toString().trim().isEmpty()) {
                    sb.append(delimeter);
                }
                sb.append(begin);
                strSet.add(begin);
            }
        }

        for (String end : endStrArr) {
            if (!strSet.contains(end)) {
                if (!sb.toString().trim().isEmpty()) {
                    sb.append(delimeter);
                }
                sb.append(end);
                strSet.add(end);
            }
        }

        return sb.toString();
    }

    /**
     * 判断字符串是否为null或者仅仅包含空格字符串
     *
     * @param str 待校验字符串
     * @return boolean 为null或者仅包含空格串时返回true；否则返回false
     * @version Revision: 2014-10-23
     * @Company name: Hewlett-Packard Company
     * @author <a href="mailto:xinglin.lu@hp.com">Lu, Xing-Lin</a>
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 获取当前日期后n天的工作日时间
     *
     * @param c       当前时间
     * @param days    间隔天数
     * @param swlDays
     * @param swkDays
     * @return
     */
    private static Date getNextNdaysWorkdayFromNow(Calendar c, int days,
                                                   List<String> swlDays, List<String> swkDays) {

        if (days >= 0) {
            if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || c
                    .get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
                if (swkDays != null) {
                    if (!swkDays.contains(sdf.format(c.getTime()))) {
                        days++;
                    }
                } else {
                    days++;
                }
            } else {
                if (swlDays != null
                        && swlDays.contains(sdf.format(c.getTime()))) {
                    days++;
                }
            }
            c.add(Calendar.DATE, 1);
            return getNextNdaysWorkdayFromNow(c, days - 1, swlDays, swkDays);
        } else {
            c.add(Calendar.DATE, -1);
            return c.getTime();
        }
    }
}
