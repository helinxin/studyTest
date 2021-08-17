import java.util.stream.Stream;

public class ProducerAndConsumer2 {

    //这个版本引入了  notify()  wait()  还有一个标记符
    //用来使线程等待和唤醒
    //哟西 搞定了
    //但是这个有个问题  就是 在多线程环境下  会有问题

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
                LOCK.notify();
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
                LOCK.notify();
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
        ProducerAndConsumer2 producerAndConsumer2 = new ProducerAndConsumer2();
       /* new Thread("t") {
            @Override
            public void run() {
                while (true) {
                    producerAndConsumer2.producer();
                }
            }
        }.start();

        new Thread("t") {
            @Override
            public void run() {
                while (true) {
                    producerAndConsumer2.consumer();
                }
            }
        }.start();*/
        Stream.of("t1", "t2").forEach(t ->
                new Thread(t) {
                    @Override
                    public void run() {
                        while (true) {
                            producerAndConsumer2.producer();
                        }
                    }
                }.start()
        );

        Stream.of("p1", "p2").forEach(t ->
                new Thread(t) {
                    @Override
                    public void run() {
                        while (true) {
                            producerAndConsumer2.consumer();
                        }
                    }
                }.start());
    }
}
