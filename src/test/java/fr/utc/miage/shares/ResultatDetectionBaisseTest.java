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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ResultatDetectionBaisseTest {

    private final Action action1 = new ActionSimple("AAPL");
    private final Action action2 = new ActionSimple("GOOG");

    @Test
    void constructeur_avecDonneesValides_creeObjet() {
        Map<Action, Double> baisse = Map.of(action1, -5.0);
        List<Action> manquantes = List.of(action2);

        ResultatDetectionBaisse resultat = new ResultatDetectionBaisse(baisse, manquantes);

        assertEquals(1, resultat.getActionsEnBaisse().size());
        assertEquals(1, resultat.getDonneesManquantes().size());
    }

    @Test
    void constructeur_avecActionsEnBaisseNull_leveException() {
        assertThrows(NullPointerException.class,
                () -> new ResultatDetectionBaisse(null, List.of()));
    }

    @Test
    void constructeur_avecDonneesManquantesNull_leveException() {
        assertThrows(NullPointerException.class,
                () -> new ResultatDetectionBaisse(Map.of(), null));
    }

    @Test
    void getActionsEnBaisse_retourneMapImmuable() {
        Map<Action, Double> baisse = Map.of(action1, -3.0);
        ResultatDetectionBaisse resultat = new ResultatDetectionBaisse(baisse, List.of());

        Map<Action, Double> retour = resultat.getActionsEnBaisse();

        assertEquals(-3.0, retour.get(action1));
        assertThrows(UnsupportedOperationException.class,
                () -> retour.put(action2, -1.0));
    }

    @Test
    void getDonneesManquantes_retourneListeImmuable() {
        List<Action> manquantes = List.of(action1);
        ResultatDetectionBaisse resultat = new ResultatDetectionBaisse(Map.of(), manquantes);

        List<Action> retour = resultat.getDonneesManquantes();

        assertEquals(action1, retour.get(0));
        assertThrows(UnsupportedOperationException.class,
                () -> retour.add(action2));
    }

    @Test
    void aucunePerte_sansActionEnBaisse_retourneTrue() {
        ResultatDetectionBaisse resultat = new ResultatDetectionBaisse(Map.of(), List.of());

        assertTrue(resultat.aucunePerte());
    }

    @Test
    void aucunePerte_avecActionEnBaisse_retourneFalse() {
        Map<Action, Double> baisse = Map.of(action1, -10.0);
        ResultatDetectionBaisse resultat = new ResultatDetectionBaisse(baisse, List.of());

        assertFalse(resultat.aucunePerte());
    }
}
