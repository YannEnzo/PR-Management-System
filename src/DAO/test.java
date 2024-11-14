package DAO;

public class test {
    public static void main(String[] args) {
        // Create an instance of CustomHashTable
        CustomHashTable<String, String> hashTable = new CustomHashTable<>();

        // Test inserting data
        hashTable.put("key1", "value1");
        hashTable.put("key2", "value2");
        hashTable.put("key3", "value3");

        // Test retrieving data
        System.out.println("Get key1: " + hashTable.get("key1")); // Should print "value1"
        System.out.println("Get key2: " + hashTable.get("key2")); // Should print "value2"
        System.out.println("Get key3: " + hashTable.get("key3")); // Should print "value3"

        // Test retrieving a non-existent key
        System.out.println("Get key4: " + hashTable.get("key4")); // Should print "null"

        // Loop through and display all values in the hash table
        System.out.println("All values in the hash table:");
        hashTable.forEachValue(value -> System.out.println(value));
    }
}
