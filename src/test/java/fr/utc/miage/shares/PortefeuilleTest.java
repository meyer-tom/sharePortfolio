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

    private static final Jour JOUR_1 = new Jour(1, 1, 2026);
    private static final Jour JOUR_2 = new Jour(2, 1, 2026);

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

        assertAll("Vérification des quantités invalides à l'achat",
                () -> assertThrows(IllegalArgumentException.class, () -> portfolio.acheter(action, 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> portfolio.acheter(action, -5)));
    }

    @Test
    void testAcheterWithValidParametersShouldAddAction() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple(ACTION_1_NAME);

        portfolio.acheter(action, 10);

        Map<Action, Integer> actions = portfolio.getActions();
        assertAll("Vérification de l'ajout d'une action",
                () -> assertEquals(1, actions.size(), "Le portefeuille doit contenir 1 action"),
                () -> assertTrue(actions.containsKey(action), "Le portefeuille doit contenir l'action achetée"),
                () -> assertEquals(10, actions.get(action), "La quantité de l'action doit être 10"));
    }

    @Test
    void testAcheterExistingActionShouldIncrementQuantity() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple(ACTION_1_NAME);

        portfolio.acheter(action, 10);
        portfolio.acheter(action, 5);

        assertEquals(15, portfolio.getActions().get(action), "La quantité totale doit être incrémentée (10 + 5 = 15)");
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
                () -> assertThrows(IllegalArgumentException.class, () -> portfolio.vendre(action, -5)));
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
    void testGetActionsOnNonEmptyPortefeuilleShouldReturnMapWithActions() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action1 = new ActionSimple(ACTION_1_NAME);
        ActionSimple action2 = new ActionSimple(ACTION_2_NAME);

        portfolio.acheter(action1, 10);
        portfolio.acheter(action2, 5);

        Map<Action, Integer> actions = portfolio.getActions();
        assertAll("Vérification des actions dans le portefeuille",
                () -> assertEquals(2, actions.size(), "Le portefeuille doit contenir 2 actions"),
                () -> assertTrue(actions.containsKey(action1), "Le portefeuille doit contenir l'action 1"),
                () -> assertTrue(actions.containsKey(action2), "Le portefeuille doit contenir l'action 2"),
                () -> assertEquals(10, actions.get(action1), "La quantité de l'action 1 doit être 10"),
                () -> assertEquals(5, actions.get(action2), "La quantité de l'action 2 doit être 5"));
    }

    @Test
    void testAcheterMultipleDifferentActionsShouldStoreAllCorrectly() {

        Portefeuille portfolio = new Portefeuille();
        ActionSimple action1 = new ActionSimple("A1");
        ActionSimple action2 = new ActionSimple("A2");
        ActionSimple action3 = new ActionSimple("A3");

        portfolio.acheter(action1, 10);
        portfolio.acheter(action2, 4);
        portfolio.acheter(action3, 13);

        Map<Action, Integer> actions = portfolio.getActions();

        assertAll("Vérification du portefeuille avec plusieurs actions différentes",
                () -> assertEquals(3, actions.size(),
                        "Le portefeuille doit contenir bien 3 lignes (actions différentes)"),
                () -> assertEquals(10, actions.get(action1), "La quantité pour A1 doit être de 10"),
                () -> assertEquals(4, actions.get(action2), "La quantité pour A2 doit être de 4"),
                () -> assertEquals(13, actions.get(action3), "La quantité pour A3 doit être de 13"));
    }

    // --- Tests pour detecterVariationsBrutales ---

    @Test
    void testDetecterVariationsBrutalesScenario1HausseDépasseSeuil() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple actionA = new ActionSimple("Action A");
        actionA.enrgCours(JOUR_1, 100.0);
        actionA.enrgCours(JOUR_2, 120.0);
        portfolio.acheter(actionA, 1);

        ResultatAlerteVariation resultat = portfolio.detecterVariationsBrutales(JOUR_1, JOUR_2, 10.0);

        assertTrue(resultat.getActionsEnAlerte().containsKey(actionA),
                "Une alerte doit être générée pour l'action A (hausse de 20% > seuil 10%)");
    }

    @Test
    void testDetecterVariationsBrutalesScenario2BaisseDépasseSeuil() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple actionA = new ActionSimple("Action A");
        actionA.enrgCours(JOUR_1, 100.0);
        actionA.enrgCours(JOUR_2, 85.0);
        portfolio.acheter(actionA, 1);

        ResultatAlerteVariation resultat = portfolio.detecterVariationsBrutales(JOUR_1, JOUR_2, 10.0);

        assertTrue(resultat.getActionsEnAlerte().containsKey(actionA),
                "Une alerte doit être générée pour l'action A (baisse de 15% > seuil 10%)");
    }

    @Test
    void testDetecterVariationsBrutalesScenario3VariationInférieureSeuil() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple actionA = new ActionSimple("Action A");
        actionA.enrgCours(JOUR_1, 100.0);
        actionA.enrgCours(JOUR_2, 105.0);
        portfolio.acheter(actionA, 1);

        ResultatAlerteVariation resultat = portfolio.detecterVariationsBrutales(JOUR_1, JOUR_2, 10.0);

        assertAll("Aucune alerte ne doit être générée (variation 5% < seuil 10%)",
                () -> assertFalse(resultat.getActionsEnAlerte().containsKey(actionA)),
                () -> assertTrue(resultat.aucuneAlerte()));
    }

    @Test
    void testDetecterVariationsBrutalesScenario4VariationEgaleSeuil() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple actionA = new ActionSimple("Action A");
        actionA.enrgCours(JOUR_1, 100.0);
        actionA.enrgCours(JOUR_2, 110.0);
        portfolio.acheter(actionA, 1);

        ResultatAlerteVariation resultat = portfolio.detecterVariationsBrutales(JOUR_1, JOUR_2, 10.0);

        assertAll("Une alerte doit être générée (variation 10% = seuil 10%)",
                () -> assertTrue(resultat.getActionsEnAlerte().containsKey(actionA)),
                () -> assertEquals(10.0, resultat.getActionsEnAlerte().get(actionA), 0.001));
    }

    @Test
    void testDetecterVariationsBrutalesScenario5PlusieursActionsPortefeuille() {
        Portefeuille portfolio = new Portefeuille();

        ActionSimple actionA = new ActionSimple("Action A");
        actionA.enrgCours(JOUR_1, 100.0);
        actionA.enrgCours(JOUR_2, 120.0);

        ActionSimple actionB = new ActionSimple("Action B");
        actionB.enrgCours(JOUR_1, 200.0);
        actionB.enrgCours(JOUR_2, 170.0);

        ActionSimple actionC = new ActionSimple("Action C");
        actionC.enrgCours(JOUR_1, 50.0);
        actionC.enrgCours(JOUR_2, 52.0);

        portfolio.acheter(actionA, 1);
        portfolio.acheter(actionB, 1);
        portfolio.acheter(actionC, 1);

        ResultatAlerteVariation resultat = portfolio.detecterVariationsBrutales(JOUR_1, JOUR_2, 10.0);

        assertAll("Les alertes doivent être générées uniquement pour A et B",
                () -> assertTrue(resultat.getActionsEnAlerte().containsKey(actionA),
                        "Alerte attendue pour l'action A (hausse 20%)"),
                () -> assertTrue(resultat.getActionsEnAlerte().containsKey(actionB),
                        "Alerte attendue pour l'action B (baisse 15%)"),
                () -> assertFalse(resultat.getActionsEnAlerte().containsKey(actionC),
                        "Aucune alerte pour l'action C (variation 4%)"));
    }

    @Test
    void testDetecterVariationsBrutalesAvecJ1NullLeveException() {
        Portefeuille portfolio = new Portefeuille();
        assertThrows(NullPointerException.class,
                () -> portfolio.detecterVariationsBrutales(null, JOUR_2, 10.0));
    }

    @Test
    void testDetecterVariationsBrutalesAvecJ2NullLeveException() {
        Portefeuille portfolio = new Portefeuille();
        assertThrows(NullPointerException.class,
                () -> portfolio.detecterVariationsBrutales(JOUR_1, null, 10.0));
    }

    @Test
    void testDetecterVariationsBrutalesAvecSeuilNegatifLeveException() {
        Portefeuille portfolio = new Portefeuille();
        assertThrows(IllegalArgumentException.class,
                () -> portfolio.detecterVariationsBrutales(JOUR_1, JOUR_2, -1.0));
    }

    @Test
    void testDetecterVariationsBrutalesActionSansCoursEnDonneesManquantes() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple actionSansCours = new ActionSimple("Sans cours");
        portfolio.acheter(actionSansCours, 1);

        ResultatAlerteVariation resultat = portfolio.detecterVariationsBrutales(JOUR_1, JOUR_2, 10.0);

        assertAll(
                () -> assertTrue(resultat.getDonneesManquantes().contains(actionSansCours)),
                () -> assertTrue(resultat.aucuneAlerte()));
    }

    @Test
    void testDetecterVariationsBrutalesAvecV1EgalZeroMetsEnDonneesManquantes() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple("Action zero");
        action.enrgCours(JOUR_1, 0.0);
        action.enrgCours(JOUR_2, 10.0);
        portfolio.acheter(action, 1);

        ResultatAlerteVariation resultat = portfolio.detecterVariationsBrutales(JOUR_1, JOUR_2, 10.0);

        assertAll(
                () -> assertTrue(resultat.getDonneesManquantes().contains(action)),
                () -> assertTrue(resultat.aucuneAlerte()));
    }

    // --- Tests pour detecterActionsEnBaisse ---

    @Test
    void testDetecterActionsEnBaisseAvecJ1NullLeveException() {
        Portefeuille portfolio = new Portefeuille();
        assertThrows(NullPointerException.class,
                () -> portfolio.detecterActionsEnBaisse(null, JOUR_2));
    }

    @Test
    void testDetecterActionsEnBaisseAvecJ2NullLeveException() {
        Portefeuille portfolio = new Portefeuille();
        assertThrows(NullPointerException.class,
                () -> portfolio.detecterActionsEnBaisse(JOUR_1, null));
    }

    @Test
    void testDetecterActionsEnBaissePortefeuilleVideRetourneAucunePerte() {
        Portefeuille portfolio = new Portefeuille();
        ResultatDetectionBaisse resultat = portfolio.detecterActionsEnBaisse(JOUR_1, JOUR_2);
        assertTrue(resultat.aucunePerte());
    }

    @Test
    void testDetecterActionsEnBaisseActionEnBaisseDetectee() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple("Action baisse");
        action.enrgCours(JOUR_1, 100.0);
        action.enrgCours(JOUR_2, 80.0);
        portfolio.acheter(action, 1);

        ResultatDetectionBaisse resultat = portfolio.detecterActionsEnBaisse(JOUR_1, JOUR_2);

        assertAll(
                () -> assertFalse(resultat.aucunePerte()),
                () -> assertTrue(resultat.getActionsEnBaisse().containsKey(action)),
                () -> assertEquals(-20.0, resultat.getActionsEnBaisse().get(action), 0.001));
    }

    @Test
    void testDetecterActionsEnBaisseActionEnHausseNonDetectee() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple("Action hausse");
        action.enrgCours(JOUR_1, 80.0);
        action.enrgCours(JOUR_2, 100.0);
        portfolio.acheter(action, 1);

        ResultatDetectionBaisse resultat = portfolio.detecterActionsEnBaisse(JOUR_1, JOUR_2);

        assertTrue(resultat.aucunePerte());
    }

    @Test
    void testDetecterActionsEnBaisseActionStableNonDetectee() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple("Action stable");
        action.enrgCours(JOUR_1, 100.0);
        action.enrgCours(JOUR_2, 100.0);
        portfolio.acheter(action, 1);

        ResultatDetectionBaisse resultat = portfolio.detecterActionsEnBaisse(JOUR_1, JOUR_2);

        assertTrue(resultat.aucunePerte());
    }

    @Test
    void testDetecterActionsEnBaisseActionSansCoursEnDonneesManquantes() {
        Portefeuille portfolio = new Portefeuille();
        ActionSimple action = new ActionSimple("Sans cours");
        portfolio.acheter(action, 1);

        ResultatDetectionBaisse resultat = portfolio.detecterActionsEnBaisse(JOUR_1, JOUR_2);

        assertAll(
                () -> assertTrue(resultat.getDonneesManquantes().contains(action)),
                () -> assertTrue(resultat.aucunePerte()));
    }
}
