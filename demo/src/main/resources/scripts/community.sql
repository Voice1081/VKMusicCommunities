CREATE TABLE COMMUNITY
(
    community_id bigint PRIMARY KEY,
    name TEXT,
    domain text,
    genre text[],
    top_year UUID[],
    link TEXT,
    last_post_time bigint
)