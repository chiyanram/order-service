alter table orderline
    add column total NUMERIC(32, 2) NOT NULL;

CREATE TABLE offer
(
    id         SERIAL PRIMARY KEY,
    created    TIMESTAMP(6) NOT NULL,
    updated    TIMESTAMP(6),
    version    BIGINT       NOT NULL,
    start_date TIMESTAMP(6) NOT NULL,
    end_date   TIMESTAMP(6) NOT NULL,
    product_id BIGINT       NOT NULL,
    type       SMALLINT     NOT NULL,
    config     JSON         NOT NULL,
    constraint offer_product_id_fk foreign key (product_id) references product (id)
);

INSERT INTO offer(created, updated, version, start_date, end_date, product_id, type, config)
VALUES (CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 0, CURRENT_TIMESTAMP(6) - INTERVAL '1 DAY',
        CURRENT_TIMESTAMP(6) + INTERVAL '1 DAY',
        (select id from product where name = 'Apple'), 1, '{}');

INSERT INTO offer(created, updated, version, start_date, end_date, product_id, type, config)
VALUES (CURRENT_TIMESTAMP(6), CURRENT_TIMESTAMP(6), 0, CURRENT_TIMESTAMP(6) - INTERVAL '1 DAY',
        CURRENT_TIMESTAMP(6) + INTERVAL '1 DAY',
        (select id from product where name = 'Orange'), 2, '
        {
          "multiSaving": {
            "buy": 3,
            "price": 2
          }
        }
        ');