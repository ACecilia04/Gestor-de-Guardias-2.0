package model;

public enum HorarioGuardia {
    T8_20("8:00-20:00"),
    T9_14("9:00-14:00"),
    T14_19("14:00-19:00"),
    T20_8("20:00-8:00");

    //para mostrar en la tabla
    //deberia ser final?
    private final String text;

    HorarioGuardia(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}