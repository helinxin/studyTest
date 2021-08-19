package Lock;

/**
 * @author hlx
 * 自己手动写一个lock 实现在多线程环境中 获取锁释放锁的过程 其实主要用的 wait() notifyAll() 然后 一个标识符
 */
public interface Lock {
    void lock();

    void lock(long mills);

    void unlock();

    /**
     * 这是一个自己定义的异常类
     */
    class TimeOutException extends Exception {
        public TimeOutException(String message) {
            super(message);
        }
    }
}
