USE [Gestor_de_Guardias]

INSERT INTO [dbo].[rol] ([nombre]) VALUES
    (N'Administrador'),
    (N'Usuario')
GO

SET IDENTITY_INSERT [dbo].[usuario] ON
GO
INSERT INTO [dbo].[usuario] ([id], [nombre], [contrasenna], [rol]) VALUES
    (1, N'administrador', N'administrador', N'Administrador'),
    (2, N'usuario', N'usuario', N'Usuario')
GO
SET IDENTITY_INSERT [dbo].[usuario] OFF
GO


INSERT INTO [dbo].[tipo_persona] ([nombre]) VALUES
    (N'Estudiante'),
    (N'Trabajador')
GO


 SET IDENTITY_INSERT [dbo].[horario] ON
 GO

 INSERT INTO [dbo].[horario] ([inicio], [fin], [id], [borrado]) VALUES 
	 (N'20:00:00', N'08:00:00', N'1', N'0'),
	 (N'08:00:00', N'20:00:00', N'2', N'0'),
	 (N'09:00:00', N'14:00:00', N'3', N'0'),
	 (N'14:00:00', N'19:00:00', N'4', N'0')
 GO

 SET IDENTITY_INSERT [dbo].[horario] OFF
 GO

 SET IDENTITY_INSERT [dbo].[configuracion] ON
 GO

 INSERT INTO [dbo].[configuracion] ([dia_semana], [dia_es_receso], [horario], [tipo_persona], [sexo], [cant_personas], [id], [borrado]) VALUES 
	 (N'1', N'0', N'1', N'Estudiante', N'M', N'1', N'1', N'0'),
	 (N'2', N'0', N'1', N'Estudiante', N'M', N'1', N'2', N'0'),
	 (N'3', N'0', N'1', N'Estudiante', N'M', N'1', N'3', N'0'),
	 (N'4', N'0', N'1', N'Estudiante', N'M', N'1', N'4', N'0'),
	 (N'5', N'0', N'1', N'Estudiante', N'M', N'1', N'5', N'0'),
	 (N'6', N'0', N'1', N'Estudiante', N'M', N'1', N'6', N'0'),
	 (N'7', N'0', N'1', N'Estudiante', N'M', N'1', N'7', N'0'),
	 (N'6', N'0', N'2', N'Estudiante', N'F', N'1', N'8', N'0'),
	 (N'7', N'0', N'2', N'Estudiante', N'F', N'1', N'9', N'0'),
	 (N'6', N'0', N'3', N'Trabajador', NULL, N'1', N'10', N'0'),
	 (N'6', N'0', N'4', N'Trabajador', NULL, N'1', N'11', N'0'),
	 (N'7', N'0', N'3', N'Trabajador', NULL, N'1', N'12', N'0'),
	 (N'7', N'0', N'4', N'Trabajador', NULL, N'1', N'13', N'0'),
	 (N'1', N'1', N'3', N'Trabajador', NULL, N'1', N'14', N'0'),
	 (N'1', N'1', N'4', N'Trabajador', NULL, N'1', N'15', N'0'),
	 (N'2', N'1', N'3', N'Trabajador', NULL, N'1', N'16', N'0'),
	 (N'2', N'1', N'4', N'Trabajador', NULL, N'1', N'17', N'0'),
	 (N'3', N'1', N'3', N'Trabajador', NULL, N'1', N'18', N'0'),
	 (N'3', N'1', N'4', N'Trabajador', NULL, N'1', N'19', N'0'),
	 (N'4', N'1', N'3', N'Trabajador', NULL, N'1', N'20', N'0'),
	 (N'4', N'1', N'4', N'Trabajador', NULL, N'1', N'21', N'0'),
	 (N'5', N'1', N'3', N'Trabajador', NULL, N'1', N'22', N'0'),
	 (N'5', N'1', N'4', N'Trabajador', NULL, N'1', N'23', N'0'),
	 (N'6', N'1', N'3', N'Trabajador', NULL, N'1', N'24', N'0'),
	 (N'6', N'1', N'4', N'Trabajador', NULL, N'1', N'25', N'0'),
	 (N'7', N'1', N'3', N'Trabajador', NULL, N'1', N'26', N'0'),
	 (N'7', N'1', N'4', N'Trabajador', NULL, N'1', N'27', N'0')
 GO

 SET IDENTITY_INSERT [dbo].[configuracion] OFF
 GO


