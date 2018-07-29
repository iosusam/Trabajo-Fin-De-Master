-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-03-2018 a las 21:05:56
-- Versión del servidor: 10.1.25-MariaDB
-- Versión de PHP: 7.1.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `suite`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `app`
--

CREATE TABLE `app` (
  `IDApp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `IDCategoria` int(5) NOT NULL,
  `IDPadre` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `channels`
--

CREATE TABLE `channels` (
  `IDChannels` int(5) NOT NULL,
  `LogicalChannelNumber` int(11) NOT NULL,
  `Url` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  `Name` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `Description` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `AspectRatio` varchar(6) COLLATE utf8_spanish_ci NOT NULL,
  `Quality` enum('SD','HD') COLLATE utf8_spanish_ci NOT NULL,
  `Visibility` enum('Private','Public') COLLATE utf8_spanish_ci NOT NULL,
  `SmallIcon` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `MediumIcon` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `LargeIcon` varchar(50) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `containchannelspaquetes`
--

CREATE TABLE `containchannelspaquetes` (
  `IDPaquete` int(5) NOT NULL,
  `IDChannels` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `containorganizacionapp`
--

CREATE TABLE `containorganizacionapp` (
  `IDApp` int(5) NOT NULL,
  `IDOrganizacion` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `containparrichanels`
--

CREATE TABLE `containparrichanels` (
  `IDChannels` int(5) NOT NULL,
  `IDParrilla` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `containparripaquetes`
--

CREATE TABLE `containparripaquetes` (
  `IDParrilla` int(5) NOT NULL,
  `IDPaquetes` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `containvideoscategia`
--

CREATE TABLE `containvideoscategia` (
  `IDCategoria` int(5) NOT NULL,
  `IDVideo` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `epg`
--

CREATE TABLE `epg` (
  `IDEpg` int(5) NOT NULL,
  `IDChannels` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `infochannels`
--

CREATE TABLE `infochannels` (
  `IDInfo` int(11) NOT NULL,
  `IDChannels` int(11) NOT NULL,
  `IDOrganizacion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `organizacion`
--

CREATE TABLE `organizacion` (
  `ID` int(5) NOT NULL,
  `IDParrilla` int(5) NOT NULL,
  `Pais` varchar(15) COLLATE utf8_spanish_ci DEFAULT NULL,
  `Region` int(15) DEFAULT NULL,
  `Direccion` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  `Codigo Postal` int(5) DEFAULT NULL,
  `Ciudad` varchar(15) COLLATE utf8_spanish_ci DEFAULT NULL,
  `Telefono` varchar(13) COLLATE utf8_spanish_ci DEFAULT NULL,
  `Email` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paquetes`
--

CREATE TABLE `paquetes` (
  `IDPaquete` int(5) NOT NULL,
  `Descripcion` varchar(15) COLLATE utf8_spanish_ci NOT NULL,
  `IDChannels` int(5) NOT NULL,
  `IDParrilla` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parrilla`
--

CREATE TABLE `parrilla` (
  `IDParrilla` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `suscriptores`
--

CREATE TABLE `suscriptores` (
  `IDSuscriptor` int(11) NOT NULL,
  `IDOrganizacion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `videos`
--

CREATE TABLE `videos` (
  `IDVideo` int(5) NOT NULL,
  `Titulo` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `Publish` date NOT NULL,
  `Unpublish` date NOT NULL,
  `Visibilidad` enum('Public','Private') COLLATE utf8_spanish_ci NOT NULL,
  `Calidad` enum('SD','HD') COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vod`
--

CREATE TABLE `vod` (
  `IDVod` int(5) NOT NULL,
  `IDOrganizacion` int(5) NOT NULL,
  `IDCategoria` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `app`
--
ALTER TABLE `app`
  ADD PRIMARY KEY (`IDApp`);

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`IDCategoria`,`IDPadre`) USING BTREE;

--
-- Indices de la tabla `channels`
--
ALTER TABLE `channels`
  ADD PRIMARY KEY (`IDChannels`) USING BTREE;

--
-- Indices de la tabla `containchannelspaquetes`
--
ALTER TABLE `containchannelspaquetes`
  ADD PRIMARY KEY (`IDPaquete`,`IDChannels`),
  ADD KEY `RelChannelContainPaque` (`IDChannels`);

--
-- Indices de la tabla `containorganizacionapp`
--
ALTER TABLE `containorganizacionapp`
  ADD PRIMARY KEY (`IDApp`,`IDOrganizacion`),
  ADD KEY `IDOrganizacion` (`IDOrganizacion`);

--
-- Indices de la tabla `containparrichanels`
--
ALTER TABLE `containparrichanels`
  ADD PRIMARY KEY (`IDChannels`,`IDParrilla`),
  ADD KEY `RelParriContainChannel` (`IDParrilla`);

--
-- Indices de la tabla `containparripaquetes`
--
ALTER TABLE `containparripaquetes`
  ADD PRIMARY KEY (`IDPaquetes`,`IDParrilla`),
  ADD KEY `RelParriContainsPaque` (`IDParrilla`);

--
-- Indices de la tabla `containvideoscategia`
--
ALTER TABLE `containvideoscategia`
  ADD PRIMARY KEY (`IDCategoria`,`IDVideo`),
  ADD KEY `IDVideo` (`IDVideo`);

--
-- Indices de la tabla `epg`
--
ALTER TABLE `epg`
  ADD PRIMARY KEY (`IDEpg`,`IDChannels`),
  ADD KEY `IDChannels` (`IDChannels`);

--
-- Indices de la tabla `infochannels`
--
ALTER TABLE `infochannels`
  ADD PRIMARY KEY (`IDInfo`,`IDChannels`,`IDOrganizacion`),
  ADD KEY `IDOrganizacion` (`IDOrganizacion`),
  ADD KEY `IDChannels` (`IDChannels`);

--
-- Indices de la tabla `organizacion`
--
ALTER TABLE `organizacion`
  ADD PRIMARY KEY (`ID`,`IDParrilla`) USING BTREE,
  ADD KEY `IDParrilla` (`IDParrilla`);

--
-- Indices de la tabla `paquetes`
--
ALTER TABLE `paquetes`
  ADD PRIMARY KEY (`IDChannels`,`IDParrilla`,`IDPaquete`),
  ADD KEY `RelaPaquetContainsChan` (`IDPaquete`);

--
-- Indices de la tabla `parrilla`
--
ALTER TABLE `parrilla`
  ADD PRIMARY KEY (`IDParrilla`);

--
-- Indices de la tabla `suscriptores`
--
ALTER TABLE `suscriptores`
  ADD PRIMARY KEY (`IDSuscriptor`,`IDOrganizacion`),
  ADD KEY `IDOrganizacion` (`IDOrganizacion`);

--
-- Indices de la tabla `videos`
--
ALTER TABLE `videos`
  ADD PRIMARY KEY (`IDVideo`);

--
-- Indices de la tabla `vod`
--
ALTER TABLE `vod`
  ADD PRIMARY KEY (`IDVod`,`IDOrganizacion`,`IDCategoria`) USING BTREE,
  ADD KEY `IDOrganizacion` (`IDOrganizacion`),
  ADD KEY `IDCategoria` (`IDCategoria`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `app`
--
ALTER TABLE `app`
  MODIFY `IDApp` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `IDCategoria` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `channels`
--
ALTER TABLE `channels`
  MODIFY `IDChannels` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `epg`
--
ALTER TABLE `epg`
  MODIFY `IDEpg` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `infochannels`
--
ALTER TABLE `infochannels`
  MODIFY `IDInfo` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `organizacion`
--
ALTER TABLE `organizacion`
  MODIFY `ID` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `parrilla`
--
ALTER TABLE `parrilla`
  MODIFY `IDParrilla` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `suscriptores`
--
ALTER TABLE `suscriptores`
  MODIFY `IDSuscriptor` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `videos`
--
ALTER TABLE `videos`
  MODIFY `IDVideo` int(5) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `vod`
--
ALTER TABLE `vod`
  MODIFY `IDVod` int(5) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `channels`
--
ALTER TABLE `channels`
  ADD CONSTRAINT `RelChannelsContainsPaque` FOREIGN KEY (`IDChannels`) REFERENCES `containchannelspaquetes` (`IDChannels`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `containchannelspaquetes`
--
ALTER TABLE `containchannelspaquetes`
  ADD CONSTRAINT `RelChannelContainPaque` FOREIGN KEY (`IDChannels`) REFERENCES `channels` (`IDChannels`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `RelPaqueContainChannel` FOREIGN KEY (`IDPaquete`) REFERENCES `paquetes` (`IDPaquete`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `containorganizacionapp`
--
ALTER TABLE `containorganizacionapp`
  ADD CONSTRAINT `containorganizacionapp_ibfk_1` FOREIGN KEY (`IDApp`) REFERENCES `app` (`IDApp`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `containorganizacionapp_ibfk_2` FOREIGN KEY (`IDOrganizacion`) REFERENCES `organizacion` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `containparrichanels`
--
ALTER TABLE `containparrichanels`
  ADD CONSTRAINT `RelChannelContainParri` FOREIGN KEY (`IDChannels`) REFERENCES `channels` (`IDChannels`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `RelParriContainChannel` FOREIGN KEY (`IDParrilla`) REFERENCES `parrilla` (`IDParrilla`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `containparripaquetes`
--
ALTER TABLE `containparripaquetes`
  ADD CONSTRAINT `RelPaqueContainsParri` FOREIGN KEY (`IDPaquetes`) REFERENCES `paquetes` (`IDPaquete`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `RelParriContainsPaque` FOREIGN KEY (`IDParrilla`) REFERENCES `parrilla` (`IDParrilla`);

--
-- Filtros para la tabla `containvideoscategia`
--
ALTER TABLE `containvideoscategia`
  ADD CONSTRAINT `containvideoscategia_ibfk_1` FOREIGN KEY (`IDCategoria`) REFERENCES `categorias` (`IDCategoria`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `containvideoscategia_ibfk_2` FOREIGN KEY (`IDVideo`) REFERENCES `videos` (`IDVideo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `epg`
--
ALTER TABLE `epg`
  ADD CONSTRAINT `epg_ibfk_1` FOREIGN KEY (`IDChannels`) REFERENCES `channels` (`IDChannels`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `infochannels`
--
ALTER TABLE `infochannels`
  ADD CONSTRAINT `infochannels_ibfk_1` FOREIGN KEY (`IDOrganizacion`) REFERENCES `organizacion` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `infochannels_ibfk_2` FOREIGN KEY (`IDChannels`) REFERENCES `channels` (`IDChannels`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `organizacion`
--
ALTER TABLE `organizacion`
  ADD CONSTRAINT `organizacion_ibfk_1` FOREIGN KEY (`IDParrilla`) REFERENCES `parrilla` (`IDParrilla`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `paquetes`
--
ALTER TABLE `paquetes`
  ADD CONSTRAINT `RelPaqueteContainsParri` FOREIGN KEY (`IDPaquete`) REFERENCES `containparripaquetes` (`IDPaquetes`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `RelaPaquetContainsChan` FOREIGN KEY (`IDPaquete`) REFERENCES `containchannelspaquetes` (`IDPaquete`);

--
-- Filtros para la tabla `suscriptores`
--
ALTER TABLE `suscriptores`
  ADD CONSTRAINT `suscriptores_ibfk_1` FOREIGN KEY (`IDOrganizacion`) REFERENCES `organizacion` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `vod`
--
ALTER TABLE `vod`
  ADD CONSTRAINT `vod_ibfk_1` FOREIGN KEY (`IDOrganizacion`) REFERENCES `organizacion` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `vod_ibfk_2` FOREIGN KEY (`IDCategoria`) REFERENCES `categorias` (`IDCategoria`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
