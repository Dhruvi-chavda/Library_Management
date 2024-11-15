package com.library.management.modules.book.service;

import com.library.management.modules.book.transfer.BookTransfer;
import com.library.management.modules.book.transfer.BookUpdateRequest;

import java.util.List;

public interface BookService {

    void saveBookDetail(BookTransfer bookTransfer);

    BookTransfer getBookDetail(Long id);

    List<BookTransfer> getAllBookDetail();

    void updateBookDetails(BookUpdateRequest updateRequest);

    void deleteBookDetails(Long id);
}
