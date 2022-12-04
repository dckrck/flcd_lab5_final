package program;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class LanguageSpec {
    private final String keywordsFile;
    private final String separatorsFile;
    private final String operatorsFile;
    private final List<String> keywords = new ArrayList<String>();
    private final List<String> operators = new ArrayList<String>();
    private final List<String> separators = new ArrayList<String>();

    private final HashMap<String, Integer> codification = new HashMap<>();

    public LanguageSpec(String kwFile, String opFile, String spFile) {
        keywordsFile = kwFile;
        operatorsFile = opFile;
        separatorsFile = spFile;

        try {
            File file1 = new File(kwFile);
            Scanner reader1 = new Scanner(file1);

            while (reader1.hasNextLine()) {
                String currentKeyword = reader1.nextLine().strip();
                keywords.add(currentKeyword);
            }
            reader1.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            File file2 = new File(opFile);
            Scanner reader2 = new Scanner(file2);

            while (reader2.hasNextLine()) {
                String currentOperator = reader2.nextLine().strip();
                operators.add(currentOperator);
            }
            reader2.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            File file3 = new File(spFile);
            Scanner reader3 = new Scanner(file3);

            while (reader3.hasNextLine()) {
                String currentSeparator = reader3.nextLine().strip();
                separators.add(currentSeparator);
            }
            reader3.close();
            separators.add(" "); // add SPACE separator separately
            separators.add("\t"); // add TAB separator separately
            separators.add("\n"); // add NEWLINE separator separately

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        generatePifCodification();
    }

    private void generatePifCodification() {
        codification.put("identifier", 0);
        codification.put("constant", 1);

        int code = 2;

        for (String keyword : keywords) {
            codification.put(keyword, code);
            code++;
        }

        for (String operator : operators) {
            codification.put(operator, code);
            code++;
        }

        for (String separator : separators) {
            codification.put(separator, code);
            code++;
        }
    }

    public boolean isReservedWord(String token) {
        return keywords.contains(token);
    }

    public boolean isOperator(String token) {
        return operators.contains(token);
    }

    public boolean isMultiCharacterOperator(char op) {
        return op == '!' || isOperator(String.valueOf(op));
    }

    public boolean isSeparator(String token) {
        return separators.contains(token);
    }

    public boolean isIdentifier(String token) {
        String regex = "^[a-zA-Z_]([a-z|A-Z|0-9|_])*$";
        return token.matches(regex);
    }

    public boolean isConstant(String token) {
        String numberRegex = "^0|[1-9]([0-9])*|[+|-][1-9]([0-9])*|[1-9]([0-9])*\\.([0-9])*|[+|-][1-9]([0-9])*\\.([0-9])*$";
        String stringRegex = "^\"[a-zA-Z0-9+=<>%/.*#!?_;)(}{ ]+\"";
        String charRegex = "^\'[a-zA-Z0-9+=<>%/.*#!?_;)(}{ ]\'";

        if(token.matches(numberRegex) || token.matches(stringRegex) || token.matches(charRegex))
            return true;
        return false;
    }

    public Integer getCode(String token) {
        return codification.get(token);
    }
}