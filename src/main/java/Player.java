import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.image;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;

public class Player extends Component {
    private Entity player;
    private String name;
    private int speed = 100;
    private int bombCount = 1;
    private int power = 1;
    private int health;

    private PhysicsComponent physics;
    private AnimatedTexture texture;
    private String direction;

    public Player() {
        AnimationChannel initialAnimation = new AnimationChannel(
                image("BombermanBlondVSpritesheet.png"),
                2,
                72,
                72,
                Duration.seconds(1),
                0,
                1
        );
        texture = new AnimatedTexture(initialAnimation);
        texture.loop();
    }

    public Image getAnimationOnMovement() {
        String animationName = "player_";

        animationName += physics.isMovingX() || physics.isMovingY() ? "move" : "idle";

        animationName += direction;

        return (new Image("BombermanBlondVSpritesheet.png"));
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
