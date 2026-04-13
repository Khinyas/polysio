-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 11, 2026 at 06:28 AM
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
  `prix` int DEFAULT NULL,
  `loyer_nue` int DEFAULT NULL,
  `loyer_batiment` int DEFAULT NULL,
  `id_couleur` int UNSIGNED DEFAULT NULL,
  `chemin_svg` varchar(255) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `case_plateau`
--

INSERT INTO `case_plateau` (`id_case_plateau`, `nom_case`, `type_case`, `positionX`, `positionY`, `prix`, `loyer_nue`, `loyer_batiment`, `id_couleur`, `chemin_svg`) VALUES
(0, 'Départ', 'Départ', 10, 10, NULL, NULL, NULL, NULL, ''),
(1, 'Boulevard Web', 'Rue', 9, 10, 100, 25, 50, 1, ''),
(2, 'Communauté', 'Communauté', 8, 10, NULL, NULL, NULL, NULL, ''),
(3, 'Rue de l’HTML', 'Rue', 7, 10, 100, 50, 100, 1, ''),
(4, 'Taxe sur le Code', 'Taxe', 6, 10, 200, NULL, NULL, NULL, ''),
(5, 'Gare des Développeurs', 'Gare', 5, 10, 300, 50, NULL, NULL, ''),
(6, 'Rue de l’Algorithme', 'Rue', 4, 10, 150, 75, 150, 2, ''),
(7, 'Chance', 'Chance', 3, 10, NULL, NULL, NULL, NULL, ''),
(8, 'Rue de la Balise', 'Rue', 2, 10, 150, 75, 150, 2, ''),
(9, 'Avenue du JavaScript', 'Rue', 1, 10, 175, 100, 200, 2, ''),
(10, 'Visite Prison', 'Prison', 0, 10, NULL, NULL, NULL, NULL, ''),
(11, 'Boulevard du Serveur', 'Rue', 0, 9, 200, 125, 250, 3, ''),
(12, 'Compagnie du Réseau', 'Compagnie', 0, 8, 250, NULL, NULL, NULL, ''),
(13, 'Avenue du Code', 'Rue', 0, 7, 200, 125, 250, 3, ''),
(14, 'Rue de Python', 'Rue', 0, 6, 225, 150, 300, 3, ''),
(15, 'Gare des Logs', 'Gare', 0, 5, 300, 50, NULL, NULL, ''),
(16, 'Avenue de la Donnée', 'Rue', 0, 4, 250, 175, 350, 4, ''),
(17, 'Communauté', 'Communauté', 0, 3, NULL, NULL, NULL, NULL, ''),
(18, 'Boulevard JSON', 'Rue', 0, 2, 250, 175, 350, 4, ''),
(19, 'Rue du Framework', 'Rue', 0, 1, 275, 200, 400, 4, ''),
(20, 'Parc Gratuit', 'Parc', 0, 0, NULL, NULL, NULL, NULL, ''),
(21, 'Avenue du Malware', 'Rue', 1, 0, 300, 225, 450, 5, ''),
(22, 'Chance', 'Chance', 2, 0, NULL, NULL, NULL, NULL, ''),
(23, 'Boulevard de la Cybersécurité', 'Rue', 3, 0, 300, 225, 450, 5, ''),
(24, 'Avenue Mainframe', 'Rue', 4, 0, 325, 250, 500, 5, ''),
(25, 'Gare du Node', 'Gare', 5, 0, 300, 50, NULL, NULL, ''),
(26, 'Rue du Saint-Réseau', 'Rue', 6, 0, 350, 275, 550, 6, ''),
(27, 'Place du GRETA', 'Rue', 7, 0, 350, 275, 550, 6, ''),
(28, 'Compagnie du Cloud', 'Compagnie', 8, 0, 250, NULL, NULL, NULL, ''),
(29, 'Rue de la Fibre', 'Rue', 9, 0, 375, 300, 600, 6, ''),
(30, 'Police', 'Police', 10, 0, NULL, NULL, NULL, NULL, ''),
(31, 'Avenue de la BDD', 'Rue', 10, 1, 400, 325, 650, 7, ''),
(32, 'Avenue du Firewall', 'Rue', 10, 2, 400, 325, 650, 7, ''),
(33, 'Communauté', 'Communauté', 10, 3, NULL, NULL, NULL, NULL, ''),
(34, 'Boulevard des C++', 'Rue', 10, 4, 425, 350, 700, 7, ''),
(35, 'Gare du Git', 'Gare', 10, 5, 300, 50, NULL, NULL, ''),
(36, 'Chance', 'Chance', 10, 6, NULL, NULL, NULL, NULL, ''),
(37, 'Avenue de la Block Chain', 'Rue', 10, 7, 450, 400, 800, 8, ''),
(38, 'Taxe de Luxe', 'Taxe', 10, 8, 100, NULL, NULL, NULL, ''),
(39, 'Rue de la PHP', 'Rue', 10, 9, 500, 450, 900, 8, ''),
(99, 'centre', 'Rue', 0, 0, NULL, NULL, NULL, NULL, '');

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
  `batiment` int NOT NULL,
  `id_case_plateau` int UNSIGNED NOT NULL,
  `collection` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
(9, 'usernametest', 'perdf@dfdfdf', 'yUfEEOQgEnzG6HB9iOVDxyftexevFDwe7YKSSWFVPE4=', 'Admin', '2026-04-10 17:45:06');

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
  ADD PRIMARY KEY (`id_case_plateau`),
  ADD KEY `fk_case_plateau_id_couleur` (`id_couleur`);

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
  ADD KEY `fk_propriete_id_case_plateau` (`id_case_plateau`);

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
  MODIFY `id_propriete` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sauvegarde_partie`
--
ALTER TABLE `sauvegarde_partie`
  MODIFY `id_sauvegarde` int UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_utilisateur` int UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `case_plateau`
--
ALTER TABLE `case_plateau`
  ADD CONSTRAINT `fk_case_plateau_id_couleur` FOREIGN KEY (`id_couleur`) REFERENCES `groupe_couleur` (`id_couleur`) ON DELETE RESTRICT ON UPDATE CASCADE;

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
  ADD CONSTRAINT `fk_propriete_id_case_plateau` FOREIGN KEY (`id_case_plateau`) REFERENCES `case_plateau` (`id_case_plateau`) ON DELETE RESTRICT ON UPDATE RESTRICT;

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
