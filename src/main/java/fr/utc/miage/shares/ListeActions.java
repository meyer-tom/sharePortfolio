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