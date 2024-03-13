
-- UserRole Table
INSERT INTO user_role (role_name)
VALUES
('Admin'),
('User');

-- FavoriteBean Table
INSERT INTO favorite_bean (bean_name, is_banned)
VALUES
('Acacia holosericea', false),
('Adzuki bean', false),
('Alb-Leisa', false),
('Apios americana', false),
('Appaloosa bean', false),
('Arachis hypogaea', false),
('Archidendron pauciflorum', false),
('Asparagus bean', false),
('Bean', false),
('Black adzuki bean', false),
('Black turtle bean', false),
('Black-eyed pea', false),
('Bolita bean', false),
('Broad bean', false),
('Cajanus cajan', false),
('Cajanus scarabaeoides', false),
('Calypso bean', false),
('Canavalia gladiata', false),
('Caragana arborescens', false),
('Carob', false),
('Catjang', false),
('Chickpea', false),
('Cicer', false),
('Cicer arietinum', false),
('Cowpea', false),
('Crotalaria longirostrata', false),
('Detarium microcarpum', false),
('Detarium senegalense', false),
('Dixie Lee pea', false),
('Dragon tongue bean', false),
('Erythrina edulis', false),
('Fabes de la Granja', false),
('Fava bean', false),
('Fenugreek', false),
('Flageolet bean', false),
('Flat bean', false),
('Genetically modified soybean', false),
('Glycine max', false),
('Green bean', false),
('Guar', false),
('Honey locust', false),
('Inga edulis', false),
('Inga feuilleei', false),
('Inocarpus fagifer', false),
('Kidney bean', false),
('Lablab', false),
('Lablab purpureus', false),
('Lathyrus aphaca', false),
('Lathyrus japonicus', false),
('Lathyrus sativus', false),
('Le Puy green lentil', false),
('Lentil', false),
('Lima bean', false),
('Lupin bean', false),
('Lupinus luteus', false),
('Macrotyloma geocarpum', false),
('Macrotyloma uniflorum', false),
('Mesquite', false),
('Mucuna pruriens', false),
('Mung bean', false),
('Neptunia oleracea', false),
('Parkia javanica', false),
('Parkia speciosa', false),
('Parkia timoriana', false),
('Parkinsonia florida', false),
('Parkinsonia microphylla', false),
('Peanut', false),
('Pediomelum cuspidatum', false),
('Pediomelum esculentum', false),
('Phaseolus acutifolius', false),
('Phaseolus coccineus', false),
('Phaseolus vulgaris', false),
('Pigeon pea', false),
('Pinquito bean', false),
('Pinto bean', false),
('Pithecellobium dulce', false),
('Pochas', false),
('Prosopis alba', false),
('Prosopis glandulosa', false),
('Prosopis nigra', false),
('Prosopis pubescens', false),
('Prosopis velutina', false),
('Sea Island red pea', false),
('Senna tora', false),
('Shucky beans', false),
('Snap pea', false),
('Snow pea', false),
('Sorana bean', false),
('Soybean', false),
('Split pea', false),
('Tamarind', false),
('Tarbais bean', false),
('Trigonella caerulea', false),
('Tylosema esculentum', false),
('Vicia faba', false),
('Vicia lens', false),
('Vigna aconitifolia', false),
('Vigna mungo', false),
('Vigna subterranea', false),
('Vigna umbellata', false),
('Wattleseed', false),
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
INSERT INTO comment (post_id, user_id, comment_info, date_posted)
VALUES
(1, 2, 'Comment 1 on Post 1', NOW()),
(2, 1, 'Comment 1 on Post 2', NOW()),
(3, 3, 'Comment 1 on Post 3', NOW()),

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
