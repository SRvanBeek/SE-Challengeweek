import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class Game extends GameApplication {

    private Entity player1;
    private Entity player2;


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(950);
        gameSettings.setHeight(820);
        gameSettings.setDeveloperMenuEnabled(true);
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setTitle("Bomberman");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BombermanFactory());
        FXGL.setLevelFromMap("bomberman_level_3.tmx");

        player1 = getGameWorld().spawn("player", new SpawnData(65, 65).put("playerNumber", 1));
        player2 = getGameWorld().spawn("player", new SpawnData(850, 700).put("playerNumber", 2));

        for (int i = 1; i < 12; i++) {
            for (int j = 1; j < 5; j++) {
                Entity box = getGameWorld().spawn("eBlock", new SpawnData(64 * i + 64, 128 * j + 64).put("viewbox", "box-1.png"));
                int spawnrate = (int) (Math.random() * 7);
                if (spawnrate < 3) {
                    if (spawnrate == 0) {
                        getGameWorld().spawn("speed_up", new SpawnData(box.getX(), box.getY()));
                        break;
                    }
                    if (spawnrate == 1) {
                        getGameWorld().spawn("power_up", new SpawnData(box.getX(), box.getY()));
                        break;
                    }
                    if (spawnrate == 2) {
                            getGameWorld().spawn("bomb_up", new SpawnData(box.getX(), box.getY()));
                            break;
                    }
                }
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
        System.out.println(playerX + " - " + playerY);

        coords.add((int) Math.floor((playerX + 14) / 64) * 64);
        coords.add((int) Math.round((playerY) / 64) * 64);

        return coords;
    }

    @Override
    protected void initInput() {
        //move up
        getInput().addAction(new UserAction("up") {
            @Override
            protected void onAction() {
                super.onAction();
                player1.getComponent(Player.class).up();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player1.getComponent(Player.class).stopYMovement();
            }
        }, KeyCode.W, VirtualButton.UP);

        // for p2
        getInput().addAction(new UserAction("up2") {
            @Override
            protected void onAction() {
                super.onAction();
                player2.getComponent(Player.class).up();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player2.getComponent(Player.class).stopYMovement();
            }
        }, KeyCode.UP, VirtualButton.UP);

        //move down
        getInput().addAction(new UserAction("down") {
            @Override
            protected void onAction() {
                super.onAction();
                player1.getComponent(Player.class).down();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player1.getComponent(Player.class).stopYMovement();
            }
        }, KeyCode.S, VirtualButton.DOWN);

        getInput().addAction(new UserAction("down2") {
            @Override
            protected void onAction() {
                super.onAction();
                player2.getComponent(Player.class).down();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player2.getComponent(Player.class).stopYMovement();
            }
        }, KeyCode.DOWN, VirtualButton.DOWN);

        //move left
        getInput().addAction(new UserAction("left") {
            @Override
            protected void onAction() {
                super.onAction();
                player1.getComponent(Player.class).left();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player1.getComponent(Player.class).stopXMovement();
            }
        }, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("left2") {
            @Override
            protected void onAction() {
                super.onAction();
                player2.getComponent(Player.class).left();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player2.getComponent(Player.class).stopXMovement();
            }
        }, KeyCode.LEFT, VirtualButton.LEFT);

        //move right
        getInput().addAction(new UserAction("right") {
            @Override
            protected void onAction() {
                super.onAction();
                player1.getComponent(Player.class).right();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player1.getComponent(Player.class).stopXMovement();
            }
        }, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("right2") {
            @Override
            protected void onAction() {
                super.onAction();
                player2.getComponent(Player.class).right();
            }

            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player2.getComponent(Player.class).stopXMovement();
            }
        }, KeyCode.RIGHT, VirtualButton.RIGHT);

        //place bomb
        getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                ArrayList<Integer> coords = getTileCoordinates(player1.getX(), player1.getY());
                player1.getComponent(Player.class).placeBomb(spawn(
                        "bomb", new SpawnData(coords.get(0), coords.get(1)).put("radius", player1.getComponent(Player.class).getPower())));

                System.out.println("power - " +player1.getComponent(Player.class).getPower());
            }
        }, KeyCode.SPACE);

        getInput().addAction(new UserAction("Place Bomb2") {
            @Override
            protected void onActionBegin() {
                ArrayList<Integer> coords = getTileCoordinates(player2.getX(), player2.getY());
                player2.getComponent(Player.class).placeBomb(spawn(
                        "bomb", new SpawnData(coords.get(0), coords.get(1)).put("radius", player2.getComponent(Player.class).getPower())));
            }
        }, KeyCode.ENTER);
    }


    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().setGravity(0, 0);

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.EXPLOSION) {
            @Override
            protected void onCollisionBegin(Entity player, Entity explosion) {
                player.getComponent(Player.class).loseHealth();
                System.out.println("health");

                player1.getComponent(Player.class).adjustHeartsPlayerOne();
                player2.getComponent(Player.class).adjustHeartsPlayerTwo();

                if (player.getComponent(Player.class).getHealth() > 0) {
                    FXGL.play("player_hit.wav");
                }

                if (player.getComponent(Player.class).getHealth() == 0) {
                    FXGL.play("player_dead.wav");
                    FXGL.showMessage("Player " + player.getComponent(Player.class).getPlayerNumber() + " died in " + Math.round(getGameTimer().getNow()) + " seconds!");

                    FXGL.getGameTimer().runOnceAfter(() -> FXGL.getAudioPlayer().stopAllMusic() ,Duration.seconds(4.5));
                    FXGL.getGameTimer().runOnceAfter(() -> {
                        FXGL.getGameController().gotoMainMenu();
                        } ,Duration.seconds(5));
                }
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.EXPLODABLE_BLOCK, EntityTypes.EXPLOSION) {
            @Override
            protected void onCollisionBegin(Entity block, Entity explosion) {
                block.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BOMB) {
            @Override
            protected void onCollisionEnd(Entity player, Entity bomb) {
                System.out.println("collision end");
                ArrayList<Integer> coords = getTileCoordinates(bomb.getX(), bomb.getY());
                player1.getComponent(Player.class).placeBomb(spawn(
                        "bomb_active", new SpawnData(coords.get(0) + 1, coords.get(1) + 1).put("radius", player.getComponent(Player.class).getPower())));

                System.out.println(bomb.getType());
                bomb.removeFromWorld();
            }
        });

        //power-ups
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.SPEED_UP) {
            @Override
            protected void onCollisionBegin(Entity player, Entity item) {
                System.out.println("player " + player.getComponent(Player.class).getPlayerNumber());
                player.getComponent(Player.class).speedUp();
                item.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.POWER_UP) {
            @Override
            protected void onCollisionBegin(Entity player, Entity item) {
                player.getComponent(Player.class).powerUp();
                item.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BOMB_UP) {
            @Override
            protected void onCollisionBegin(Entity player, Entity item) {
                player.getComponent(Player.class).bombCountUp();
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