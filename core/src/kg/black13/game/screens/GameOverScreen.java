package kg.black13.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import kg.black13.game.MainClass;

public class GameOverScreen implements Screen  {

    public interface Callback{
        void callingBack();
    }

    Callback callback;
    final MainClass game;
    private final Stage stage;
    int score=0;
    int MostScore=0;
    private TextButton level;
    private final SpriteBatch batch;
    static BitmapFont text;

    public static final String FONT_CHARACTERS = "йцукенгшщзхъывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    public GameOverScreen(MainClass gam, int score, int MostScore, final Callback callback) {
        this.callback = callback;
        game = gam;
        this.score = score;
        this.MostScore = MostScore;
        stage = new Stage(new ScreenViewport());

        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/game/images.pack"));
        skin.addRegions(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.levels;
        textButtonStyle.up = skin.getDrawable("level-up");
        textButtonStyle.down = skin.getDrawable("level-down");
        textButtonStyle.checked = skin.getDrawable("level-up");
        batch = new SpriteBatch();
        text = new BitmapFont();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/russoone.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = Gdx.graphics.getHeight() / 18; // Размер шрифта. Я сделал его исходя из размеров экрана. Правда коряво, но вы сами можете поиграться, как вам угодно.
        // text = TrueTypeFontFactory.createBitmapFont(Gdx.files.internal("font.ttf"), FONT_CHARACTERS, 12.5f, 7.5f, 1.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        param.characters = FONT_CHARACTERS; // Наши символы
        text = generator.generateFont(param); // Генерируем шрифт
        param.size = Gdx.graphics.getHeight() / 20;
        text.setColor(Color.RED); //Красный

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = game.levels; // Берем размер шрифта из класса MyGame
        Table table = new Table();
        table.row().pad(20); // Новая строка + отступы
        table.center();
        table.setFillParent(true);
        generator.dispose();

        table = new Table();
        table.setFillParent(true);


        // Кнопка играть. Добавляем новый listener, чтобы слушать события касания. После касания, выбрирует и переключает на экран выбора уровней, а этот экран уничтожается
        TextButton play = new TextButton("Еще раз", textButtonStyle);
        play.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.input.vibrate(20);
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayScreen(game,callback));
                dispose();
            };
        });

        // Кнопка выхода. Вообще это не обязательно. Просто для красоты, ибо обычно пользователь жмет на кнопку телефона.
        TextButton exit = new TextButton("Выход", textButtonStyle);
        exit.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                Gdx.input.vibrate(20);
                return true;
            };
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                dispose();
            };
        });
        table.add(play);
        table.row();
        table.add(exit);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
        Gdx.input.setCatchBackKey(true);

        callback.callingBack();

        // Это случится, когда пользователь нажмет на кнопку Назад на своем устройстве. Мы переведем его на прошлый экран.
//        stage.setHardKeyListener(new PlayStage.OnHardKeyListener() {
//            @Override
//            public void onHardKey(int keyCode, int state) {
//                if (keyCode == Input.Keys.BACK && state == 1){
//                    game.setScreen(new MainMenuScreen(game));
//                }
//            }
//        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        text.draw(batch,String.valueOf(score),stage.getWidth()/2-100,stage.getHeight()-(stage.getHeight()/4));
        text.draw(batch,"Рекорд: ",10,(stage.getHeight()/3));
        text.draw(batch,String.valueOf(MostScore),stage.getWidth() - 300,(stage.getHeight()/3));
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);

    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        game.dispose();
    }
}