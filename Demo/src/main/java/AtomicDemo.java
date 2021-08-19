import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author hlx
 */
public class AtomicDemo {

    static AtomicInteger atomicInteger = new AtomicInteger(1);

    public static final Object LOCK = new Object();

    public synchronized int add() {
        return atomicInteger.addAndGet(1);
    }

    public AtomicInteger get() {

        return atomicInteger;

    }

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(100);


        AtomicDemo atomicDemo = new AtomicDemo();

        IntStream.rangeClosed(0, 100).forEach(item -> {


            new Thread(() -> {

                IntStream.rangeClosed(1, 100).forEach(i -> {

                    atomicDemo.add();

                });

            }).start();
            countDownLatch.countDown();

        });
        countDownLatch.await();


        System.out.println(atomicDemo.get());

    }
}
