import java.util.stream.Stream;

public class ProducerAndConsumer3 {

    //这是第三个版本， 优化在多线程环境下 生产消费假死的问题
    //这里用到了 notifyAll()
    //1.我初步就是把notify 换成了 notifyAll()，就没有假死了，不记得视频里是怎么说的了
    //2.好的，这样好像还是出问题， 我先把sleep个10ms ，然后3个线程
    //3.视频里用的 while(IS_PRODUCER)  我目前没有用while 没有出现问题，不纠结

    static final Object LOCK = new Object();

    static int COUNT = 1;

    static boolean IS_PRODUCER = false;


    public void producer() {
        synchronized (LOCK) {
            if (IS_PRODUCER) {
                //已经生产  那就等待
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //生产
                COUNT++;
                IS_PRODUCER = true;
                LOCK.notifyAll();
                System.out.println("生产了......" + COUNT);
            }
        }
    }

    public void consumer() {
        synchronized (LOCK) {
            if (IS_PRODUCER) {
                //那就消费  并且唤醒
                System.out.println("消费了....." + COUNT);
                IS_PRODUCER = false;
                LOCK.notifyAll();
            } else {
                //睡觉
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerAndConsumer3 producerAndConsumer3 = new ProducerAndConsumer3();
        Stream.of("t1", "t2","t3").forEach(t ->
                new Thread(t) {
                    @Override
                    public void run() {
                        while (true) {
                            producerAndConsumer3.producer();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start()
        );

        Stream.of("p1", "p2","p3").forEach(t ->
                new Thread(t) {
                    @Override
                    public void run() {
                        while (true) {
                            producerAndConsumer3.consumer();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start());
    }
}
