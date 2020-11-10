package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.dto.BookCommentDto;
import ru.otus.library.models.Book;
import ru.otus.library.models.BookComment;
import ru.otus.library.repositories.BookCommentRepository;
import ru.otus.library.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookCommentCrudServiceImpl implements BookCommentCrudService {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;

    @Override
    public List<BookCommentDto> findAllByBookId(String bookId) {
        List<BookCommentDto> bookCommentDtoList = new ArrayList<>();
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isEmpty()) {
            Book book = optionalBook.get();
            List<BookComment> bookComments = bookCommentRepository.findAllByBook(book);
            System.out.println(bookComments);
            for (int i = 0; i < bookComments.size(); i++) {
                BookComment bookComment = bookComments.get(i);
                BookCommentDto bookCommentDto = new BookCommentDto(
                        bookComment.getId(),
                        bookComment.getBook().getId(),
                        bookComment.getBook().getTitle(),
                        bookComment.getComment());
                bookCommentDtoList.add(bookCommentDto);
            }
        }
        return bookCommentDtoList;
    }

    @Override
    public void add(BookCommentDto bookCommentDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookCommentDto.getBookId());
        if (!optionalBook.isEmpty()) {
            Book book = optionalBook.get();
            BookComment bookComment = new BookComment(bookCommentDto.getBookComment(), book);
            bookCommentRepository.save(bookComment);
        }
    }

    @Override
    public BookCommentDto findById(String id) {
        Optional<BookComment> optionalBookComment = bookCommentRepository.findById(id);
        BookCommentDto bookCommentDto;
        if (!optionalBookComment.isEmpty()) {
            BookComment bookComment = optionalBookComment.get();
            bookCommentDto = new BookCommentDto(
                    bookComment.getId(),
                    bookComment.getBook().getId(),
                    bookComment.getBook().getTitle(),
                    bookComment.getComment());
        } else {
            bookCommentDto = new BookCommentDto("", "", null, null);
        }
        return bookCommentDto;
    }

    @Override
    public void deleteById(String id) {
        bookCommentRepository.deleteById(id);
    }

    @Override
    public void modifyComment(BookCommentDto bookCommentDto) {
        Optional<BookComment> optionalBookComment = bookCommentRepository.findById(bookCommentDto.getId());
        if (!optionalBookComment.isEmpty()) {
            BookComment bookComment = optionalBookComment.get();
            bookComment.setComment(bookCommentDto.getBookComment());
            bookCommentRepository.save(bookComment);
        }
    }
}
