/*
 * Copyright 2026 Tom Meyer.
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
