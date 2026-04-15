-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 13, 2026 at 02:11 PM
-- Server version: 8.4.3
-- PHP Version: 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `polysio`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`Ken96`@`%` PROCEDURE `MigrerDonnees` ()   BEGIN
    -- 1. On ouvre la transaction
    START TRANSACTION;

    -- 2. Migration du prix
    UPDATE propriete p
    JOIN case_plateau c ON p.id_case_plateau = c.id_case_plateau
    SET p.prix = c.prix;

    -- 3. Nettoyage (Correction de la syntaxe ici)
    ALTER TABLE case_plateau 
    DROP COLUMN prix;

    -- 4. On valide
    COMMIT;
    
    SELECT 'Migration terminée avec succès' AS resultat;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `cartes`
--

CREATE TABLE `cartes` (
  `id_carte` int UNSIGNED NOT NULL,
  `type_carte` enum('Chance','Communauté') NOT NULL,
  `description` text NOT NULL,
  `effet` varchar(100) NOT NULL,
  `chemin_svg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `case_plateau`
--

CREATE TABLE `case_plateau` (
  `id_case_plateau` int UNSIGNED NOT NULL,
  `nom_case` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type_case` enum('Départ','Rue','Gare','Chance','Communauté','Taxe','Prison','Compagnie','Parc','Police') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `positionX` int NOT NULL,
  `positionY` int NOT NULL,
  `chemin_svg` varchar(255) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `case_plateau`
--

INSERT INTO `case_plateau` (`id_case_plateau`, `nom_case`, `type_case`, `positionX`, `positionY`, `chemin_svg`) VALUES
(0, 'Départ', 'Départ', 10, 10, '/asset/Plateau/Case/0.png'),
(1, 'Boulevard Web', 'Rue', 9, 10, '/asset/Plateau/Case/1.png'),
(2, 'Local Informatique', 'Communauté', 8, 10, '/asset/Plateau/Case/2.png'),
(3, 'Rue de l’HTML', 'Rue', 7, 10, '/asset/Plateau/Case/3.png'),
(4, 'URSSAF', 'Taxe', 6, 10, '/asset/Plateau/Case/4.png'),
(5, 'Cours Particulier D\'Algo', 'Gare', 5, 10, '/asset/Plateau/Case/5.png'),
(6, 'Rue de l’Algorithme', 'Rue', 4, 10, '/asset/Plateau/Case/6.png'),
(7, 'Chance', 'Chance', 3, 10, '/asset/Plateau/Case/7.png'),
(8, 'Rue de la Balise', 'Rue', 2, 10, '/asset/Plateau/Case/8.png'),
(9, 'Avenue du JavaScript', 'Rue', 1, 10, '/asset/Plateau/Case/9.png'),
(10, 'Visite Prison', 'Prison', 0, 10, '/asset/Plateau/Case/10.png'),
(11, 'Boulevard du Serveur', 'Rue', 0, 9, '/asset/Plateau/Case/11.png'),
(12, 'Compagnie du Réseau', 'Compagnie', 0, 8, '/asset/Plateau/Case/12.png'),
(13, 'Avenue du Code', 'Rue', 0, 7, '/asset/Plateau/Case/13.png'),
(14, 'Rue de Python', 'Rue', 0, 6, '/asset/Plateau/Case/14.png'),
(15, 'Cours Particulier D\'Info', 'Gare', 0, 5, '/asset/Plateau/Case/15.png'),
(16, 'Avenue de la Donnée', 'Rue', 0, 4, '/asset/Plateau/Case/16.png'),
(17, 'Local Informatique', 'Communauté', 0, 3, '/asset/Plateau/Case/17.png'),
(18, 'Boulevard JSON', 'Rue', 0, 2, '/asset/Plateau/Case/18.png'),
(19, 'Rue du Framework', 'Rue', 0, 1, '/asset/Plateau/Case/19.png'),
(20, 'Parc Gratuit', 'Parc', 0, 0, '/asset/Plateau/Case/20.png'),
(21, 'Avenue du Malware', 'Rue', 1, 0, '/asset/Plateau/Case/21.png'),
(22, 'Chance', 'Chance', 2, 0, '/asset/Plateau/Case/22.png'),
(23, 'Boulevard de la Cybersécurité', 'Rue', 3, 0, '/asset/Plateau/Case/23.png'),
(24, 'Avenue Mainframe', 'Rue', 4, 0, '/asset/Plateau/Case/24.png'),
(25, 'Cours Particulier De BDD', 'Gare', 5, 0, '/asset/Plateau/Case/25.png'),
(26, 'Rue du Saint-Réseau', 'Rue', 6, 0, '/asset/Plateau/Case/26.png'),
(27, 'Place du GRETA', 'Rue', 7, 0, '/asset/Plateau/Case/27.png'),
(28, 'Compagnie du Cloud', 'Compagnie', 8, 0, '/asset/Plateau/Case/28.png'),
(29, 'Rue de la Fibre', 'Rue', 9, 0, '/asset/Plateau/Case/29.png'),
(30, 'Départ En Stage', 'Police', 10, 0, '/asset/Plateau/Case/30.png'),
(31, 'Avenue de la BDD', 'Rue', 10, 1, '/asset/Plateau/Case/31.png'),
(32, 'Avenue du Firewall', 'Rue', 10, 2, '/asset/Plateau/Case/32.png'),
(33, 'Local Informatique', 'Communauté', 10, 3, '/asset/Plateau/Case/33.png'),
(34, 'Boulevard des C++', 'Rue', 10, 4, '/asset/Plateau/Case/34.png'),
(35, 'Cours Particulier De Math', 'Gare', 10, 5, '/asset/Plateau/Case/35.png'),
(36, 'Chance', 'Chance', 10, 6, '/asset/Plateau/Case/36.png'),
(37, 'Avenue de la Block Chain', 'Rue', 10, 7, '/asset/Plateau/Case/37.png'),
(38, 'Bug Fiscal', 'Taxe', 10, 8, '/asset/Plateau/Case/38.png'),
(39, 'Rue de la PHP', 'Rue', 10, 9, '/asset/Plateau/Case/39.png'),
(99, 'centre', 'Rue', 0, 0, '/asset/Plateau/Case/centre.png');

-- --------------------------------------------------------

--
-- Table structure for table `groupe_couleur`
--

CREATE TABLE `groupe_couleur` (
  `id_couleur` int UNSIGNED NOT NULL,
  `nom_couleur` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `groupe_couleur`
--

INSERT INTO `groupe_couleur` (`id_couleur`, `nom_couleur`) VALUES
(2, 'Bleu clair'),
(8, 'Bleu foncé'),
(6, 'Jaune'),
(1, 'Marron'),
(4, 'Orange'),
(3, 'Rose'),
(5, 'Rouge'),
(7, 'Vert');

-- --------------------------------------------------------

--
-- Table structure for table `participer`
--

CREATE TABLE `participer` (
  `id_utilisateur` int UNSIGNED NOT NULL,
  `id_partie` int UNSIGNED NOT NULL,
  `score` int NOT NULL,
  `pointDeCompetences` int NOT NULL,
  `position_plateau` int NOT NULL,
  `est_en_jeu` tinyint(1) NOT NULL,
  `enPrison` tinyint(1) DEFAULT '0',
  `toursEnPrison` int DEFAULT '0',
  `couleur` enum('Rouge','Vert','Bleu','Jaune') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `partie`
--

CREATE TABLE `partie` (
  `id_partie` int UNSIGNED NOT NULL,
  `date_creation` datetime NOT NULL,
  `date_fin` datetime DEFAULT NULL,
  `statut` enum('En cours','Terminée') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mode_jeu` enum('Classique','Custom') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nombre_tours` int NOT NULL,
  `id_createur` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `posseder`
--

CREATE TABLE `posseder` (
  `id_utilisateur` int UNSIGNED NOT NULL,
  `id_partie` int UNSIGNED NOT NULL,
  `id_propriete` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `propriete`
--

CREATE TABLE `propriete` (
  `id_propriete` int UNSIGNED NOT NULL,
  `batiment` int NOT NULL DEFAULT '0',
  `id_case_plateau` int UNSIGNED NOT NULL,
  `collection` int DEFAULT NULL,
  `chemin_svg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `loyer_nue` int DEFAULT NULL,
  `loyer_batiment` int DEFAULT NULL,
  `id_couleur` int UNSIGNED DEFAULT NULL,
  `prix` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `propriete`
--

INSERT INTO `propriete` (`id_propriete`, `batiment`, `id_case_plateau`, `collection`, `chemin_svg`, `loyer_nue`, `loyer_batiment`, `id_couleur`, `prix`) VALUES
(0, 0, 0, NULL, '/asset/Plateau/Case/0.png', NULL, NULL, NULL, NULL),
(1, 0, 1, NULL, '/asset/CarteRue/CarteRueBoulevard_Web.png', 25, 50, 1, 100),
(2, 0, 2, NULL, '/asset/Plateau/Case/2.png', NULL, NULL, NULL, NULL),
(3, 0, 3, NULL, '/asset/CarteRue/Rue_de_l’HTML.png', 50, 100, 1, 100),
(4, 0, 4, NULL, '/asset/Plateau/Case/4.png', NULL, NULL, NULL, 200),
(5, 0, 5, NULL, '/asset/CarteCoursParticulier/Cours_particulier_d’algo.png', 50, NULL, NULL, 300),
(6, 0, 6, NULL, '/asset/CarteRue/Rue_de_l’Algorithme.png', 75, 150, 2, 150),
(7, 0, 7, NULL, '/asset/Plateau/Case/7.png', NULL, NULL, NULL, NULL),
(8, 0, 8, NULL, '/asset/CarteRue/Rue_de_la_Balise.png', 75, 150, 2, 150),
(9, 0, 9, NULL, '/asset/CarteRue/Avenue_du_JavaScript.png', 100, 200, 2, 175),
(10, 0, 10, NULL, '/asset/Plateau/Case/10.png', NULL, NULL, NULL, NULL),
(11, 0, 11, NULL, '/asset/CarteRue/Boulevard_du_Serveur.png', 125, 250, 3, 200),
(12, 0, 12, NULL, '/asset/Plateau/Case/12.png', NULL, NULL, NULL, 250),
(13, 0, 13, NULL, '/asset/CarteRue/Avenue_du_Code.png', 125, 250, 3, 200),
(14, 0, 14, NULL, '/asset/CarteRue/Rue_de_Python.png', 150, 300, 3, 225),
(15, 0, 15, NULL, '/asset/CarteCoursParticulier/Cours_particulier_d’info.png', 50, NULL, NULL, 300),
(16, 0, 16, NULL, '/asset/CarteRue/Avenue_de_la_Donnée.png', 175, 350, 4, 250),
(18, 0, 18, NULL, '/asset/CarteRue/Boulevard_JSON.png', 175, 350, 4, 250),
(19, 0, 19, NULL, '/asset/CarteRue/Rue_du_Framework.png', 200, 400, 4, 275),
(20, 0, 20, NULL, '/asset/Plateau/Case/20.png', NULL, NULL, NULL, NULL),
(21, 0, 21, NULL, '/asset/CarteRue/Avenue_du_Malware.png', 225, 450, 5, 300),
(22, 0, 22, NULL, '/asset/Plateau/Case/22.png', NULL, NULL, NULL, NULL),
(23, 0, 23, NULL, '/asset/CarteRue/Boulevard_Cybersécurité.png', 225, 450, 5, 300),
(24, 0, 24, NULL, '/asset/CarteRue/Avenue_Mainframe.png', 250, 500, 5, 325),
(25, 0, 25, NULL, '/asset/CarteCoursParticulier/Cours_particulier_de_bdd.png', 50, NULL, NULL, 300),
(26, 0, 26, NULL, '\\asset\\CarteRue\\Rue_du_Saint-Réseau.png', 275, 550, 6, 350),
(27, 0, 27, NULL, '\\asset\\CarteRue\\Place_du_GRETA.png', 275, 550, 6, 350),
(28, 0, 28, NULL, '\\asset\\Plateau\\Case\\28.png', NULL, NULL, NULL, 250),
(29, 0, 29, NULL, '\\asset\\CarteRue\\Rue_de_la_Fibre.png', 300, 600, 6, 375),
(30, 0, 30, NULL, '\\asset\\Plateau\\Case\\30.png', NULL, NULL, NULL, NULL),
(31, 0, 31, NULL, '\\asset\\CarteRue\\Avenue_de _la_BDD.png', 325, 650, 7, 400),
(32, 0, 32, NULL, '\\asset\\CarteRue\\Avenue_du_Firewall.png', 325, 650, 7, 400),
(33, 0, 33, NULL, '\\asset\\Plateau\\Case\\33.png', NULL, NULL, NULL, NULL),
(34, 0, 34, NULL, '\\asset\\CarteRue\\Boulevard_C++.png', 350, 700, 7, 425),
(35, 0, 35, NULL, '\\asset\\CarteCoursParticulier\\Cours_particulier_de_maths.png', 50, NULL, NULL, 300),
(36, 0, 36, NULL, '\\asset\\Plateau\\Case\\36.png', NULL, NULL, NULL, NULL),
(37, 0, 37, NULL, '\\asset\\CarteRue\\Avenue_de_la_Block_Chain.png', 400, 800, 8, 450),
(38, 0, 38, NULL, '\\asset\\Plateau\\Case\\38.png', NULL, NULL, NULL, 100),
(39, 0, 39, NULL, '\\asset\\CarteRue\\Rue_de_la_PHP.png', 450, 900, 8, 500);

-- --------------------------------------------------------

--
-- Table structure for table `sauvegarde_partie`
--

CREATE TABLE `sauvegarde_partie` (
  `id_sauvegarde` int UNSIGNED NOT NULL,
  `date_sauvegarde` datetime NOT NULL,
  `id_partie` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_utilisateur` int UNSIGNED NOT NULL,
  `pseudo` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mot_de_passe` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` enum('Utilisateur','Admin') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'Utilisateur',
  `date_creation` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `pseudo`, `email`, `mot_de_passe`, `role`, `date_creation`) VALUES
(10, 'usernamtest', 'dsfgfdsg@dfgdfg', 'yUfEEOQgEnzG6HB9iOVDxyftexevFDwe7YKSSWFVPE4=', 'Admin', '2026-04-13 10:42:55');

-- --------------------------------------------------------

--
-- Table structure for table `utiliser`
--

CREATE TABLE `utiliser` (
  `id_partie` int UNSIGNED NOT NULL,
  `id_carte` int UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cartes`
--
ALTER TABLE `cartes`
  ADD PRIMARY KEY (`id_carte`);

--
-- Indexes for table `case_plateau`
--
ALTER TABLE `case_plateau`
  ADD PRIMARY KEY (`id_case_plateau`);

--
-- Indexes for table `groupe_couleur`
--
ALTER TABLE `groupe_couleur`
  ADD PRIMARY KEY (`id_couleur`),
  ADD UNIQUE KEY `nom_couleur` (`nom_couleur`);

--
-- Indexes for table `participer`
--
ALTER TABLE `participer`
  ADD PRIMARY KEY (`id_utilisateur`,`id_partie`),
  ADD KEY `fk_participer_id_partie` (`id_partie`);

--
-- Indexes for table `partie`
--
ALTER TABLE `partie`
  ADD PRIMARY KEY (`id_partie`),
  ADD KEY `fk_partie_id_createur` (`id_createur`) USING BTREE;

--
-- Indexes for table `posseder`
--
ALTER TABLE `posseder`
  ADD PRIMARY KEY (`id_utilisateur`,`id_partie`,`id_propriete`),
  ADD KEY `fk_posseder_id_propriete` (`id_propriete`),
  ADD KEY `fk_posseder_id_partie` (`id_partie`),
  ADD KEY `fk_posseder_id_utilisateur` (`id_utilisateur`) USING BTREE;

--
-- Indexes for table `propriete`
--
ALTER TABLE `propriete`
  ADD PRIMARY KEY (`id_propriete`),
  ADD UNIQUE KEY `id_case_plateau` (`id_case_plateau`),
  ADD KEY `fk_propriete_id_case_plateau` (`id_case_plateau`),
  ADD KEY `fk_propriete_id_couleur` (`id_couleur`);

--
-- Indexes for table `sauvegarde_partie`
--
ALTER TABLE `sauvegarde_partie`
  ADD PRIMARY KEY (`id_sauvegarde`),
  ADD KEY `fk_sauvegarde_partie_id_partie` (`id_partie`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `pseudo` (`pseudo`);

--
-- Indexes for table `utiliser`
--
ALTER TABLE `utiliser`
  ADD PRIMARY KEY (`id_partie`,`id_carte`),
  ADD KEY `fk_utiliser_id_carte` (`id_carte`),
  ADD KEY `fk_utiliser_id_partie` (`id_partie`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cartes`
--
ALTER TABLE `cartes`
  MODIFY `id_carte` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `case_plateau`
--
ALTER TABLE `case_plateau`
  MODIFY `id_case_plateau` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100;

--
-- AUTO_INCREMENT for table `groupe_couleur`
--
ALTER TABLE `groupe_couleur`
  MODIFY `id_couleur` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `partie`
--
ALTER TABLE `partie`
  MODIFY `id_partie` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `propriete`
--
ALTER TABLE `propriete`
  MODIFY `id_propriete` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `sauvegarde_partie`
--
ALTER TABLE `sauvegarde_partie`
  MODIFY `id_sauvegarde` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_utilisateur` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `participer`
--
ALTER TABLE `participer`
  ADD CONSTRAINT `fk_participer_id_partie` FOREIGN KEY (`id_partie`) REFERENCES `partie` (`id_partie`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_participer_id_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `partie`
--
ALTER TABLE `partie`
  ADD CONSTRAINT `fk_partie_id_createur` FOREIGN KEY (`id_createur`) REFERENCES `utilisateur` (`id_utilisateur`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `posseder`
--
ALTER TABLE `posseder`
  ADD CONSTRAINT `fk_posseder_id_partie` FOREIGN KEY (`id_partie`) REFERENCES `partie` (`id_partie`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_posseder_id_propriete` FOREIGN KEY (`id_propriete`) REFERENCES `propriete` (`id_propriete`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_posseder_id_utilisateur` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id_utilisateur`) ON DELETE CASCADE ON UPDATE RESTRICT;

--
-- Constraints for table `propriete`
--
ALTER TABLE `propriete`
  ADD CONSTRAINT `fk_propriete_id_case_plateau` FOREIGN KEY (`id_case_plateau`) REFERENCES `case_plateau` (`id_case_plateau`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_propriete_id_couleur` FOREIGN KEY (`id_couleur`) REFERENCES `groupe_couleur` (`id_couleur`) ON DELETE RESTRICT ON UPDATE CASCADE;

--
-- Constraints for table `sauvegarde_partie`
--
ALTER TABLE `sauvegarde_partie`
  ADD CONSTRAINT `fk_sauvegarde_partie_id_partie` FOREIGN KEY (`id_partie`) REFERENCES `partie` (`id_partie`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `utiliser`
--
ALTER TABLE `utiliser`
  ADD CONSTRAINT `fk_utiliser_id_carte` FOREIGN KEY (`id_carte`) REFERENCES `cartes` (`id_carte`) ON DELETE CASCADE ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_utiliser_id_partie` FOREIGN KEY (`id_partie`) REFERENCES `partie` (`id_partie`) ON DELETE CASCADE ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
