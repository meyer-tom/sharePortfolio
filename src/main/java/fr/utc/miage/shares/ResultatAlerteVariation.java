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

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Résultat de la détection des actions dont la variation dépasse un seuil défini
 * entre deux jours consécutifs.
 *
 * @author Tom MEYER
 */
public class ResultatAlerteVariation {

    private final Map<Action, Double> actionsEnAlerte;
    private final List<Action> donneesManquantes;

    /**
     * Construit un résultat d'alerte de variation.
     *
     * @param actionsEnAlerte   map associant chaque action en alerte à sa variation en pourcentage
     * @param donneesManquantes liste des actions dont les données sont absentes pour l'un des jours
     */
    public ResultatAlerteVariation(final Map<Action, Double> actionsEnAlerte,
            final List<Action> donneesManquantes) {
        this.actionsEnAlerte = Collections.unmodifiableMap(
                new java.util.HashMap<>(java.util.Objects.requireNonNull(actionsEnAlerte, "actionsEnAlerte")));
        this.donneesManquantes = Collections.unmodifiableList(
                new java.util.ArrayList<>(java.util.Objects.requireNonNull(donneesManquantes, "donneesManquantes")));
    }

    /**
     * Retourne les actions en alerte avec leur variation en pourcentage.
     *
     * @return map action → variation en pourcentage (valeur positive)
     */
    public Map<Action, Double> getActionsEnAlerte() {
        return actionsEnAlerte;
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
     * Indique si aucune alerte n'a été générée.
     *
     * @return true si aucune action ne dépasse le seuil
     */
    public boolean aucuneAlerte() {
        return actionsEnAlerte.isEmpty();
    }
}
