package kg.black13.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import kg.black13.game.managers.InputManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Hero extends Body {

    private Calendar calendar = new GregorianCalendar();
    private static Hero _instance;
    Float width;
    Float height;

    public List<kg.black13.game.Bullet> getBullets() {
        return bullets;
    }

    List<kg.black13.game.Bullet> bullets;
    OrthographicCamera camera;
    private Stage stage;
//    Texture bulletTexture;
    public Hero(Stage stage)
    {
        bullets = new ArrayList<kg.black13.game.Bullet>();
        _instance = this;
        this.stage=stage;
        // получаем размер экрана телефона (ширину и высоту)
        width = stage.getWidth();
        height = stage.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width/2, height/2);

        texture = new Texture("images/sheep.png");
        position = new Vector2(width/2-100,0);

        speed = 10.0f;
    }

    @Override
    public void render(SpriteBatch spriteBath) {
        for (kg.black13.game.Bullet b : bullets)
        {
            spriteBath.draw(b.texture,b.position.x+60,b.position.y+260);
        }
        super.render(spriteBath);
    }



    public static Hero instance() {
        return _instance;
    }

    @Override
    public  void update() {
        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {if(position.x>0)position.x -= speed;}

        if(Gdx.input.isKeyPressed(Input.Keys.D))
        {if( position.x< width )position.x += speed;}

        if(Gdx.input.isKeyPressed(Input.Keys.W))
        {position.y += speed;}

        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {position.y -= speed;}

        for(int i = 0; i<bullets.size();i++)
        {
            bullets.get(i).update();
            if(bullets.get(i).position.y>stage.getHeight()) bullets.remove(i);
        }
        Gdx.input.setInputProcessor(new InputManager(camera));// доступ класса InputManager для получения касаний/нажатий
        addBullet();
    }

    public void handleTouch(float x,float y){
        position = new Vector2(x-40, height-y-40);
    }

    public void addBullet(){
        long timeToCompare = calendar.getTimeInMillis() + 100;
        if(timeToCompare <= new GregorianCalendar().getTimeInMillis())
        {
            bullets.add(new Bullet(new Vector2(position.x,position.y)));
            calendar = new GregorianCalendar();
        }
    }
}
