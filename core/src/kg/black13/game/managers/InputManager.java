package kg.black13.game.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

import kg.black13.game.Hero;

public class InputManager extends InputAdapter {

    OrthographicCamera camera;

    public InputManager(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Hero.instance().handleTouch(screenX, screenY);
        return super.touchDragged(screenX, screenY, pointer);
    }
}