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
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new BombermanFactory());
        FXGL.setLevelFromMap("test.tmx");

        player1 = getGameWorld().spawn("player1");

        Viewport viewport = getGameScene().getViewport();
        viewport.bindToEntity(player1, getAppWidth() /2.0, getAppHeight() /2.0);
        viewport.setLazy(true);
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
                player1.getComponent(Player.class).placeBomb(spawn(
                        "bomb", new SpawnData(player1.getX(), player1.getY()).put("radius", player1.getComponent(Player.class).getPower())));
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
            protected void onCollisionEnd(Entity player, Entity bomb) {

                System.out.println("collision end");
                player1.getComponent(Player.class).placeBomb(spawn(
                        "bomb_active", new SpawnData(bomb.getX(), bomb.getY()).put("radius", player.getComponent(Player.class).getPower())));

                System.out.println(bomb.getType());
                bomb.removeFromWorld();

            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}