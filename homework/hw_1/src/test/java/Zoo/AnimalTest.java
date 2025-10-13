package Zoo;

import zoo.Models.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    void gettingFood(){
        Monkey mk = new Monkey("qwe", 3, 7);
        assertEquals(3, mk.getFood(), "Getting food");
    }
    @Test
    void settingFood(){
        Monkey mk = new Monkey("qwe", 3, 7);
        mk.setFood(10);
        assertEquals(10, mk.getFood(), "Setting food");
    }
    @Test
    void gettingName(){
        Monkey mk = new Monkey("qwe", 3, 7);
        assertEquals("qwe", mk.getName(), "Getting name");
    }
    @Test
    void settingInventoryNumber(){
        Monkey mk = new Monkey("qwe", 3, 7);
        mk.setInventoryNumber(100);
        assertEquals(100, mk.getInventoryNumber(), "Setting inventory");
    }
    @Test
    void gettingInventoryNumber(){
        Monkey mk = new Monkey("qwe", 3, 7);
        mk.setInventoryNumber(11);
        assertEquals(11, mk.getInventoryNumber(), "Getting inventory");
    }
    @Test
    void gettingisHealth(){
        Monkey mk = new Monkey("qwe", 3, 7);
        assertFalse(mk.isHealthy(), "Getting health");
    }
    @Test
    void settingisHealth(){
        Monkey mk = new Monkey("qwe", 3, 7);
        mk.setHealth(true);
        assertTrue(mk.isHealthy(), "Setting health");
    }
}
