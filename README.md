# 2048 – Spring Boot

Requirements: JDK 17+, Maven 3.6+

    mvn spring-boot:run

Open http://localhost:8080

REST API (session-based):
- GET  /api/game/state
- POST /api/game/new
- POST /api/game/move/{up|down|left|right}
