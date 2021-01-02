package ru.otus.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dto.BookDto;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookCommentRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookCommentRepository bookCommentRepository;
    private final MutableAclService mutableAclService;

    private static BookDto bookToDto(Book book) {
        return new BookDto(
                String.valueOf(book.getId()),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getGenre().getName()
        );
    }

    @Override
    public List<BookDto> findAll() {
        Iterable<Book> books = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        books.forEach(book -> bookDtoList.add(bookToDto(book)));
        return bookDtoList;
    }

    @Override
    public BookDto findBookById(String id) {
        Book book = bookRepository.getById(Long.valueOf(id));
        BookDto bookDto;
        if (book != null) {
            bookDto = bookToDto(book);
        } else {
            bookDto = new BookDto(
                    "",
                    "",
                    "",
                    ""
            );
        }
        return bookDto;
    }

    @Override
    @Transactional
    public void deleteBookById(String id) {
        Long idLong = Long.valueOf(id);
        Book book = bookRepository.getById(idLong);
        if (book != null) {
            // проверяем права на удаление объекта
            final ObjectIdentity oid = new ObjectIdentityImpl(Book.class, book.getId());
            // прочитать ACL бизнес сущности
            Acl acl = mutableAclService.readAclById(oid);
            // определить какие права и для кого проверять
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final Sid sid = new PrincipalSid(authentication);
            List<Sid> sids = new ArrayList<>();
            sids.add(sid);
            authentication.getAuthorities().forEach(grantedAuthority -> sids.add(new GrantedAuthoritySid(grantedAuthority)));
            final List<Permission> permissions = Arrays.asList(BasePermission.DELETE, BasePermission.ADMINISTRATION);
            if (acl.isGranted(permissions, sids, false)) {
                bookCommentRepository.deleteAllByBook(book);
                bookRepository.deleteById(idLong);
            }
        }
    }

    @Override
    @Transactional
    public void add(BookDto bookDto) {
        Author author;
        Author existingAuthor;
        existingAuthor = authorRepository.findByName(bookDto.getAuthorName());
        if (existingAuthor != null) {
            author = existingAuthor;
        } else {
            Author authorNew = new Author(0, bookDto.getAuthorName());
            author = authorRepository.save(authorNew);
        }

        Genre genre;
        Genre existingGenre;
        existingGenre = genreRepository.findByName(bookDto.getGenreName());
        if (existingGenre != null) {
            genre = existingGenre;
        } else {
            Genre genreNew = new Genre(0, bookDto.getGenreName());
            genre = genreRepository.save(genreNew);
        }

        Book book = new Book(0, bookDto.getBookTitle(), author, genre, null);
        bookRepository.save(book);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(book.getClass(), book.getId());
        final Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");
        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, owner, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, owner, true);
        mutableAclService.updateAcl(acl);
    }

    @Override
    @Transactional
    public void modifyBook(BookDto bookDto) {
        Long idLong = Long.valueOf(bookDto.getId());
        Book book = bookRepository.getById(idLong);
        if (book != null) {
            // проверяем права на модификацию объекта
            final ObjectIdentity oid = new ObjectIdentityImpl(Book.class, book.getId());
            // прочитать ACL бизнес сущности
            Acl acl = mutableAclService.readAclById(oid);
            // определить какие права и для кого проверять
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final Sid sid = new PrincipalSid(authentication);
            List<Sid> sids = new ArrayList<>();
            sids.add(sid);
            authentication.getAuthorities().forEach(grantedAuthority -> sids.add(new GrantedAuthoritySid(grantedAuthority)));
            final List<Permission> permissions = Arrays.asList(BasePermission.WRITE, BasePermission.ADMINISTRATION);
            // выполнить проверку
            if (acl.isGranted(permissions, sids, false)) {
                Author author;
                Author existingAuthor;
                existingAuthor = authorRepository.findByName(bookDto.getAuthorName());
                if (existingAuthor != null) {
                    author = existingAuthor;
                } else {
                    Author authorNew = new Author(0, bookDto.getAuthorName());
                    author = authorRepository.save(authorNew);
                }

                Genre genre;
                Genre existingGenre;
                existingGenre = genreRepository.findByName(bookDto.getGenreName());
                if (existingGenre != null) {
                    genre = existingGenre;
                } else {
                    Genre genreNew = new Genre(0, bookDto.getGenreName());
                    genre = genreRepository.save(genreNew);
                }

                Book bookForUpdate = new Book(idLong, bookDto.getBookTitle(), author, genre, book.getBookComment());
                bookRepository.save(bookForUpdate);
            }
        }
    }
}
