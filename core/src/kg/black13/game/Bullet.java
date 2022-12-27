package kg.black13.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Body {

    public  Bullet(Vector2 position)
    {
        this.position = position;
        texture = new Texture("images/bullet.png");
        speed = 10.0f;
    }
    @Override
    void update() {
    position.y+=speed;
    }
}
