package Lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * @author hlx
 * 自定义lock的实现类，实现获取锁释放锁，基础api
 */
public class MyLock implements Lock {

    private boolean FLAG;

    public static Collection LIST = new ArrayList();

    private Thread currentThread;

    public MyLock() {
        this.FLAG = false;
    }

    @Override
    public synchronized void lock() {
        //第一次进来 flag置为true,在unlock中flag置为false
        while (FLAG) {
            try {
                LIST.add(Thread.currentThread());
                Optional.of(Thread.currentThread().getName() + "锁已经被占用").ifPresent(System.out::println);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Optional.of(Thread.currentThread().getName() + "获取到了锁").ifPresent(System.out::println);
        LIST.remove(Thread.currentThread());
        FLAG = true;
        currentThread=Thread.currentThread();
    }

    @Override
    public void lock(long mills) {
        //todo 这个是下一讲说的
    }

    @Override
    public synchronized void unlock() {
        //把flag置为false
        Optional.of(Thread.currentThread().getName() + " -----" + currentThread).ifPresent(System.out::println);
        if (Thread.currentThread() == currentThread) {
            Optional.of(Thread.currentThread().getName() + " is unlock").ifPresent(System.out::println);
            FLAG = false;
            notifyAll();
        }
    }
}
