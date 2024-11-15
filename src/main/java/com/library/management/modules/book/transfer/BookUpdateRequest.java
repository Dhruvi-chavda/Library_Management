package com.library.management.modules.book.transfer;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateRequest {

    public Long id;
    private String title;
    private String author;
    private Integer publicationYear;
}
