import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.input.virtual.VirtualButton;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Game extends GameApplication {

    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(15 * 70);
        gameSettings.setHeight(10 * 70);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new Factory());
        FXGL.setLevelFromMap("mario.tmx");

        player = getGameWorld().spawn("player");

        Viewport viewport = getGameScene().getViewport();
        viewport.bindToEntity(player, getAppWidth() /2.0, getAppHeight() /2.0);
        viewport.setLazy(true);
    }

    @Override
    protected void initInput() {

        // Leest input voor naar rechts
        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                super.onAction();
                player.getComponent(Player.class).right();
            }
        }, KeyCode.D, VirtualButton.RIGHT);


//         Leest input voor naar beneden (einde)        TO DO: D kan niet 2 keer assigned worden (voor elke input)
//        getInput().addAction(new UserAction("RightEnd") {
//            @Override
//            protected void onActionEnd() {
//                super.onActionEnd();
//                player.getComponent(Player.class).rightEnd();
//            }
//        }, KeyCode.D, VirtualButton.RIGHT);


        // Leest input voor naar links
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                super.onAction();
                player.getComponent(Player.class).left();
            }
        }, KeyCode.A, VirtualButton.LEFT);


        // Leest input voor naar boven
        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                super.onAction();
                player.getComponent(Player.class).up();
            }
        }, KeyCode.W, VirtualButton.LEFT);


        // Leest input voor naar beneden
        getInput().addAction(new UserAction("Down") {
            @Override
            protected void onAction() {
                super.onAction();
                player.getComponent(Player.class).down();
            }
        }, KeyCode.S, VirtualButton.LEFT);
    }



    public static void main(String[] args) {
        launch(args);
    }
}