package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.configs.YamlProps;
import ru.otus.library.models.Book;
import ru.otus.library.models.BookComment;
import ru.otus.library.models.BookCommentWithBook;
import ru.otus.library.models.BookWithComments;
import ru.otus.library.repositories.BookCommentRepository;
import ru.otus.library.repositories.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCommentCrudServiceConsole implements BookCommentCrudService {
    private final YamlProps props;
    private final MessageSource messageSource;
    private final IOService ioService;
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;

    @Override
    public void listAllByBookId(long bookId) {
        BookWithComments bookWithComments = bookRepository.getBookWithCommentsById(bookId);
        List<BookComment> bookComments = bookWithComments.getBookComment();
        String outLine;
        if (bookComments.size() > 0) {
            outLine = messageSource.getMessage("book.with.id", new String[]{String.valueOf(bookWithComments.getId())}, props.getLocale());
            ioService.out(outLine);
            outLine = messageSource.getMessage("book.title", new String[]{bookWithComments.getTitle()}, props.getLocale());
            ioService.out(outLine);
            for (int i = 0; i < bookComments.size(); i++) {
                BookComment bookComment = bookComments.get(i);
                outLine = messageSource.getMessage("book.comment.data", new String[]{String.valueOf(bookComment.getId()), bookComment.getComment()}, props.getLocale());
                ioService.out(outLine);
            }
        } else {
            outLine = messageSource.getMessage("there.are.no.comments.for.this.book", new String[]{}, props.getLocale());
            ioService.out(outLine);
        }
    }

    @Transactional
    public void add(long book_id) {
        String requestText;
        Book book = bookRepository.getById(book_id);
        if (book != null) {
            requestText = messageSource.getMessage("enter.book.comment", new String[]{}, props.getLocale());
            ioService.out(requestText);
            String comment = ioService.readString();
            BookCommentWithBook bookCommentWithBook = new BookCommentWithBook(0, comment, book);
            bookCommentRepository.save(bookCommentWithBook);
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        BookCommentWithBook deletingBookCommentWithBook = bookCommentRepository.getById(id);
        bookCommentRepository.delete(deletingBookCommentWithBook);
    }

    @Override
    @Transactional
    public void modifyCommentById(long id) {
        String requestText;
        BookCommentWithBook bookCommentWithBook = bookCommentRepository.getById(id);
        if (bookCommentWithBook != null) {
            requestText = messageSource.getMessage("enter.new.book.comment", new String[]{}, props.getLocale());
            ioService.out(requestText);
            String comment = ioService.readString();
            if (!comment.isEmpty()) {
                bookCommentWithBook.setComment(comment);
            }
            bookCommentRepository.update(bookCommentWithBook);
        }
    }
}
