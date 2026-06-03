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

import java.util.HashMap;
import java.util.Map;

/**
 * Allows the creation of simple Action objects.
 *
 * @author David Navarre &lt;David.Navarre at irit.fr&gt;
 */
public class ActionSimple extends Action {

    private static final double DEFAULT_ACTION_VALUE = 0.0;

    // attribut lien
    private final Map<Jour, Double> mapCours;

    /**
     * Construit une ActionSimple à partir de son libellé.
     *
     * @param libelle le nom de l'action simple
     * @throws IllegalArgumentException si le libellé est null ou vide
     */
    public ActionSimple(final String libelle) {
        // Action simple initialisée comme 1 action
        super(libelle);
        if (libelle == null || libelle.equals("")) {
            throw new IllegalArgumentException("le libele est null");
        }
        // init spécifique
        this.mapCours = new HashMap<>();
    }

    /**
     * Enregistre le cours de l'action pour un jour donné, uniquement si aucun cours
     * n'existe déjà pour ce jour.
     *
     * @param j le jour concerné
     * @param v la valeur du cours à enregistrer
     */
    public void enrgCours(final Jour j, final double v) {
        this.mapCours.putIfAbsent(j, v);
    }

    @Override
    public double valeur(final Jour j) {
        if (this.mapCours.containsKey(j)) {
            return this.mapCours.get(j);
        } else {
            return DEFAULT_ACTION_VALUE;
        }
    }

    @Override
    public boolean hasCours(final Jour j) {
        return this.mapCours.containsKey(j);
    }
}
