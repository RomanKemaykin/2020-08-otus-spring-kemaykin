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

@RequiredArgsConstructor
@Service
public class BookCommentCrudServiceImpl implements BookCommentCrudService {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;

    @Override
    public List<BookCommentDto> findAllByBookId(long bookId) {
        Book bookWithComments = bookRepository.findById(bookId);
        List<BookComment> bookComments = bookWithComments.getBookComment();
        List<BookCommentDto> bookCommentDtoList = new ArrayList<>();
        for (int i = 0; i < bookComments.size(); i++) {
            BookComment bookComment = bookComments.get(i);
            BookCommentDto bookCommentDto = new BookCommentDto(
                    bookComment.getId(),
                    bookComment.getBook().getId(),
                    bookComment.getBook().getTitle(),
                    bookComment.getComment());
            bookCommentDtoList.add(bookCommentDto);
        }
        return bookCommentDtoList;
    }

    @Override
    public void add(BookCommentDto bookCommentDto) {
        Book book = bookRepository.findById(bookCommentDto.getBookId());
        if (book != null) {
            BookComment bookComment = new BookComment(0, bookCommentDto.getBookComment(), book);
            bookCommentRepository.save(bookComment);
        }
    }

    @Override
    public BookCommentDto findById(long id) {
        BookComment bookComment = bookCommentRepository.getById(id);
        BookCommentDto bookCommentDto;
        if (bookComment != null) {
            bookCommentDto = new BookCommentDto(
                    bookComment.getId(),
                    bookComment.getBook().getId(),
                    bookComment.getBook().getTitle(),
                    bookComment.getComment());
        } else {
            bookCommentDto = new BookCommentDto(0, 0, null, null);
        }
        return bookCommentDto;
    }

    @Override
    public void deleteById(long id) {
        bookCommentRepository.deleteById(id);
    }

    @Override
    public void modifyComment(BookCommentDto bookCommentDto) {
        BookComment bookComment = bookCommentRepository.getById(bookCommentDto.getId());
        bookComment.setComment(bookCommentDto.getBookComment());
        bookCommentRepository.save(bookComment);
    }
}
