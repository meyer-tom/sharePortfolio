/*
 * Copyright 2025 Selim Hamza <Selim.Hamza at irit.fr>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.utc.miage.shares;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ActionComposeeTest {

    private static final String LIBELLE_NULL = null;
    private static final String LIBELLE_UN = "ActionComposeeTest1";
    private static final String LIBELLE_DEUX = "ActionComposeeTest2";
    private static final String LIBELLE_TROIS = "ActionComposeeTest3";
    private static final double VALUE_UN = 50.0;
    private static final double VALUE_DEUX = 60.0;
    private static final double VALUE_TROIS = 40.0;
    private static final double VALUE_QUATRE = -20.0;
    private static final double VALUE_CINQ = -10.0;
    private static final Jour JOUR_TEST = new Jour(1, 1, 2026);

    @Test
    void testConstructorWithValidComposition() {

        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_UN);

        assertDoesNotThrow(() -> new ActionComposee(LIBELLE_UN, localComposition));
    }

    @Test
    void testConstructorWithNullLibelle() {
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_DEUX);

        assertThrows(NullPointerException.class, () -> new ActionComposee(LIBELLE_NULL, localComposition));
    }

    @Test
    void testConstructorWithNullComposition() {
        assertThrows(NullPointerException.class, () -> new ActionComposee(LIBELLE_UN, null));
    }

    @Test
    void testConstructorWithAdditionalPercentsSuperiorTo100() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, 60.0);
        localComposition.put(action2, 50.0);
        assertThrows(IllegalArgumentException.class, () -> new ActionComposee(LIBELLE_UN, localComposition));
    }

    @Test
    void testConstructorWithAdditionalPercentsInferiorTo100() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_TROIS);
        assertThrows(IllegalArgumentException.class, () -> new ActionComposee(LIBELLE_UN, localComposition));
    }

    @Test
    void testConstructorWithNegativePercents() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_QUATRE);
        localComposition.put(action2, VALUE_CINQ);
        assertThrows(IllegalArgumentException.class, () -> new ActionComposee(LIBELLE_UN, localComposition));
    }

    @Test
    void testValeurShouldWork() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_UN);
        ActionComposee actionComposee = new ActionComposee(LIBELLE_UN, localComposition);

        assertDoesNotThrow(() -> actionComposee.valeur(JOUR_TEST));
    }

    @Test
    void testValeurShouldNotWork() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_UN); // 50.0
        localComposition.put(action2, VALUE_UN); // 50.0

        ActionComposee actionComposee = new ActionComposee(LIBELLE_UN, localComposition);

        assertNotEquals(VALUE_UN, actionComposee.valeur(JOUR_TEST));
    }

    @Test
    void testEditCompositionShouldWork() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_UN);
        ActionComposee actionComposee = new ActionComposee(LIBELLE_UN, localComposition);

        Map<ActionSimple, Double> newComposition = new HashMap<>();
        newComposition.put(action1, VALUE_DEUX);
        newComposition.put(action2, VALUE_TROIS);

        assertDoesNotThrow(() -> actionComposee.editComposition(newComposition));
    }

    @Test
    void testEditCompositionShouldNotWorkWithNewCompositionNull() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_UN);
        ActionComposee actionComposee = new ActionComposee(LIBELLE_UN, localComposition);

        assertThrows(NullPointerException.class, () -> actionComposee.editComposition(null));
    }

    @Test
    void testEditCompositionShouldNotWorkWithNewCompositionEmpty() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_UN);
        ActionComposee actionComposee = new ActionComposee(LIBELLE_UN, localComposition);

        Map<ActionSimple, Double> newComposition = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> actionComposee.editComposition(newComposition));
    }

    @Test
    void testEditCompositionShouldNotWorkWithNewCompositionSumNot100() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_UN);
        ActionComposee actionComposee = new ActionComposee(LIBELLE_UN, localComposition);

        Map<ActionSimple, Double> newComposition = new HashMap<>();
        newComposition.put(action1, VALUE_DEUX); // 60.0
        newComposition.put(action2, VALUE_DEUX); // 60.0

        assertThrows(IllegalArgumentException.class, () -> actionComposee.editComposition(newComposition));
    }

    @Test
    void testEditCompositionShouldNotWorkWithNewCompositionNegativePercents() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_UN);
        ActionComposee actionComposee = new ActionComposee(LIBELLE_UN, localComposition);

        Map<ActionSimple, Double> newComposition = new HashMap<>();
        newComposition.put(action1, VALUE_QUATRE); // -20.0
        newComposition.put(action2, VALUE_CINQ); // -10.0

        assertThrows(IllegalArgumentException.class, () -> actionComposee.editComposition(newComposition));
    }

    @Test
    void testEditCompositionShouldNotWorkWithNewCompositionDifferentActions() {
        Map<ActionSimple, Double> localComposition = new HashMap<>();
        ActionSimple action1 = new ActionSimple(LIBELLE_UN);
        ActionSimple action2 = new ActionSimple(LIBELLE_DEUX);
        localComposition.put(action1, VALUE_UN);
        localComposition.put(action2, VALUE_UN);
        ActionComposee actionComposee = new ActionComposee(LIBELLE_UN, localComposition);

        Map<ActionSimple, Double> newComposition = new HashMap<>();
        ActionSimple action3 = new ActionSimple(LIBELLE_TROIS); // same label but different instance
        newComposition.put(action1, VALUE_UN);
        newComposition.put(action3, VALUE_UN);

        assertThrows(IllegalArgumentException.class, () -> actionComposee.editComposition(newComposition));
    }

}