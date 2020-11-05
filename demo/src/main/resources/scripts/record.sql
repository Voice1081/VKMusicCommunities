CREATE TABLE public.record
(
    id uuid NOT NULL,
    date timestamp with time zone,
    likes integer,
    link text COLLATE pg_catalog."default",
    CONSTRAINT record_pkey PRIMARY KEY (id)
)