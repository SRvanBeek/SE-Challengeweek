import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;


public class Player extends Component {

    private Integer movementSpeed = 200;
    private Integer idleSpeed = 0;
    private String direction = " ";
    private int playerNumber;

    private AnimationChannel initialAnimation;
    private PhysicsComponent physics;
    private AnimatedTexture texture;
<<<<<<< Updated upstream

=======
    private AnimationChannel initialAnimation;
    private AnimationChannel newAnimation;
>>>>>>> Stashed changes

    public Player() {
        this.initialAnimation = new AnimationChannel(
                image("player_1_move_down.png"),
                4,
                31,
                41,
                Duration.seconds(1),
                0,
                0
        );
        texture = new AnimatedTexture(initialAnimation);
    }

<<<<<<< Updated upstream

=======
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
>>>>>>> Stashed changes
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
        if(physics.isMovingX() || physics.isMovingY()){
            animationMoving();
        }
    }

    public void left() {
        direction = "left";
        physics.setVelocityX(-movementSpeed);
    }

    public void leftEnd() {
        direction = "left";
        physics.setVelocityX(idleSpeed);
    }


    public void right() {
        direction = "right";
        physics.setVelocityX(movementSpeed);
    }

    public void rightEnd() {
        direction = "right";
        physics.setVelocityX(idleSpeed);
    }


    public void up() {
        direction = "up";
        physics.setVelocityY(-movementSpeed);
    }

    public void upEnd() {
        direction = "up";
        physics.setVelocityY(idleSpeed);
    }

    public void down() {
        direction = "down";
        physics.setVelocityY(movementSpeed);
    }

    public void downEnd() {
        direction = "down";
        physics.setVelocityY(idleSpeed);
    }
}