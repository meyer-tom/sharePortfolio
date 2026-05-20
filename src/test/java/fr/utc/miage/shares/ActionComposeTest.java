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

/**
 * This class contains unit tests for the {@link ActionComposee} class.
 * It verifies the constructor's validation rules, including null checks,
 * empty composition checks, and the percentage sum validation.
 *
 * @author Selim Hamza <Selim.Hamza at irit.fr>
 */
class ActionComposeeTest {

    /**
     * Utility method to generate a valid composition map where the sum of percentages equals 100%.
     * * @return a valid composition map of {@link ActionSimple} and their corresponding percentages
     */
    private Map<ActionSimple, Double> getValidComposition() {
        Map<ActionSimple, Double> composition = new HashMap<>();
        composition.put(new ActionSimple("Action 1"), 60.0);
        composition.put(new ActionSimple("Action 2"), 40.0);
        return composition;
    }

    /**
     * Tests that the constructor throws a {@link NullPointerException} 
     * when the provided label (libelle) is null.
     */
    @Test
    void testConstructorWithNullLibelleShouldThrowException() {
        Map<ActionSimple, Double> compositionValide = getValidComposition();
        assertThrows(NullPointerException.class, () -> new ActionComposee(null, compositionValide));
    }

    /**
     * Tests that the constructor throws a {@link NullPointerException} 
     * when the provided composition map is null.
     */
    @Test
    void testConstructorWithNullCompositionShouldThrowException() {
        assertThrows(NullPointerException.class, () -> new ActionComposee("Action Composee", null));
    }

    /**
     * Tests that the constructor throws an {@link IllegalArgumentException} 
     * when the provided composition map is empty.
     */
    @Test
    void testConstructorWithEmptyCompositionShouldThrowException() {
        Map<ActionSimple, Double> emptyComposition = new HashMap<>();
        assertThrows(IllegalArgumentException.class, () -> new ActionComposee("Action Composee", emptyComposition));
    }

    /**
     * Tests that the constructor throws an {@link IllegalArgumentException} 
     * when the sum of the percentages in the composition map is not exactly 100%.
     */
    @Test
    void testConstructorWithInvalidPercentageSumShouldThrowException() {
        Map<ActionSimple, Double> invalidComposition = new HashMap<>();
        invalidComposition.put(new ActionSimple("Action 1"), 50.0);
        invalidComposition.put(new ActionSimple("Action 2"), 30.0); // Somme = 80% (différent de 100%)
        
        assertThrows(IllegalArgumentException.class, () -> new ActionComposee("Action Composee", invalidComposition));
    }

    /**
     * Tests that the constructor successfully creates an {@link ActionComposee} 
     * instance without throwing any exception when all parameters are valid.
     */
    @Test
    void testConstructorWithValidParametersShouldNotThrowException() {
        Map<ActionSimple, Double> compositionValide = getValidComposition();
        assertDoesNotThrow(() -> new ActionComposee("Action Composee", compositionValide));
    }
}