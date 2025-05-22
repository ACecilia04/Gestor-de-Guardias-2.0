package logica.test.logica;

import logica.Facultad;
import logica.excepciones.EntradaInvalidaException;
import logica.excepciones.MultiplesErroresException;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

public class FacultadTest {

    @Test
    public void inicializarDatos() {
        Facultad facultad = new Facultad();
        boolean valido = true;

        try {

            facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO);
            facultad.annadirTrabajador("73050632847", "Byron David", "Cevallos Trujillo", Sexo.MASCULINO);
            facultad.annadirTrabajador("87051952603", "Gonzalo Luis", "Balcazar Campoverde", Sexo.MASCULINO);
            facultad.annadirTrabajador("78092587290", "Fernanda Noemi", "Campos Mendieta", Sexo.FEMENINO);
            facultad.annadirTrabajador("93020322844", "Jaime Eduardo", "C�rdenas Molina", Sexo.MASCULINO);
            facultad.annadirTrabajador("84092027046", "Carlos Daniel", "Villavicencio Pesantez", Sexo.MASCULINO);
            facultad.annadirTrabajador("78111051339", "Marlene Estefania", "Novillo Jara", Sexo.FEMENINO);
            facultad.annadirTrabajador("63040450933", "Johanna Sofia", "Perez Cabrera", Sexo.FEMENINO);
            facultad.annadirTrabajador("70071327412", "Martha Patricia", "Morales Harris", Sexo.FEMENINO);
            facultad.annadirTrabajador("85110233916", "Diana Luc�a", "I�iguez I�iguez", Sexo.FEMENINO);
            facultad.annadirTrabajador("73122830205", "Jaime Vicente", "Chuchuca Serrano", Sexo.MASCULINO);
            facultad.annadirTrabajador("91101144653", "Sahira Maribel", "Martinez Cepeda", Sexo.FEMENINO);
            facultad.annadirTrabajador("82021061482", "Xavier Eduardo", "Montalvo Aponte", Sexo.MASCULINO);
            facultad.annadirTrabajador("65021376166", "Alex Ruben", "Cobos Veloz", Sexo.MASCULINO);
            facultad.annadirTrabajador("66040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
            facultad.annadirTrabajador("74122662229", "Danilo Fernando", "Garc�a Garc�a", Sexo.MASCULINO);
            facultad.annadirTrabajador("86112273545", "Oswaldo Ernesto", "Lopez Bravo", Sexo.MASCULINO);
            facultad.annadirTrabajador("91122339065", "Juan Jose", "Ortega Vintimilla", Sexo.MASCULINO);
            facultad.annadirTrabajador("63110921162", "Geovanny Ramiro", "Cabezas Velasco", Sexo.MASCULINO);
            facultad.annadirTrabajador("74102594455", "Marlene Alexandra", "Narvaez Calvachi", Sexo.FEMENINO);
            facultad.annadirTrabajador("83022156066", "Lenin Eduardo", "Saguay Sanaguano", Sexo.MASCULINO);
            facultad.annadirTrabajador("94061345883", "Hern�n Sebasti�n", "L�pez Montero", Sexo.MASCULINO);
            facultad.annadirTrabajador("64101742842", "Pedro", "Guaraca Cu�as", Sexo.MASCULINO);
            facultad.annadirTrabajador("74061424753", "Raquel Alejandra", "Arcos Cabezas", Sexo.FEMENINO);
            facultad.annadirTrabajador("81010751413", "Georgina Piedad", "Panchez Hernandez", Sexo.FEMENINO);
            facultad.annadirTrabajador("92122025074", "Karina Monserrath", "Masache Alvarado", Sexo.FEMENINO);
            facultad.annadirTrabajador("62101372362", "Diego Javier", "Cajas Logro�o", Sexo.MASCULINO);
            facultad.annadirTrabajador("75031748220", "Ricardo Israel", "Valencia Cuvi�a", Sexo.MASCULINO);
            facultad.annadirTrabajador("86092649453", "Lilli Lucia", "Romero Pacheco", Sexo.FEMENINO);
            facultad.annadirTrabajador("94040884719", "Susana Del Rocio", "Andrade Soto", Sexo.FEMENINO);
            facultad.annadirTrabajador("61051126489", "Jos� Eliecer", "Chicaiza Ronquillo", Sexo.MASCULINO);
            facultad.annadirTrabajador("75090959743", "Edgar Guillerno", "Guasca Tulmo", Sexo.MASCULINO);
            facultad.annadirTrabajador("84111526245", "Gustavo Patricio", "Mena Zapata", Sexo.MASCULINO);
            facultad.annadirTrabajador("94010697432", "Lourdes Del Roc�o", "Lara Valencia", Sexo.FEMENINO);
            facultad.annadirTrabajador("64122519575", "Fanny Margoth", "Ca�ar Caisalitin", Sexo.FEMENINO);
            facultad.annadirTrabajador("76032257421", "Jorge Alfonso", "Llerena Vargas", Sexo.MASCULINO);

            facultad.annadirEstudiante("05032394338", "Alexandra Elizabeth", "Carrion C�rdova", Sexo.FEMENINO);
            facultad.annadirEstudiante("06100346049", "Jorge Xavier", "Paredes Lucas", Sexo.MASCULINO);
            facultad.annadirEstudiante("01011788427", "Andr�s Alberto", "Su�rez Pineda", Sexo.MASCULINO);
            facultad.annadirEstudiante("05121525761", "Wilson Fernando", "Montenegro Espinoza", Sexo.MASCULINO);
            facultad.annadirEstudiante("02011137116", "Karol Narcisa", "Macias Yanez", Sexo.FEMENINO);
            facultad.annadirEstudiante("06021673071", "Elizzabeth Vanessa", "De Ring Salvador", Sexo.FEMENINO);
            facultad.annadirEstudiante("02091027151", "Luz Mariuxi", "Murillo Calvache", Sexo.FEMENINO);
            facultad.annadirEstudiante("01112526860", "Santander Rosero", "Luis Enrique", Sexo.MASCULINO);
            facultad.annadirEstudiante("05051486014", "Gladys Monserrate", "Ord��ez Alem�n", Sexo.FEMENINO);
            facultad.annadirEstudiante("01061965833", "Maria Jose", "Franco Pico", Sexo.FEMENINO);
            facultad.annadirEstudiante("04092080501", "Diego Alejandro", "Redroban Becerra", Sexo.MASCULINO);
            facultad.annadirEstudiante("05102048498", "Angela Lucciola", "Suarez Velasquez", Sexo.FEMENINO);
            facultad.annadirEstudiante("06091298833", "Martha Fabiola", "Rizzo Gonzalez", Sexo.FEMENINO);
            facultad.annadirEstudiante("02120755742", "Carlos Dionisio", "Sornoza Moran", Sexo.MASCULINO);
            facultad.annadirEstudiante("04101954771", "Karen Gabriela", "Romero Zavala", Sexo.FEMENINO);
            facultad.annadirEstudiante("01011186884", "Marlon Roberto", "Banegas Andrade", Sexo.MASCULINO);
            facultad.annadirEstudiante("02061281247", "Douglas Alfredo", "Plaza Galarza", Sexo.MASCULINO);
            facultad.annadirEstudiante("01100144547", "Teodoro Ivan", "Blakman Briones", Sexo.MASCULINO);
            facultad.annadirEstudiante("01112288152", "Mireya Veronica", "Choez Caicedo", Sexo.FEMENINO);
            facultad.annadirEstudiante("03101439760", "Kurt Ernesto", "Lainez Landi", Sexo.MASCULINO);
            facultad.annadirEstudiante("05111090166", "Luis Gabriel", "De Sousa Diaz", Sexo.MASCULINO);
            facultad.annadirEstudiante("02041375955", "Jessica Rosa", "Macio Cueva", Sexo.FEMENINO);
            facultad.annadirEstudiante("03030339569", "Kerlin Gustavo", "Yagual Arreaga", Sexo.MASCULINO);
            facultad.annadirEstudiante("01010576841", "Jose Santiago", "Lindao Gonzalez", Sexo.MASCULINO);
            facultad.annadirEstudiante("04061565685", "Carlos Humberto", "Viteri Torres", Sexo.MASCULINO);
            facultad.annadirEstudiante("05050240809", "Carlos Alberto", "Vaca Nu�ez", Sexo.MASCULINO);
            facultad.annadirEstudiante("03081534231", "Nina Dolores", "Romero Delgado", Sexo.FEMENINO);
            facultad.annadirEstudiante("04080235136", "Hilda Maria", "Molina Lopez", Sexo.FEMENINO);
            facultad.annadirEstudiante("05022226625", "Luis Fernando", "Valarezo Lingen", Sexo.MASCULINO);
            facultad.annadirEstudiante("01091895597", "Lilia Edith", "Garcia Anchundia", Sexo.FEMENINO);
            facultad.annadirEstudiante("06091956525", "Enrique Sigifredo", "Centeno Mendoza", Sexo.MASCULINO);
            facultad.annadirEstudiante("03121368195", "Ileana Azucena", "Luna Veliz", Sexo.FEMENINO);
            facultad.annadirEstudiante("04042031731", "Carmen Lucila", "Jordan Medina", Sexo.FEMENINO);
            facultad.annadirEstudiante("01061115387", "Ricardo Javier", "Ruilova Elizalde", Sexo.MASCULINO);
            facultad.annadirEstudiante("04081337042", "Jaime Daniel", "Santos Balon", Sexo.MASCULINO);
            facultad.annadirEstudiante("06040824680", "Roman Fernando", "Arias Cede�o", Sexo.MASCULINO);
            facultad.annadirEstudiante("04020964790", "Virginia Azucena", "Trivi�o Bonilla", Sexo.FEMENINO);
            facultad.annadirEstudiante("04090785292", "Ana Alegria", "Mero Delgado", Sexo.FEMENINO);
            facultad.annadirEstudiante("04101759502", "Angel Leon", "Coloma Carrasco", Sexo.MASCULINO);
            facultad.annadirEstudiante("04080841013", "Sylvia Ines", "Escalante Hernandez", Sexo.FEMENINO);
            facultad.annadirEstudiante("03070968084", "Mario Francisco", "Cuvi Santacruz", Sexo.MASCULINO);
            facultad.annadirEstudiante("01011349819", "Maria Jose", "Friere Almeida", Sexo.FEMENINO);
            facultad.annadirEstudiante("06090627399", "Jenny Soraya", "Mero O�a", Sexo.FEMENINO);

        } catch (MultiplesErroresException e) {
            System.out.println(e.getErrores().get(0));
            e.printStackTrace();
            valido = false;
        } catch (EntradaInvalidaException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            valido = false;
        }
        assertTrue(valido);
    }

