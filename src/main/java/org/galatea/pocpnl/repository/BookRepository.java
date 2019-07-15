package org.galatea.pocpnl.repository;

import java.util.Optional;
import org.galatea.pocpnl.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> getByBookId(String bookId);
}
