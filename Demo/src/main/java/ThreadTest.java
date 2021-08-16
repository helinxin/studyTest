

public class ThreadTest {

    public static void main(String[] args) {

        DemoContext demoContext = new DemoContext();
        String shoot = demoContext.getHandler("SHOOT");
        System.out.println(shoot);

    }
}


