package kg.black13.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import kg.black13.game.MainClass;

public class MainMenuScreen implements Screen {

    private GameOverScreen.Callback callback;
    // наш основной класс
    final MainClass game;
    OrthographicCamera camera;
    // Объявим все необходимые объекты
    private Stage stage;
    private TextButton play, exit;
    private Table table;
    private Label.LabelStyle labelStyle;

    // Конструктор принимает объект нашего основого класса (объяснения позже)
    public MainMenuScreen(final MainClass gam, final GameOverScreen.Callback callback) {
        this.callback = callback;
        game = gam;

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        // Сцена -- она поможет существенно уменьшить количество кода и упростить нам жизнь
        stage = new Stage(new ScreenViewport());
        stage.addActor(game.background);

        // Скин для кнопок. Изображения вы найдете по ссылке внизу статьи
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("images/game/images.pack"));
        skin.addRegions(buttonAtlas);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = game.font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-up");

        labelStyle = new Label.LabelStyle();
        labelStyle.font = game.font;
        table = new Table();
        table.setFillParent(true);

        // Кнопка играть. Добавляем новый listener, чтобы слушать события касания. После касания, выбрирует и переключает на экран выбора уровней, а этот экран уничтожается
        play = new TextButton("Играть", textButtonStyle);
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
        exit = new TextButton("Выход", textButtonStyle);
        exit.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(20);
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

        Gdx.input.setInputProcessor(stage);  // Устанавливаем нашу сцену основным процессором для ввода (нажатия, касания, клавиатура etc.)
        Gdx.input.setCatchBackKey(true); // Это нужно для того, чтобы пользователь возвращался назад, в случае нажатия на кнопку Назад на своем устройстве

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        if    (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
           Gdx.app.exit();
        }

        // Очищаем экран и устанавливаем цвет фона черным
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Рисуем сцену

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
    public void resume() {


    }

    @Override
    public void dispose() {
        // Уничтожаем сцену и объект game.
        stage.dispose();
        game.dispose();
    }
}