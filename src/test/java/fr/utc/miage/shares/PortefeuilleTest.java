package fr.utc.miage.shares;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PortefeuilleTest {

    private static final int YEAR = 2026;
    private static final int MONTH = 12;
    private static final int DAY = 31;

    @Test
    void testPortefeuilleConstructorNoThrowsException() {
        assertDoesNotThrow(PortefeuilleTest::new);
    }

    @Test
    void newPortefeuilleHasNoActions() {
        Portefeuille portfolio = new Portefeuille();
        Jour aujourdHui = new Jour(YEAR, MONTH, DAY);

        // Test nb actions
        assertEquals(0, portfolio.getActions().size());

        // Test valeur portefeuille aujourd'hui
        assertEquals(0, portfolio.valeurPortefeuille(aujourdHui));
    }
}
