import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class ExplosionComponent extends Component {

    private AnimationChannel explosionAnimation;
    private AnimatedTexture texture;

    Image image = image("bomb_explosion.png");

    public ExplosionComponent() {
        this.explosionAnimation = new AnimationChannel(
                image,
                4,
                64,
                64,
                Duration.seconds(0.5),
                0,
                3
        );
        texture = new AnimatedTexture(explosionAnimation);
        texture.play();

        getGameTimer().runOnceAfter(() -> entity.removeFromWorld() , Duration.seconds(0.6));
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }
}
