import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Game extends GameApplication {

    private Entity player;


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(15 * 70);
        gameSettings.setHeight(10 * 70);
        gameSettings.setDeveloperMenuEnabled(true);
    }


    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());
        FXGL.setLevelFromMap("level1.tmx");

        player = getGameWorld().spawn("player");

        FXGL.entityBuilder()
                .at(200, 200)
                .viewWithBBox(new Rectangle(10, 10, Color.RED))
                .type(EntityTypes.DESTRUCTABLE_BOX)
                .with(new CollidableComponent(true))
                .buildAndAttach();


        Viewport viewport = getGameScene().getViewport();
        viewport.bindToEntity(player, getAppWidth() /2.0, getAppHeight() /2.0);
        viewport.setLazy(true);
    }


    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                super.onAction();
                player.getComponent(Player.class).right();
            }
            @Override
            protected void onActionEnd() {
                super.onActionEnd();
                player.getComponent(Player.class).rightEnd();
            }}, KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                super.onAction();
                player.getComponent(Player.class).left();
            }
            @Override
            protected void onActionEnd() {
                super.onAction();
                player.getComponent(Player.class).leftEnd();
            }}, KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                super.onAction();
                player.getComponent(Player.class).up();
            }
            @Override
            protected void onActionEnd() {
                super.onAction();
                player.getComponent(Player.class).upEnd();
            }}, KeyCode.W, VirtualButton.UP);

        getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                super.onAction();
                player.getComponent(Player.class).down();
            }
            @Override
            protected void onActionEnd() {
                super.onAction();
                player.getComponent(Player.class).downEnd();
            }}, KeyCode.S, VirtualButton.DOWN);
    }


    @Override
    protected void initPhysics() {
        FXGL.getPhysicsWorld().setGravity(0, 0);
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.DESTRUCTABLE_BOX) {
            @Override
            protected void onCollision(Entity player, Entity dbox) {
                dbox.removeFromWorld();
                System.out.println("removed from world");
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}