
-- UserRole Table
INSERT INTO user_role (role_name)
VALUES
('Admin'),
('User');

-- FavoriteBean Table
INSERT INTO favorite_bean (bean_name, is_banned)
VALUES
('Bean1', false),
('Bean2', false),
('Bean3', true);

-- Tag Table
INSERT INTO tag (tag_name)
VALUES
('Tag1'),
('Tag2'),
('Tag3');

-- ReactionType Table
INSERT INTO reaction_type (bean_name)
VALUES
('Like'),
('Dislike');


-- User Table
INSERT INTO users (username, bio, user_role_id, favorite_bean_id)
VALUES
('user1', 'Bio for user1', 1, 1),
('user2', 'Bio for user2', 2, 2),
('user3', 'Bio for user3', 1, 3);

-- Post Table
INSERT INTO post (user_id, tag_id, post_title, post_content, date_posted)
VALUES
(1, 1, 'Post 1 Title', 'Post 1 Content', NOW()),
(2, 2, 'Post 2 Title', 'Post 2 Content', NOW()),
(3, 3, 'Post 3 Title', 'Post 3 Content', NOW());



-- Comment Table
INSERT INTO comment (post_id, user_id, comment_info)
VALUES
(1, 2, 'Comment 1 on Post 1'),
(2, 1, 'Comment 1 on Post 2'),
(3, 3, 'Comment 1 on Post 3');

-- Reaction Table
INSERT INTO reaction (user_id, reaction_type_id, date_of_reaction)
VALUES
(1, 1, NOW()), -- Assuming reaction type 1 is 'Like'
(2, 2, NOW()), -- Assuming reaction type 2 is 'Dislike'
(3, 1, NOW());


-- PostReaction Table
INSERT INTO post_reaction (post_id, reaction_id)
VALUES
(1, 1),
(2, 2),
(3, 1);

-- CommentReaction Table
INSERT INTO comment_reaction (comment_id, reaction_id)
VALUES
(1, 2),
(2, 1),
(3, 2);
