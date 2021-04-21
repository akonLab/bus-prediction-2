package example;

import base.APIConn;

public class Main {
    public static void main(String[] args) {
//
//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(new Task(), 0, 30, TimeUnit.SECONDS);
//

        APIConn apiConn=new APIConn();
        System.out.println(apiConn.generateToken());
//
//        AIBusAPIConn aiBusAPIConn=new AIBusAPIConn();
//        aiBusAPIConn.getConnection();

        }
    }
