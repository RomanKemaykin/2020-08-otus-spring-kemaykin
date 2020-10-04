package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.configs.YamlProps;
import ru.otus.library.service.BookCrudService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommands {
    private final YamlProps props;

    private final BookCrudService bookCrudService;

    @ShellMethod(value = "Book add", key = {"ba", "bookAdd"})
    public void bookAdd() {
        bookCrudService.add();
    }

    @ShellMethod(value = "Book list", key = {"bl", "bookList"})
    public void bookList() {
        bookCrudService.listAll();
    }

    @ShellMethod(value = "Book show", key = {"bs", "bookShow"})
    public void bookShow(@ShellOption(defaultValue = "1") String stringId) {
        long id = Long.valueOf(stringId);
        bookCrudService.showBookById(id);
    }

    @ShellMethod(value = "Book delete", key = {"bd", "bookDelete"})
    public void bookDelete(@ShellOption(defaultValue = "1") String stringId) {
        long id = Long.valueOf(stringId);
        bookCrudService.deleteBookById(id);
    }

    @ShellMethod(value = "Book modify", key = {"bm", "bookModify"})
    public void bookModify(@ShellOption(defaultValue = "1") String stringId) {
        long id = Long.valueOf(stringId);
        bookCrudService.modifyBookById(id);
    }

}
