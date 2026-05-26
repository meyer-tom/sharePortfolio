package fr.utc.miage.shares;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListActionsTest {

    @Test
    void testListeNonVide() {
        ActionSimple a1 = new ActionSimple("Action 1");
        ActionSimple a2 = new ActionSimple("Action 2");
        ListeActions liste = new ListeActions(List.of(a1, a2));
        
        Assertions.assertEquals(2, liste.getActions().size());
    }

    @Test
    void testActionSimpleEtComposee() {
        ActionSimple a1 = new ActionSimple("Action 1");
        ActionComposee a2 = new  ActionComposee("Action 2", Map.of(a1, 100.0));
        ListeActions liste = new ListeActions(List.of(a1, a2));
        
        Assertions.assertTrue(liste.getActions().get(0) instanceof ActionSimple);
        Assertions.assertTrue(liste.getActions().get(1) instanceof ActionComposee);
    }

    @Test
    void testListeVide() {
        ListeActions liste = new ListeActions(List.of());
        
        Assertions.assertTrue(liste.getActions().isEmpty());
    }

    @Test

    void testToString() {
        ActionSimple a1 = new ActionSimple("Action 1");
        ListeActions liste = new ListeActions(List.of(a1));
        
        Assertions.assertNotNull(liste.toString());
    }

}
