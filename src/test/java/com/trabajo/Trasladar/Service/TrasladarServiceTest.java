package com.trabajo.Trasladar.Service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.trabajo.Trasladar.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.trabajo.Trasladar.TrasladarApplication;

@SpringBootTest
@ActiveProfiles("test")
public class TrasladarServiceTest {

    @Test
    void contextLoads() {
    }

    @Test
    void mainClassExists() {
        assertNotNull(TrasladarApplication.class);
    }

}
