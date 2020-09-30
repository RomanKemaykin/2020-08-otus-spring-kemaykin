package ru.otus.springBootstudentTesting.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.springBootstudentTesting.configs.YamlProps;
import ru.otus.springBootstudentTesting.service.TestingService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommands {
    private final YamlProps props;

    private final TestingService testingService;

    private final MessageSource messageSource;

    private String name;

    private String lastName;

    private final static String DEFAULT_VALUE_NAME = "anonymous";

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = DEFAULT_VALUE_NAME) String userName, @ShellOption(defaultValue = DEFAULT_VALUE_NAME) String userLastName) {
        String greetingMessage = messageSource.getMessage("greeting.message", new String[]{userName, userLastName}, props.getLocale());
        this.name = userName;
        this.lastName = userLastName;
        return greetingMessage;
    }

    @ShellMethod(value = "Run testing", key = {"rt", "runtest"})
    @ShellMethodAvailability(value = "isRunTestCommandAvailable")
    public void runTest() {
        testingService.runStudentTesting(name, lastName);
    }

    private Availability isRunTestCommandAvailable() {
        String unavailableReason = messageSource.getMessage("you.should.login", new String[]{}, props.getLocale());
        return ((name == null) || (name.contentEquals(DEFAULT_VALUE_NAME))
                               || (lastName == null)
                               || (lastName.contentEquals(DEFAULT_VALUE_NAME)))
                ? Availability.unavailable(unavailableReason)
                : Availability.available();
    }

}
