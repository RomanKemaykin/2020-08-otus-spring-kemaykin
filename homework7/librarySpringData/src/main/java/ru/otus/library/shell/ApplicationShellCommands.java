package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.configs.YamlProps;
import ru.otus.library.service.BookCommentCrudService;
import ru.otus.library.service.BookCrudService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommands {
    private final YamlProps props;

    private final BookCrudService bookCrudService;
    private final BookCommentCrudService bookCommentCrudService;

    // Book methods

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

    // Book comment methods

    @ShellMethod(value = "Book comments show", key = {"bcs", "bookCommentsShow"})
    public void bookCommentsShow(@ShellOption(defaultValue = "1") String stringId) {
        long id = Long.valueOf(stringId);
        bookCommentCrudService.listAllByBookId(id);
    }

    @ShellMethod(value = "Book comment add", key = {"bca", "bookCommentAdd"})
    public void bookCommentAdd(@ShellOption(defaultValue = "1") String stringId) {
        long id = Long.valueOf(stringId);
        bookCommentCrudService.add(id);
    }

    @ShellMethod(value = "Book comment delete", key = {"bcd", "bookCommentDelete"})
    public void bookCommentDelete(@ShellOption(defaultValue = "1") String stringId) {
        long id = Long.valueOf(stringId);
        bookCommentCrudService.deleteById(id);
    }

    @ShellMethod(value = "Book comment modify", key = {"bcm", "bookCommentModify"})
    public void bookCommentModify(@ShellOption(defaultValue = "1") String stringId) {
        long id = Long.valueOf(stringId);
        bookCommentCrudService.modifyCommentById(id);
    }

}
