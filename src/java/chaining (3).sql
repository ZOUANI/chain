-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Ven 22 Décembre 2017 à 08:35
-- Version du serveur :  10.1.8-MariaDB
-- Version de PHP :  5.6.14

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `chaining`
--

-- --------------------------------------------------------

--
-- Structure de la table `chain`
--

CREATE TABLE `chain` (
  `ID` bigint(20) NOT NULL,
  `REFERENCE` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `chain`
--

INSERT INTO `chain` (`ID`, `REFERENCE`) VALUES
(1, 'chaine 1'),
(2, 'chaine 2'),
(3, 'chaine 3'),
(4, 'chaine 4'),
(5, 'chaine 5');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `ID` bigint(20) NOT NULL,
  `ADRESSE` varchar(255) DEFAULT NULL,
  `BLOQUER` tinyint(1) DEFAULT '0',
  `CREANCE` decimal(38,0) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `DETAILBLOQUAGE` varchar(255) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `MODIFIER` tinyint(1) DEFAULT '0',
  `NOM` varchar(255) DEFAULT NULL,
  `SUPPRIMER` tinyint(1) DEFAULT '0',
  `TEL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`ID`, `ADRESSE`, `BLOQUER`, `CREANCE`, `DESCRIPTION`, `DETAILBLOQUAGE`, `EMAIL`, `MODIFIER`, `NOM`, `SUPPRIMER`, `TEL`) VALUES
(1, 'cl1', 0, '0', 'cl1', '', 'cl1', 0, 'cl1', 0, 'cl1'),
(2, 'cl2', 0, '0', 'cl2', '', 'cl2', 0, 'cl2', 0, 'cl2');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `ID` bigint(20) NOT NULL,
  `COMMENTAIRE` varchar(255) DEFAULT NULL,
  `DATECOMMANDE` date DEFAULT NULL,
  `DATEECHANCE` date DEFAULT NULL,
  `ETATRECEPTION` int(11) DEFAULT NULL,
  `MODIFIER` int(11) DEFAULT NULL,
  `MONTANTTOTAL` decimal(38,0) DEFAULT NULL,
  `MONTANTTOTALAVOIR` decimal(38,0) DEFAULT NULL,
  `MONTANTTOTALRECEPTION` decimal(38,0) DEFAULT NULL,
  `PAIEMENT` decimal(38,0) DEFAULT NULL,
  `PAIEMENTEFFETENCOUR` decimal(38,0) DEFAULT NULL,
  `REFERENCE` varchar(255) DEFAULT NULL,
  `REFERENCEINDEX` bigint(20) DEFAULT NULL,
  `REFERENCEPRIFFIX` varchar(255) DEFAULT NULL,
  `REFERENCESUFFIX` varchar(255) DEFAULT NULL,
  `SUPPRIMER` int(11) DEFAULT NULL,
  `TVA` decimal(38,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `commande`
--

INSERT INTO `commande` (`ID`, `COMMENTAIRE`, `DATECOMMANDE`, `DATEECHANCE`, `ETATRECEPTION`, `MODIFIER`, `MONTANTTOTAL`, `MONTANTTOTALAVOIR`, `MONTANTTOTALRECEPTION`, `PAIEMENT`, `PAIEMENTEFFETENCOUR`, `REFERENCE`, `REFERENCEINDEX`, `REFERENCEPRIFFIX`, `REFERENCESUFFIX`, `SUPPRIMER`, `TVA`) VALUES
(1, 'bb', '2017-12-21', '2017-12-21', 0, 0, '9800', NULL, NULL, NULL, NULL, '2017-1-44', 1, '2017', '44', 0, '20'),
(2, 'ffff', '2017-12-21', '2017-12-21', 0, 0, '2500', NULL, NULL, NULL, NULL, '2017-2-ZZ', 2, '2017', 'ZZ', 0, '20');

-- --------------------------------------------------------

--
-- Structure de la table `commandeitem`
--

CREATE TABLE `commandeitem` (
  `ID` bigint(20) NOT NULL,
  `PRIX` decimal(38,0) DEFAULT NULL,
  `QTE` decimal(38,0) DEFAULT NULL,
  `QTERECU` decimal(38,0) DEFAULT NULL,
  `COMMANDE_ID` bigint(20) DEFAULT NULL,
  `COULEUR_ID` bigint(20) DEFAULT NULL,
  `PRODUIT_ID` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `commandeitem`
--

INSERT INTO `commandeitem` (`ID`, `PRIX`, `QTE`, `QTERECU`, `COMMANDE_ID`, `COULEUR_ID`, `PRODUIT_ID`) VALUES
(1, '44', '100', '100', 1, 6, 1),
(2, '54', '100', '20', 1, 7, 3),
(3, '5', '100', '10', 2, 7, 3),
(4, '200', '10', '0', 2, 6, 1);

-- --------------------------------------------------------

--
-- Structure de la table `couleur`
--

CREATE TABLE `couleur` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `REFERENCE` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `couleur`
--

INSERT INTO `couleur` (`ID`, `NOM`, `REFERENCE`) VALUES
(6, 'rouge', 'c92626'),
(7, 'vert', '00ff44');

-- --------------------------------------------------------

--
-- Structure de la table `famille`
--

CREATE TABLE `famille` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `famille`
--

INSERT INTO `famille` (`ID`, `LIBELLE`) VALUES
(1, 'F1'),
(2, 'F2'),
(3, 'F3');

-- --------------------------------------------------------

--
-- Structure de la table `heure`
--

CREATE TABLE `heure` (
  `ID` bigint(20) NOT NULL,
  `HEUREDEBUT` varchar(255) DEFAULT NULL,
  `HEUREFIN` varchar(255) DEFAULT NULL,
  `REFERENCE` varchar(255) DEFAULT NULL,
  `DURREE` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `heure`
--

INSERT INTO `heure` (`ID`, `HEUREDEBUT`, `HEUREFIN`, `REFERENCE`, `DURREE`) VALUES
(51, '9h10', '10h15', 'H1', 5),
(52, '10h20', '10h55', 'H2', 35),
(53, '11h00', '11h45', 'H3', 45);

-- --------------------------------------------------------

--
-- Structure de la table `productionitem`
--

CREATE TABLE `productionitem` (
  `ID` bigint(20) NOT NULL,
  `DATEPRODUCTION` date DEFAULT NULL,
  `QTE` decimal(38,0) DEFAULT NULL,
  `CHAIN_ID` bigint(20) DEFAULT NULL,
  `COMMANDE_ID` bigint(20) DEFAULT NULL,
  `HEURE_ID` bigint(20) DEFAULT NULL,
  `PRODUIT_ID` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `productionitem`
--

INSERT INTO `productionitem` (`ID`, `DATEPRODUCTION`, `QTE`, `CHAIN_ID`, `COMMANDE_ID`, `HEURE_ID`, `PRODUIT_ID`) VALUES
(101, '2017-12-21', '100', 1, 1, 52, 1),
(102, '2017-12-21', '20', 1, 1, 51, 3),
(103, '2017-12-21', '10', 2, 2, 51, 3);

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `ID` bigint(20) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  `MODIFIER` tinyint(1) DEFAULT '0',
  `QTEGLOBALSTOCK` decimal(38,0) DEFAULT NULL,
  `QTEPARSTOCK` decimal(38,0) DEFAULT NULL,
  `REFERENCE` varchar(255) DEFAULT NULL,
  `SEUILALERT` double DEFAULT NULL,
  `SUPPRIMER` tinyint(1) DEFAULT '0',
  `FAMILLE_ID` bigint(20) DEFAULT NULL,
  `UNITEMESURE_ID` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `produit`
--

INSERT INTO `produit` (`ID`, `LIBELLE`, `MODIFIER`, `QTEGLOBALSTOCK`, `QTEPARSTOCK`, `REFERENCE`, `SEUILALERT`, `SUPPRIMER`, `FAMILLE_ID`, `UNITEMESURE_ID`) VALUES
(1, 'p1', 0, '500', NULL, 'REE3', 9, 0, 1, 1),
(2, 'p2', 0, '8990', NULL, 'ERTY4', 90, 0, 2, 2),
(3, 'pr3', 0, '900', NULL, 'YYE11', 11, 0, 3, 3),
(4, NULL, 0, NULL, NULL, NULL, 0, 0, NULL, NULL),
(5, NULL, 0, NULL, NULL, NULL, 0, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `sequence`
--

CREATE TABLE `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `sequence`
--

INSERT INTO `sequence` (`SEQ_NAME`, `SEQ_COUNT`) VALUES
('SEQ_GEN', '150');

-- --------------------------------------------------------

--
-- Structure de la table `unitemesure`
--

CREATE TABLE `unitemesure` (
  `ID` bigint(20) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `unitemesure`
--

INSERT INTO `unitemesure` (`ID`, `NOM`) VALUES
(1, 'Kg'),
(2, 'L'),
(3, 'M');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `LOGIN` varchar(255) NOT NULL,
  `BLOCKED` int(11) DEFAULT NULL,
  `EMAIL` varchar(255) DEFAULT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `PRENOM` varchar(255) DEFAULT NULL,
  `TEL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`LOGIN`, `BLOCKED`, `EMAIL`, `NOM`, `PASSWORD`, `PRENOM`, `TEL`) VALUES
('ana', 0, 'zouani', 'zouani', 'ana', 'ana', 'ana');

--
-- Index pour les tables exportées
--

--
-- Index pour la table `chain`
--
ALTER TABLE `chain`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `commandeitem`
--
ALTER TABLE `commandeitem`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_COMMANDEITEM_PRODUIT_ID` (`PRODUIT_ID`),
  ADD KEY `FK_COMMANDEITEM_COULEUR_ID` (`COULEUR_ID`),
  ADD KEY `FK_COMMANDEITEM_COMMANDE_ID` (`COMMANDE_ID`);

--
-- Index pour la table `couleur`
--
ALTER TABLE `couleur`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `famille`
--
ALTER TABLE `famille`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `heure`
--
ALTER TABLE `heure`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `productionitem`
--
ALTER TABLE `productionitem`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_PRODUCTIONITEM_COMMANDE_ID` (`COMMANDE_ID`),
  ADD KEY `FK_PRODUCTIONITEM_PRODUIT_ID` (`PRODUIT_ID`),
  ADD KEY `FK_PRODUCTIONITEM_CHAIN_ID` (`CHAIN_ID`),
  ADD KEY `FK_PRODUCTIONITEM_HEURE_ID` (`HEURE_ID`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_PRODUIT_UNITEMESURE_ID` (`UNITEMESURE_ID`),
  ADD KEY `FK_PRODUIT_FAMILLE_ID` (`FAMILLE_ID`);

--
-- Index pour la table `sequence`
--
ALTER TABLE `sequence`
  ADD PRIMARY KEY (`SEQ_NAME`);

--
-- Index pour la table `unitemesure`
--
ALTER TABLE `unitemesure`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`LOGIN`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `commandeitem`
--
ALTER TABLE `commandeitem`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT pour la table `produit`
--
ALTER TABLE `produit`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `unitemesure`
--
ALTER TABLE `unitemesure`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `commandeitem`
--
ALTER TABLE `commandeitem`
  ADD CONSTRAINT `FK_COMMANDEITEM_COMMANDE_ID` FOREIGN KEY (`COMMANDE_ID`) REFERENCES `commande` (`ID`),
  ADD CONSTRAINT `FK_COMMANDEITEM_COULEUR_ID` FOREIGN KEY (`COULEUR_ID`) REFERENCES `couleur` (`ID`),
  ADD CONSTRAINT `FK_COMMANDEITEM_PRODUIT_ID` FOREIGN KEY (`PRODUIT_ID`) REFERENCES `produit` (`ID`);

--
-- Contraintes pour la table `productionitem`
--
ALTER TABLE `productionitem`
  ADD CONSTRAINT `FK_PRODUCTIONITEM_CHAIN_ID` FOREIGN KEY (`CHAIN_ID`) REFERENCES `chain` (`ID`),
  ADD CONSTRAINT `FK_PRODUCTIONITEM_COMMANDE_ID` FOREIGN KEY (`COMMANDE_ID`) REFERENCES `commande` (`ID`),
  ADD CONSTRAINT `FK_PRODUCTIONITEM_HEURE_ID` FOREIGN KEY (`HEURE_ID`) REFERENCES `heure` (`ID`),
  ADD CONSTRAINT `FK_PRODUCTIONITEM_PRODUIT_ID` FOREIGN KEY (`PRODUIT_ID`) REFERENCES `produit` (`ID`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `FK_PRODUIT_FAMILLE_ID` FOREIGN KEY (`FAMILLE_ID`) REFERENCES `famille` (`ID`),
  ADD CONSTRAINT `FK_PRODUIT_UNITEMESURE_ID` FOREIGN KEY (`UNITEMESURE_ID`) REFERENCES `unitemesure` (`ID`);
SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
