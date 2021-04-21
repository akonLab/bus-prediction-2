package example;

import java.time.LocalDateTime;

public class Task implements Runnable {
    @Override
    public void run() {
        System.out.println("hello, "+ LocalDateTime.now().toString());
    }
}
