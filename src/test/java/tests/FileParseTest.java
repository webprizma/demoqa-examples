package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.domain.Person;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParseTest {

    ClassLoader classLoader = FileParseTest.class.getClassLoader();

    @Disabled
    @Test
    void pdfTest() throws IOException {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File pdf = $("a[href*='junit-user-guide-5.8.2.pdf']").download();
        PDF pdfParsed = new PDF(pdf);
        assertThat(pdfParsed.author).isEqualTo("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein");
    }

    @Disabled
    @Test
    void xlsTest() throws FileNotFoundException {
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
    void csvTest() throws IOException, CsvException {
        try (InputStream is = classLoader.getResourceAsStream("1.csv")) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(is, UTF_8));
            List<String[]> csv = csvReader.readAll();
            assertThat(csv).contains(
                    new String[]{"sdhgdfhjdre", "dshghgdshg", "testData"}
            );
        }
    }

    @Disabled
    @Test
    void zipTest() {
        try (InputStream is = classLoader.getResourceAsStream("1.zip")) {
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                assertThat(fileName).isEqualTo("1.txt");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Disabled
    @Test
    void jsonTest() {
        try (InputStream is = classLoader.getResourceAsStream("1.json")) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(new InputStreamReader(is), JsonObject.class);
            assertThat(jsonObject.get("name").getAsString()).isEqualTo("Yuri");
            assertThat(jsonObject.get("goodBoy").getAsBoolean()).isEqualTo(true);
            assertThat(jsonObject.get("passport").getAsJsonObject().get("number").getAsInt()).isEqualTo(1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Disabled
    @Test
    void jsonTestAlt() {
        try (InputStream is = classLoader.getResourceAsStream("1.json")) {
            Gson gson = new Gson();
            Person jsonObject = gson.fromJson(new InputStreamReader(is), Person.class);
            assertThat(jsonObject.getName()).isEqualTo("Yuri");
            assertThat(jsonObject.getGoodBoy()).isEqualTo(true);
            assertThat(jsonObject.getPassport().getNumber()).isEqualTo(1234);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("Реализовать чтение и проверку содержимого каждого файла из архива testArchive.zip")
    @Test
    void testArchiveTest() throws IOException, CsvException {
        try (ZipFile zipFile = new ZipFile("src/test/resources/testArchive.zip")) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                if (entry.getName().contains("csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(stream, UTF_8));
                    List<String[]> csv = csvReader.readAll();
                    assertThat(csv).contains(new String[]{"johnson81;4081;Craig;Johnson"});
                    System.out.println("Информация в CSV файле найдена");
                } else if (entry.getName().contains(("pdf"))) {
                    PDF pdfParsed = new PDF(stream);
                    System.out.println(pdfParsed.creator);
                    assertThat(pdfParsed.creator).isEqualTo("Rave (http://www.nevrona.com/rave)");
                    System.out.println("Информация в PDF файле найдена");
                } else if (entry.getName().contains(("xls"))) {
                    XLS xlsParsed = new XLS(stream);
                    assertThat(
                            xlsParsed.excel.getSheetAt(0)
                                    .getRow(5)
                                    .getCell(2)
                                    .getStringCellValue()
                    ).contains("Ashish");
                    System.out.println("Информация в XLS файле найдена");
                } else {
                    System.out.println("Расширение файла не распознано");
                }
            }
        }
    }

    @DisplayName("Реализовать разбор json файла библиотекой Jackson")
    @Test
    void jsonJackson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = classLoader.getResourceAsStream("myExample.json")) {
            JsonNode jsonNode = objectMapper.readTree(new InputStreamReader(is, UTF_8));
            assertThat(jsonNode.get("Name").asText()).isEqualTo("Craig");
            assertThat(jsonNode.withArray("BookInterests").findValue("Book").asText()).isEqualTo("The Kite Runner");
            assertThat((jsonNode.findValue("FoodInterests")).withArray("Breakfast").findValue("Bread").asText()).isEqualTo("Whole wheat");
        }
    }
}