    @Test
    public void annadirEstudiante_NullCiShouldThrowMEException() throws EntradaInvalidaException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            //facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
            facultad.annadirEstudiante(null, "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no especificado.");
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_InvalidCiLongitudShouldThrowMEException() throws EntradaInvalidaException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            facultad.annadirEstudiante("0604087833712123", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no v�lido: Longitud incorrecta.");
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_InvalidCiCaracteresShouldThrowMEException() throws EntradaInvalidaException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            facultad.annadirEstudiante("060408783as", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no v�lido: Caracteres no num�ricos.");
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_InvalidCiMesShouldThrowMEException() throws EntradaInvalidaException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            facultad.annadirEstudiante("06130878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no v�lido: Mes incorrecto.");
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_InvalidCiDia1ShouldThrowMEException() throws EntradaInvalidaException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            facultad.annadirEstudiante("06043178337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no v�lido: Dia incorrecto.");
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_InvalidCiDia2ShouldThrowMEException() throws EntradaInvalidaException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            facultad.annadirEstudiante("06053278337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no v�lido: Dia incorrecto.");
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_InvalidCiDia3ShouldThrowMEException() throws EntradaInvalidaException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            facultad.annadirEstudiante("06023278337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Carnet de identidad no v�lido: Dia incorrecto.");
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_NullNombreShouldThrowMEException() throws EntradaInvalidaException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            //facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
            facultad.annadirEstudiante("06040878337", null, "Pazmi�o Arregui", Sexo.FEMENINO);
        } catch (MultiplesErroresException e) {
            validaError = (e.getErrores().contains("Nombre no especificado."));
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_NullApellidoShouldThrowMEException() throws EntradaInvalidaException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            //facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
            facultad.annadirEstudiante("06040878337", "Mireya Katerine", null, Sexo.FEMENINO);
        } catch (MultiplesErroresException e) {
            validaError = e.getErrores().contains("Apellido no especificado.");
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_CiDuplicadoShouldThrowEIException() throws MultiplesErroresException {
        Facultad facultad = new Facultad();
        boolean validaError = false;
        try {
            facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
            facultad.annadirEstudiante("06040878337", "Mireya Katerine1", "Pazmi�o Arregui1", Sexo.FEMENINO);
        } catch (EntradaInvalidaException e) {
            validaError = (e.getMessage().equals("Estudiante ya registrado."));
        }
        assertTrue(validaError);
    }

    @Test
    public void annadirEstudiante_Success() throws MultiplesErroresException, EntradaInvalidaException {
        Facultad facultad = new Facultad();
        facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
        assertTrue(facultad.buscarPersona("06040878337") != null);
    }


//	@Test
//	public void annadirTrabajador_EmptyCiShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		boolean validaError = false;
//		try {
//			//facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//			facultad.annadirTrabajador("", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Carnet de identidad no especificado."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void annadirTrabajador_EmptyNombreShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		boolean validaError = false;
//		try {
//			facultad.annadirTrabajador("70101787233", "", "L�pez Gonz�lez", Sexo.FEMENINO );
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Nombre no especificado."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void annadirTrabajador_EmptyApellidoShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		boolean validaError = false;
//		try {
//			facultad.annadirTrabajador("70101787233", "Mercedes Maria", "", Sexo.FEMENINO );
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Apellido no especificado."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void annadirTrabajador_EmptySexoShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		boolean validaError = false;
//		try {
//			facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", null );
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Sexo no especificado."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void annadirTrabajador_SexoNoCoincideCiShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		boolean validaError = false;
//		try {
//			facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.MASCULINO );
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Sexo seleccionado no coincide con la informaci�n del carnet de identidad."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void annadirTrabajador_CiDuplicadoShouldThrowEIException() throws MultiplesErroresException {
//		Facultad facultad = new Facultad();
//		boolean validaError = false;
//		try {
//			facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//			facultad.annadirTrabajador("70101787233", "Mercedes Maria1", "L�pez Gonz�lez1", Sexo.FEMENINO);
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Trabajador ya registrado."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void annadirTrabajador_NombreDuplicadoShouldThrowNothing() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		facultad.annadirTrabajador("70101787234", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		assertTrue(facultad != null);
//	}
//
//	@Test
//	public void annadirTrabajador_Success() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		assertTrue(facultad.buscarPersona("70101787233") != null);
//	}
//	
//	@Test
//	public void buscarPersona_Success() throws EntradaInvalidaException, MultiplesErroresException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		assertTrue(facultad.buscarPersona("70101787233") != null);
//	}
//
//	@Test(expected = EntradaInvalidaException.class)
//	public void buscarPersona_EmptyCiShouldThrowEIException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		facultad.buscarPersona(null);
//	}
//
//	@Test
//	public void eliminarPersona_PersonaInexistenteShouldThrowEIException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		boolean validaError = false;
//		try {
//			facultad.eliminarPersona("70101787234");
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Persona no registrada."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void eliminarPersona_PersonaExistenteDebeSerEliminada() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		facultad.eliminarPersona("70101787233");
//		assertTrue(facultad.buscarPersona("70101787233") == null);
//	}
//	
//	
//	
//	@Test
//	public void buscarRecesoDocente_Success() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin = LocalDate.of(2025, 01, 5);
//		facultad.annadirRecesoDocente(fechaInicio, fechaFin, "Fin de a�o");
//		assertTrue(facultad.buscarRecesoDocente(fechaInicio) != null);
//	}
//
//	@Test
//	public void annadirRecesoDocente_FechaInicioEmptyShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = null;
//		LocalDate fechaFin = LocalDate.of(2025, 01, 5);
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaInicio, fechaFin, "Fin de a�o");
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Fecha de inicio no especificada."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void annadirRecesoDocente_FechaFinEmptyShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin = null;
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaInicio, fechaFin, "Fin de a�o");
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Fecha de fin no especificada."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void annadirRecesoDocente_NombreEmptyShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin = LocalDate.of(2025, 01, 5);
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaInicio, fechaFin, null);
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Nombre no especificado."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void annadirRecesoDocente_NombreDuplicadoShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio1 = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin1 = LocalDate.of(2025, 01, 5);
//		LocalDate fechaInicio2 = LocalDate.of(2025, 12, 25);
//		LocalDate fechaFin2 = LocalDate.of(2026, 01, 5);
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaInicio1, fechaFin1, "Fin de a�o");
//			facultad.annadirRecesoDocente(fechaInicio2, fechaFin2, "Fin de a�o");
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Ya existe un receso docente con ese nombre."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void annadirRecesoDocente_FechaInicioIgualFinShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = LocalDate.of(2024, 12, 25);
//		// LocalDate fechaFin = LocalDate.of(2025, 01, 5);
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaInicio, fechaInicio, "Fin de a�o");
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Fecha de inicio coincide con fecha de fin."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void annadirRecesoDocente_FechaInicioDespuesFinShouldThrowMEException() throws EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin = LocalDate.of(2025, 01, 5);
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaFin, fechaInicio, "Fin de a�o");
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Fecha de fin precede fecha de inicio."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void annadirRecesoDocente_FechasInicio1Fin2SolapadasShouldThrowEiException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio1 = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin1 = LocalDate.of(2025, 01, 5);
//		LocalDate fechaInicio2 = LocalDate.of(2024, 12, 20);
//		LocalDate fechaFin2 = LocalDate.of(2024, 12, 26);
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaInicio1, fechaFin1, "Fin de a�o");
//			facultad.annadirRecesoDocente(fechaInicio2, fechaFin2, "Otro receso");
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Las fechas introducidas coinciden con un receso docente ya registrado."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void annadirRecesoDocente_FechasInicio2Fin1SolapadasShouldThrowEiException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio1 = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin1 = LocalDate.of(2025, 1, 5);
//		LocalDate fechaInicio2 = LocalDate.of(2025, 1, 4);
//		LocalDate fechaFin2 = LocalDate.of(2025, 1, 31);
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaInicio1, fechaFin1, "Fin de a�o");
//			facultad.annadirRecesoDocente(fechaInicio2, fechaFin2, "Otro receso");
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Las fechas introducidas coinciden con un receso docente ya registrado."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void annadirRecesoDocente_FechasSolapadas1ShouldThrowEiException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio1 = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin1 = LocalDate.of(2025, 1, 5);
//		LocalDate fechaInicio2 = LocalDate.of(2024, 12, 26);
//		LocalDate fechaFin2 = LocalDate.of(2025, 1, 2);
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaInicio1, fechaFin1, "Fin de a�o");
//			facultad.annadirRecesoDocente(fechaInicio2, fechaFin2, "Otro receso");
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Las fechas introducidas coinciden con un receso docente ya registrado."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void annadirRecesoDocente_FechasSolapadas2ShouldThrowEiException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio1 = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin1 = LocalDate.of(2025, 1, 5);
//		LocalDate fechaInicio2 = LocalDate.of(2024, 12, 20);
//		LocalDate fechaFin2 = LocalDate.of(2025, 1, 10);
//		boolean validaError = false;
//		try {
//			facultad.annadirRecesoDocente(fechaInicio1, fechaFin1, "Fin de a�o");
//			facultad.annadirRecesoDocente(fechaInicio2, fechaFin2, "Otro receso");
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Las fechas introducidas coinciden con un receso docente ya registrado."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void annadirRecesoDocente_Success() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin = LocalDate.of(2025, 01, 5);
//		facultad.annadirRecesoDocente(fechaInicio, fechaFin, "Fin de a�o");
//		assertTrue(facultad.buscarRecesoDocente(fechaInicio) != null);
//	}
//	
//	@Test
//	public void eliminarRecesoDocente_RecesoInexistenteShouldThrowEIException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin = LocalDate.of(2025, 01, 5);
//		facultad.annadirRecesoDocente(fechaInicio, fechaFin, "Fin de a�o");
//		boolean validaError = false;
//		try {
//			facultad.eliminarRecesoDocente(LocalDate.of(2025, 12, 25));
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Receso docente no registrado."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void eliminarRecesoDocente_RecesoExistenteDebeSerEliminado() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin = LocalDate.of(2025, 01, 5);
//		facultad.annadirRecesoDocente(fechaInicio, fechaFin, "Fin de a�o");
//		facultad.eliminarRecesoDocente(LocalDate.of(2024, 12, 25));
//		assertTrue(facultad.buscarRecesoDocente(LocalDate.of(2024, 12, 25)) == null);
//	}
//	
//
//	@Test
//	public void fechaEsRecesoDocente_Success() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		LocalDate fechaInicio = LocalDate.of(2024, 12, 25);
//		LocalDate fechaFin = LocalDate.of(2025, 01, 5);
//		facultad.annadirRecesoDocente(fechaInicio, fechaFin, "Fin de a�o");
//		assertTrue(facultad.fechaEsRecesoDocente(LocalDate.of(2024, 12, 25)));
//	}
//

    @Test
    public void darBaja_PersonaInexistenteShouldThrowEIException() throws MultiplesErroresException, EntradaInvalidaException {
        Facultad facultad = new Facultad();
        facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO);
        LocalDate fechaBaja = LocalDate.of(2024, 12, 25);
        boolean validaError = false;
        try {
            facultad.darBaja("70101787234", fechaBaja);
        } catch (EntradaInvalidaException e) {
            validaError = (e.getMessage().equals("Persona no registrada."));
        }
        assertTrue(validaError);
    }

    @Test
    public void darBaja_EmptyCiShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
        Facultad facultad = new Facultad();
        facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO);
        LocalDate fechaBaja = LocalDate.of(2024, 12, 25);
        boolean validaError = false;
        try {
            facultad.darBaja(null, fechaBaja);
        } catch (MultiplesErroresException e) {
            validaError = (e.getErrores().contains("Carnet de identidad no especificado."));
        }
        assertTrue(validaError);
    }

    @Test
    public void darBaja_EmptyFechaBajaShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
        Facultad facultad = new Facultad();
        facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO);
        boolean validaError = false;
        try {
            facultad.darBaja("70101787233", null);
        } catch (MultiplesErroresException e) {
            validaError = (e.getErrores().contains("Fecha de baja no especificada."));
        }
        assertTrue(validaError);
    }

    @Test
    public void darBaja_Success() throws MultiplesErroresException, EntradaInvalidaException {
        Facultad facultad = new Facultad();
        facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO);
        LocalDate fechaBaja = LocalDate.of(2024, 12, 25);
        facultad.darBaja("70101787233", fechaBaja);
        assertTrue(facultad.buscarPersona("70101787233").getFechaDeBaja().equals(fechaBaja));
    }


