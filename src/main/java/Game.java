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
        Entity box = getGameWorld().spawn("eBlock", new SpawnData(64, 128).put("viewbox", "box-1.png"));

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
                player.getComponent(Player.class).setHealth(player.getComponent(Player.class).getHealth() - 1);
                System.out.println("health");
                if (player.getComponent(Player.class).getHealth() == 0) {
                    FXGL.showMessage("Player " + player.getComponent(Player.class).getPlayerNumber() + " died in " + Math.round(getGameTimer().getNow()) + " seconds!");
                    FXGL.getGameTimer().runOnceAfter(() -> FXGL.getGameController().gotoMainMenu() ,Duration.seconds(5));
                }
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.EXPLODABLE_BLOCK, EntityTypes.EXPLOSION) {
            @Override
            protected void onCollisionEnd(Entity block, Entity explosion) {
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
    }


    public static void main(String[] args) {
        launch(args);
    }
}