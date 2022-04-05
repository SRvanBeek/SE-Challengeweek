import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public class Game extends GameApplication {

    // test if push works (by Stan)

    private Entity player;
    private int speed = 2;
    private ArrayList<Entity> walls = new ArrayList<>();
    private int width = 960;
    private int height = 832;


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(width);
        settings.setHeight(height);
        settings.setTitle("Bomberman SE");
        settings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(40, 40)
                .viewWithBBox("sprite.png")
                .with(new CollidableComponent(true))
                .scale(0.4, 0.4)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        //outside walls
        Entity wall1 = FXGL.entityBuilder()
                .at(0, 0)
                .viewWithBBox("wall-1.png")
                .with(new CollidableComponent(true))
                .type(EntityTypes.FIXEDBLOCK)
                .buildAndAttach();

        walls.add(wall1);

        Entity wall2 = FXGL.entityBuilder()
                .at(0, height - 64)
                .viewWithBBox("wall-1.png")
                .with(new CollidableComponent(true))
                .type(EntityTypes.FIXEDBLOCK)
                .buildAndAttach();

        walls.add(wall2);

        Entity wall3 = FXGL.entityBuilder()
                .at(0, 0)
                .viewWithBBox("wall-2.png")
                .with(new CollidableComponent(true))
                .type(EntityTypes.FIXEDBLOCK)
                .buildAndAttach();

        walls.add(wall3);

        Entity wall4 = FXGL.entityBuilder()
                .at(width - 64, 0)
                .viewWithBBox("wall-2.png")
                .with(new CollidableComponent(true))
                .type(EntityTypes.FIXEDBLOCK)
                .buildAndAttach();

        walls.add(wall4);



        //inner walls
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 6; j++) {
                Entity box = FXGL.entityBuilder()
                        .at(128 * i, 128 * j)
                        .viewWithBBox("metal-1.png")
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
                            .viewWithBBox("box-1.png")
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
                            .viewWithBBox("box-2.png")
                            .with(new CollidableComponent(true))
                            .type(EntityTypes.FIXEDBLOCK)
                            .buildAndAttach();

                    walls.add(box);
                }
            }
        }

        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    public void objCrash(String input) {
        for (Entity wall : walls) {
            if (player.isColliding(wall)) {
                if (Objects.equals(input, "W")) {
                    player.translateY(speed);
                }
                if (Objects.equals(input, "S")) {
                    player.translateY(-speed);
                }
                if (Objects.equals(input, "A")) {
                    player.translateX(speed);
                }
                if (Objects.equals(input, "D")) {
                    player.translateX(-speed);
                }
            }
        }
    }


    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-speed);
            objCrash("W");
        });

        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(speed);
            objCrash("S");
        });

        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-speed);
            objCrash("A");
        });

        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(speed);
            objCrash("D");
        });
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.FIXEDBLOCK) {
            @Override
            protected void onCollision(Entity player, Entity block) {

            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
