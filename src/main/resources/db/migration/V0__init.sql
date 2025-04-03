CREATE TABLE "order"
(
    id      serial primary key,
    name    varchar,
    version bigint default 0
);


CREATE TABLE milestone
(
    id         SERIAL PRIMARY KEY,
    index      INT,
    start_date DATE,
    end_date   DATE,
    order_id   INT references "order" (id),
    version    bigint default 0
);
