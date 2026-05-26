/*
 * Copyright 2026 Lois Pacqueteau.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.utc.miage.shares;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ListActionsTest {

    @Test
    void testListeNonVide() {
        ActionSimple a1 = new ActionSimple("Action 1");
        ActionSimple a2 = new ActionSimple("Action 2");
        ListeActions liste = new ListeActions(new ArrayList<>());
        liste.ajouterAction(a1);
        liste.ajouterAction(a2);

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

    //test le setAction

    @Test
    void testSetActions() {
        ActionSimple a1 = new ActionSimple("Action 1");
        ActionSimple a2 = new ActionSimple("Action 2");
        ListeActions liste = new ListeActions(List.of(a1));
        
        liste.setActions(List.of(a2));
        
        Assertions.assertEquals(1, liste.getActions().size());
        Assertions.assertEquals("Action 2", liste.getActions().get(0).getLibelle());
    }


    @Test
    void testAjouterAction() {
        ActionSimple a1 = new ActionSimple("Action 1");
        ListeActions liste = new ListeActions(new ArrayList<>());
        
        liste.ajouterAction(a1);
        
        Assertions.assertEquals(1, liste.getActions().size());
        Assertions.assertEquals("Action 1", liste.getActions().get(0).getLibelle());
    }

    @Test
    void testSupprimerAction() {
        ActionSimple a1 = new ActionSimple("Action 1");
        ListeActions liste = new ListeActions(new ArrayList<>());
        liste.ajouterAction(a1);
        
        liste.supprimerAction(a1);
        
        Assertions.assertTrue(liste.getActions().isEmpty());
    }


}
