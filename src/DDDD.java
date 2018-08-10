import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ArrayList
 * 采用数组存储，初始为0
 * 当没有指定ArrayList大小并向其他添加一个元素时，默认大小为10
 * 当ArrayList的容量不足时，其扩容规则为 newCapacity = oldCapacity + (oldCapacity >> 1)
 * ArrayList默认最大大小为Integer.MAX_VALUE - 8，达到这个值的时候很多时候你的内存已经溢出了
 * ArrayList最大大小为Integer.MAX_VALUE
 * 如果数据很大，那么有必要为集合初始化一个默认大小，防止多次扩容，但如果数据增长很慢，那么就会浪费内存了，具体怎么做，还是要看实际应用场景。
 * ArrayList线程不安全
 */
public class DDDD {
    public static void main(String[] args) {
        try {
            ArrayList<Character> arrayList = new ArrayList<>();

            Object[] object = (Object[]) new DDDD().getFieldValue(ArrayList.class, arrayList, "elementData");
            System.out.println("size:" + arrayList.size() + ", length: " + object.length +
                    " -> 初始时都为0");

            arrayList.add('1');
            object = (Object[]) new DDDD().getFieldValue(ArrayList.class, arrayList, "elementData");
            System.out.println("size:" + arrayList.size() + ", length: " + object.length +
                    " -> 有元素时默认大小为10");

            for (int i=0; i<10; ++i) {
                arrayList.add('1');
            }
            object = (Object[]) new DDDD().getFieldValue(ArrayList.class, arrayList, "elementData");
            System.out.println("size:" + arrayList.size() + ", length: " + object.length +
                    " -> 一般的大小扩充规则 newCapacity = oldCapacity + (oldCapacity >> 1) 即 15 = 10 + 10/2");

            int MAX_ARRAY_SIZE = (int) new DDDD().getFieldValue(ArrayList.class, arrayList, "MAX_ARRAY_SIZE");
            System.out.println(MAX_ARRAY_SIZE + " -> 默认最大大小 MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8");

            arrayList.clear();
            /**
             * 主线程等待子线程都结束后，再执行
             * 第一种方法是使用CountDownLatch
             * 第二种方法是所有子线程都进行thread.join()
             * 第三种方法是使用同步屏障，所有子线程在线程最后cyclicBarrier.await()，主线程也cyclicBarrier.await() --- 不一定对
             */


            CountDownLatch countDownLatch = new CountDownLatch(Integer.MAX_VALUE - 8);
            ExecutorService executorService = Executors.newFixedThreadPool(20);

            for (int i=0; i<Integer.MAX_VALUE - 8; ++i) {
                    executorService.submit(() -> {arrayList.add('1'); countDownLatch.countDown();});
            }

            countDownLatch.await();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    //反射
    Object getFieldValue(Class classType, Object object, String fieldName) throws Exception{
        //属性
        Field[] fields = classType.getDeclaredFields();
        for (int i=0; i<fields.length; ++i) {
            fields[i].setAccessible(true);
            /*System.out.println("name:" + fields[i].getName() + ", value:" + fields[i].get(object));*/
            if (fields[i].getName().equals(fieldName)) {
                return fields[i].get(object);
            }
        }
        //方法
        Method[] methods = classType.getDeclaredMethods();
        for (int i=0; i<methods.length; i++) {
            methods[i].setAccessible(true);
            if (methods[i].getName().equals("getFence")) {
                methods[i].invoke(object);
            }
        }
        return null;
    }
}
