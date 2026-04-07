package com.daniel.Repository.impl;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.nio.file.*;

import com.daniel.Repository.Repository;
import com.daniel.model.Category;
import com.daniel.model.Expense;

// handles all direct file operations with encryption and backup
public class FileRepository implements Repository {
    
    static List<Expense> fileExpeList = new ArrayList<>();
    
    // file paths
    private static final String DATA_DIR = "C:\\Users\\markd\\Desktop\\Budget_Tracker\\Data\\";
    private static final String fileName = DATA_DIR + "expenses.encrypted";
    private static final String humanReadable = DATA_DIR + "expenses_readable.txt";
    private static final String BACKUP_DIR = DATA_DIR + "backups\\";
    private static final String ENCRYPTION_KEY_FILE = DATA_DIR + "secret.key";
    
    private static SecretKey secretKey;
    private static final String ALGORITHM = "AES";
    private static final DateTimeFormatter BACKUP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    
    // static block to initialize encryption
    static {
        try {
            secretKey = getOrCreateSecretKey();
            createDirectories();
        } catch(Exception e) {
            System.out.println("Encryption initialization error: " + e.getMessage());
        }
    }
    
    // create all required directories
    private static void createDirectories() {
        new File(DATA_DIR).mkdirs();
        new File(BACKUP_DIR).mkdirs();
    }
    
    // get existing secret key or create new one
    private static SecretKey getOrCreateSecretKey() throws Exception {
        File keyFile = new File(ENCRYPTION_KEY_FILE);
        if(keyFile.exists()) {
            try(FileInputStream fis = new FileInputStream(keyFile)) {
                byte[] keyBytes = fis.readAllBytes();
                return new SecretKeySpec(keyBytes, ALGORITHM);
            }
        } else {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(256);
            SecretKey key = keyGen.generateKey();
            try(FileOutputStream fos = new FileOutputStream(keyFile)) {
                fos.write(key.getEncoded());
            }
            // make key file read-only
            keyFile.setReadOnly();
            return key;
        }
    }
    
    // encrypt data
    private String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    // decrypt data
    private String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }
    
    // create backup of current data
    private void createBackup() {
        try {
            File sourceFile = new File(fileName);
            if(!sourceFile.exists()) return;
            
            String timestamp = LocalDate.now().format(BACKUP_FORMAT);
            String backupName = BACKUP_DIR + "expenses_backup_" + timestamp + ".encrypted";
            
            // copy encrypted file as backup
            Files.copy(sourceFile.toPath(), new File(backupName).toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            // keep only last 30 backups
            cleanOldBackups();
            
        } catch(Exception e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }
    
    // delete backups older than 30 days
    private void cleanOldBackups() {
        try {
            File backupFolder = new File(BACKUP_DIR);
            File[] backups = backupFolder.listFiles();
            if(backups == null) return;
            
            long thirtyDaysAgo = System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000);
            for(File backup : backups) {
                if(backup.lastModified() < thirtyDaysAgo) {
                    backup.delete();
                }
            }
        } catch(Exception e) {
            System.out.println("Backup cleanup failed: " + e.getMessage());
        }
    }
    
    // make file readable but not writable
    private void setReadOnlyMode(boolean readOnly) {
        File file = new File(fileName);
        if(file.exists()) {
            file.setWritable(!readOnly);
            if(readOnly) {
                file.setReadOnly();
            }
        }
    }
    
    // clear file contents
    public void clearFile() {
        setReadOnlyMode(false);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            writer.write("");
        } catch(IOException e) {
            System.out.println("Error clearing file: " + e.getMessage());
        }
        setReadOnlyMode(true);
    }
    
    // save expense to encrypted file
    public String save(Expense expense) {
        setReadOnlyMode(false);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            String results = String.format("%d|%.2f|%s|%s|%s|%s",
                expense.getId(),
                expense.getAmount(),
                expense.getCategory().getType(),
                expense.getCategory().getQuantity(),
                expense.getCategory().getDescription(),
                expense.getDate());
            
            String encryptedLine = encrypt(results);
            writer.write(encryptedLine);
            writer.newLine();
            
        } catch(IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        } catch(Exception e) {
            System.out.println("Encryption error: " + e.getMessage());
        }
        setReadOnlyMode(true);
        createBackup();  // auto backup after save
        return "Expense saved to encrypted file";
    }
    
    // read all expenses from encrypted file
    public List<Expense> findAll() {
        fileExpeList.clear();
        File file = new File(fileName);
        if(!file.exists()) {
            return fileExpeList;
        }
        
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String encryptedLine = reader.readLine();
            while(encryptedLine != null) {
                if(!encryptedLine.trim().isEmpty()) {
                    try {
                        String decryptedLine = decrypt(encryptedLine.trim());
                        String[] parts = decryptedLine.split("\\|");
                        if(parts.length >= 6) {
                            int id = Integer.parseInt(parts[0].trim());
                            double amount = Double.parseDouble(parts[1].trim());
                            String type = parts[2].trim();
                            String quantity = parts[3].trim();
                            String description = parts[4].trim();
                            LocalDate date = LocalDate.parse(parts[5].trim());
                            
                            Category category = new Category(type, quantity, description);
                            Expense expense = new Expense(id, amount, category, date);
                            fileExpeList.add(expense);
                        }
                    } catch(Exception e) {
                        System.out.println("Skipping corrupted line: " + encryptedLine);
                    }
                }
                encryptedLine = reader.readLine();
            }
        } catch(IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch(Exception e) {
            System.out.println("Decryption error: " + e.getMessage());
        }
        return fileExpeList;
    }
    
    // write to human readable format (not encrypted for viewing)
    public void writeToHumanReadable(Expense expense) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(humanReadable, true))) {
            writer.write(expense.toString());
            writer.newLine();
        } catch(IOException e) {
            System.out.println("Error writing readable file: " + e.getMessage());
        }
    }
    
    // restore from backup
    public boolean restoreFromBackup(String backupFileName) {
        File backupFile = new File(BACKUP_DIR + backupFileName);
        File currentFile = new File(fileName);
        
        if(!backupFile.exists()) {
            System.out.println("Backup file not found");
            return false;
        }
        
        setReadOnlyMode(false);
        try {
            Files.copy(backupFile.toPath(), currentFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Restored from backup: " + backupFileName);
            return true;
        } catch(IOException e) {
            System.out.println("Restore failed: " + e.getMessage());
            return false;
        } finally {
            setReadOnlyMode(true);
        }
    }
    
    // list all available backups
    public List<String> listBackups() {
        List<String> backups = new ArrayList<>();
        File backupFolder = new File(BACKUP_DIR);
        File[] files = backupFolder.listFiles((dir, name) -> name.endsWith(".encrypted"));
        
        if(files != null) {
            for(File file : files) {
                backups.add(file.getName() + " - " + new Date(file.lastModified()));
            }
        }
        return backups;
    }
    
    // export to CSV for external use
    public void exportToCsv() {
        String csvFile = DATA_DIR + "export_" + LocalDate.now() + ".csv";
        setReadOnlyMode(false);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write("ID,Amount,Category,Quantity,Description,Date\n");
            for(Expense expense : findAll()) {
                writer.write(String.format("%d,%.2f,%s,%s,%s,%s\n",
                    expense.getId(),
                    expense.getAmount(),
                    expense.getCategory().getType(),
                    expense.getCategory().getQuantity(),
                    expense.getCategory().getDescription(),
                    expense.getDate()));
            }
            System.out.println("Exported to: " + csvFile);
        } catch(IOException e) {
            System.out.println("Export failed: " + e.getMessage());
        }
        setReadOnlyMode(true);
    }
}