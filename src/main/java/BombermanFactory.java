import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.sun.javafx.scene.text.TextLayout;
import javafx.geometry.Point2D;

import java.awt.*;

public class BombermanFactory implements EntityFactory {
    public BombermanFactory() {
    }

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityTypes.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer1(SpawnData data) {
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
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(31, 41)))
                .with(new CollidableComponent(true))
                .with(physics)
                .with(new Player(data.get("playerNumber")))
                .build();
    }


    @Spawns("eBlock")
    public Entity newExplodableBlock(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox((String) data.get("viewbox"))
                .with(new PhysicsComponent())
                .with(new CollidableComponent(true))
                .type(EntityTypes.EXPLODABLE_BLOCK)
                .build();
    }

    @Spawns("bomb")
    public Entity newBomb(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .view("bomb-1.png")
                .with(new Bomb(data.get("radius")))
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(62, 62)))
                .scale(1, 1)
                .type(EntityTypes.BOMB)
                .build();
    }

    @Spawns("bomb_active")
    public Entity newBombActive(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        FixtureDef fd = new FixtureDef();
        fd.friction(0.0f);

        physics.setFixtureDef(fd);

        return FXGL.entityBuilder()
                .from(data)
                .view("bomb-1.png")
                .with(physics)
                .type(EntityTypes.BOMB_ACTIVE)
                .with(new Bomb(data.get("radius")))
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(62, 62)))
                .build();
    }

    @Spawns("explosion")
    public Entity newExplosion(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityTypes.EXPLOSION)
                .with(new ExplosionComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(62, 62)))
                .scale(1, 1)

                .build();
    }

    @Spawns("speed_up")
    public Entity newSpeedUp(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox("speedup-1.png")
                .type(EntityTypes.SPEED_UP)
                .with(new CollidableComponent(true))
                .scale(1, 1)
                .build();
    }
    @Spawns("bomb_up")
    public Entity newBombUp(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox("extrabomb-1.png")
                .type(EntityTypes.SPEED_UP)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(62, 62)))
                .scale(1, 1)
                .build();
    }
    @Spawns("power_up")
    public Entity newPowerUp(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox("bomradius-1.png")
                .type(EntityTypes.SPEED_UP)
                .with(new CollidableComponent(true))
                .bbox(new HitBox(new Point2D(0, 0), BoundingShape.box(62, 62)))
                .scale(1, 1)
                .build();
    }

}



