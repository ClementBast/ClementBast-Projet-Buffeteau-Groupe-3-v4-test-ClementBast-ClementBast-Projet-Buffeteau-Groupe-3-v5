-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : dim. 21 avr. 2024 à 16:59
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projetbuff`
--

-- --------------------------------------------------------

--
-- Structure de la table `competence`
--

DROP TABLE IF EXISTS `competence`;
CREATE TABLE IF NOT EXISTS `competence` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_matiere` int NOT NULL,
  `id_user` int NOT NULL,
  `sous_matiere` longtext NOT NULL,
  `statut` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user_competence` (`id_user`),
  KEY `id_matiere_competence` (`id_matiere`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `competence`
--

INSERT INTO `competence` (`id`, `id_matiere`, `id_user`, `sous_matiere`, `statut`) VALUES
(64, 5, 2, '#java#python#javascript', 1),
(66, 4, 2, '#équations', 1),
(68, 1, 2, '#verbesirréguliers', 1),
(69, 1, 1, '#verbesirréguliers', 1),
(70, 1, 4, '#verbesirréguliers', 1);

-- --------------------------------------------------------

--
-- Structure de la table `demande`
--

DROP TABLE IF EXISTS `demande`;
CREATE TABLE IF NOT EXISTS `demande` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_updated` date NOT NULL,
  `date_fin_demande` date NOT NULL,
  `sous_matiere` longtext NOT NULL,
  `id_user` int NOT NULL,
  `id_matiere` int NOT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  KEY `id_matiere` (`id_matiere`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `demande`
--

INSERT INTO `demande` (`id`, `date_updated`, `date_fin_demande`, `sous_matiere`, `id_user`, `id_matiere`, `status`) VALUES
(56, '2024-04-17', '2024-04-17', '#verbesirréguliers', 5, 1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `matiere`
--

DROP TABLE IF EXISTS `matiere`;
CREATE TABLE IF NOT EXISTS `matiere` (
  `id` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(200) NOT NULL,
  `code` int NOT NULL,
  `sous_matiere` longtext CHARACTER SET latin1 COLLATE latin1_swedish_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `matiere`
--

INSERT INTO `matiere` (`id`, `designation`, `code`, `sous_matiere`) VALUES
(1, 'Anglais', 1, '#verbesirréguliers#gtrf#présent#date#nombres#toeic'),
(2, 'CEJM', 2, '#contrats#régulation#organisation#intégration#rôleétat#environnement#facteurséconomiques#structurejuridique#droit'),
(3, 'Francais', 3, '#orthographe#conjugaison#participepassé#présent#futursimple,#pronompersonnel,#conjonctioncoordination#auxiliareavoir#indicatif#test'),
(4, 'Mathématiques', 2, '#équations#factorisation#nombresrelatifs#intégrale#dérivée#tableaudevariation#matrice#développement#loidepoisson#probabilités#statistiques'),
(5, 'Informatique', 5, '#java#sql#python#php#javascript#modelosi#tcpip#windows#linux#dhcp#dns#voip#cisco#poo#boucles#conditions#json#api'),
(6, 'Histoire', 6, '#crise1929#régimestotalitaires#secondeguerremondiale#tiersmonde#france#constructioneuropéenne#puissanceetenjeuxmondiaux');

-- --------------------------------------------------------

--
-- Structure de la table `niveau`
--

DROP TABLE IF EXISTS `niveau`;
CREATE TABLE IF NOT EXISTS `niveau` (
  `id` int NOT NULL AUTO_INCREMENT,
  `designation` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `niveau`
--

INSERT INTO `niveau` (`id`, `designation`) VALUES
(1, 'Master 2'),
(2, 'Master 1'),
(3, 'Licence'),
(4, 'BTS 2'),
(5, 'BTS 1'),
(6, 'Terminale'),
(7, 'Premiere');

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

DROP TABLE IF EXISTS `salle`;
CREATE TABLE IF NOT EXISTS `salle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code_salle` varchar(10) NOT NULL,
  `etage` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=307 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `salle`
--

INSERT INTO `salle` (`id`, `code_salle`, `etage`) VALUES
(101, 'Salle 101', 1),
(102, 'Salle 102', 1),
(201, 'Salle 201', 2),
(202, 'Salle 202', 2),
(301, 'Salle 301', 3),
(302, 'Salle 302', 3),
(303, 'Salle 303', 3),
(304, 'Salle 304', 3),
(306, 'Salle 400', 0);

-- --------------------------------------------------------

--
-- Structure de la table `soutien`
--

DROP TABLE IF EXISTS `soutien`;
CREATE TABLE IF NOT EXISTS `soutien` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_demande` int NOT NULL,
  `id_competence` int NOT NULL,
  `id_salle` int DEFAULT NULL,
  `date_du_soutien` date DEFAULT NULL,
  `date_updated` date DEFAULT NULL,
  `description` longtext CHARACTER SET latin1 COLLATE latin1_swedish_ci,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_competence` (`id_competence`),
  KEY `id_demande` (`id_demande`),
  KEY `id_salle_soutien` (`id_salle`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `soutien`
--

INSERT INTO `soutien` (`id`, `id_demande`, `id_competence`, `id_salle`, `date_du_soutien`, `date_updated`, `description`, `status`) VALUES
(32, 56, 68, NULL, '2023-12-12', '2023-12-12', 'prendre livre', 1);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `role` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `niveau` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `sexe` int NOT NULL,
  `telephone` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `nom`, `prenom`, `email`, `password`, `role`, `niveau`, `sexe`, `telephone`) VALUES
(1, 'Castaing', 'Sabine', 'sabine.castaing@lerebours.org', 'sabcas', 'Etudiant', '1', 2, '0661010101'),
(2, 'Thouri', 'Erwan', 'erwan.thouri@lerebours.org', 'erwtho', 'Etudiant', '2', 1, '0661020202'),
(3, 'Bloch', 'Nicolas', 'nicolas.bloch@lerebours.org', 'nicblo', 'Etudiant', '3', 1, '0661030303'),
(4, 'Chittarath', 'Christophe', 'chittarath.christophe@lerebours.org', 'chrchi', 'Etudiant', '4', 1, '0661040404'),
(5, 'Buffeteau', 'Jacques', 'jacques.buffeteau@lerebours.org', 'jacbuf', 'Etudiant', '5', 1, '0661050505'),
(6, 'Pamart', 'Marie-Sophie', 'pamart.marie.sophie@lerebours.org', 'pammar', 'Etudiant', '6', 2, '0661060606'),
(7, 'Sordet', 'Evelyne', 'evelyne.sordet@lerebours.org', 'evesor', 'Etudiant', '1', 2, '0661070707'),
(8, 'Cornia', 'Alberto', 'alberto.cornia@lerebours.org', 'albcor', 'Etudiant', '7', 1, '0661080808'),
(9, 'Ioualitene', 'Fatah', 'loualitene.fatah@lerebours.org', 'fatlou', 'Etudiant', '4', 1, '0661090909'),
(10, 'test', 'test', 'admin@ler.com', 'admin', 'Admin', '[value-7]', 0, '0');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `competence`
--
ALTER TABLE `competence`
  ADD CONSTRAINT `id_matiere_competence` FOREIGN KEY (`id_matiere`) REFERENCES `matiere` (`id`),
  ADD CONSTRAINT `id_user_competence` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `demande`
--
ALTER TABLE `demande`
  ADD CONSTRAINT `id_matiere` FOREIGN KEY (`id_matiere`) REFERENCES `matiere` (`id`),
  ADD CONSTRAINT `id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `soutien`
--
ALTER TABLE `soutien`
  ADD CONSTRAINT `id_competence` FOREIGN KEY (`id_competence`) REFERENCES `competence` (`id`),
  ADD CONSTRAINT `id_demande` FOREIGN KEY (`id_demande`) REFERENCES `demande` (`id`),
  ADD CONSTRAINT `id_salle_soutien` FOREIGN KEY (`id_salle`) REFERENCES `salle` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
