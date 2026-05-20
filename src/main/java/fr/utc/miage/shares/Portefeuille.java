package fr.utc.miage.shares;

import java.util.List;

/**
 * Cette classe représente un portfolio d'actions.
 *
 * @author Tom MEYER
 */
public class Portefeuille {
    private final List<Action> actions;

    /**
     * Créer un portfolio vide.
     */
    public Portefeuille() {
        this.actions = List.of();
    }

    /**
     * Retourne la liste des actions du portfolio.
     *
     * @return la liste des actions du portfolio
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Retourne la valeur du portfolio pour une jour donnée.
     *
     * @param j la jour pour laquelle on veut la valeur du portfolio
     * @return la valeur du portfolio pour la jour donnée
     */
    public float valeurPortefeuille(Jour j) {
        float somme = 0;
        for (Action action : actions) {
            somme += action.valeur(j);
        }
        return somme;
    }
}
