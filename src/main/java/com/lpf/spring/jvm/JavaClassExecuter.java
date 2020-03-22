package com.lpf.spring.jvm;

import java.lang.reflect.Method;

/**
 * JavaClass执行工具
 *
 * @author lipengfei
 * @create 2018-08-22 18:31
 **/
public class JavaClassExecuter {


    /**
     * 执行外部传来的代表一个Java类的byte数组
     * 将输入的byte数组中代表java.lang.System的CONSTANRT_Utf8_info常量修改为劫持后的HackSystem类
     * 执行方法为该类的static main(String[] args)方法，输出结果为该类想System.out/err输出的信息
     *
     * @param classByte
     * @return
     */
    public static String exexute(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier cm = new ClassModifier(classByte);
        byte[] modiBytes = cm.modifyUTF8Constant("java/lang/System", "com/lpf/spring/jvm/HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);

        try {

            Method method = clazz.getMethod("main", new Class[]{String[].class});
            method.invoke(null, new String[]{null});

        } catch (Throwable e) {
            e.printStackTrace(HackSystem.out);
        }

        return HackSystem.getBufferString();
    }
}
