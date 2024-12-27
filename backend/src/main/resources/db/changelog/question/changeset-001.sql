-- er_bal.question definition

CREATE TABLE `question` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT,
                            `question_text` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                            `choice_a` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '질문 A',
                            `choice_b` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '질문 B',
                            `created_date` datetime NOT NULL,
                            `updated_date` datetime NOT NULL,
                            `choice_a_count` int unsigned NOT NULL DEFAULT '0' COMMENT '질문 A 선택 횟수',
                            `choice_b_count` int unsigned NOT NULL DEFAULT '0' COMMENT '질문 B 선택 횟수',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1728 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;