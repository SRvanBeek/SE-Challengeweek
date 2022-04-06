import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;


public class Game extends GameApplication {
    private Entity player1;
    private ArrayList<Entity> walls = new ArrayList<>();
    private int width = 960;
    private int height = 832;
    private final BombermanFactory bombermanFactory = new BombermanFactory();


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setTitle("Bomberman SE");
        settings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(bombermanFactory);
        player1 = spawn("player", 40, 40);


        Entity speedUp = FXGL.entityBuilder()
                .at(130, 96)
                .viewWithBBox(new Rectangle(32, 32, Color.YELLOW))
                .with(new CollidableComponent(true))
                .type(EntityTypes.ITEM)
                .zIndex(10)
                .buildAndAttach();


        //outside walls
        Entity wall1 = FXGL.entityBuilder()
                .at(0, 0)
                .viewWithBBox(new Rectangle(FXGL.getGameScene().getAppWidth(), 64, Color.BLUE))
                .with(new CollidableComponent(true))
                .type(EntityTypes.FIXEDBLOCK)
                .buildAndAttach();

        walls.add(wall1);

        Entity wall2 = FXGL.entityBuilder()
                .at(0, height - 64)
                .viewWithBBox(new Rectangle(FXGL.getGameScene().getAppWidth(), 64, Color.BLUE))
                .with(new CollidableComponent(true))
                .type(EntityTypes.FIXEDBLOCK)
                .buildAndAttach();

        walls.add(wall2);

        Entity wall3 = FXGL.entityBuilder()
                .at(0, 0)
                .viewWithBBox(new Rectangle(64, FXGL.getGameScene().getAppHeight(), Color.BLUE))
                .with(new CollidableComponent(true))
                .type(EntityTypes.FIXEDBLOCK)
                .buildAndAttach();

        walls.add(wall3);

        Entity wall4 = FXGL.entityBuilder()
                .at(width - 64, 0)
                .viewWithBBox(new Rectangle(64, FXGL.getGameScene().getAppHeight(), Color.BLUE))
                .with(new CollidableComponent(true))
                .type(EntityTypes.FIXEDBLOCK)
                .buildAndAttach();

        walls.add(wall4);


        //inner walls
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 6; j++) {
                Entity box = FXGL.entityBuilder()
                        .at(128 * i, 128 * j)
                        .viewWithBBox(new Rectangle(64, 64, Color.GRAY))
                        .with(new CollidableComponent(true))
                        .type(EntityTypes.FIXEDBLOCK)
                        .buildAndAttach();

                walls.add(box);
            }
        }

        //random walls
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 12; j++) {
                int spawn = (int) (Math.random() * 4);
                if (spawn > 0) {
                    Entity box = FXGL.entityBuilder()
                            .at((128 * i) + 64, (64 * j))
                            .viewWithBBox("tonnetje-1.png")
                            .with(new CollidableComponent(true))
                            .type(EntityTypes.FIXEDBLOCK)
                            .buildAndAttach();

                    walls.add(box);
                }
            }
        }

        for (int i = 1; i < 14; i++) {
            for (int j = 1; j < 5; j++) {
                int spawn = (int) (Math.random() * 4);
                if (spawn > 0) {
                    Entity box = FXGL.entityBuilder()
                            .at((64 * i), (128 * j) + 64)
                            .viewWithBBox("tonnetje-1.png")
                            .with(new CollidableComponent(true))
                            .type(EntityTypes.FIXEDBLOCK)
                            .buildAndAttach();

                    walls.add(box);
                }
            }
        }

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onUpdate(double tpf) {
        System.out.println(player1.getComponent(Player.class).getSpeed());
    }

    public void objCrash(String input) {
        int speed = player1.getComponent(Player.class).getSpeed();

        for (int i = 0; i < walls.size(); i++) {
            if (player1.isColliding(walls.get(i))) {
                if (Objects.equals(input, "W")) {
                    player1.translateY(speed);
                }
                if (Objects.equals(input, "S")) {
                    player1.translateY(-speed);
                }
                if (Objects.equals(input, "A")) {
                    player1.translateX(speed);
                }
                if (Objects.equals(input, "D")) {
                    player1.translateX(-speed);
                }
            }
        }
    }


    @Override
    protected void initInput() {
        getGameWorld().addEntityFactory(bombermanFactory);
        player1 = spawn("player", 40, 40);


        FXGL.onKey(KeyCode.W, () -> {
            player1.translateY(-player1.getComponent(Player.class).getSpeed());
            objCrash("W");
        });

        FXGL.onKey(KeyCode.S, () -> {
            player1.translateY(player1.getComponent(Player.class).getSpeed());
            objCrash("S");
        });

        FXGL.onKey(KeyCode.A, () -> {
            player1.translateX(-player1.getComponent(Player.class).getSpeed());
            objCrash("A");
        });

        FXGL.onKey(KeyCode.D, () -> {
            player1.translateX(player1.getComponent(Player.class).getSpeed());
            objCrash("D");
        });
    }

    public void setScene() {
        getGameWorld().addEntityFactory(bombermanFactory);
        player1 = spawn("player", 40, 40);
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.FIXEDBLOCK) {
            @Override
            protected void onCollision(Entity player, Entity block) {

            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.ITEM) {
            @Override
            protected void onCollision(Entity player, Entity item) {
                player1.getComponent(Player.class).setSpeed(player1.getComponent(Player.class).getSpeed() + 1);
                item.removeFromWorld();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
