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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import megamek.common.actions.PhysicalAttackAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import megamek.server.victory.VictoryResult;

class GameTest {

    private Game game;
    @BeforeEach
    void setUp() {
        game = new Game();
    }
    @Test
    void testCancelVictory() {
        // Default test
        Game game = new Game();
        game.cancelVictory();
        assertFalse(game.isForceVictory());
        assertSame(Player.PLAYER_NONE, game.getVictoryPlayerId());
        assertSame(Player.TEAM_NONE, game.getVictoryTeam());

        // Test with members set to specific values
        Game game2 = new Game();
        game2.setVictoryPlayerId(10);
        game2.setVictoryTeam(10);
        game2.setForceVictory(true);

        game2.cancelVictory();
        assertFalse(game.isForceVictory());
        assertSame(Player.PLAYER_NONE, game.getVictoryPlayerId());
        assertSame(Player.TEAM_NONE, game.getVictoryTeam());
    }

    @Test
    void testGetVictoryReport() {
        Game game = new Game();
        game.createVictoryConditions();
        VictoryResult victoryResult = game.getVictoryResult();
        assertNotNull(victoryResult);

        // Note: this accessors are tested in VictoryResultTest
        assertSame(Player.PLAYER_NONE, victoryResult.getWinningPlayer());
        assertSame(Player.TEAM_NONE, victoryResult.getWinningTeam());

        int winningPlayer = 2;
        int winningTeam = 5;

        // Test an actual scenario
        Game game2 = new Game();
        game2.setVictoryTeam(winningTeam);
        game2.setVictoryPlayerId(winningPlayer);
        game2.setForceVictory(true);
        game2.createVictoryConditions();
        VictoryResult victoryResult2 = game2.getVictoryResult();

        assertSame(winningPlayer, victoryResult2.getWinningPlayer());
        assertSame(winningTeam, victoryResult2.getWinningTeam());
    }

    @Test
    void testResetCharges() {
        PhysicalAttackAction charge = new PhysicalAttackAction(1, 1, 1);
        game.addCharge(charge);
        assertTrue(game.getCharges().hasMoreElements());

        game.resetCharges();
        assertEquals(0, game.getChargesVector().size());
    }

    @Test
    void testResetRams() {
        PhysicalAttackAction ram = new PhysicalAttackAction(1, 1, 1);
        game.addRam(ram);
        assertTrue(game.getRams().hasMoreElements());

        game.resetRams();
        assertEquals(0, game.getRamsVector().size());
    }

    @Test
    void testResetTeleMissileAttacks() {
        PhysicalAttackAction teleMissileAttack = new PhysicalAttackAction(1, 1, 1);
        game.addTeleMissileAttack(teleMissileAttack);
        assertTrue(game.getTeleMissileAttacks().hasMoreElements());

        game.resetTeleMissileAttacks();
        assertEquals(0, game.getTeleMissileAttacksVector().size());
    }

}
