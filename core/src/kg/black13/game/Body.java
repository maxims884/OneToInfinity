package kg.black13.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Body {
    Texture texture;
    Texture asteroidTexture;
    Vector2 position;
    float speed;

    public Body(){};

    public void render(SpriteBatch spriteBath)
    {
        spriteBath.draw(texture, position.x, position.y);
    }

    abstract  void update();

    public Rectangle getRect()
    {
        return new Rectangle(position.x,position.y, texture.getWidth(), texture.getHeight());
    }

    public Circle getAsteroidCircle()
    {
        Vector2 center = new Vector2();

        center.x = position.x + (asteroidTexture.getWidth()/2);
        center.y = position.y + (asteroidTexture.getHeight()/2);
        return new Circle(center, (asteroidTexture.getHeight()/2));
//        return new Rectangle(position.x,position.y, asteroidTexture.getWidth() * 1.2f, asteroidTexture.getHeight() * 1.2f);
    }
}
