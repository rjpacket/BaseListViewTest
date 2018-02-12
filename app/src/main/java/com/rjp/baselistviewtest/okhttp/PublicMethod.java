package com.rjp.baselistviewtest.okhttp;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;


/**
 * 共用方法类
 */

public class PublicMethod {


    /**
     * 格式化时间
     *
     * @param longTime
     *
     * @return
     */
    public static String formatDate(long longTime) {
        SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format_date.format(longTime);
    }

    /**
     * 保留两位小数
     *
     * @param num
     *
     * @return
     */
    public static String formatStringToTwoPoint(double num) {
        DecimalFormat df1 = new DecimalFormat("###0.00");
        String result = df1.format(num);
        return result;
    }


    /**
     * 从数组sourceInt中取出num个组合：规定前num-1为，最后一位去子组合的分组和，然后将固定值和子组合合并 如：{1,2,3}取2位组合：
     * 第1步：固定第1为1，取子组合{2，3}的2-1位
     * 第2步：2-1=1位，无需固定为，则划分子组合为一位子组合{{2}{3}}；否则继续固定第n为，去子组合m-n为；
     * 第3步：将一位子组合于前一位固定值合并，为{2,1}{3,1}；若还有固定值，将合并组合和前固定值组合，至组合完毕
     *
     * @param sourceInt 源数组
     * @param num       子组合大小
     *
     * @return 子组合数据
     */
    public static int[][] getGamesZhuHe(int[] sourceInt, int num) {
        if (num <= sourceInt.length) {
            // 计算结果组合的大小，更初始化结果组合
            long resultSize = zuHe(sourceInt.length, num);
            int[][] resultInts = new int[(int) resultSize][];

            // 从第fixed_i开始固定，去子组合的num-1位组合。
            // 如{1,2,3,4}中去2位组合，则先固定第1位1，然后去{2,3,4}的(2-1)位子组合
            for (int fixed_i = 0; fixed_i <= sourceInt.length - num; fixed_i++) {
                // 如果取1位子组合，递归返回。
                // 如去{2,3,4}一位组合，则返回{{2},{3},{4}}
                if (num == 1) {
                    for (int source_i = 0; source_i < sourceInt.length; source_i++) {
                        int[] subInt = new int[num];
                        subInt[0] = sourceInt[source_i];
                        resultInts[source_i] = subInt;
                    }
                } else {
                    // 取第fixed_i位为固定值。
                    // 如第1次循环，固定值为1...依次类推
                    int fixedInt = sourceInt[fixed_i];

                    // 获取固定值后的子组合。
                    // 第1次循环，子组合为{2,3,4}..依次类推
                    int[] nextInt = subIntByIndex(sourceInt, fixed_i);

                    // 递归获取{2,3,4}组合中的2-1位子组合集合
                    // 第一次返回{{2}，{3}，{4}}...依次类推
                    int[][] tempInts = getGamesZhuHe(nextInt, num - 1);

                    // 将返回的子组合与固定值组合
                    // 如第一次返回{{2},{3},{4}}与固定值1组合{{2,1},{3,1},{4,1}}...依次类推
                    for (int temp_i = 0; temp_i < tempInts.length; temp_i++) {
                        int[] tempInt = new int[tempInts[temp_i].length + 1];
                        tempInt = addInt(tempInt, tempInts[temp_i]);
                        tempInt[tempInt.length - 1] = fixedInt;
                        // 将子组合集合加入结果组合中
                        int size = getIntsSize(resultInts);
                        resultInts[size] = tempInt;
                    }

                }
            }
            return resultInts;
        } else {
            System.out.println("num error!!!");
            return null;
        }
    }

    /**
     * 截取数组的子数组，从start位至末尾
     *
     * @param sourceInt 源数组
     * @param start     起始位
     *
     * @return
     */
    private static int[] subIntByIndex(int[] sourceInt, int start) {
        int[] resultInt = new int[sourceInt.length - start - 1];

        for (int i = start + 1, j = 0; i < sourceInt.length; i++, j++) {
            resultInt[j] = sourceInt[i];
        }

        return resultInt;
    }


    /**
     * 将sourceInt数组，添加到destinationInt数组中，从第一位开始填充
     *
     * @param resultInt 结果数组
     * @param sourceInt 源数组
     *
     * @return 结果数组
     */
    private static int[] addInt(int[] resultInt, int[] sourceInt) {
        for (int source_i = 0; source_i < sourceInt.length; source_i++) {
            resultInt[source_i] = sourceInt[source_i];
        }

        return resultInt;
    }

    /**
     * 获取数组的大小
     *
     * @param resultInts 数组对象
     *
     * @return 数组大小
     */
    private static int getIntsSize(int[][] resultInts) {
        int size = 0;

        for (int i = 0; i < resultInts.length; i++) {
            if (resultInts[i] != null) {
                size++;
            }
        }
        return size;
    }


