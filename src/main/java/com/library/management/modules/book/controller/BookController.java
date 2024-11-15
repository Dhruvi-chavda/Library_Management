package com.library.management.modules.book.controller;


import com.library.management.model.RestResponse;
import com.library.management.modules.book.service.BookService;
import com.library.management.modules.book.transfer.BookTransfer;
import com.library.management.modules.book.transfer.BookUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Save Book Details")
    @PostMapping()
    RestResponse createBookDetails(@RequestBody BookTransfer bookTransfer){
        bookService.saveBookDetail(bookTransfer);
        return new RestResponse(true, "Book Detail Added SuccessFully");
    }

    @Operation(summary = "Update Book Detail")
    @PutMapping()
    RestResponse updateBookDetails(@RequestBody BookUpdateRequest bookUpdateRequest){
        bookService.updateBookDetails(bookUpdateRequest);
        return new RestResponse(true, "Updated Book Detail SuccessFully");
    }

    @Operation(summary = "Retrieve Book Detail")
    @GetMapping("/{id}")
    RestResponse updateBookDetails(@PathVariable("id") Long id){
        return new RestResponse(true, bookService.getBookDetail(id));
    }

    @Operation(summary = "Retrieve All Book Detail")
    @GetMapping()
    RestResponse getAllBookDetails(){
        return new RestResponse(true, bookService.getAllBookDetail());
    }

    @Operation(summary = "Delete Book Detail")
    @DeleteMapping("/{id}")
    RestResponse deleteBookDetails(@PathVariable("id") Long id){
        bookService.deleteBookDetails(id);
        return new RestResponse(true, "Deleted Book Detail SuccessFully");
    }

}
