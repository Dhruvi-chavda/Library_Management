package com.library.management.modules.book.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Author cannot be empty")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Publication year cannot be null")
    @Column(name = "publicationYear")
    private Integer publicationYear;

    @Column(name = "created_date")
    @CreationTimestamp
    protected LocalDateTime created;

    @Column(name = "updated_date")
    @UpdateTimestamp
    protected LocalDateTime updated;

}

