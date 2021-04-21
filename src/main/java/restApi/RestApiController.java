package restApi;

import controllers.TimeController;
import models.AIBusDataModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buses")
public class RestApiController {
    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;
    private static final int AUTH_FAILURE = 102;

//    private final TimeController timeController = new TimeController();
    private List<AIBusDataModel> allBusData = new ArrayList<>();

    @GetMapping
    public BusDataResponse showAllBuses() {
        TimeController timeController = new TimeController();

//        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(timeController, 0, 5, TimeUnit.SECONDS);
timeController.run();

        allBusData = timeController.getAllBusData();

        return new BusDataResponse(SUCCESS_STATUS, CODE_SUCCESS, allBusData);

    }

}
