package ru.otus.library.page.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.dto.BookDto;

@Controller
public class BookPageController {

    @GetMapping("/book")
    public String bookListPage(Model model) {
        return "bookList";
    }

    @GetMapping("/book/edit")
    public String editBookGetPage(@RequestParam("id") String id, Model model) {
        return "bookEdit";
    }

    @GetMapping("/book/delete")
    public String deleteBookGetPage(@RequestParam("id") String id, Model model) {
        return "bookDelete";
    }

    @GetMapping("/book/add")
    public String addBookGetPage(Model model) {
        return "bookAdd";
    }
}
