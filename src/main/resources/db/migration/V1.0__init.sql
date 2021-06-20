CREATE TABLE product
(
    id      SERIAL PRIMARY KEY,
    created TIMESTAMP(6)   NOT NULL,
    updated TIMESTAMP(6),
    version BIGINT         NOT NULL,
    name    VARCHAR(255)   NOT NULL UNIQUE,
    price   NUMERIC(32, 2) NOT NULL
);


CREATE TABLE orders
(
    id      SERIAL PRIMARY KEY,
    created TIMESTAMP(6)   NOT NULL,
    updated TIMESTAMP(6),
    version BIGINT         NOT NULL,
    qty     INTEGER        NOT NULL,
    total   NUMERIC(32, 2) NOT NULL
);

CREATE TABLE orderline
(
    id         SERIAL PRIMARY KEY,
    created    TIMESTAMP(6)   NOT NULL,
    updated    TIMESTAMP(6),
    version    BIGINT         NOT NULL,
    order_id   BIGINT         NOT NULL,
    product_id BIGINT         NOT NULL,
    qty        INTEGER        NOT NULL,
    price      NUMERIC(32, 2) NOT NULL,
    constraint order_line_order_id_fk foreign key (order_id) references orders (id),
    constraint order_line_product_id_fk foreign key (product_id) references product (id)
);