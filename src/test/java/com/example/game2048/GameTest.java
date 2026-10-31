package com.example.game2048;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void newGameHasTwoTiles() {
        Game g = new Game();
        int count = 0;
        for (int[] row : g.getBoard()) for (int v : row) if (v != 0) count++;
        assertEquals(2, count);
        assertEquals(0, g.getScore());
    }
}
