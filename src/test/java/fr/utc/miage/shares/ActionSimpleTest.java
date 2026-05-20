package fr.utc.miage.shares;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import fr.utc.miage.shares.Jour;


class ActionSimpleTest {

    private static final String FOO_SHARE1 = "Foo Share 1";
    private static final String FOO_SHARE2 = "Foo Share 2";
    private static final Jour JOUR_1 =  new Jour(2026, 1);
    private static final int VALEUR =  10;


    @Test
    void testAllConstructorUsage() {
        Assertions.assertDoesNotThrow(() -> {new ActionSimple(FOO_SHARE1);});
    }

    @Test
    void testenrgCoursShouldWork(){
        final ActionSimple action = new ActionSimple(FOO_SHARE1);

        Assertions.assertDoesNotThrow(() -> {action.enrgCours(JOUR_1, VALEUR);});
    }

    @Test
    void testValeurShouldWork(){
        final ActionSimple action = new ActionSimple(FOO_SHARE1);

        action.enrgCours(JOUR_1, VALEUR);

        assertEquals(VALEUR,action.valeur(JOUR_1));
        
    }

    @Test
    void testValeurShouldNotWork(){
        final ActionSimple action = new ActionSimple(FOO_SHARE1);

        action.enrgCours(JOUR_1, VALEUR);

        assertEquals(VALEUR,action.valeur(JOUR_1));
        
    }

}
