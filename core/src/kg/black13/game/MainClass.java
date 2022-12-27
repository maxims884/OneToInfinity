package kg.black13.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import kg.black13.game.objects.BackgroundActor;

import kg.black13.game.screens.GameOverScreen;
import kg.black13.game.screens.MainMenuScreen;

public class MainClass extends Game {

    private kg.black13.game.screens.GameOverScreen.Callback callback;
    public MainClass(GameOverScreen.Callback callback) {
        this.callback = callback;
    }

    // Объявляем наш шрифт и символы для него (чтобы нормально читались русские буковки)
    public BitmapFont font, levels;
    private static final String FONT_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    public BackgroundActor background;
    @Override
    public void create() {
        // Я взял шрифт RussoOne с Google Fonts. Сконвертировал его в TTF. (как я понял, только ttf и поддерживается)
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/russoone.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = Gdx.graphics.getHeight() / 18; // Размер шрифта. Я сделал его исходя из размеров экрана. Правда коряво, но вы сами можете поиграться, как вам угодно.
        param.characters = FONT_CHARACTERS; // Наши символы
        font = generator.generateFont(param); // Генерируем шрифт
        param.size = Gdx.graphics.getHeight() / 20;
        levels = generator.generateFont(param);
        font.setColor(Color.WHITE); // Цвет белый
        levels.setColor(Color.WHITE);
        generator.dispose(); // Уничтожаем наш генератор за ненадобностью.

        background = new BackgroundActor();
        background.setPosition(0, 0);
        this.setScreen(new MainMenuScreen(this,callback));
    }
        @Override
        public void render() {
            super.render();

        }
}