//	@Test
//	public void darLicenciaTrabajador_PersonaInexistenteShouldThrowEIException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaTrabajador(fechaInicio, fechaFin, "70101787234", TipoLicencia.ESTADIA_PROVINCIA);
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Trabajador no registrado."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void darLicenciaTrabajador_EmptyCiShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaTrabajador(fechaInicio, fechaFin, null, TipoLicencia.ESTADIA_PROVINCIA);
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Carnet de identidad no especificado."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void darLicenciaTrabajador_EmptyFechaInicioShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		//LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaTrabajador(null, fechaFin, "70101787233", TipoLicencia.ESTADIA_EXTRANJERO);
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Fecha de inicio no especificada."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void darLicenciaTrabajador_EmptyFechaFinShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		//LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaTrabajador(fechaInicio, null, "70101787233", TipoLicencia.LICENCIA);
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Fecha de fin no especificada."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void darLicenciaTrabajador_EmptyTipoLicenciaShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaTrabajador(fechaInicio, fechaFin, "70101787233", null);
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Tipo de licencia no especificado."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void darLicenciaTrabajador_Success() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirTrabajador("70101787233", "Mercedes Maria", "L�pez Gonz�lez", Sexo.FEMENINO );
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		facultad.darLicenciaTrabajador(fechaInicio, fechaFin, "70101787233", TipoLicencia.LICENCIA);
//		Trabajador trabajador = (Trabajador)facultad.buscarPersona("70101787233");
//		assertTrue(trabajador.buscarLicencia(fechaInicio) != null);
//	}
//
//
//	@Test
//	public void darLicenciaEstudiante_PersonaInexistenteShouldThrowEIException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaEstudiante(fechaInicio, fechaFin, "06040878338");
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Estudiante no registrado."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void darLicenciaEstudiante_EmptyCiShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaEstudiante(fechaInicio, fechaFin, null);
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Carnet de identidad no especificado."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void darLicenciaEstudiante_EmptyFechaInicioShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		//LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaEstudiante(null, fechaFin, "07040878337");
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Fecha de inicio no especificada."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void darLicenciaEstudiante_EmptyFechaFinShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		//LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaEstudiante(fechaInicio, null, "07040878337");
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Fecha de fin no especificada."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void darLicenciaEstudiante_Success() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		facultad.darLicenciaEstudiante(fechaInicio, fechaFin, "06040878337");
//		assertTrue(((Estudiante)facultad.buscarPersona("06040878337")).buscarLicencia(fechaInicio) != null);
//	}
//
//	@Test
//	public void darLicenciaEstudianteSinFechaFin_PersonaInexistenteShouldThrowEIException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaEstudiante(fechaInicio, "06040878338");
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage().equals("Estudiante no registrado."));
//		}
//		assertTrue(validaError);
//	}
//	
//	@Test
//	public void darLicenciaEstudianteSinFechaFin_EmptyCiShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaEstudiante(fechaInicio, null);
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Carnet de identidad no especificado."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void darLicenciaEstudianteSinFechaFin_EmptyFechaInicioShouldThrowMEException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		//LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		boolean validaError = false;
//		try {
//			facultad.darLicenciaEstudiante(null, "07040878337");
//		} catch (MultiplesErroresException e) {
//			validaError = (e.getErrores().contains("Fecha de inicio no especificada."));
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void darLicenciaEstudianteSinFechaFin_Success() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		facultad.darLicenciaEstudiante(fechaInicio, "06040878337");
//		assertTrue(((Estudiante)facultad.buscarPersona("06040878337")).buscarLicencia(fechaInicio) != null);
//	}
//	
//	
//	@Test
//	public void getPersonasDeLicencia_EmptyDateShouldThrowEIException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		facultad.darLicenciaEstudiante(fechaInicio, fechaFin, "06040878337");
//		boolean validaError = false;
//		try {
//			facultad.getPersonasDeLicencia(null);
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage() == "Fecha no especificada.");
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void getPersonasDeLicencia_Success() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaInicio = LocalDate.of(2024, 06, 25);
//		LocalDate fechaFin = LocalDate.of(2024, 12, 25);
//		facultad.darLicenciaEstudiante(fechaInicio, fechaFin, "06040878337");
//		List<Persona> personasDeLicencia = facultad.getPersonasDeLicencia(fechaInicio);
//		assertTrue(personasDeLicencia != null && personasDeLicencia.size() == 1);
//	}
//
//	@Test
//	public void getPersonasDeBaja_EmptyDateShouldThrowEIException() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaBaja = LocalDate.of(2024, 06, 25);
//		facultad.darBaja("06040878337", fechaBaja );
//		boolean validaError = false;
//		try {
//			facultad.getPersonasDeBaja(null);
//		} catch (EntradaInvalidaException e) {
//			validaError = (e.getMessage() == "Fecha no especificada.");
//		}
//		assertTrue(validaError);
//	}
//
//	@Test
//	public void getPersonasDeBaja_Success() throws MultiplesErroresException, EntradaInvalidaException {
//		Facultad facultad = new Facultad();
//		facultad.annadirEstudiante("06040878337", "Mireya Katerine", "Pazmi�o Arregui", Sexo.FEMENINO);
//		LocalDate fechaBaja = LocalDate.of(2024, 06, 25);
//		facultad.darBaja("06040878337", fechaBaja );
//		List<Persona> personasDeBaja = facultad.getPersonasDeBaja(fechaBaja);
//		assertTrue(personasDeBaja != null && personasDeBaja.size() == 1);
//	}
//
}