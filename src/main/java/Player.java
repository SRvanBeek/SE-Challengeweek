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


    public Player() {
        this.initialAnimation = new AnimationChannel(
                image("player_1" + "_move_down.png"),
                4,
                31,
                41,
                Duration.seconds(1),
                0,
                0
        );
        texture = new AnimatedTexture(initialAnimation);
    }


    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(texture);
    }

    public Image getAnimationOnMovement() {
        String animationName = "player_" + playerNumber;
        animationName += physics.isMovingX() || physics.isMovingY() ? "move_" : "idle_";
        animationName += direction;
        return (new Image(animationName));
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