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

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Résultat de la détection des actions en baisse dans un portefeuille entre deux jours.
 *
 * @author Tom MEYER
 */
public class ResultatDetectionBaisse {

    private final Map<Action, Double> actionsEnBaisse;
    private final List<Action> donneesManquantes;

    /**
     * Construit un résultat de détection de baisse.
     *
     * @param actionsEnBaisse map associant chaque action en baisse à sa variation (valeur négative)
     * @param donneesManquantes liste des actions dont les données sont absentes pour l'un des jours
     */
    public ResultatDetectionBaisse(final Map<Action, Double> actionsEnBaisse,
            final List<Action> donneesManquantes) {
        this.actionsEnBaisse = Collections.unmodifiableMap(actionsEnBaisse);
        this.donneesManquantes = Collections.unmodifiableList(donneesManquantes);
    }

    /**
     * Retourne les actions en baisse avec leur variation (valeur négative).
     *
     * @return map action → variation
     */
    public Map<Action, Double> getActionsEnBaisse() {
        return actionsEnBaisse;
    }

    /**
     * Retourne la liste des actions exclues du calcul par manque de données.
     *
     * @return liste des actions avec données manquantes
     */
    public List<Action> getDonneesManquantes() {
        return donneesManquantes;
    }

    /**
     * Indique si aucune perte n'a été détectée.
     *
     * @return true si aucune action n'est en baisse
     */
    public boolean aucunePerte() {
        return actionsEnBaisse.isEmpty();
    }
}
