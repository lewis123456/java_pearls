import java.util.ArrayList;
import java.util.List;

/**
 * 栈溢出、堆内存溢出
 */
public class AAAA {
    //StackOverflowError, 递归调用发生栈溢出
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
        //new AAAA().stackOverflowError();
        new AAAA().outOfMemoryError();
    }
}
