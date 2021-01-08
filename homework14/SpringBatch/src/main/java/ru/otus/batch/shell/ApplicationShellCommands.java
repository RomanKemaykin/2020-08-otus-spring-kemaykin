package ru.otus.batch.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.batch.configs.YamlProps;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommands {
    private final JobOperator jobOperator;
    private final YamlProps props;

    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws Exception {
        Long executionId = jobOperator.start(props.getJobName(), null);
    }

}
