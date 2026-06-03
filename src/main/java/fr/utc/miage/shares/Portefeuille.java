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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Cette classe représente un portfolio d'actions.
 *
 * @author Tom MEYER
 */
public class Portefeuille {
    private final Map<Action, Integer> actions;

    /**
     * Crée un portfolio vide.
     */
    public Portefeuille() {
        this.actions = new HashMap<>();
    }

    /**
     * Retourne une copie de la composition du portefeuille (actions et quantités).
     *
     * @return une map contenant les actions et leurs quantités respectives
     */
    public Map<Action, Integer> getActions() {
        return new HashMap<>(actions);
    }

    /**
     * Ajoute une certaine quantité d'une action au portefeuille.
     * 
     * @param action   l'action à acheter
     * @param quantite la quantité à acheter (doit être > 0)
     * @throws NullPointerException     si l'action est null
     * @throws IllegalArgumentException si la quantité est inférieure ou égale à 0
     */
    public void acheter(final Action action, final int quantite) {
        Objects.requireNonNull(action, "L'action ne peut pas être null");
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité à acheter doit être strictement positive");
        }

        this.actions.put(action, this.actions.getOrDefault(action, 0) + quantite);
    }

    /**
     * Retire une certaine quantité d'une action du portefeuille.
     *
     * @param action   l'action à vendre
     * @param quantite la quantité à vendre (doit être &gt; 0)
     * @throws NullPointerException     si l'action est null
     * @throws IllegalArgumentException si la quantité est &lt;= 0 ou supérieure à
     *                                  la quantité détenue
     */
    public void vendre(final Action action, final int quantite) {
        Objects.requireNonNull(action, "L'action ne peut pas être null");
        if (quantite <= 0) {
            throw new IllegalArgumentException("La quantité à vendre doit être strictement positive");
        }

        int quantiteActuelle = this.actions.getOrDefault(action, 0);
        if (quantiteActuelle < quantite) {
            throw new IllegalArgumentException("Quantité insuffisante dans le portefeuille pour effectuer la vente");
        }

        if (quantiteActuelle == quantite) {
            this.actions.remove(action);
        } else {
            this.actions.put(action, quantiteActuelle - quantite);
        }
    }

    /**
     * Retourne la valeur totale du portefeuille pour un jour donné,
     * calculée en fonction des quantités détenues.
     *
     * @param j le jour pour lequel on veut la valeur du portefeuille
     * @return la valeur totale du portefeuille pour le jour donné
     */
    public float valeurPortefeuille(final Jour j) {
        Objects.requireNonNull(j, "Le jour ne peut pas être null");

        float somme = 0;
        for (Map.Entry<Action, Integer> entry : actions.entrySet()) {
            somme += entry.getKey().valeur(j) * entry.getValue();
        }
        return somme;
    }

    /**
     * Détecte les actions du portefeuille dont la variation de cours dépasse un seuil défini
     * entre deux jours consécutifs. La variation est calculée en valeur absolue : une hausse
     * ou une baisse dépassant le seuil génère une alerte.
     *
     * @param j1                le jour de référence initial
     * @param j2                le jour de comparaison
     * @param seuilPourcentage  le seuil de variation en pourcentage (ex. 10.0 pour 10%)
     * @return le résultat contenant les actions en alerte et les éventuelles données manquantes
     * @throws NullPointerException     si j1 ou j2 est null
     * @throws IllegalArgumentException si le seuil est négatif
     */
    public ResultatAlerteVariation detecterVariationsBrutales(final Jour j1, final Jour j2,
            final double seuilPourcentage) {
        Objects.requireNonNull(j1, "Le jour j1 ne peut pas être null");
        Objects.requireNonNull(j2, "Le jour j2 ne peut pas être null");
        if (seuilPourcentage < 0) {
            throw new IllegalArgumentException("Le seuil de variation doit être positif ou nul");
        }

        Map<Action, Double> actionsEnAlerte = new HashMap<>();
        List<Action> donneesManquantes = new ArrayList<>();

        for (Action action : actions.keySet()) {
            if (!action.hasCours(j1) || !action.hasCours(j2)) {
                donneesManquantes.add(action);
            } else {
                double v1 = action.valeur(j1);
                double v2 = action.valeur(j2);
                if (v1 == 0.0) {
                    donneesManquantes.add(action);
                    continue;
                }
                double variationPct = Math.abs((v2 - v1) / v1) * 100.0;
                if (variationPct >= seuilPourcentage) {
                    actionsEnAlerte.put(action, variationPct);
                }
            }
        }

        return new ResultatAlerteVariation(actionsEnAlerte, donneesManquantes);
    }

    /**
     * Détecte les actions du portefeuille dont le cours a baissé entre deux jours donnés.
     * Les actions dont les données sont absentes pour l'un des jours sont exclues du calcul
     * et signalées dans le résultat.
     *
     * @param j1 le jour de référence initial
     * @param j2 le jour de comparaison
     * @return le résultat contenant les actions en baisse et les éventuelles données manquantes
     * @throws NullPointerException si j1 ou j2 est null
     */
    public ResultatDetectionBaisse detecterActionsEnBaisse(final Jour j1, final Jour j2) {
        Objects.requireNonNull(j1, "Le jour j1 ne peut pas être null");
        Objects.requireNonNull(j2, "Le jour j2 ne peut pas être null");

        Map<Action, Double> actionsEnBaisse = new HashMap<>();
        List<Action> donneesManquantes = new ArrayList<>();

        for (Action action : actions.keySet()) {
            if (!action.hasCours(j1) || !action.hasCours(j2)) {
                donneesManquantes.add(action);
            } else {
                double v1 = action.valeur(j1);
                double v2 = action.valeur(j2);
                if (v2 < v1) {
                    actionsEnBaisse.put(action, v2 - v1);
                }
            }
        }

        return new ResultatDetectionBaisse(actionsEnBaisse, donneesManquantes);
    }
}
