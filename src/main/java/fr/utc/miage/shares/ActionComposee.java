/*
 * Copyright 2025 Tristan Delthil &lt;Tristan.Delthil at irit.fr&gt;.
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a composite action, which is a combination of simple actions with specified percentages. The value of the composite action for a given day is calculated as the weighted sum of the values of the simple actions based on their respective percentages.
 *
 * @author Tristan Delthil &lt;Tristan.Delthil at irit.fr&gt;
 */
public class ActionComposee extends Action {

    private static final double PERCENTAGE_TOTAL = 100.0;
    private static final double PERCENTAGE_TOLERANCE = 0.001;

    private final Map<ActionSimple, Double> composition;

    /**
     * Build a composite action from a label and a composition in percentages.
     *
     * @param libelle le label of the composite action
     * @param composition a map of simple actions and their corresponding percentages in the composite action
     * @throws NullPointerException if libelle or composition is null
     * @throws IllegalArgumentException if composition is empty or if the sum is not 100
     */
    public ActionComposee(final String libelle, final Map<ActionSimple, Double> composition) {
        super(Objects.requireNonNull(libelle, "Le libelle ne peut pas être null"));
        Objects.requireNonNull(composition, "La composition ne peut pas être null");

        if (composition.isEmpty()) {
            throw new IllegalArgumentException("La composition ne peut pas être vide");
        }

        double total = 0;
        for (Double pct : composition.values()) {
            total += pct;
        }

        if (Math.abs(total - PERCENTAGE_TOTAL) > PERCENTAGE_TOLERANCE) {
            throw new IllegalArgumentException(
                "Les pourcentages doivent s'additionner à 100%, obtenu: " + total
            );
        }

        this.composition = new HashMap<>(composition);
    }

    /**
     * Value of the composite action for a given day, calculated as the weighted sum of the values of the simple actions based on their respective percentages.
     *
     * @param j the day for which to calculate the value of the composite action
     * @return the value of the composite action for the given day
     */
    @Override
    public double valeur(final Jour j) {
        double total = 0;
        for (Map.Entry<ActionSimple, Double> entry : composition.entrySet()) {
            total += entry.getKey().valeur(j) * (entry.getValue() / PERCENTAGE_TOTAL);
        }
        return total;
    }
    
}