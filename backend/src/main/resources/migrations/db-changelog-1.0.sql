CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(50) UNIQUE NOT NULL,
    username VARCHAR(50)        NOT NULL
);


CREATE TYPE sex_type AS ENUM ('male', 'female');


CREATE TABLE body_type
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    level INT                NOT NULL
);


CREATE TABLE profession
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    level INT                NOT NULL
);


CREATE TABLE trait
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    level INT                NOT NULL
);


CREATE TABLE phobia
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    level INT                NOT NULL
);


CREATE TABLE health
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    level INT                NOT NULL
);


CREATE TABLE bag
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    level INT                NOT NULL
);


CREATE TABLE hobby
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    level INT                NOT NULL
);


CREATE TABLE equipment
(
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(50) UNIQUE NOT NULL,
    level INT                NOT NULL
);


CREATE TABLE bunker
(
    id        SERIAL PRIMARY KEY,
    square    INT NOT NULL CHECK ( square > 0 ),
    stay_days INT NOT NULL CHECK ( stay_days > 0 ),
    food_days INT NOT NULL CHECK ( food_days > 0 )
);


CREATE TABLE cataclysm
(
    id          SERIAL PRIMARY KEY,
    description VARCHAR(500) UNIQUE NOT NULL
);


CREATE TABLE room
(
    id           SERIAL PRIMARY KEY,
    bunker_id    INT NOT NULL REFERENCES bunker (id),
    cataclysm_id INT NOT NULL REFERENCES cataclysm (id)
);


CREATE TABLE heroes
(
    id            SERIAL PRIMARY KEY,
    user_id       INT         NOT NULL REFERENCES users (id),
    name          VARCHAR(50) NOT NULL,
    age           INT         NOT NULL CHECK (age > 0),
    sex           sex_type    NOT NULL,
    body_type_id  INT         NOT NULL REFERENCES body_type (id),
    health_id     INT         NOT NULL REFERENCES health (id),
    trait_id      INT         NOT NULL REFERENCES trait (id),
    hobby_id      INT         NOT NULL REFERENCES hobby (id),
    profession_id INT         NOT NULL REFERENCES profession (id),
    phobia_id     INT         NOT NULL REFERENCES phobia (id),
    equipment_id  INT         NOT NULL REFERENCES equipment (id),
    bag_id        INT         NOT NULL REFERENCES bag (id),
    notes         VARCHAR(255),
    is_active     BOOLEAN     NOT NULL DEFAULT TRUE
);


CREATE TABLE hero_in_room
(
    hero_id INT NOT NULL REFERENCES heroes (id),
    room_id      INT NOT NULL REFERENCES room (id),
    PRIMARY KEY (hero_id, room_id)
);


CREATE TABLE equipment_in_bunker
(
    equipment_id INT NOT NULL REFERENCES equipment (id),
    bunker_id    INT NOT NULL REFERENCES bunker (id),
    PRIMARY KEY (equipment_id, bunker_id)
);


CREATE TABLE votes
(
    id                  SERIAL PRIMARY KEY,
    room_id             INT NOT NULL REFERENCES room (id),
    hero_id        INT NOT NULL REFERENCES heroes (id),
    target_hero_id INT NOT NULL REFERENCES heroes (id),
    round_number        INT NOT NULL CHECK ( round_number > 0 ),
    CONSTRAINT unique_vote UNIQUE (room_id, hero_id, round_number),
    CONSTRAINT hero_ne_target CHECK (hero_id <> target_hero_id)
);