------------------------CREAR PERSONAS------------------------------------------------------------------------------------------------
SET IDENTITY_INSERT [dbo].[persona] ON
GO
-- TRABAJADORES
INSERT INTO [dbo].[persona] ([id], [nombre], [apellido], [sexo], [carnet], [tipo]) VALUES
	( 1, N'Sonia', N'Perez Lovelle', N'f', N'68110225093', N'Trabajador'),
    ( 2, N'Byron David', N'Cevallos Trujillo', 'm', N'73050632847', N'Trabajador'),
    ( 3, N'Gonzalo Luis', N'Balcazar Campoverde', 'm', N'87051952603', N'Trabajador'),
    ( 4, N'Fernanda Noemi', N'Campos Mendieta', 'f', N'78092587290', N'Trabajador'),
	( 5, N'Jaime Eduardo', N'Cárdenas Molina', 'm', N'93020322844', N'Trabajador'),
	( 6, N'Carlos Daniel', N'Villavicencio Pesantez', 'm', N'84092027046', N'Trabajador'),
	( 7, N'Marlene Estefania', N'Novillo Jara', 'f', N'78111051339', N'Trabajador'),
	( 8, N'Johanna Sofia', N'Perez Cabrera', 'f', N'63040450933', N'Trabajador'),
	( 9, N'Martha Patricia', N'Morales Harris', 'f', N'70071327412', N'Trabajador'),
	( 10, N'Diana Lucía', N'Iñiguez Iñiguez', 'f', N'85110233916', N'Trabajador'),
	( 11, N'Jaime Vicente', N'Chuchuca Serrano', 'm', N'73122830205', N'Trabajador'),
	( 12, N'Sahira Maribel', N'Martinez Cepeda', 'f', N'91101144653', N'Trabajador'),
	( 13, N'Xavier Eduardo', N'Montalvo Aponte', 'm', N'82021061482', N'Trabajador'),
	( 14, N'Alex Ruben', N'Cobos Veloz', 'm', N'65021376166', N'Trabajador'),
	( 15, N'Mireya Katerine', N'Pazmiño Arregui', 'f', N'66040878337', N'Trabajador'),
	( 16, N'Danilo Fernando', N'García García', 'm', N'74122662229', N'Trabajador'),
	( 17, N'Oswaldo Ernesto', N'Lopez Bravo', 'm', N'86112273545', N'Trabajador'),
	( 18, N'Juan Jose', N'Ortega Vintimilla', 'm', N'91122339065', N'Trabajador'),
	( 19, N'Geovanny Ramiro', N'Cabezas Velasco', 'm', N'63110921162', N'Trabajador'),
	( 20, N'Marlene Alexandra', N'Narvaez Calvachi', 'f', N'74102594455', N'Trabajador'),
	( 21, N'Lenin Eduardo', N'Saguay Sanaguano', 'm', N'83022156066', N'Trabajador'),
	( 22, N'Hernán Sebastián', N'López Montero', 'm', N'94061345883', N'Trabajador'),
	( 23, N'Pedro', N'Guaraca Cuñas', 'm', N'64101742842', N'Trabajador'),
	( 24, N'Raquel Alejandra', N'Arcos Cabezas', 'f', N'74061424753', N'Trabajador'),
	( 25, N'Georgina Piedad', N'Panchez Hernandez', 'f', N'81010751413', N'Trabajador'),
	( 26, N'Karina Monserrath', N'Masache Alvarado', 'f', N'92122025074', N'Trabajador'),
	( 27, N'Diego Javier', N'Cajas Logroño', 'm', N'62101372362', N'Trabajador'),
	( 28, N'Ricardo Israel', N'Valencia Cuviña', 'm', N'75031748220', N'Trabajador'),
	( 29, N'Lilli Lucia', N'Romero Pacheco', 'f', N'86092649453', N'Trabajador'),
	( 30, N'Susana Del Rocio', N'Andrade Soto', 'f', N'94040884719', N'Trabajador'),
	( 31, N'José Eliecer', N'Chicaiza Ronquillo', 'm', N'61051126489', N'Trabajador'),
	( 32, N'Edgar Guillerno', N'Guasca Tulmo', 'm', N'75090959743', N'Trabajador'),
	( 33, N'Gustavo Patricio', N'Mena Zapata', 'm', N'84111526245', N'Trabajador'),
	( 34, N'Lourdes Del Rocío', N'Lara Valencia', 'f', N'94010697432', N'Trabajador'),
	( 35, N'Fanny Margoth', N'Cañar Caisalitin', 'f', N'64122519575', N'Trabajador'),
	( 36, N'Jorge Alfonso', N'Llerena Vargas', 'm', N'76032257421', N'Trabajador')
