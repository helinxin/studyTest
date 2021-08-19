public class SingleDemo {


    private SingleDemo() {
    }

    private static SingleDemo instance = null;

    public static synchronized SingleDemo getInstance() {
        if (instance == null) {
            instance = new SingleDemo();
        }
        return instance;
    }



}
