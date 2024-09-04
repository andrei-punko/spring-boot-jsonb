
create table articles (
    id serial not null,
    title varchar(100) not null,
    summary varchar(255),
    ts timestamp,
    "text" varchar not null,
    author varchar not null,
    location JSONB,
    date_created timestamp not null,
    date_updated timestamp not null,
    primary key (id)
);

create sequence article_id_seq START WITH 1 INCREMENT BY 1;

create index article_title_idx on articles(title);
create index article_ts_idx on articles(ts);
create index article_date_created_idx on articles(date_created);

-- according to: https://www.2ndquadrant.com/en/blog/jsonb-type-performance-postgresql-9-4/
create index article_location_idx on articles using gin (location jsonb_path_ops);
