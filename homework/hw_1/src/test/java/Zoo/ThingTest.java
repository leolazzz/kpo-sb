package Zoo;

import zoo.Models.*;
import zoo.services.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ThingTest {
    @Test
    void gettingInventoryNumber(){
        Table tb = new Table(100);
        assertEquals(100, tb.getInventoryNumber(), "Getting InventoryNumber");
    }
    @Test
    void settingInventoryNumber(){
        Table tb = new Table(100);
        tb.setInventoryNumber(121);
        assertEquals(121, tb.getInventoryNumber(), "Setting InventoryNumber");
    }
}
