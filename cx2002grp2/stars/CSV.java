package cx2002grp2.stars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cx2002grp2.stars.util.ToStringRowInterface;

/**
 * For reading and writing csv table files. Containing two subclasses:
 * {@link CSV.Reader} and {@link CSV.Writer}
 * <p>
 * Assumptions: only one csv reader or csv writer is accessing the csv file at
 * any given point. That is, the file reading and writing is not synchronized.
 * <p>
 * CSV dialect used:
 * <ul>
 * <li>The non-quoted fields will be trimmed.
 * <li>The field with multiple lines is not supported.
 * </ul>
 * 
 * @author Wei Kaitao
 * @since 2020 Oct. 17
 * 
 */
public class CSV {
    public static final char DEFALUT_DELIMITER = ',';
    public static final char DEFALUT_ESCAPE_CHAR = '"';
    public static final boolean NO_HEADER = false;
    public static final boolean HAS_HEADER = true;

    /**
     * For reading csv table files.
     * <p>
     * File will no be occupied until the reading method is called.
     */
    public static class Reader {
        /**
         * The escape character used during parsing.
         */
        private char escapeChar;
        /**
         * The delimiter used during parsing.
         */
        private char delimiter;
        /**
         * CSV file going to read.
         */
        private File csvFile;
        /**
         * If the csv to be read has header row.
         */
        private boolean hasHeader;

        /**
         * Construct csv reader to read the csv file with given filepath, assuming that
         * the csv file has header.
         * 
         * @param filepath the path to the csv file to be read
         */
        public Reader(String filepath) {
            this(filepath, HAS_HEADER);
        }

        /**
         * Construct csv reader to read the csv file with given filepath.
         * <p>
         * If hasHeader is true, the first line of csv file will be regarded as header
         * row. Otherwise, the first line of csv file will be treated as normal data
         * row.
         * 
         * @param filepath  the path to the csv file to be read
         * @param hasHeader whether the csv file has header
         */
        public Reader(String filepath, boolean hasHeader) {
            this(filepath, hasHeader, CSV.DEFALUT_DELIMITER, CSV.DEFALUT_ESCAPE_CHAR);
        }

        /**
         * Construct csv reader to read the csv file with given filepath.
         * <p>
         * If hasHeader is true, the first line of csv file will be regarded as header
         * row. Otherwise, the first line of csv file will be treated as normal data
         * row.
         * 
         * @param filepath   the path to the csv file to be read
         * @param hasHeader  whether the csv file has header
         * @param delimiter  user defined delimiter used during csv parsing
         * @param escapeChar user defined escaping character used during csv parsing
         */
        public Reader(String filepath, boolean hasHeader, char delimiter, char escapeChar) {
            this.csvFile = new File(filepath);
            this.hasHeader = hasHeader;
            this.escapeChar = escapeChar;
            this.delimiter = delimiter;
        }

        /**
         * Read the data rows from the csv file.
         * <p>
         * Each data row in the csv file is parsed into a List of String.
         * <p>
         * All the List of String parsing from rows will be stored in a List and return.
         * <p>
         * If {@link hasHeader()} is true, the first line of csv file will be ignore
         * during parsing.
         * 
         * @return the parsed csv table.
         */
        public List<List<String>> readData() {
            Scanner sc = getScanner();
            List<List<String>> ret = new ArrayList<>();

            if (hasHeader) {
                sc.nextLine(); // skip header
            }

            while (sc.hasNextLine()) {
                List<String> oneRow = parseLine(sc.nextLine());
                ret.add(oneRow);
            }

            sc.close();
            return ret;
        }

        /**
         * Read the header rows from the csv file.
         * <p>
         * If {@link hasHeader()} is true, the first line of csv file is parsed into a
         * List of String and return. Otherwise, null will be returned.
         * 
         * @return null or the parsed csv header.
         */
        public List<String> readHeader() {
            if (!hasHeader) {
                return null;
            }

            Scanner sc = getScanner();
            List<String> header = parseLine(sc.nextLine());
            sc.close();

            return header;
        }

        /**
         * Convert the csv reader to the corresponding csv writer.
         * 
         * @return a csv writer with the same related csv file, header setting, parsing
         *         delimiter and parsing escape character
         */
        public Writer toWriter() {
            return new Writer(csvFile.getName(), hasHeader, delimiter, escapeChar);
        }

        /**
         * The delimiter used during parsing.
         * 
         * @return The delimiter used during parsing.
         */
        public char delimiter() {
            return this.delimiter;
        }

        /**
         * The escape character used during parsing.
         * 
         * @return The escape character used during parsing.
         */
        public char escapeChar() {
            return this.escapeChar;
        }

        /**
         * The path to the csv file being read.
         * 
         * @return The path to the csv file being read.
         */
        public String filepath() {
            return this.csvFile.getName();
        }

