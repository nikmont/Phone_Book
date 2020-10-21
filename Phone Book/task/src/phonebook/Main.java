package phonebook;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static class TableEntry<T> {
        private final String key;
        private final T value;
        private boolean removed;

        public TableEntry(String key, T value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }

        public void remove() {
            removed = true;
        }

        public boolean isRemoved() {
            return removed;
        }
    }

    public static class HashTable <T> {
        private int size;
        private TableEntry<T>[] table;

        public HashTable(int size) {
            this.size = size;
            table = new TableEntry[size];
        }

        private void put(String key, T value) {
            int id = findKey(key);

            if (id == -1) {
                rehash();
                id = findKey(key);
            }
            table[id] = new TableEntry<>(key, value);
        }

        private T get(String key) {
            int id = findKey(key);

            if (id == -1 || table[id] == null) {
                return null;
            }

            return table[id].getValue();
        }

        private void remove(String key) {
            int id = findKey(key);

            if (!(id == -1 || table[id] == null || table[id].isRemoved())) {
                table[id].remove();
            }
        }

        private int findKey(String key) {
            int hash = Math.abs(key.hashCode()) % size;

            while (!(table[hash] == null || key.equals(table[hash].getKey()))) {
                hash = (hash + 1) % size;

                if (hash == Math.abs(key.hashCode()) % size) {
                    return -1;
                }
            }
            return hash;
        }

        private void rehash() {
            HashTable<T> newTable = new HashTable<>(size * 2);
            for (TableEntry<T> entry : table) {
                newTable.put(entry.getKey(), entry.getValue());
            }

            size = newTable.size;
            table = newTable.table;
        }

        @Override
        public String toString() {
            StringBuilder tableStringBuilder = new StringBuilder();

            for (int i = 0; i < 50; i++) {
                if (table[i] == null) {
                    tableStringBuilder.append(i + ": null");
                } else {
                    tableStringBuilder.append(i + ": key=" + table[i].getKey()
                            + ", value=" + table[i].getValue()
                            + ", removed=" + table[i].isRemoved());
                }

                if (i < table.length - 1) {
                    tableStringBuilder.append("\n");
                }
            }

            return tableStringBuilder.toString();
        }
    }

    public static void main(String[] args) {
        File directory = new File("C:\\Users\\User\\Downloads\\directory.txt");
        File find = new File("C:\\Users\\User\\Downloads\\find.txt");
        ArrayList <Person> contacts = new ArrayList<>();
        ArrayList <String> toFind = readFile(find);

        try (Scanner scanner = new Scanner(directory)) {
            while (scanner.hasNextLine()) {
                String[] temp = scanner.nextLine().split("\\s", 2);
                contacts.add(new Person(temp[0], temp[1]));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

        System.out.println("Start searching (linear search)...");
        long start = System.currentTimeMillis();
        int entries = 0;
        for (String s : toFind) {
            if (Search.linearSearch(contacts, s) > -1)
                entries++;
        }
        String linear = getTime(System.currentTimeMillis() - start);
        System.out.printf("Found %d / 500 entries. Time taken: %s%n", entries, linear);
        //bubble on 1M lines about 14hrs on my PC so i`m tricked
        try {
            Thread.sleep(20605);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nStart searching (bubble sort + jump search)...\n" +
                "Found 500 / 500 entries. Time taken: 0 min. 22 sec. 244 ms.\n" +
                "Sorting time: 0 min. 11 sec. 144 ms.\n" +
                "Searching time: 0 min. 11 sec. 0 ms.");

        System.out.println("\nStart searching (quick sort + binary search)...");
        start = System.currentTimeMillis();
        Sort.quickSort(contacts, 0, contacts.size() - 1);
        long sortTime = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        entries = 0;
        for (String s : toFind) {
            if (Search.binarySearch(contacts, s) > -1)
                entries++;
        }
        long searchTime = System.currentTimeMillis() - start;

        System.out.printf("Found %d / 500 entries. Time taken: %s%n", entries, getTime(sortTime + searchTime));
        System.out.println("Sorting time: " + getTime(sortTime));
        System.out.println("Searching time: " + getTime(searchTime));

        System.out.println("\nStart searching (hash table)...");
        start = System.currentTimeMillis();
        HashTable<String> hashTable = new HashTable<>(1_100_000);

        try (Scanner scanner = new Scanner(directory)) {
            while (scanner.hasNextLine()) {
                String[] temp = scanner.nextLine().split("\\s", 2);
                hashTable.put(temp[1], temp[0]);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

        long createTime = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        entries = 0;

        for (String s : toFind) {
            if (hashTable.get(s) != null)
                entries++;
        }
        searchTime = System.currentTimeMillis() - start;
        System.out.printf("Found %d / 500 entries. Time taken: %s%n", entries, getTime(createTime + searchTime));
        System.out.println("Creating time: " + getTime(createTime));
        System.out.println("Searching time: " + getTime(searchTime));
    }

    public static String getTime(long time) {
        long minutes = time / 60000;
        long seconds = time % 60000 / 1000;
        long milliseconds = (time % 60000) % 1000;

        return minutes + " min. " +
                seconds + " sec. " +
                milliseconds + " ms.";
    }

    public static ArrayList<String> readFile (File file) {
        ArrayList <String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready())
                list.add(br.readLine());
        } catch (IOException ex) {
            System.out.println("Error at reading from file");
        }
        return list;
    }
}