GO

-- ESTUDIANTES
INSERT INTO [dbo].[persona] ([id], [nombre], [apellido], [sexo], [carnet], [tipo]) VALUES
	( 37, N'Alexandra Elizabeth', N'Carrion Córdova', 'f', N'05032394338', N'Estudiante'),
	( 38, N'Jorge Xavier', N'Paredes Lucas', 'm', N'06100346049', N'Estudiante'),
	( 39, N'Andrés Alberto', N'Suárez Pineda', 'm', N'01011788427', N'Estudiante'),
	( 40, N'Wilson Fernando', N'Montenegro Espinoza', 'm', N'05121525761', N'Estudiante'),
	( 41, N'Karol Narcisa', N'Macias Yanez', 'f', N'02011137116', N'Estudiante'),
	( 42, N'Elizzabeth Vanessa', N'De Ring Salvador', 'f', N'06021673071', N'Estudiante'),
	( 43, N'Luz Mariuxi', N'Murillo Calvache', 'f', N'02091027151', N'Estudiante'),
	( 44, N'Santander Rosero', N'Luis Enrique', 'm', N'01112526860', N'Estudiante'),
	( 45, N'Gladys Monserrate', N'Ordóñez Alemán', 'f', N'05051486014', N'Estudiante'),
	( 46, N'Maria Jose', N'Franco Pico', 'f', N'01061965833', N'Estudiante'),
	( 47, N'Diego Alejandro', N'Redroban Becerra', 'm', N'04092080501', N'Estudiante'),
	( 48, N'Angela Lucciola', N'Suarez Velasquez', 'f', N'05102048498', N'Estudiante'),
	( 49, N'Martha Fabiola', N'Rizzo Gonzalez', 'f', N'06091298833', N'Estudiante'),
	( 50, N'Carlos Dionisio', N'Sornoza Moran', 'm', N'02120755742', N'Estudiante'),
	( 51, N'Karen Gabriela', N'Romero Zavala', 'f', N'04101954771', N'Estudiante'),
	( 52, N'Marlon Roberto', N'Banegas Andrade', 'm', N'01011186884', N'Estudiante'),
	( 53, N'Douglas Alfredo', N'Plaza Galarza', 'm', N'02061281247', N'Estudiante'),
	( 54, N'Teodoro Ivan', N'Blakman Briones', 'm', N'01100144547', N'Estudiante'),
	( 55, N'Mireya Veronica', N'Choez Caicedo', 'f', N'01112288152', N'Estudiante'),
	( 56, N'Kurt Ernesto', N'Lainez Landi', 'm', N'03101439760', N'Estudiante'),
	( 57, N'Luis Gabriel', N'De Sousa Diaz', 'm', N'05111090166', N'Estudiante'),
	( 58, N'Jessica Rosa', N'Macio Cueva', 'f', N'02041375955', N'Estudiante'),
	( 59, N'Kerlin Gustavo', N'Yagual Arreaga', 'm', N'03030339569', N'Estudiante'),
	( 60, N'Jose Santiago', N'Lindao Gonzalez', 'm', N'01010576841', N'Estudiante'),
	( 61, N'Carlos Humberto', N'Viteri Torres', 'm', N'04061565685', N'Estudiante'),
	( 62, N'Carlos Alberto', N'Vaca Nuñez', 'm', N'05050240809', N'Estudiante'),
	( 63, N'Nina Dolores', N'Romero Delgado', 'f', N'03081534231', N'Estudiante'),
	( 64, N'Hilda Maria', N'Molina Lopez', 'f', N'04080235136', N'Estudiante'),
	( 65, N'Luis Fernando', N'Valarezo Lingen', 'm', N'05022226625', N'Estudiante'),
	( 66, N'Lilia Edith', N'Garcia Anchundia', 'f', N'01091895597', N'Estudiante'),
	( 67, N'Rosendo', N'Pinilla Bustamante', 'm', N'04092958123', N'Estudiante'),
	( 68, N'Timoteo', N'Pinilla Calderón', 'm', N'05060543228', N'Estudiante'),
	( 69, N'Sandalio', N'Pinto Alcázar', 'm', N'04031443303', N'Estudiante'),
	( 70, N'Federico', N'Pizarro Coello', 'm', N'03010520044', N'Estudiante'),
	( 71, N'Eugenio', N'Pla Alegre', 'm', N'04021160367', N'Estudiante'),
	( 72, N'Urbano', N'Plana Cornejo', 'm', N'07040427065', N'Estudiante'),
	( 73, N'Dimas Rafael', N'Polo Bravo', 'm', N'00080561087', N'Estudiante'),
	( 74, N'Leoncio', N'Pombo Nadal', 'm', N'01052912505', N'Estudiante'),
	( 75, N'Leyre', N'Ponce Quintana', 'm', N'09032391987', N'Estudiante'),
	( 76, N'Elpidio', N'Pont Expósito', 'm', N'09122350388', N'Estudiante'),
	( 77, N'Nilo', N'Porras Casas', 'm', N'06120598508', N'Estudiante'),
	( 78, N'Andrés Felipe', N'Prado Infante', 'm', N'03032273247', N'Estudiante'),
	( 79, N'Ciro Hernando', N'Puga Patiño', 'm', N'09061075029', N'Estudiante'),
	( 80, N'Héctor', N'Puga Tudela', 'm', N'02022432721', N'Estudiante'),
	( 81, N'Isaac', N'Pujol Yáñez', 'm', N'08022592243', N'Estudiante'),
	( 82, N'Amaro', N'Quero Paredes', 'm', N'00041298806', N'Estudiante'),
	( 83, N'Norberto Chucho', N'Raya Saldaña', 'm', N'09021957508', N'Estudiante'),
	( 84, N'Gastón', N'Real Barriga', 'm', N'06021351043', N'Estudiante'),
	( 85, N'Urbano', N'Real Tolosa', 'm', N'02090795423', N'Estudiante'),
	( 86, N'Josep', N'Reina Prieto', 'm', N'01011368406', N'Estudiante'),
	( 87, N'Leocadio', N'Requena Barriga', 'm', N'06040539402', N'Estudiante'),
	( 88, N'José', N'Reyes Leal', 'm', N'00032012604', N'Estudiante'),
	( 89, N'Matías', N'Rincón Roldan', 'm', N'05101772540', N'Estudiante'),
	( 90, N'Ibán', N'Rios Calvet', 'm', N'06090822104', N'Estudiante'),
	( 91, N'Goyo', N'Ríos Guerra', 'm', N'02041842287', N'Estudiante'),
	( 92, N'Basilio', N'Riquelme Gordillo', 'm', N'06062166608', N'Estudiante'),
	( 93, N'Alberto', N'Rius Rius', 'm', N'02031023945', N'Estudiante'),
	( 94, N'Josué', N'Rocha Juan', 'm', N'03013188362', N'Estudiante'),
	( 95, N'Amado', N'Rodríguez Girón', 'm', N'08010481360', N'Estudiante'),
	( 96, N'Sosimo', N'Roig Bayo', 'm', N'08042639440', N'Estudiante'),
	( 97, N'Ruy', N'Roldan Lara', 'm', N'02092845049', N'Estudiante'),
	( 98, N'Manolo Isidro', N'Ros Salgado', 'm', N'09052753221', N'Estudiante'),
	( 99, N'Urbano', N'Rosselló Llabrés', 'm', N'00030350986', N'Estudiante'),
	( 100, N'Isidro', N'Rozas Gómez', 'm', N'08050547040', N'Estudiante'),
	( 101, N'Elías', N'Sacristán Vázquez', 'm', N'08013166429', N'Estudiante'),
	( 102, N'Cecilio Apolinar', N'Salas Alemán', 'm', N'08123077047', N'Estudiante'),
	( 103, N'Alberto', N'Salas Báez', 'm', N'01081923845', N'Estudiante'),
	( 104, N'Isidoro', N'Salas Marqués', 'm', N'00100449127', N'Estudiante'),
	( 105, N'Eladio', N'Salcedo Cabanillas', 'm', N'03123147703', N'Estudiante'),
	( 106, N'Jose Luis', N'Salgado Monreal', 'm', N'05081377503', N'Estudiante'),
	( 107, N'Sergio', N'Salmerón Carreras', 'm', N'02012231543', N'Estudiante'),
	( 108, N'Josué', N'Salom González', 'm', N'08100938684', N'Estudiante'),
	( 109, N'Marco', N'Sanchez Yuste', 'm', N'03030889249', N'Estudiante'),
	( 110, N'Pedro', N'Sancho Garrido', 'm', N'00050146422', N'Estudiante'),
	( 111, N'Gonzalo', N'Sans Miró', 'm', N'00050855265', N'Estudiante'),
	( 112, N'Jaime', N'Santiago Vazquez', 'm', N'09041585182', N'Estudiante'),
	( 113, N'Benigno', N'Sanz Gomis', 'm', N'08052028224', N'Estudiante'),
	( 114, N'Venceslás', N'Sastre Peñalver', 'm', N'08090363643', N'Estudiante'),
	( 115, N'Jordi', N'Seco Cantón', 'm', N'06070191487', N'Estudiante'),
	( 116, N'Eutropio', N'Sedano Cabanillas', 'm', N'07100357443', N'Estudiante'),
	( 117, N'Victor Manuel', N'Sedano Domingo', 'm', N'01033177342', N'Estudiante'),
	( 118, N'Pepito', N'Sedano Pou', 'm', N'05042453864', N'Estudiante'),
	( 119, N'Curro', N'Segarra Peinado', 'm', N'00041949609', N'Estudiante'),
	( 120, N'Leonel', N'Sierra Fajardo', 'm', N'00121292742', N'Estudiante'),
	( 121, N'Sigfrido', N'Simó Benavent', 'm', N'07111164182', N'Estudiante'),
	( 122, N'Juan Antonio', N'Solana Torrijos', 'm', N'02100290222', N'Estudiante'),
	( 123, N'Édgar Manu', N'Suárez Anglada', 'm', N'05052331549', N'Estudiante'),
	( 124, N'Hugo', N'Tamarit Hoz', 'm', N'00050113769', N'Estudiante'),
	( 125, N'Vasco', N'Tamayo Barrio', 'm', N'04010723166', N'Estudiante'),
	( 126, N'Victoriano', N'Tapia Cabanillas', 'm', N'01030679668', N'Estudiante'),
	( 127, N'Victor Manuel', N'Tejedor Nevado', 'm', N'06110657501', N'Estudiante'),
	( 128, N'Luis Ángel', N'Téllez Conde', 'm', N'01030354747', N'Estudiante'),
	( 129, N'Máximo', N'Téllez Ferrera', 'm', N'07100774366', N'Estudiante'),
	( 130, N'Baltasar', N'Téllez Vargas', 'm', N'05010834583', N'Estudiante'),
	( 131, N'Alcides', N'Tello Fernandez', 'm', N'09031068065', N'Estudiante'),
	( 132, N'Rodolfo Duilio', N'Terrón Serrano', 'm', N'03122019904', N'Estudiante'),
	( 133, N'Yago', N'Teruel Palomino', 'm', N'06112069287', N'Estudiante'),
	( 134, N'Roberto', N'Tomás Bermúdez', 'm', N'00061998800', N'Estudiante'),
	( 135, N'Damián', N'Tomas Solís', 'm', N'01050179685', N'Estudiante'),
	( 136, N'Anacleto', N'Tomás Torrecilla', 'm', N'09041970662', N'Estudiante'),
	( 137, N'Gerardo', N'Tomá Real', 'm', N'07060574389', N'Estudiante'),
	( 138, N'Hugo', N'Torrecilla Canals', 'm', N'03091590427', N'Estudiante'),
	( 139, N'Jonatan', N'Trillo Larrañaga', 'm', N'01050545567', N'Estudiante'),
	( 140, N'Abel', N'Uribe Cerro', 'm', N'00061631407', N'Estudiante'),
	( 141, N'Sigfrido', N'Urrutia Vargas', 'm', N'01022472085', N'Estudiante'),
	( 142, N'Juan Bautista', N'Valderrama Méndez', 'm', N'01052377727', N'Estudiante'),
	( 143, N'Victoriano', N'Valencia Coloma', 'm', N'08060598126', N'Estudiante'),
	( 144, N'Jose', N'Valenciano Osuna', 'm', N'04071412349', N'Estudiante'),
	( 145, N'Valerio', N'Valentín Céspedes', 'm', N'09050514842', N'Estudiante'),
	( 146, N'Carmelo', N'Valentín Mancebo', 'm', N'07010837047', N'Estudiante'),
	( 147, N'Isaac', N'Vallejo Coll', 'm', N'06061188741', N'Estudiante'),
	( 148, N'Natanael', N'Vallés Prieto', 'm', N'08112858462', N'Estudiante'),
	( 149, N'Esteban', N'Vaquero Dominguez', 'm', N'00080317424', N'Estudiante'),
	( 150, N'Quirino Rosario', N'Vázquez Carvajal', 'm', N'00011789545', N'Estudiante'),
	( 151, N'Manu', N'Vega Girona', 'm', N'00061011241', N'Estudiante'),
	( 152, N'José Ángel', N'Vélez Vila', 'm', N'08011425141', N'Estudiante'),
	( 153, N'Lucas', N'Vendrell Valverde', 'm', N'07121186389', N'Estudiante'),
	( 154, N'Alcides', N'Viana Benavente', 'm', N'08091550202', N'Estudiante'),
	( 155, N'Damián', N'Vicens Cabrera', 'm', N'05110316782', N'Estudiante'),
	( 156, N'Jerónimo', N'Vidal Hernandez', 'm', N'01100540969', N'Estudiante'),
	( 157, N'Eustaquio', N'Vigil Gil', 'm', N'03032892061', N'Estudiante'),
	( 158, N'Maximino', N'Vigil Morante', 'm', N'05060587629', N'Estudiante'),
	( 159, N'Jose Ignacio', N'Vila Rios', 'm', N'01040682009', N'Estudiante'),
	( 160, N'Silvio', N'Vilanova Amador', 'm', N'06101520605', N'Estudiante'),
	( 161, N'Román', N'Villa Marqués', 'm', N'03122421525', N'Estudiante'),
	( 162, N'Remigio', N'Villalonga Roman', 'm', N'00102956565', N'Estudiante'),
	( 163, N'Miguel', N'Villegas Jover', 'm', N'02043082529', N'Estudiante'),
	( 164, N'Desiderio', N'Vizcaíno Seguí', 'm', N'02101483786', N'Estudiante'),
	( 165, N'Plácido', N'Zabala Feijoo', 'm', N'09011988483', N'Estudiante'),
	( 166, N'Rafael Edu', N'Zamorano Ochoa', 'm', N'03100954346', N'Estudiante'),
	( 167, N'Santos', N'Zurita Barrio', 'm', N'01100643902', N'Estudiante')
GO
SET IDENTITY_INSERT [dbo].[persona] OFF
GO