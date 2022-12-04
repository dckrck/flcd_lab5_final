package program;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Grammar {
    private Set<String> NonTerminals = new HashSet<>();
    private Set<String> Terminals = new HashSet<>();
    private final HashMap<String, Set<List<String>>> Productions = new HashMap<>();
    private String StartSymbol = "";

    public Grammar(String file) {
        readFile(file);
    }

    private void readFile(String file) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String currentLine = reader.readLine();
            String nonterminals[] = currentLine.split("=")[1].strip()
                    .replaceAll("\\{", "")
                    .replaceAll("}", "").strip().split(" ");
            this.NonTerminals = new HashSet<>(Arrays.asList(nonterminals));

            currentLine = reader.readLine();
            String terminals[] = currentLine.split("=", 2)[1].strip()
                    .replaceAll("\\{", "")
                    .replaceAll("}", "").strip().split(" ");
            this.Terminals = new HashSet<>(Arrays.asList(terminals));

            this.StartSymbol = reader.readLine().split("=")[1].strip();

            reader.readLine();
            String productionsLine = reader.readLine();
            while(productionsLine != null){
                if(!productionsLine.equals("}")) {
                    String[] tokens = productionsLine.split("->");
                    String lhs = tokens[0].strip();
                    String[] rhs = tokens[1].split("\\|");

                    if(!Productions.containsKey(lhs))
                        Productions.put(lhs, new HashSet<>());

                    for(String rhsToken : rhs) {
                        ArrayList<String> oneProduction = new ArrayList<>();
                        String[] rhsTokenSymbol = rhsToken.strip().split(" ");
                        for(String token : rhsTokenSymbol)
                            oneProduction.add(token.strip());
                        Productions.get(lhs).add(oneProduction);
                    }
                }
                productionsLine = reader.readLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String printNonTerminals() {
        StringBuilder builder = new StringBuilder("N = { ");
        for(String nt : NonTerminals)
            builder.append(nt).append(" ");
        builder.append("}");
        return builder.toString();
    }

    public String printTerminals() {
        StringBuilder builder = new StringBuilder("E = { ");
        for(String t : Terminals)
            builder.append(t).append(" ");
        builder.append("}");
        return builder.toString();
    }

    public String printProductions() {
        StringBuilder builder = new StringBuilder("P = { \n");

        Productions.forEach((nonterminal, productions) -> {
            builder.append(nonterminal);
            builder.append(" -> ");

            int prodNum = 0;
            for(List<String> oneProduction : productions){ // separated by |
                for(String token : oneProduction) {
                    builder.append(token).append(" ");
                }
                prodNum++;
                if (prodNum < productions.size())
                    builder.append("| ");

            }
            builder.append("\n");
        });
        builder.append("}");
        return builder.toString();
    }

    public String printProductionsForNonTerminal(String nonTerminal){
        StringBuilder builder = new StringBuilder();

        for(String lhs : Productions.keySet()) {
            if(lhs.equals(nonTerminal)) {
                builder.append(nonTerminal).append(" -> ");
                Set<List<String>> rhs = Productions.get(lhs);

                int prodNum = 0;
                for(List<String> oneProduction : rhs){ // separated by |
                    for(String token : oneProduction) {
                        builder.append(token).append(" ");
                    }
                    prodNum++;
                    if (prodNum < rhs.size())
                        builder.append("| ");

                }
            }
        }
        return builder.toString();
    }


    public boolean checkIfCFG(){
        return true;
    }

    public Set<String> getNonTerminals() {
        return NonTerminals;
    }

    public Set<String> getTerminals() {
        return Terminals;
    }

    public HashMap<String, Set<List<String>>> getProductions() {
        return Productions;
    }

    public String getStartSymbol() {
        return StartSymbol;
    }

    public Set<List<String>> getProductionForNonterminal(String nonTerminal) {
        for (String lhs : Productions.keySet()) {
            if (lhs.equals(nonTerminal)) {
                return Productions.get(lhs);
            }
        }
        return new HashSet<>();
    }
}