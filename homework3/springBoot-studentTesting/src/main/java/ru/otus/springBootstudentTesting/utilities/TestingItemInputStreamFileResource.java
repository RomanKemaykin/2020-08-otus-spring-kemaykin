package ru.otus.springBootstudentTesting.utilities;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.otus.springBootstudentTesting.configs.YamlProps;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@Component
public class TestingItemInputStreamFileResource implements TestingItemInputStream {
    private final YamlProps props;

    private final String testingItemsFileNamePath;

    public TestingItemInputStreamFileResource(YamlProps props) {
        this.props = props;
        this.testingItemsFileNamePath = Paths.get("i18n", props.getLocale().toString(), props.getTestingItemsFileName()).toString();
    }

    @Override
    public InputStream inputStream() {
        ClassPathResource classPathResource = new ClassPathResource(testingItemsFileNamePath);
        InputStream inputStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        try {
            inputStream = classPathResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
