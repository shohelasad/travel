

CREATE TABLE public.booking (
    id bigint NOT NULL,
    booking_id character varying(255),
    total_price double precision NOT NULL,
    trail_id bigint NOT NULL
);


CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



CREATE TABLE public.hiker (
    id bigint NOT NULL,
    age integer NOT NULL,
    email character varying(255),
    name character varying(255),
    booking_id bigint
);



CREATE TABLE public.trail (
    id bigint NOT NULL,
    end_at character varying(255),
    maximum_age integer NOT NULL,
    minimum_age integer NOT NULL,
    name character varying(255) NOT NULL,
    start_at character varying(255),
    unit_price double precision NOT NULL
);



