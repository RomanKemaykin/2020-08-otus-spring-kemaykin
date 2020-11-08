package ru.otus.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.service.BookCommentIOService;
import ru.otus.library.service.BookIOService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellCommands {

    private final BookIOService bookIOService;
    private final BookCommentIOService bookCommentIOService;

    // Book methods

    @ShellMethod(value = "Book add", key = {"ba", "bookAdd"})
    public void bookAdd() {
        bookIOService.add();
    }

    @ShellMethod(value = "Book list", key = {"bl", "bookList"})
    public void bookList() {
        bookIOService.listAll();
    }

    @ShellMethod(value = "Book show", key = {"bs", "bookShow"})
    public void bookShow(@ShellOption(defaultValue = "1") String stringId) {
        bookIOService.showBookById(stringId);
    }

    @ShellMethod(value = "Book delete", key = {"bd", "bookDelete"})
    public void bookDelete(@ShellOption(defaultValue = "1") String stringId) {
        bookIOService.deleteBookById(stringId);
    }

    @ShellMethod(value = "Book modify", key = {"bm", "bookModify"})
    public void bookModify(@ShellOption(defaultValue = "1") String stringId) {
        bookIOService.modifyBookById(stringId);
    }

    // Book comment methods

    @ShellMethod(value = "Book comments show", key = {"bcs", "bookCommentsShow"})
    public void bookCommentsShow(@ShellOption(defaultValue = "1") String stringId) {
        bookCommentIOService.listAllByBookId(stringId);
    }

    @ShellMethod(value = "Book comment add", key = {"bca", "bookCommentAdd"})
    public void bookCommentAdd(@ShellOption(defaultValue = "1") String stringId) {
        bookCommentIOService.add(stringId);
    }

    @ShellMethod(value = "Book comment delete", key = {"bcd", "bookCommentDelete"})
    public void bookCommentDelete(@ShellOption(defaultValue = "1") String stringId) {
        bookCommentIOService.deleteById(stringId);
    }

    @ShellMethod(value = "Book comment modify", key = {"bcm", "bookCommentModify"})
    public void bookCommentModify(@ShellOption(defaultValue = "1") String stringId) {
        bookCommentIOService.modifyCommentById(stringId);
    }

}
