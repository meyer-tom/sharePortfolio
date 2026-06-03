/*
 * Copyright 2026 Tom Meyer/Selim Hamza.
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

class PortefeuilleTest {

    private static final int YEAR = 2026;
    private static final int MONTH = 12;
    private static final int DAY = 31;

    private static final Jour DEFAULT_JOUR = new Jour(DAY, MONTH, YEAR);
    
    private static final String ACTION_1_NAME = "Action 1";
    private static final String ACTION_2_NAME = "Action 2";

    @Test
    void testPortefeuilleConstructorNoThrowsException() {
        assertDoesNotThrow(Portefeuille::new);
    }


    // --- Tests pour la méthode acheter ---

    @Test
    void testAcheterWithNullActionShouldThrowException() {
        Portefeuille portfolio = new Portefeuille();
        assertThrows(NullPointerException.class, () -> portfolio.acheter(null, 10));
    }

    @Test
    void testAcheterWithZeroOrNegativeQuantityShouldThrowException() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple(ACTION_1_NAME);
        
        IllegalArgumentException exceptionZero = assertThrows(
            IllegalArgumentException.class, 
            () -> portfolio.acheter(action, 0)
        );
        assertEquals("La quantité à acheter doit être strictement positive", exceptionZero.getMessage());

        IllegalArgumentException exceptionNegative = assertThrows(
            IllegalArgumentException.class, 
            () -> portfolio.acheter(action, -3)
        );
        assertEquals("La quantité à acheter doit être strictement positive", exceptionNegative.getMessage());
    }

     @Test
    void testAcheterWithValidParametersShouldAddAction() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action1 = new ActionSimple(ACTION_1_NAME);
        ActionSimple action2 = new ActionSimple(ACTION_2_NAME);
        
        portfolio.acheter(action1, 5);
        
        assertEquals(5, portfolio.getActions().get(action1), "Le portefeuille doit contenir 5 pour l'action achetée");

        portfolio.acheter(action2, 10);
        assertEquals(10, portfolio.getActions().get(action2), "Le portefeuille doit contenir 10 pour la seconde action achetée");
    }

    @Test
    void testAcheterExistingActionShouldIncrementQuantity() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action1 = new ActionSimple(ACTION_1_NAME);
        ActionSimple action2 = new ActionSimple(ACTION_2_NAME);
        
        portfolio.acheter(action1, 10);
        portfolio.acheter(action1, 5); 
        assertEquals(15, portfolio.getActions().get(action1), "La quantité totale doit être incrémentée (10 + 5 = 15)");

        portfolio.acheter(action2, 3);
        portfolio.acheter(action2, 7); 
        assertEquals(10, portfolio.getActions().get(action2), "La quantité totale doit être incrémentée (3 + 7 = 10)");
    }

    // --- Tests pour la méthode vendre ---

    @Test
    void testVendreWithNullActionShouldThrowException() {
        Portefeuille portfolio = new Portefeuille();
        assertThrows(NullPointerException.class, () -> portfolio.vendre(null, 10));
    }

    @Test
    void testVendreWithZeroOrNegativeQuantityShouldThrowException() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple(ACTION_1_NAME);
        portfolio.acheter(action, 10);
        
        assertAll("Vérification des quantités invalides à la vente",
            () -> assertThrows(IllegalArgumentException.class, () -> portfolio.vendre(action, 0)),
            () -> assertThrows(IllegalArgumentException.class, () -> portfolio.vendre(action, -5))
        );
    }

    @Test
    void testVendreMoreThanOwnedQuantityShouldThrowException() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple(ACTION_1_NAME);
        portfolio.acheter(action, 10);
        
        assertThrows(IllegalArgumentException.class, () -> portfolio.vendre(action, 15), 
            "La vente d'une quantité supérieure à la quantité détenue doit lever une exception");
    }

    @Test
    void testVendreExactQuantityShouldRemoveAction() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple(ACTION_1_NAME);
        portfolio.acheter(action, 10);
        
        portfolio.vendre(action, 10);
        
        assertFalse(portfolio.getActions().containsKey(action), 
            "L'action doit être supprimée du portefeuille si on vend toute la quantité détenue");
    }

    @Test
    void testVendrePartialQuantityShouldDecrementQuantity() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple(ACTION_1_NAME);
        portfolio.acheter(action, 10);
        
        portfolio.vendre(action, 3);
        
        assertEquals(7, portfolio.getActions().get(action), 
            "La quantité restante doit être la différence entre la quantité initiale et la quantité vendue (10 - 3 = 7)");
    }

    // --- Tests pour la méthode valeurPortefeuille ---

    @Test
    void testValeurPortefeuilleWithNullJourShouldThrowException() {
        Portefeuille portfolio = new Portefeuille();
        assertThrows(NullPointerException.class, () -> portfolio.valeurPortefeuille(null));
    }

    @Test
    void testValeurPortefeuilleEmptyShouldReturnZero() {
        Portefeuille portfolio = new Portefeuille();
        assertEquals(0f, portfolio.valeurPortefeuille(DEFAULT_JOUR), 
            "Un portefeuille vide doit valoir 0");
    }

    @Test
    void testValeurPortefeuilleShouldReturnCorrectSum() {
        Portefeuille portfolio = new Portefeuille();
        
        // Configuration de la première action
        ActionSimple action1 = new ActionSimple(ACTION_1_NAME);
        action1.enrgCours(DEFAULT_JOUR, 10.5f);
        portfolio.acheter(action1, 10); // Valeur = 10 * 10.5 = 105.0
        
        // Configuration de la deuxième action
        ActionSimple action2 = new ActionSimple(ACTION_2_NAME);
        action2.enrgCours(DEFAULT_JOUR, 20.0f);
        portfolio.acheter(action2, 5); // Valeur = 5 * 20.0 = 100.0
        
        // Valeur totale attendue : 105.0 + 100.0 = 205.0
        float expectedValue = 205.0f;
        assertEquals(expectedValue, portfolio.valeurPortefeuille(DEFAULT_JOUR), 0.001, 
            "La valeur du portefeuille doit être la somme pondérée des actions détenues");
    }

    @Test
    void testGetActionsOnNewPortefeuilleShouldReturnEmptyMap() {
        Portefeuille portfolio = new Portefeuille();
        assertEquals(0, portfolio.getActions().size(), "Un nouveau portefeuille ne doit contenir aucune action");
    }


    @Test
    void newPortefeuilleHasNoActions() {
        Portefeuille portfolio = new Portefeuille();
        Jour aujourdHui = new Jour(DAY, MONTH, YEAR);

       
        assertEquals(0, portfolio.getActions().size());

        
        assertEquals(0, portfolio.valeurPortefeuille(aujourdHui));
    }


    @Test
    void testAcheterMultipleDifferentActionsShouldStoreAllCorrectly() {
        // Given : Etant donné un portefeuille vide et plusieurs actions existent
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action1 = new ActionSimple("A1");
        ActionSimple action2 = new ActionSimple("A2");
        ActionSimple action3 = new ActionSimple("A3");

        // When : Lorsque j'achète des quantités différentes de chacune de ces actions
        portfolio.acheter(action1, 10);
        portfolio.acheter(action2, 4);
        portfolio.acheter(action3, 13);

        // Then : Alors le portefeuille contient bien toutes ces actions avec leurs quantités respectives
        Map<Action, Integer> actions = portfolio.getActions();
        
        assertAll("Vérification du portefeuille avec plusieurs actions différentes",
            () -> assertEquals(3, actions.size(), "Le portefeuille doit contenir bien 3 lignes (actions différentes)"),
            () -> assertEquals(10, actions.get(action1), "La quantité pour A1 doit être de 10"),
            () -> assertEquals(4, actions.get(action2), "La quantité pour A2 doit être de 4"),
            () -> assertEquals(13, actions.get(action3), "La quantité pour A3 doit être de 13")
        );
    }
}
