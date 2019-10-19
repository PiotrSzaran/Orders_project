package pl.szaran;

import j2html.TagCreator;
import j2html.tags.ContainerTag;
import pl.szaran.service.CategoryService;

import java.io.FileWriter;
import java.io.IOException;

import static j2html.TagCreator.*;
import static j2html.TagCreator.p;

public class HtmlExport {
    public static void main(String[] args) {

        CategoryService categoryService = new CategoryService();
        var categories = categoryService.getCategories();


        try (
                FileWriter fw = new FileWriter("test.html")) {

            TagCreator.html(
                    head(
                            title("Kategorie")
                    ),

                    body(
                            div(attrs("#categories"),
                                    categories.stream().map(category ->
                                            div(attrs(".category"),
                                                    p(category.getName())
                                            )
                                    ).toArray(ContainerTag[]::new))
                    )
            )
                    .render(fw);

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
