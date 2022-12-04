package program;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LexycalScanner {
    private final int symbolTableSize = 64;
    private final LanguageSpec languageSpecification = new LanguageSpec("src/resources/keywords.txt", "src/resources/operators.txt", "src/resources/separators.txt");
    private final SymbolTable symbolTable = new SymbolTable(symbolTableSize);
    private final ProgramInternalForm pif = new ProgramInternalForm();

    private final String programFile;
    private final String stFile;
    private final String pifFile;


    public LexycalScanner(String programFile, String pifFile, String stFile) {
        this.programFile = programFile;
        this.pifFile = pifFile;
        this.stFile = stFile;
    }

    public ArrayList<String> tokenize(String line) {
        ArrayList<String> tokens = new ArrayList<>();

        for (int position = 0; position < line.length(); ++position) {

            // if character is a separator which is not " "
            if (languageSpecification.isSeparator(String.valueOf(line.charAt(position))) && !(String.valueOf(line.charAt(position))).equals(" ")) {
                tokens.add(String.valueOf(line.charAt(position)));
            }

            // check if minus is operator or part of a constant
            else if (line.charAt(position) == '-') {
                String token;

                // operator
                if (languageSpecification.isIdentifier(tokens.get(tokens.size() - 1)) || languageSpecification.isConstant(tokens.get(tokens.size() - 1))) {
                    token = "-";
                }

                else {
                    // minus is before a number constant, token should be number constant
                    StringBuilder number = new StringBuilder();
                    number.append('-');
                    for (int i = position + 1; i < line.length() && (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.'); ++i) {
                        number.append(line.charAt(i));
                    }
                    token = number.toString();
                }

                tokens.add(token);
                position += token.length() - 1;
            }
            // check if plus is operator or part of a constant
            else if (line.charAt(position) == '+') {
                String token;

                // operator
                if (languageSpecification.isIdentifier(tokens.get(tokens.size() - 1)) || languageSpecification.isConstant(tokens.get(tokens.size() - 1))) {
                    token = "+";
                }

                else {
                    // plus is before a number constant, token should be number constant
                    StringBuilder number = new StringBuilder();
                    number.append('+');
                    for (int i = position + 1; i < line.length() && (Character.isDigit(line.charAt(i)) || line.charAt(i) == '.'); ++i) {
                        number.append(line.charAt(i));
                    }
                    token = number.toString();
                }

                tokens.add(token);
                position += token.length() - 1;
            }

            else if (line.charAt(position) == '\"') {
                StringBuilder constant = new StringBuilder();

                for (int i = position; i < line.length(); ++i) {

                    if ((languageSpecification.isSeparator(String.valueOf(line.charAt(i))) || languageSpecification.isOperator(String.valueOf(line.charAt(i)))) && ((i == line.length() - 2 && line.charAt(i + 1) != '\"') || (i == line.length() - 1)))
                        break;
                    constant.append(line.charAt(i));
                    if (line.charAt(i) == '\"' && i != position)
                        break;
                }

                tokens.add(constant.toString());
                position += constant.length() - 1;
            }

            else if (line.charAt(position) == '\'') {
                StringBuilder constant = new StringBuilder();

                for (int i = position; i < line.length(); ++i) {

                    if ((languageSpecification.isSeparator(String.valueOf(line.charAt(i))) || languageSpecification.isOperator(String.valueOf(line.charAt(i)))) && ((i == line.length() - 2 && line.charAt(i + 1) != '\'') || (i == line.length() - 1)))
                        break;
                    constant.append(line.charAt(i));
                    if (line.charAt(i) == '\'' && i != position)
                        break;
                }

                tokens.add(constant.toString());
                position += constant.length() - 1;
            }

            else if (languageSpecification.isMultiCharacterOperator(line.charAt(position))) {
                StringBuilder operator = new StringBuilder();
                operator.append(line.charAt(position));
                operator.append(line.charAt(position + 1));

                if (languageSpecification.isOperator(operator.toString()))
                    tokens.add(operator.toString());
                else
                    tokens.add(String.valueOf(line.charAt(position)));

                position += operator.length() - 1;
            }

            else if (line.charAt(position) != ' ') {
                StringBuilder token = new StringBuilder();
                int i = position;

                while(i < line.length() && !languageSpecification.isSeparator(String.valueOf(line.charAt(i)))
                        && !languageSpecification.isMultiCharacterOperator(line.charAt(i))
                        && line.charAt(i) != ' ') {
                                token.append(line.charAt(i));
                                ++i;
                }
                tokens.add(token.toString());
                position += token.length() - 1;
            }

        }

        return tokens;
    }

    public void scan() {
        List<Pair<String, Integer>> tokenPairs = new ArrayList<>();
        try {
            File file = new File(programFile);
            Scanner reader = new Scanner(file);

            int currentLineNumber = 1;

            while (reader.hasNextLine()) {
                String currentLine = reader.nextLine();
                List<String> tokens = tokenize(currentLine);

                for (String token : tokens) tokenPairs.add(new Pair<>(token, currentLineNumber));

                currentLineNumber++;
            }
            reader.close();

            createPifAndSymbolTable(tokenPairs);
            writeResultsToPifAndSt();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createPifAndSymbolTable(List<Pair<String, Integer>> tokens) {
        List<String> invalidTokens = new ArrayList<>();

        for (Pair<String, Integer> tokenPair : tokens) {
            String token = tokenPair.getKey();

            if (languageSpecification.isOperator(token) ||
                    languageSpecification.isReservedWord(token) ||
                    languageSpecification.isSeparator(token))
            {
                int code = languageSpecification.getCode(token);
                pif.add_element(code, new Pair<>(-1, -1));
            }

            else if (languageSpecification.isIdentifier(token))
            {
                symbolTable.add(token);
                Pair<Integer, Integer> position = symbolTable.getPosition(token);
                pif.add_element(0, position);
            }

            else if (languageSpecification.isConstant(token)) {
                symbolTable.add(token);
                Pair<Integer, Integer> position = symbolTable.getPosition(token);
                pif.add_element(1, position);
            }

            else if (!invalidTokens.contains(token)) {
                invalidTokens.add(token);
                System.out.println("Error at line " + tokenPair.getValue() + " - invalid token :  " + token);
            }
        }

        if (invalidTokens.isEmpty()) {
            System.out.println("Lexically Correct!");
        }
    }

    public void writeResultsToPifAndSt() {
        try {
            File stFile = new File(this.stFile);
            FileWriter stFileWriter = new FileWriter(stFile, false);
            BufferedWriter stTableWriter = new BufferedWriter(stFileWriter);

            stTableWriter.write(symbolTable.toString());
            stTableWriter.close();

            File pifFile = new File(this.pifFile);
            FileWriter pifFileWriter = new FileWriter(pifFile, false);
            BufferedWriter pifWriter = new BufferedWriter(pifFileWriter);

            pifWriter.write(pif.toString());
            pifWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}