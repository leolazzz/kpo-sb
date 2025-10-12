package Zoo;

import zoo.Models.*;
import zoo.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VetClinicServiceTest {
    private VetClinic clinic;

    @BeforeEach
    void setUp(){
        clinic = new VetClinic();
    }

    @Test
    void checkingHealthAnimal(){
        Monkey mk = new Monkey("qwe", 3, 7);
        Tiger tg = new Tiger("hnrtgn", 10);
        boolean res = clinic.isHealth(mk);
        assertTrue(res, "checkingHealthAnimal mk");
        res = clinic.isHealth(tg);
        assertTrue(res, "checkingHealthAnimal tg");
    }

    @Test
    void checkingNotHealthAnimal(){
        Monkey mk = new Monkey("q", 3, 7);
        Tiger tg = new Tiger("hn", 10);
        boolean res = clinic.isHealth(mk);
        assertFalse(res, "checkingNotHealthAnimal mk");
        res = clinic.isHealth(tg);
        assertFalse(res, "checkingNotHealthAnimal tg");
    }
}
