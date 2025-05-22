package logica.test.logica;

import logica.excepciones.EntradaInvalidaException;
import model.TurnoDeGuardia;
import org.junit.BeforeClass;
import org.junit.Test;
import services.Facultad;
import services.Gestor;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class GestorTest {

    // Cuando programa empieza abre con crearPlantilla, que llama a getHorariosDeFecha
    // Despues se llama, mes a mes a crearProximaPlantilla
    // crearPlanificacionAutomaticamente: Llena la plantilla del mes,
    //		llama a getPersonasDisponibles y a asignarPersona
    //  asignarPersona
    //	guardar
    //	borrarPersonaDeTurno
    //	borrarPersonasDeDia

    //	cancelarPlanificacion
    //	darBaja
    //	intercambiarTurnos

    private static Gestor gestor;

    @BeforeClass
    public static void inicializarListaPersonas() {
        gestor = Gestor.getInstance();
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
//            facultad.annadirEstudiante("06091956525", "Enrique Sigifredo", "Centeno Mendoza", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03121368195", "Ileana Azucena", "Luna Veliz", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04042031731", "Carmen Lucila", "Jordan Medina", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01061115387", "Ricardo Javier", "Ruilova Elizalde", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04081337042", "Jaime Daniel", "Santos Balon", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06040824680", "Roman Fernando", "Arias Cede�o", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04020964790", "Virginia Azucena", "Trivi�o Bonilla", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04090785292", "Ana Alegria", "Mero Delgado", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04101759502", "Angel Leon", "Coloma Carrasco", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04080841013", "Sylvia Ines", "Escalante Hernandez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("03070968084", "Mario Francisco", "Cuvi Santacruz", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01011349819", "Maria Jose", "Friere Almeida", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06090627399", "Jenny Soraya", "Mero O�a", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06072141424", "Pablo Roberto", "Chandi Ulcuango", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05062161001", "Hugo Fabricio", "Navarro Villac�s", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05092325728", "Jos� Bol�var", "Reina Narv�ez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06052397819", "Monica Cristina", "Guaman Guaman", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02072835004", "Pablo Sa�l", "Sol�rzano Salinas", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06040242195", "Maria Belen", "Loor Segovia", Sexo.FEMENINO);
//            facultad.annadirEstudiante("03050684340", "Tem�stocles Genaro", "Ortega Cevallos", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03041045030", "Karina Annabell", "Chamorro Cede�o", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01091473630", "Marcia Elizabeth", "Mora Pe�arrita", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01052651307", "Jaime Jahnny", "Villacreses Garcia", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06021436949", "Edgar Alpidio", "Pe�afiel Ruiz", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04051671256", "Diana Esperanza", "Delgado Rodriguez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02110675233", "Fatima Monserrate", "Mendoza Ramos", Sexo.FEMENINO);
//            facultad.annadirEstudiante("05042462517", "Ana Del Roc�o", "Pilay Tejena", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02051476141", "Andres Wilmer", "Gonzalez Sanchez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03031728761", "Iv�n Francisco", "Rivadeneira Torres", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02010176300", "Edison Mauricio", "Villavicencio Orellana", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03022221381", "Mario Francisco", "Jativa Reyes", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04071168892", "Veronica Carolina", "R�os Merchan", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06062472907", "Gabriel Alejandro", "Ram�rez Rosales", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02060790586", "Pablo Ren�n", "Vallejo Aristiz�bal", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06092275765", "Washington Bol�var", "Y�nez L�pez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06040328340", "Freddy Whimper", "Torres Flores", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04081243467", "C�sar Mauricio", "Macas Chulde", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04072550136", "Elsa Del Carmen", "Goyes Aguilar", Sexo.FEMENINO);
//            facultad.annadirEstudiante("03110777234", "Karina Elizabeth", "Nieto Suarez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("05032335366", "Andr�s Esteban", "Aguilar Viteri", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03041348320", "Vladimir Rafael", "Moreno Negrete", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02041447919", "Katteryne Esilda", "Prado S�nchez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("05102429339", "Lady Gabriela", "Luna Sanchez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01110726315", "Wilma Soraya", "Vargas Caiza", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04090449503", "Carlos Emilio", "Figueroa Cepeda", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04072489980", "Doubosky Delos", "Marquez Mantilla", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06051516076", "Rita Ellena", "Almeida Shap�n", Sexo.FEMENINO);
//            facultad.annadirEstudiante("05040613608", "Jaime Patricio", "Reyes Garces", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04032092749", "Nelson Fabian", "Carrion Villavicencio", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06021777665", "Fidel Antonio", "Viscaino Burgos", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01051135591", "Nancy Isabel", "Escobar Paredes", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06020727015", "Ginna Carmita", "Suarez Cruz", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06072736444", "Diego Adalberto", "Hernandez Pinos", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06112393497", "Alba Cumanda", "Mina D Ela Cruz", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02092055502", "Williams Juli�n", "Sanch�z D�az", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03012260035", "Evelyn Geraldine", "Suarez Salazar", Sexo.FEMENINO);
//            facultad.annadirEstudiante("05101547610", "Cecilia Elizabeth", "Coba P�rez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02020569695", "Karina Del Rocio", "Verdesoto Moreira", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02061563102", "Hector Patricio", "Tapia Ramirez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03111981538", "Andrea Maria", "Garcia Benitez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("05092140972", "Sahara Mishell", "Mera Cede�o", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02091973037", "Silvia Susana", "Baldeon Loza", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06111068649", "Pa�l Vinicio", "Dom�inguez Berr�", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01061767532", "Diana Alejandra", "Mijas Castillo", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06111363785", "Juan Carlos", "Aguas Ortiz", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01122862276", "Erika Paola", "Coronel Salguero", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01090778535", "Maria Cecilia", "Naula Mullo", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04031839219", "Ximena Elizabeth", "Cayambe Badillo", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04031326942", "Wilfrido Efren", "Guaoto Pillalaza", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05072055380", "Dalai Piragov", "Silva Narvaez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03070156023", "Roger Andr�s", "Vallejo P�rez", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01021564723", "Luis Alberto", "Andrango Cadena", Sexo.MASCULINO);
//            facultad.annadirEstudiante("06070435514", "Maria Esther", "Barragan Chiluisa", Sexo.FEMENINO);
//            facultad.annadirEstudiante("03101498015", "Sonia Lilian", "Bermeo Aynaguano", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01120420483", "Diego German", "Yanez Molina", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03062039024", "Wilson An�bal", "L�pez Sevilla", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01121434806", "Pablo Fernando", "Saltos Galarza", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04030583421", "Andres Mauricio", "Castillo Aucancela", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02050328496", "Karen Viviana", "Luzardo Alarcon", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04052212386", "Andr�s Patricio", "Valarezo D�az", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04032120466", "Francisco Eduardo", "Y�pez Cadena", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05060155136", "Stephanie Judith", "Badillo Herrera", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01120585412", "Ana Milena", "Arbel�ez Hurtado", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04091231486", "Edwin Iv�n", "Revelo Revelo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02031341765", "Victor Hugo", "Corrales Tapia", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04060889444", "Edmundo Roberto", "S�nchez Cruz", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04110143453", "Betty Consuelo", "B�ez Villag�mez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("05082078434", "Veronica Cecilia", "Medina Niama", Sexo.FEMENINO);
//            facultad.annadirEstudiante("06020397226", "Segundo Jose", "Chimbo Chimbo", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02092126727", "Jose Estuardo", "Cevallos Serrano", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02122469762", "Luis Herminio", "Mullo Cepeda", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01121334911", "Jassmin Rocio", "Cuenca Flores", Sexo.FEMENINO);
//            facultad.annadirEstudiante("03031069492", "Maria Fernanda", "Jaramillo Borja", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01060368575", "Mar�a Bel�n", "Rocha D�az", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04071854086", "Juan Jose", "Montufar Marcayata", Sexo.MASCULINO);
//            facultad.annadirEstudiante("02111048204", "Alexis Sandino", "Ortega Bone", Sexo.MASCULINO);
//            facultad.annadirEstudiante("05022772907", "Tirso Ra�l", "Torres Lovato", Sexo.MASCULINO);
//            facultad.annadirEstudiante("04021644391", "Ruth Maribel", "Almeida Cortez", Sexo.FEMENINO);
//            facultad.annadirEstudiante("04110438473", "Tania Patricia", "Andrade Ullauri", Sexo.FEMENINO);
//            facultad.annadirEstudiante("01091890861", "Joseph Humberto", "Travez Villalba", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01060193246", "Cristian Mauricio", "Tello Moreno", Sexo.MASCULINO);
//            facultad.annadirEstudiante("01120434860", "Xavier Eduardo", "J�piter Coronel", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03081080870", "Maria Antonieta", "Guarderas Celi", Sexo.FEMENINO);
//            facultad.annadirEstudiante("03052448688", "Edison Hermogenes", "Vera Vera", Sexo.MASCULINO);
//            facultad.annadirEstudiante("03102276532", "Marlin Jenit", "Charcopa Pereira", Sexo.FEMENINO);
//            facultad.annadirEstudiante("03051669636", "Monica Mireya", "Mojarrango Mera", Sexo.FEMENINO);
//            facultad.annadirEstudiante("02060229203", "Juan Santiago", "Morocho Llangari", Sexo.MASCULINO);
//        } catch (MultiplesErroresException | EntradaInvalidaException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
    }


    @Test
    public void crearPlantilla_true() throws EntradaInvalidaException {
        ArrayList<DiaGuardia> planDeGuardia = new ArrayList<DiaGuardia>();
        planDeGuardia = gestor.crearPlantilla(true);
        assertTrue(planDeGuardia.size() > 0);
        System.out.println("crearPlantilla_true " + planDeGuardia.size());
        mostrarPlanDeGuardia(planDeGuardia);
    }

    @Test
    public void crearPlantilla_false() throws EntradaInvalidaException {
        ArrayList<DiaGuardia> planDeGuardia = new ArrayList<DiaGuardia>();
        planDeGuardia = gestor.crearPlantilla(false);
        assertTrue(planDeGuardia.size() > 0);
        System.out.println("crearPlantilla_false " + planDeGuardia.size());
        mostrarPlanDeGuardia(planDeGuardia);

    }

    private void mostrarPlanDeGuardia(ArrayList<DiaGuardia> planDeGuardia) {
        for (DiaGuardia guardia : planDeGuardia) {
            for (TurnoDeGuardia turno : guardia.getTurnos()) {
                System.out.printf("%1$s %2$s%n", guardia.getFecha(), turno.getHorario());
            }
        }
    }

//	@Test
//	public void crearPlanificacionAutomaticamente_success() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		ArrayList<DiaGuardia> planDeGuardia = gestor.crearPlantilla(true);
//		gestor.crearPlanificacionAutomaticamente(planDeGuardia);
//		mostrarPlanDeGuardia(planDeGuardia);
//		assertFalse(validaError);
//	}
//
//	private void mostrarPlanDeGuardia(ArrayList<DiaGuardia> planDeGuardia){
//		for(DiaGuardia guardia: planDeGuardia){
//			for(TurnoGuardia turno: guardia.getTurnos()){
//				System.out.println(String.format("%1$s %2$s %3$s %4$s", guardia.getFecha(), turno.getHorario().getText(), turno.getPersonaAsignada() != null ? turno.getPersonaAsignada().getNombre() : "", turno.getPersonaAsignada() != null ? turno.getPersonaAsignada().getApellidos() : ""));
//			}
//		}
//	}
//	
//	
//	@Test
//	public void guardar_nuevosDiasNull() throws EntradaInvalidaException{
//		boolean validaError = false;
//		try {
//			gestor.guardar(null);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("No hay d�as para guardar.");
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void guardar_nuevosDiasEmpty() throws EntradaInvalidaException{
//		boolean validaError = false;
//		try {
//			gestor.guardar(new ArrayList<DiaGuardia>());
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("No hay d�as para guardar.");
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void guardar_nuevosDiasSinTocar() throws EntradaInvalidaException{
//		boolean validaError = false;
//		ArrayList<DiaGuardia> planDeGuardia = gestor.crearPlantilla(true);
//		try {
//			gestor.guardar(planDeGuardia);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("Todo d�a lectivo debe tener una persona asignada.");
//		}
//		assertTrue(validaError);
//	}
//
//
//	@Test
//	public void guardar_nuevosDiasParcial() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		ArrayList<DiaGuardia> planDeGuardia = gestor.crearPlantilla(true);
//		gestor.asignarPersona(planDeGuardia.get(0).getFecha(), planDeGuardia.get(0).getTurnos().get(0).getHorario(), gestor.getFacultad().getPersonas().get(0));
//		try {
//			gestor.guardar(planDeGuardia);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("Todo d�a lectivo debe tener una persona asignada.");
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void guardar_nuevosDiasTodos() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		ArrayList<DiaGuardia> planDeGuardia = gestor.crearPlantilla(true);
//		gestor.crearPlanificacionAutomaticamente(planDeGuardia);
//		try {
//			gestor.guardar(planDeGuardia);
//		} catch (EntradaInvalidaException e) {
//			validaError = true;
//		}
//		assertFalse(validaError);
//	}
//
//	@Test
//	public void guardar_nuevosDiasTodosMenosUnoShouldThrowEiException() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		ArrayList<DiaGuardia> planDeGuardia = gestor.crearPlantilla(true);
//		gestor.crearPlanificacionAutomaticamente(planDeGuardia);
//		planDeGuardia.get(0).getTurnos().get(0).asignarPersona(null);
//		try {
//			gestor.guardar(planDeGuardia);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("Todo d�a lectivo debe tener una persona asignada.");
//		}
//		assertTrue(validaError);
//	}
//
//	//======================== borrarPersonasDeDia ==========================================
//	
//	@Test
//	public void borrarPersonasDeDia_fechaEmptyShouldThrowEiException() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		try {
//			gestor.borrarPersonasDeDia(null);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("No hay planificaci�n para esta fecha.");
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void borrarPersonasDeDia_fechaInexistenteShouldThrowEiException() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		LocalDate fecha = LocalDate.of(2024, 12, 26);
//		try {
//			gestor.borrarPersonasDeDia(fecha);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("No hay planificaci�n para esta fecha.");
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void borrarPersonasDeDia_success() throws MultiplesErroresException, EntradaInvalidaException{
//		boolean success = true;
//		ArrayList<DiaGuardia> planDeGuardia = gestor.crearPlantilla(true);
//		gestor.crearPlanificacionAutomaticamente(planDeGuardia);
//		gestor.guardar(planDeGuardia);
//		LocalDate fecha = LocalDate.of(2024, 9, 30);
//		try {
//			gestor.borrarPersonasDeDia(fecha);
//		} catch (Exception e) {
//			success = false;
//		}
//		assertTrue(success);
//	}
//
//	//======================== borrarPersonasDeTurno ==========================================
//	
//	@Test
//	public void borrarPersonasDeTurno_fechaEmptyShouldThrowEiException() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		try {
//			gestor.borrarPersonaDeTurno(null, null);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("No hay planificaci�n para esta fecha.");
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void borrarPersonasDeTurno_turnoEmptyShouldThrowEiException() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		LocalDate fecha = LocalDate.of(2024, 9, 30);
//		try {
//			gestor.borrarPersonaDeTurno(fecha, null);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("Horario no especificado.");
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void borrarPersonasDeTurno_fechaInexistenteShouldThrowEiException() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		LocalDate fecha = LocalDate.of(2024, 12, 26);
//		HorarioGuardia horario = HorarioGuardia.T20_8; 
//		try {
//			gestor.borrarPersonaDeTurno(fecha, horario);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("No hay planificaci�n para esta fecha.");
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void borrarPersonasDeTurno_turnoInexistenteShouldThrowEiException() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		LocalDate fecha = LocalDate.of(2024, 9, 30);
//		HorarioGuardia horario = HorarioGuardia.T8_20; 
//		try {
//			gestor.borrarPersonaDeTurno(fecha, horario);
//		} catch (EntradaInvalidaException e) {
//			validaError = e.getMessage().equals("Este d�a no tiene el horario deseado.");
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void borrarPersonasDeTurno_success() throws MultiplesErroresException, EntradaInvalidaException{
//		boolean success = true;
//		ArrayList<DiaGuardia> planDeGuardia = gestor.crearPlantilla(true);
//		gestor.crearPlanificacionAutomaticamente(planDeGuardia);
//		gestor.guardar(planDeGuardia);
//		LocalDate fecha = LocalDate.of(2024, 9, 30);
//		HorarioGuardia horario = HorarioGuardia.T20_8; 
//		try {
//			gestor.borrarPersonaDeTurno(fecha, horario);
//		} catch (Exception e) {
//			success = false;
//		}
//		assertTrue(success);
//	}
//	@Test
//	public void getDiasPorActualizar() throws EntradaInvalidaException, MultiplesErroresException{
//		boolean validaError = false;
//		ArrayList<DiaGuardia> planDeGuardia = gestor.crearPlantilla(true);
//		gestor.crearPlanificacionAutomaticamente(planDeGuardia);
//		gestor.guardar(planDeGuardia);
//		ArrayList<DiaGuardia> diasXact = gestor.getDiasPorActualizarCumplimiento();
//		mostrarDiasPorActualizar(diasXact);
//		assertFalse(validaError);
//	}
//	private void mostrarDiasPorActualizar(ArrayList<DiaGuardia> planDeGuardia){
//		for(DiaGuardia guardia: planDeGuardia){
//			for(TurnoGuardia turno: guardia.getTurnosPorActualizar()){
//				System.out.println(String.format("%1$s %2$s %3$s %4$s", guardia.getFecha(), turno.getHorario().getText(), turno.getPersonaAsignada() != null ? turno.getPersonaAsignada().getNombre() : "", turno.getPersonaAsignada() != null ? turno.getPersonaAsignada().getApellidos() : ""));
//			}
//		}
//	}
//
//    @Test
//    public void asignarPersona_fechaEmptyShouldThrowMEException() throws EntradaInvalidaException, MultiplesErroresException {
//        boolean validaError = false;
//        try {
//            gestor.asignarPersona(null, HorarioGuardia.T20_8, new Estudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO));
//        } catch (MultiplesErroresException e) {
//            validaError = e.getErrores().contains("D�a a planificar no especificado.");
//        }
//        assertTrue(validaError);
//    }

//    @Test
//    public void asignarPersona_fechaPasadaShouldThrowMEException() throws EntradaInvalidaException, MultiplesErroresException {
//        boolean validaError = false;
//        try {
//            ArrayList<DiaGuardia> dias = gestor.crearPlantilla(true);
//            Instant.now(Clock.fixed(Instant.parse("2025-08-22T10:00:00Z"), ZoneOffset.UTC));
//            gestor.asignarPersona(dias.get(9), HorarioGuardia.T20_8, new Estudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//
//            validaError = e.getMessage().contains("No se pueden hacer cambios a fechas pasadas.");
//        }
//        assertTrue(validaError);
//    }
//
//    @Test
//    public void asignarPersona_horarioEmptyShouldThrowMEException() throws EntradaInvalidaException, MultiplesErroresException {
//        boolean validaError = false;
//        try {
//            ArrayList<DiaGuardia> dias = gestor.crearPlantilla(false);
//            gestor.asignarPersona(dias.get(14), null, new Estudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO));
//        } catch (MultiplesErroresException e) {
//            validaError = e.getErrores().contains("Turno a planificar no especificado.");
//        }
//        assertTrue(validaError);
//    }
//
//    @Test
//    public void asignarPersona_personaEmptyShouldThrowMEException() throws EntradaInvalidaException, MultiplesErroresException {
//        boolean validaError = false;
//        try {
//            ArrayList<DiaGuardia> dias = gestor.crearPlantilla(false);
//            gestor.asignarPersona(dias.get(14), HorarioGuardia.T20_8, null);
//        } catch (MultiplesErroresException e) {
//            validaError = e.getErrores().contains("Persona a asignar no especificada.");
//        }
//        assertTrue(validaError);
//    }
//
//
//    @Test
//    public void asignarPersona_horarioIncorrectoShouldThrowEIException() throws EntradaInvalidaException, MultiplesErroresException {
//        boolean validaError = false;
//        try {
//            ArrayList<DiaGuardia> dias = gestor.crearPlantilla(false);
//            gestor.asignarPersona(dias.get(15), HorarioGuardia.T8_20, new Estudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO));
//        } catch (EntradaInvalidaException e) {
//            validaError = e.getMessage().equals("Este d�a no tiene el horario deseado.");
//        }
//        assertTrue(validaError);
//    }
//
//    @Test
//    public void asignarPersona_success() throws EntradaInvalidaException, MultiplesErroresException {
//        boolean validaError = false;
//        try {
//            ArrayList<DiaGuardia> dias = gestor.crearPlantilla(false);
//            gestor.asignarPersona(dias.get(11), HorarioGuardia.T20_8, new Estudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO));
//        } catch (EntradaInvalidaException e) {
//            System.out.println(e.getMessage());
//            validaError = true;
//        }
//        assertFalse(validaError);
//    }
}
