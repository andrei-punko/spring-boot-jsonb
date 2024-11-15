
insert into articles (id, title, summary, "text", author, location, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Игрок', 'Рассказ о страсти игромании', '', 'Федор Достоевский', '{ "country": "RU", "city": "Moscow" }', current_timestamp, current_timestamp);

insert into articles (id, title, summary, "text", author, location, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Преступление и наказание', 'Рассказ о убийце и его раскаянии', '', 'Федор Достоевский', '{ "country": "RU", "city": "Smolensk" }', current_timestamp, current_timestamp);

insert into articles (id, title, summary, "text", author, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Келейные письма', 'Сборник писем', '', 'Тихон Задонский', current_timestamp, current_timestamp);

insert into articles (id, title, summary, "text", author, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Ревизор', '', '', 'Николай Гоголь', current_timestamp, current_timestamp);

insert into articles (id, title, summary, "text", author, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Моя жизнь во Христе', 'Дневниковые записи Иоанна Кронштадтского', '', 'Иоанн Сергиев', current_timestamp, current_timestamp);

insert into articles (id, title, summary, "text", author, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Слова подвижнические', '', '', 'Исаак Сирский', current_timestamp, current_timestamp);

insert into articles (id, title, summary, "text", author, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Современная практика православного благочестия', 'Руководство к духовной жизни', '', 'Николай Пестов', current_timestamp, current_timestamp);

insert into articles (id, title, summary, "text", author, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Сила Божия и немощь человеческая', 'Жизнеописание игумена Феодосия', '', 'Сергей Нилус', current_timestamp, current_timestamp);

insert into articles (id, title, summary, "text", author, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Отечник', 'Цитаты Святых Отцов', '', 'Игнатий Брянчанинов', current_timestamp, current_timestamp);

insert into articles (id, title, summary, "text", author, date_created, date_updated)
values (nextval('ARTICLE_ID_SEQ'), 'Душеполезные поучения', 'Азбука духовной жизни', '', 'Авва Дорофей', current_timestamp, current_timestamp);
