DROP TABLE IF EXISTS books;
CREATE TABLE books(
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    book_name VARCHAR(100) NOT NULL,
    book_stock INT NOT NULL,
    book_details VARCHAR(100)
);
INSERT INTO books(book_name, book_stock, book_details)
VALUES ('Java',5,'JavaBase'),('Php',10,'PhpBase'),('MySQL',4,'MySQL Advanced'),('Linux',6,'Linux Socket');