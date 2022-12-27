package kg.black13.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Background extends  Body {
    Texture textureStar;
    Star[] stars;

    Stage st;
    public Background(Stage stage)
    {
        st=stage;
        textureStar = new Texture("images/star.png");
        stars  = new Star[75];
        for(int i=0; i < stars.length; i++)
        { stars[i] = new Star();}
    }

    class Star extends Body {

        public Star(){
            position = new Vector2((float)Math.random()*st.getWidth() , (float) Math.random()*st.getHeight() );
            speed = 1.0f + (float) Math.random() * 2.0f;
        }
        @Override
        void update() {
            position.y -= speed;
            if(position.y <-20){
                position.y = st.getHeight();
                position.x = (float) Math.random()*st.getWidth();
                speed = 1.0f + (float) Math.random() * 2.0f ;
            }
        }
    }

public  void render(SpriteBatch spriteBatch)
{
    for(Star s :stars)
    {
        if(Math.random() < 0.99)
        {
            float scale = s.speed * 0.2f + 0.5f;
            spriteBatch.draw(textureStar,s.position.x, s.position.y,6,6,12,12, scale,scale,0,0,0,12,12,false,false);
        }
    }
}


    @Override
    public void update() {
        for(Star s:stars)
        {
            s.update();
        }

    }
}
