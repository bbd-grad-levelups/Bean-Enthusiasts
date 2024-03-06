# Bean-Enthusiasts
BBD Graduate Program JavaLevel-up (2024)



# Prototype instructions
Some instructions for running the basic prototype set up for connectivity between the three main elements of the system.

### Database

The prototype expects a postgres instance running on localhost. Specifically, a database called _javadat_, running on localhost:5432. It uses a username _postgres_ and password _root_. The database has a single table, created and filled as follows:

```
CREATE TABLE greeting (
    id SERIAL PRIMARY KEY,
    greeting VARCHAR(255),
    random_val INT
);

INSERT INTO greeting (greeting, random_val)
VALUES 
  ('Hello', 42),
  ('Hi', 55),
  ('Hey', 68);
```

Do note, there seems to be additional complications when running a database from windows and trying to connect from WSL and vice-versa, so keep everything in one place if possible.

### Server/Client

Ideally, both of these should be able to run with a single maven command from their directory (/Server or /Client).

```
mvn spring-boot:run
```

If you installed everything required for the work required in the Level-Up's courses, I believe this should run without additional setup.
