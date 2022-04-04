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

import java.util.concurrent.ThreadLocalRandom;


public class Game extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1920);
        settings.setHeight(1080);
        settings.setTitle("Bomberman SE");
        settings.setVersion("1.0");
    }

    @Override
    protected void initGame() {
        player = FXGL.entityBuilder()
                .at(400, 0)
                .viewWithBBox("sprite.png")
                .with(new CollidableComponent(true))
                .scale(0.05, 0.05)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        FXGL.getGameTimer().runAtInterval(() -> {
            int randomPosW = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() -80);
            int randomPosH = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppHeight() -80);

            FXGL.entityBuilder()
                    .at(randomPosW, randomPosH)
                    .viewWithBBox(new Rectangle(50, 50, Color.WHITE))
                    .with(new CollidableComponent(true))
                    .type(EntityTypes.FIXEDBLOCK)
                    .buildAndAttach();

        }, Duration.millis(2000));


        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () -> {
            player.translateX(5);
        });
        FXGL.onKey(KeyCode.A, () -> {
            player.translateX(-5);
        });
        FXGL.onKey(KeyCode.W, () -> {
            player.translateY(-5);
        });
        FXGL.onKey(KeyCode.S, () -> {
            player.translateY(5);
        });
    }

    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.FIXEDBLOCK) {
            @Override
            protected void onCollision(Entity player, Entity block) {
              block.removeFromWorld();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
