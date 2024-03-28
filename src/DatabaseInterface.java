public interface DatabaseInterface {
    static boolean readUsers() {
        return false;
    }

    static boolean createUser(String username, String password, String displayName) {
        return false;
    }

    static boolean createUser(User user) {
        return false;
    }

    static boolean removeUser(User user) {
        return false;
    }

    static void writeUsers() {}

    static void writeMessages() {}
}

