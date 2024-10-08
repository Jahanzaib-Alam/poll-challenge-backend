DROP DATABASE IF EXISTS `poll_challenge`;

CREATE DATABASE `poll_challenge` DEFAULT CHARACTER SET utf8mb4;

DROP USER IF EXISTS `poll_user`;
CREATE USER 'poll_user'@'%' IDENTIFIED BY 'fl@tAlarm94';
GRANT Insert ON poll_challenge.* TO 'poll_user'@'%';
GRANT Select ON poll_challenge.* TO 'poll_user'@'%';
GRANT Update ON poll_challenge.* TO 'poll_user'@'%';

CREATE TABLE poll_challenge.poll (
	id INT(11) auto_increment NOT NULL,
	name varchar(255) NOT NULL,
	question_text varchar(255) NOT NULL,
	is_active BIT(1) DEFAULT b'0' NOT NULL,
	CONSTRAINT pk_poll_id PRIMARY KEY (id),
	CONSTRAINT uq_poll_name UNIQUE KEY (name)
);

CREATE TABLE poll_challenge.poll_option (
	id int(11) auto_increment NOT NULL,
	poll_id int(11) NOT NULL,
	option_text varchar(100) NOT NULL,
	CONSTRAINT pk_poll_option_id PRIMARY KEY (id),
	CONSTRAINT fk_poll_option_poll_poll_id FOREIGN KEY (poll_id) REFERENCES poll_challenge.poll(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE poll_challenge.poll_vote (
	id int(11) auto_increment NOT NULL,
	option_id int(11) NOT NULL,
	placed_date_time DATETIME NOT NULL,
	CONSTRAINT pk_poll_vote_id PRIMARY KEY (id),
	CONSTRAINT fk_poll_vote_poll_option_option_id FOREIGN KEY (option_id) REFERENCES poll_challenge.poll_option(id) ON DELETE CASCADE ON UPDATE CASCADE
);

/* Insert poll */
INSERT INTO poll_challenge.poll (name,question_text,is_active) VALUES
	 ('Premier League Poll','Who will win the Premier League?',1);

/* Insert options */
INSERT INTO poll_challenge.poll_option (poll_id,option_text) VALUES
	 (1,'Manchester City'),
	 (1,'Arsenal'),
	 (1,'Liverpool');
	 
/* Insert votes */
INSERT INTO poll_challenge.poll_vote (option_id,placed_date_time) VALUES
	 (1,'2024-10-08 08:17:54'),
	 (1,'2024-10-08 08:17:57'),
	 (1,'2024-10-08 08:21:11'),
	 (1,'2024-10-08 08:21:18'),
	 (1,'2024-10-08 08:21:24'),
	 (1,'2024-10-08 08:21:25'),
	 (1,'2024-10-08 08:21:26'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:28'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27'),
	 (1,'2024-10-08 08:21:27');
INSERT INTO poll_challenge.poll_vote (option_id,placed_date_time) VALUES
	 (2,'2024-10-08 08:21:28'),
	 (2,'2024-10-08 08:21:28'),
	 (2,'2024-10-08 08:21:28'),
	 (2,'2024-10-08 08:21:28'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29'),
	 (2,'2024-10-08 08:21:29');
INSERT INTO poll_challenge.poll_vote (option_id,placed_date_time) VALUES
	 (3,'2024-10-08 08:21:45'),
	 (3,'2024-10-08 08:21:45'),
	 (3,'2024-10-08 08:21:45'),
	 (3,'2024-10-08 08:21:45'),
	 (3,'2024-10-08 08:21:45');