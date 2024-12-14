package com.example.railgo.domain.utils.validation;

public class SecurityValidator {

    // Phương thức kiểm tra tất cả các loại tấn công
    public static boolean contain(String element) {
        return containsXSS(element) ||
                containsSQLInjection(element) ||
                containsCommandInjection(element) ||
                containsPathTraversal(element) ||
                containsLDAPInjection(element) ||
                containsSensitiveData(element) ||
                containsEmailInjection(element) ||
                containsEncodedAttacks(element);
    }

    // Kiểm tra XSS
    public static boolean containsXSS(String element) {
        String xssPattern = "(?i)<(script|img|div|style|iframe|object|embed|form|input|button|select|option|textarea|a|meta|link|base|title|frame|frameset)[^>]*>";
        String specialCharsPattern = "(?i)<.*?[\\u0000-\\u001f\\u007f<>\"'/;#]+.*?>";
        String javascriptPattern = "(?i)<.*?(on[a-zA-Z]+|javascript:|data:).*?>";

        return element.matches(xssPattern) ||
                element.matches(specialCharsPattern) ||
                element.matches(javascriptPattern) ||
                element.matches(".*<.*?>.*") ||
                element.matches(".*<script.*?>.*</script>.*");
    }

    // Kiểm tra SQL Injection
    public static boolean containsSQLInjection(String element) {
        String sqlPattern = "(?i)(select|insert|update|delete|drop|--|;|\\/\\*|\\*\\/|union|where|limit|into|load_file|outfile)";
        return element.matches(sqlPattern);
    }

    // Kiểm tra Command Injection
    public static boolean containsCommandInjection(String element) {
        String commandPattern = "(?i)(;|&&|\\|\\||\\|)";
        return element.matches(commandPattern);
    }

    // Kiểm tra Path Traversal
    public static boolean containsPathTraversal(String element) {
        String pathTraversalPattern = "(?i)\\..*\\/.*";
        return element.matches(pathTraversalPattern);
    }

    // Kiểm tra LDAP Injection
    public static boolean containsLDAPInjection(String element) {
        String ldapPattern = "(?i)(\\(|\\)|\\*|\\||\\&|\\/)";
        return element.matches(ldapPattern);
    }

    // Kiểm tra Sensitive Data Exposure
    public static boolean containsSensitiveData(String element) {
        String sensitiveDataPattern = "(?i)(password|creditcard|ssn|cvv)";
        return element.matches(sensitiveDataPattern);
    }

    // Kiểm tra Email Injection
    public static boolean containsEmailInjection(String element) {
        String emailInjectionPattern = "(?i)(\\r\\n|\\n\\r|\\n|\\r|\\t)";
        return element.matches(emailInjectionPattern);
    }

    // Kiểm tra các kiểu tấn công đã mã hóa (Encoded Attacks)
    public static boolean containsEncodedAttacks(String element) {
        String encodedPattern = "(?i)(%27|%22|%3B|%2F|%3C|%3E|%5C)";
        return element.matches(encodedPattern);
    }
}
