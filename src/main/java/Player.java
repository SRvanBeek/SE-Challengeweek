import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;

public class Player extends Component {
    private Entity player;
    private String name;
    private int speed = 1;
    private int bombCount = 1;
    private int power = 1;
    private int health;

    public Player() {

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

    public int getBombCount() {
            return this.bombCount;
        }

    public void setName(String name) {
            this.name = name;
        }

    public void setHealth(int health) {
            this.health = health;
        }

    public void setPower(int power) {
            this.power = power;
        }

    public void setSpeed(int speed) {
            this.speed = speed;
        }

    public void setBombCount(int bombCount) {
            this.bombCount = bombCount;
        }
    }


