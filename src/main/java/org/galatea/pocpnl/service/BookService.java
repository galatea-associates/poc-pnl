package org.galatea.pocpnl.service;

import java.util.Optional;
import org.galatea.pocpnl.domain.Book;
import org.galatea.pocpnl.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  public String getBookCurrency(String bookId) {
    Optional<Book> book = bookRepository.getByBookId(bookId);
    return book.get().getCurrency();
  }
}
