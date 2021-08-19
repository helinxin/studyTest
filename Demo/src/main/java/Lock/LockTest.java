package Lock;


/**
 * @author hlx
 */
public class LockTest {

    //1.我没有在MyLock 的lock 和 unlock 方法上 加锁
    //2.MyLock 中 没有给当前线程  赋值


    public static void main(String[] args) {
        MyLock myLock = new MyLock();

        new Thread(() -> {
            myLock.lock();
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                myLock.unlock();
            }
        }, "m1").start();

        new Thread(() -> {
            myLock.lock();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                myLock.unlock();
            }
        }, "m2").start();


    }

}
