package com.example.game2048;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private static final Set<String> DIRS = Set.of("up", "down", "left", "right");

    private Game game(HttpSession session) {
        Game g = (Game) session.getAttribute("game");
        if (g == null) {
            g = new Game();
            session.setAttribute("game", g);
        }
        return g;
    }

    @GetMapping("/state")
    public Game state(HttpSession session) {
        return game(session);
    }

    @PostMapping("/new")
    public Game newGame(HttpSession session) {
        Game g = game(session);
        g.reset();
        return g;
    }

    @PostMapping("/move/{dir}")
    public Game move(@PathVariable String dir, HttpSession session) {
        if (!DIRS.contains(dir))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Direction must be up, down, left or right");
        Game g = game(session);
        g.move(dir);
        return g;
    }
}
