package program;

import java.io.File;
import java.util.*;

public class FiniteAutomaton {
    public HashSet<String> states;
    public HashSet<String> alphabet;
    public HashSet<String> finalStates;
    public String initialState;
    public Map<Pair<String, String>, Set<String>> transitions;

    //transitions todo

    public FiniteAutomaton(String file) {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        finalStates = new HashSet<>();
        transitions = new HashMap<>();
        readFromFile(file);
    }

    public void readFromFile(String file) {
        try {
            File faFile = new File(file);
            Scanner faScanner = new Scanner(faFile);

            String statesLine = faScanner.nextLine();
            String[] stateElems = statesLine.split(" ");
            List<String> statesList = Arrays.asList(stateElems);
            states = new HashSet<>(statesList);

            String alphabetLine = faScanner.nextLine();
            String[] alphabetElems = alphabetLine.split(" ");
            List<String> alphabetList = Arrays.asList(alphabetElems);
            alphabet = new HashSet<>(alphabetList);

            initialState = faScanner.nextLine().trim();

            String finalStateLine = faScanner.nextLine();
            String[] finalStateElems = finalStateLine.split(" ");
            List<String> finalStateList = Arrays.asList(finalStateElems);
            finalStates = new HashSet<>(finalStateList);

            while(faScanner.hasNextLine()) {
                String line = faScanner.nextLine().trim();
                String[] elems = line.split(" ");

                if(states.contains(elems[0]) && alphabet.contains(elems[1]) && states.contains(elems[2])) {
                    Pair<String, String> transitionKey = new Pair<String, String>(elems[0], elems[1]);
                    if(!transitions.containsKey(transitionKey)) {
                        Set<String> transitionSet = new HashSet<>();
                        transitionSet.add(elems[2]);
                        transitions.put(transitionKey, transitionSet);
                    }
                    else {
                        transitions.get(transitionKey).add(elems[2]);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String buildStates() {
        StringBuilder builder = new StringBuilder();
        for(String state : this.states)
            builder.append(state).append(", ");
        return builder.toString();
    }

    public String buildAlphabet() {
        StringBuilder builder = new StringBuilder();
        for(String symbol : this.alphabet)
            builder.append(symbol).append(", ");
        return builder.toString();
    }

    public String buildFinalStates() {
        StringBuilder builder = new StringBuilder();
        for(String finalState : this.finalStates)
            builder.append(finalState).append(", ");
        return builder.toString();
    }

    public String buildInitialState() {
        return this.initialState;
    }

    public String buildTransitions() {
        StringBuilder builder = new StringBuilder();
        transitions.forEach((key, value) -> {
            builder.append("(").append(key.key).append(", ").append(key.value).append(") -> ").append(value).append("\n");
        });
        return builder.toString();
    }

    public boolean checkSequence(String sequence) {
        if(sequence.length() == 0) return finalStates.contains(initialState);

        String currentState = this.initialState;
        for(int i = 0; i < sequence.length(); ++i) {
            Pair<String, String> transitionKey = new Pair<>(currentState, String.valueOf(sequence.charAt(i)));
            if(transitions.containsKey(transitionKey)) {
                currentState = transitions.get(transitionKey).iterator().next();
            }
            else return false;
        }

        return finalStates.contains(currentState);
    }

    public boolean checkDFA() {

        for (Map.Entry<Pair<String, String>, Set<String>> set :
                transitions.entrySet()) {
            if(set.getValue().size() > 1)
                return false;
        }
        return true;
    }
}
