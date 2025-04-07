/*
 * Copyright (c) 2024 - The MegaMek Team. All Rights Reserved.
 *
 * This file is part of MegaMek.
 *
 * MegaMek is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MegaMek is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MegaMek. If not, see <http://www.gnu.org/licenses/>.
 */
package megamek.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import megamek.common.enums.GamePhase;
import org.junit.jupiter.api.Test;

import megamek.client.ui.swing.util.PlayerColour;
import org.mockito.Mockito;

class PlayerTest {

    @Test
    void testGetColorForPlayerDefault() {
        String playerName = "Test Player 1";
        Player player = new Player(0, playerName);
        assertEquals("<B><font color='8080b0'>" + playerName + "</font></B>", player.getColorForPlayer());
    }

    @Test
    void testGetColorForPlayerFuchsia() {
        String playerName = "Test Player 2";
        Player player = new Player(1, playerName);
        player.setColour(PlayerColour.FUCHSIA);
        assertEquals("<B><font color='f000f0'>" + playerName + "</font></B>", player.getColorForPlayer());
    }

    @Test
    public void testIsObserverWhenVictoryPhase() {
        Player player = new Player(1, "TestPlayer");

        IGame mockGame = Mockito.mock(IGame.class);
        GamePhase mockPhase = Mockito.mock(GamePhase.class);

        Mockito.when(mockGame.getPhase()).thenReturn(mockPhase);
        Mockito.when(mockPhase.isVictory()).thenReturn(true);

        player.setGame(mockGame);

        assertFalse(player.isObserver());
    }

    @Test
    public void testIsObserverWhenNotVictoryPhase() {
        Player player = new Player(1, "TestPlayer");

        IGame mockGame = Mockito.mock(IGame.class);
        GamePhase mockPhase = Mockito.mock(GamePhase.class);

        Mockito.when(mockGame.getPhase()).thenReturn(mockPhase);
        Mockito.when(mockPhase.isVictory()).thenReturn(false);

        player.setGame(mockGame);

        assertTrue(player.isObserver());
    }

    @Test
    public void testIsObserverWhenGameIsNull() {
        Player player = new Player(1, "TestPlayer");

        player.setGame(null);

        assertTrue(player.isObserver());
    }

    @Test
    public void testAdjustStartingPosForReinforcementsAboveThreshold() {
        Player player = new Player(1, "TestPlayer");
        player.setStartingPos(15);

        player.adjustStartingPosForReinforcements();

        assertEquals(5, player.getStartingPos(),
              "La position de départ devrait être réduite de 10.");
    }

    @Test
    public void testAdjustStartingPosForReinforcementsBelowThreshold() {
        Player player = new Player(1, "TestPlayer");
        player.setStartingPos(8);

        player.adjustStartingPosForReinforcements();

        assertEquals(8, player.getStartingPos(),
              "La position de départ ne devrait pas changer si elle est inférieure au seuil.");
    }

    @Test
    public void testAdjustStartingPosForReinforcementsEqualThreshold() {
        Player player = new Player(1, "TestPlayer");

        player.setStartingPos(10);

        System.out.println("Position avant ajustement: " + player.getStartingPos());
        assertEquals(10, player.getStartingPos(),
              "La position de départ devrait être correctement définie avant l'ajustement.");

        // Appeler la méthode pour ajuster la position de départ
        player.adjustStartingPosForReinforcements();

        // Vérifier que la position de départ reste inchangée
        System.out.println("Position après ajustement: " + player.getStartingPos());
        assertEquals(10, player.getStartingPos(),
              "La position de départ ne devrait pas changer si elle est égale au seuil.");
    }
}
