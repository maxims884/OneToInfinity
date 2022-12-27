package kg.black13.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Asteroid extends Body {
    Stage st;
    public Asteroid(Stage stage)
    {
        st=stage;
//        if (asteroidTexture == null)
        if(Math.random() < 0.5)    asteroidTexture = new Texture("images/asteroid.png");
        else
            asteroidTexture = new Texture("images/asteroid2.png");
        position = new Vector2((float)(Math.random())*(stage.getWidth()-100),(float)Math.random()*stage.getHeight()+stage.getHeight());
        speed = 3f;
    }

    public void render (SpriteBatch spriteBatch) {
        spriteBatch.draw(asteroidTexture, position.x, position.y);
    }

    public void recreate()
    {
        position = new Vector2( (float) Math.random()*(st.getHeight()-100), (float) Math.random()*st.getHeight()+st.getHeight());
        speed  += 0.8f ;
    }

    @Override
   public void update() {
        position.y -= speed;
        if(position.y < -100) recreate();

    }
}