    /**
     * 求a取b的组合数
     */
    private static long zuHe(int a, int b) {
        long up = 1;
        for (int up_i = 0; up_i < b; up_i++) {
            up = up * a;
            a--;
        }

        long down = jieCheng(b);
        return up / down;
    }


    /**
     * 求b的阶乘
     */
    private static long jieCheng(int b) {
        long result = 0;

        if (b == 1 || b == 0) {
            result = b;
        } else {
            result = b * jieCheng(b - 1);
        }

        return result;
    }


    /**
     * 计算任九胆投注数：注数=（胆投数组元素1*元素2...）*（（非胆投数组1元素1*元素2...）+ （非胆投数组2元素1*元素2）+...）
     *
     * @param danGames    胆投场次数组
     * @param notDanGames 非胆投场次组合数组
     *
     * @return
     */
    public static int caculateZhuShu(int[] danGames, int[][] notDanGames) {
        int betNums = 0;
        // 计算胆部组合数
        int danBetNums = 1;
        if (danGames != null) {
            for (int dan_i = 0; dan_i < danGames.length; dan_i++) {
                danBetNums = danBetNums * danGames[dan_i];
            }
        }
        // 计算非胆部组合数
        int noDanBetNums = 0;
        if (notDanGames != null) {
            for (int nodan_i = 0; nodan_i < notDanGames.length; nodan_i++) {
                int[] notDanGame = notDanGames[nodan_i];
                int noDanBetNum = 1;

                for (int nodan_j = 0; nodan_j < notDanGame.length; nodan_j++) {
                    // System.out.println("~~~~~~~~~~~~~~~~~i=" + nodan_i + "***j="
                    // + nodan_j + "&&&&" + notDanGame[nodan_j]);
                    noDanBetNum = noDanBetNum * notDanGame[nodan_j];
                }

                noDanBetNums = noDanBetNums + noDanBetNum;
            }
        }


        // 返回注数
        return danBetNums * noDanBetNums;
    }
    //


    /**
     * gzip
     *
     * @param data
     *
     * @return byte[]
     */
    public static byte[] decompress2(byte[] data) {
        byte[] output = new byte[0];


        Inflater decompresser = new Inflater();
        ByteArrayOutputStream o = null;
        try {
            decompresser.reset();
            decompresser.setInput(data);

            o = new ByteArrayOutputStream(data.length);

            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                if (o != null) {
                    o.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        decompresser.end();
        return output;
    }

    //检查是否11位电话号码
    public static boolean isphonenum(String phonenum) {
        Pattern p = Pattern.compile("^\\d{11}");
        Matcher m = p.matcher(phonenum);
        return m.matches();
    }

    /**
     * 返回总注数竞彩过关投注计算注数
     *
     * @param betcodes
     * @param select
     *
     * @return
     */
    public static int getAllAmt(List<String> betcodes, int select) {
        // 初始化原始数据
        int[] a = new int[betcodes.size()];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        // 接收数据
        int[] b = new int[select];

        List<int[]> list = new ArrayList<int[]>();

        // 进行组合
        combine(a, a.length, select, b, select, list);

        // 返回数据对象
        int resultInt = 0;
        for (int[] result : list) {
            int itemNum = 1;
            for (int p : result) {
                itemNum *= Integer.parseInt(betcodes.get(p));
            }
            resultInt += itemNum;
        }

        return resultInt;
    }

    /**
     * 组合的递归算法
     *
     * @param a    原始数据
     * @param n    原始数据个数
     * @param m    选择数据个数
     * @param b    存放被选择的数据
     * @param M    常量，选择数据个数
     * @param list 存放计算结果
     */
    public static void combine(int a[], int n, int m, int b[], final int M, List<int[]> list) {
        for (int i = n; i >= m; i--) {
            b[m - 1] = i - 1;
            if (m > 1)
                combine(a, i - 1, m - 1, b, M, list);
            else {
                int[] result = new int[M];
                for (int j = M - 1; j >= 0; j--) {
                    result[j] = a[b[j]];
                }
                list.add(result);
            }
        }
    }

    /**
     * 数组排序
     *
     * @param t
     *
     * @return
     */
    public static int[] sort(int t[]) {
        int t_s[] = t;
        int temp;
        for (int i = 0; i < t_s.length; i++) {
            for (int j = i + 1; j < t_s.length; j++) {
                if (t_s[i] > t_s[j]) {
                    temp = t_s[i];
                    t_s[i] = t_s[j];
                    t_s[j] = temp;
                }
            }
        }
        return t_s;
    }

    public static void setTextColor(TextView text, String content, int start, int end, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(content);
        builder.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_COMPOSING);
        text.setText(builder, TextView.BufferType.EDITABLE);
    }

    /**
     * 将1转换成01
     *
     * @param time
     *
     * @return
     */
    public static String isTen(int time) {
        String timeStr = "";
        if (time < 10) {
            timeStr += "0" + time;
        } else {
            timeStr += time;
        }
        return timeStr;
    }
}