        /**
         * Whether the csv file being read has header row.
         * 
         * @return Whether the csv file being read has header row.
         */
        public boolean hasHeader() {
            return this.hasHeader;
        }

        /**
         * Helper class used to store the result of a csv field parsing.
         * 
         * @see parseNormalField(String line, int i)
         * @see parseQuotedField(String line, int i)
         */
        private static class ReadFieldResult {
            /**
             * Simple constructor
             * 
             * @param str The String parsed out from the input field.
             * @param pos The index of where the field parsing end.
             */
            public ReadFieldResult(String str, int pos) {
                this.str = str;
                this.pos = pos;
            }

            /**
             * The String parsed out from the input field.
             */
            public String str;
            /**
             * The index of where the field parsing end.
             */
            public int pos;
        }

        /**
         * Parse a csv line in to a List of String.
         * 
         * @param line the csv line to be parsed.
         * @return A List of String containing all the fields in the csv line.
         */
        private List<String> parseLine(String line) {
            ArrayList<String> ret = new ArrayList<String>();
            int i = 0;
            do {
                i = nextNonBlank(line, i);
                if (i >= line.length()) {
                    ret.add("");
                    return ret;
                }

                ReadFieldResult result;
                if (line.charAt(i) == escapeChar) {
                    result = parseQuotedField(line, i);
                } else {
                    result = parseNormalField(line, i);
                }
                i = result.pos;

                i = nextNonBlank(line, i);
                if (i < line.length() && line.charAt(i) != delimiter) {
                    throw new IllegalArgumentException(
                            "The " + (ret.size() + 1) + "-th field is not ended with delimiter (" + delimiter + "), "
                                    + "\"" + line.charAt(i) + "\" is found instead.");
                }
                ret.add(result.str);
                ++i;
            } while (i < line.length());

            return ret;
        }

        /**
         * Get the scanner binding to the csv file to be read.
         * 
         * @return the scanner binding to the csv file to be read.
         * @exception RuntimeException When the file cannot be found or unaccessable,
         *                             FileNotFoundException thrown from FileInputStream
         *                             constructor will be degraded and propegated as
         *                             RuntimeException
         */
        private Scanner getScanner() {
            Scanner sc = null;

            try {
                sc = new Scanner(new FileInputStream(this.csvFile));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            return sc;
        }

        /**
         * Find the index of next non-blank character in a string starting from a given
         * index.
         * 
         * @param str the string to be searched
         * @param i   the beginning index to be searched
         * @return the lowerest index such that the index is greater or equal to i and
         *         Character.isWhitespace(str.charAt(i)) is true. If it does not exist,
         *         a index greater or equal to str.length() will be returned.
         */
        private static int nextNonBlank(String str, int i) {
            while (i < str.length() && Character.isWhitespace(str.charAt(i))) {
                ++i;
            }
            return i;
        }

        /**
         * Parse a csv field quoted by the escape character.
         * <p>
         * The beginning and ending escape character will be removed. All the two
         * consecutive escape characters in the field will be transformed into in one
         * escape character. All the other character in between will be reserved.
         * 
         * @param line the csv row where the field is in.
         * @param beg  the beginning position of csv field.
         * @return The parsed field.
         */
        private ReadFieldResult parseQuotedField(String line, int beg) {
            int i = nextNonBlank(line, beg);

            assert line.charAt(i) == escapeChar
                    : "The input feild is not quoted by expected character (" + escapeChar + ")";
            ++i;
            StringBuilder builder = new StringBuilder();
            boolean closed = false;
            while (i < line.length()) {
                if (line.charAt(i) == escapeChar) {
                    ++i;
                    if (i >= line.length() || line.charAt(i) != escapeChar) {
                        closed = true;
                        break;
                    } else {
                        builder.append(escapeChar);
                    }
                } else {
                    builder.append(line.charAt(i));
                }
                ++i;
            }
            if (!closed) {
                throw new IllegalArgumentException(
                        "The input line is not properly closed with escaping character (" + escapeChar + ").");
            }
            return new ReadFieldResult(builder.toString(), i);
        }

        /**
         * Parse a csv field not quoted by the escape character.
         * <p>
         * The beginning and ending blank characters will be trimmed.
         * All the other charactor will reserved.
         * 
         * @param line the csv row where the field is in.
         * @param beg  the beginning position of csv field.
         * @return The parsed field.
         */
        private ReadFieldResult parseNormalField(String line, int beg) {
            beg = nextNonBlank(line, beg);

            if (line.charAt(beg) == delimiter) {
                return new ReadFieldResult("", beg);
            }
            
            int i = beg;
            int lastNonSpace = beg;
            while (i < line.length() && line.charAt(i) != delimiter) {
                if (line.charAt(i) == escapeChar) {
                    throw new IllegalArgumentException(
                            "Unexpected escaping character (" + escapeChar + ") in a normal (non-quoted) field.");
                }
                if (!Character.isWhitespace(line.charAt(i))) {
                    lastNonSpace = i;
                }
                ++i;
            }
            return new ReadFieldResult(line.substring(beg, lastNonSpace + 1), i);
        }
    }

