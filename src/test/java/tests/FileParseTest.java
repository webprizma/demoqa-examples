package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tests.domain.Person;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {

    ClassLoader classLoader = FileParseTest.class.getClassLoader();
    @Disabled
    @Test
    void pdfTest() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File pdf = $("a[href*='junit-user-guide-5.8.2.pdf']").download();
        PDF pdfParsed = new PDF(pdf);
        assertThat(pdfParsed.author).isEqualTo("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein");
    }

    @Disabled
    @Test
    void xlsTest() throws Exception {
        Selenide.open("http://romashka2008.ru/price");
        File xls = $x("//h1[text()='Скачать Прайс-лист Excel']").sibling(0).$("a").download();
        XLS xlsParsed = new XLS(xls);
        assertThat(
                xlsParsed.excel.getSheetAt(0)
                        .getRow(19)
                        .getCell(3)
                        .getStringCellValue()
        ).contains("Цена");
    }
    @Disabled
    @Test
    void csvTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("1.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(is, UTF_8));
        List<String[]> csv = csvReader.readAll();
        assertThat(csv).contains(
                new String[]{"sdhgdfhjdre", "dshghgdshg", "testData"}
        );
    }
    @Disabled
    @Test
    void zipTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("1.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            String fileName = entry.getName();
            assertThat(fileName).isEqualTo("1.txt");
            //достаем файл из архива
            FileOutputStream fout = new FileOutputStream("src/test/resources/" + entry.getName());
            for (int c = zis.read(); c != -1; c = zis.read()) {
                fout.write(c);
            }
            fout.flush();
            zis.closeEntry();
            fout.close();
            //проверяем содержимое файла
            FileInputStream fileInZIP = new FileInputStream("src/test/resources/" + entry.getName());
            byte[] fileContent = fileInZIP.readAllBytes();
            assertThat(new String(fileContent, UTF_8)).contains("привет");
        }
    }
    @Disabled
    @Test
    void jsonTest() {
        InputStream is = classLoader.getResourceAsStream("1.json");
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
        assertThat(jsonObject.get("name").getAsString()).isEqualTo("Yuri");
        assertThat(jsonObject.get("goodBoy").getAsBoolean()).isEqualTo(true);
        assertThat(jsonObject.get("passport").getAsJsonObject().get("number").getAsInt()).isEqualTo(1234);
    }
    @Disabled
    @Test
    void jsonTestAlt() {
        InputStream is = classLoader.getResourceAsStream("1.json");
        Gson gson = new Gson();
        Person jsonObject = gson.fromJson(new InputStreamReader(is), Person.class);
        assertThat(jsonObject.getName()).isEqualTo("Yuri");
        assertThat(jsonObject.getGoodBoy()).isEqualTo(true);
        assertThat(jsonObject.getPassport().getNumber()).isEqualTo(1234);
    }

    //Реализовать чтение и проверку содержимого каждого файла из архива testArchive.zip

    @Disabled
    @Test
    void testArchiveTest() throws Exception {
        InputStream is = classLoader.getResourceAsStream("testArchive.zip");
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            String fileName = entry.getName();
            FileOutputStream fout = new FileOutputStream("build/resources/test/testArchive/" + fileName);
            for (int c = zis.read(); c != -1; c = zis.read()) {
                fout.write(c);
            }
            fout.flush();
            zis.closeEntry();
            fout.close();

            FileInputStream fileInZIP = new FileInputStream("build/resources/test/testArchive/" + fileName);
            if(fileName.contains("csv")) {
                CSVReader csvReader = new CSVReader(new InputStreamReader(fileInZIP, UTF_8));
                List<String[]> csv = csvReader.readAll();
                assertThat(csv).contains(new String[]{"johnson81;4081;Craig;Johnson"}
                );
            } else if (fileName.contains(("pdf"))) {
                PDF pdfParsed = new PDF(fileInZIP);
                System.out.println(pdfParsed.creator);
                assertThat(pdfParsed.creator).isEqualTo("Rave (http://www.nevrona.com/rave)");
            } else if (fileName.contains(("xls"))) {
                XLS xlsParsed = new XLS(fileInZIP);
                assertThat(
                        xlsParsed.excel.getSheetAt(0)
                                .getRow(5)
                                .getCell(2)
                                .getStringCellValue()
                ).contains("Ashish");
            } else {
                System.out.println("Расширение файла не распознано");
            }
        }
    }
    //Реализовать разбор json  файла библиотекой Jackson https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core/2.13.1
    //Придумать реальный объект и описать его в виде json
    //В идеале json должен содержать массив

    @Test
    void jsonJackson() {
        InputStream is = classLoader.getResourceAsStream("myExample.json");
//        Gson gson = new Gson();
//        JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
//        assertThat(jsonObject.get("name").getAsString()).isEqualTo("Yuri");
//        assertThat(jsonObject.get("goodBoy").getAsBoolean()).isEqualTo(true);
//        assertThat(jsonObject.get("passport").getAsJsonObject().get("number").getAsInt()).isEqualTo(1234);

    }


}
