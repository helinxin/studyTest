import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Stream;

public class CaptureService {

    //一共10个线程， 但是我同时只允许跑五个 ， 有点自定义线程池 ， 还有堵塞队列的感觉
    //用 LinkedList  在过程中 把 这个 list对象锁住  利用 linkedList 先进先出的特点  删除第一个 ，然后唤醒
    //这里我不解的一点是  后面 会把 这个list for 循环， 然后join() 一遍 ，自己测试  ， 就算不join，结果也是一样的， 所以为什么要join， 回头再看看视频
    //一共有两个list  一个是 linkedList 用来 存放10个 线程名称  ，然后 一个Thread 的list
    //lets gan
    //复盘一下我最大的问题 1. 就是 这个LinkedList.add 的放在了锁外
    //2.锁在wait 和 notifyAll的时候锁  不是整个锁 ，sleep的时候不锁
    //3.我还是不知道join 是为了干什么
    //4.这里由于是多线程  我每次判断都是用的if ， 应该要用while
    //5.LinkedList.addLast() 这个函数 是每次只能加一个，并且只能插到最后  这个为什么要尾插， 我也不知道

    //同时最大线程数
    public static final int MAX_THREAD_COUNT = 5;

    public static LinkedList<Control> CONTROL_LIST = new LinkedList();


    public static void main(String[] args) {
        //先造10个线程

        /*Stream.of("m1", "m2", "m3", "m4", "m5", "m6", "m7", "m8", "m9", "m10").forEach(threadName -> {
            doingThread(threadName).start();
        });*/
        Stream.of("m1", "m2", "m3", "m4", "m5", "m6", "m7", "m8", "m9", "m10").map(CaptureService::doingThread).forEach(Thread::start);

    }

    public static Thread doingThread(String threadName) {
        return new Thread(() -> {
            synchronized (CONTROL_LIST) {
                while (CONTROL_LIST.size() > MAX_THREAD_COUNT) {
                    try {
                        CONTROL_LIST.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CONTROL_LIST.add(new Control());
            }
            Optional.of(threadName + " is working...").ifPresent(System.out::println);

            //do something 10s
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Optional.of(threadName + "do it finished").ifPresent(System.out::println);

            //把自己删掉，并且唤醒
            synchronized (CONTROL_LIST) {
                CONTROL_LIST.removeFirst();
                CONTROL_LIST.notifyAll();
            }
        }, threadName);
    }


    public static class Control {
    }
}
