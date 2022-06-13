package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import tests.domain.Person;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {

    ClassLoader classLoader = FileParseTest.class.getClassLoader();

    @Test
    void pdfTest() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File pdf = $("a[href*='junit-user-guide-5.8.2.pdf']").download();
        PDF pdfParsed = new PDF(pdf);
        assertThat(pdfParsed.author).isEqualTo("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein");
    }

    @Test
    void xlsTest() throws Exception {
        Selenide.open("http://romashka2008.ru/price");
        File xls = $("a[href='/f/prajs_ot_0806.xls']").download();
        XLS xlsParsed = new XLS(xls);
        assertThat(
                xlsParsed.excel.getSheetAt(0)
                        .getRow(22)
                        .getCell(2)
                        .getStringCellValue()
        ).contains("БОЛЬШАЯ РАСПРОДАЖА");
    }

    @Test
    void csvTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("1.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(is, UTF_8));
        List<String[]> csv = csvReader.readAll();
        assertThat(csv).contains(
                new String[]{"sdhgdfhjdre", "dshghgdshg", "testData"}
        );
    }

    @Test
    void zipTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("1.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            String fileName = entry.getName();
            assertThat(fileName).isEqualTo("1.txt");

            //достаем файл из архива
            FileOutputStream fout = new FileOutputStream("build/resources/test/" + entry.getName());
            for (int c = zis.read(); c != -1; c = zis.read()) {
                fout.write(c);
            }
            fout.flush();
            zis.closeEntry();
            fout.close();

            //проверяем содержимое файла
            FileInputStream fileInZIP = new FileInputStream("build/resources/test/" + entry.getName());
            byte[] fileContent = fileInZIP.readAllBytes();
            assertThat(new String(fileContent, UTF_8)).contains("привет");
        }
    }

    @Test
    void jsonTest() {
        InputStream is = classLoader.getResourceAsStream("1.json");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
        assertThat(jsonObject.get("name").getAsString()).isEqualTo("Yuri");
        assertThat(jsonObject.get("goodBoy").getAsBoolean()).isEqualTo(true);
        assertThat(jsonObject.get("passport").getAsJsonObject().get("number").getAsInt()).isEqualTo(1234);
    }

    @Test
    void jsonTestAlt() {
        InputStream is = classLoader.getResourceAsStream("1.json");
        Gson gson = new Gson();
        Person jsonObject = gson.fromJson(new InputStreamReader(is), Person.class);
        assertThat(jsonObject.getName()).isEqualTo("Yuri");
        assertThat(jsonObject.getGoodBoy()).isEqualTo(true);
        assertThat(jsonObject.getPassport().getNumber()).isEqualTo(1234);
    }

    //построить свой JSON и сделать с ним ассёрты
}
