import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.pathfinding.CellMoveComponent;
import com.almasb.fxgl.pathfinding.astar.AStarMoveComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.contacts.Position;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.awt.geom.Point2D;

import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;


public class Player extends Component {
    private String name;

    private CellMoveComponent cell;
    private AStarMoveComponent astar;


    private int speed = 100;
    private int bombCount = 1;
    private int bombsPlaced = 0;
    private int power = 1;
    private int health;
    private int playerNumber;

    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private String direction;

    private AnimationChannel initialAnimation;
    private AnimationChannel newAnimation;

    Image image = image("player_1_move_down.png");


    public Player() {

        this.initialAnimation = new AnimationChannel(
                image,
                4,
                31,
                41,
                Duration.seconds(1),
                0,
                3
        );
        texture = new AnimatedTexture(initialAnimation);
        texture.loop();
    }



    public void animationMoving(){
        this.newAnimation = new AnimationChannel(
                image(getAnimationOnMovement()),
                4,
                31,
                41,
                Duration.seconds(1),
                1,
                3

        );
        texture = new AnimatedTexture(newAnimation);
        texture.loop();
    }

    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(texture);
    }
    public String getAnimationOnMovement() {
        String animationName = "player_1" + "_move_";
        //animationName += physics.isMovingX() || physics.isMovingY() ? "move_" : "idle_";
        animationName += direction + ".png";
        return animationName;
    }


    @Override
    public void onUpdate(double tpf) {
        Image chosenAnimationImage = image(getAnimationOnMovement());
        if (chosenAnimationImage != texture.getImage()) {
            image = chosenAnimationImage;
            texture.setImage(image);
        }
    }

    //movement functions
    public void left() {
        direction = "left";
        physics.setVelocityX(-speed);
    }

    public void right() {
        direction = "right";
        physics.setVelocityX(speed);
    }

    public void up() {
        direction = "Up";
        physics.setVelocityY(-speed);
    }

    public void down() {
        direction = "down";
        physics.setVelocityY(speed);
    }

    public void stopYMovement() {
        physics.setVelocityY(0);
    }

    public void stopXMovement() {
        physics.setVelocityX(0);
    }


    //bomb mechanics
    public void placeBomb() {
        System.out.println("bomb placed");

        if (bombsPlaced == bombCount) {
            return;
        }
        bombsPlaced++;

        Entity bomb = spawn("bomb", new SpawnData(entity.getX(), entity.getY()));

        getGameTimer().runOnceAfter(() -> {
            bomb.getComponent(Bomb.class).explode();
            bombsPlaced--;
        }, Duration.seconds(2));
    }

    public String getName() {
        return this.name;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getPower() {
        return this.power;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
