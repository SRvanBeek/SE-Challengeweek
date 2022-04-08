import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class Game extends GameApplication {

    private Entity player1;
    private Entity player2;
    private int level = 1;

    private int player1Score = 0;
    private int player2Score = 0;

    private int pFrames = 0;
    private double currentTime;


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(950);
        gameSettings.setHeight(820);
        gameSettings.setDeveloperMenuEnabled(true);
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setSceneFactory(new MenuFactory());
        gameSettings.setTitle("Bomberman");
    }


    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BombermanFactory());
        FXGL.setLevelFromMap("bomberman_level_" + level + ".tmx");

        currentTime = getGameTimer().getNow();

        player1 = getGameWorld().spawn("player", new SpawnData(65, 65).put("playerNumber", 1));
        player2 = getGameWorld().spawn("player", new SpawnData(850, 700).put("playerNumber", 2));

        for (int i = 1; i < 12; i++) {
            for (int j = 1; j < 5; j++) {
                Entity box = getGameWorld().spawn("eBlock", new SpawnData(64 * i + 64, 128 * j + 64).put("viewbox", "box-1.png"));
            }
        }
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                Entity box = getGameWorld().spawn("eBlock", new SpawnData(128 * i + 64, 128 * j).put("viewbox", "box-1.png"));
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 8; j++) {
                Entity box = getGameWorld().spawn("eBlock", new SpawnData((64 * 12) * i + 64, 64 * j + 128).put("viewbox", "box-1.png"));
            }
        }
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                Entity box = getGameWorld().spawn("eBlock", new SpawnData(64 * i + 128, (64 * 10) * j + 64).put("viewbox", "box-1.png"));
            }
        }
        FXGL.loopBGM("BGM.wav");
    }


    public ArrayList getTileCoordinates(double playerX, double playerY) {
        ArrayList<Integer> coords = new ArrayList<>();
        coords.add((int) Math.floor((playerX + 14) / 64) * 64);
        coords.add((int) Math.round((playerY) / 64) * 64);
        return coords;
    }


    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("up") {
            @Override
            protected void onAction() {
                super.onAction();
                player1.getComponent(PlayerComponent.class).up();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player1.getComponent(PlayerComponent.class).stopYMovement();
            }
        }, KeyCode.W, VirtualButton.UP);

        getInput().addAction(new UserAction("up2") {
            @Override
            protected void onAction() {
                super.onAction();
                player2.getComponent(PlayerComponent.class).up();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player2.getComponent(PlayerComponent.class).stopYMovement();
            }
        }, KeyCode.UP, VirtualButton.UP);

        getInput().addAction(new UserAction("down") {
            @Override
            protected void onAction() {
                super.onAction();
                player1.getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player1.getComponent(PlayerComponent.class).stopYMovement();
            }
        }, KeyCode.S, VirtualButton.DOWN);

        getInput().addAction(new UserAction("down2") {
            @Override
            protected void onAction() {
                super.onAction();
                player2.getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player2.getComponent(PlayerComponent.class).stopYMovement();
            }
        }, KeyCode.DOWN, VirtualButton.DOWN);

        getInput().addAction(new UserAction("left") {
            @Override
            protected void onAction() {
                super.onAction();
                player1.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player1.getComponent(PlayerComponent.class).stopXMovement();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("left2") {
            @Override
            protected void onAction() {
                super.onAction();
                player2.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player2.getComponent(PlayerComponent.class).stopXMovement();
            }
        }, KeyCode.LEFT, VirtualButton.LEFT);

        getInput().addAction(new UserAction("right") {
            @Override
            protected void onAction() {
                super.onAction();
                player1.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player1.getComponent(PlayerComponent.class).stopXMovement();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("right2") {
            @Override
            protected void onAction() {
                super.onAction();
                player2.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player2.getComponent(PlayerComponent.class).stopXMovement();
            }
        }, KeyCode.RIGHT, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                ArrayList<Integer> coords = getTileCoordinates(player1.getX(), player1.getY());
                player1.getComponent(PlayerComponent.class).placeBomb(spawn(
                        "bomb", new SpawnData(coords.get(0), coords.get(1)).put("radius", player1.getComponent(PlayerComponent.class).getPower())));
            }
        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("Place Bomb2") {
            @Override
            protected void onActionBegin() {
                ArrayList<Integer> coords = getTileCoordinates(player2.getX(), player2.getY());
                player2.getComponent(PlayerComponent.class).placeBomb(spawn(
                        "bomb", new SpawnData(coords.get(0), coords.get(1)).put("radius", player2.getComponent(PlayerComponent.class).getPower())));
            }
        }, KeyCode.ENTER);
    }

    public String checkScore() {
        if (player1Score == 3) {
            System.out.println("player 1 wins!");
            return "player 1";
        }
        if (player2Score == 3) {
            System.out.println("player 2 wins!");
            return "player 2";
        } else {
            return null;
        }
    }

    public void scoreUp() {
        if (player1.getComponent(PlayerComponent.class).getHealth() == 0) {
            player2Score++;
            System.out.println("player 2: " + player2Score);
        }
        if (player2.getComponent(PlayerComponent.class).getHealth() == 0) {
            player1Score++;
            System.out.println("player 1: " + player1Score);
        }
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().setGravity(0, 0);
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.EXPLOSION) {
            @Override
            protected void onCollisionBegin(Entity player, Entity explosion) {
                player.getComponent(PlayerComponent.class).loseHealth();


                player1.getComponent(PlayerComponent.class).adjustHeartsPlayerOne();
                player2.getComponent(PlayerComponent.class).adjustHeartsPlayerTwo();

                if (player.getComponent(PlayerComponent.class).getHealth() > 0) {
                    FXGL.play("player_hit.wav");
                }


                if (player.getComponent(PlayerComponent.class).getHealth() == 0) {
                    if (pFrames == 0) {
                        pFrames = 1;
                        FXGL.play("player_dead.wav");
                        FXGL.showMessage("Player " + player.getComponent(PlayerComponent.class).getPlayerNumber() + " died in " + (Math.round(getGameTimer().getNow() - currentTime)) + " seconds!");

                        getGameTimer().runOnceAfter(() -> {
                            pFrames = 0;
                        }, Duration.millis(600));

                        scoreUp();
                        if (checkScore() != null) {
                            FXGL.showMessage(checkScore() + " wins!");
                            player1Score = 0;
                            player2Score = 0;
                            level = 0;
                        }

                        FXGL.getGameTimer().runOnceAfter(() -> FXGL.getAudioPlayer().stopAllMusic(), Duration.seconds(4.5));
                        FXGL.getGameTimer().runOnceAfter(() -> {
                            FXGL.getGameController().gotoMainMenu();
                            level++;
                        }, Duration.seconds(5));
                    }
                }
            }
            });


        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.EXPLODABLE_BLOCK, EntityTypes.EXPLOSION) {
            @Override
            protected void onCollisionBegin(Entity block, Entity explosion) {
                int spawnrate = (int) (Math.random() * 10);
                if (spawnrate < 3) {
                    if (spawnrate == 0) {
                        getGameWorld().spawn("speed_up", new SpawnData(block.getX(), block.getY()));
                    }
                    if (spawnrate == 1) {
                        getGameWorld().spawn("power_up", new SpawnData(block.getX(), block.getY()));
                    }
                    if (spawnrate == 2) {
                        getGameWorld().spawn("bomb_up", new SpawnData(block.getX(), block.getY()));
                    }
                }
                block.removeFromWorld();
            }
        });


        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BOMB) {
            @Override
            protected void onCollisionEnd(Entity player, Entity bomb) {
                ArrayList<Integer> coords = getTileCoordinates(bomb.getX(), bomb.getY());
                player.getComponent(PlayerComponent.class).placeBomb(spawn(
                        "bomb_active", new SpawnData(coords.get(0) + 1, coords.get(1) + 1).put("radius", player.getComponent(PlayerComponent.class).getPower())));
                bomb.removeFromWorld();
            }
        });

        //power-ups
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.SPEED_UP) {
            @Override
            protected void onCollisionBegin(Entity player, Entity item) {
                player.getComponent(PlayerComponent.class).speedUp();
                item.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.POWER_UP) {
            @Override
            protected void onCollisionBegin(Entity player, Entity item) {
                player.getComponent(PlayerComponent.class).powerUp();
                item.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BOMB_UP) {
            @Override
            protected void onCollisionBegin(Entity player, Entity item) {
                player.getComponent(PlayerComponent.class).bombCountUp();
                item.removeFromWorld();
            }
        });

    }

    @Override
    protected void initUI() {
        //Sorry dat jullie ogen deze code moeten zien

        Image heart = new Image("/assets/textures/heart.png");

        //Hartjes Speler 1
        ImageView hearticon1_1 = new ImageView(heart);
        ImageView hearticon1_2 = new ImageView(heart);
        ImageView hearticon1_3 = new ImageView(heart);

        Label heart1_1 = new Label();
        Label heart1_2 = new Label();
        Label heart1_3 = new Label();

        heart1_1.setGraphic(hearticon1_1);
        heart1_1.setTranslateY(10);
        heart1_1.setTranslateX(83);

        heart1_2.setGraphic(hearticon1_2);
        heart1_2.setTranslateY(10);
        heart1_2.setTranslateX(156);

        heart1_3.setGraphic(hearticon1_3);
        heart1_3.setTranslateY(10);
        heart1_3.setTranslateX(229);

        FXGL.getGameScene().addUINode(heart1_1);
        FXGL.getGameScene().addUINode(heart1_2);
        FXGL.getGameScene().addUINode(heart1_3);

        //Hartjes speler 2
        ImageView hearticon2_1 = new ImageView(heart);
        ImageView hearticon2_2 = new ImageView(heart);
        ImageView hearticon2_3 = new ImageView(heart);

        Label heart2_1 = new Label();
        Label heart2_2 = new Label();
        Label heart2_3 = new Label();

        heart2_1.setGraphic(hearticon2_1);
        heart2_1.setTranslateY(10);
        heart2_1.setTranslateX(805);

        heart2_2.setGraphic(hearticon2_2);
        heart2_2.setTranslateY(10);
        heart2_2.setTranslateX(732);

        heart2_3.setGraphic(hearticon2_3);
        heart2_3.setTranslateY(10);
        heart2_3.setTranslateX(659);

        FXGL.getGameScene().addUINode(heart2_1);
        FXGL.getGameScene().addUINode(heart2_2);
        FXGL.getGameScene().addUINode(heart2_3);

        //Player Closeup Graphics
        Image player1 = new Image("/assets/textures/player1_closeup.png");
        ImageView player1Icon = new ImageView(player1);

        Label player1Graphic = new Label();
        player1Graphic.setGraphic(player1Icon);
        player1Graphic.setTranslateY(10);
        player1Graphic.setTranslateX(10);

        Image player2 = new Image("/assets/textures/player2_closeup.png");
        ImageView player2Icon = new ImageView(player2);

        Label player2Graphic = new Label();
        player2Graphic.setGraphic(player2Icon);
        player2Graphic.setTranslateY(10);
        player2Graphic.setTranslateX(878);

        FXGL.getGameScene().addUINode(player1Graphic);
        FXGL.getGameScene().addUINode(player2Graphic);

    }


    public static void main(String[] args) {
        launch(args);
    }
}