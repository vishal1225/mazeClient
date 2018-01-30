package com.systema.maze.client;


import com.systema.maze.domain.MazeGame;
import com.systema.maze.solver.MazeSolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by vssharma on 25/01/2018.
 */
@SpringBootApplication
public class MazeClient {
    private static final Logger log = LoggerFactory.getLogger(MazeClient.class);
    private Map<String,MazeGame> mazeGameMap = new HashMap<>();

    private RestTemplate restTemplate = new RestTemplate();

    public static void main(String args[]) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(MazeClient.class);
//        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("ausproxy.au.adp.com", 8080));
//        clientHttpRequestFactory.setProxy(proxy);
        String choice = null;
        Scanner scan = new Scanner(System.in);
        showchoice(scan);
        String id = "";
        do {
            choice = scan.nextLine();
            switch (choice) {
                case "a":
                    applicationContext.getBean(MazeClient.class).createGame();
                    break;
                case "b":
                    applicationContext.getBean(MazeClient.class).askForGameId();
                     id = scan.nextLine();
                    applicationContext.getBean(MazeClient.class).moveNorth(id);
                    break;
                case "c":
                    applicationContext.getBean(MazeClient.class).askForGameId();
                    id = scan.nextLine();
                    applicationContext.getBean(MazeClient.class).moveSouth(id);
                    break;
                case "d":
                    applicationContext.getBean(MazeClient.class).askForGameId();
                    id = scan.nextLine();
                    applicationContext.getBean(MazeClient.class).moveEast(id);
                    break;
                case "e":
                    applicationContext.getBean(MazeClient.class).askForGameId();
                    id = scan.nextLine();
                    applicationContext.getBean(MazeClient.class).moveWest(id);
                    break;
                case "f":
                    applicationContext.getBean(MazeClient.class).askForGameId();
                    id = scan.nextLine();
                    applicationContext.getBean(MazeClient.class).solve(id);
                    break;
                case "default":
                    scan.close();
                    System.exit(1);
            }
        } while (!choice.equals("q"));
    }

    private void askForGameId() {
        System.out.println("Enter Game ID.");
    }

    private static void showchoice(Scanner scan) {
        System.out.println("Enter Choice");
        System.out.println("a. create new game.");
        System.out.println("b. move North");
        System.out.println("c. move South");
        System.out.println("d. move East");
        System.out.println("e. move West");
        System.out.println("f. Solve Game");
    }

    private void moveEast(String id) {
        if (validateMazeId(id)) return;
        ResponseEntity<MazeGame> response = restTemplate.postForEntity("http://maze.systema.ai/game/"+id+"/move/east", null, MazeGame.class);
        analyseMove(response);
    }

    private void analyseMove(ResponseEntity<MazeGame> response) {
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            MazeGame mazeGame = response.getBody();
            System.out.println("Move Successfull. " + mazeGame.getGame_id());
            mazeGameMap.put(mazeGame.getGame_id(), mazeGame);
        } else if (HttpStatus.FORBIDDEN.equals(response.getStatusCode())) {
            System.out.println("Move not allowed.");
        } else if (HttpStatus.NOT_FOUND.equals(response.getStatusCode())) {
            System.out.println("Game not found.");
        }
    }

    private void moveWest(String id) {
        if (validateMazeId(id)) return;
        ResponseEntity<MazeGame> response = restTemplate.postForEntity("http://maze.systema.ai/game/"+id+"/move/west", null, MazeGame.class);
        analyseMove(response);
    }

    private MazeGame getGamefromCache(String id) {
        return mazeGameMap.get(id);
    }

    private void moveSouth(String id) {
        if (validateMazeId(id)) return;
        ResponseEntity<MazeGame> response = restTemplate.postForEntity("http://maze.systema.ai/game/"+id+"/move/south",null, MazeGame.class);
        analyseMove(response);
    }

    private  void solve(String id) {
        MazeGame mazeGame = getGamefromCache(id);
        if (mazeGame == null) {
            System.out.println("Enter valid game Id.");
            return;
        }
        MazeSolver mazeSolver = new MazeSolver(mazeGame);
        String label = String.valueOf(mazeGame.getCurrentX()).concat(String.valueOf(mazeGame.getCurrentY()));
        mazeSolver.solve(mazeGame.getGraph().getVertex(label));
        log.info(mazeGame.toString());
    }

    private  void moveNorth(String id) {
        if (validateMazeId(id)) return;
        ResponseEntity<MazeGame> response = restTemplate.postForEntity("http://maze.systema.ai/game/"+id+"/move/north",null, MazeGame.class);
        analyseMove(response);

    }

    private boolean validateMazeId(String id) {
        MazeGame mazeGame = getGamefromCache(id);
        if (mazeGame == null) {
            System.out.println("Enter valid game Id.");
            return true;
        }
        return false;
    }

    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    private void createGame() throws Exception {

        try {
            ResponseEntity<MazeGame> response = restTemplate.postForEntity("http://maze.systema.ai/game", null, MazeGame.class);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                MazeGame mazeGame = response.getBody();
                System.out.println("Game created with Id. " + mazeGame.getGame_id());
                mazeGameMap.put(mazeGame.getGame_id(), mazeGame);
            } else {
                System.out.println("Error while creating Game.");
            }
        } catch (Exception e) {
            System.out.println("Error while creating Game.");
        }
    }
}
