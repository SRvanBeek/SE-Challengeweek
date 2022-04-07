import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

public class Bomb extends Component {
    private int radius;

    public Bomb(int radius) {
        this.radius = radius;
    }

    public void explode() {
        BoundingBoxComponent bbox = entity.getBoundingBoxComponent();

        entity.removeFromWorld();
    }
}
