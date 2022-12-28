-- starts here
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(32) NOT NULL,
    is_admin  BOOLEAN NOT NULL DEFAULT FALSE,
    password VARCHAR(64) NOT NULL  
);

CREATE TABLE course (
    id SERIAL PRIMARY KEY,
    title VARCHAR(64) NOT NULL,
    is_queue_open BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE administrator (
    userid INT NOT NULL,
    course_id INT NOT NULL,
    FOREIGN KEY (userid)
        REFERENCES users (id),
    FOREIGN KEY (course_id)
        REFERENCES course (id)
);

CREATE TABLE queue_item (
    id SERIAL PRIMARY KEY,
    userid INT NOT NULL,
    course_id INT NOT NULL,
    place VARCHAR(128) NOT NULL,
    comment VARCHAR(128) NOT NULL,
    FOREIGN KEY (userid)
        REFERENCES users (id),
    FOREIGN KEY (course_id) 
        REFERENCES course (id)
);

CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    from_id INT NOT NULL,
    to_id INT NOT NULL,
    timeof TIMESTAMP NOT NULL,
    content VARCHAR(128) NOT NULL,
    FOREIGN KEY (from_id)
        REFERENCES users (id),
    FOREIGN KEY (to_id)
        REFERENCES users (id)
);