    /**
     * For writing csv file 
     */
    public static class Writer {
        /**
         * The escape character used during parsing.
         */
        private char escapeChar;
        /**
         * The delimiter used during parsing.
         */
        private char delimiter;
        /**
         * CSV file going to write.
         */
        private File csvFile;
        /**
         * If the csv to be read has header row.
         */
        private boolean hasHeader;

        public Writer(String filepath) {
            this(filepath, HAS_HEADER);
        }

        public Writer(String filepath, boolean hasHeader) {
            this(filepath, hasHeader, CSV.DEFALUT_DELIMITER, CSV.DEFALUT_ESCAPE_CHAR);
        }

        public Writer(String filepath, boolean hasHeader, char delimiter, char escapeChar) {
            this.csvFile = new File(filepath);
            this.escapeChar = escapeChar;
            this.delimiter = delimiter;
            this.hasHeader = hasHeader;
        }

        public <StringIterable extends Iterable<String>> void writeTable(Iterable<String> header,
                Iterable<StringIterable> data) {
            PrintStream printer = getPrinter();

            if (header != null) {
                printer.println(convertRow(header));
                hasHeader = true;
            } else {
                hasHeader = false;
            }

            if (header != null) {
                for (Iterable<String> row : data) {
                    printer.println(convertRow(row));
                }
            }

            printer.close();
        }

        public <StringIterable extends Iterable<String>> void writeTable(Iterable<StringIterable> data) {
            Reader reader = this.toReader();
            List<String> header = reader.readHeader();
            writeTable(header, data);
        }

        public void writeHeader(Iterable<String> header) {
            Reader reader = this.toReader();
            List<List<String>> data = reader.readData();
            writeTable(header, data);
        }

        public void writeHeader(ToStringRowInterface header) {
            writeHeader(header.toStringRow());
        }

        public void writeData(Iterable<ToStringRowInterface> data) {
            List<Iterable<String>> convertedData = new LinkedList<Iterable<String>>();
            for (ToStringRowInterface row : data) {
                convertedData.add(row.toStringRow());
            }
            writeTable(convertedData);
        }

        public Reader toReader() {
            return new Reader(csvFile.getName(), hasHeader, delimiter, escapeChar);
        }

        public char delimiter() {
            return this.delimiter;
        }

        public char escapeChar() {
            return this.escapeChar;
        }

        public String filepath() {
            return this.csvFile.getName();
        }

        public boolean hasHeader() {
            return this.hasHeader;
        }

        private String csvFormatStr(String str) {
            assert str.indexOf('\n') == -1 : "CSV formater does not support multiple line field.";

            if (str.length() == 0) {
                return str;
            }

            if (str.indexOf(delimiter) != -1 || str.indexOf(escapeChar) != -1) {

                StringBuilder builder = new StringBuilder(str.length() + 2);
                builder.append(escapeChar);
                for (int i = 0; i < str.length(); ++i) {
                    if (str.charAt(i) == escapeChar) {
                        builder.append(escapeChar).append(escapeChar);
                    } else {
                        builder.append(str.charAt(i));
                    }
                }
                builder.append(escapeChar);
                return builder.toString();
            }

            return str;
        }

        private String convertRow(Iterable<String> row) {
            StringBuilder builder = new StringBuilder();
            boolean firstField = true;
            for (String field : row) {
                if (!firstField)
                    builder.append(delimiter);
                builder.append(csvFormatStr(field));
                firstField = false;
            }
            return builder.toString();
        }

        private PrintStream getPrinter() {
            PrintStream printer;

            try {
                printer = new PrintStream(csvFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            return printer;
        }

    }

    public static void main(String[] args) {
        CSV.Reader reader = new CSV.Reader("tables/schedule.csv");
        List<String> header = reader.readHeader();
        List<List<String>> data = reader.readData();

        for (String field : header) {
            System.out.print(field + " ### ");
        }

        System.out.println();
        System.out.println();
        System.out.println();
        CSV.Writer writer = new CSV.Writer("test1.csv");
        header.set(1, "Happy \"!\"!\"\"");

        writer.writeTable(data);
        writer.writeHeader(header);

        CSV.Reader reader0 = writer.toReader();
        List<String> header0 = reader0.readHeader();

        for (String field : header0) {
            System.out.print(field + " ### ");
        }
    }
}