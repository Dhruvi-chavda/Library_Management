package com.library.management.modules.book.transfer;


import com.library.management.modules.book.domain.Books;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTransfer {

    private String bookName;
    private String bookAuthor;
    private Integer publicationYear;

    public BookTransfer(Books book){
        this.bookName = book.getTitle();
        this.bookAuthor = book.getAuthor();
        this.publicationYear = book.getPublicationYear();
    }
}
