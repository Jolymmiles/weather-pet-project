create table cities
(
    city_id  bigint not null primary key,
    cityname varchar(255),
    lat      real,
    lon      real
);

create table weather(
    weather_id   bigint not null primary key,
    date_of_weather                 date,
    city_id  bigint references cities,
    date_of_getting_data date,
    temperature          real,
    weather_condition    varchar(255),
    icon                 varchar(255)
);

CREATE  Sequence weather_sequence
start with 1
increment by 1;

CREATE  Sequence city_sequence
start with 1
increment by 1;

GRANT ALL ON TABLE public.weather TO "user";

GRANT ALL ON TABLE public.cities TO "user";

GRANT ALL ON Sequence to public.weather_sequence TO "user";

GRANT ALL ON Sequence to public.city_sequence TO "user";
