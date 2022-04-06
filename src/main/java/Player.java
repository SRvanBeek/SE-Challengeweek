import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;
import static com.almasb.fxgl.dsl.FXGLForKtKt.image;


public class Player extends Component {
    private Entity player;
    private String name;
    private int speed = 100;
    private int bombCount = 1;
    private int power = 1;
    private int health;
    private int playerNumber;

    private AnimationChannel initialAnimation;
    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private String direction;


    public Player() {
        this.initialAnimation = new AnimationChannel(
                image("player_1" + "_move_down.png"),
                4,
                31,
                41,
                Duration.seconds(1),
                0,
                1
        );
        texture = new AnimatedTexture(initialAnimation);
    }


    @Override
    public void onAdded(){
        entity.getViewComponent().addChild(texture);
    }

    public Image getAnimationOnMovement() {
        String animationName = "player_";

        animationName += physics.isMovingX() || physics.isMovingY() ? "move" : "idle";

        String animationName = "player_" + playerNumber;
        animationName += physics.isMovingX() || physics.isMovingY() ? "move_" : "idle_";
        animationName += direction;
        return (new Image(animationName));
    }

    @Override
    public void onUpdate(double tpf) {
        Image chosenAnimationImage = getAnimationOnMovement();
        if (chosenAnimationImage != texture.getImage()) {
            texture.setImage(chosenAnimationImage);
        }
    }

    public void left() {
        direction = "left";
        physics.setVelocityX(-speed);
    }

    public void right() {
        direction = "left";
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
        physics.setVelocityY(0);
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
