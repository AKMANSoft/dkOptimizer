public enum Position {
    STARTING_PITCHER("SP"),
    RELIEF_PITCHER("RP"),
    CATCHER("C"),
    BASE_1("1B"),
    BASE_2("2B"),
    BASE_3("3B"),
    SHORT_STOP("SS"),
    OUTFIELD("OF"),
    DESIGNATED_HITTER("DH");

    private String abbreviation;

    Position(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public static Position findByAbbreviationAndLeague(String abbreviation) {
        for (Position position : values()) {
            String[] positionAbbreviations = position.getAbbreviation().split(",");
            for (String currentPositionAbbreviation : positionAbbreviations) {
                if (currentPositionAbbreviation.equalsIgnoreCase(abbreviation)) {
                    return position;
                }
            }
        }
        return null;
    }
}
