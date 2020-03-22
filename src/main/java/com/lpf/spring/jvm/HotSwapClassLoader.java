package com.lpf.spring.jvm;

/**
 * 自定义类加载器
 * 为了多次载入执行类而加入的加载器<br>
 * 把defineClass方法开放出来，只有外部显示调用的时候才会使用到loadByte方法
 * 由虚拟机调用时，仍然按照原有的双亲委派规则使用loadClass方法进行加载
 *
 * @author lipengfei
 * @create 2018-08-14 19:13
 **/
public class HotSwapClassLoader extends ClassLoader {

    public HotSwapClassLoader() {
        super(HotSwapClassLoader.class.getClassLoader());
    }

    public Class loadByte(byte[] classByte) {
        return defineClass(null, classByte, 0, classByte.length);
    }
}
