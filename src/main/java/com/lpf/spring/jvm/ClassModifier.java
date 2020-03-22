package com.lpf.spring.jvm;

/**
 * 修改Class二进制文件，暂时只修改常量池
 *
 * @author lipengfei
 * @create 2018-08-14 19:31
 **/
public class ClassModifier {

    /**
     * Class类文件中常量池的起始偏移
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;


    /**
     * CONSTANT_Utf8_Info常量的tag标志
     */
    private static final int CONSTANT_UTF8_INFO = 1;

    /**
     * 常量池中11中常量所占的长度，CONSTANT_Utf8_Info型常量除外，因为它不是定长的
     */
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5};

    private static final int u1 = 1;
    private static final int u2 = 2;

    private byte[] classBytes;

    public ClassModifier(byte[] classBytes) {
        this.classBytes = classBytes;
    }

    /**
     * 获取常量池中常量的数量
     *
     * @return
     */
    public int getConstantPoolCount() {
        return ByteUtils.byte2Int(classBytes, CONSTANT_POOL_COUNT_INDEX, u2);
    }


    public byte[] modifyUTF8Constant(String oldStr, String newStr) {
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for (int i = 0; i < cpc; i++) {
            int tag = ByteUtils.byte2Int(classBytes, offset, u1);
            if (tag == CONSTANT_UTF8_INFO) {
                int len = ByteUtils.byte2Int(classBytes, offset + u1, u2);

                offset += u1 + u2;
                String str = ByteUtils.bytes2String(classBytes, offset, len);
                if (str.equalsIgnoreCase(oldStr)) {
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);
                    byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset - u2, u2, strLen);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset, len, strBytes);
                    return classBytes;

                } else {
                    offset += len;
                }
            } else {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }


        }
        return classBytes;

    }
}
