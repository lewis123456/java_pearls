import java.util.ArrayList;
import java.util.List;

/**
 * 栈溢出：java.lang.StackOverflowError
 *
 * 堆溢出：1. java.lang.OutOfMemoryError: GC overhead limit exceeded, 执行垃圾收集的时间比例太大,有效的运算量太小。默认
 * 情况下, 如果GC花费的时间超过 98%, 并且GC回收的内存少于 2%, JVM就会抛出这个错误。2. java.lang.OutOfMemoryError: Java
 * heap space。通过设置不同的堆大小，或者不同的垃圾回收算法，异常的类型也不同。
 *
 * JVM设置，-Xms堆的初始大小(默认物理内存的1/64)，-Xmx堆的最大值(默认为物理内存的1/4)，-Xmn堆中新生代的大小,
 * -Xss每个线程栈的大小，-XX:PermSize设置非堆内存的初始值(默认物理内存的1/64), -XX:MaxPermSize设置最大非堆内存的大小(默
 * 认物理内存的1/4), -XX:NewSize设置新生代的默认大小, -XX:MaxNewSize设置新生代的最大大小, -XX:SurvivorRatio, 设置Eden与
 * 一个Survivor区的比值，-XX:NewRatio，设置老年代与年轻代的比值。
 *
 * 收集器设置，-XX:+UseParallelOldGC新生代和老生代都使用并行回收器，-XX:ParallelGCThreads设置多少个线程来回收垃圾(默认
 * 如果处理器个数小于8，则设置成处理器个数，否则设置成3+5N/8)。如果只有一个核，使用串行更好，-XX:+UseSerialGC。
 * -XX:+UseConcMarkSweepGC表示老年代开启CMS收集器，新生代默认使用并行收集器，-XX:ConcGCThreads指定多少个线程来执行CMS的并行阶段。
 *
 * 使用jstat查看当前GC的情况，计算YGC平均耗时，FGC平均耗时，YGC触发的频率。多产生几次FullGC后，使用jmap -heap观察当前内
 * 存的情况。计算老年代内存的大小，重新分配内存，保证整个堆的大小是老年代的3到4倍左右。
 *
 * JDK7 从永久代中移除了字符串内部池
 * JDK8 完全移除永久代，之前会设置PermSize和MaxPermSize，以后直接设置元空间Metaspace, 可以对比PermGen和Metaspace
 * JDK9的变化
 */
public class AAAA {
    //递归调用发生栈溢出
    public void stackOverflowError() {
        stackOverflowError();
    }

    //OutOfMemoryError, 对象过多发生堆内存溢出
    public void outOfMemoryError() {
        List<AAAA> list = new ArrayList<>();
        while (true) {
            list.add(new AAAA());
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            //new AAAA().stackOverflowError();
            new AAAA().outOfMemoryError();
        } catch (Error e) {
            long end = System.currentTimeMillis();
            System.out.println("waste:" + (end - start));
            System.out.println(e);
        }
    }
}