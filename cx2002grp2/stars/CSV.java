package cx2002grp2.stars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
 */
public class CSV {
    public static final char DEFAULT_DELIMITER = ',';
    public static final char DEFAULT_ESCAPE_CHAR = '"';
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
         * Construct csv reader to read the csv file with given filePath, assuming that
         * the csv file has header.
         * 
         * @param filePath the path to the csv file to be read
         */
        public Reader(String filePath) {
            this(filePath, HAS_HEADER);
        }

        /**
         * Construct csv reader to read the csv file with given filePath and header
         * setting.
         * <p>
         * If hasHeader is true, the first line of csv file will be regarded as header
         * row. Otherwise, the first line of csv file will be treated as normal data
         * row.
         * 
         * @param filePath  the path to the csv file to be read
         * @param hasHeader whether the csv file has header
         */
        public Reader(String filePath, boolean hasHeader) {
            this(filePath, hasHeader, CSV.DEFAULT_DELIMITER, CSV.DEFAULT_ESCAPE_CHAR);
        }

        /**
         * Construct csv reader to read the csv file with given filePath, header
         * setting, delimeter and escaping character.
         * <p>
         * If hasHeader is true, the first line of csv file will be regarded as header
         * row. Otherwise, the first line of csv file will be treated as normal data
         * row.
         * 
         * @param filePath   the path to the csv file to be read
         * @param hasHeader  whether the csv file has header
         * @param delimiter  user defined delimiter used during csv parsing
         * @param escapeChar user defined escaping character used during csv parsing
         */
        public Reader(String filePath, boolean hasHeader, char delimiter, char escapeChar) {
            this.csvFile = new File(filePath);
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
        public String filePath() {
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
            while (true) {
                i = nextNonBlank(line, i);
                if (i >= line.length()) {
                    ret.add("");
                    break;
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
            }

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
         * The beginning and ending escape character will be removed. Any two
         * consecutive escape characters in the field will be transformed into in one
         * escape character. All the other character in between will be reserved.
         * 
         * @param line the csv row where the field is in.
         * @param beg  the beginning position of csv field.
         * @return The parsed field.
         * @throws IllegalArgumentException The format of the input field is illegal.
         */
        private ReadFieldResult parseQuotedField(String line, int beg) {
            int i = nextNonBlank(line, beg);

            if (line.charAt(i) != escapeChar) {
                throw new IllegalArgumentException(
                        "The input feild is not quoted by expected character (" + escapeChar + ")");
            }

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
         * The beginning and ending blank characters will be trimmed. All the other
         * charactor will reserved.
         * 
         * @param line the csv row where the field is in.
         * @param beg  the beginning position of csv field.
         * @return The parsed field.
         * @throws IllegalArgumentException The format of the input field is illegal.
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
     * <p>
     * File will no be occupied unless the writing method is called.
     * <p>
     * If the state of header changes due to modification functions like {@link void
     * writeHeader(Iterable header)}, then the value of hasHeader will change
     * simultaneously.
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
         * <p>
         * If the state of header changes due to modification functions like {@link void
         * writeHeader(Iterable header)}, then the value of hasHeader will change
         * simultaneously.
         */
        private boolean hasHeader;

        /**
         * Construct csv writer to write the csv file with given filePath, assuming that
         * the csv file has header.
         * 
         * @param filePath the path to the csv file to be read
         */
        public Writer(String filePath) {
            this(filePath, HAS_HEADER);
        }

        /**
         * Construct csv writer to write the csv file with given filePath and header
         * setting.
         * <p>
         * If hasHeader is true, the first line of csv file will be regarded as header
         * row. Otherwise, the first line of csv file will be treated as normal data
         * row.
         * 
         * @param filePath  the path to the csv file to be read
         * @param hasHeader whether the csv file has header
         */
        public Writer(String filePath, boolean hasHeader) {
            this(filePath, hasHeader, CSV.DEFAULT_DELIMITER, CSV.DEFAULT_ESCAPE_CHAR);
        }

        /**
         * Construct csv writer to write the csv file with given filePath, header
         * setting, delimeter and escaping character.
         * <p>
         * If hasHeader is true, the first line of csv file will be regarded as header
         * row. Otherwise, the first line of csv file will be treated as normal data
         * row.
         * 
         * @param filePath   the path to the csv file to be read
         * @param hasHeader  whether the csv file has header
         * @param delimiter  user defined delimiter used during csv parsing
         * @param escapeChar user defined escaping character used during csv parsing
         */
        public Writer(String filePath, boolean hasHeader, char delimiter, char escapeChar) {
            this.csvFile = new File(filePath);
            this.escapeChar = escapeChar;
            this.delimiter = delimiter;
            this.hasHeader = hasHeader;
        }

        /**
         * Write both header and data into the csv file.
         * <p>
         * If header is null, the new csv file will has no header and
         * {@link hasHeader()} will return false after writing. Otherwise, the new csv
         * file will has header and {@link hasHeader()} will return true.
         * <p>
         * If the data is null, the new csv file will has no any data.
         * 
         * @param <StringIterable> The type of inner string iterable of the 2D-Iterable.
         * @param header           The header of new csv file
         * @param data             A 2D-Iterable of String that representing the data of
         *                         table to be written.
         */
        public <StringIterable extends Iterable<String>>
        void writeBoth(Iterable<String> header, Iterable<StringIterable> data) {
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

        /**
         * Write the data in a 2D-Iterable into the csv file and reserve the original
         * header.
         * <p>
         * If the data is null, the new csv file will has no any data.
         * 
         * @param <StringIterable> The type inner string iterable of the 2D-Iterable.
         * @param data             The data to be written into csv file.
         */
        public <StringIterable extends Iterable<String>> void writeData(Iterable<StringIterable> data) {
            Reader reader = this.toReader();
            List<String> header = reader.readHeader();
            writeBoth(header, data);
        }

        /**
         * Write the new header into csv file and reserve the original data.
         * <p>
         * If header is null, the new csv file will has no header and
         * {@link hasHeader()} will return false after writing. Otherwise, the new csv
         * file will has header and {@link hasHeader()} will return true.
         * 
         * @param header
         */
        public void writeHeader(Iterable<String> header) {
            Reader reader = this.toReader();
            List<List<String>> data = reader.readData();
            writeBoth(header, data);
        }

        /**
         * Convert the csv writer to the corresponding csv reader.
         * 
         * @return a csv reader with the same related csv file, header setting, parsing
         *         delimiter and parsing escape character.
         */
        public Reader toReader() {
            return new Reader(csvFile.getName(), hasHeader, delimiter, escapeChar);
        }

        /**
         * Get the delimiter used to parse the csv file.
         * 
         * @return The delimiter used to parse the csv file
         */
        public char delimiter() {
            return this.delimiter;
        }

        /**
         * Get the escaping character used to parse the csv file.
         * 
         * @return the escaping character used to parse the csv file.
         */
        public char escapeChar() {
            return this.escapeChar;
        }

        /**
         * Get the file path that the csv writer going to write.
         * 
         * @return the file path that the csv writer going to write.
         */
        public String filePath() {
            return this.csvFile.getName();
        }

        /**
         * Get whether the csv has header.
         * 
         * @return Whether the csv has header.
         */
        public boolean hasHeader() {
            return this.hasHeader;
        }

        /**
         * Convert a string to a csv field format.
         * <p>
         * If the field contains neither delimeter nor escaping character, and has no
         * white space character at the beginning or the end, the original string will
         * be returned.
         * <p>
         * Otherwise, the string will be converted into csv field format. That is, a
         * escaping character will be prepended at the beginning of the field, another
         * one will be appended at the end, any single escaping character in the
         * original text will be transformed into two consecutive escaping characters.
         * 
         * @param str the string to be transformed.
         * @return The string in csv format.
         */
        private String csvFormatStr(String str) {
            if (str.indexOf('\n') != -1) {
                throw new IllegalArgumentException("Multiple-line field is not supported.");
            }

            if (str.length() == 0) {
                return str;
            }

            if (str.indexOf(delimiter) != -1 || str.indexOf(escapeChar) != -1 || Character.isWhitespace(str.charAt(0))
                    || Character.isWhitespace(str.charAt(str.length() - 1))) {

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

        /**
         * Convert a series of Strings in to a csv row.
         * <p>
         * That is, all the string fields are converted into csv format and concatenated
         * together; deleimeters are inserted between every two consecutive converted
         * fields.
         * 
         * @param row the series of strings to be converted.
         * @return a csv format row
         */
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

        /**
         * Get the printer of the csv file for output.
         * 
         * @return the printer binding to the csv file to be written.
         * @exception RuntimeException If the given file object does not denote an
         *                             existing, writable regular file and a new regular
         *                             file of that name cannot be created, or if some
         *                             other error occurs while opening or creating the
         *                             file, FileNotFoundException will be thrown from
         *                             FileInputStream constructor and will be degraded
         *                             and propegated as RuntimeException
         */
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

        writer.writeData(data);
        writer.writeHeader(header);

        CSV.Reader reader0 = writer.toReader();
        List<String> header0 = reader0.readHeader();

        for (String field : header0) {
            System.out.print(field + " ### ");
        }
    }
}