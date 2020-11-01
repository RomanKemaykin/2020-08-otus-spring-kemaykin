package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.configs.YamlProps;
import ru.otus.library.dto.BookCommentDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookCommentIOServiceConsole implements BookCommentIOService {
    private final YamlProps props;
    private final MessageSource messageSource;
    private final IOService ioService;
    private final BookCommentCrudService bookCommentCrudService;

    @Override
    @Transactional
    public void listAllByBookId(long bookId) {
        List<BookCommentDto> bookCommentDtoList = bookCommentCrudService.findAllByBookId(bookId);
        String outLine;
        if (bookCommentDtoList.size() > 0) {
            outLine = messageSource.getMessage("book.with.id", new String[]{String.valueOf(bookCommentDtoList.get(0).getBookId())}, props.getLocale());
            ioService.out(outLine);
            outLine = messageSource.getMessage("book.title", new String[]{bookCommentDtoList.get(0).getBookTitle()}, props.getLocale());
            ioService.out(outLine);
            for (int i = 0; i < bookCommentDtoList.size(); i++) {
                BookCommentDto bookCommentDto = bookCommentDtoList.get(i);
                outLine = messageSource.getMessage("book.comment.data", new String[]{String.valueOf(bookCommentDto.getId()), bookCommentDto.getBookComment()}, props.getLocale());
                ioService.out(outLine);
            }
        } else {
            outLine = messageSource.getMessage("there.are.no.comments.for.this.book", new String[]{}, props.getLocale());
            ioService.out(outLine);
        }
    }

    @Override
    public void add(long book_id) {
        String requestText;
        requestText = messageSource.getMessage("enter.book.comment", new String[]{}, props.getLocale());
        ioService.out(requestText);
        String comment = ioService.readString();
        BookCommentDto bookCommentDto = new BookCommentDto(0, book_id, "", comment);
        bookCommentCrudService.add(bookCommentDto);
    }

    @Override
    public void deleteById(long id) {
        bookCommentCrudService.deleteById(id);
    }

    @Override
    public void modifyCommentById(long id) {
        String requestText;
        BookCommentDto bookCommentDto = bookCommentCrudService.findById(id);
        if (bookCommentDto.getId() != 0) {
            requestText = messageSource.getMessage("enter.new.book.comment", new String[]{}, props.getLocale());
            ioService.out(requestText);
            String comment = ioService.readString();
            if (!comment.isEmpty()) {
                bookCommentDto.setBookComment(comment);
            }
            bookCommentCrudService.modifyComment(bookCommentDto);
        }
    }
}
