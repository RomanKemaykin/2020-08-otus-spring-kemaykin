package ru.otus.springBootstudentTesting.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class YamlProps {
    private Integer correctAnswersCountForPass;

    private String testingItemsFileName;

    private Locale locale;

    public Integer getCorrectAnswersCountForPass() {
        return correctAnswersCountForPass;
    }

    public void setCorrectAnswersCountForPass(Integer correctAnswersCountForPass) {
        this.correctAnswersCountForPass = correctAnswersCountForPass;
    }

    public String getTestingItemsFileName() {
        return testingItemsFileName;
    }

    public void setTestingItemsFileName(String testingItemsFileName) {
        this.testingItemsFileName = testingItemsFileName;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
