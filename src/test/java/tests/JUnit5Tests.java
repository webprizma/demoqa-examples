package tests;

import org.junit.jupiter.api.*;

public class JUnit5Tests {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Тестирование началось");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Тест запущен");
    }

    @AfterEach
    void afterEach() {
        System.out.println("Тест завершен");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Тестирование завершено");
    }

    @Test
    public void successfulTest() {
        Assertions.assertTrue(3 > 2);
    }

    @Test
    public void unsuccessfulTest() {
        Assertions.assertTrue(3 < 2);
    }

    @Test
    public void failedTest() {
        throw new RuntimeException();
    }
}