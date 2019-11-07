package pl.szaran.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class JsonConverter<T> {

    private final String jsonFilename;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    public JsonConverter(String jsonFilename) {
        this.jsonFilename = jsonFilename;
    }

    //konwersja do json:
    public void toJson(final T element) {
        try (FileWriter fileWriter = new FileWriter(jsonFilename)){
            if (element == null) {
                throw new NullPointerException("ELEMENT IS NULL");
            }
            gson.toJson(element, fileWriter);

        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("TO JSON - JSON FILENAME EXCEPTION");
        }
    }
}
