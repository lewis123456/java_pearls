/**
 * Object类的hashcode实现
 * Java调用C/C++
 * 1. javac -encoding UTF-8 CCCC.java
 * 2. javah -encoding UTF-8 -jni CCCC
 * 3. 自己实现CCCC.cc
 * 4. 将jni.h, jni_md.h, jawt_md.h拷贝到g++安装目录的include中
 * 5. g++ -Wall -shared -m64 CCCC.cc -o getAddress.dll
 * 6. 将getAddress.dll拷贝到java安装目录的bin文件夹中
 */

public class CCCC {
    native long getAddress(Object c);

    static {
        System.loadLibrary("getAddress");
    }

    public static void main(String[] args) {
        CCCC cccc = new CCCC();
        //判断Object的hashcode是否是该对象在内存中的地址
        System.out.println("hashCode:" + cccc.hashCode());
        System.out.println("getAddress:" + cccc.getAddress(cccc));
    }
}
