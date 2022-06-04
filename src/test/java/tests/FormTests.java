package tests;

import org.junit.jupiter.api.Test;

public class FormTests extends TestBase {
    @Test
    public void formTest() {
        automationPracticeForm.openPage();

        TestData testData = new TestData();

        automationPracticeForm
                .setFullName(testData.firstName, testData.lastName)
                //.setFirstName(firstName)
                //.setLastName(lastName)
                .setEmail(testData.email)
                .setGender(testData.gender)
                .setPhoneNumber(testData.phone)
                .setDateOfBirth(testData.birthMonth,
                        testData.birthYear,
                        testData.birthDay)
                //.setDateOfBirthWithKeys(birthMonth, birthYear, birthDay)
                .setSubjects(testData.subject)
                .setHobby(testData.hobby)
                .uploadFile(testData.fileName)
                .setAddress(testData.address)
                .setState(testData.state)
                .setCity(testData.city)
                .sendForm();
        automationPracticeForm
                .checkFormOpened()
                .checkResult("Student Name", testData.firstName + " " + testData.lastName)
                .checkResult("Student Email", testData.email)
                .checkResult("Gender", testData.gender)
                .checkResult("Mobile", testData.phone)
                .checkResult("Date of Birth", testData.birthDate)
                .checkResult("Subjects", testData.subject)
                .checkResult("Hobbies", testData.hobby)
                .checkResult("Picture", testData.fileName)
                .checkResult("Address", testData.address)
                .checkResult("State and City", testData.state + " " + testData.city);
    }
}