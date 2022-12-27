package kg.black13.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import kg.black13.game.Background;

import java.util.ArrayList;
import java.util.List;

import kg.black13.game.Asteroid;
import kg.black13.game.Hero;
import kg.black13.game.MainClass;

public class PlayScreen implements Screen  {
    private GameOverScreen.Callback callback;

    final kg.black13.game.MainClass game;
    private final Background background;

    int score = 0;
    private final Stage stage;
    private final kg.black13.game.Hero hero;
    private final List<kg.black13.game.Asteroid> asteroids;
    private final SpriteBatch batch;
    static BitmapFont text;

    public static final String FONT_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    // В следующем уроке мы подробно рассмотрим этот класс, поэтому комментировать ничего не буду. Да тут и так понятно.
    public PlayScreen(final MainClass gam, GameOverScreen.Callback callback) {
        this.callback = callback;
        game = gam;

        stage = new Stage(new ScreenViewport());
        stage.addActor(game.background);

        text = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/russoone.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = Gdx.graphics.getHeight() / 18; // Размер шрифта. Я сделал его исходя из размеров экрана. Правда коряво, но вы сами можете поиграться, как вам угодно.
       // text = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("font.ttf"), FONT_CHARACTERS, 12.5f, 7.5f, 1.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        param.characters = FONT_CHARACTERS; // Наши символы
        text = generator.generateFont(param); // Генерируем шрифт
        param.size = Gdx.graphics.getHeight() / 20;
        text.setColor(Color.RED); //Красный

        generator.dispose();

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        batch = new SpriteBatch();
        hero = new Hero(stage);

        background = new Background(stage);

        asteroids = new ArrayList<kg.black13.game.Asteroid>();
		for(int i=0;i< 50; i++) {
		    asteroids.add( new kg.black13.game.Asteroid(stage));
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if    (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
        {

            game.setScreen(new MainMenuScreen(game,callback));

            dispose();
        }

        // Рисуем сцену
        stage.act(delta);
        stage.draw();


		batch.begin();

        background.render(batch);
        hero.render(batch);


        update();
        for(kg.black13.game.Asteroid s : asteroids){ s.render(batch);}
        text.draw(batch,String.valueOf(score),stage.getWidth()-200,stage.getHeight()-50);
        batch.end();
    }

    private void update()
    {
        hero.update();
        background.update();

        int [] shout = new int[asteroids.size()];
        for(int i=0; i< asteroids.size();i++)
        {
            Asteroid a = asteroids.get(i);
            a.update();
            if(isAsteroidGainedBody (hero.getRect(),a.getAsteroidCircle()))

            {

                System.out.println("Попал в героя");
                Preferences prefs = Gdx.app.getPreferences("My Preferences");
                int MostScore = prefs.getInteger("score");

                if (score > MostScore){
                prefs.putInteger("score",score);
                prefs.flush();}


                game.setScreen(new GameOverScreen(game,score,MostScore,callback));
                //dispose();
                //hero = null;
            }

            for(int j=0;j<hero.getBullets().size();j++)
            {
                if(isAsteroidGainedBody(hero.getBullets().get(j).getRect(),a.getAsteroidCircle()))
                {
                    shout[i]++;
                    //if(shout[i]>=2){
                    System.out.println("Попал в астероид");
                    score++;
                   // asteroids.remove(i);
                    asteroids.get(i).recreate();
                    hero.getBullets().remove(j);}
                //}
            }
        }
    }


    public boolean isAsteroidGainedBody(Rectangle starRect, Circle asteroidCircle)
    {
        return Intersector.overlaps(asteroidCircle,starRect);
    }

    @Override
    public void resize(int width, int height) {stage.getViewport().update(width, height,true);}

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() { hero.update(); }

    @Override
    public void dispose() {
        // Уничтожаем сцену и объект game.
        stage.dispose();
        game.dispose();
    }
}