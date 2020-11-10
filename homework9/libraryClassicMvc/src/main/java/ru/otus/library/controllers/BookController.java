package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.dto.BookDto;
import ru.otus.library.service.BookCrudService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookCrudService bookCrudService;

    @GetMapping("/book")
    public String bookListPage(Model model) {
        List<BookDto> bookDtoList = bookCrudService.findAll();
        model.addAttribute("bookDtoList", bookDtoList);
        return "bookList";
    }

    @GetMapping("/book/edit")
    public String editBookGetPage(@RequestParam("id") String id, Model model) {
        BookDto bookDto = bookCrudService.findBookById(id);
        model.addAttribute("bookDto", bookDto);
        return "bookEdit";
    }

    @PostMapping("/book/edit")
    public String editBookPostPage(BookDto bookDto, Model model) {
        bookCrudService.modifyBook(bookDto);
        return "redirect:/book";
    }

    @GetMapping("/book/delete")
    public String deleteBookGetPage(@RequestParam("id") String id, Model model) {
        BookDto bookDto = bookCrudService.findBookById(id);
        model.addAttribute("bookDto", bookDto);
        return "bookDelete";
    }

    @PostMapping("/book/delete")
    public String deleteBookPostPage(BookDto bookDto, Model model) {
        bookCrudService.deleteBookById(bookDto.getId());
        return "redirect:/book";
    }

    @GetMapping("/book/add")
    public String addBookGetPage(Model model) {
        BookDto bookDto = new BookDto("", "", "", "");
        model.addAttribute("bookDto", bookDto);
        return "bookAdd";
    }

    @PostMapping("/book/add")
    public String addBookPostPage(BookDto bookDto, Model model) {
        bookCrudService.add(bookDto);
        return "redirect:/book";
    }

}
