package gui.secciones;

import logica.Facultad;
import logica.Gestor;
import logica.excepciones.EntradaInvalidaException;
import logica.excepciones.MultiplesErroresException;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            //*****INICIOOOO!!!*****
                            inicializarDatos();
                            Ventana.getInstance();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void inicializarDatos() {
        Gestor gestor = Gestor.getInstance();

        Facultad facultad = gestor.getFacultad();

//        try {
//
//            facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO);
//            facultad.annadirTrabajador("73050632847", "Byron David", "Cevallos Trujillo", Sexo.MASCULINO);
//            facultad.annadirTrabajador("87051952603", "Gonzalo Luis", "Balcazar Campoverde", Sexo.MASCULINO);
//            facultad.annadirTrabajador("78092587290", "Fernanda Noemi", "Campos Mendieta", Sexo.FEMENINO);
//            facultad.annadirTrabajador("93020322844", "Jaime Eduardo", "C�rdenas Molina", Sexo.MASCULINO);
//            facultad.annadirTrabajador("84092027046", "Carlos Daniel", "Villavicencio Pesantez", Sexo.MASCULINO);
//            facultad.annadirTrabajador("78111051339", "Marlene Estefania", "Novillo Jara", Sexo.FEMENINO);
//            facultad.annadirTrabajador("63040450933", "Johanna Sofia", "Perez Cabrera", Sexo.FEMENINO);
//            facultad.annadirTrabajador("70071327412", "Martha Patricia", "Morales Harris", Sexo.FEMENINO);
//            facultad.annadirTrabajador("85110233916", "Diana Luc�a", "I�iguez I�iguez", Sexo.FEMENINO);
//            facultad.annadirTrabajador("73122830205", "Jaime Vicente", "Chuchuca Serrano", Sexo.MASCULINO);
//            facultad.annadirTrabajador("91101144653", "Sahira Maribel", "Martinez Cepeda", Sexo.FEMENINO);
//            facultad.annadirTrabajador("82021061482", "Xavier Eduardo", "Montalvo Aponte", Sexo.MASCULINO);
//            facultad.annadirTrabajador("65021376166", "Alex Ruben", "Cobos Veloz", Sexo.MASCULINO);
//            facultad.annadirTrabajador("66040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//            facultad.annadirTrabajador("74122662229", "Danilo Fernando", "Garc�a Garc�a", Sexo.MASCULINO);
//            facultad.annadirTrabajador("86112273545", "Oswaldo Ernesto", "Lopez Bravo", Sexo.MASCULINO);
//            facultad.annadirTrabajador("91122339065", "Juan Jose", "Ortega Vintimilla", Sexo.MASCULINO);
//            facultad.annadirTrabajador("63110921162", "Geovanny Ramiro", "Cabezas Velasco", Sexo.MASCULINO);
//            facultad.annadirTrabajador("74102594455", "Marlene Alexandra", "Narvaez Calvachi", Sexo.FEMENINO);
//            facultad.annadirTrabajador("83022156066", "Lenin Eduardo", "Saguay Sanaguano", Sexo.MASCULINO);
//            facultad.annadirTrabajador("94061345883", "Hern�n Sebasti�n", "L�pez Montero", Sexo.MASCULINO);
//            facultad.annadirTrabajador("64101742842", "Pedro", "Guaraca Cu�as", Sexo.MASCULINO);
//            facultad.annadirTrabajador("74061424753", "Raquel Alejandra", "Arcos Cabezas", Sexo.FEMENINO);
//            facultad.annadirTrabajador("81010751413", "Georgina Piedad", "Panchez Hernandez", Sexo.FEMENINO);
//            facultad.annadirTrabajador("92122025074", "Karina Monserrath", "Masache Alvarado", Sexo.FEMENINO);
//            facultad.annadirTrabajador("62101372362", "Diego Javier", "Cajas Logro�o", Sexo.MASCULINO);
//            facultad.annadirTrabajador("75031748220", "Ricardo Israel", "Valencia Cuvi�a", Sexo.MASCULINO);
//            facultad.annadirTrabajador("86092649453", "Lilli Lucia", "Romero Pacheco", Sexo.FEMENINO);
//            facultad.annadirTrabajador("94040884719", "Susana Del Rocio", "Andrade Soto", Sexo.FEMENINO);
//            facultad.annadirTrabajador("61051126489", "Jos� Eliecer", "Chicaiza Ronquillo", Sexo.MASCULINO);
//            facultad.annadirTrabajador("75090959743", "Edgar Guillerno", "Guasca Tulmo", Sexo.MASCULINO);
//            facultad.annadirTrabajador("84111526245", "Gustavo Patricio", "Mena Zapata", Sexo.MASCULINO);
//            facultad.annadirTrabajador("94010697432", "Lourdes Del Roc�o", "Lara Valencia", Sexo.FEMENINO);
//            facultad.annadirTrabajador("64122519575", "Fanny Margoth", "Ca�ar Caisalitin", Sexo.FEMENINO);
//            facultad.annadirTrabajador("76032257421", "Jorge Alfonso", "Llerena Vargas", Sexo.MASCULINO);
//
//            facultad.annadirEstudiante("05032394338", "Alexandra Elizabeth", "Carrion C�rdova", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06100346049", "Jorge Xavier", "Paredes Lucas", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01011788427", "Andr�s Alberto", "Su�rez Pineda", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05121525761", "Wilson Fernando", "Montenegro Espinoza", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02011137116", "Karol Narcisa", "Macias Yanez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06021673071", "Elizzabeth Vanessa", "De Ring Salvador", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02091027151", "Luz Mariuxi", "Murillo Calvache", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01112526860", "Santander Rosero", "Luis Enrique", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05051486014", "Gladys Monserrate", "Ord��ez Alem�n", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01061965833", "Maria Jose", "Franco Pico", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04092080501", "Diego Alejandro", "Redroban Becerra", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05102048498", "Angela Lucciola", "Suarez Velasquez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06091298833", "Martha Fabiola", "Rizzo Gonzalez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02120755742", "Carlos Dionisio", "Sornoza Moran", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04101954771", "Karen Gabriela", "Romero Zavala", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01011186884", "Marlon Roberto", "Banegas Andrade", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02061281247", "Douglas Alfredo", "Plaza Galarza", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01100144547", "Teodoro Ivan", "Blakman Briones", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01112288152", "Mireya Veronica", "Choez Caicedo", Sexo.FEMENINO);
//            facultad.annadirEstudiante("03101439760", "Kurt Ernesto", "Lainez Landi", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05111090166", "Luis Gabriel", "De Sousa Diaz", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02041375955", "Jessica Rosa", "Macio Cueva", Sexo.FEMENINO);
//            facultad.annadirEstudiante("03030339569", "Kerlin Gustavo", "Yagual Arreaga", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01010576841", "Jose Santiago", "Lindao Gonzalez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04061565685", "Carlos Humberto", "Viteri Torres", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05050240809", "Carlos Alberto", "Vaca Nu�ez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03081534231", "Nina Dolores", "Romero Delgado", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04080235136", "Hilda Maria", "Molina Lopez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("05022226625", "Luis Fernando", "Valarezo Lingen", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01091895597", "Lilia Edith", "Garcia Anchundia", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04092958123", "Rosendo", "Pinilla Bustamante", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05060543228", "Timoteo", "Pinilla Calder�n", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04031443303", "Sandalio", "Pinto Alc�zar", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03010520044", "Federico", "Pizarro Coello", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04021160367", "Eugenio", "Pla Alegre", Sexo.MASCULINO);
//            facultad.annadirEstudiante("07040427065", "Urbano", "Plana Cornejo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00080561087", "Dimas Rafael", "Polo Bravo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01052912505", "Leoncio", "Pombo Nadal", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09032391987", "Leyre", "Ponce Quintana", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09122350388", "Elpidio", "Pont Exp�sito", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06120598508", "Nilo", "Porras Casas", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03032273247", "Andr�s Felipe", "Prado Infante", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09061075029", "Ciro Hernando", "Puga Pati�o", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02022432721", "H�ctor", "Puga Tudela", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08022592243", "Isaac", "Pujol Y��ez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00041298806", "Amaro", "Quero Paredes", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09021957508", "Norberto Chucho", "Raya Salda�a", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06021351043", "Gast�n", "Real Barriga", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02090795423", "Urbano", "Real Tolosa", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01011368406", "Josep", "Reina Prieto", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06040539402", "Leocadio", "Requena Barriga", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00032012604", "Jos�", "Reyes Leal", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05101772540", "Mat�as", "Rinc�n Roldan", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06090822104", "Ib�n", "Rios Calvet", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02041842287", "Goyo", "R�os Guerra", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06062166608", "Basilio", "Riquelme Gordillo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02031023945", "Alberto", "Rius Rius", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03013188362", "Josu�", "Rocha Juan", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08010481360", "Amado", "Rodr�guez Gir�n", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08042639440", "Sosimo", "Roig Bayo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02092845049", "Ruy", "Roldan Lara", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09052753221", "Manolo Isidro", "Ros Salgado", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00030350986", "Urbano", "Rossell� Llabr�s", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08050547040", "Isidro", "Rozas G�mez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08013166429", "El�as", "Sacrist�n V�zquez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08123077047", "Cecilio Apolinar", "Salas Alem�n", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01081923845", "Alberto", "Salas B�ez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00100449127", "Isidoro", "Salas Marqu�s", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03123147703", "Eladio", "Salcedo Cabanillas", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05081377503", "Jose Luis", "Salgado Monreal", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02012231543", "Sergio", "Salmer�n Carreras", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08100938684", "Josu�", "Salom Gonz�lez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03030889249", "Marco", "Sanchez Yuste", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00050146422", "Pedro", "Sancho Garrido", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00050855265", "Gonzalo", "Sans Mir�", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09041585182", "Jaime", "Santiago Vazquez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08052028224", "Benigno", "Sanz Gomis", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08090363643", "Vencesl�s", "Sastre Pe�alver", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06070191487", "Jordi", "Seco Cant�n", Sexo.MASCULINO);
//            facultad.annadirEstudiante("07100357443", "Eutropio", "Sedano Cabanillas", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01033177342", "Victor Manuel", "Sedano Domingo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05042453864", "Pepito", "Sedano Pou", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00041949609", "Curro", "Segarra Peinado", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00121292742", "Leonel", "Sierra Fajardo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("07111164182", "Sigfrido", "Sim� Benavent", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02100290222", "Juan Antonio", "Solana Torrijos", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05052331549", "�dgar Manu", "Su�rez Anglada", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00050113769", "Hugo", "Tamarit Hoz", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04010723166", "Vasco", "Tamayo Barrio", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01030679668", "Victoriano", "Tapia Cabanillas", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06110657501", "Victor Manuel", "Tejedor Nevado", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01030354747", "Luis �ngel", "T�llez Conde", Sexo.MASCULINO);
//            facultad.annadirEstudiante("07100774366", "M�ximo", "T�llez Ferrera", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05010834583", "Baltasar", "T�llez Vargas", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09031068065", "Alcides", "Tello Fernandez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03122019904", "Rodolfo Duilio", "Terr�n Serrano", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06112069287", "Yago", "Teruel Palomino", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00061998800", "Roberto", "Tom�s Berm�dez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01050179685", "Dami�n", "Tomas Sol�s", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09041970662", "Anacleto", "Tom�s Torrecilla", Sexo.MASCULINO);
//            facultad.annadirEstudiante("07060574389", "Gerardo", "Tom� Real", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03091590427", "Hugo", "Torrecilla Canals", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01050545567", "Jonatan", "Trillo Larra�aga", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00061631407", "Abel", "Uribe Cerro", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01022472085", "Sigfrido", "Urrutia Vargas", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01052377727", "Juan Bautista", "Valderrama M�ndez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08060598126", "Victoriano", "Valencia Coloma", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04071412349", "Jose", "Valenciano Osuna", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09050514842", "Valerio", "Valent�n C�spedes", Sexo.MASCULINO);
//            facultad.annadirEstudiante("07010837047", "Carmelo", "Valent�n Mancebo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06061188741", "Isaac", "Vallejo Coll", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08112858462", "Natanael", "Vall�s Prieto", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00080317424", "Esteban", "Vaquero Dominguez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00011789545", "Quirino Rosario", "V�zquez Carvajal", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00061011241", "Manu", "Vega Girona", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08011425141", "Jos� �ngel", "V�lez Vila", Sexo.MASCULINO);
//            facultad.annadirEstudiante("07121186389", "Lucas", "Vendrell Valverde", Sexo.MASCULINO);
//            facultad.annadirEstudiante("08091550202", "Alcides", "Viana Benavente", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05110316782", "Dami�n", "Vicens Cabrera", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01100540969", "Jer�nimo", "Vidal Hernandez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03032892061", "Eustaquio", "Vigil Gil", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05060587629", "Maximino", "Vigil Morante", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01040682009", "Jose Ignacio", "Vila Rios", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06101520605", "Silvio", "Vilanova Amador", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03122421525", "Rom�n", "Villa Marqu�s", Sexo.MASCULINO);
//            facultad.annadirEstudiante("00102956565", "Remigio", "Villalonga Roman", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02043082529", "Miguel", "Villegas Jover", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02101483786", "Desiderio", "Vizca�no Segu�", Sexo.MASCULINO);
//            facultad.annadirEstudiante("09011988483", "Pl�cido", "Zabala Feijoo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03100954346", "Rafael Edu", "Zamorano Ochoa", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01100643902", "Santos", "Zurita Barrio", Sexo.MASCULINO);
//
//        } catch (MultiplesErroresException | EntradaInvalidaException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

}
