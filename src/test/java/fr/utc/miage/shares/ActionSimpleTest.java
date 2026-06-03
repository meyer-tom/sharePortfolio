/*
 * Copyright 2025 David Navarre &lt;David.Navarre at irit.fr&gt;.
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

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ActionSimpleTest {

    private static final String FOO_SHARE1 = "Foo Share 1";
    private static final String FOO_EMPTY = "";
    private static final Jour JOUR_1 = new Jour(1,1,2026);
    private static final int VALEUR = 10;
    private static final int VALEUR_0 = 0;

    @Test
    void testConstructor() {
        Assertions.assertDoesNotThrow(() -> {
            new ActionSimple(FOO_SHARE1);
        });
    }

    @Test
    void testConstructorWithoutLabel(){
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            new ActionSimple(FOO_EMPTY);
        });
    }

    @Test
    void testEnrgCoursShouldWork() {
        final ActionSimple action = new ActionSimple(FOO_SHARE1);
        Assertions.assertDoesNotThrow(() -> {
            action.enrgCours(JOUR_1, VALEUR);
        });
    }

    @Test
    void testValeurShouldWork() {
        final ActionSimple action = new ActionSimple(FOO_SHARE1);

        action.enrgCours(JOUR_1, VALEUR);

        assertEquals(VALEUR, action.valeur(JOUR_1));

    }

    @Test
    void testValeurShouldReturnZero() {
        final ActionSimple action = new ActionSimple(FOO_SHARE1);

        assertEquals(VALEUR_0, action.valeur(JOUR_1));
    }

    @Test
    void testEnrgCoursShouldNotWork() {
        final ActionSimple action = new ActionSimple(FOO_SHARE1);

        action.enrgCours(JOUR_1, VALEUR);

        Assertions.assertDoesNotThrow(() -> {
            action.enrgCours(JOUR_1, VALEUR_0);
        });
    }

    @Test
    void testActionSimpleCouldBeDeleted() {
        final ActionSimple action = new ActionSimple(FOO_SHARE1);
        final List<Action> actions = new ArrayList<>();

        actions.add(action);
        final ListeActions liste = new ListeActions(actions);

        liste.supprimerAction(action);

        Assertions.assertFalse(liste.getActions().contains(action));

    }


}
