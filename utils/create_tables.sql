CREATE TABLE IF NOT EXISTS LogGuildMemberUpdateAvatar
(
    id             BIGINT   NOT NULL AUTO_INCREMENT,
    case_id        INT      NULL,
    dis_user_id    BIGINT   NOT NULL,
    username       TINYTEXT NOT NULL,
    timestamp      DATETIME NOT NULL,
    guild_id       BIGINT   NOT NULL,
    old_avatar_url TEXT     NULL,
    new_avatar_url TEXT     NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogUserUpdateName
(
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    case_id     INT      NULL,
    dis_user_id BIGINT   NOT NULL,
    username    TINYTEXT NOT NULL,
    timestamp   DATETIME NOT NULL,
    old_name    TINYTEXT NOT NULL,
    new_name    TINYTEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogGuildMemberJoin
(
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    case_id     INT      NULL,
    dis_user_id BIGINT   NOT NULL,
    username    TINYTEXT NOT NULL,
    timestamp   DATETIME NOT NULL,
    guild_id    BIGINT   NOT NULL,
    displayname TINYTEXT NOT NULL,
    user_count  INT      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogGuildMemberRemove
(
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    case_id     INT      NULL,
    dis_user_id BIGINT   NOT NULL,
    username    TINYTEXT NOT NULL,
    timestamp   DATETIME NOT NULL,
    guild_id    BIGINT   NOT NULL,
    displayname TINYTEXT NULL,
    user_count  INT      NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogGuildMemberUpdateNickname
(
    id           BIGINT   NOT NULL AUTO_INCREMENT,
    case_id      INT      NULL,
    dis_user_id  BIGINT   NOT NULL,
    username     TINYTEXT NOT NULL,
    timestamp    DATETIME NOT NULL,
    guild_id     BIGINT   NOT NULL,
    old_nickname TINYTEXT NULL,
    new_nickname TINYTEXT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogGuildVoiceGuildMute
(
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    case_id     INT      NULL,
    dis_user_id BIGINT   NOT NULL,
    username    TINYTEXT NOT NULL,
    timestamp   DATETIME NOT NULL,
    guild_id    BIGINT   NOT NULL,
    new_state   BOOLEAN  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogGuildVoiceGuildDeafen
(
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    case_id     INT      NULL,
    dis_user_id BIGINT   NOT NULL,
    username    TINYTEXT NOT NULL,
    timestamp   DATETIME NOT NULL,
    guild_id    BIGINT   NOT NULL,
    new_state   BOOLEAN  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogMessageDelete
(
    id                 BIGINT   NOT NULL AUTO_INCREMENT,
    case_id            INT      NULL,
    author_dis_user_id BIGINT   NULL,
    author_username    TINYTEXT NULL,
    timestamp          DATETIME NOT NULL,
    guild_id           BIGINT   NOT NULL,
    channel_id         BIGINT   NOT NULL,
    channel_name       TINYTEXT NOT NULL,
    message_id         BIGINT   NOT NULL,
    msg_truncated      TINYTEXT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogMessage
(
    id                    BIGINT   NOT NULL AUTO_INCREMENT,
    case_id               INT      NULL,
    dis_user_id           BIGINT   NOT NULL,
    username              TINYTEXT NOT NULL,
    received_timestamp    DATETIME NOT NULL,
    last_update_timestamp DATETIME NULL,
    guild_id              BIGINT   NOT NULL,
    channel_id            BIGINT   NOT NULL,
    channel_name          TINYTEXT NOT NULL,
    channel_type          TINYTEXT NOT NULL,
    message_id            BIGINT   NOT NULL,
    original_msg          TEXT     NOT NULL,
    latest_msg            TEXT     NOT NULL,
    msg_type              TINYTEXT NOT NULL,
    msg_jump_url          TEXT     NOT NULL,
    referenced_msg_id     BIGINT   NULL,
    attachments_summary   TEXT     NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogMessageUpdate
(
    id                  BIGINT   NOT NULL AUTO_INCREMENT,
    case_id             INT      NULL,
    dis_user_id         BIGINT   NOT NULL,
    username            TINYTEXT NOT NULL,
    timestamp           DATETIME NOT NULL,
    guild_id            BIGINT   NOT NULL,
    channel_id          BIGINT   NOT NULL,
    channel_name        TINYTEXT NOT NULL,
    message_id          BIGINT   NOT NULL,
    old_msg             TEXT     NULL,
    /*
    old_msg: nullable as Discord does not provide the old message, we try retrieve it from the LogMessage table
    if it hasn't been deleted yet.
    */
    new_msg             TEXT     NOT NULL,
    attachments_summary TEXT     NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogUserActivityStart
(
    id               BIGINT   NOT NULL AUTO_INCREMENT,
    case_id          INT      NULL,
    dis_user_id      BIGINT   NOT NULL,
    username         TINYTEXT NOT NULL,
    timestamp        DATETIME NOT NULL,
    type             TINYTEXT NOT NULL,
    name             TINYTEXT NOT NULL,
    url              TEXT     NULL,
    is_rich_presence BOOLEAN  NOT NULL,
    emoji_name       TINYTEXT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogUserActivityEnd
(
    id               BIGINT   NOT NULL AUTO_INCREMENT,
    case_id          INT      NULL,
    dis_user_id      BIGINT   NOT NULL,
    username         TINYTEXT NULL,
    timestamp        DATETIME NOT NULL,
    type             TINYTEXT NOT NULL,
    name             TINYTEXT NOT NULL,
    url              TEXT     NULL,
    is_rich_presence BOOLEAN  NOT NULL,
    emoji_name       TINYTEXT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS LogUserUpdateGlobalName
(
    id          BIGINT   NOT NULL AUTO_INCREMENT,
    case_id     INT      NULL,
    dis_user_id BIGINT   NOT NULL,
    username    TINYTEXT NOT NULL,
    timestamp   DATETIME NOT NULL,
    old_name    TINYTEXT NULL,
    new_name    TINYTEXT NULL,
    PRIMARY KEY (id)
);