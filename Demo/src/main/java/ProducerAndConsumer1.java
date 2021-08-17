public class ProducerAndConsumer1 {

    public static int COUNT = 1;

    /**
     * 锁
     */
    public static Object LOCK = new Object();

    public void Producer() {
        synchronized (LOCK) {
            COUNT++;
            System.out.println("生产了...." + COUNT);
        }
    }

    public void Consumer() {
        synchronized (LOCK) {
            System.out.println("消费了...." + COUNT);
        }
    }

    public static void main(String[] args) {
        //看来这个是版本问题，忘记视频是怎么说的了
        //这个现在跑起来不是生产一个消费一个
        ProducerAndConsumer1 producerAndComsumer1 = new ProducerAndConsumer1();
        new Thread("m1") {
            @Override
            public void run() {
                while (true) {
                    producerAndComsumer1.Producer();

                }
            }
        }.start();

        new Thread("m2") {
            @Override
            public void run() {
                while (true) {
                    producerAndComsumer1.Consumer();
                }
            }
        }.start();
    }

}
