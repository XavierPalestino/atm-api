CREATE TABLE IF NOT EXISTS billing(
    id INT NOT NULL AUTO_INCREMENT,
    bill INT NOT NULL,
    import_type DECIMAL (12,2) NULL DEFAULT '0.00',
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;