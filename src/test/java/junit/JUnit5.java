package junit;

import com.google.common.reflect.ClassPath;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;


public class JUnit5 {
    public static void main(String[] args) throws Exception {

        // Get all classes
        Set<? extends Class<?>> classes = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(classInfo -> classInfo.getName().contains("JUnit5Tests"))
                .map(ClassPath.ClassInfo::load)
                .collect(Collectors.toSet());

        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getDeclaredMethods();

            for (Method method : methods) {
                BeforeAll beforeAllAnnotation = method.getAnnotation(BeforeAll.class);
                if (beforeAllAnnotation != null) {
                    method.setAccessible(true);
                    method.invoke(aClass.getDeclaredConstructor().newInstance());
                }
            }

            for (Method method : methods) {
                Test testAnnotation = method.getAnnotation(Test.class);
                if (testAnnotation != null) {
                    runBeforeEach();
                    runTest(aClass, method);
                    runAfterEach();
                }
            }

            for (Method method : methods) {
                AfterAll afterAllAnnotation = method.getAnnotation(AfterAll.class);
                if (afterAllAnnotation != null) {
                    method.setAccessible(true);
                    method.invoke(aClass.getDeclaredConstructor().newInstance());
                }
            }

        }
    }

    public static void runAfterEach() throws Exception {

        // Get all classes
        Set<? extends Class<?>> classes = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(classInfo -> classInfo.getName().contains("JUnit5Tests"))
                .map(ClassPath.ClassInfo::load)
                .collect(Collectors.toSet());

        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                AfterEach afterEachAnnotation = method.getAnnotation(AfterEach.class);
                if (afterEachAnnotation != null) {
                    method.setAccessible(true);
                    method.invoke(aClass.getDeclaredConstructor().newInstance());
                }
            }
        }
    }

    public static void runBeforeEach() throws Exception {

        // Get all classes
        Set<? extends Class<?>> classes = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(classInfo -> classInfo.getName().contains("JUnit5Tests"))
                .map(ClassPath.ClassInfo::load)
                .collect(Collectors.toSet());

        for (Class<?> aClass : classes) {
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                BeforeEach beforeEachAnnotation = method.getAnnotation(BeforeEach.class);
                if (beforeEachAnnotation != null) {
                    method.setAccessible(true);
                    method.invoke(aClass.getDeclaredConstructor().newInstance());
                }
            }
        }
    }

    private static void runTest(Class<?> aClass, Method method) {
        method.setAccessible(true);
        try {
            method.invoke(aClass.getDeclaredConstructor().newInstance());
        } catch (Throwable t) {
            if (AssertionError.class.isAssignableFrom(t.getCause().getClass())) {
                System.out.println(method.getName() + ": желтый цвет");
            } else {
                System.out.println(method.getName() + ": красный цвет");
            }
            return;
        }
        System.out.println(method.getName() + ": зелёный цвет");
    }
}