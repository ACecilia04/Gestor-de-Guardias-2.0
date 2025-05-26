package logica.principal;

public enum TipoLicencia {
    LICENCIA,
    ESTADIA_PROVINCIA,
    ESTADIA_EXTRANJERO;

    public static String[] getOpciones() {
        // Crear un array de Strings con los nombres de las constantes
        String[] opciones = new String[TipoLicencia.values().length];
        for (int i = 0; i < TipoLicencia.values().length; i++) {
            opciones[i] = TipoLicencia.values()[i].name();
        }
        return opciones;
    }

    public static TipoLicencia fromString(String tipo) {
        for (TipoLicencia licencia : TipoLicencia.values()) {
            if (licencia.name().equalsIgnoreCase(tipo)) {
                return licencia; // Retorna la coincidencia encontrada
            }
        }
        throw new IllegalArgumentException("Tipo de licencia no v�lido: " + tipo);
    }

}