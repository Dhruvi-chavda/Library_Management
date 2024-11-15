package com.library.management.modules.book.service.impl;

import com.library.management.exception.NotFoundException;
import com.library.management.modules.book.domain.Books;
import com.library.management.modules.book.repository.BookRepository;
import com.library.management.modules.book.service.BookService;
import com.library.management.modules.book.transfer.BookTransfer;
import com.library.management.modules.book.transfer.BookUpdateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void saveBookDetail(BookTransfer bookTransfer) {
        Books books = new Books();
        books.setTitle(bookTransfer.getBookName());
        books.setAuthor(bookTransfer.getBookAuthor());
        books.setPublicationYear(bookTransfer.getPublicationYear());
        books.setCreated(LocalDateTime.now());
        books.setUpdated(LocalDateTime.now());
        bookRepository.save(books);
    }

    @Override
    public BookTransfer getBookDetail(Long id){
       Books book = getBookById(id);
        return new BookTransfer(book);
    }

    private Books getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException(NotFoundException.NotFound.BOOK_NOT_FOUND));
    }

    @Override
    public List<BookTransfer> getAllBookDetail(){
        List<Books> booksList = bookRepository.findAll();
        return booksList.stream().map(BookTransfer::new).toList();
    }

    @Override
    public void updateBookDetails(BookUpdateRequest updateRequest){
        Books books = getBookById(updateRequest.getId());
        books.setTitle(updateRequest.getTitle());
        books.setAuthor(updateRequest.getAuthor());
        books.setPublicationYear(updateRequest.getPublicationYear());
        books.setUpdated(LocalDateTime.now());
        bookRepository.save(books);
    }

    @Override
    public void deleteBookDetails(Long id){
        Books books = getBookById(id);
        bookRepository.delete(books);
    }
}
