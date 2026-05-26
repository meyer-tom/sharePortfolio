package fr.utc.miage.shares;

import java.util.List;

public class ListeActions {
    List<Action> actions;

    public ListeActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Action a : actions) {
            String type = (a instanceof ActionSimple) ? "Simple" : "Composée";
            sb.append(a.getLibelle()).append(" [").append(type).append("]\n");
        }
        return sb.toString();
    }

    public void ajouterAction(Action a) {
        actions.add(a);
    }

    public void supprimerAction(Action a) {
        actions.remove(a);
    }
}