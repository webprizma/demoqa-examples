package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.$;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideFileTest {
    @Test
    void downloadTest() throws Exception {                    // типовое исключение
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
//        try {
//            File file = $("#raw-url").download();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }                                                   //так в тестах лучше не делать
        File file = $("#raw-url").download();
//        InputStream is = new FileInputStream(file);
//        byte[] fileContent = is.readAllBytes();
//        is.close();                                         // неправильный подход
        try (InputStream is = new FileInputStream(file)) {
            byte[] fileContent = is.readAllBytes();
            assertThat(new String(fileContent, UTF_8)).contains("Contributions to JUnit 5");
        }
    }

    @Test
    void uploadTest() {
        Selenide.open("https://the-internet.herokuapp.com/upload");
//        $("input[type='file']").uploadFile(new File("/src/test/resources/1.png"));  //внутри jar нет путей, не сработает
        $("input[type='file']").uploadFromClasspath("1.png");
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(Condition.text("1.png"));
    }
}
