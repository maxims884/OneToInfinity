package kg.black13.game.managers;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class XMLparse {
    public Array<String> XMLparseLevels() {
        Array<String> levels = new Array<String>();
        Array<Integer> int_levels = new Array<Integer>();

        FileHandle dirHandle;
        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            dirHandle = Gdx.files.internal("xml/levels");
        } else {
            dirHandle = Gdx.files.internal(System.getProperty("user.dir") + "/assets/xml/levels"); // хак для desktop проекта, так как он почему-то не видел этих файлов. Создайте символическую ссылку папки assets в в корне desktop-проекта на папку assets android-проекта
        }
        for (FileHandle entry : dirHandle.list()) {
            levels.add(entry.name().split(".xml")[0]);
        }

        // Эту жесть я сделал потому что сортировка строк немного не верно сортирует уровни. В комментариях подскажут как это сделать красивее. Я не особо Java программист. Я только учусь :)
        for (int i = 0; i < levels.size; i++) {
            int_levels.add(Integer.parseInt(levels.get(i)));
        }
        int_levels.sort();
        levels.clear();

        for (int i = 0; i < int_levels.size; i++) {
            levels.add(String.valueOf(int_levels.get(i)));
        }
        return levels;
    }
}