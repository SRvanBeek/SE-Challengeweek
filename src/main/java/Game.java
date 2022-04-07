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
import java.util.ArrayList;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;


public class Game extends GameApplication {
    private Entity player1;
    private Entity player2;
    private ArrayList<Entity> walls = new ArrayList<>();
    private int width = 960;
    private int height = 832;
    private final BombermanFactory bombermanFactory = new BombermanFactory();



    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(15 * 70);
        gameSettings.setHeight(10 * 70);
        gameSettings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BombermanFactory());
        FXGL.setLevelFromMap("level1.tmx");

        player1 = getGameWorld().spawn("player1");

        Viewport viewport = getGameScene().getViewport();
        viewport.bindToEntity(player1, getAppWidth() /2.0, getAppHeight() /2.0);
        viewport.setLazy(true);
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

        //place bomb
        getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                ArrayList<Integer> coords = getTileCoordinates(player1.getX(), player1.getY());
                player1.getComponent(Player.class).placeBomb(spawn(
                        "bomb", new SpawnData(coords.get(0), coords.get(1)).put("radius", player1.getComponent(Player.class).getPower())));
            }
        }, KeyCode.F);
    }


    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().setGravity(0, 0);
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.ITEM) {
            @Override
            protected void onCollision(Entity player, Entity item) {
                player1.getComponent(Player.class).setSpeed(player1.getComponent(Player.class).getSpeed() + 1);
                item.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.BOMB) {

            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                System.out.println(getTileCoordinates(player1.getX(), player1.getY()));
                System.out.println((player1.getX() + 15) + " - " + (player1.getY() + 21));
            }

            @Override
            protected void onCollisionEnd(Entity player, Entity bomb) {
                System.out.println("collision end");
                ArrayList<Integer> coords = getTileCoordinates(bomb.getX(), bomb.getY());
                player1.getComponent(Player.class).placeBomb(spawn(
                        "bomb_active", new SpawnData(coords.get(0), coords.get(1)).put("radius", player.getComponent(Player.class).getPower())));

                System.out.println(bomb.getType());
                bomb.removeFromWorld();

            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}