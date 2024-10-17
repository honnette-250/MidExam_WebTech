package rw.transport.VubaRide;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class VubaRideApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(VubaRideApplicationTests.class);

    @Test
    void contextLoads() {
        // This test will load the application context and check if everything is wired
        // correctly
        logger.info("Application context loaded successfully.");
    }
}
