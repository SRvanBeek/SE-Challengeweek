import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Factory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityTypes.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        physics.addGroundSensor(
                new HitBox(
                        "GROUND_SENSOR",
                        new Point2D(16, 38),
                        BoundingShape.box(6, 8)
                )
        );

        physics.setFixtureDef(new FixtureDef().friction(0.0f));

        return FXGL.entityBuilder(data)
                .type(EntityTypes.PLAYER)
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.circle(36)))
                .with(new CollidableComponent(true))
                .viewWithBBox(new Rectangle(30, 30, Color.RED))
                .with(physics)
                .with(new Player())
                .build();
    }

    @Spawns("coin")
    public Entity newCoin(SpawnData data) {
        return FXGL.entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }
}
