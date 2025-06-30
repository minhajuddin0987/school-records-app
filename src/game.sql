
-- Drop existing tables/views
DROP TABLE IF EXISTS moves CASCADE;
DROP TABLE IF EXISTS games CASCADE;
DROP TABLE IF EXISTS players CASCADE;
DROP TABLE IF EXISTS match_statistics CASCADE ;

CREATE TABLE players (
                         player_id VARCHAR(50) PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         symbol CHAR(1) NOT NULL CHECK (symbol IN ('X', 'O')),
                         total_games INTEGER DEFAULT 0,
                         games_won INTEGER DEFAULT 0,
                         games_loss INTEGER DEFAULT 0
);


-- Games table (only stores human player)
CREATE TABLE games (
                       game_id SERIAL PRIMARY KEY,
                       player_id VARCHAR(50) NOT NULL REFERENCES players(player_id), -- Only human player
                       start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       end_time TIMESTAMP,
                       winner BOOLEAN -- TRUE if human won, FALSE if AI won, NULL if not finished
);

-- Moves table (only stores human moves)
CREATE TABLE moves (
                       move_id SERIAL PRIMARY KEY,
                       game_id INTEGER NOT NULL REFERENCES games(game_id),
                       move_duration DOUBLE PRECISION,
                       move_number INTEGER NOT NULL
);



select * from games;
select * from moves;
select * from players;

ALTER TABLE moves RENAME COLUMN move_duration TO duration;
SELECT column_name
FROM information_schema.columns
WHERE table_name = 'moves';
