package program;

import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {
    // Pair of Integer and Pair; first Integer is the Pif Codification of the token
    // Second Pair represents the hashed ArrayList position and the index inside that specific ArrayList (kind of like a matrix)
    private List<Pair<Integer, Pair<Integer, Integer>>> programInternalForm = new ArrayList<>();

    public void add_element(Integer code, Pair<Integer, Integer> value) {
        Pair<Integer, Pair<Integer, Integer>> pair = new Pair<>(code, value);
        programInternalForm.add(pair);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Pair<Integer, Pair<Integer, Integer>> pair : programInternalForm) {
            result.append(pair.getKey()).append(" - (")
                    .append(pair.getValue().getKey()).append(", ")
                    .append(pair.getValue().getValue()).append(")\n");
        }
        return result.toString();
    }
}