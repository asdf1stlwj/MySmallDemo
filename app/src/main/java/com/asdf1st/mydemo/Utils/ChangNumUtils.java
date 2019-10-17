package com.asdf1st.mydemo.Utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author xubingyan
 * @time 2017/8/7  11:34
 * @desc ${TODD}
 */
public class ChangNumUtils {
    public static int bytes2Int(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = ((int) b[i]) & 0xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }

    public static long bytes2Long(byte[] byteNum) {
        long sum = 0;
        int end = byteNum.length;
        int len = byteNum.length;
        for (int ix = 0; ix < end; ++ix) {
            long num = ((long) byteNum[ix]) & 0xff;
            num <<= (--len) * 8;
            sum += num;
        }
        return sum;
    }

    public static int bytes2IntSmallEnd(byte[] b, int start, int len) {
        int sum = 0;
        int end = start + len;
        for (int i = start; i < end; i++) {
            int n = ((int) b[i]) & 0xff;
            n <<= (i - start) * 8;
            sum += n;
        }
        return sum;
    }

    public static int changeBytesToInt(byte[] buffer) {
        int i1, i2, sum = 0;
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] < 0) {
                i1 = buffer[i] & 0xff;
                i2 = i1 << i * 8;
            } else {
                i1 = buffer[i];
                i2 = i1 << i * 8;
            }
            sum = sum + i2;
        }
        return sum;
    }

    public static long changeBytesToLong(byte[] buffer) {
        long i1, i2, sum = 0;
        for (int i = 0; i < buffer.length; i++) {
            if (buffer[i] < 0) {
                i1 = buffer[i] & 0xff;
                i2 = i1 << i * 8;
            } else {
                i1 = buffer[i];
                i2 = i1 << i * 8;
            }
            sum = sum + i2;
        }
        return sum;
    }

    public static byte[] intToByte4(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }

    public static byte[] longToByte4(long i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }

    public static boolean bytesCompare(byte[] src, int src_offset, byte[] dest, int dest_offset, int len) {
        if (src == null || dest == null ||
                src_offset + len > src.length ||
                dest_offset + len > dest.length) {
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (src[i + src_offset] != dest[i + dest_offset]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @param str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public static String str2HexStr(String str) {

        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制转换字符串
     *
     * @param hexStr Byte字符串(Byte之间无分隔符 如:[616C6B])
     * @return String 对应的字符串
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b byte数组
     * @return String 每个Byte值之间用separator分隔
     */
    public static String byte2HexStr(byte[] b, String separator) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            if (n < b.length - 1) {
                sb.append(separator);
            }
        }
        return sb.toString().toUpperCase().trim();
    }


    /**
     * bytes字符串转换为Byte值
     *
     * @param src Byte字符串，每个Byte之间没有分隔符
     * @return byte[]bcd转码这是
     */
    public static byte[] hexStr2Bytes(String src) {
        byte a = (byte) 22;
        int m = 0, n = 0;
        if (src.length() % 2 != 0) {
            src = "0" + src;
        }
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            //ret[i] = Byte.decode("0x" + src.substring(i*2, m) + src.substring(m,n));
            ret[i] = (byte) ((Integer.parseInt(src.substring(i * 2, m) + src.substring(m, n), 16)));
        }
        return ret;
    }




    /**
     * String的字符串转换成unicode的String
     *
     * @param strText 全角字符串
     * @return String 每个unicode之间无分隔符
     * @throws Exception
     */
    public static String strToUnicode(String strText) {
        char c;
        StringBuilder str = new StringBuilder();
        int intAsc;
        String strHex;
        for (int i = 0; i < strText.length(); i++) {
            c = strText.charAt(i);
            intAsc = (int) c;
            strHex = Integer.toHexString(intAsc);
           /* if (intAsc > 128)
                str.append("\\u" + strHex);
            else // 低位在前面补00
                str.append("\\u00" + strHex);*/
            if (intAsc > 128)
                str.append(strHex);
            else // 低位在前面补00
                str.append(strHex);
        }
        return str.toString();
    }

    /**
     * unicode的String转换成String的字符串
     *
     * @param hex 16进制值字符串 （一个unicode为2byte）
     * @return String 全角字符串
     */
    public static String unicodeToString(String hex) {
        int t = hex.length() / 4;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < t; i++) {
            String s = hex.substring(i * 4, (i + 1) * 4);
            if (s.equals("0000")) {
                continue;
            }
            // 高位需要补上00再转
            String s1 = s.substring(0, 2) + "00";
            // 低位直接转
            String s2 = s.substring(2);
            // 将16进制的string转为int
            int n = Integer.valueOf(s1, 16) + Integer.valueOf(s2, 16);
            // 将int转换为字符
            char[] chars = Character.toChars(n);
            str.append(new String(chars));
        }
        return str.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String revert(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }


    public static boolean testCPU() {
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            // System.out.println("is big ending");
            return true;
        } else {
            // System.out.println("is little ending");
            return false;
        }
    }

    public static byte[] getBytes(short s, boolean bBigEnding) {
        byte[] buf = new byte[2];

        if (bBigEnding) {
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x00ff);
                s >>= 8;
            }
        } else {
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x00ff);
                s >>= 8;
            }
        }

        return buf;
    }

    public static byte[] getBytes(int s, boolean bBigEnding) {
        byte[] buf = new byte[4];

        if (bBigEnding) {
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x000000ff);
                s >>= 8;
            }
        } else {
            System.out.println("1");
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x000000ff);
                s >>= 8;
            }
        }

        return buf;
    }

    public static byte[] getBytes(long s, boolean bBigEnding) {
        byte[] buf = new byte[8];

        if (bBigEnding) {
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x00000000000000ff);
                s >>= 8;
            }
        } else {
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x00000000000000ff);
                s >>= 8;
            }
        }

        return buf;
    }

    public static short getShort(byte[] buf, boolean bBigEnding) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }

        if (buf.length > 2) {
            throw new IllegalArgumentException("byte array size > 2 !");
        }

        short r = 0;
        if (bBigEnding) {
            for (int i = 0; i < buf.length; i++) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        } else {
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        }

        return r;
    }

    public static int getInt(byte[] buf, boolean bBigEnding) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }

        if (buf.length > 4) {
            throw new IllegalArgumentException("byte array size > 4 !");
        }

        int r = 0;
        if (bBigEnding) {
            for (int i = 0; i < buf.length; i++) {
                r <<= 8;
                r |= (buf[i] & 0x000000ff);
            }
        } else {
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x000000ff);
            }
        }

        return r;
    }

    public static long getLong(byte[] buf, boolean bBigEnding) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }

        if (buf.length > 8) {
            throw new IllegalArgumentException("byte array size > 8 !");
        }

        long r = 0;
        if (bBigEnding) {
            for (int i = 0; i < buf.length; i++) {
                r <<= 8;
                r |= (buf[i] & 0x00000000000000ff);
            }
        } else {
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x00000000000000ff);
            }
        }

        return r;
    }

    /*----------------------------------------------------------*/
     /* 对转换进行一个简单的封装 */
     /*----------------------------------------------------------*/
    public static byte[] getBytes(int i) {
        return getBytes(i, testCPU());
    }

    public static byte[] getBytes(short s) {
        return getBytes(s, testCPU());
    }

    public static byte[] getBytes(long l) {
        return getBytes(l, testCPU());
    }

    public static int getInt(byte[] buf) {
        return getInt(buf, testCPU());
    }

    public static short getShort(byte[] buf) {
        return getShort(buf, testCPU());
    }

    public static long getLong(byte[] buf) {
        return getLong(buf, testCPU());
    }

    /****************************************/
    public static short[] Bytes2Shorts(byte[] buf) {
        byte bLength = 2;
        short[] s = new short[buf.length / bLength];

        for (int iLoop = 0; iLoop < s.length; iLoop++) {
            byte[] temp = new byte[bLength];

            for (int jLoop = 0; jLoop < bLength; jLoop++) {
                temp[jLoop] = buf[iLoop * bLength + jLoop];
            }

            s[iLoop] = getShort(temp);
        }

        return s;
    }

    public static byte[] Shorts2Bytes(short[] s) {
        byte bLength = 2;
        byte[] buf = new byte[s.length * bLength];

        for (int iLoop = 0; iLoop < s.length; iLoop++) {
            byte[] temp = getBytes(s[iLoop]);

            for (int jLoop = 0; jLoop < bLength; jLoop++) {
                buf[iLoop * bLength + jLoop] = temp[jLoop];
            }
        }

        return buf;
    }

    /****************************************/
    public static int[] Bytes2Ints(byte[] buf) {
        byte bLength = 4;
        int[] s = new int[buf.length / bLength];

        for (int iLoop = 0; iLoop < s.length; iLoop++) {
            byte[] temp = new byte[bLength];

            for (int jLoop = 0; jLoop < bLength; jLoop++) {
                temp[jLoop] = buf[iLoop * bLength + jLoop];
            }

            s[iLoop] = getInt(temp);

            System.out.println("2out->" + s[iLoop]);
        }

        return s;
    }

    public static byte[] Ints2Bytes(int[] s) {
        byte bLength = 4;
        byte[] buf = new byte[s.length * bLength];

        for (int iLoop = 0; iLoop < s.length; iLoop++) {
            byte[] temp = getBytes(s[iLoop]);

            System.out.println("1out->" + s[iLoop]);

            for (int jLoop = 0; jLoop < bLength; jLoop++) {
                buf[iLoop * bLength + jLoop] = temp[jLoop];
            }
        }

        return buf;
    }

    /****************************************/
    public static long[] Bytes2Longs(byte[] buf) {
        byte bLength = 8;
        long[] s = new long[buf.length / bLength];

        for (int iLoop = 0; iLoop < s.length; iLoop++) {
            byte[] temp = new byte[bLength];

            for (int jLoop = 0; jLoop < bLength; jLoop++) {
                temp[jLoop] = buf[iLoop * bLength + jLoop];
            }

            s[iLoop] = getLong(temp);
            System.out.println("1out->" + s[iLoop]);
        }

        return s;
    }

    public static byte[] Longs2Bytes(long[] s) {
        byte bLength = 8;
        byte[] buf = new byte[s.length * bLength];

        for (int iLoop = 0; iLoop < s.length; iLoop++) {
            byte[] temp = getBytes(s[iLoop]);

            for (int jLoop = 0; jLoop < bLength; jLoop++) {
                buf[iLoop * bLength + jLoop] = temp[jLoop];
            }
        }

        return buf;
    }


    //将9byte的楼层号转为int的楼层号(目的楼层)
    public static int[] byteToIntLift(byte[] lift)    //处理电梯联动信息字符串
    {
        int i, j;
        int count = 0, pos = 0;
        //get floor count
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 8; j++) {
                if ((lift[i] & (0x1 << j)) != 0) {
                    count++;
                }
            }
        }
        int[] floor = new int[count];
        for (i = 0; i < 9; i++) {
            for (j = 0; j < 8; j++) {
                if ((lift[i] & (0x1 << j)) != 0) {
                    if (i == 0) {       //负楼层
                        floor[pos++] = -(j + 1);
                    } else {
                        floor[pos++] = i * 8 + j + 1 - 8;
                    }
                }
            }
        }
        return floor;
    }

    //将楼层号转为9byte的数(目的楼层)
    public static byte[] intToByteLift(int aimFloor) {
        //63
        int index, offset;
        byte[] sendBuff = new byte[9];
        Arrays.fill(sendBuff, (byte) 0);
        aimFloor += 8;

        if (aimFloor % 8 > 0) {
            index = aimFloor / 8 + 1;
        } else {
            index = aimFloor / 8;
        }
        offset = aimFloor % 8;

        if (aimFloor <= 8) {
            //负楼层
            offset = 7 - offset;
            sendBuff[0] |= 0x01 << offset;
        } else {
            if (offset == 0) {
                offset = 8;
            }
            sendBuff[index - 1] |= 0x01 << (offset - 1);
        }
        return sendBuff;
    }

    public static String int2HexStr(int value, int miniLen) {
        String stmp = "";
        stmp = Integer.toHexString(value);
        while (stmp.length() < miniLen) {
            stmp = "0" + stmp;
        }
        return stmp.toUpperCase().trim();
    }


    public static String my_itoa(byte[] tmpval,boolean isAuth)   //存入数据库前转换
    {
        //一个byte转为2个string
        byte[] strBytes = new byte[19];
        if(isAuth){
            strBytes[0] = (byte)'1';
            for(int i=0; i<tmpval.length; i++){
                int temp = 0;
                temp = tmpval[i] & 0xff;
                strBytes[i*2+1] = (byte)(temp/16 + '0');
                strBytes[i*2+1+1] = (byte)(temp%16 + '0');
            }

        }else{
            strBytes[0] = (byte)'0';
        }

        String str = null;
        try {
            str = new String(strBytes, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static byte[]  my_atoi(String str)  //从数据库取出来是解析
    {
        int pos = 0;
        byte[] result = new byte[9];
        int temp,temp2, temp3;
        try {
            byte[] strBytes = str.getBytes("US-ASCII");
            for(int i=1; i<strBytes.length; i+=2){
                temp = strBytes[i] & 0xff;
                temp2 = strBytes[i+1] & 0xff;
                temp3 = ((temp -'0') * 16) + (temp2 -'0');
                result[pos++] = (byte)temp3;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
