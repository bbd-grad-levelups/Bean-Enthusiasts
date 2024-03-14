-- UserRole Table
INSERT INTO user_role (role_name)
VALUES
('Admin'),
('User');

-- FavoriteBean Table
INSERT INTO favorite_bean (bean_name, is_banned)
VALUES
('Appaloosa bean', false),
('Asparagus bean', false),
('Blood', false),
('Black turtle bean', false),
('Black-eyed pea', false),
('Broad bean', false),
('Calypso bean', false),
('Carob', false),
('Chickpea', false),
('Dixie Lee pea', false),
('Dragon tongue bean', false),
('Fenugreek', false),
('Flageolet bean', false),
('Flat bean', false),
('Genetically modified soybean', false),
('Glycine max', false),
('Green bean', false),
('Honey locust', false),
('Kidney bean', false),
('Le Puy green lentil', false),
('Lentil', false),
('Lima bean', false),
('Lupin bean', false),
('Mesquite', false),
('Mung bean', false),
('Peanut', false),
('Snap pea', false),
('Snow pea', false),
('Soybean', false),
('Split pea', false),
('Tamarind', false),
('Vigna mungo', false),
('Winged bean', false);

-- Tag Table
INSERT INTO tag (tag_name)
VALUES
('Cooking'),
('Sport'),
('DIY'),
('Humor');

-- ReactionType Table
INSERT INTO reaction_type (reaction_name)
VALUES
('Like'),
('Dislike');


-- User Table
INSERT INTO users (username, bio, user_role_id, favorite_bean_id)
VALUES
('BeanLover42', 'Coffee addict, bean enthusiast, and amateur barista. Let''s talk beans!', (SELECT user_role_id FROM user_role WHERE role_name = 'Admin'), (SELECT favorite_bean_id FROM favorite_bean WHERE bean_name = 'Dragon tongue bean')),
('CoffeeConnoisseur23', 'Exploring the world one coffee bean at a time. Join me in my bean journey!', (SELECT user_role_id FROM user_role WHERE role_name = 'User'), (SELECT favorite_bean_id FROM favorite_bean WHERE bean_name = 'Black turtle bean')),
('EspressoEnthusiast', 'Passionate abouts espresso and all things bean-related. Coffee is life!', (SELECT user_role_id FROM user_role WHERE role_name = 'Admin'), (SELECT favorite_bean_id FROM favorite_bean WHERE bean_name = 'Blood'));


-- Post Table
INSERT INTO post (user_id, tag_id, post_title, post_content, date_posted)
VALUES
((SELECT user_id FROM users WHERE username = 'BeanLover42'), (SELECT tag_id FROM tag WHERE tag_name = 'Cooking'), 'The Magic of Arabica Beans', 'Exploring the rich flavor profile and history of Arabica beans. Did you know they originated in Ethiopia?', NOW()),
((SELECT user_id FROM users WHERE username = 'CoffeeConnoisseur23'), (SELECT tag_id FROM tag WHERE tag_name = 'Sport'), 'A Journey Through Colombian Coffee Farms', 'Sharing my recent adventure through Colombian coffee farms. The sights, the smells, and of course, the coffee!', NOW()),
((SELECT user_id FROM users WHERE username = 'EspressoEnthusiast'), (SELECT tag_id FROM tag WHERE tag_name = 'DIY'), 'The Art of Espresso Making', 'Mastering the art of pulling the perfect shot of espresso. Tips, tricks, and recommendations for espresso lovers!', NOW());


-- Comment Table
INSERT INTO comment (post_id, user_id, comment_info, date_posted)
VALUES
((SELECT post_id FROM post WHERE post_title = 'The Magic of Arabica Beans'), (SELECT user_id FROM users WHERE username = 'CoffeeConnoisseur23'), 'Arabica beans are my favorite too! Have you tried the Ethiopian Yirgacheffe variety?', NOW()),
((SELECT post_id FROM post WHERE post_title = 'A Journey Through Colombian Coffee Farms'), (SELECT user_id FROM users WHERE username = 'BeanLover42'), 'Your journey sounds amazing! I''ve always wanted to visit Colombia. Which region did you explore?', NOW()),
((SELECT post_id FROM post WHERE post_title = 'The Art of Espresso Making'), (SELECT user_id FROM users WHERE username = 'EspressoEnthusiast'), 'Espresso-making truly is an art! What''s your go-to espresso machine?', NOW());




